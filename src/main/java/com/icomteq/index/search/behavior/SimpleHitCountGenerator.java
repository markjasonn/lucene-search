package com.icomteq.index.search.behavior;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.lucene.analysis.standard.UAX29URLEmailAnalyzer;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.TooManyClauses;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.icomteq.index.search.models.CustomLuceneSingleFieldSelector;
import com.icomteq.index.search.parser.CustomLuceneFolderIDFieldSelector;
import com.icomteq.index.search.parser.CustomMultiTermQueryParser;
import com.icomteq.index.search.parser.LuceneDefaultAnalyzer;
import com.icomteq.index.search.util.CollectionUtil;

public class SimpleHitCountGenerator {
    private long hitCount;

    public SimpleHitCountGenerator(Integer caseId, long[] sourceId, Long maxDocs, String finalQuery, String indexFilePath) {
	IndexReader reader_primary = null;
	IndexReader reader_secondary = null;
	// Get the indexFilePath

	StringBuilder sourceIdQuery = new StringBuilder();

	for (long sid : sourceId) {
	    sourceIdQuery.append("folderid:").append(sid).append(" OR ");
	}

	sourceIdQuery = new StringBuilder(sourceIdQuery.substring(0, sourceIdQuery.lastIndexOf("OR")));

	try {
	    reader_primary = IndexReader.open(FSDirectory.open(new File(indexFilePath + "/indexfiles/INX_Contents")));
	    reader_secondary = IndexReader.open(FSDirectory.open(new File(indexFilePath + "/indexfiles/INX_Folders")));

	    List<Long> docs = CollectionUtil.intersection(searchPrimaryIndex(reader_primary, finalQuery, maxDocs), searchSecondaryIndex(reader_secondary, sourceIdQuery.toString(), maxDocs));

	    hitCount = Long.valueOf(docs.size());

	} catch (CorruptIndexException e) {
	    e.printStackTrace();
	} catch (IOException e) {

	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private List<Long> searchPrimaryIndex(IndexReader reader, String finalQuery, long maxDocs) {
	ScoreDoc[] hits;
	IndexSearcher searcher = null;
	List<Long> result = new ArrayList<Long>();

	try {
	    searcher = new IndexSearcher(reader);
	    UAX29URLEmailAnalyzer analyzer = new UAX29URLEmailAnalyzer(Version.LUCENE_36, Collections.EMPTY_SET);
	    QueryParser parser = new QueryParser(Version.LUCENE_36, "content", analyzer);
	    parser.setAutoGeneratePhraseQueries(true);
	    parser.setAllowLeadingWildcard(true);
	    parser.setDefaultOperator(QueryParser.Operator.OR);
	    Query query = parser.parse(finalQuery);
	    TopDocs tp;
	    tp = searcher.search(query, 1);
	    TopScoreDocCollector collector = TopScoreDocCollector.create(tp.totalHits + 1, true);

	    try {
		searcher.search(query, collector);
	    } catch (TooManyClauses manyClauses) {
		// log.info("TOO MANY CLAUSES >> will begin search again");
		BooleanQuery.setMaxClauseCount(Integer.MAX_VALUE);
		searcher.search(query, collector);
		BooleanQuery.setMaxClauseCount(1024);
	    }

	    hits = collector.topDocs().scoreDocs;
	    for (int i = 0; i < hits.length; i++) {
		Document hitDoc = searcher.getIndexReader().document(hits[i].doc, CustomLuceneSingleFieldSelector.instance()); // getting
															       // actual
															       // document
		result.add(Long.valueOf(hitDoc.get("id")));
		hitDoc = null;
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (ParseException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return result;
    }

    private List<Long> searchSecondaryIndex(IndexReader reader, String finalQuery, long maxDocReturn) {
	List<Long> result = new ArrayList<Long>();
	ScoreDoc[] hits = null;

	try {
	    IndexSearcher searcher = new IndexSearcher(reader);
	    Query q = getLuceneQueryInstance(finalQuery);
	    TopDocs tp = searcher.search(q, 1);
	    TopScoreDocCollector collector = TopScoreDocCollector.create(tp.totalHits + 1, true);
	    searcher.search(q, collector);
	    hits = collector.topDocs().scoreDocs;

	    for (int i = 0; i < hits.length; i++) {
		Document hitDoc = searcher.doc(hits[i].doc, CustomLuceneFolderIDFieldSelector.instance());
		result.add(Long.valueOf(hitDoc.get("id")));
	    }
	} catch (ParseException e) {

	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return result;
    }

    private Query getLuceneQueryInstance(String folderObjectId) throws ParseException {
	CustomMultiTermQueryParser parser = new CustomMultiTermQueryParser(Version.LUCENE_36, "folderid", LuceneDefaultAnalyzer.getInstance());
	parser.setAllowLeadingWildcard(true);
	parser.setDefaultOperator(Operator.AND);
	parser.setDateResolution(Resolution.MILLISECOND);
	Query baseQuery = null;

	baseQuery = parser.parse(folderObjectId);

	return baseQuery;
    }

    public long getHitCount() {
	return hitCount;
    }

    public void setHitCount(long hitCount) {
	this.hitCount = hitCount;
    }
}
