package edu.ncsu.csc216.wolf_tasks.model.tasks;

import edu.ncsu.csc216.wolf_tasks.model.util.ISwapList;
import edu.ncsu.csc216.wolf_tasks.model.util.SwapList;

/**
 * Contains information about each individual task including its name,
 * description, recurring and active status. Also contains an ISwapList of
 * AbstractTaskList(s). Task objects know which AbstractTaskList they belong to.
 * Follows Observer Design Pattern with the TaskList class.
 * 
 * @author Michael Warstler
 */
public class Task implements Cloneable {

	/** Task's name */
	private String taskName;
	/** Tasks' description */
	private String taskDescription;
	/** Task's recurring status */
	private boolean recurring;
	/** Task's active status */
	private boolean active;
	/** TaskList that the Task "belongs" to */
	private ISwapList<AbstractTaskList> taskLists;

	/**
	 * Constructor of a Task object. Sets name, details, recurring, and active
	 * fields. Sets taskLists to an empty swapList of AbstractTaskList(s).
	 * 
	 * @param taskName    of Task.
	 * @param taskDetails of Task.
	 * @param recurring   status for a Task.
	 * @param active      status for a Task.
	 */
	public Task(String taskName, String taskDetails, boolean recurring, boolean active) {
		setTaskName(taskName);
		setTaskDescrption(taskDetails);
		setRecurring(recurring);
		setActive(active);
		taskLists = new SwapList<AbstractTaskList>();
	}

	/**
	 * Gets the name of the Task
	 * 
	 * @return is the taskName field.
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * Sets the taskName field.
	 * 
	 * @param taskName is name of Task to set.
	 * @throws IllegalArgumentException if parameter is null or empty.
	 */
	public void setTaskName(String taskName) {
		// Check exception.
		if (taskName == null || "".equals(taskName)) {
			throw new IllegalArgumentException("Incomplete task information.");
		}
		this.taskName = taskName;
	}

	/**
	 * Gets the Task's description.
	 * 
	 * @return is taskDescription field.
	 */
	public String getTaskDescription() {
		return taskDescription;
	}

	/**
	 * Sets the taskDescription field.
	 * 
	 * @param taskDescription are details of Task to set.
	 * @throws IllegalArgumentException if parameter is null.
	 */
	public void setTaskDescrption(String taskDescription) {
		// Check exception.
		if (taskDescription == null) {
			throw new IllegalArgumentException("Incomplete task information.");
		}
		this.taskDescription = taskDescription;
	}

	/**
	 * Provides the recurring status of the Task object.
	 * 
	 * @return is recurring field. True if recurring, false otherwise.
	 */
	public boolean isRecurring() {
		return recurring;
	}

	/**
	 * Sets the recurring status of the Task.
	 * 
	 * @param recurring is true if recurring, false otherwise.
	 */
	public void setRecurring(boolean recurring) {
		this.recurring = recurring;
	}

	/**
	 * Provides the active status of the Task object.
	 * 
	 * @return is active field. True if active, false otherwise.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the active status of the Task.
	 * 
	 * @param active is true if active, false otherwise.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Returns a copy of the Task.
	 * 
	 * @return is a copy of this Task object.
	 * @throws CloneNotSupportedException if there are no AbstractTaskLists
	 *                                    registered with the Task.
	 */
	public Object clone() throws CloneNotSupportedException {
		// Check if there are no AbstractTaskLists registered with the task
		if (taskLists.size() == 0) {
			throw new CloneNotSupportedException("Cannot clone.");
		}

		// Create a clone of this Task.
		Task clonedTask = new Task(taskName, taskDescription, recurring, active);

		// Add each taskList object stored in taskLists field to the cloned Task.
		for (int i = 0; i < taskLists.size(); i++) {
			clonedTask.addTaskList(taskLists.get(i));
		}
		return clonedTask;
	}

	/**
	 * Returns a String representation of the Task for printing to a file. A task
	 * follows the format of * taskName,recurring,active\ntaskDescription
	 * 
	 * @return is String representation of Task object.
	 */
	public String toString() {
		// Different outputs depending on active/recurring status.
		String initialTaskString = "* " + taskName;
		if (active && recurring) {
			return initialTaskString + ",recurring,active\n" + taskDescription;
		} else if (active && !recurring) {
			return initialTaskString + ",active\n" + taskDescription;
		} else if (!active && recurring) {
			return initialTaskString + ",recurring\n" + taskDescription;
		} else {
			return initialTaskString + "\n" + taskDescription;
		}
	}

	/**
	 * Gets the name of the AbstractTaskList at index 0. If the taskLists field is
	 * null or empty, an empty string is returned.
	 * 
	 * @return is the name of the AbstractTaskList at index 0 or empty string.
	 */
	public String getTaskListName() {
		// Check for null or empty taskLists field.
		if (taskLists == null || taskLists.size() == 0) {
			return "";
		}

		// Return value at index 0.
		return taskLists.get(0).getTaskListName();
	}

	/**
	 * If the AbstractTaskList is NOT already registered with the Task object, then
	 * the parameter is added to the end of the taskLists field.
	 * 
	 * @param taskList is AbstractTaskList to add to taskLists field.
	 * @throws IllegalArgumentException if parameter is null
	 */
	public void addTaskList(AbstractTaskList taskList) {
		// Check exception.
		if (taskList == null) {
			throw new IllegalArgumentException("Incomplete task information.");
		}

		// See if param is already in the registered taskLists field for this Task.
		boolean alreadyRegistered = false;
		for (int i = 0; i < taskLists.size(); i++) {
			if (taskLists.get(i) == taskList) {
				alreadyRegistered = true;
			}
		}

		// Only register parameter with the Task, if it has not been registered before.
		if (!alreadyRegistered) {
			taskLists.add(taskList);
		}
	}

	/**
	 * Completes the Task object and notify the taskLists by sharing the current
	 * Task instance via the TaskList.completeTask(Task) method. If the task is
	 * recurring, the Task is cloned and the cloned Task is added to each registered
	 * AbstractTaskList.
	 * 
	 * @throws CloneNotSupportedException if unable to clone the recurring task.
	 */
	public void completeTask() {
		// Cycle through taskLists[] and call on each TaskList.completeTask to complete
		for (int i = 0; i < taskLists.size(); i++) {
			taskLists.get(i).completeTask(this); // send current Task.
		}

		// If recurring, clone and add back to each registered AbstractTaskList
		if (recurring) {
			try {
				Task clonedTask = (Task) clone();
				for (int k = 0; k < taskLists.size(); k++) {
					taskLists.get(k).addTask(clonedTask);
				}
			} catch (CloneNotSupportedException e) {
				// TODO - do something? Getting green Jenkins ball...
			}
		}
	}
}
