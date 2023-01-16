package cpt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Sort class file
 * @author: D. Gu
 * 
 */ 

public class Sort {
	
	/**
    * A method that merges a list of CovidRecord objects
	*
	* @param - left, the left half of the list
	* @param - right, the right half of the list
	* @param - comparator, compares elements in the list and determines their order
	* 
	* @return a new list of merged data 
    */	
	private static List<CovidRecord> merge(List<CovidRecord> left, List<CovidRecord> right, Comparator<CovidRecord> comparator) {
		// Initialize variables
		List<CovidRecord> result = new ArrayList<>(); 
		int intLeftIndex = 0;
		int intRightIndex = 0; 
		// Proceed as long as the indexes are smaller than the list sizes
		while (intLeftIndex < left.size() && intRightIndex < right.size()) {
			// As long as left index is smaller than right index
			if (comparator.compare(left.get(intLeftIndex), right.get(intRightIndex)) < 0) {
				// Add element from the left of the data
				result.add(left.get(intLeftIndex));
				intLeftIndex++;
			}else {
				// Add element from the right of the data
				result.add(right.get(intRightIndex));
				intRightIndex++;
			}
		}
		// Add remaining elements from left list
		while (intLeftIndex < left.size()) {
			result.add(left.get(intLeftIndex));
			intLeftIndex++;
		}
		// Add remaining elements from right list
		while (intRightIndex < right.size()) {
			result.add(right.get(intRightIndex));
			intRightIndex++;
		}		
		return result;
	}
	
	/** 
    * A method that sorts a list of CovidRecord objects
	*
	* @param - records, the list of covid-19 data
	* @param - comparator, compares elements in the list and determines their order
	* 
	* @return a new list of sorted data 
    */	
	public static List<CovidRecord> mergeSort(List<CovidRecord> records, Comparator<CovidRecord> comparator) {
		int intSize = records.size();
		if (intSize <= 1) {
			return records;
		}
		// Seperate the list into the left and right components
		List<CovidRecord> left = records.subList(0, intSize / 2);
		List<CovidRecord> right = records.subList(intSize / 2, intSize);
		left = mergeSort(left, comparator);
		right = mergeSort(right, comparator);
		records = merge(left, right, comparator);
		return records;
	}
	
	
}
