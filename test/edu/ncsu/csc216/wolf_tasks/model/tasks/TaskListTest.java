package edu.ncsu.csc216.wolf_tasks.model.tasks;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test the TaskList class.
 * 
 * @author Michael Warstler
 */
public class TaskListTest {

	/** Task Name */
	public static final String NAME = "Project";
	/** Task description */
	public static final String DESCRIPTION = "Finish Project 2";

	/**
	 * Test getTasksAsArray() method.
	 */
	@Test
	public void testGetTasksAsArray() {
		// Create valid TaskList.
		TaskList myTaskList = new TaskList("My List", 0);
		// Construct valid Tasks and add to TaskList.
		Task myTask1 = new Task(NAME, DESCRIPTION, false, false);
		Task myTask2 = new Task("Exercise", "Go for a walk", true, true);
		Task myTask3 = new Task("Piano Practice", "Starts at 5:30", true, false);
		Task myTask4 = new Task("Job", "Pizza delivery", false, false);
		myTaskList.addTask(myTask1);
		myTaskList.addTask(myTask2);
		myTaskList.addTask(myTask3);
		myTaskList.addTask(myTask4);

		// Check values in 2D array.
		String[][] taskListArray = myTaskList.getTasksAsArray();
		assertEquals(4, taskListArray.length);
		assertEquals("1", taskListArray[0][0]);
		assertEquals(NAME, taskListArray[0][1]);
		assertEquals("2", taskListArray[1][0]);
		assertEquals("Exercise", taskListArray[1][1]);
		assertEquals("3", taskListArray[2][0]);
		assertEquals("Piano Practice", taskListArray[2][1]);
		assertEquals("4", taskListArray[3][0]);
		assertEquals("Job", taskListArray[3][1]);
	}

	/**
	 * Test compareTo() method.
	 */
	@Test
	public void testCompareTo() {
		// Create valid AbstractTaskList.
		TaskList myTaskList = new TaskList("My List", 0);
		TaskList anotherTaskList = new TaskList("Exercise Goals", 0);
		TaskList sameNameTaskList = new TaskList("My List", 0);
		
		// Confirm correct return values.
		assertEquals(1, myTaskList.compareTo(anotherTaskList));
		assertEquals(-1, anotherTaskList.compareTo(myTaskList));
		assertEquals(0, myTaskList.compareTo(sameNameTaskList));
	}
}
