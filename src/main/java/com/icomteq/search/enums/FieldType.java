package com.icomteq.search.enums;

public enum FieldType {
	DATE((byte) 6, "DATE"),
	CUSTODIAN((byte) 12, "OBJECT"),
	TIMESTAMP((byte) 8, "TIMESTAMP"),
	STRING((byte) 2, "STRING"),
	BOOLEAN((byte) 9, "BOOLEAN"),
	MULTIPLE_CHOICE((byte) 11, "MULTIPLE CHOICE"),
	NUMERIC((byte) 4, "NUMERIC"),
	SINGLE_CHOICE((byte) 10, "SINGLE CHOICE"),
	COMBINED_FIELD((byte) 19, "COMBINED FIELD");

	private byte fieldTypeCode;
	private String longName;

	FieldType(byte fieldTypeCode, String longName) {
		this.fieldTypeCode = fieldTypeCode;
		this.longName = longName;
	}

	public String getLongName() {
		return longName;
	}

	public byte getFieldTypeCode() {
		return fieldTypeCode;
	}

	public void setFieldTypeCode(byte fieldTypeCode) {
		this.fieldTypeCode = fieldTypeCode;
	}

	public static FieldType getFieldTypeByFieldTypeCodeProperty(byte fieldTypeCodeProperty) {
		FieldType result = FieldType.STRING;

		for (FieldType type : values()) {
			if (type.getFieldTypeCode() == fieldTypeCodeProperty) {
				result = type;
				break;
			}
		}

		return result;
	}

	@Override
	public String toString() {
		return getLongName();
	}
}
