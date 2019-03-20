package com.icomteq.index.search.models;

import java.util.List;

/**
 * POJO binded in Angular 2. Warning! Any change on below instance variables
 * should be reflected in the equivalent Angular class.
 * 
 * @author marianne.rey
 *
 */
public class CaseFilterOptionList {

	private List<CaseFilterOptionItem> optionList;

	public List<CaseFilterOptionItem> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<CaseFilterOptionItem> optionList) {
		this.optionList = optionList;
	}

	public static class CaseFilterOptionItem {

		private long optionID;
		private String optionName;
		private String value;

		public long getOptionID() {
			return optionID;
		}

		public void setOptionID(long optionID) {
			this.optionID = optionID;
		}

		public String getOptionName() {
			return optionName;
		}

		public void setOptionName(String optionName) {
			this.optionName = optionName;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}
}
