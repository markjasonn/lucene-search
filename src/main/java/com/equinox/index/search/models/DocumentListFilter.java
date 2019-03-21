package com.equinox.index.search.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO binded in Angular 2. Warning! Any change on below instance variables
 * should be reflected in the equivalent Angular class. ss
 * 
 * @author marianne.rey
 *
 */
public class DocumentListFilter {

	@JsonProperty("active")
	private boolean active;

	@JsonProperty("lastActiveFolderID")
	private long lastActiveFolderID;

	@JsonProperty("toggleFilter")
	private boolean toggleFilter;

	@JsonProperty("filterRows")
	private List<DocumentListFilterRow> filterRows;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public long getLastActiveFolderID() {
		return lastActiveFolderID;
	}

	public void setLastActiveFolderID(long lastActiveFolderID) {
		this.lastActiveFolderID = lastActiveFolderID;
	}

	public boolean isToggleFilter() {
		return toggleFilter;
	}

	public void setToggleFilter(boolean toggleFilter) {
		this.toggleFilter = toggleFilter;
	}

	public List<DocumentListFilterRow> getFilterRows() {
		return filterRows;
	}

	public void setFilterRows(List<DocumentListFilterRow> filterRows) {
		this.filterRows = filterRows;
	}

}