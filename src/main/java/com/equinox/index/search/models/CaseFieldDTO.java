package com.equinox.index.search.models;


public class CaseFieldDTO {

    private Long fieldObjectID;

	private byte fieldObjectType;

	private byte fieldTypeCode;

	private String fieldName;

	private String attachedToObjectTypeTable;

	private boolean filterableFlag;

	private boolean sortableFlag;

	private boolean forConflictCheckFlag;

	private boolean hasOptions;

	private Integer fieldValueObjectType;

	private Integer fieldSequenceID;

	private String fieldDisplayShortName;
	
	private String fieldDisplayLongName;

	private String formatSpec;

	private Long length;

	private Integer displayWidth;

	private String filterSpec;

	private String booleanTrueDisplay = "Yes";

	private String booleanFalseDisplay = "No";

	private boolean allowCaseEdit;

	private boolean isRestricted;
	
	private boolean recordActiveFlag;

	private boolean isRequired;

	public Long getFieldObjectID() {
	    return fieldObjectID;
	}

	public void setFieldObjectID(Long fieldObjectID) {
	    this.fieldObjectID = fieldObjectID;
	}

	public byte getFieldObjectType() {
	    return fieldObjectType;
	}

	public void setFieldObjectType(byte fieldObjectType) {
	    this.fieldObjectType = fieldObjectType;
	}

	public byte getFieldTypeCode() {
	    return fieldTypeCode;
	}

	public void setFieldTypeCode(byte fieldTypeCode) {
	    this.fieldTypeCode = fieldTypeCode;
	}

	public String getFieldName() {
	    return fieldName;
	}

	public void setFieldName(String fieldName) {
	    this.fieldName = fieldName;
	}

	public String getAttachedToObjectTypeTable() {
	    return attachedToObjectTypeTable;
	}

	public void setAttachedToObjectTypeTable(String attachedToObjectTypeTable) {
	    this.attachedToObjectTypeTable = attachedToObjectTypeTable;
	}

	public boolean isFilterableFlag() {
	    return filterableFlag;
	}

	public void setFilterableFlag(boolean filterableFlag) {
	    this.filterableFlag = filterableFlag;
	}

	public boolean isSortableFlag() {
	    return sortableFlag;
	}

	public void setSortableFlag(boolean sortableFlag) {
	    this.sortableFlag = sortableFlag;
	}

	public boolean isForConflictCheckFlag() {
	    return forConflictCheckFlag;
	}

	public void setForConflictCheckFlag(boolean forConflictCheckFlag) {
	    this.forConflictCheckFlag = forConflictCheckFlag;
	}

	public boolean isHasOptions() {
	    return hasOptions;
	}

	public void setHasOptions(boolean hasOptions) {
	    this.hasOptions = hasOptions;
	}

	public Integer getFieldValueObjectType() {
	    return fieldValueObjectType;
	}

	public void setFieldValueObjectType(Integer fieldValueObjectType) {
	    this.fieldValueObjectType = fieldValueObjectType;
	}

	public Integer getFieldSequenceID() {
	    return fieldSequenceID;
	}

	public void setFieldSequenceID(Integer fieldSequenceID) {
	    this.fieldSequenceID = fieldSequenceID;
	}

	public String getFieldDisplayShortName() {
	    return fieldDisplayShortName;
	}

	public void setFieldDisplayShortName(String fieldDisplayShortName) {
	    this.fieldDisplayShortName = fieldDisplayShortName;
	}

	public String getFieldDisplayLongName() {
	    return fieldDisplayLongName;
	}

	public void setFieldDisplayLongName(String fieldDisplayLongName) {
	    this.fieldDisplayLongName = fieldDisplayLongName;
	}

	public String getFormatSpec() {
	    return formatSpec;
	}

	public void setFormatSpec(String formatSpec) {
	    this.formatSpec = formatSpec;
	}

	public Long getLength() {
	    return length;
	}

	public void setLength(Long length) {
	    this.length = length;
	}

	public Integer getDisplayWidth() {
	    return displayWidth;
	}

	public void setDisplayWidth(Integer displayWidth) {
	    this.displayWidth = displayWidth;
	}

	public String getFilterSpec() {
	    return filterSpec;
	}

	public void setFilterSpec(String filterSpec) {
	    this.filterSpec = filterSpec;
	}

	public String getBooleanTrueDisplay() {
	    return booleanTrueDisplay;
	}

	public void setBooleanTrueDisplay(String booleanTrueDisplay) {
	    this.booleanTrueDisplay = booleanTrueDisplay;
	}

	public String getBooleanFalseDisplay() {
	    return booleanFalseDisplay;
	}

	public void setBooleanFalseDisplay(String booleanFalseDisplay) {
	    this.booleanFalseDisplay = booleanFalseDisplay;
	}

	public boolean isAllowCaseEdit() {
	    return allowCaseEdit;
	}

	public void setAllowCaseEdit(boolean allowCaseEdit) {
	    this.allowCaseEdit = allowCaseEdit;
	}

	public boolean isRestricted() {
	    return isRestricted;
	}

	public void setRestricted(boolean isRestricted) {
	    this.isRestricted = isRestricted;
	}

	public boolean isRecordActiveFlag() {
	    return recordActiveFlag;
	}

	public void setRecordActiveFlag(boolean recordActiveFlag) {
	    this.recordActiveFlag = recordActiveFlag;
	}

	public boolean isRequired() {
	    return isRequired;
	}

	public void setRequired(boolean isRequired) {
	    this.isRequired = isRequired;
	}
	
	
}
