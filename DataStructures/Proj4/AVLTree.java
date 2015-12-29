package Project4;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class implements an AVL binary search tree.  This tree is 
 * self balancing and mostly recursively implemented
 * 
 * @author George Miller
 *
 * @param <E>
 * The type that the nodes will be in the tree
 */
public class AVLTree <E extends Comparable<E>>{
	private Node <E> root;
	
	// Getter for the private data field root
	public Node<E> getRoot() {
		return root;
	}

	

	public AVLTree(){
		
	}
	
	/**
	 * This method serves as a constructor to initialize a tree starting with a given root
	 * @param root
	 * The data to become the root
	 */
	public AVLTree(E data){
		Node<E> root = new Node<E>(data);
		this.root = root;
	}
	
	/**
	 * This method is a helper method for the recursive method that
	 * checks whether the data pass as a parameter is contained 
	 * as a node within the tree
	 * @param data
	 * the data we are looking for
	 * @return
	 * True if we find the data, false if we do not
	 */
	public boolean contains(E data){
		return recContains(root, data);
	}
	
	/**
	 * This method checks whether the data passed as a parameter is 
	 * contained in a node within the tree using a recursive implementation
	 * @param n
	 * The node we are currently on
	 * @param data
	 * The data we are looking for
	 * @return
	 * True if we find the data, false if we do not
	 */
	public boolean recContains(Node<E> n, E data){
		// If we reached a null value, we have gone 
		// off the tree and the node isn't in the tree
		if (n == null)
			return false;
		// If the data is greater than the node we are on, traverse right
		if (data.compareTo(n.getData()) > 0){
			return recContains(n.getRight(), data);
		}
		// If data is less than the node we are on, traverse left
		else if (data.compareTo(n.getData()) < 0){
			return recContains(n.getLeft(), data);
		}
		// If the compareTo returns 0, then we have found it
		else
			return true;
		
	}
	
	
	/**
	 * This method serves as a helper method for the recursive method used to add a node to the tree
	 * @param item
	 * The data we want to add as a node
	 */
	public void add(E item){
		root = recAdd(root, item);
	}
	
	/**
	 * This method is a recursive implementation that adds a specific node to the binary tree
	 * @param n
	 * The node we are currently on, acting as a pointer
	 * @param newData
	 * The data we want to add into the tree
	 * @return
	 * The node we want to return to the parent for repointing
	 */
	public Node<E> recAdd(Node<E> n, E newData){
		// If the node we are on is null, we have found the spot where 
		// the new node should be placed
		if (n == null){
			Node<E> newNode = new Node<E>(newData);
			updateHeight(newNode);
			return balanceFactorCheck(newNode);
			
		}
		// Use the compareTo method to traverse down the tree 
		else if (newData.compareTo(n.getData()) < 0){
			n.setLeft(recAdd(n.getLeft(), newData));
		}
		else{
			n.setRight(recAdd(n.getRight(), newData));
		}
		// Before every return statement, we must update the height of the current node
		updateHeight(n);
		return balanceFactorCheck(n);
		
	}
	
	/**
	 * This method is a helper method for the recurse remove method.
	 * It serves to remove a node from the tree
	 * @param item
	 * The data we want to remove
	 */
	public void remove(E item){
		root = recRemove(root, item);
	}
	
	/**
	 * This method is a recursive implementation used to remove a node from the tree
	 * @param n
	 * The node we are currently on
	 * @param dataTBR
	 * The data we want to remove
	 * @return
	 * The node that we want to return to the parent for repointing
	 */
	public Node<E> recRemove(Node<E> n, E dataTBR){
		if (n != null){
			// Data is less than current node, traverse right
			if (dataTBR.compareTo(n.getData()) < 0){
				n.setLeft(recRemove(n.getLeft(), dataTBR));
				return n.getLeft();
			}
			// Data is greater than current node, traverse right
			else if (dataTBR.compareTo(n.getData()) > 0){
				n.setRight(recRemove(n.getRight(), dataTBR));
				return n.getRight();
			}
			// Data is the one we are looking for
			else {
				// If this node has no children, return null, making 
				// the parent point to null thus removing the node
				if (n.getRight() == null && n.getLeft() == null){
					return null;
				}
				// If the node has one child, return the child to the parent 
				else if (n.getRight() == null || n.getLeft() == null){
					if (n.getRight() == null){
						return n.getLeft();
					}
					else
						return n.getRight();
				}
				// The node has two children
				else{
					// get the predecessor and then swap then and remove the duplicate node
					E data = getPredecessor(n);
					n.setData(data);
					n.setLeft(recRemove(n.getLeft(), data));
					return n;
					
				}
					
			}
		}
		else return null;
		
	}
	
	/**
	 * This method helps when trying to remove a node with two children, 
	 * helping to find the node to become the new root to the subtree
	 * @param n
	 * The node we need a predecessor for
	 * @return
	 * The data of the predecessor
	 */
	public E getPredecessor(Node<E> n){
		
		Node<E> cur = n.getLeft();
		while (cur.getRight() != null){
			cur = cur.getRight();
		}
		return cur.getData();
		
		
	}
	
	/**
	 * This method serves to update the height of a node
	 * @param n
	 * The node that will be updated
	 */
	public void updateHeight(Node<E> n){
		// If both children are null, the height is zero
		if (n.getRight() == null && n.getLeft() == null)
			n.setHeight(1);
		
		// If the node has one child, the height is the height of the other child + 1
		else if (n.getRight() == null || n.getLeft() == null){
			if (n.getRight() == null){
				n.setHeight(n.getLeft().getHeight() + 1);
			}
			else
				n.setHeight(n.getRight().getHeight() + 1);
		}
		// If the node has two children, the height is 
		// one more than the highest height of the children
		else
			n.setHeight((n.getRight().getHeight() > n.getLeft().getHeight() ? n.getRight().getHeight()+1 : n.getLeft().getHeight() +1));
		
	}
	public Node<E> balanceFactorCheck(Node<E> n){
		// If the current node isn't null and one of the children isn't null, we can continue
		if (n != null && (n.getRight() != null || n.getLeft() != null)){
			int balFactor;
			// if one of the children is null, then the balance factor is as follows
			if (n.getRight() == null){
				balFactor = -n.getLeft().getHeight();
			}
			else if (n.getLeft() == null){
				balFactor = n.getRight().getHeight();
			}
			// If not, compute balance factor the normal way
			else balFactor = n.getRight().getHeight() - n.getLeft().getHeight();
			
			// If the balance factor is unacceptable according to AVL standards,
			// there is no way n.right's children can be null or n.left's children
			// can be null because it cannot get that skinny if the previous 
			// calls to this method have worked.  So we can continue
			
			// Do the appropriate rotation
			// We must return the fixed root of the current subtree so that we can connect 
			// the parent back onto the correct root
			if (balFactor > 1){
				if (n.getRight().getRight() == null || n.getRight().getLeft() == null){
					if (n.getRight().getRight() == null){
						n = RotationRL(n);
						return n;
					}
					else{
						n = RotationRR(n);
						return n;
					}

				}
				int newBF = n.getRight().getRight().getHeight() - n.getRight().getLeft().getHeight();
				if (newBF >= 0){
					n = RotationRR(n);
					return n;
				}
				else {
					n = RotationRL(n);
					return n;
				}
			}
			else if (balFactor < -1){
				int newBF = n.getLeft().getRight().getHeight() - n.getLeft().getLeft().getHeight();
				if (newBF <= 0){
					n = RotationLL(n);
					return n;
				}
				else {
					n = RotationLR(n);
					return n;
				}
	
			}
			else return n;
		}
		else return n;
	}
	
	/**
	 * This method performs an RR rotation to balance the AVL tree back to acceptable levels
	 * @param n
	 * The node which the rotation will be performed on
	 * @return
	 * returns the new root of the subtree that the rotation was called on
	 */
	public Node<E> RotationRR(Node<E> n){
		Node<E> B = n.getRight();
		n.setRight(B.getLeft());
		B.setLeft(n);
		
		updateHeight(n);
		updateHeight(B);
		return B;
	}
	/**
	 * This method performs an RL rotation to balance the AVL tree back to acceptable levels
	 * @param n
	 * The node which the rotation will be performed on
	 * @return
	 * returns the new root of the subtree that the rotation was called on
	 */
	public Node<E> RotationRL(Node<E> n){
		Node<E> B = n.getRight();
		Node<E> C = B.getLeft();
		
		n.setRight(C.getRight());
		B.setLeft(C.getLeft());
		C.setRight(B);
		C.setLeft(n);
		updateHeight(n);
		updateHeight(B);
		updateHeight(C);
		return C;
	}
	/**
	 * This method performs an LL rotation to balance the AVL tree back to acceptable levels
	 * @param n
	 * The node which the rotation will be performed on
	 * @return
	 * returns the new root of the subtree that the rotation was called on
	 */
	public Node<E> RotationLL(Node<E> n){
		Node<E> B = n.getLeft();
		n.setLeft(B.getRight());
		B.setRight(n);
		
		updateHeight(n);
		updateHeight(B);
		return B;
	}
	/**
	 * This method performs an LR rotation to balance the AVL tree back to acceptable levels
	 * @param n
	 * The node which the rotation will be performed on
	 * @return
	 * returns the new root of the subtree that the rotation was called on
	 */
	public Node<E> RotationLR(Node<E> n){
		Node<E> B = n.getLeft();
		Node<E> C = B.getRight();
		
		n.setLeft(C.getRight());
		B.setRight(C.getLeft());
		C.setRight(n);
		C.setLeft(B);
		
		updateHeight(n);
		updateHeight(B);
		updateHeight(C);
		return C;
	}
	
	public Iterator<E> getIterator(){
		return new AVLIterator<E>(this);
	}

	
	
	/**
	 * @author George Miller
	 *
	 * @param <E>
	 * The data of this iterator and tree
	 */
	@SuppressWarnings("hiding")
	public class AVLIterator <E extends Comparable<E>> implements Iterator<E>{
		AVLTree<E> tree;
		ArrayList<E> iterator;
		int current;
		
		
		/**
		 * This constructor creates a iterator on the given AVLTree
		 * @param tree
		 * The tree to make the iterator on
		 */
		public AVLIterator(AVLTree<E> tree){
			this.tree = tree;
			iterator = new ArrayList<E>();
			inOrderTraversal(tree.root);
			current = -1;
		}
		
		/**
		 * This initializes the iterator recursively according to an in order traversal
		 * @param n
		 * The node we are currently on
		 */
		private void inOrderTraversal(Node<E> n){
			
			if (n.getLeft() != null){
				inOrderTraversal(n.getLeft());
			}
			iterator.add(n.getData());
			if (n.getRight() != null){
				inOrderTraversal(n.getRight());
			}
			
		}
		
		
		@Override
		public boolean hasNext() {
			if (current < iterator.size() - 1){
				return true;
			}
			else return false;
		}

		@Override
		public E next() {
			current++;
			return iterator.get(current);
		}

		@Override
		public void remove() {
			iterator.remove(current);
			current--;
			
		}
		
		
	}
	
	
}
