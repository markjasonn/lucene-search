package com.icomteq.index.search.behavior;

import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

public class LuceneSimpleHighlighter extends SimpleHTMLFormatter {
	public LuceneSimpleHighlighter(String preTag, String postTag) {
		super(preTag, postTag);
	}
}
