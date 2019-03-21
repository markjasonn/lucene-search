package com.equinox.index.search.behavior;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.standard.UAX29URLEmailAnalyzer;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.TooManyClauses;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopFieldCollector;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.equinox.index.search.models.CustomLuceneSingleFieldSelector;
import com.equinox.index.search.models.DocumentListFilter;
import com.equinox.index.search.models.DocumentListProperty;
import com.equinox.index.search.models.DocumentListSort;
import com.equinox.index.search.models.DocumentListSortRow;
import com.equinox.index.search.models.LuceneSearchParameter;
import com.equinox.index.search.models.LuceneSearchWrapper;
import com.equinox.index.search.models.Searchable;
import com.equinox.index.search.parser.CustomLuceneFolderIDFieldSelector;
import com.equinox.index.search.parser.CustomMultiTermQueryParser;
import com.equinox.index.search.parser.LuceneDefaultAnalyzer;
import com.equinox.search.enums.FieldType;
import com.equinox.search.enums.OrderSequence;

public class SimpleLuceneSearch extends LuceneSearchWrapper implements Searchable {

	static final Logger logger = LoggerFactory.getLogger(SimpleLuceneSearch.class);

	public SimpleLuceneSearch(LuceneSearchParameter params) {
		this.setParams(params);
	}

	public SimpleLuceneSearch() {
		this.setParams(null);
	}

	@Override
	public void search() {
		IndexReader reader_primary = null;
		IndexReader reader_secondary = null;
		try {

			reader_primary = IndexReader.open(FSDirectory.open(new File(this.getParams().getIndexFilePath() + "/indexfiles/INX_Contents")));
			reader_secondary = IndexReader.open(FSDirectory.open(new File(this.getParams().getIndexFilePath() + "/indexfiles/INX_Folders")));

			List<Long> result = searchDocumentsAll(reader_primary, reader_secondary, this.getParams().getQuery(), this.getParams().getMetaQuery(),
					this.getParams().getDocsPerPage(), this.getParams().getPageNum(), this.getParams().getCaseID(), this.getParams().getTotalDocs(),
					this.getParams().getDocumentListProperty());
			setResult(result);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private LinkedHashSet<Long> searchPrimaryIndex(IndexReader reader, String finalQuery, int maxDocs) throws IOException {
		ScoreDoc[] hits;
		IndexSearcher searcher = null;
		LinkedHashSet<Long> result = new LinkedHashSet<Long>();
		try {
			searcher = new IndexSearcher(reader);
			UAX29URLEmailAnalyzer analyzer = new UAX29URLEmailAnalyzer(Version.LUCENE_36, Collections.EMPTY_SET);
			QueryParser parser = new QueryParser(Version.LUCENE_36, "content", analyzer);
			parser.setAutoGeneratePhraseQueries(false);
			parser.setAllowLeadingWildcard(true);
			parser.setDefaultOperator(QueryParser.Operator.OR);
			Query query = parser.parse(finalQuery);
			TopDocs tp;
			tp = searcher.search(query, 1);
			TopScoreDocCollector collector = TopScoreDocCollector.create(tp.totalHits + 1, true);

			try {
				searcher.search(query, collector);
			} catch (TooManyClauses manyClauses) {
				logger.info("TOO MANY CLAUSES >> will begin search again");
				BooleanQuery.setMaxClauseCount(Integer.MAX_VALUE);
				searcher.search(query, collector);
				BooleanQuery.setMaxClauseCount(1024);
			}

			hits = collector.topDocs().scoreDocs;
			for (int i = 0; i < hits.length; i++) {
				Document hitDoc = searcher.getIndexReader().document(hits[i].doc, CustomLuceneSingleFieldSelector.instance());
				result.add(Long.valueOf(hitDoc.get("id")));
				hitDoc = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (searcher != null) {
				searcher.close();
			}

		}

		return result;
	}

	private LinkedHashSet<Long> searchSecondaryIndex(IndexReader reader, String finalQuery, int maxDocReturn, int currentPage, int docsPerPage)
			throws IOException {
		LinkedHashSet<Long> result = new LinkedHashSet<Long>();
		ScoreDoc[] hits = null;
		IndexSearcher searcher = null;
		logger.info("searchSecondaryIndex else");
		try {
			searcher = new IndexSearcher(reader);
			Query q = getLuceneQueryInstance(finalQuery);
			// Sort sorter = new Sort(new SortField("id",
			// FieldCache.DEFAULT_INT_PARSER));
			// TopFieldCollector topFields = TopFieldCollector.create(sorter,
			// maxDocReturn, true, false, false, false);
			// searcher.search(q, topFields);
			// hits =
			// topFields.topDocs(getFirstResultIndexDisplayed(currentPage,
			// docsPerPage), docsPerPage).scoreDocs;
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
		} finally {
			if (searcher != null) {
				searcher.close();
			}
		}
		return result;
	}

	private List<Long> searchDocumentsAll(IndexReader reader_primary, IndexReader reader_secondary, String query_primary, String query_secondary,
			int docsPerPage, int currentPage, int caseId, int totaldocs, DocumentListProperty docListProperty) {
		List<Long> docs = new ArrayList<Long>();

		Set<Long> docresult_primary = new LinkedHashSet<Long>();

		Set<Long> docresult_secondary = new LinkedHashSet<Long>();

		try {

			if (!"".equalsIgnoreCase(query_primary)) {
				docresult_primary = searchPrimaryIndex(reader_primary, query_primary, totaldocs);
			}

			if (docListProperty != null && docListProperty.getDocumentListFilter() != null
					&& (docListProperty.getDocumentListFilter().isActive() || docListProperty.getDocumentListSort().isActive())) {
				docresult_secondary = searchDocViaSecondaryIndex(reader_secondary, query_secondary, docListProperty.getDocumentListFilter()
						.getLastActiveFolderID(), docsPerPage, currentPage, docListProperty, caseId);
			} else {
				docresult_secondary = searchSecondaryIndex(reader_secondary, query_secondary, totaldocs, currentPage, docsPerPage);
			}

			if (!"".equalsIgnoreCase(query_primary)) {
				// logger.info("primary index: " + docresult_primary);
				// logger.info("secondary index: " + docresult_secondary);
				// docresult_primary.retainAll(docresult_secondary); //
				// intersect
				docresult_secondary.retainAll(docresult_primary);
				docs.addAll(docresult_secondary);
			} else {
				// logger.info("secondary index only: " +
				// docresult_secondary.size());
				docs.addAll(docresult_secondary);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return docs;
	}

	private Set<Long> searchDocViaSecondaryIndex(IndexReader reader, String finalQuery, Long folderObjectId, int viewPerPage, int currentPage,
			DocumentListProperty docListProperty, Integer caseId) throws IOException, ParseException, Exception {

		ScoreDoc[] hits = null;
		Set<Long> result = new LinkedHashSet<Long>();
		IndexSearcher searcher = new IndexSearcher(reader);
		Long source = getParams().getSourceFolders().length > 1 ? 0l : getParams().getSourceFolders()[0];
		Query q = null;

		// Integer totalDocs = getTotalDocsInCase(caseId);
		Integer totalDocs = docListProperty.getDocCount() == null ? 1000000 : docListProperty.getDocCount().intValue();

		q = getLuceneQueryInstance(finalQuery, docListProperty);
		logger.info("script mo > " + q.toString());
		logger.info("folderObjectId > " + folderObjectId);
		logger.info("sourceeeeee >> " + source);
		logger.info("lasr activated folder id >> " + docListProperty.getDocumentListSort().getLastActiveFolderID().longValue());

		try {
			Sort sorter = null;
			if (docListProperty.getDocumentListSort().isActive()
					&& (source == docListProperty.getDocumentListSort().getLastActiveFolderID().longValue())) {
				sorter = createSortConditionViaLucene(docListProperty.getDocumentListSort());
				logger.info("sort mo > " + sorter);
			} else {
				sorter = new Sort(new SortField("id", FieldCache.DEFAULT_INT_PARSER));
			}

			TopFieldCollector topFields = TopFieldCollector.create(sorter, totalDocs, true, false, false, false);
			searcher.search(q, topFields);
			hits = topFields.topDocs().scoreDocs;

			// Set the counts
			if (docListProperty != null) {
				// docListProperty.setDocCount(getTotalHits(reader,
				// folderObjectId, docListProperty.getDocumentListFilter(),
				// docListProperty));
				// docListProperty.setParentCount(getParentHits(reader,
				// folderObjectId, docListProperty.getDocumentListFilter()));
				// docListProperty.setAttachmentCount(getAttachmentsHits(reader,
				// folderObjectId, docListProperty.getDocumentListFilter()));
			}

			for (int i = 0; i < hits.length; i++) {
				Document hitDoc = searcher.doc(hits[i].doc, CustomLuceneFolderIDFieldSelector.instance());
				result.add(Long.valueOf(hitDoc.get("id")));
			}
		} finally {
			searcher.close();
			searcher = null;
		}

		return result;
	}

	private Sort createSortConditionViaLucene(DocumentListSort sortCondition) {
		boolean hasSortFields = false;
		List<SortField> sortConditions = new ArrayList<SortField>();

		for (DocumentListSortRow item : sortCondition.getSortFields()) {
			if (item.getField() != null && item.getField() != 0) {
				String fieldName = "CaseDocument".equalsIgnoreCase(item.getCaseField().getAttachedToObjectTypeTable()) ? item.getCaseField()
						.getFieldName().toLowerCase() : item.getCaseField().getFieldObjectID().toString();
				boolean reverse = OrderSequence.getOrderSequence(item.getOrderSequence()) == OrderSequence.ASCENDING ? false : true;
				if (item.getCaseField().getFieldObjectID() == 0l) {
					continue;
				}

				if (FieldType.getFieldTypeByFieldTypeCodeProperty(item.getCaseField().getFieldTypeCode()) == FieldType.MULTIPLE_CHOICE) {
					fieldName = fieldName.concat("_sortfield");
				}

				switch (FieldType.getFieldTypeByFieldTypeCodeProperty(item.getCaseField().getFieldTypeCode())) {
				case STRING:
				case BOOLEAN:
				case COMBINED_FIELD:
				case MULTIPLE_CHOICE:
				case SINGLE_CHOICE:
				case CUSTODIAN:
				case TIMESTAMP:
					logger.info("Text Sort by > " + fieldName);
					SortField textfield = new SortField(fieldName, SortField.STRING, reverse);
					sortConditions.add(textfield);
					break;
				case NUMERIC:
					logger.info("Numeric Sort by > " + fieldName);
					SortField numericfield = new SortField(fieldName, SortField.INT, reverse);
					sortConditions.add(numericfield);
					break;
				case DATE:
					logger.info("Date Sort by > " + fieldName);
					SortField date = new SortField(fieldName, FieldCache.NUMERIC_UTILS_LONG_PARSER, reverse);
					sortConditions.add(date);
				default:
					break;
				}

				hasSortFields = true;
			}
		}

		if (!hasSortFields) {
			return new Sort(new SortField("id", FieldCache.DEFAULT_INT_PARSER));
		} else {
			SortField[] a = (SortField[]) (sortConditions.toArray(new SortField[sortConditions.size()]));
			return new Sort(a);
		}
	}

	private Query getLuceneQueryInstance(String finalQuery, DocumentListProperty docListProperty) throws ParseException {
		CustomMultiTermQueryParser parser = new CustomMultiTermQueryParser(Version.LUCENE_36, "folderid", LuceneDefaultAnalyzer.getInstance());
		parser.setAllowLeadingWildcard(true);
		parser.setDefaultOperator(Operator.AND);
		parser.setDateResolution(Resolution.MILLISECOND);
		parser.setAutoGeneratePhraseQueries(true);
		Query baseQuery = getLuceneQueryInstance(finalQuery);

		if (docListProperty.getDocumentListFilter().isActive()) {
			if (docListProperty.getDocumentListFilter() != null && docListProperty.getDocumentListFilter().getFilterRows() != null) {
				String filterString = LuceneQueryGenerator.generateLuceneMetadataScript(docListProperty.getDocumentListFilter().getFilterRows());

				if (!"".equalsIgnoreCase(filterString)) {
					baseQuery = parser.parse("+(" + finalQuery + ")(" + filterString + ")");

				}
			} else {
				baseQuery = parser.parse(finalQuery);
			}
		} else {
			baseQuery = parser.parse(finalQuery);
		}

		return baseQuery;
	}

	private Query getLuceneQueryInstance(Long folderObjectId, DocumentListProperty docListProperty) throws ParseException {
		CustomMultiTermQueryParser parser = new CustomMultiTermQueryParser(Version.LUCENE_36, "folderid", LuceneDefaultAnalyzer.getInstance());
		parser.setAllowLeadingWildcard(true);
		parser.setDefaultOperator(Operator.AND);
		parser.setDateResolution(Resolution.MILLISECOND);
		Query baseQuery = null;
		if (docListProperty.getDocumentListFilter().getFilterRows() == null) {
			baseQuery = parser.parse("+(" + createBaseQuery(folderObjectId) + ")");
		} else {
			String filterString = LuceneQueryGenerator.generateLuceneMetadataScript(docListProperty.getDocumentListFilter().getFilterRows());

			if (!"".equalsIgnoreCase(filterString)) {
				if (folderObjectId != null && folderObjectId != 0) {
					baseQuery = parser.parse("+((" + createBaseQuery(folderObjectId) + ")(" + filterString + "))");
				} else {
					baseQuery = parser.parse("+((" + filterString + "))");
				}

			} else {
				baseQuery = parser.parse("+(" + createBaseQuery(folderObjectId) + ")");
			}
		}
		return baseQuery;
	}

	private String createBaseQuery(Long folderObjectId) {
		StringBuilder builder = new StringBuilder("folderid:" + folderObjectId.toString());
		builder = new StringBuilder("folderid:" + folderObjectId.toString());
		return builder.toString();
	}

	private int getFirstResultIndexDisplayed(int currentPage, int viewPerPage) {
		int rowIndex = 0;
		// if on the first page, list should start on first or index 0
		if (currentPage == 1) {
			rowIndex = 0;
		} else if (currentPage > 1) {
			rowIndex = (currentPage - 1) * viewPerPage;
		}

		logger.info("page > " + currentPage);
		logger.info("rowindex > " + rowIndex);
		logger.info("viewPerPage > " + viewPerPage);
		return rowIndex;
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

	private Long getAttachmentsHits(IndexReader reader, Long folderObjectId, DocumentListFilter filterCondition) {
		// TODO Auto-generated method stub
		return null;
	}

	private Long getParentHits(IndexReader reader, Long folderObjectId, DocumentListFilter filterCondition) {
		// TODO Auto-generated method stub
		return null;
	}

	private Long getTotalHits(IndexReader reader, Long folderObjectId, DocumentListFilter filterCondition, DocumentListProperty docListProperty) {
		// TODO Auto-generated method stub
		return null;
	}

	private Integer getTotalDocsInCase(Integer caseId) {
		// TODO Auto-generated method stub
		return null;
	}
}
