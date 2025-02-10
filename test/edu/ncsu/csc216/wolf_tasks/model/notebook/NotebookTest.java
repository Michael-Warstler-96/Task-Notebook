package edu.ncsu.csc216.wolf_tasks.model.notebook;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_tasks.model.tasks.Task;
import edu.ncsu.csc216.wolf_tasks.model.tasks.TaskList;

/**
 * Test the Notebook class.
 * 
 * @author Michael Warstler
 */
public class NotebookTest {

	/** Task Name */
	public static final String NAME = "Project";
	/** Task description */
	public static final String DESCRIPTION = "Finish Project 2";
	/** ActiveTaskList name */
	public static final String ACTIVE_TASKS_NAME = "Active Tasks";

	/**
	 * Test constructor for Notebook.
	 */
	@Test
	public void testNotebook() {
		// Create valid Notebook
		Notebook myNotebook = new Notebook("My Notebook");

		// Check values
		assertEquals("My Notebook", myNotebook.getNotebookName());
		assertTrue(myNotebook.isChanged());
		assertEquals(ACTIVE_TASKS_NAME, myNotebook.getCurrentTaskList().getTaskListName());

		// Check exception for Notebook name
		assertThrows(IllegalArgumentException.class, () -> new Notebook(""));
		assertThrows(IllegalArgumentException.class, () -> new Notebook(null));
		assertThrows(IllegalArgumentException.class, () -> new Notebook(ACTIVE_TASKS_NAME));
	}

	/**
	 * Test addTaskList() method.
	 */
	@Test
	public void testAddTaskList() {
		// Create valid Notebook
		Notebook myNotebook = new Notebook("My Notebook");
		myNotebook.setChanged(false); // Set changed to see if adding TaskList changes.
		assertFalse(myNotebook.isChanged());

		// Create valid TaskList.
		TaskList myTaskList = new TaskList("My List", 0);

		// Add TaskList to Notebook, and check fields
		myNotebook.addTaskList(myTaskList);
		assertEquals("My List", myNotebook.getCurrentTaskList().getTaskListName());
		assertTrue(myNotebook.isChanged());

		// Test exceptions
		TaskList badTaskList1 = new TaskList(ACTIVE_TASKS_NAME, 0); // same name as Active.
		assertThrows(IllegalArgumentException.class, () -> myNotebook.addTaskList(badTaskList1));
		TaskList badTaskList2 = new TaskList("My List", 3); // Same name as one already added.
		assertThrows(IllegalArgumentException.class, () -> myNotebook.addTaskList(badTaskList2));
	}

	/**
	 * Test getTaskListsNames() method.
	 */
	@Test
	public void testGetTaskListsNames() {
		// Create valid Notebook
		Notebook myNotebook = new Notebook("My Notebook");

		// Create valid TaskList objects.
		TaskList myTaskList1 = new TaskList("My List", 0);
		TaskList myTaskList2 = new TaskList("Job", 1);
		TaskList myTaskList3 = new TaskList("School", 3);
		TaskList myTaskList4 = new TaskList("Chores", 4);
		TaskList myTaskList5 = new TaskList("Car Work", 0);

		// Add to Notebook. TaskLists should be sorted alphabetically during add.
		myNotebook.addTaskList(myTaskList1); // goes to index 4 of String array.
		myNotebook.addTaskList(myTaskList2); // goes to index 3
		myNotebook.addTaskList(myTaskList3); // goes to index 5
		myNotebook.addTaskList(myTaskList4); // goes to index 2
		myNotebook.addTaskList(myTaskList5); // goes to index 1 after Active List.

		// Assign a String array and check values
		String[] taskListsNamesArray = myNotebook.getTaskListsNames();
		assertEquals(6, taskListsNamesArray.length); // ActiveTaskList is always there.
		assertEquals(ACTIVE_TASKS_NAME, taskListsNamesArray[0]);
		assertEquals("Car Work", taskListsNamesArray[1]);
		assertEquals("Chores", taskListsNamesArray[2]);
		assertEquals("Job", taskListsNamesArray[3]);
		assertEquals("My List", taskListsNamesArray[4]);
		assertEquals("School", taskListsNamesArray[5]);
	}

	/**
	 * Test setCurrentTaskList() method.
	 */
	@Test
	public void testSetCurrentTaskList() {
		// Create valid Notebook
		Notebook myNotebook = new Notebook("My Notebook");
		// Confirm currentTaskList ActiveList from construction.
		assertEquals(ACTIVE_TASKS_NAME, myNotebook.getCurrentTaskList().getTaskListName());

		// Create valid TaskList objects.
		TaskList myTaskList1 = new TaskList("My List", 0);
		TaskList myTaskList2 = new TaskList("Job", 1);
		TaskList myTaskList3 = new TaskList("School", 3);
		TaskList myTaskList4 = new TaskList("Chores", 4);
		TaskList myTaskList5 = new TaskList("Car Work", 0);

		// Add to Notebook.
		myNotebook.addTaskList(myTaskList1);
		myNotebook.addTaskList(myTaskList2);
		myNotebook.addTaskList(myTaskList3);
		myNotebook.addTaskList(myTaskList4);
		myNotebook.addTaskList(myTaskList5);

		// Confirm that the current list is the last one added.
		assertEquals("Car Work", myNotebook.getCurrentTaskList().getTaskListName());

		// Set currentTaskList to a different TaskList
		myNotebook.setCurrentTaskList("My List");
		assertEquals("My List", myNotebook.getCurrentTaskList().getTaskListName());
		myNotebook.setCurrentTaskList("Chores");
		assertEquals("Chores", myNotebook.getCurrentTaskList().getTaskListName());
		myNotebook.setCurrentTaskList("School");
		assertEquals("School", myNotebook.getCurrentTaskList().getTaskListName());
		myNotebook.setCurrentTaskList(ACTIVE_TASKS_NAME);
		assertEquals(ACTIVE_TASKS_NAME, myNotebook.getCurrentTaskList().getTaskListName());

		// Set currentTaskList to a name not found. Check that current becomes active.
		myNotebook.setCurrentTaskList("Unknown list");
		assertEquals(ACTIVE_TASKS_NAME, myNotebook.getCurrentTaskList().getTaskListName());
	}

	/**
	 * Test editTaskList() method.
	 */
	@Test
	public void testEditTaskList() {
		// Create valid Notebook
		Notebook myNotebook = new Notebook("My Notebook");
		// Create valid TaskList objects.
		TaskList myTaskList1 = new TaskList("My List", 0);
		TaskList myTaskList2 = new TaskList("Job", 1);
		TaskList myTaskList3 = new TaskList("School", 3);
		TaskList myTaskList4 = new TaskList("Chores", 4);
		TaskList myTaskList5 = new TaskList("Car Work", 0);

		// Add to Notebook.
		myNotebook.addTaskList(myTaskList1);
		myNotebook.addTaskList(myTaskList2);
		myNotebook.addTaskList(myTaskList3);
		myNotebook.addTaskList(myTaskList4);
		myNotebook.addTaskList(myTaskList5);

		// Attempt to edit ActiveTaskList
		myNotebook.setCurrentTaskList(ACTIVE_TASKS_NAME);
		assertThrows(IllegalArgumentException.class, () -> myNotebook.editTaskList("New List Name"));

		// Attempt to edit currentTaskList with the same name matching existing current
		// or another TaskList.
		myNotebook.setCurrentTaskList("Chores");
		assertThrows(IllegalArgumentException.class, () -> myNotebook.editTaskList("Chores"));
		assertThrows(IllegalArgumentException.class, () -> myNotebook.editTaskList("Car Work"));
		assertThrows(IllegalArgumentException.class, () -> myNotebook.editTaskList("My List"));

		// Edit the currentTaskList and check for fields updating
		myNotebook.setChanged(false);
		myNotebook.editTaskList("New List Name"); // current was previously Chores.
		assertEquals("New List Name", myNotebook.getCurrentTaskList().getTaskListName());
		assertTrue(myNotebook.isChanged());
	}

	/**
	 * Test removeTaskList() method.
	 */
	@Test
	public void testRemoveTaskList() {
		// Create valid Notebook
		Notebook myNotebook = new Notebook("My Notebook");
		// Create valid TaskList objects.
		TaskList myTaskList1 = new TaskList("My List", 0);
		TaskList myTaskList2 = new TaskList("Job", 1);
		TaskList myTaskList3 = new TaskList("School", 3);
		TaskList myTaskList4 = new TaskList("Chores", 4);
		TaskList myTaskList5 = new TaskList("Car Work", 0);

		// Add to Notebook.
		myNotebook.addTaskList(myTaskList1);
		myNotebook.addTaskList(myTaskList2);
		myNotebook.addTaskList(myTaskList3);
		myNotebook.addTaskList(myTaskList4);
		myNotebook.addTaskList(myTaskList5);

		// Confirm that the current list is the last one added.
		assertEquals("Car Work", myNotebook.getCurrentTaskList().getTaskListName());

		// Modify Changed field, and remove the currentTaskList. Confirm current now =
		// Active.
		myNotebook.setChanged(false);
		myNotebook.removeTaskList();
		assertEquals(ACTIVE_TASKS_NAME, myNotebook.getCurrentTaskList().getTaskListName());
		assertTrue(myNotebook.isChanged());

		// Attempt to remove while current = active
		assertThrows(IllegalArgumentException.class, () -> myNotebook.removeTaskList());
	}

	/**
	 * Test addTask() method.
	 */
	@Test
	public void testAddTask() {
		// Create valid Notebook
		Notebook myNotebook = new Notebook("My Notebook");
		assertEquals(0, myNotebook.getCurrentTaskList().getTasks().size()); // current = ActiveList

		// Create valid TaskList objects.
		TaskList myTaskList1 = new TaskList("My List", 0);
		TaskList myTaskList2 = new TaskList("Job", 1);
		TaskList myTaskList3 = new TaskList("School", 3);

		// Add to Notebook and check that initial size is zero.
		myNotebook.setChanged(false);
		myNotebook.addTaskList(myTaskList1);
		assertTrue(myNotebook.isChanged());
		assertEquals(0, myNotebook.getCurrentTaskList().getTasks().size()); // current = myTaskList1
		myNotebook.addTaskList(myTaskList2);
		assertEquals(0, myNotebook.getCurrentTaskList().getTasks().size()); // current = myTaskList2
		myNotebook.addTaskList(myTaskList3);
		assertEquals(0, myNotebook.getCurrentTaskList().getTasks().size()); // current = myTaskList3

		// Construct Task objects to be added to various list.
		Task myTask1 = new Task(NAME, DESCRIPTION, false, false); // not active
		Task myTask2 = new Task("Exercise", "Go for a walk", true, true); // active
		Task myTask3 = new Task("Piano Practice", "Starts at 5:30", true, false); // not active
		Task myTask4 = new Task("Job", "Pizza delivery", false, true); // active
		Task myTask5 = new Task("Yard Work", "Mow the lawn", true, true); // active
		Task myTask6 = new Task("Trip Planning", "Get flights", false, true);

		// Adding Tasks (active and non-active) to myTaskList3 "School" which is also
		// current.
		myNotebook.addTask(myTask1);
		assertEquals(1, myNotebook.getCurrentTaskList().getTasks().size());
		assertEquals(NAME, myNotebook.getCurrentTaskList().getTask(0).getTaskName());
		myNotebook.addTask(myTask2);
		assertEquals(2, myNotebook.getCurrentTaskList().getTasks().size());
		assertEquals(NAME, myNotebook.getCurrentTaskList().getTask(0).getTaskName());
		assertEquals("Exercise", myNotebook.getCurrentTaskList().getTask(1).getTaskName());
		// Check that Active changed
		myNotebook.setCurrentTaskList("");
		assertEquals(1, myNotebook.getCurrentTaskList().getTasks().size());
		assertEquals("Exercise", myNotebook.getCurrentTaskList().getTask(0).getTaskName());

		// Add Tasks (no actives) to myTaskList2
		myNotebook.setCurrentTaskList("Job");
		myNotebook.addTask(myTask1);
		myNotebook.addTask(myTask3);
		assertEquals(2, myNotebook.getCurrentTaskList().getTasks().size());
		assertEquals(NAME, myNotebook.getCurrentTaskList().getTask(0).getTaskName());
		assertEquals("Piano Practice", myNotebook.getCurrentTaskList().getTask(1).getTaskName());
		// Confirm that ActiveList did not change
		myNotebook.setCurrentTaskList("");
		assertEquals(1, myNotebook.getCurrentTaskList().getTasks().size());
		assertEquals("Exercise", myNotebook.getCurrentTaskList().getTask(0).getTaskName());

		// Add Tasks (only actives) to myTaskList1
		myNotebook.setCurrentTaskList("My List");
		myNotebook.addTask(myTask4);
		myNotebook.addTask(myTask5);
		assertEquals(2, myNotebook.getCurrentTaskList().getTasks().size());
		assertEquals("Job", myNotebook.getCurrentTaskList().getTask(0).getTaskName());
		assertEquals("Yard Work", myNotebook.getCurrentTaskList().getTask(1).getTaskName());
		// Check ActiveList - items should be sorted by TaskListName, then Tasks.
		// Active should currently have MyList-Job, MyList-Yard Work, School-Project
		myNotebook.setCurrentTaskList("");
		assertEquals(3, myNotebook.getCurrentTaskList().getTasks().size());
		assertEquals("Job", myNotebook.getCurrentTaskList().getTask(0).getTaskName());
		assertEquals("Yard Work", myNotebook.getCurrentTaskList().getTask(1).getTaskName());
		assertEquals("Exercise", myNotebook.getCurrentTaskList().getTask(2).getTaskName());

		// Confirm nothing happens when trying to add to ActiveList. Current == active
		// from last step.
		myNotebook.addTask(myTask6);
		assertEquals(3, myNotebook.getCurrentTaskList().getTasks().size());
		assertEquals("Job", myNotebook.getCurrentTaskList().getTask(0).getTaskName());
		assertEquals("Yard Work", myNotebook.getCurrentTaskList().getTask(1).getTaskName());
		assertEquals("Exercise", myNotebook.getCurrentTaskList().getTask(2).getTaskName());
	}

	/**
	 * Test editTask() method.
	 */
	@Test
	public void testEditTask() {
		// Create valid Notebook
		Notebook myNotebook = new Notebook("My Notebook");

		// Create valid TaskList objects.
		TaskList myTaskList1 = new TaskList("My List", 0);
		myNotebook.addTaskList(myTaskList1); // myTaskList1 is now current

		// Construct Task objects to be added to various list.
		Task myTask1 = new Task(NAME, DESCRIPTION, false, false); // not active
		Task myTask2 = new Task("Exercise", "Go for a walk", true, true); // active
		Task myTask3 = new Task("Piano Practice", "Starts at 5:30", true, false); // not active
		Task myTask4 = new Task("Job", "Pizza delivery", false, true); // active
		Task myTask5 = new Task("Yard Work", "Mow the lawn", true, true); // active
		Task myTask6 = new Task("Trip Planning", "Get flights", false, true);

		// Add Tasks to TaskList
		myNotebook.addTask(myTask1);
		myNotebook.addTask(myTask2);
		myNotebook.addTask(myTask3);
		myNotebook.addTask(myTask4);
		myNotebook.addTask(myTask5);
		myNotebook.addTask(myTask6);

		// Confirm ActiveList contents.
		myNotebook.setCurrentTaskList("");
		assertEquals(4, myNotebook.getCurrentTaskList().getTasks().size());
		assertEquals("Exercise", myNotebook.getCurrentTaskList().getTask(0).getTaskName());
		assertEquals("Job", myNotebook.getCurrentTaskList().getTask(1).getTaskName());
		assertEquals("Yard Work", myNotebook.getCurrentTaskList().getTask(2).getTaskName());
		assertEquals("Trip Planning", myNotebook.getCurrentTaskList().getTask(3).getTaskName());

		// Try to edit through Active
		myNotebook.setChanged(false);
		myNotebook.editTask(0, "New Task Name", "New Description", false, false);
		assertFalse(myNotebook.isChanged());
		assertEquals(4, myNotebook.getCurrentTaskList().getTasks().size());
		assertEquals("Exercise", myNotebook.getCurrentTaskList().getTask(0).getTaskName());
		assertEquals("Job", myNotebook.getCurrentTaskList().getTask(1).getTaskName());
		assertEquals("Yard Work", myNotebook.getCurrentTaskList().getTask(2).getTaskName());
		assertEquals("Trip Planning", myNotebook.getCurrentTaskList().getTask(3).getTaskName());

		// Change current list to myTaskList1
		myNotebook.setCurrentTaskList("My List");
		assertEquals(6, myNotebook.getCurrentTaskList().getTasks().size());
		assertEquals(NAME, myNotebook.getCurrentTaskList().getTask(0).getTaskName());
		assertEquals("Exercise", myNotebook.getCurrentTaskList().getTask(1).getTaskName());
		assertEquals("Piano Practice", myNotebook.getCurrentTaskList().getTask(2).getTaskName());
		assertEquals("Job", myNotebook.getCurrentTaskList().getTask(3).getTaskName());
		assertEquals("Yard Work", myNotebook.getCurrentTaskList().getTask(4).getTaskName());
		assertEquals("Trip Planning", myNotebook.getCurrentTaskList().getTask(5).getTaskName());

		// Edit first in list. Task becomes Active
		myNotebook.editTask(0, "New Name", "New Description", true, true);
		assertTrue(myNotebook.isChanged());
		assertEquals(6, myNotebook.getCurrentTaskList().getTasks().size());
		assertEquals("New Name", myNotebook.getCurrentTaskList().getTask(0).getTaskName());
		assertEquals("New Description", myNotebook.getCurrentTaskList().getTask(0).getTaskDescription());
		assertTrue(myNotebook.getCurrentTaskList().getTask(0).isRecurring());
		assertTrue(myNotebook.getCurrentTaskList().getTask(0).isActive());
		// Confirm other Tasks in list did no change
		assertEquals("Exercise", myNotebook.getCurrentTaskList().getTask(1).getTaskName());
		assertEquals("Piano Practice", myNotebook.getCurrentTaskList().getTask(2).getTaskName());
		assertEquals("Job", myNotebook.getCurrentTaskList().getTask(3).getTaskName());
		assertEquals("Yard Work", myNotebook.getCurrentTaskList().getTask(4).getTaskName());
		assertEquals("Trip Planning", myNotebook.getCurrentTaskList().getTask(5).getTaskName());
		// Confirm that ActiveList received a new Task
		myNotebook.setCurrentTaskList("");
		assertEquals(5, myNotebook.getCurrentTaskList().getTasks().size());
		assertEquals("New Name", myNotebook.getCurrentTaskList().getTask(0).getTaskName());
		assertEquals("Exercise", myNotebook.getCurrentTaskList().getTask(1).getTaskName());
		assertEquals("Job", myNotebook.getCurrentTaskList().getTask(2).getTaskName());
		assertEquals("Yard Work", myNotebook.getCurrentTaskList().getTask(3).getTaskName());
		assertEquals("Trip Planning", myNotebook.getCurrentTaskList().getTask(4).getTaskName());

		// Edit middle task in list. Task no longer Active
		myNotebook.setCurrentTaskList("My List"); // change back to regular TaskList.
		myNotebook.editTask(3, "Active to not Active", "No Longer Active", false, false); // myTask4
		assertEquals(6, myNotebook.getCurrentTaskList().getTasks().size());
		assertEquals("Active to not Active", myNotebook.getCurrentTaskList().getTask(3).getTaskName());
		assertEquals("No Longer Active", myNotebook.getCurrentTaskList().getTask(3).getTaskDescription());
		assertFalse(myNotebook.getCurrentTaskList().getTask(3).isRecurring());
		assertFalse(myNotebook.getCurrentTaskList().getTask(3).isActive());
		// Confirm other Tasks in list did no change
		assertEquals("New Name", myNotebook.getCurrentTaskList().getTask(0).getTaskName());
		assertEquals("Exercise", myNotebook.getCurrentTaskList().getTask(1).getTaskName());
		assertEquals("Piano Practice", myNotebook.getCurrentTaskList().getTask(2).getTaskName());
		assertEquals("Yard Work", myNotebook.getCurrentTaskList().getTask(4).getTaskName());
		assertEquals("Trip Planning", myNotebook.getCurrentTaskList().getTask(5).getTaskName());
		// Confirm that ActiveList has lost a Task.
		myNotebook.setCurrentTaskList("");
		assertEquals(4, myNotebook.getCurrentTaskList().getTasks().size());
		assertEquals("New Name", myNotebook.getCurrentTaskList().getTask(0).getTaskName());
		assertEquals("Exercise", myNotebook.getCurrentTaskList().getTask(1).getTaskName());
		assertEquals("Yard Work", myNotebook.getCurrentTaskList().getTask(2).getTaskName());
		assertEquals("Trip Planning", myNotebook.getCurrentTaskList().getTask(3).getTaskName());
	}
}
