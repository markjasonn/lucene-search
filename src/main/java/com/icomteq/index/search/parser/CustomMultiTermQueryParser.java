package com.icomteq.index.search.parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.util.Version;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class CustomMultiTermQueryParser extends QueryParser {

	public CustomMultiTermQueryParser(Version matchVersion, String f, Analyzer a) {
		super(matchVersion, f, a);
	}

	@Override
	protected org.apache.lucene.search.Query getRangeQuery(String field, String part1, String part2, boolean inclusive) {
		if (this.getLowercaseExpandedTerms()) {
			part1 = (part1 == null) ? null : part1.toLowerCase();
			part2 = (part2 == null) ? null : part2.toLowerCase();
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		df.setLenient(true);
		DateTools.Resolution resolution = getDateResolution(field);
		try {
			Date d1 = df.parse(part1);
			if (resolution == null) {
				// part1 = DateField.dateToString(d1);
				part1 = Long.valueOf(DateTime.parse(part1, DateTimeFormat.forPattern("yyyy-MM-dd")).getMillis()).toString();
			} else {
				// part1 = DateTools.dateToString(d1, resolution);
				DateTime fromDate = new DateTime(d1);
				part1 = Long.valueOf(fromDate.getMillis()).toString();
			}
		} catch (Exception e) {
		}

		try {
			Date d2 = df.parse(part2);
			// System.out.println("inclusive? > " + inclusive);
			if (inclusive) {
				Calendar cal = Calendar.getInstance(this.getLocale());
				cal.setTime(d2);
				cal.set(11, 23);
				cal.set(12, 59);
				cal.set(13, 59);
				cal.set(14, 999);
				d2 = cal.getTime(); // dateTime.toDate();
			}
			if (resolution == null) {
				// part2 = DateField.dateToString(d2);
				part2 = Long.valueOf(DateTime.parse(part2, DateTimeFormat.forPattern("yyyy-MM-dd")).getMillis()).toString();
			} else {
				// part2 = DateTools.dateToString(d2, resolution);
				DateTime fromDate = new DateTime(d2);
				part2 = Long.valueOf(fromDate.getMillis()).toString();
			}
		} catch (Exception e) {
		}

		// System.out.println("part1 > " + part1);
		// System.out.println("part2 > " + part2);
		// return newRangeQuery(field,
		// QueryParser.escape(NumericUtils.longToPrefixCoded(Long.valueOf(part1))),
		// QueryParser.escape(NumericUtils.longToPrefixCoded(Long.valueOf(part2))),
		// inclusive);
		if (part1 == null && part2 == null) {
			return newRangeQuery(field, part1, part2, inclusive);
		} else {
			return newNumericRangeQuery(field, Long.valueOf(part1), Long.valueOf(part2), inclusive);
		}
	}

	protected org.apache.lucene.search.Query newNumericRangeQuery(String field, Long part1, Long part2, boolean inclusive) {
		NumericRangeQuery<Long> query = NumericRangeQuery.newLongRange(field, part1, part2, inclusive, inclusive);
		return query;
	}
}
