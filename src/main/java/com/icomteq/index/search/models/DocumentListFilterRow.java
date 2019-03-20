package com.icomteq.index.search.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.icomteq.library.persistence.cases.model.CaseFieldCachedEntity;
import com.icomteq.search.enums.BooleanOperator;

/**
 * POJO binded in Angular 2. Warning! Any change on below instance variables
 * should be reflected in the equivalent Angular class. ss
 * 
 * @author marianne.rey
 *
 */
public class DocumentListFilterRow {

	@JsonProperty("openGroup")
	private String openGroup;

	@JsonProperty("field")
	private Integer field;

	@JsonProperty("filterValue")
	private String filterValue;

	@JsonProperty("filterOperator")
	private String filterOperator;

	@JsonProperty("closeGroup")
	private String closeGroup;

	@JsonProperty("booleanOperator")
	private String booleanOperatorStr;
	@JsonIgnore
	private BooleanOperator booleanOperator;

	@JsonIgnore
	private Date dateTimeFromType;
	@JsonIgnore
	private Date dateTimeToType;

	@JsonProperty("dateFrom")
	private String dateFrom;

	@JsonProperty("dateTo")
	private String dateTo;

	@JsonIgnore
	private CaseFieldCachedEntity caseField;

	@JsonProperty("selectedOptions")
	private List<String> selectedOptions;

	public String getOpenGroup() {
		return openGroup;
	}

	public void setOpenGroup(String openGroup) {
		this.openGroup = openGroup;
	}

	public Integer getField() {
		return field;
	}

	public void setField(Integer field) {
		this.field = field;
	}

	public String getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}

	public String getFilterOperator() {
		return filterOperator;
	}

	public void setFilterOperator(String filterOperator) {
		this.filterOperator = filterOperator;
	}

	public String getCloseGroup() {
		return closeGroup;
	}

	public void setCloseGroup(String closeGroup) {
		this.closeGroup = closeGroup;
	}

	public String getBooleanOperatorStr() {
		return booleanOperatorStr;
	}

	public void setBooleanOperatorStr(String booleanOperatorStr) {
		this.booleanOperatorStr = booleanOperatorStr;
	}

	public BooleanOperator getBooleanOperator() {
		return booleanOperator;
	}

	public void setBooleanOperator(BooleanOperator booleanOperator) {
		this.booleanOperator = booleanOperator;
	}

	public CaseFieldCachedEntity getCaseField() {
		return caseField;
	}

	public void setCaseField(CaseFieldCachedEntity caseField) {
		this.caseField = caseField;
	}

	public Date getDateTimeFromType() {
		return dateTimeFromType;
	}

	public void setDateTimeFromType(Date dateTimeFromType) {
		this.dateTimeFromType = dateTimeFromType;
	}

	public Date getDateTimeToType() {
		return dateTimeToType;
	}

	public void setDateTimeToType(Date dateTimeToType) {
		this.dateTimeToType = dateTimeToType;
	}

	public List<String> getSelectedOptions() {
		return selectedOptions;
	}

	public void setSelectedOptions(List<String> selectedOptions) {
		this.selectedOptions = selectedOptions;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

}
