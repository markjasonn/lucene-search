package com.equinox.index.search.models;

import java.util.List;

/**
 * POJO binded in Angular 2. Warning! Any change on below instance variables
 * should be reflected in the equivalent Angular class.
 * 
 * @author marianne.rey
 *
 */
public class CaseFilterList {

	private List<CaseFilterItem> caseFilterItemList;

	public List<CaseFilterItem> getCaseFilterItemList() {
		return caseFilterItemList;
	}

	public void setCaseFilterItemList(List<CaseFilterItem> caseFilterItemList) {
		this.caseFilterItemList = caseFilterItemList;
	}

	public static class CaseFilterItem {
		private long filterID;
		private byte filterTypeCode;
		private String fieldTypeName;
		private String filterName;

		public long getFilterID() {
			return filterID;
		}

		public void setFilterID(int filterID) {
			this.filterID = filterID;
		}

		public byte getFilterTypeCode() {
			return filterTypeCode;
		}

		public void setFilterTypeCode(byte filterTypeCode) {
			this.filterTypeCode = filterTypeCode;
		}

		public String getFilterName() {
			return filterName;
		}

		public void setFilterName(String filterName) {
			this.filterName = filterName;
		}

		public String getFieldTypeName() {
			return fieldTypeName;
		}

		public void setFieldTypeName(String fieldTypeName) {
			this.fieldTypeName = fieldTypeName;
		}

	}
}
