package com.equinox.search.enums;

public enum MetadataFilterOperator {
	OR("Or"),
	AND("And"),
	NOT("Not"),
	EQUALS("Equals"),
	DOES_NOT_EQUAL("Does not Equal"),
	IS_SET("Is Set"),
	IS_NOT_SET("Is Not Set"),
	IS_LIKE("Is Like"),
	IS_NOT_LIKE("Is Not Like"),
	CONTAINS("Contains"),
	DOES_NOT_CONTAIN("Does not contain"),
	IS_AFTER("Is After"),
	IS_BEFORE("Is Before"),
	ENDS_WITH("Ends With"),
	BEGINS_WITH("Begins with"),
	IS_WITHIN("Is Within"),
	IS_BETWEEN("Is Between"),
	IS_LESS_THAN("Is Less Than"),
	IS_GREATER_THAN("Is Greater Than"),
	IS_ON_OR_AFTER("Is On or After"),
	IS_ON_OR_BEFORE("Is On or Before"),
	IS_ANY_OF_THESE("Is Any of These"),
	IS_ALL_OF_THESE("Is All of These"),
	IS_NONE_OF_THESE("Is None of These");

	private String description;

	private MetadataFilterOperator(String description) {
		this.setDescription(description);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static MetadataFilterOperator getOperatorByString(String filterOperator) {
		MetadataFilterOperator result = null;

		for (MetadataFilterOperator operator : values()) {
			if (filterOperator.equalsIgnoreCase(operator.getDescription())) {
				result = operator;
				break;
			}
		}

		if (result != null) {
			return result;
		} else {
			throw new IllegalArgumentException(filterOperator + " does not generate a MetadataFilterOperator object. Please check parameter");
		}
	}
}
