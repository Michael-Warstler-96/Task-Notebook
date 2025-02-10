package edu.ncsu.csc216.wolf_tasks.model.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test the SwapList class.
 * 
 * @author Michael Warstler
 */
public class SwapListTest {

	/**
	 * Test constructor in SwapList.
	 */
	@Test
	public void testSwapList() {
		// Create new
		SwapList<String> myList = new SwapList<String>();
		assertEquals(0, myList.size());
	}

	/**
	 * Test add() method.
	 */
	@Test
	public void testAdd() {
		// Create new
		SwapList<String> myList = new SwapList<String>();

		// Add element and check size update
		myList.add("Berry");
		assertEquals(1, myList.size()); // size is 1
		assertEquals("Berry", myList.get(0)); // index is 0.

		// Add more elements (to end of list)
		myList.add("Dogs");
		myList.add("Cats");
		myList.add("Dragons");
		myList.add("Boats");

		// Check size and locations of elements
		assertEquals(5, myList.size());
		assertEquals("Berry", myList.get(0));
		assertEquals("Cats", myList.get(2));
		assertEquals("Boats", myList.get(4));

		// Add more elements to enable checkCapacity() method
		myList.add("Bears");
		myList.add("Tiger");
		myList.add("Trains");
		myList.add("Beaver");
		myList.add("Bikes"); // 10th element should cause capacity to grow
		myList.add("Yellow");
		assertEquals(11, myList.size());

		// Test exception cases
		assertThrows(NullPointerException.class, () -> myList.add(null)); // null element
		assertThrows(IllegalArgumentException.class, () -> myList.add("Beaver"));	// duplicate.
	}

	/**
	 * Test remove() method.
	 */
	@Test
	public void testRemove() {
		// Create new
		SwapList<String> myList = new SwapList<String>();

		// Add elements
		myList.add("Berry");
		myList.add("Dogs");
		myList.add("Cats");
		myList.add("Dragons");
		myList.add("Boats");
		assertEquals(5, myList.size());

		// IndexOutOfBounds check for less than 0 or greater than size.
		assertThrows(IndexOutOfBoundsException.class, () -> myList.remove(-4));
		assertThrows(IndexOutOfBoundsException.class, () -> myList.remove(8));

		// Remove from end of list
		myList.remove(4);
		assertEquals(4, myList.size());
		// Check that last value is now "Dragons" and other elements did not shift.
		assertEquals("Berry", myList.get(0));
		assertEquals("Dogs", myList.get(1));
		assertEquals("Cats", myList.get(2));
		assertEquals("Dragons", myList.get(3));

		// Remove from beginning of list. Check that elements shifted.
		myList.remove(0);
		assertEquals(3, myList.size());
		assertEquals("Dogs", myList.get(0));
		assertEquals("Cats", myList.get(1));
		assertEquals("Dragons", myList.get(2)); // previously index 3

		// Remove from middle of list. Check that elements shifted.
		myList.remove(1);
		assertEquals(2, myList.size());
		assertEquals("Dogs", myList.get(0));
		assertEquals("Dragons", myList.get(1)); // previously index 2
	}

	/**
	 * Test moveUp() method.
	 */
	@Test
	public void testMoveUp() {
		// Create new
		SwapList<String> myList = new SwapList<String>();

		// Add elements
		myList.add("Berry");
		myList.add("Dogs");
		myList.add("Cats");
		myList.add("Dragons");
		myList.add("Boats");
		assertEquals(5, myList.size());

		// confirm nothing changes when swapping front.
		myList.moveUp(0);
		assertEquals("Berry", myList.get(0));
		assertEquals("Dogs", myList.get(1));
		assertEquals("Cats", myList.get(2));
		assertEquals("Dragons", myList.get(3));
		assertEquals("Boats", myList.get(4));
		assertEquals(5, myList.size());

		// Move up from middle
		myList.moveUp(2);
		assertEquals("Berry", myList.get(0));
		assertEquals("Cats", myList.get(1));
		assertEquals("Dogs", myList.get(2));
		assertEquals("Dragons", myList.get(3));
		assertEquals("Boats", myList.get(4));
		assertEquals(5, myList.size());

		// Move up from back
		myList.moveUp(4);
		assertEquals("Berry", myList.get(0));
		assertEquals("Cats", myList.get(1));
		assertEquals("Dogs", myList.get(2));
		assertEquals("Boats", myList.get(3));
		assertEquals("Dragons", myList.get(4));
		assertEquals(5, myList.size());

		// IndexOutOfBounds check for less than 0 or greater than size.
		assertThrows(IndexOutOfBoundsException.class, () -> myList.moveUp(-4));
		assertThrows(IndexOutOfBoundsException.class, () -> myList.moveUp(8));
	}

	/**
	 * Test moveDown() method.
	 */
	@Test
	public void testMoveDown() {
		// Create new
		SwapList<String> myList = new SwapList<String>();

		// Add elements
		myList.add("Berry");
		myList.add("Dogs");
		myList.add("Cats");
		myList.add("Dragons");
		myList.add("Boats");
		assertEquals(5, myList.size());

		// confirm nothing changes when swapping back.
		myList.moveDown(4);
		assertEquals("Berry", myList.get(0));
		assertEquals("Dogs", myList.get(1));
		assertEquals("Cats", myList.get(2));
		assertEquals("Dragons", myList.get(3));
		assertEquals("Boats", myList.get(4));
		assertEquals(5, myList.size());

		// Move down from middle
		myList.moveDown(2);
		assertEquals("Berry", myList.get(0));
		assertEquals("Dogs", myList.get(1));
		assertEquals("Dragons", myList.get(2));
		assertEquals("Cats", myList.get(3));
		assertEquals("Boats", myList.get(4));
		assertEquals(5, myList.size());

		// Move down from front
		myList.moveDown(0);
		assertEquals("Dogs", myList.get(0));
		assertEquals("Berry", myList.get(1));
		assertEquals("Dragons", myList.get(2));
		assertEquals("Cats", myList.get(3));
		assertEquals("Boats", myList.get(4));
		assertEquals(5, myList.size());

		// IndexOutOfBounds check for less than 0 or greater than size.
		assertThrows(IndexOutOfBoundsException.class, () -> myList.moveUp(-4));
		assertThrows(IndexOutOfBoundsException.class, () -> myList.moveUp(8));
	}

	/**
	 * Test moveToFront() method.
	 */
	@Test
	public void testMoveToFront() {
		// Create new
		SwapList<String> myList = new SwapList<String>();

		// Add elements
		myList.add("Berry");
		myList.add("Dogs");
		myList.add("Cats");
		myList.add("Dragons");
		myList.add("Boats");
		assertEquals(5, myList.size());

		// Confirm no change when move to front at front
		myList.moveToFront(0);
		assertEquals("Berry", myList.get(0));
		assertEquals("Dogs", myList.get(1));
		assertEquals("Cats", myList.get(2));
		assertEquals("Dragons", myList.get(3));
		assertEquals("Boats", myList.get(4));
		assertEquals(5, myList.size());

		// Move to front from middle
		myList.moveToFront(2);
		assertEquals("Cats", myList.get(0));
		assertEquals("Berry", myList.get(1));
		assertEquals("Dogs", myList.get(2));
		assertEquals("Dragons", myList.get(3));
		assertEquals("Boats", myList.get(4));
		assertEquals(5, myList.size());

		// Move to front from end
		myList.moveToFront(4);
		assertEquals("Boats", myList.get(0));
		assertEquals("Cats", myList.get(1));
		assertEquals("Berry", myList.get(2));
		assertEquals("Dogs", myList.get(3));
		assertEquals("Dragons", myList.get(4));
		assertEquals(5, myList.size());

		// IndexOutOfBounds check for less than 0 or greater than size.
		assertThrows(IndexOutOfBoundsException.class, () -> myList.moveToFront(-4));
		assertThrows(IndexOutOfBoundsException.class, () -> myList.moveToFront(8));
	}

	/**
	 * Test moveToBack() method.
	 */
	@Test
	void testMoveToBack() {
		// Create new
		SwapList<String> myList = new SwapList<String>();

		// Add elements
		myList.add("Berry");
		myList.add("Dogs");
		myList.add("Cats");
		myList.add("Dragons");
		myList.add("Boats");
		assertEquals(5, myList.size());

		// Confirm no change when move to back at back
		myList.moveToBack(4);
		assertEquals("Berry", myList.get(0));
		assertEquals("Dogs", myList.get(1));
		assertEquals("Cats", myList.get(2));
		assertEquals("Dragons", myList.get(3));
		assertEquals("Boats", myList.get(4));
		assertEquals(5, myList.size());

		// Move to back from middle
		myList.moveToBack(2);
		assertEquals("Berry", myList.get(0));
		assertEquals("Dogs", myList.get(1));
		assertEquals("Dragons", myList.get(2));
		assertEquals("Boats", myList.get(3));
		assertEquals("Cats", myList.get(4));
		assertEquals(5, myList.size());

		// Move to back from start
		myList.moveToBack(0);
		assertEquals("Dogs", myList.get(0));
		assertEquals("Dragons", myList.get(1));
		assertEquals("Boats", myList.get(2));
		assertEquals("Cats", myList.get(3));
		assertEquals("Berry", myList.get(4));
		assertEquals(5, myList.size());

		// IndexOutOfBounds check for less than 0 or greater than size.
		assertThrows(IndexOutOfBoundsException.class, () -> myList.moveToBack(-4));
		assertThrows(IndexOutOfBoundsException.class, () -> myList.moveToBack(8));
	}

	/**
	 * Test get() method (mainly exception check)
	 */
	@Test
	public void testGet() {
		// Create new
		SwapList<String> myList = new SwapList<String>();

		// Add elements
		myList.add("Berry");
		myList.add("Dogs");
		myList.add("Cats");

		assertThrows(IndexOutOfBoundsException.class, () -> myList.get(-4));
		assertThrows(IndexOutOfBoundsException.class, () -> myList.get(3));
	}
}
