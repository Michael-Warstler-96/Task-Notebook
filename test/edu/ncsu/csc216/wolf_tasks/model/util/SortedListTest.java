package edu.ncsu.csc216.wolf_tasks.model.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test the SortedList Class.
 * 
 * @author Michael Warstler
 */
public class SortedListTest {

	/**
	 * Test constructor for SortedList.
	 */
	@Test
	public void testSortedList() {
		SortedList<String> myList = new SortedList<String>();
		assertEquals(0, myList.size());
	}

	/**
	 * Test add() method.
	 */
	@Test
	public void testAdd() {
		SortedList<String> myList = new SortedList<String>();

		// Add element and check
		myList.add("Berry");
		assertEquals(1, myList.size()); // size is 1
		assertEquals("Berry", myList.get(0)); // index is 0.

		// Add element to end
		myList.add("Dogs");
		assertEquals(2, myList.size());
		assertEquals("Berry", myList.get(0));
		assertEquals("Dogs", myList.get(1));

		// Add element to beginning
		myList.add("Apples");
		assertEquals(3, myList.size());
		assertEquals("Apples", myList.get(0));
		assertEquals("Berry", myList.get(1));
		assertEquals("Dogs", myList.get(2));

		// Add element to middle
		myList.add("Cats");
		assertEquals(4, myList.size());
		assertEquals("Apples", myList.get(0));
		assertEquals("Berry", myList.get(1));
		assertEquals("Cats", myList.get(2));
		assertEquals("Dogs", myList.get(3));

		// Add more
		myList.add("Dragons");
		myList.add("Peach");
		myList.add("Ant");
		assertEquals(7, myList.size());
		assertEquals("Ant", myList.get(0));
		assertEquals("Apples", myList.get(1));
		assertEquals("Berry", myList.get(2));
		assertEquals("Cats", myList.get(3));
		assertEquals("Dogs", myList.get(4));
		assertEquals("Dragons", myList.get(5));
		assertEquals("Peach", myList.get(6));

		// Check Exceptions
		assertThrows(NullPointerException.class, () -> myList.add(null));
		assertThrows(IllegalArgumentException.class, () -> myList.add("Cats"));
	}

	/**
	 * Test remove() method.
	 */
	@Test
	public void testRemove() {
		SortedList<String> myList = new SortedList<String>();

		// Add elements
		myList.add("Berry"); // index 0
		myList.add("Dogs"); // index 3
		myList.add("Cats"); // index 2
		myList.add("Dragons"); // index 4
		myList.add("Boats"); // index 1
		assertEquals(5, myList.size());

		// IndexOutOfBounds check for less than 0 or greater than size.
		assertThrows(IndexOutOfBoundsException.class, () -> myList.remove(-4));
		assertThrows(IndexOutOfBoundsException.class, () -> myList.remove(8));

		// Remove from end of list
		myList.remove(4); // dragons
		assertEquals(4, myList.size());
		// Check that other elements did not shift.
		assertEquals("Berry", myList.get(0));
		assertEquals("Boats", myList.get(1));
		assertEquals("Cats", myList.get(2));
		assertEquals("Dogs", myList.get(3));

		// Remove from beginning of list. Check that elements shifted.
		myList.remove(0);
		assertEquals(3, myList.size());
		assertEquals("Boats", myList.get(0));
		assertEquals("Cats", myList.get(1));
		assertEquals("Dogs", myList.get(2));

		// Remove from middle of list. Check that elements shifted.
		myList.remove(1);
		assertEquals(2, myList.size());
		assertEquals("Boats", myList.get(0));
		assertEquals("Dogs", myList.get(1));
	}

	/**
	 * Test contains() method.
	 */
	@Test
	public void testContains() {
		SortedList<String> myList = new SortedList<String>();

		// Add elements
		myList.add("Berry");
		myList.add("Dogs");
		myList.add("Cats");
		myList.add("Dragons");
		myList.add("Boats");

		// Front
		assertTrue(myList.contains("Berry"));
		// Middle
		assertTrue(myList.contains("Cats"));
		// End
		assertTrue(myList.contains("Dragons"));
		// Element not in list
		assertFalse(myList.contains("Grapes"));
	}

	/**
	 * Test get() method (exceptions).
	 */
	@Test
	public void testGet() {
		SortedList<String> myList = new SortedList<String>();

		// Add elements
		myList.add("Berry");
		myList.add("Dogs");
		myList.add("Cats");
		myList.add("Dragons");
		myList.add("Boats");
		assertEquals(5, myList.size());

		assertThrows(IndexOutOfBoundsException.class, () -> myList.get(-4));
		assertThrows(IndexOutOfBoundsException.class, () -> myList.get(8));
	}
}
