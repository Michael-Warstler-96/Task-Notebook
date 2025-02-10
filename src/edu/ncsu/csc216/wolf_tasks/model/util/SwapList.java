package edu.ncsu.csc216.wolf_tasks.model.util;

/**
 * Handles implementation of ISwapList interface. Controls functionality of an
 * array containing generic type E. Array methods involve adding/removing
 * elements, moving elements up/down/front/back, returning an element and the
 * size of the array.
 * 
 * @param <E> represents the generic type
 * @author Michael Warstler
 */
public class SwapList<E> implements ISwapList<E> {

	/** Initial Capacity of the SwapList */
	private static final int INITIAL_CAPACITY = 10;
	/** Array to hold E objects */
	private E[] list;
	/** Indicates the number of elements currently in the list field */
	private int size;

	/**
	 * Constructor for the SwapList. Sets up the list array as an object array. Size
	 * initialized to 0.
	 */
	@SuppressWarnings("unchecked")
	public SwapList() {
		list = (E[]) new Object[INITIAL_CAPACITY];
		size = 0;
	}

	/**
	 * Adds the element to the end of the list.
	 * 
	 * @param element element to add
	 * @throws NullPointerException     if element is null
	 * @throws IllegalArgumentException if element is a duplicate to another in the
	 *                                  list.
	 */
	@Override
	public void add(E element) {
		// Check for null
		if (element == null) {
			throw new NullPointerException("Cannot add null element.");
		}

		// Check for duplicate
		for (int i = 0; i < size; i++) {
			if (list[i].equals(element)) {
				throw new IllegalArgumentException("Cannot add a duplicate element");
			}
		}

		// see if needing to grow the array. + 1 since we want array to always be
		// partially filled (never filled all the way to the end where size == length)
		checkCapacity(size + 1);

		// Add element to end of the list and increment size.
		list[size] = element;
		size++;
	}

	/**
	 * Checks the capacity of the current list and determines if it needs to grow in
	 * size. If parameter equals or exceeds current list.length, list is doubled in
	 * length. Method is used by add(E element) method, and will always receive a
	 * parameter equal to size + 1. Size + 1 instead of just size is used to always
	 * ensure a partially filled array where the last index before capacity/length
	 * is empty.
	 * 
	 * @param newCapacity is the possible new capacity for the list.length. Always
	 *                    checked/set with size + 1.
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void checkCapacity(int newCapacity) {
		// If the newCapacity (current size + 1) value equals or exceeds list
		// length/capacity, grow the array.
		if (newCapacity >= list.length) {
			E[] newArray = (E[]) new Object[list.length * 2]; // set up new array
			// Copy elements from original into new array.
			for (int i = 0; i < size; i++) {
				newArray[i] = list[i];
			}
			list = newArray; // sets the original array to the new array.
		}
	}

	/**
	 * Returns the element from the given index. The element is removed from the
	 * list.
	 * 
	 * @param idx index to remove element from
	 * @return element at given index
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	@Override
	public E remove(int idx) {
		checkIndex(idx);
		// Store the removed element
		E removedElement = list[idx];

		// Start at index param and set element at that index to the next element in
		// list. Stop at the element before the last one (size - 1).
		for (int i = idx; i < size - 1; i++) {
			list[i] = list[i + 1];
		}
		// Set the last element to null (it is already in previous index now)
		list[size - 1] = null;
		// Decrement size
		size--;
		return removedElement;
	}

	/**
	 * Checks the index parameter for possible out of bounds exceptions.
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
	 * Moves the element at the given index to index-1. If the element is already at
	 * the front of the list, the list is not changed.
	 * 
	 * @param idx index of element to move up
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	@Override
	public void moveUp(int idx) {
		checkIndex(idx);

		// Front of list does nothing, else swap idx element with idx-1.
		if (idx != 0) {
			E movedElement = list[idx]; // temporarily store value
			list[idx] = list[idx - 1];
			list[idx - 1] = movedElement;
		}
	}

	/**
	 * Moves the element at the given index to index+1. If the element is already at
	 * the end of the list, the list is not changed.
	 * 
	 * @param idx index of element to move down
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	@Override
	public void moveDown(int idx) {
		checkIndex(idx);
		// Back of list does nothing, else swap idx element with idx+1.
		if (idx != size - 1) {
			E movedElement = list[idx]; // temporarily store value
			list[idx] = list[idx + 1];
			list[idx + 1] = movedElement;
		}
	}

	/**
	 * Moves the element at the given index to index 0. If the element is already at
	 * the front of the list, the list is not changed.
	 * 
	 * @param idx index of element to move to the front
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	@Override
	public void moveToFront(int idx) {
		checkIndex(idx);

		// Front of list does nothing, else move element from idx to front.
		if (idx != 0) {
			E movedElement = list[idx]; // temporarily store value
			// Only shift values earlier than index.
			for (int i = idx; i > 0; i--) {
				list[i] = list[i - 1];
			}
			// Swap the front with the element previously at idx
			list[0] = movedElement;
		}
	}

	/**
	 * Moves the element at the given index to size-1. If the element is already at
	 * the end of the list, the list is not changed.
	 * 
	 * @param idx index of element to move to the back
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	@Override
	public void moveToBack(int idx) {
		checkIndex(idx);

		// Back of list does nothing, else move element from idx to back.
		if (idx != size - 1) {
			E movedElement = list[idx]; // temporarily store value
			// Only shift values earlier than index.
			for (int i = idx; i < size - 1; i++) {
				list[i] = list[i + 1];
			}
			// Swap the back with the element previously at idx
			list[size - 1] = movedElement;
		}
	}

	/**
	 * Returns the element at the given index.
	 * 
	 * @param idx index of the element to retrieve
	 * @return element at the given index
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	@Override
	public E get(int idx) {
		checkIndex(idx);
		return list[idx];
	}

	/**
	 * Returns the number of elements in the list.
	 * 
	 * @return number of elements in the list
	 */
	@Override
	public int size() {
		return size;
	}
}
