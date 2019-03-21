package com.equinox.index.search.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionUtil {
	public static <T> List<T> intersection(List<T> list1, List<T> list2) {
		List<T> list = new ArrayList<T>();

		for (T t : list2) {
			if (list1.contains(t)) {
				list.add(t);
			}
		}

		return list;
	}

	/*
	 * returns a view (not a new list) of the sourceList for the range based on
	 * page and pageSize
	 * 
	 * @param sourceList
	 * 
	 * @param page
	 * 
	 * @param pageSize
	 * 
	 * @return
	 */
	public static <T> List<T> getPage(List<T> sourceList, int page, int pageSize) {
		if (pageSize <= 0 || page <= 0) {
			throw new IllegalArgumentException("invalid page size: " + pageSize);
		}

		int fromIndex = (page - 1) * pageSize;
		if (sourceList == null || sourceList.size() < fromIndex) {
			return Collections.emptyList();
		}

		// toIndex exclusive
		return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
	}
	
	public static <T> Integer getStartIndex(List<T> parentList, List<T> childList){
		return Collections.indexOfSubList(parentList, childList);
	}
	
}
