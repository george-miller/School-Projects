
import java.util.ArrayList;


/**
 * The class represent a dictionary of words. 
 * It provides a way of searching through the dictionary.
 * It also can produce a dictionary in which the words are limited
 * to a particular length. 
 * 
 * @author Joanna Klukowska
 *
 */
public class Dictionary {
	//actual storage for the words
	private AVLTree<String> words;
	
	
	/**
	 * Creates an empty Dictionary object (no words).
	 */
	public Dictionary ( ) {
		words = new AVLTree < String > () ;
	}
	
	/**
	 * Creates a Dictionary object containing all words from the 
	 * listOfWords passed as a parameter.
	 * 
	 * @param listOfWords the list of words to be stored in the newly created 
	 * Dictionary object
	 */
	public Dictionary ( ArrayList < String > listOfWords ) {
		words = new AVLTree<String>(listOfWords.get(0));
		for (int i = 1; i < listOfWords.size(); i++){
			words.add(listOfWords.get(i));
		}
	}
	
	
	
	 
	/**
	 * Performs (binary) search in this Dictionary object for a given word.
	 * @param word  the word to look for in this Dictionary object. 
	 * @return true if the word is in this Dictionary object, false otherwise
	 */
	public boolean isWordInDictionary ( String word ) {
		return words.contains(word);
	}
	
	
	
	/**
	 * Performs (binary) search in this Dictionary object for a given prefix.
	 * @param prefix  the prefix to look for in this Dictionary object. 
	 * @return true if at least one word with the specified prefix exists 
	 * in this Dictionary object, false otherwise
	 */
	public boolean isPrefixInDictionary (String prefix ) {
		return isPrefixInDictionaryRecursive (prefix, words.getRoot());
	}

	/**
	 * The actual method providing recursive implementation of the binary search
	 * for the prefix. 
	 * @param prefix the prefix to look for in this Dictionary object.
	 * @param begin start of the range for the current iteration
	 * @param end end of the range for the current iteration
	 * @return true if at least one word with the specified prefix exists 
	 * in this Dictionary object, false otherwise
	 */
	private boolean isPrefixInDictionaryRecursive(String prefix, Node<String> n) {
		// If we reached a null value, we have gone 
		// off the tree and the node isn't in the tree
		if (n == null)
			return false;
		// If the data is greater than the node we are on, traverse right
		int prefixLength;
		if (prefix.length() > n.getData().length()){
			prefixLength = n.getData().length();
		}
		else prefixLength = prefix.length();
		
		if (prefix.compareTo(n.getData().substring(0, prefixLength)) > 0){
			return isPrefixInDictionaryRecursive(prefix, n.getRight());
		}
		// If data is less than the node we are on, traverse left
		else if (prefix.compareTo(n.getData().substring(0, prefixLength)) < 0){
			return isPrefixInDictionaryRecursive(prefix, n.getLeft());
		}
		// If the compareTo returns 0, then we have found it
		else
			return true;
	}
	
	
	
}
