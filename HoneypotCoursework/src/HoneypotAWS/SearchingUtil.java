package HoneypotAWS;

import java.util.ArrayList;

public class SearchingUtil {

	int binarySearch(ArrayList<String> attackSource, int leftPointer, int rightPointer, long source) {
		int midPointer = leftPointer + (rightPointer - leftPointer) / 2;
		//Long.parseLong(attackSource.get(midPointer).substring(0, attackSource.get(midPointer).lastIndexOf(',')))
		//This section firsts gets the CSV at the midpoint then does the substring from the first character, the
		//next section gets the value at the midpoint again and returns the last index of "," which then isolates
		//the Long number so "Long.parseLong()" turns it into a long variable type.
		
		//Checking if reached the end.
		if (rightPointer >= leftPointer) {
			if (Long.parseLong(attackSource.get(midPointer).substring(0, attackSource.get(midPointer).lastIndexOf(','))) == source) {
				// If the element is present at the middle.
				return midPointer;
			}else if (Long.parseLong(attackSource.get(midPointer).substring(0, attackSource.get(midPointer).lastIndexOf(','))) > source) {
				// If element is smaller than mid, then it can only be present in left subarray.
				return binarySearch(attackSource, leftPointer, midPointer - 1, source);
			}
			// If element is bigger than mid, then it can only be present in right subarray.
			return binarySearch(attackSource, midPointer + 1, rightPointer, source);
		}
		// We reach here when element is not present in array, so we return where it should be.
		return midPointer;
	}
}
