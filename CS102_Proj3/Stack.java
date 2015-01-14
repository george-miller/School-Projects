

/**
 * @author George Miller
 * @version November 2, 2014
 *
 *	This class implements a generic stack where
 * the rule is first in first out
 * @param <E>
 */
public class Stack <E>{
	private GenericNode<E> top;
	
	/**
	 * Initializes an empty stack
	 * 
	 */
	public Stack(){
		top = null;
	}
	
	/**
	 * This method adds an item onto the top of the stack
	 * @param item
	 * The item to be added, if it's null no item is added
	 */
	public void push(E item){
		if (item != null){
			// Create a node and set the arrows appropriately
			GenericNode<E> node = new GenericNode<E>(item);
			node.setPrevious(top);
			top = node;
		}
		
		
	}
	
	
	/**
	 * Pops an item from the top of the stack, returning it and removing it
	 * @return
	 * The item that was popped, if the stack is empty return null
	 */
	public E pop(){
		// If the stack is empty, return null
		if (top != null){
			// store the item to be returned
			E e = top.getItem();
			// Move arrows appropriately
			top= top.getPrevious();
			// return the value
			return e;
		}
		else 
			return null;
	}
	
	
	/**
	 * This method returns the value at the top of the stack
	 * @return
	 * The value at the top of the stack, if the stack is empty return null
	 */
	public E peek(){
		// if the stack isn't empty, return the item
		if (top != null){
			return top.getItem();
		}
		else
			return null;
	}
	
	
	
	public String toString(){
		// Iterate through the stack using a temporary node from top to bottom, 
		// then reverse that to create a string object with the top of the stack at the end
		GenericNode<E> tmp;
		tmp = top;
		String reverseOutput = "";
		while (tmp != null){
			reverseOutput += tmp.getItem();
			reverseOutput += " ";
			tmp = tmp.getPrevious();
		}
		String output = "";
		for (int i = reverseOutput.length() - 1; i >= 0; i--){
			output += reverseOutput.charAt(i);
			
		}
		return output;
	}
	
	// Getter and setter for the private top data field

	public GenericNode<E> getTop() {
		return top;
	}

	public void setTop(GenericNode<E> top) {
		this.top = top;
	}

}
