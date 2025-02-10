package edu.ncsu.csc216.wolf_tasks.model.util;

/**
 * Interface for a list that keeps objects in sorted order as defined by the
 * Comparable interface.
 * 
 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
 * 
 * @param <E> type for ISortedList; must implement Comparable
 */
public interface ISortedList<E extends Comparable<E>> {

	/**
	 * Adds the element to the list in sorted order.
	 * 
	 * @param element element to add
	 * @throws NullPointerException     if element is null
	 * @throws IllegalArgumentException if element cannot be added
	 */
	void add(E element);

	/**
	 * Returns the element from the given index. The element is removed from the
	 * list.
	 * 
	 * @param idx index to remove element from
	 * @return element at given index
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	E remove(int idx);

	/**
	 * Returns true if the element is in the list.
	 * 
	 * @param element element to search for
	 * @return true if element is found
	 */
	boolean contains(E element);

	/**
	 * Returns the element at the given index.
	 * 
	 * @param idx index of the element to retrieve
	 * @return element at the given index
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	E get(int idx);

	/**
	 * Returns the number of elements in the list.
	 * 
	 * @return number of elements in the list
	 */
	int size();

}