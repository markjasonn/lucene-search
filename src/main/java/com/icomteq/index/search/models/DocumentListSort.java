package com.icomteq.index.search.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentListSort {

	@JsonProperty("active")
	private boolean active;
	
	@JsonProperty("lastActiveFolderID")
	private Long lastActiveFolderID;
	
	@JsonProperty("toggleSort")
	private boolean toggleSort;

	@JsonProperty("sortFields")
	private List<DocumentListSortRow> sortFields;
	
	public DocumentListSort() {
		this.setSortFields(new ArrayList<DocumentListSortRow>());
	}
	
	/**
	 * Constructor for setting the maximum level sort fields.
	 * @param level
	 */
	public DocumentListSort(Integer level) {
		this.setLastActiveFolderID(0l);
		this.setSortFields(new ArrayList<DocumentListSortRow>());
	
		for(int x = 1; x <= level; x++) {
			DocumentListSortRow row = new DocumentListSortRow();
			sortFields.add(row);
		}
	}
	
	public boolean isToggleSort() {
		return toggleSort;
	}

	public void setToggleSort(boolean toggleSort) {
		this.toggleSort = toggleSort;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Long getLastActiveFolderID() {
		return lastActiveFolderID;
	}

	public void setLastActiveFolderID(Long lastActiveFolderID) {
		this.lastActiveFolderID = lastActiveFolderID;
	}

	public List<DocumentListSortRow> getSortFields() {
		return sortFields;
	}

	public void setSortFields(List<DocumentListSortRow> sortFields) {
		this.sortFields = sortFields;
	}

}