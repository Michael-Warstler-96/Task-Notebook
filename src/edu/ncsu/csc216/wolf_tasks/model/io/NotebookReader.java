package edu.ncsu.csc216.wolf_tasks.model.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.wolf_tasks.model.notebook.Notebook;
import edu.ncsu.csc216.wolf_tasks.model.tasks.AbstractTaskList;
import edu.ncsu.csc216.wolf_tasks.model.tasks.Task;
import edu.ncsu.csc216.wolf_tasks.model.tasks.TaskList;

/**
 * Handles reading in file contents to create a Notebook object.
 * 
 * @author Michael Warstler
 */
public class NotebookReader {

	/**
	 * Reads in file contents and creates a Notebook object. Utilizes helper methods
	 * processTaskList() and processTask() to simplify process. Invalid task lists
	 * or task (can't be constructed or information is missing) are ignored.
	 * 
	 * @param fileName is name of file to process.
	 * @return is a Notebook object created from the file's contents.
	 * @throws IllegalArgumentException if the file cannot be loaded.
	 * @throws FileNotFoundException    if the file does not exist on the file
	 *                                  system.
	 */
	public static Notebook readNotebookFile(File fileName) {
		try {
			Scanner fileReader = new Scanner(fileName); // Create Scanner for File.
			String fileAsString = ""; // Set up String to hold entire File.
			Notebook notebook; // Set up Notebook object to create and add TaskList (and Task) to.

			// Put entire File contents into a String.
			while (fileReader.hasNextLine()) {
				fileAsString += fileReader.nextLine() + "\n"; // separate lines with newline.
			}
			fileReader.close();
			fileAsString = fileAsString.trim(); // remove unnecessary whitespace.

			// Check that first character is correct (!).
			Scanner s1 = new Scanner(fileAsString);
			String notebookName = "";
			notebookName = s1.next(); // Just grab the '!' first.
			if (notebookName.charAt(0) != '!') {
				s1.close();
				throw new IllegalArgumentException("Unable to load file");
			} else {
				notebookName = s1.nextLine().trim(); // Rest of line 1 goes to NotebookName.
				notebook = new Notebook(notebookName); // Create Notebook with name read from file.
			}

			// Process the rest of fileAsString, while dividing into TaskLists.
			s1.useDelimiter("\\r?\\n?[#]");
			while (s1.hasNext()) {
				TaskList taskList = processTaskList(s1.next()); // process contents of a String representing a TaskList.
				// Only add VALID TaskList
				if (taskList != null) {
					notebook.addTaskList(taskList);
				}
			}
			s1.close();

			// Return the completed Notebook
			notebook.setCurrentTaskList("Active Tasks"); // Sets current tasklist = ActiveTaskList.
			return notebook;

		} catch (FileNotFoundException | IllegalArgumentException e) {
			// Error found with a Notebook or TaskList name, or could not load the file.
			throw new IllegalArgumentException("Unable to load file.");
		}
	}

	/**
	 * Reads in a specific line relating to a taskList and produces a TaskList
	 * object.
	 * 
	 * @param taskListContents is a String representing a taskList from a read in
	 *                         file.
	 * @return is a TaskList object created from the processed parameter or null, if
	 *         a TaskList could not be created.
	 */
	private static TaskList processTaskList(String taskListContents) {
		try {
			// Create Scanner for entire TaskList String
			Scanner taskListScnr = new Scanner(taskListContents);

			// Create scanner and variables specifically for line 1 (TaskListName and
			// completed count)
			String nameLine = taskListScnr.nextLine(); // first line is TaskListName with completed count.
			Scanner nameLineScnr = new Scanner(nameLine); // Used to separate Name and completed.
			nameLineScnr.useDelimiter(",");
			String taskListName = nameLineScnr.next().trim(); // get just the name
			int completedCount = nameLineScnr.nextInt(); // get just the completed count
			nameLineScnr.close(); // No longer need Scanner for line 1.

			// Create a TaskList object from the data read on line 1 of taskListContents.
			TaskList taskList = new TaskList(taskListName, completedCount);

			// Read in rest of taskListContents and create Task objects to be added to this
			// TaskList.
			taskListScnr.useDelimiter("\\r?\\n?[*]");
			while (taskListScnr.hasNext()) {
				// Create Task objects to be added to the TaskList.
				Task task = processTask(taskList, taskListScnr.next());
				// Only add VALID Tasks
				if (task != null) {
					taskList.addTask(task);
				}
			}
			taskListScnr.close();

			// Close TaskList Scanner and return the TaskList object.
			return taskList;

		} catch (NoSuchElementException | IllegalArgumentException e) {
			// Any issues with reading the TaskList return null, so this TaskList is
			// skipped.
			return null;
		}
	}

	/**
	 * Processes a Task from a specific string of a taskList object.
	 * 
	 * @param taskList     is the particular AbstractTaskList that the taskContents
	 *                     would belong to.
	 * @param taskContents is the information of possibly many Tasks in String form
	 *                     from the file.
	 * @return is a Task object created after processing. Return is null if there
	 *         are any issues with reading in a Task.
	 */
	private static Task processTask(AbstractTaskList taskList, String taskContents) {
		try {
			// Create scanner for entire Task String.
			Scanner taskScnr = new Scanner(taskContents);

			// Create scanner and variables specifically for line 1 (TaskName, recurring,
			// active)
			String nameLine = taskScnr.nextLine(); // first line of TaskContents
			Scanner nameLineScnr = new Scanner(nameLine); // Used to separate name, recurring, active
			nameLineScnr.useDelimiter(",");
			String taskName = nameLineScnr.next().trim(); // First token of line 1 for Task is always name.
			// Check for recurring and active. Should only be 2 tokens remaining.
			boolean recurring = false;
			boolean active = false;
			while (nameLineScnr.hasNext()) {
				String value = nameLineScnr.next();
				if ("recurring".equalsIgnoreCase(value)) {
					recurring = true;
				} else if ("active".equalsIgnoreCase(value)) {
					active = true;
				} else {
					nameLineScnr.close();
					taskScnr.close();
					return null; // invalid format.
				}
			}
			nameLineScnr.close();

			// Read in rest of taskContents (contents after line 1 == description)
			String taskDescription = "";
			while (taskScnr.hasNextLine()) {
				taskDescription += taskScnr.nextLine() + "\n";
			}
			taskDescription = taskDescription.trim(); // remove unnecessary whitespace.
			taskScnr.close();

			// Create Task object using name, description, recurring, and active
			Task task = new Task(taskName, taskDescription, recurring, active);
			// Register the TaskList with the new Task and return the Task.
			task.addTaskList(taskList);
			return task;

		} catch (NoSuchElementException | IllegalArgumentException e) {
			// Any issues with reading the Task will return null, so this Task is skipped.
			return null;
		}
	}
}
