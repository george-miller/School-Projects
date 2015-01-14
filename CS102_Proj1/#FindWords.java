
import java.util.Arrays;
import java.util.Scanner;


/**
 * This class is used to demonstrate the LetterSet and Dictionary Classes
 * @author George Miller
 * @version September 25, 2014
 *
 */
public class FindWords {
	/**
	 * This method is the main method used for taking advantage of the method implemented in the 
	 * LetterSet and Dictionary classes
	 * @param args
	 * The command line arguments to be used as the file name of the dictionary text file
	 * NOTE: this file must be a sorted list of words separated by \n characters
	 * @throws IllegalArgumentException
	 * This exception is thrown if the user enters too little or too many letters, or enters things that aren't letters
	 */
	public static void main(String[] args) throws IllegalArgumentException{
		
		// This block of code prompts the user and takes the input
		Scanner in = new Scanner(System.in);
		System.out.print("Enter the list of letters to be "
				+ "combined, recombined, and searched in the dictionary \n(input must be only letters and be between 2 and 10 letters long): ");
		String unfilteredInput = in.next();
		String filteredInput = "";
		in.close();
		
		// This checks whether the user has inputed the correct number of letters.  
		// If they haven't, it throws an exception
		if (unfilteredInput.length() > 10 || unfilteredInput.length() < 2) {
			throw new IllegalArgumentException("Input must be between 2 and 10 letters long");
		}
		
		// This loop checks whether the characters that were inputed are valid
		// including checking if they are letters if they aren't, it throws an exception
		// It also checks if the letters are lowercase, and if they aren't, it changes them to lowercase
		for (int i = 0; i < unfilteredInput.length(); i ++){
			if (!Character.isLetter(unfilteredInput.charAt(i))){
				throw new IllegalArgumentException("Input must be all letters");
			}
			if (Character.isUpperCase(unfilteredInput.charAt(i)))
				filteredInput += Character.toLowerCase(unfilteredInput.charAt(i));
			else
				filteredInput += unfilteredInput.charAt(i);
				
		}
		
		
		// Create a LetterSet and find the words
		LetterSet set = new LetterSet(filteredInput, args[0]);
		set.findWords();

		
		
		// Move the foundWords ArrayList to a new foundWords array
		String [] foundWords = new String[set.foundWords.size()];

		for (int i = 0; i < set.foundWords.size(); i++){
			foundWords[i] = set.foundWords.get(i);
		}
		// Sort the foundWords array
		Arrays.sort(foundWords);
		
		// Put the sorted foundWords back into the ArrayList
		set.foundWords.clear();
		for (int i = 0; i < foundWords.length; i++)
			set.foundWords.add(foundWords[i]);
		
		// This for loop removes the duplicates that exist in the foundWords ArrayList
		for (int i = 1; i < set.foundWords.size(); i++){
			String current = set.foundWords.get(i-1);
			String next = set.foundWords.get(i);
			if (current.equals(next)){
				set.foundWords.remove(i);
				i--;
			}
		}
		
		// Print the completed foundWords ArrayList
		for (int i = 0; i < set.foundWords.size(); i ++){
			System.out.println(set.foundWords.get(i));
		}

	}
}
