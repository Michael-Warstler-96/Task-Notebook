package edu.ncsu.csc216.wolf_tasks.model.tasks;

import edu.ncsu.csc216.wolf_tasks.model.util.ISwapList;
import edu.ncsu.csc216.wolf_tasks.model.util.SwapList;

/**
 * Abstract class that is the top of the hierarchy for task lists. The
 * AbstractTaskLists knows its taskListName, the ISwapList of Task objects, and
 * the number of completed tasks. Can add/remove task from a list, return a task
 * from a list, complete a task in a list, and get tasks as an array.
 * 
 * @author Michael Warstler
 */
public abstract class AbstractTaskList {

	/** Name of Task List */
	private String taskListName;
	/** Counter of completed tasks */
	private int completedCount;
	/** ISwapList of task objects */
	private ISwapList<Task> tasks;

	/**
	 * Constructor for AbstractTaskList. Sets fields for taskListName and
	 * completedCount. Creates a SwapList of Task objects.
	 * 
	 * @param taskListName   of task list to set.
	 * @param completedCount of completed objects.
	 * @throws IllegalArgumentException if taskListName parameter is null or empty,
	 *                                  or if completedCount parameter is less than
	 *                                  0.
	 */
	public AbstractTaskList(String taskListName, int completedCount) {
		// Check exception.
		if (completedCount < 0) {
			throw new IllegalArgumentException("Invalid completed count.");
		}

		// Set fields and construct a SwapList for the Tasks.
		setTaskListName(taskListName);
		this.completedCount = completedCount;
		tasks = new SwapList<Task>();
	}

	/**
	 * Provides the taskListName field.
	 * 
	 * @return is taskListName.
	 */
	public String getTaskListName() {
		return taskListName;
	}

	/**
	 * Sets the taskListName field.
	 * 
	 * @param taskListName is string to attempt to set as the field taskListName.
	 */
	public void setTaskListName(String taskListName) {
		// Check parameters
		if (taskListName == null || "".equals(taskListName)) {
			throw new IllegalArgumentException("Invalid name.");
		}
		this.taskListName = taskListName;
	}

	/**
	 * Gets the SwapList of task objects.
	 * 
	 * @return is the tasks field.
	 */
	public ISwapList<Task> getTasks() {
		return tasks;
	}

	/**
	 * Gets the number of completed tasks.
	 * 
	 * @return is completedCount field.
	 */
	public int getCompletedCount() {
		return completedCount;
	}

	/**
	 * Adds the Task to the END of the list. Current instance of the TaskList adds
	 * itself to the task.
	 * 
	 * @param t is task object to add.
	 */
	public void addTask(Task t) {
		// Add task to the AbstractTaskList. (added to end)
		tasks.add(t);

		// Add this instance of this (Abstract)TaskList to (be registered) to the Task
		tasks.get(tasks.size() - 1).addTaskList(this); // ensures TaskList always goes to last task.
	}

	/**
	 * Removes a Task from the list of tasks and returns the removed task.
	 * 
	 * @param idx is index of list.
	 * @return is the task that was removed from the list.
	 */
	public Task removeTask(int idx) {
		return tasks.remove(idx);
	}

	/**
	 * Returns the Task object at the given index.
	 * 
	 * @param idx of list to get task from.
	 * @return is Task at the idx param from the task list.
	 */
	public Task getTask(int idx) {
		return tasks.get(idx);
	}

	/**
	 * Finds the given Task in the list and removes it. The completedCount field is
	 * incremented.
	 * 
	 * @param t is the task to complete.
	 */
	public void completeTask(Task t) {
		// Find task to remove
		for (int i = 0; i < tasks.size(); i++) {
			if (t == tasks.get(i)) {
				removeTask(i); // param == task in list, remove at this index.
			}
		}
		completedCount++;
	}

	/**
	 * Returns a 2D string array. Contents are defined by child classes.
	 * 
	 * @return is a 2D array.
	 */
	public abstract String[][] getTasksAsArray();
}
