
import java.util.ArrayList;

/**
 * This class represents a set of letters inputed by the user. This class uses an instance
 * of the Dictionary class to create all possible words that could be created from the 
 * set of letters and then checks if they are in the dictionary.  If they are, it stores them
 * @author George Miller
 * @version September 25, 2014
 *
 */
public class LetterSet {
	char [] letters;
	Dictionary dict;
	ArrayList<String> foundWords;
	ArrayList<Character> unused = new ArrayList<Character>();
	String buildingWord = "";
	int firstLetterIndex = 0;
	int [] shuffleCount;
	int currentIndex = 0;
	
	
	/**
	 * This constructor is used to initialize the unused ArrayList and other variables
	 * @param input
	 * the input sequence of letters
	 * @param filename
	 * the dictionary file
	 */
	public LetterSet(String input, String filename){
		letters = new char[input.length()];
		for (int i = 0; i < input.length(); i++){
			letters[i] = input.charAt(i);
			unused.add(input.charAt(i));
		}
		dict = new Dictionary(filename);
		foundWords = new ArrayList<String>();
		shuffleCount = new int [letters.length];
		for (int i = 0; i < shuffleCount.length; i++){
			shuffleCount[i] = 0;
		}
		
		
		
	}
	
	/**
	 * This method is a backtracking recursive implementation used to create all 
	 * the different possible words from a set of letters and check if they are in the dictionary.
	 * If some are, they will be returned as an ArrayList.  This method uses the shuffleCount array
	 * to keep track of how many times each position has been tried
	 * @return
	 * The ArrayList of found words
	 */
	public ArrayList<String> findWords(){
		
		// If the buildingWord is empty, that means either we are starting the program 
		// or we have finished and are ready to return our completed ArrayList
		if (buildingWord.equals("")){
			if (shuffleCount[0] >= letters.length){
				return foundWords;
			}
		}
		
		
		
		// If unused is empty, that means we have a full word in buildingWord
		if (unused.isEmpty()){
			// This loop will backtrack until we get to a point where we haven't tried 
			// all combinations (using shuffleCount as our guide)
			for (; currentIndex>=0 && shuffleCount[currentIndex] > unused.size(); currentIndex--){
				unused.add(buildingWord.charAt(buildingWord.length()-1));
				buildingWord = buildingWord.substring(0, buildingWord.length()-1);
			}
			// If we get all the way back in the array, that means everything has been tried and we are done 
			if (currentIndex<0)
				return foundWords;
			// If not, we keep trying, but now with the buildingWord put back to the position where there is 
			// undone calculations
			else
				return findWords();
		}
		
		// Add an element to the buildingWord
		buildingWord += getUnusedElement();
		// Update the currentIndex of the buildingWord
		currentIndex = buildingWord.length() -1;
		// And increment shuffleCount at the right index to show that we have tried this combination of letters
		incrementShuffleCount(currentIndex);
		
		// If the currentIndex of shuffle count is more than the size of unused,
		// that means we have exhausted all combinations at the point and we must backtrack
		if (shuffleCount[currentIndex] > unused.size()){
			// Take off the letter we just added, and then backtrack by taking off yet another letter and 
			// adding it to unused
			buildingWord = buildingWord.substring(0, buildingWord.length()-1);
			
			shuffleCount[currentIndex] = 0;
			
			unused.add(buildingWord.charAt(buildingWord.length()-1));
			buildingWord = buildingWord.substring(0, buildingWord.length()-1);
			return findWords();
			
		}
		
//		If you want to see how the ShuffleCount works in real time, un-comment this block 
//		of code and it will print out the shuffleCount ArrayList along with the buildingWord
//		
//		try{
//			Thread.sleep(50);
//		}
//		catch(Exception e){}
//		System.out.println(buildingWord);
//		for (int i = 0; i < shuffleCount.length; i++)
//			System.out.print(shuffleCount[i] + " ,");
//		System.out.println();
		
		
		// If the buildingWord's has 2 or more letters, start checking if it's in the dictionary
		if (buildingWord.length() >= 2){
			
			// Check if the word is in the dictionary, and if it is, add it to foundWords and recurse
			if (dict.isIn(buildingWord, 0, dict.words.size())){
				foundWords.add(buildingWord);
				unused.remove(0);
				return findWords();
				

				
			}
			// If the prefix is in the dictionary, recurse
			else if (dict.isPrefixIn(buildingWord, 0, dict.words.size())){
				unused.remove(0);
			
				return findWords();
				
			}
				
			// If the prefix isn't in the dictionary, backtrack and shuffle the unused and recurse	
			else{
				
				// cut off last letter from buildingWord
				buildingWord = buildingWord.substring(0, buildingWord.length()-1);
				
				// shuffle the unused so that it doesn't test the same thing twice
				shuffleUnused();
				
				return findWords();
			}
			
			
		}
		// If the word is less than two letters long, recurse
		else{
			unused.remove(0);
			return findWords();
		}
		
	}
	
	
	/**
	 * This method returns the next element in the unused ArrayList
	 * @return
	 * The next element in unused
	 */
	public char getUnusedElement(){
		return unused.get(0);
	}
	
	
	/**
	 * This method 'shuffles' the unused ArrayList so 
	 * that a different element appears at index 0
	 */
	public void shuffleUnused(){
		char x = unused.get(0);
		unused.remove(0);
		unused.add(x);
	}
	
	/**
	 * This method increments shuffleCount, essentially showing how many times we have tried a certain index
	 * @param current
	 * The current index
	 */
	public void incrementShuffleCount(int current){
		shuffleCount[current]++;
		current ++;
		// To be sure that every possible word will be checked,
		// we must reset the count of letters that come after the index we are incrementing
		// because it is now a new word, and all the letters that come after can be any of the unused
		for (; current < shuffleCount.length; current++)
			shuffleCount[current] = 0;
		
	}
	
	
	
	
}
