package edu.ncsu.csc216.wolf_tasks.model.util;

/**
 * Interface for a list that changes the position of elements through swap
 * operations.
 * 
 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
 *
 * @param <E> type for the ISwapList
 */
public interface ISwapList<E> {

	/**
	 * Adds the element to the end of the list.
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
	 * Moves the element at the given index to index-1. If the element is already at
	 * the front of the list, the list is not changed.
	 * 
	 * @param idx index of element to move up
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	void moveUp(int idx);

	/**
	 * Moves the element at the given index to index+1. If the element is already at
	 * the end of the list, the list is not changed.
	 * 
	 * @param idx index of element to move down
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	void moveDown(int idx);

	/**
	 * Moves the element at the given index to index 0. If the element is already at
	 * the front of the list, the list is not changed.
	 * 
	 * @param idx index of element to move to the front
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	void moveToFront(int idx);

	/**
	 * Moves the element at the given index to size-1. If the element is already at
	 * the end of the list, the list is not changed.
	 * 
	 * @param idx index of element to move to the back
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	void moveToBack(int idx);

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