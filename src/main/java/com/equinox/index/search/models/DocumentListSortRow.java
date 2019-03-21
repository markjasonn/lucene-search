package com.equinox.index.search.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.icomteq.library.persistence.cases.model.CaseFieldCachedEntity;

public class DocumentListSortRow {
	
	@JsonIgnore
	private CaseFieldCachedEntity caseField;
	
	@JsonProperty("orderSequence")
	private String orderSequence;
	
	@JsonProperty("field")	
	private Integer field;

	public CaseFieldCachedEntity getCaseField() {
		return caseField;
	}

	public void setCaseField(CaseFieldCachedEntity caseField) {
		this.caseField = caseField;
	}

	public String getOrderSequence() {
		return orderSequence;
	}

	public void setOrderSequence(String orderSequence) {
		this.orderSequence = orderSequence;
	}

	public Integer getField() {
	    return field;
	}

	public void setField(Integer field) {
	    this.field = field;
	}
	
	

}
