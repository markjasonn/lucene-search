package com.equinox.search.enums;

public enum OrderSequence {
	ASCENDING("ascending"),
	DESCENDING("descending");

	OrderSequence(String description) {
		this.setDescription(description);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String description;

	public static OrderSequence getOrderSequence(String orderSequence) {
		for (OrderSequence sequence : values()) {
			if (sequence.getDescription().equalsIgnoreCase(orderSequence)) {
				return sequence;
			}
		}

		return null;
	}
}