package com.icomteq.index.search.models;

import java.util.List;

/**
 * Lucene Search Parameter wrapper
 * @author mjbnacional
 *
 */
public class LuceneSearchWrapper {
	
	private LuceneSearchParameter params;
	private List<Long> result;

	public LuceneSearchParameter getParams() {
		return params;
	}

	public void setParams(LuceneSearchParameter params) {
		this.params = params;
	}

	public List<Long> getResult() {
		return result;
	}

	public void setResult(List<Long> result) {
		this.result = result;
	}
}
