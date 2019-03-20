package com.icomteq.index.search.parser;

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.analysis.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.util.Version;

public class LuceneDefaultAnalyzer {
	private static Analyzer INSTANCE;

	private LuceneDefaultAnalyzer() {
		// TODO: make this constructor private to avoid any other initialization.
	}

	public static Analyzer getInstance() {
		if (INSTANCE == null) {
			Map<String, Analyzer> analyzers = new HashMap<String, Analyzer>();
			analyzers.put("folderid", new WhitespaceAnalyzer(Version.LUCENE_36));
			INSTANCE = new PerFieldAnalyzerWrapper(new KeywordAnalyzer(), analyzers);
		}

		return INSTANCE;
	}
}