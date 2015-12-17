

import java.util.Random;



/**
 * This class tests multiple sort methods and sees which ones are the fastest/slowest using the ArrayTools class
 * @author George Miller
 * @version October 12, 2014
 *
 */
public class MainTestSort {
	

	/**
	 * The main method used to test sort methods
	 */
	public static void main(String [] args){
		// Create variables used for timing each sort
		long start;
		long end;
		
		// Create an array and populate it with random values
		Integer [] originalArray = new Integer[10000]; // This value can be changed to test different lengths of lists
		Random rand = new Random();
		for (int i = 0 ; i < originalArray.length; i++){
			originalArray[i] = rand.nextInt();
		}
		
		// Make copies to be sorted
		Integer [] array1 = ArrayTools.createCopy(originalArray);
		Integer [] array2 = ArrayTools.createCopy(originalArray);
		Integer [] array3 = ArrayTools.createCopy(originalArray);

		// For each sort: start a timer, sort the array, end the timer, and then print the elapsed time to the console
		start = System.nanoTime();
		array1 = ArrayTools.selectionSort(array1);
		end = System.nanoTime();
		System.out.println("Selection sort took " + (end-start)/1000 + " miliseconds to complete");
		
		start = System.nanoTime();
		ArrayTools.quickSort(array2);
		end = System.nanoTime();
		System.out.println("Quick sort took " + (end-start)/1000 + " miliseconds to complete");
		
		
		start = System.nanoTime();
		array3 = ArrayTools.mergeSort(array3);
		end = System.nanoTime();
		System.out.println("Merge sort took " + (end-start)/1000 + " miliseconds to complete");
		


	}
}
