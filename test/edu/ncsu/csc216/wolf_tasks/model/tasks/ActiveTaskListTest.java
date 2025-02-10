package edu.ncsu.csc216.wolf_tasks.model.tasks;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test the ActiveTaskList class.
 * 
 * @author Michael Warstler
 */
public class ActiveTaskListTest {

	/** Task Name */
	public static final String NAME = "Project";
	/** Task description */
	public static final String DESCRIPTION = "Finish Project 2";

	/**
	 * Test constructor for ActiveTaskList.
	 */
	@Test
	public void testActiveTaskList() {
		// Create ActiveTaskList and confirm fields.
		ActiveTaskList myActiveList = new ActiveTaskList();
		assertEquals("Active Tasks", myActiveList.getTaskListName());
		assertEquals(0, myActiveList.getCompletedCount());
		assertEquals(0, myActiveList.getTasks().size());
	}

	/**
	 * Test addTask() method for ActiveTaskList.
	 */
	@Test
	public void testAddTask() {
		// Create ActiveTaskList and confirm fields.
		ActiveTaskList myActiveList = new ActiveTaskList();

		// Construct valid Tasks
		Task myTask1 = new Task(NAME, DESCRIPTION, false, false); // should not add
		Task myTask2 = new Task("Exercise", "Go for a walk", true, true); // should add
		Task myTask3 = new Task("Piano Practice", "Starts at 5:30", true, false); // should not
		Task myTask4 = new Task("Job", "Pizza delivery", false, true); // should add

		// Test invalid Tasks
		assertThrows(IllegalArgumentException.class, () -> myActiveList.addTask(myTask1));
		assertThrows(IllegalArgumentException.class, () -> myActiveList.addTask(myTask3));

		// Test adding valid active Tasks
		myActiveList.addTask(myTask2);
		// Check size and values. New Tasks added to back of TaskList.
		assertEquals(1, myActiveList.getTasks().size());
		assertEquals("Exercise", myActiveList.getTask(0).getTaskName());
		// Add another Task and check ordering.
		myActiveList.addTask(myTask4);
		assertEquals(2, myActiveList.getTasks().size());
		assertEquals("Exercise", myActiveList.getTask(0).getTaskName());
		assertEquals("Job", myActiveList.getTask(1).getTaskName());
	}

	/**
	 * Test setTaskListName() method for an ActiveTaskList.
	 */
	@Test
	public void testSetTaskListName() {
		// Create ActiveTaskList and confirm fields.
		ActiveTaskList myActiveList = new ActiveTaskList();

		// Try to overwrite the name
		assertThrows(IllegalArgumentException.class, () -> myActiveList.setTaskListName("A new name"));

		// Confirm no throw case
		myActiveList.setTaskListName("Active Tasks");
		assertEquals("Active Tasks", myActiveList.getTaskListName());
	}

	/**
	 * Test getTasksAsArray() method.
	 */
	@Test
	public void testGetTasksAsArray() {
		// Create valid TaskLists.
		TaskList myTaskList1 = new TaskList("List 1", 0);
		TaskList myTaskList2 = new TaskList("List 2", 0);
		TaskList myTaskList3 = new TaskList("List 3", 0);

		// Construct valid ACTIVE Tasks
		Task myTask1 = new Task(NAME, DESCRIPTION, false, true);
		Task myTask2 = new Task("Exercise", "Go for a walk", true, true);
		Task myTask3 = new Task("Piano Practice", "Starts at 5:30", true, true);
		Task myTask4 = new Task("Job", "Pizza delivery", false, true);

		// Add previous task to different task lists. Must add to standard TaskList
		// first so that index 0 for TaskLists associated with a Task is not the Active
		// List.
		myTaskList1.addTask(myTask1);
		myTaskList1.addTask(myTask2);
		myTaskList2.addTask(myTask3);
		myTaskList3.addTask(myTask4);

		// Create ActiveTaskList
		ActiveTaskList myActiveList = new ActiveTaskList();

		// Add the tasks to the ActiveTaskList
		myActiveList.addTask(myTask1);
		myActiveList.addTask(myTask2);
		myActiveList.addTask(myTask3);
		myActiveList.addTask(myTask4);

		// Create 2D array and check values
		String[][] activeTasksArray = myActiveList.getTasksAsArray();
		assertEquals("List 1", activeTasksArray[0][0]);
		assertEquals(NAME, activeTasksArray[0][1]);
		assertEquals("List 1", activeTasksArray[1][0]);
		assertEquals("Exercise", activeTasksArray[1][1]);
		assertEquals("List 2", activeTasksArray[2][0]);
		assertEquals("Piano Practice", activeTasksArray[2][1]);
		assertEquals("List 3", activeTasksArray[3][0]);
		assertEquals("Job", activeTasksArray[3][1]);
	}

	/**
	 * Test for clearTasks() method.
	 */
	@Test
	public void testClearTasks() {
		// Construct valid ACTIVE Tasks
		Task myTask1 = new Task(NAME, DESCRIPTION, false, true);
		Task myTask2 = new Task("Exercise", "Go for a walk", true, true);
		Task myTask3 = new Task("Piano Practice", "Starts at 5:30", true, true);
		Task myTask4 = new Task("Job", "Pizza delivery", false, true);

		// Create ActiveTaskList
		ActiveTaskList myActiveList = new ActiveTaskList();

		// Add the tasks to the ActiveTaskList
		myActiveList.addTask(myTask1);
		myActiveList.addTask(myTask2);
		myActiveList.addTask(myTask3);
		myActiveList.addTask(myTask4);
		
		// Confirm size
		assertEquals(4, myActiveList.getTasks().size());
		
		// Clear Tasks. Confirm size goes to zero.
		myActiveList.clearTasks();
		assertEquals(0, myActiveList.getTasks().size());
	}

}
