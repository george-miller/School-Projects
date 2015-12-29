
import java.lang.reflect.Array;
import java.util.Random;

/**
 * @author George Miller
 * @version October 19, 2014
 * 
 * This class is a collection of static methods used to sort arrays using different algorithms.
 * This class was made to be used with the TestSort class
 *
 */
public class ArrayTools {
	
	// Initialize the random value generator
	private static Random rand = new Random();
	
	
	/**
	 * This is the helper method for the mergeSortRec method
	 * @param list
	 * The list to be sorted
	 * @return
	 * The sorted list
	 */
	public static <E extends Comparable<E>> E[] mergeSort(E[] list){
		return mergeSortRec(list, 0, list.length-1);
	}
	
	
	/**
	 * This method is a recursive implementation of merge sort
	 * @param list
	 * The list to be sorted
	 * @param start
	 * The start of the subarray
	 * @param end
	 * The end of the subarray
	 * @return
	 * returns the merged array
	 */
	private static <E extends Comparable<E>> E[] mergeSortRec(E[] list, int start, int end){
		// If the subarray is one element long, return it
		if (start == end){
			@SuppressWarnings("unchecked")
			E oneElementList[] = (E[]) Array.newInstance(list.getClass().getComponentType(),
					1);
			oneElementList[0] = list[start];
			return oneElementList;
		}
		int mid = (start+end)/2;
		// split and merge the array
		return merge(mergeSortRec(list,start,mid),mergeSortRec(list,mid+1,end));
		
	}
	
	/**
	 * The helper method for the quicksortRec method
	 * @param list
	 * The list to be sorted
	 */
	public static <E extends Comparable<E>> void quickSort(E[] list){
		quickSortRec(list, 0, list.length-1);
	}
	
	/**
	 * This method is a recursive implementation of quick sort
	 * @param list
	 * The list to be sorted
	 * @param left
	 * The current leftmost index of the subarray
	 * @param right
	 * The current rightmost index of the subarray
	 */
	private static <E extends Comparable<E>> void quickSortRec(E[] list, int left, int right){
		
		// create a pivot and move it to the end of the list
		int pivotIndex = rand.nextInt(right - left) + left;
		swap(list, pivotIndex, right);
		
		// partition the rest of the array
		int newPivotIndex = partition(list, left, right-1, list[right]);
		
		// If the subarrays are more than one element long, recurse the subarrays
		if (newPivotIndex - left > 1) quickSortRec(list, left, newPivotIndex-1);
		
		if (right - newPivotIndex > 1) quickSortRec(list, newPivotIndex + 1, right);
	}
	
	
	
	/**
	 * This method uses selection Sort to sort an array of generic type
	 * @param list
	 * An input array
	 * @return
	 * returns the sorted array
	 */
	public static <E extends Comparable<E>> E[] selectionSort(E[] list){
		// Go through every element in the list, searching the array to find the smallest element
		// then put that element in the front of the list
		for (int i = 0; i < list.length; i++){
			E currentMin = list[i];
			int currentMinIndex = i;
			for (int k = i+1; k< list.length; k++){
				// If a new smallest value is found, store it
				if (currentMin.compareTo(list[k]) > 0){
					currentMin = list[k];
					currentMinIndex = k;
					
				}
			}
			// If the smallest value found after searching the whole array isn't the element we
			// started with, swap it to the front
			if (currentMinIndex != i){
				list[currentMinIndex] = list[i];
				list[i] = currentMin;
			}
		}
		return list;
	}

	
	/**
	 * This method merges two generic lists
	 * @param list1
	 * The first list to be merged
	 * @param list2
	 * The second list to be merged
	 * @return
	 * Returns the merged list
	 */
	private static <E extends Comparable<E>> E[] merge(E[] list1, E[] list2){
		@SuppressWarnings("unchecked")
		E mergedList[] = (E[]) Array.newInstance(list1.getClass().getComponentType(),
				list1.length + list2.length);
		int currentIndex1 = 0;
		int currentIndex2 = 0;
		int currentIndexMerged = 0;
		
		
		while (currentIndex1 < list1.length && currentIndex2 < list2.length){
			// If the next item in list1 is greater than the next item in list2,
			// Add the next item in list2 to to mergedList
			if (list1[currentIndex1].compareTo(list2[currentIndex2]) > 0){
				mergedList[currentIndexMerged] = list2[currentIndex2];
				currentIndexMerged++;
				currentIndex2++;
			}
			// If the next item in list1 is less than the next item in list2,
			// Add the next item in list1 to to mergedList
			else if (list1[currentIndex1].compareTo(list2[currentIndex2]) < 0){
				mergedList[currentIndexMerged] = list1[currentIndex1];
				currentIndexMerged++;
				currentIndex1++;
			}
			// They are the same, so add both to merged
			else{
				mergedList[currentIndexMerged] = list1[currentIndex1];
				currentIndex1++;
				currentIndexMerged++;
				mergedList[currentIndexMerged] = list2[currentIndex2];
				currentIndex2++;
				currentIndexMerged++;
			}
			
		}
		
		// Add the remaining elements in the arrays
		while (currentIndex1 < list1.length){
			mergedList[currentIndexMerged] = list1[currentIndex1];
			currentIndexMerged++;
			currentIndex1++;
		}
		while (currentIndex2 < list2.length){
			mergedList[currentIndexMerged] = list2[currentIndex2];
			currentIndexMerged++;
			currentIndex2++;
		}
		
		return mergedList;
	}
	
	
	/**
	 * This creates a generic copy of a generic list
	 * @param list
	 * The list to be copied
	 * @return
	 * Returns the copied list
	 */
	public static <E> E[] createCopy(E[] list){
		@SuppressWarnings("unchecked")
		E copiedList[] = (E[]) Array.newInstance(list.getClass().getComponentType(),
				list.length);
		// Make every element of the copiedList equal every element in the input list
		for (int i = 0; i < list.length; i ++){
			copiedList[i] = list[i];
		}
		return copiedList;
	}
	
	/**
	 * This method swaps two elements in a generic input array
	 * @param list
	 * The array to have elements swap
	 * @param index1
	 * The index to be swapped with index2
	 * @param index2
	 * The index to be swapped with index1
	 */
	private static <E> void swap(E [] list, int index1, int index2){
		// Create a tmp generic variable and then swaps around the list
		E tmp = list[index1];
		list[index1] = list[index2];
		list[index2] = tmp;
	}
	
	
	/**
	 * This method partitions the given array according to quick sort
	 * @param list
	 * The list to be partitioned
	 * @param left
	 * The left most index of the array to be partitioned
	 * @param right
	 * The right most index of the array to be partitioned
	 * @param pivot
	 * The pivot to compare the elements with
	 * @return
	 */
	private static <E extends Comparable<E>> int partition(E [] list, int left, int right, E pivot){
		
		int pivotLocation = right+1;
		// while the pointers haven't crossed
		while (left <= right){
			// move the left pointer until we find an element greater than the pivot
			while (list[left].compareTo(pivot) < 0)
				left++;
			// move the right pointer until we find an element less than the pivot
			while (right >= left && list[right].compareTo(pivot) >= 0)
				right--;
			// swap the elements if we find two elements that need to be swapped
			if (right > left)
				swap(list, left, right);
		}
		// Put the pivot in it's correct location
		swap(list, left, pivotLocation);
		return left;
	}
}
