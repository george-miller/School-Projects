

/**
 * @author George Miller
 * @version November 3, 2014
 *
 *	This class represents a node with a reference to a previous node 
 * and with data with type E
 *
 * @param <E>
 * The generic type
 */
public class GenericNode <E>{
	
	// private data fields for the data it holds and the previous node
	private E item;
	private GenericNode<E> previous;
	
	// A constructor that creates a node with the given item as it's data
	public GenericNode(E item){
		this.item = item;
	}

	// Various getters and setters
	public GenericNode<E> getPrevious() {
		return previous;
	}

	public void setPrevious(GenericNode<E> previous) {
		this.previous = previous;
	}
	public E getItem() {
		return item;
	}

	public void setItem(E item) {
		this.item = item;
	}
}
