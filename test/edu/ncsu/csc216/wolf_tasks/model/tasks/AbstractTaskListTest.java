package edu.ncsu.csc216.wolf_tasks.model.tasks;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test the AbstractTaskList class.
 * 
 * @author Michael Warstler
 */
public class AbstractTaskListTest {

	/** Task Name */
	public static final String NAME = "Project";
	/** Task description */
	public static final String DESCRIPTION = "Finish Project 2";

	/**
	 * Test constructor of AbstractTaskList and setTaskListName().
	 */
	@Test
	public void testAbstractTaskList() {
		// Create valid AbstractTaskList.
		AbstractTaskList myTaskList = new TaskList("My List", 0);

		// Check getters
		assertEquals("My List", myTaskList.getTaskListName());
		assertEquals(0, myTaskList.getCompletedCount());
		assertEquals(0, myTaskList.getTasks().size()); // no Tasks yet.

		// Test invalid construction
		assertThrows(IllegalArgumentException.class, () -> new TaskList("My List", -5));
		assertThrows(IllegalArgumentException.class, () -> new TaskList(null, 5));
		assertThrows(IllegalArgumentException.class, () -> new TaskList("", 5));

		// Test invalid setTaskListName directly.
		myTaskList.setTaskListName("New Name!");
		assertEquals("New Name!", myTaskList.getTaskListName());
		assertThrows(IllegalArgumentException.class, () -> myTaskList.setTaskListName(null));
	}

	/**
	 * Test addTask() and getTask() methods.
	 */
	@Test
	public void testAddAndGetTask() {
		// Create valid AbstractTaskList.
		AbstractTaskList myTaskList = new TaskList("My List", 0);
		// Construct valid Task
		Task myTask1 = new Task(NAME, DESCRIPTION, false, false);

		// Add Task to TaskList
		assertEquals(0, myTaskList.getTasks().size()); // no tasks
		myTaskList.addTask(myTask1);
		assertEquals(1, myTaskList.getTasks().size()); // confirm size increased.

		// Construct more Tasks and add
		Task myTask2 = new Task("Exercise", "Go for a walk", true, true);
		Task myTask3 = new Task("Piano Practice", "Starts at 5:30", true, false);
		Task myTask4 = new Task("Job", "Pizza delivery", false, false);
		myTaskList.addTask(myTask2);
		myTaskList.addTask(myTask3);
		myTaskList.addTask(myTask4);

		// Check size and values. New Tasks added to back of TaskList.
		assertEquals(4, myTaskList.getTasks().size());
		assertEquals(NAME, myTaskList.getTask(0).getTaskName());
		assertEquals("Exercise", myTaskList.getTask(1).getTaskName());
		assertEquals("Piano Practice", myTaskList.getTask(2).getTaskName());
		assertEquals("Job", myTaskList.getTask(3).getTaskName());
	}

	/**
	 * Test remove() method for TaskList.
	 */
	@Test
	public void testRemoveTask() {
		// Create valid AbstractTaskList.
		AbstractTaskList myTaskList = new TaskList("My List", 0);
		// Construct valid Tasks and add to TaskList.
		Task myTask1 = new Task(NAME, DESCRIPTION, false, false);
		Task myTask2 = new Task("Exercise", "Go for a walk", true, true);
		Task myTask3 = new Task("Piano Practice", "Starts at 5:30", true, false);
		Task myTask4 = new Task("Job", "Pizza delivery", false, false);
		myTaskList.addTask(myTask1);
		myTaskList.addTask(myTask2);
		myTaskList.addTask(myTask3);
		myTaskList.addTask(myTask4);

		// Confirm size and initial locations of Tasks in the TaskList.
		assertEquals(4, myTaskList.getTasks().size());
		assertEquals(NAME, myTaskList.getTask(0).getTaskName());
		assertEquals("Exercise", myTaskList.getTask(1).getTaskName());
		assertEquals("Piano Practice", myTaskList.getTask(2).getTaskName());
		assertEquals("Job", myTaskList.getTask(3).getTaskName());

		// Remove first index
		myTaskList.removeTask(0);
		// Confirm size and new locations of Tasks in the TaskList.
		assertEquals(3, myTaskList.getTasks().size());
		assertEquals("Exercise", myTaskList.getTask(0).getTaskName());
		assertEquals("Piano Practice", myTaskList.getTask(1).getTaskName());
		assertEquals("Job", myTaskList.getTask(2).getTaskName());

		// Remove middle index
		myTaskList.removeTask(1);
		// Confirm size and new locations of Tasks in the TaskList.
		assertEquals(2, myTaskList.getTasks().size());
		assertEquals("Exercise", myTaskList.getTask(0).getTaskName());
		assertEquals("Job", myTaskList.getTask(1).getTaskName());

		// Remove last index
		myTaskList.removeTask(1);
		// Confirm size and new locations of Tasks in the TaskList.
		assertEquals(1, myTaskList.getTasks().size());
		assertEquals("Exercise", myTaskList.getTask(0).getTaskName());
	}

	/**
	 * Test completeTask() method.
	 */
	@Test
	public void testCompleteTask() {
		// Create valid AbstractTaskList.
		AbstractTaskList myTaskList = new TaskList("My List", 0);
		// Construct valid Tasks and add to TaskList.
		Task myTask1 = new Task(NAME, DESCRIPTION, false, false);
		Task myTask2 = new Task("Exercise", "Go for a walk", true, true);
		Task myTask3 = new Task("Piano Practice", "Starts at 5:30", true, false);
		Task myTask4 = new Task("Job", "Pizza delivery", false, false);
		myTaskList.addTask(myTask1);
		myTaskList.addTask(myTask2);
		myTaskList.addTask(myTask3);
		myTaskList.addTask(myTask4);

		// Confirm size and initial locations of Tasks in the TaskList.
		assertEquals(0, myTaskList.getCompletedCount());
		assertEquals(4, myTaskList.getTasks().size());
		assertEquals(NAME, myTaskList.getTask(0).getTaskName());
		assertEquals("Exercise", myTaskList.getTask(1).getTaskName());
		assertEquals("Piano Practice", myTaskList.getTask(2).getTaskName());
		assertEquals("Job", myTaskList.getTask(3).getTaskName());

		// Complete Tasks. Confirm completed count increases and Tasks are removed.
		// Complete first index
		myTaskList.completeTask(myTask1);
		assertEquals(1, myTaskList.getCompletedCount());
		assertEquals(3, myTaskList.getTasks().size());
		assertEquals("Exercise", myTaskList.getTask(0).getTaskName());
		assertEquals("Piano Practice", myTaskList.getTask(1).getTaskName());
		assertEquals("Job", myTaskList.getTask(2).getTaskName());

		// Complete middle index
		myTaskList.completeTask(myTask3);
		assertEquals(2, myTaskList.getCompletedCount());
		assertEquals(2, myTaskList.getTasks().size());
		assertEquals("Exercise", myTaskList.getTask(0).getTaskName());
		assertEquals("Job", myTaskList.getTask(1).getTaskName());

		// Complete last index
		myTaskList.completeTask(myTask4);
		assertEquals(3, myTaskList.getCompletedCount());
		assertEquals(1, myTaskList.getTasks().size());
		assertEquals("Exercise", myTaskList.getTask(0).getTaskName());

		// Complete remaining Task
		myTaskList.completeTask(myTask2);
		assertEquals(4, myTaskList.getCompletedCount());
		assertEquals(0, myTaskList.getTasks().size());
	}
}
