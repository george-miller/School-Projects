
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents a dictionary and implements methods to check if a certain word 
 * is in the dictionary
 * 
 * @author George Miller
 * @version 9/22/14
 *
 */
public class Dictionary {
	
	File dict;
	Scanner dictScanner;
	ArrayList<String> words;
	int start, end, middle;
	
	
	
	/**
	 * This constructor serves to initialize everything, including reading from the 
	 * file and creating an arraylist with all of the words in the dictionary in it
	 * @param fileName
	 * The file containing the dictionary
	 * NOTE: This file must be an alphabetized list of words
	 */
	public Dictionary(String fileName){
		// Try to initialize the dictionary file, catch the exception if the filename is null and exit the program
		try{
		dict = new File(fileName);
		}
		catch (NullPointerException e){
			System.err.println("File " + fileName + " is null");
			System.exit(1);
		}
		// initialize words
		words = new ArrayList<String>();
		// Initialize the scanner, catch the exception if the file doesn't exist and exit the program
		try {
			dictScanner = new Scanner(dict);
		} 
		catch (FileNotFoundException e) {
			System.err.println("File " + fileName + " not found");
			System.exit(1);
		}
		// add all items to the ArrayList
		while (dictScanner.hasNext()){
			words.add(dictScanner.next());
		}
		
	}
	
	/**
	 * A recursive binary search method to check whether a word is in the dictionary
	 * @param word
	 * The word to be checked
	 * @return
	 * True if the word is in the dictionary, false if it is not
	 */
	public boolean isIn(String word, int start, int end){
		
		middle = ((start+end)/2);
		
		// If the word comes alphabetically before the middle word in the dictionary,
		// change perspective to eliminate the words it cannot be
		if (words.get(middle).compareTo(word) > 0){
			// if start == middle, that means we are down to a singular word
			// if this singular word doesn't match the one we are trying to compare it to, 
			// that means the word isn't in the dictionary
			if (start == middle)
				return false;
			return isIn(word, start, middle);
		}
		// If the word comes alphabetically after the middle word in the dictionary, 
		// change perspective to eliminate the words it cannot be
		else if(words.get(middle).compareTo(word) < 0){
			// if start == middle, that means we are down to a singular word
			// if this word doesn't match the one we are trying to compare it to, 
			// that means the word isn't in the dictionary
			if (start == middle)
				return false;
			return isIn(word, middle, end);
		}
		// If the middle word is the word we are searching for return true
		else if (words.get(middle).equals(word))
			return true;
		return false;
	}
	
	/**
	 * This recursive binary search method checks whether the prefix matches a prefix in the dictionary
	 * @param prefix
	 * The prefix to be checked
	 * @return
	 * False if it isn't true if it is
	 */
	public boolean isPrefixIn(String prefix, int start, int end){
		
		middle = ((start+end)/2);
		
		// If the word comes alphabetically before the middle word in the dictionary,
		// change perspective to eliminate the words it cannot be
		
		// We must use substrings so that the compareTo method doesn't return -1 when comparing
		// two items like "arg" and "argi".  In that instance, we want it to return 0
		
		// Another problem arises if the word at the 'middle' position is longer than the prefix, we cannot try
		// to cut letters off the word because it will result in an index out of bound exception

		// NOTE: I realize I used a syntax we didn't explicitly learn in class, but it would've resulted in a 
		//       huge work-around if I didn't use this syntax
		if ((prefix.length() > words.get(middle).length()? words.get(middle).compareTo(prefix)
				: words.get(middle).substring(0, prefix.length()).compareTo(prefix)) > 0){
			// if start == middle, that means we are down to a singular word
			// if this word doesn't match the one we are trying to compare it to, 
			// that means the prefix isn't in the dictionary
			if (start == middle)
				return false;
			return isPrefixIn(prefix, start, middle);
		}
		// If the word comes alphabetically after the middle word in the dictionary, 
		// change perspective to eliminate the words it cannot be
		
		// We must use substrings so that the compareTo method doesn't return -1 when comparing
		// two items like "arg" and "argi".  In that instance, we want it to return 0
		
		// Another problem arises if the word at the 'middle' position is longer than the prefix, we cannot try 
		// to cut letters off the word because it will result in an index out of bound exception
		
		// NOTE: I realize I used a syntax we didn't explicitly learn in class, but it would've resulted in a 
		//       huge work-around if I didn't use this syntax
		else if((prefix.length() > words.get(middle).length()? words.get(middle).compareTo(prefix)
				: words.get(middle).substring(0, prefix.length()).compareTo(prefix)) < 0){
			// if start == middle, that means we are down to a singular word
			// if this word doesn't match the one we are trying to compare it to, 
			// that means the prefix isn't in the dictionary
			if (start == middle)
				return false;
			return isPrefixIn(prefix, middle, end);
		}
		// If the middle word is the word we are searching for return true
		else if (words.get(middle).substring(0, prefix.length()).equals(prefix))
			return true;
		
		return true;
	}
}
