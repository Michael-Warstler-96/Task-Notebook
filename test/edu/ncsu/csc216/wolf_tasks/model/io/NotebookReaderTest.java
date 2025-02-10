package edu.ncsu.csc216.wolf_tasks.model.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_tasks.model.notebook.Notebook;
import edu.ncsu.csc216.wolf_tasks.model.tasks.AbstractTaskList;
import edu.ncsu.csc216.wolf_tasks.model.tasks.Task;

/**
 * Test the NotebookReader class.
 * 
 * @author Michael Warstler
 */
public class NotebookReaderTest {

	/**
	 * Test readNotebookFile() method.
	 */
	@Test
	public void testReadNotebookFile() {
		try {
			// Create a notebook
			Notebook notebook = NotebookReader.readNotebookFile(new File("test-files/valid_record.txt"));

			// Check Notebook contents
			assertEquals("School", notebook.getNotebookName());

			// CurrentList in Notebook always be Active first
			AbstractTaskList currentList = notebook.getCurrentTaskList();
			assertEquals("Active Tasks", currentList.getTaskListName());
			assertEquals(5, currentList.getTasks().size());

			// Change currentTasklist and check values.
			notebook.setCurrentTaskList("Habits");
			currentList = notebook.getCurrentTaskList();
			assertEquals(0, currentList.getCompletedCount());
			assertEquals(2, currentList.getTasks().size());

			// Check value in currentTaskList.
			Task exercise = currentList.getTask(0);
			assertEquals("Exercise", exercise.getTaskName());
			assertEquals("Exercise every day. \nAlternate between cardio and weight training",
					exercise.getTaskDescription());
			assertTrue(exercise.isRecurring());
			assertTrue(exercise.isActive());
			assertEquals("Habits", exercise.getTaskListName());

			// Check other TaskLists from file
			// Change currentTasklist and check values.
			notebook.setCurrentTaskList("CSC 216");
			currentList = notebook.getCurrentTaskList();
			assertEquals(35, currentList.getCompletedCount());
			assertEquals(9, currentList.getTasks().size());

			// Change currentTasklist and check values.
			notebook.setCurrentTaskList("CSC 226");
			currentList = notebook.getCurrentTaskList();
			assertEquals(23, currentList.getCompletedCount());
			assertEquals(5, currentList.getTasks().size());
		} catch (IllegalArgumentException e) {
			fail("Unexpected error reading test-files/valid_record.txt"); // Failed due error reading file.
		}
	}

	/**
	 * Test an invalid file.
	 */
	@Test
	public void testInvalidFile() {
		try {
			NotebookReader.readNotebookFile(new File("test-files/notebook3.txt"));
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Unable to load file.", e.getMessage());
		}
	}
}
