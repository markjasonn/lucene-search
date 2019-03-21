package com.equinox.index.search.models;

import org.apache.lucene.document.FieldSelector;
import org.apache.lucene.document.FieldSelectorResult;

/**
 * A {@code FieldSelector} class for Lucene Searching for mainting the loading behavior of the fields in an index.
 * 
 * @since 4.1.7
 * @version 1.0
 * @author Mark Jason Nacional
 *
 */
public class CustomLuceneSingleFieldSelector implements FieldSelector {

	/**
	 * Auto-generated serial ID.
	 */
	private static final long serialVersionUID = -3699163239138111312L;

	public static CustomLuceneSingleFieldSelector instance() {
		return Singleton.INSTANCE;
	}

	@Override
	public FieldSelectorResult accept(String fieldName) {
		if (fieldName.equals("id")) {
			return FieldSelectorResult.LOAD_AND_BREAK;
		} else {
			return FieldSelectorResult.NO_LOAD;
		}
	}

	private static class Singleton {
		private static final CustomLuceneSingleFieldSelector INSTANCE = new CustomLuceneSingleFieldSelector();
	}
}

