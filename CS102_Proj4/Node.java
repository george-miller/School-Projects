

/**
 * @author George Miller
 * @version Nov 25, 2014
 * 
 * This inner class is an implementation of a generic node.  This specific class is used best
 * with a binary tree, or in this case, with an AVL tree
 *
 * @param <E>
 * The type of the node
 */
public class Node <E extends Comparable<E>>{
	private E data;
	private Node <E> right;
	private Node <E> left;
	private int height;
	
	/**
	 * This constructor creates a node with specific data
	 * @param data
	 * The data to be put into the node object
	 */
	public Node(E data){
		this.data = data;
	}
	
	// Various getters and setters used to access the private data fields

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	public Node<E> getRight() {
		return right;
	}

	public void setRight(Node<E> right) {
		this.right = right;
	}

	public Node<E> getLeft() {
		return left;
	}

	public void setLeft(Node<E> left) {
		this.left = left;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
