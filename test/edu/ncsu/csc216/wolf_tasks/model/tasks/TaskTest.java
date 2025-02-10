package edu.ncsu.csc216.wolf_tasks.model.tasks;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test the Task class.
 * 
 * @author Michael Warstler
 */
public class TaskTest {

	/** Task Name */
	public static final String NAME = "Project";
	/** Task description */
	public static final String DESCRIPTION = "Finish Project 2";

	/**
	 * Test constructor for Task and simple getters.
	 */
	@Test
	public void testTask() {

		// Construct valid Task
		Task myTask = new Task(NAME, DESCRIPTION, false, false);
		assertEquals(NAME, myTask.getTaskName());
		assertEquals(DESCRIPTION, myTask.getTaskDescription());
		assertFalse(myTask.isRecurring());
		assertFalse(myTask.isActive());

		// Test exceptions through construction
		assertThrows(IllegalArgumentException.class, () -> new Task(null, DESCRIPTION, false, false));
		assertThrows(IllegalArgumentException.class, () -> new Task("", DESCRIPTION, false, false));
		assertThrows(IllegalArgumentException.class, () -> new Task(NAME, null, false, false));
	}

	/**
	 * Test setters outside of constructor.
	 */
	@Test
	public void testSetters() {
		// Construct valid Task
		Task myTask = new Task(NAME, DESCRIPTION, false, false);

		// Set name
		myTask.setTaskName("Job");
		assertEquals("Job", myTask.getTaskName());

		// Set description
		myTask.setTaskDescrption("Pizza Delivery");
		assertEquals("Pizza Delivery", myTask.getTaskDescription());

		// Set recurring
		myTask.setRecurring(true);
		assertTrue(myTask.isRecurring());

		// Set active
		myTask.setActive(true);
		assertTrue(myTask.isActive());
	}

	/**
	 * Test toString() method.
	 */
	@Test
	public void testToString() {
		// Construct valid Task (not active, nor recurring)
		Task myTask = new Task(NAME, DESCRIPTION, false, false);
		assertEquals("* " + NAME + "\n" + DESCRIPTION, myTask.toString());

		// Construct valid Task (not active, nor recurring)
		Task myTask1 = new Task(NAME, DESCRIPTION, true, false);
		assertEquals("* " + NAME + ",recurring\n" + DESCRIPTION, myTask1.toString());

		// Construct valid Task (not active, nor recurring)
		Task myTask2 = new Task(NAME, DESCRIPTION, false, true);
		assertEquals("* " + NAME + ",active\n" + DESCRIPTION, myTask2.toString());

		// Construct valid Task (not active, nor recurring)
		Task myTask3 = new Task(NAME, DESCRIPTION, true, true);
		assertEquals("* " + NAME + ",recurring,active\n" + DESCRIPTION, myTask3.toString());
	}

	/**
	 * Test addTaskList() and getTaskListName methods.
	 */
	@Test
	public void testAddTaskList() {
		// Test getting the TaskList name from the Task
		// Create valid AbstractTaskList and Task.
		AbstractTaskList myTaskList = new TaskList("My List", 0);
		Task myTask = new Task(NAME, DESCRIPTION, false, false);
		myTaskList.addTask(myTask);

		// Get the name of the TaskList registered with the Task. (Always gets index 0).
		assertEquals("My List", myTask.getTaskListName());

		// Create additional TaskList that gets registered to this Task.
		AbstractTaskList anotherTaskList = new TaskList("Work Responsibilities", 3);
		anotherTaskList.addTask(myTask);
		// Confirm that getTaskListName() is the same
		assertEquals("My List", myTask.getTaskListName());

		// Test exceptions
		assertThrows(IllegalArgumentException.class, () -> myTask.addTaskList(null));

		// Confirm empty string return for getting a TaskListName for a Task with no
		// TaskList.
		Task anotherTask = new Task("Job", "Pizza Delivery", false, false);
		assertEquals("", anotherTask.getTaskListName());
	}

	/**
	 * Test the clone() method.
	 */
	@Test
	public void testClone() {
		// Create valid task
		Task myTask = new Task(NAME, DESCRIPTION, false, false);

		// check exception
		assertThrows(CloneNotSupportedException.class, () -> myTask.clone());

		// Create TaskList objects to be registered with this Task
		AbstractTaskList myTaskList = new TaskList("My List", 0);
		AbstractTaskList anotherTaskList = new TaskList("School Responsibilities", 3);
		myTaskList.addTask(myTask);
		anotherTaskList.addTask(myTask);

		// Clone and check values
		try {
			Task clonedTask = (Task) myTask.clone();
			assertEquals(NAME, clonedTask.getTaskName());
			assertEquals(DESCRIPTION, clonedTask.getTaskDescription());
			assertFalse(clonedTask.isRecurring());
			assertFalse(clonedTask.isActive());
			assertEquals("My List", clonedTask.getTaskListName());
		} catch (CloneNotSupportedException e) {
			fail("Should be able to clone");
		}
	}

	/**
	 * Test the complete() method through a Task.
	 */
	@Test
	public void testComplete() {
		// Create valid Tasks
		Task myTask1 = new Task(NAME, DESCRIPTION, false, false); // NOT recurring
		Task myTask2 = new Task("Exercise", "Go for a walk", true, true); // RECURRING
		Task myTask3 = new Task("Piano Practice", "Starts at 5:30", true, false); // RECURRING
		Task myTask4 = new Task("Job", "Pizza delivery", false, false); // NOT recurring.

		// Create TaskList objects to be registered with Tasks.
		AbstractTaskList myTaskList = new TaskList("My List", 0);
		AbstractTaskList anotherTaskList = new TaskList("School Responsibilities", 3);

		// Add Tasks to the two TaskLists.
		myTaskList.addTask(myTask1);
		anotherTaskList.addTask(myTask1);
		myTaskList.addTask(myTask2);
		anotherTaskList.addTask(myTask2);
		myTaskList.addTask(myTask3);
		anotherTaskList.addTask(myTask3);
		myTaskList.addTask(myTask4);
		anotherTaskList.addTask(myTask4);

		// Confirm size, completed count, and Task order of both TaskList prior to
		// completing.
		assertEquals(4, myTaskList.getTasks().size());
		assertEquals(4, anotherTaskList.getTasks().size());
		assertEquals(0, myTaskList.getCompletedCount());
		assertEquals(3, anotherTaskList.getCompletedCount());
		assertEquals(NAME, myTaskList.getTask(0).getTaskName());
		assertEquals(NAME, anotherTaskList.getTask(0).getTaskName());
		assertEquals("Exercise", myTaskList.getTask(1).getTaskName());
		assertEquals("Exercise", anotherTaskList.getTask(1).getTaskName());
		assertEquals("Piano Practice", myTaskList.getTask(2).getTaskName());
		assertEquals("Piano Practice", anotherTaskList.getTask(2).getTaskName());
		assertEquals("Job", myTaskList.getTask(3).getTaskName());
		assertEquals("Job", anotherTaskList.getTask(3).getTaskName());

		// Complete first Task. (Not recurring) and check for updates in each TaskList.
		myTask1.completeTask();
		assertEquals(3, myTaskList.getTasks().size());
		assertEquals(3, anotherTaskList.getTasks().size());
		assertEquals(1, myTaskList.getCompletedCount());
		assertEquals(4, anotherTaskList.getCompletedCount());
		assertEquals("Exercise", myTaskList.getTask(0).getTaskName());
		assertEquals("Exercise", anotherTaskList.getTask(0).getTaskName());
		assertEquals("Piano Practice", myTaskList.getTask(1).getTaskName());
		assertEquals("Piano Practice", anotherTaskList.getTask(1).getTaskName());
		assertEquals("Job", myTaskList.getTask(2).getTaskName());
		assertEquals("Job", anotherTaskList.getTask(2).getTaskName());
		
		// Complete middle Task. (Recurring) and check for updates in each TaskList
		myTask3.completeTask();
		// Size should not change due to Recurring Task being cloned and added again.
		assertEquals(3, myTaskList.getTasks().size());
		assertEquals(3, anotherTaskList.getTasks().size());
		// Completed should increment
		assertEquals(2, myTaskList.getCompletedCount());
		assertEquals(5, anotherTaskList.getCompletedCount());
		// Confirm completed recurring Task has moved to end of list.
		assertEquals("Exercise", myTaskList.getTask(0).getTaskName());
		assertEquals("Exercise", anotherTaskList.getTask(0).getTaskName());
		assertEquals("Job", myTaskList.getTask(1).getTaskName());
		assertEquals("Job", anotherTaskList.getTask(1).getTaskName());
		assertEquals("Piano Practice", myTaskList.getTask(2).getTaskName());
		assertEquals("Piano Practice", anotherTaskList.getTask(2).getTaskName());
	}
}
