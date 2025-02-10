package edu.ncsu.csc216.wolf_tasks.model.util;

/**
 * Handles functionality of multiple TaskList objects (represented by taskLists
 * field in Notebook class). Can add/remove task lists. Can return individual
 * task lists, confirm if one exists, and return the size or number of task
 * lists currently in THIS list. Maintains a sorted order of elements, by
 * implementing the Comparable.compareTo() method.
 * 
 * @param <E> is generic type.
 * @author Michael Warstler
 */
public class SortedList<E extends Comparable<E>> implements ISortedList<E> {

	/** Number of elements currently in the list */
	private int size;
	/** Initial node of the LinkedList */
	private ListNode front;

	/**
	 * Constructor for the SortedList. Size initialized to 0 and front initialized
	 * to null.
	 */
	public SortedList() {
		size = 0;
		front = null;
	}

	/**
	 * Adds an element to the list in sorted alphabetical order.
	 * 
	 * @param element is the object to add to the list.
	 * @throws NullPointerException     if parameter is null.
	 * @throws IllegalArgumentException if parameter is a duplicate of an existing
	 *                                  element.
	 * 
	 */
	@Override
	public void add(E element) {
		// Check for null param.
		if (element == null) {
			throw new NullPointerException("Cannot add null element.");
		}

		// Variable to track if element gets added.
		boolean added = false;

		// If no elements in list, set up front
		if (size == 0) {
			front = new ListNode(element, front);
			added = true;
		} else {
			// Check if element is a duplicate of another.
			ListNode current = front;
			for (int i = 0; i < size; i++) {
				// Check for duplicate
				if (current.data.equals(element)) {
					throw new IllegalArgumentException("Cannot add duplicate element.");
				}
				current = current.next; // shift to next node. At last node, current would become null here.
			}

			// If no exception, check front of list first for adding
			if (element.compareTo(front.data) < 0) { // param comes "before".
				front = new ListNode(element, front);
				added = true;
			} else {
				// Cycle through existing list and check elements with compareTo
				current = front; // new element must come after the front.
				for (int i = 0; i < size - 1; i++) {
					// Check if parameter element comes before the data in the next node
					if (element.compareTo(current.next.data) < 0) {
						// "Next" node is a new node that points to the original "next" node.
						current.next = new ListNode(element, current.next);
						added = true;
						break; // Don't want to add again.
					} else {
						current = current.next; // move to next node
					}
				}
			}

			// If never added, (last in ordering) need to add.
			if (!added) {
				current.next = new ListNode(element, null); // adds to the end of the list.
			}
		}
		size++;
	}

	/**
	 * Removes an element/node at an index parameter.
	 * 
	 * @param idx is index of list to remove.
	 * @return is the element at the idx location that was removed.
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	@Override
	public E remove(int idx) {
		checkIndex(idx);

		// Create holder for data being removed.
		E value;
		// Check front
		if (idx == 0) {
			value = front.data; // get the data at front.
			front = front.next; // sets the "original" front to be the next node.
		} else {
			// Cycle through list until index - 1.
			ListNode current = front;
			for (int i = 0; i < idx - 1; i++) {
				current = current.next;
			}
			value = current.next.data; // get the value from the index being removed.
			current.next = current.next.next; // Skips the removed index and sets the "next" value to 2 nodes over.
		}
		size--;
		return value;
	}

	/**
	 * Checks the index parameter for possible out of bounds exceptions. Cannot get
	 * or remove an index value less than 0 or equal/greater to size.
	 * 
	 * @param idx of list to check exceptions for.
	 * @throws IndexOutOfBoundsException if idx is less than 0 or greater than or
	 *                                   equal to size.
	 */
	private void checkIndex(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
	}

	/**
	 * Determines if an element is in the SortedList or not.
	 * 
	 * @return is true if the list has the element, false otherwise.
	 */
	@Override
	public boolean contains(E element) {
		// Cycle through list looking for element
		ListNode current = front;
		for (int i = 0; i < size; i++) {
			if (current.data.equals(element)) {
				return true; // element found
			}
			current = current.next;
		}
		return false; // element not found
	}

	/**
	 * Gets the element at the parameter index from the sorted list.
	 * 
	 * @param idx is the index of the list to return.
	 * @return is the element stored at the index of the sorted list.
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	@Override
	public E get(int idx) {
		checkIndex(idx);
		// Cycle through ListNodes until at idx node.
		ListNode current = front;
		for (int i = 0; i < idx; i++) {
			current = current.next;
		}
		return current.data;
	}

	/**
	 * Provides the size field.
	 * 
	 * @return is the size field.
	 */
	@Override
	public int size() {
		return size;
	}

	//////////////////////////////////////////////////////////////////
	//PRIVATE INNER CLASS: LISTNODE
	//////////////////////////////////////////////////////////////////
	
	/**
	 * Establishes a node/link for the SortedList. Each Node contains data and a
	 * reference to the next Node in the list.
	 * 
	 * @author Michael Warstler
	 */
	private class ListNode {
		/** The data/object that the node contains */
		public E data;
		/** Reference to the "next" node */
		public ListNode next;

		/**
		 * Constructor to establish the "next" link/node of the list.
		 * 
		 * @param data of link/node to set up new node with.
		 * @param next is pointer to the "next" ListNode that this node points to.
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
	}
}
