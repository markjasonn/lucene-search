package com.icomteq.index.search.behavior;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.icomteq.index.search.models.DocumentListFilterRow;
import com.icomteq.library.persistence.cases.model.CaseFieldCachedEntity;
import com.icomteq.search.constants.LuceneSearchConstants;
import com.icomteq.search.enums.BooleanOperator;
import com.icomteq.search.enums.FieldType;
import com.icomteq.search.enums.MetadataFilterOperator;

public class LuceneQueryGenerator {

	private LuceneQueryGenerator() {
		// TODO: Do not instantiate! Utility Class!
	}

	public static String generateLuceneMetadataScript(Long folderid) {
		StringBuilder builder = new StringBuilder();
		builder.append("folderid:" + folderid);
		return builder.toString();
	}

	public static String generateLuceneMetadataScript(List<Long> folders, List<DocumentListFilterRow> filterConditions) {
		StringBuilder builder = new StringBuilder();
		builder.append("(");

		for (Long folderid : folders) {
			builder.append(" folderid:" + folderid);

			if (!(folders.indexOf(folderid) == (folders.size() - 1))) {
				builder.append(" OR ");
			}
		}

		builder.append(") ");
		builder.append(generateLuceneMetadataScript(filterConditions));
		return builder.toString();
	}

	public static String generateLuceneMetadataScript(Long folderObjectId, List<DocumentListFilterRow> filterConditions) {
		StringBuilder builder = new StringBuilder();
		builder.append(generateLuceneMetadataScript(filterConditions));
		return builder.toString();
	}

	public static String generateLuceneMetadataScript(List<DocumentListFilterRow> filterConditions) {

		StringBuilder builder = new StringBuilder();
		String output = "";
		int openParenthesisCount = 0;
		int closeParenthesisCount = 0;

		if (filterConditions != null && filterConditions.size() > 0) {
			for (DocumentListFilterRow row : filterConditions) {
				if (row.getCaseField().getFieldObjectID() == 0) {
					continue;
				} else {
					if (row.getOpenGroup() != null && !"".equalsIgnoreCase(row.getOpenGroup().trim())) {
						builder.append(row.getOpenGroup());
					}

					CaseFieldCachedEntity field = row.getCaseField();
					FieldType fieldType = FieldType.getFieldTypeByFieldTypeCodeProperty(field.getFieldTypeCode());
					String queryCondition = getQueryCondition(fieldType, row);
					builder.append(queryCondition);

					if (row.getCloseGroup() != null && !"".equalsIgnoreCase(row.getCloseGroup().trim())) {
						builder.append(row.getCloseGroup());
					}

					if (filterConditions.indexOf(row) != (filterConditions.size() - 1)) {
						builder.append(" " + generateBooleanOperator(row.getBooleanOperator()) + " ");
					}
				}
				openParenthesisCount += ("".equalsIgnoreCase(row.getOpenGroup()) ? 0 : StringUtils.countMatches(row.getOpenGroup(), "("));
				closeParenthesisCount += ("".equalsIgnoreCase(row.getCloseGroup()) ? 0 : StringUtils.countMatches(row.getCloseGroup(), ")"));
			}

			output = builder.toString();

			if (openParenthesisCount == 0 && closeParenthesisCount == 0 && output != null && output.contains("&&") && output.contains("||")) {
				String[] chunks = output.toString().split("[\\|][\\|]");

				if (chunks.length > 0) {
					builder = new StringBuilder();
					for (int x = 0; x < chunks.length; x++) {
						String chunk = chunks[x];
						builder.append("(");
						builder.append(chunk.trim());
						builder.append(")");

						if (!(x == (chunks.length - 1))) {
							builder.append(" OR ");
						}
					}
				}
			}
		} else {
			builder.append("");
		}

		return builder.toString();
	}

	private static String getQueryCondition(FieldType fieldType, DocumentListFilterRow row) {

		String result = "";
		String fieldName = "CaseDocument".equalsIgnoreCase(row.getCaseField().getAttachedToObjectTypeTable()) ? row.getCaseField().getFieldName()
				.toLowerCase() : row.getCaseField().getFieldObjectID().toString();
		String fieldValue = "";
		String dateFieldValue1 = "";
		String dateFieldValue2 = "";

		switch (fieldType) {
		case STRING:
			fieldValue = row.getFilterValue();
			result = LuceneSearchConstants.generateLuceneTextScript(fieldName.toLowerCase(), fieldValue, MetadataFilterOperator
					.getOperatorByString(row.getFilterOperator()));
			break;
		case COMBINED_FIELD:
			fieldValue = row.getFilterValue();
			result = LuceneSearchConstants.generateLuceneTextScript(fieldName.toLowerCase(), fieldValue, MetadataFilterOperator
					.getOperatorByString(row.getFilterOperator()));
			break;
		case BOOLEAN:
			fieldValue = row.getFilterValue();
			result = LuceneSearchConstants.generateLuceneTextScript(fieldName.toLowerCase(), fieldValue.toLowerCase(), MetadataFilterOperator
					.getOperatorByString(row.getFilterOperator()));
			break;
		case CUSTODIAN:
			// Custodian Field
			result = LuceneSearchConstants.generateLuceneMultipleChoiceScript("custodian", row.getSelectedOptions(), MetadataFilterOperator
					.getOperatorByString(row.getFilterOperator()));
			break;
		case SINGLE_CHOICE:
		case MULTIPLE_CHOICE:
			result = LuceneSearchConstants.generateLuceneMultipleChoiceScript(fieldName.toLowerCase(), row.getSelectedOptions(),
					MetadataFilterOperator.getOperatorByString(row.getFilterOperator()));
			break;
		case DATE:
			DateTime fromDate = new DateTime(row.getDateTimeFromType());
			DateTime toDate = new DateTime(row.getDateTimeToType());

			if (fromDate != null) {
				dateFieldValue1 = fromDate.toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
			}

			if (toDate != null) {
				dateFieldValue2 = toDate.toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
			}

			if (!"".equalsIgnoreCase(dateFieldValue2.trim())) {
				result = LuceneSearchConstants.generateLuceneDateScript(fieldName, dateFieldValue1, dateFieldValue2, MetadataFilterOperator
						.getOperatorByString(row.getFilterOperator()));
			} else {
				result = LuceneSearchConstants.generateLuceneDateScript(fieldName, dateFieldValue1, dateFieldValue1, MetadataFilterOperator
						.getOperatorByString(row.getFilterOperator()));
			}
			break;
		}

		return result;
	}

	private static String generateBooleanOperator(BooleanOperator operator) {
		String result = "&&";
		if (operator == null || "OR".equalsIgnoreCase(operator.toString().trim()) || "".equalsIgnoreCase(operator.toString().trim())) {
			result = "||";
		}
		return result;
	}
}
