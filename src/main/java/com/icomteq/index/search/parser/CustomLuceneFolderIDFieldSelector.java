package com.icomteq.index.search.parser;

import org.apache.lucene.document.FieldSelector;
import org.apache.lucene.document.FieldSelectorResult;

public class CustomLuceneFolderIDFieldSelector implements FieldSelector {
	/**
	 * Auto-generated serial ID.
	 */
	private static final long serialVersionUID = -3699163239138111312L;

	public static CustomLuceneFolderIDFieldSelector instance() {
		return Singleton.INSTANCE;
	}

	@Override
	public FieldSelectorResult accept(String fieldName) {
		if (fieldName.equals("id")) {
			return FieldSelectorResult.LOAD;
		} else if (fieldName.equals("actualfiletypeextension")) {
			return FieldSelectorResult.LOAD;
		} else if (fieldName.equals("duplicateloadfiledocid")) {
			return FieldSelectorResult.LOAD;
		} else if (fieldName.equals("duplicatedocumentobjectid")) {
			return FieldSelectorResult.LOAD;
		}  else if (fieldName.equals("documentdate")) {
			return FieldSelectorResult.LOAD;
		} else if (fieldName.equals("familyloadfiledocid")) {
			return FieldSelectorResult.LOAD;
		} else if (fieldName.equals("familydocumentobjectid")) {
			return FieldSelectorResult.LOAD;
		} else if (fieldName.equals("emailthreadid")) {
			return FieldSelectorResult.LOAD;
		} else if (fieldName.equals("neardupeid")) {
			return FieldSelectorResult.LOAD;
		} else if (fieldName.equals("neardupeobjectid")) {
			return FieldSelectorResult.LOAD;
		} else if (fieldName.equals("loadfiledocid")) {
			return FieldSelectorResult.LOAD;
		} else if (fieldName.equals("hasnativefile")) {
			return FieldSelectorResult.LOAD;
		} else {
			return FieldSelectorResult.NO_LOAD;
		}
	}

	private static class Singleton {
		private static final CustomLuceneFolderIDFieldSelector INSTANCE = new CustomLuceneFolderIDFieldSelector();
	}
}
