package edu.ncsu.csc216.wolf_tasks.model.notebook;

import java.io.File;

import edu.ncsu.csc216.wolf_tasks.model.io.NotebookWriter;
import edu.ncsu.csc216.wolf_tasks.model.tasks.AbstractTaskList;
import edu.ncsu.csc216.wolf_tasks.model.tasks.ActiveTaskList;
import edu.ncsu.csc216.wolf_tasks.model.tasks.Task;
import edu.ncsu.csc216.wolf_tasks.model.tasks.TaskList;
import edu.ncsu.csc216.wolf_tasks.model.util.ISortedList;
import edu.ncsu.csc216.wolf_tasks.model.util.SortedList;

/**
 * Contains an ISortedList of TaskList(s), one ActiveTaskList, and one TaskList
 * representing the currentTaskList, a name, and a boolean flag to keep track if
 * the Notebook has been changed since the last save.
 * 
 * @author Michael Warstler
 */
public class Notebook {

	/** Notebook's name */
	private String notebookName;
	/** Tracks if Notebook has been changed since last save */
	private boolean isChanged;
	/** The SortedList of TaskList objects contained in the Notebook */
	private ISortedList<TaskList> taskLists;
	/** The active task list for the Notebook */
	private ActiveTaskList activeTaskList;
	/**
	 * The current task list for the Notebook (could be an ActiveTaskList or
	 * standard TaskList
	 */
	private AbstractTaskList currentTaskList;

	/**
	 * Constructs a Notebook object with the given name. Field isChanged is set
	 * true. The taskLists field is constructed as a SortedList and the
	 * activeTaskList field is constructed (and set) to the currentTaskList.
	 * 
	 * @param name of Notebook to set.
	 * @throws IllegalArgumentException when name is invalid through
	 *                                  setNoteBookeName().
	 */
	public Notebook(String name) {
		setNotebookName(name);
		isChanged = true;
		taskLists = new SortedList<TaskList>();
		activeTaskList = new ActiveTaskList();
		currentTaskList = activeTaskList;
	}

	/**
	 * Saves the current Notebook to the given file. Field isChanged is changed to
	 * false.
	 * 
	 * @param notebookFile is the file to save notebook to.
	 */
	public void saveNotebook(File notebookFile) {
		NotebookWriter.writeNotebookFile(notebookFile, notebookName, taskLists);
		isChanged = false;
	}

	/**
	 * Gets the Notebook's name.
	 * 
	 * @return is notebookName field.
	 */
	public String getNotebookName() {
		return notebookName;
	}

	/**
	 * Sets the Notebook's name to the parameter.
	 * 
	 * @param name of Notebook to set.
	 * @throws IllegalArgumentException if name is null or empty or matches the
	 *                                  activeTaskList's name.
	 */
	private void setNotebookName(String name) {
		if (name == null || "".equals(name) || name.equals(ActiveTaskList.ACTIVE_TASKS_NAME)) {
			throw new IllegalArgumentException("Invalid name.");
		}
		notebookName = name; 
	}

	/**
	 * Provides the isChanged status for the Notebook.
	 * 
	 * @return is the isChanged field.
	 */
	public boolean isChanged() {
		return isChanged;
	}

	/**
	 * Changes the isChanged status depending on the parameter.
	 * 
	 * @param change is true/false for changed or not.
	 */
	public void setChanged(boolean change) {
		isChanged = change;
	}

	/**
	 * Adds the taskList to the list of task lists. The currentTaskList is updated
	 * to the new task list. The isChanged field is updated to true.
	 * 
	 * @param taskList is the taskList to add.
	 * @throws IllegalArgumentException if the parameter's name matches
	 *                                  activeTaskList's name or is a duplicate of
	 *                                  an existing TaskList (case IN-sensitive).
	 */
	public void addTaskList(TaskList taskList) {
		// Check if param name matches ActiveTaskList name
		if (taskList.getTaskListName().equalsIgnoreCase(ActiveTaskList.ACTIVE_TASKS_NAME)) {
			throw new IllegalArgumentException("Invalid name.");
		}
		// Check if param name matches any name in taskLists.
		if (taskLists.size() != 0) {
			for (int i = 0; i < taskLists.size(); i++) {
				if (taskList.getTaskListName().equalsIgnoreCase(taskLists.get(i).getTaskListName())) {
					throw new IllegalArgumentException("Invalid name.");
				}
			}
		}

		// If no exceptions, add the TaskList object to taskLists field.
		taskLists.add(taskList);
		getActiveTaskList();	// update the ActiveTaskList to include any possible new tasks.
		currentTaskList = taskList; // Update currentTaskList to be param.
		isChanged = true;
	}

	/**
	 * Gets a list of task list names. ActiveTasks list is always first.
	 * 
	 * @return is list of task list names.
	 */
	public String[] getTaskListsNames() {
		String[] nameArray = new String[taskLists.size() + 1]; // +1 for activelist.
																
		// First name is always "Active Tasks
		nameArray[0] = ActiveTaskList.ACTIVE_TASKS_NAME;

		// Remaining names from taskLists field added. (These are already sorted.)
		for (int i = 0; i < taskLists.size(); i++) {
			nameArray[i + 1] = taskLists.get(i).getTaskListName(); 										
		}
		return nameArray;
	}

	/**
	 * Whenever a change occurs, the ActiveTaskList field is cleared and
	 * reconstructed. Active Tasks in the ActiveTaskList are ordered first by the
	 * Task's TaskList name, then the subsequent Active Tasks by priority.
	 */
	private void getActiveTaskList() {
		// Check if there has been a Notebook change.
		if (isChanged) {
			// Clear the existing ActiveTaskList
			activeTaskList.clearTasks();

			// Go through taskLists (SortedList) field
			for (int i = 0; i < taskLists.size(); i++) {
				TaskList list = taskLists.get(i); // get each TaskList object.
				// Cycle through the TaskList object and check each Task if Active.
				for (int k = 0; k < list.getTasks().size(); k++) {
					// Get each Task in the TaskList.
					Task task = list.getTasks().get(k);
					// If a Task is Active, add it to the ActiveTaskList (field/Object).
					if (task.isActive()) {
						activeTaskList.addTask(task);
					}
				}
			}
		}
	}

	/**
	 * Sets the currentTaskList field to the AbstractTaskList with the given
	 * parameter name. If the name parameter is not found, then the currentTaskList
	 * is set to activeTaskList.
	 * 
	 * @param taskListName to set as the currentTaskList.
	 */
	public void setCurrentTaskList(String taskListName) {
		// Track if the currentTaskList has taken the parameter name.
		boolean matchFound = false;

		// Go through the taskLists field.
		for (int i = 0; i < taskLists.size(); i++) {
			// If the param name matches a TaskList name, set that to the currentTaskList
			// field.
			if (taskListName.equals(taskLists.get(i).getTaskListName())) {
				currentTaskList = taskLists.get(i);
				matchFound = true;
			}
		}

		// If no match found in the taskLists field, set current = active.
		if (!matchFound) {
			// Rebuild ActiveTaskList (if needed) and set current = active.
			getActiveTaskList();
			currentTaskList = activeTaskList;
		}
	}

	/**
	 * Gets the currentTaskList.
	 * 
	 * @return is the currentTaskList field.
	 */
	public AbstractTaskList getCurrentTaskList() {
		return currentTaskList;
	}

	/**
	 * Edits the currentTaskList's name with the parameter. Will remove
	 * currentTaskList from SortedList, edit, then add back to taskLists field.
	 * 
	 * @param taskListName is name to try to change currentTaskList to.
	 * @throws IllegalArgumentException if the currentTaskList is an ActiveTaskList
	 *                                  or if the param name matches "Active Tasks"
	 *                                  or is a duplicate of the name of another
	 *                                  TaskList (case insensitive).
	 */
	public void editTaskList(String taskListName) {
		// check exception.
		if (currentTaskList == activeTaskList) {
			throw new IllegalArgumentException("The Active Tasks list may not be edited.");
		}
		// Check if param name matches existing TaskList names
		for (int i = 0; i < taskLists.size(); i++) {
			if (taskListName.equals(taskLists.get(i).getTaskListName())) {
				throw new IllegalArgumentException("Invalid name.");
			}
		}
		// Check if param = "Active Tasks"
		if (taskListName.equals(ActiveTaskList.ACTIVE_TASKS_NAME)) {
			throw new IllegalArgumentException("Invalid name.");
		}

		// Find and remove currentTaskList from taskLists
		for (int k = 0; k < taskLists.size(); k++) {
			// If currentTaskList is found, remove it from the taskLists field.
			if (currentTaskList == taskLists.get(k)) {
				taskLists.remove(k); // removes current from taskLists.
			}
		}

		// Edit the currentTaskList name and add back to taskLists field.
		currentTaskList.setTaskListName(taskListName);
		taskLists.add((TaskList) currentTaskList); // must cast to concrete TaskList to add.
		isChanged = true;
	}

	/**
	 * The currentTaskList is removed from the taskLists field. Then the
	 * currentTaskList is set to the activeTaskList.
	 * 
	 * @throws IllegalArgumentException if the currentTasklist is the
	 *                                  activeTaskList.
	 */
	public void removeTaskList() {
		// check exception.
		if (currentTaskList == activeTaskList) {
			throw new IllegalArgumentException("The Active Tasks list may not be deleted.");
		}

		// Find and remove currentTaskList from taskLists
		for (int k = 0; k < taskLists.size(); k++) {
			// If currentTaskList is found, remove it from the taskLists field.
			if (currentTaskList == taskLists.get(k)) {
				taskLists.remove(k); // removes current from taskLists.
			}
		}

		// Set current to be active and changed to true.
		currentTaskList = activeTaskList;
		isChanged = true;
	}

	/**
	 * Adds a Task object to the currentTaskList. If the Task parameter is active,
	 * the activeTaskList is also updated accordingly. If the currentTaskList is not
	 * a TaskList, nothing is done. (does this use instanceof or do I check if
	 * currentTaskList == activeTaskList)
	 * 
	 * @param t is Task object to add.
	 */
	public void addTask(Task t) {
		// Only add if current is not the ActiveTaskList.
		if (currentTaskList instanceof TaskList) {
			currentTaskList.addTask(t); // add to current task list.
			isChanged = true;
		}
		// Rebuild ActiveTaskList so that edited Task is either added or removed from
		// ActiveTaskList.
		getActiveTaskList();
	}

	/**
	 * Edits fields of a Task with the parameter values. A Task can only be edited
	 * if the currentTaskList is a TaskList, otherwise method does nothing. If the
	 * Task is active, the ActiveTaskList is updated.
	 * 
	 * @param idx             of currentTaskList to edit.
	 * @param taskName        of Task to edit.
	 * @param taskDescription of Task to edit.
	 * @param recurring       status of Task to edit.
	 * @param active          status of Task to edit.
	 */
	public void editTask(int idx, String taskName, String taskDescription, boolean recurring, boolean active) {
		// Only edit if current is not the ActiveTaskList
		if (currentTaskList instanceof TaskList) {
			isChanged = true;
			currentTaskList.getTask(idx).setTaskName(taskName);
			currentTaskList.getTask(idx).setTaskDescrption(taskDescription);
			currentTaskList.getTask(idx).setRecurring(recurring);
			currentTaskList.getTask(idx).setActive(active);
		}
		// Rebuild ActiveTaskList so that edited Task is either added or removed from
		// ActiveTaskList.
		getActiveTaskList();
	}
}
