package HoneypotAWS;

import java.util.ArrayList;

public class SortingUtil {

	//This function takes last element as pivot, places the pivot element at its
	//correct position in sorted array, and places all smaller (smaller than pivot)
	//to left of pivot and all greater elements to right of pivot
	
	int partition(ArrayList<String> arr, int low, int high) {
		int lastIndex = (arr.get(high).lastIndexOf(',')) + 1;
		int pivot = Integer.parseInt(arr.get(high).substring(lastIndex));
		int i = (low - 1); // index of smaller element
		for (int j = low; j < high; j++) {
			// If current element is smaller than the pivot
			lastIndex = arr.get(j).lastIndexOf(',') + 1;
			if (Integer.parseInt(arr.get(j).substring(lastIndex)) < pivot) {
				i++;
				// swap arr[i] and arr[j]
				String temp = arr.get(i);
				arr.set(i, arr.get(j));
				arr.set(j, temp);
			}
		}
		// swap arr[i+1] and arr[high] (or pivot)
		String temp = arr.get(i + 1);
		arr.set(i + 1, arr.get(high));
		arr.set(high, temp);
		return i + 1;
	}

	ArrayList<String> sort(ArrayList<String> arr, int low, int high) {
		if (low < high) {
			int part = partition(arr, low, high);
			// Recursively sort elements before
			// partition and after partition
			sort(arr, low, part - 1);
			sort(arr, part + 1, high);
		}
		return arr;
	}
}