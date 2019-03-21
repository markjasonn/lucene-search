package com.equinox.search.constants;

import java.util.List;

import org.apache.lucene.queryParser.QueryParser;

import com.equinox.search.enums.MetadataFilterOperator;

public class LuceneSearchConstants {
	public static final String DEFAULT_HIGHLIGHT_PRE_TAG = "<B>";
	public static final String DEFAULT_HIGHLIGHT_POST_TAG = "</B>";

	public static String generateLuceneTextScript(String fieldName, String fieldValue, MetadataFilterOperator filterOperator) {
		StringBuilder resultBuilder = new StringBuilder();
		String finalFieldValue = parseFilterValue(fieldValue.trim());

		switch (filterOperator) {
		case EQUALS:
			resultBuilder.append(fieldName);
			resultBuilder.append(":");
			resultBuilder.append(finalFieldValue);
			break;
		case DOES_NOT_EQUAL:
			resultBuilder.append("(*:* -");
			resultBuilder.append(fieldName);
			resultBuilder.append(":");
			resultBuilder.append(finalFieldValue);
			resultBuilder.append(")");
			break;
		case CONTAINS:
			resultBuilder.append(fieldName + ":");
			resultBuilder.append(parseFilterValue(fieldValue.trim(), filterOperator));
			break;
		case DOES_NOT_CONTAIN:
			resultBuilder.append("(*:* -");
			resultBuilder.append(fieldName + ":");
			resultBuilder.append(parseFilterValue(fieldValue.trim(), filterOperator));
			resultBuilder.append(")");
			break;
		case IS_SET:
			resultBuilder.append(fieldName);
			resultBuilder.append(":*");
			break;
		case IS_NOT_SET:
			resultBuilder.append("(");
			resultBuilder.append("*:* ");
			resultBuilder.append("-");
			resultBuilder.append(fieldName);
			resultBuilder.append(":*)");
			break;
		case BEGINS_WITH:
			resultBuilder.append(fieldName);
			resultBuilder.append(":");
			resultBuilder.append(parseFilterValue(fieldValue.trim(), filterOperator));
			break;
		case ENDS_WITH:
			resultBuilder.append(fieldName);
			resultBuilder.append(":");
			resultBuilder.append(parseFilterValue(fieldValue.trim(), filterOperator));
			break;
		}

		return resultBuilder.toString();
	}

	public static String generateLuceneMultipleChoiceScript(String fieldName, List<String> fieldValue, MetadataFilterOperator filterOperator) {
		StringBuilder resultBuilder = new StringBuilder();

		switch (filterOperator) {
		case IS_ALL_OF_THESE:
			// resultBuilder.append("("); EDDAD-48055 modified by eric
			for (String value : fieldValue) {
				resultBuilder.append(fieldName);
				resultBuilder.append(":");
				resultBuilder.append(parseFilterValue(value.replace(" ", "_").toLowerCase().trim()));
				if (!(fieldValue.indexOf(value) == (fieldValue.size() - 1)))
					resultBuilder.append(" ");
			}
			// resultBuilder.append(")"); EDDAD-48055 modified by eric
			break;
		case IS_ANY_OF_THESE:
			resultBuilder.append("(");
			for (String value : fieldValue) {
				resultBuilder.append(fieldName);
				resultBuilder.append(":");
				resultBuilder.append(parseFilterValue(value.replace(" ", "_").toLowerCase().trim()));
				if (!(fieldValue.indexOf(value) == (fieldValue.size() - 1)))
					resultBuilder.append(" OR ");
			}
			resultBuilder.append(")");
			break;
		case IS_NONE_OF_THESE:
			resultBuilder.append("(*:* ");

			for (String value : fieldValue) {
				resultBuilder.append("-");
				resultBuilder.append(fieldName);
				resultBuilder.append(":");
				resultBuilder.append(parseFilterValue(value.replace(" ", "_").toLowerCase().trim()));
				if (!(fieldValue.indexOf(value) == (fieldValue.size() - 1)))
					resultBuilder.append(" AND ");
			}
			resultBuilder.append(")");
			break;
		case IS_SET:
			resultBuilder.append(fieldName);
			resultBuilder.append(":*");
			break;
		case IS_NOT_SET:
			resultBuilder.append("(");
			resultBuilder.append("*:* ");
			resultBuilder.append("-");
			resultBuilder.append(fieldName);
			resultBuilder.append(":*)");
			break;
		}

		return resultBuilder.toString();
	}

	public static String generateLuceneDateScript(String fieldName, String fieldValue1, String fieldValue2, MetadataFilterOperator filterOperator) {
		StringBuilder resultBuilder = new StringBuilder();
		// DateTime date = DateTime.parse(fieldValue1,
		// DateTimeFormat.forPattern("yyyy-MM-dd"));
		// resultBuilder.append(QueryParser.escape(NumericUtils.longToPrefixCoded(date.getMillis(),
		// 4)));

		switch (filterOperator) {
		case EQUALS:
			resultBuilder.append(fieldName);
			resultBuilder.append(":[");
			resultBuilder.append(fieldValue1);
			resultBuilder.append(" TO ");
			resultBuilder.append(fieldValue1);
			resultBuilder.append("]");
			break;
		case DOES_NOT_EQUAL:
			resultBuilder.append("(*:* -");
			resultBuilder.append(fieldName);
			resultBuilder.append(":[");
			resultBuilder.append(fieldValue1);
			resultBuilder.append(" TO ");
			resultBuilder.append(fieldValue1);
			resultBuilder.append("]");
			resultBuilder.append(")");
			break;
		case IS_SET:
			resultBuilder.append(fieldName);
			resultBuilder.append(":*");
			break;
		case IS_NOT_SET:
			resultBuilder.append("(");
			resultBuilder.append("*:* ");
			resultBuilder.append("-");
			resultBuilder.append(fieldName);
			resultBuilder.append(":*)");
			break;
		case IS_BEFORE:
			resultBuilder.append(fieldName);
			resultBuilder.append(":{");
			resultBuilder.append("0");
			resultBuilder.append(" TO ");
			resultBuilder.append(fieldValue1);
			resultBuilder.append("} ");
			break;
		case IS_AFTER:
			resultBuilder.append(fieldName);
			resultBuilder.append(":{");
			resultBuilder.append(fieldValue1);
			resultBuilder.append(" TO ");
			resultBuilder.append(Long.MAX_VALUE);
			resultBuilder.append("}");
			break;
		case IS_ON_OR_BEFORE:
			resultBuilder.append(fieldName);
			resultBuilder.append(":[");
			resultBuilder.append("0");
			resultBuilder.append(" TO ");
			resultBuilder.append(fieldValue1);
			resultBuilder.append("] ");
			break;
		case IS_ON_OR_AFTER:
			resultBuilder.append(fieldName);
			resultBuilder.append(":[");
			resultBuilder.append(fieldValue1);
			resultBuilder.append(" TO ");
			resultBuilder.append(Long.MAX_VALUE);
			resultBuilder.append("]");
			break;
		case IS_WITHIN:
			resultBuilder.append(fieldName);
			resultBuilder.append(":[");
			resultBuilder.append(fieldValue1);
			resultBuilder.append(" TO ");
			resultBuilder.append(fieldValue2);
			resultBuilder.append("] ");
			break;
		case IS_BETWEEN:
			resultBuilder.append(fieldName);
			resultBuilder.append(":{");
			resultBuilder.append(fieldValue1);
			resultBuilder.append(" TO ");
			resultBuilder.append(fieldValue2);
			resultBuilder.append("} ");
			break;
		}

		return resultBuilder.toString();

	}

	private static String parseFilterValue(String fieldValue) {
		String output = QueryParser.escape(fieldValue.toLowerCase());

		if (output.contains(" ")) {
			return "\"" + output + "\"";
		} else {
			return output;
		}
	}

	/**
	 * Another layer for filter value validation, especially for adding special
	 * characters when building search script.
	 * 
	 * @param fieldValue
	 * @param filterOperator
	 * @return
	 */
	private static String parseFilterValue(String fieldValue, MetadataFilterOperator filterOperator) {
		String output = QueryParser.escape(fieldValue.toLowerCase());

		switch (filterOperator) {
		case CONTAINS:
		case DOES_NOT_CONTAIN:
			return "*" + output.replace(" ", "\\ ") + "*";
		case ENDS_WITH:
			return "*" + output.replace(" ", "\\ ");
		case BEGINS_WITH:
			return output.replace(" ", "\\ ") + "*";
		default:
			return output.replace(" ", "\\ ");
		}
	}
}
