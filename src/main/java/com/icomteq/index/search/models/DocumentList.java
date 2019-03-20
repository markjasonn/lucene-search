package com.icomteq.index.search.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DocumentList {

	private Integer activeTotalSearchCount;
	@JsonIgnore
	private List<Long> activeSearchResultIds;
	private List<DocumentItem> documents;
	private Integer totalResultCount;
	private boolean filterActive;
	private Integer viewObjectId;


	public List<DocumentItem> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentItem> documents) {
		this.documents = documents;
	}

	public Integer getActiveTotalSearchCount() {
		return activeTotalSearchCount;
	}

	public void setActiveTotalSearchCount(Integer activeTotalSearchCount) {
		this.activeTotalSearchCount = activeTotalSearchCount;
	}

	public List<Long> getActiveSearchResultIds() {
		return activeSearchResultIds;
	}

	public void setActiveSearchResultIds(List<Long> activeSearchResultIds) {
		this.activeSearchResultIds = activeSearchResultIds;
	}

	public Integer getTotalResultCount() {
		return totalResultCount;
	}

	public void setTotalResultCount(Integer totalResultCount) {
		this.totalResultCount = totalResultCount;
	}

	public boolean isFilterActive() {
		return filterActive;
	}

	public void setFilterActive(boolean filterActive) {
		this.filterActive = filterActive;
	}

	public Integer getViewObjectId() {
		return viewObjectId;
	}

	public void setViewObjectId(Integer viewObjectId) {
		this.viewObjectId = viewObjectId;
	}

}
