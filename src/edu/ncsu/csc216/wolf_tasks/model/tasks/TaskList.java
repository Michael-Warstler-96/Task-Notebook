package edu.ncsu.csc216.wolf_tasks.model.tasks;

/**
 * Child class of the AbstractTaskList class. Represents a generic TaskList
 * holding Task objects, that is NOT the ActiveTaskList.
 * 
 * @author Michael Warstler
 */
public class TaskList extends AbstractTaskList implements Comparable<TaskList> {

	/**
	 * Constructs a TaskList by calling the super constructor.
	 * 
	 * @param taskListName   of TaskList.
	 * @param completedCount is number of completed tasks in the list.
	 */
	public TaskList(String taskListName, int completedCount) {
		super(taskListName, completedCount);
	}

	/**
	 * Returns a 2D String array where first column is priority of the Task,
	 * starting at 1, and the name of the Task.
	 * 
	 * @return is 2D String array representation of Tasks objects in the TaskList.
	 */
	public String[][] getTasksAsArray() {
		// Create array with # rows equal to size of tasks array and # columns = 2.
		String[][] tasksArray = new String[getTasks().size()][2];

		// Create 2D array by cycling through Tasks in Task list.
		for (int i = 0; i < tasksArray.length; i++) {
			// Row i, column 1 is just priority/order of Tasks in task list.
			tasksArray[i][0] = String.valueOf(i + 1); // Starts at 1.
			// Row i, column 2 is name of the Task.
			tasksArray[i][1] = getTasks().get(i).getTaskName(); // Task name at Task list i index.
		}
		return tasksArray;
	}

	/**
	 * Compares the names of the TaskLists and determines which is "ordered" first
	 * alphabetically. If this.TaskList's name comes "before" the parameter's, then
	 * the return is -1. If this.TaskList's name comes "after" the parameter, then
	 * return is 1. The return is 0 if this.TaskList's name is the same as the
	 * parameter's.
	 * 
	 * @param taskList is the TaskList object to check/compare the TaskList name
	 *                 with.
	 * @return is -1, 1, or 0.
	 */
	@Override
	public int compareTo(TaskList taskList) {
		// This comes "before" param. Invoke String compareTo.
		if (getTaskListName().compareTo(taskList.getTaskListName()) < 0) {
			return -1;
		}
		// This comes "after" param. Invoke String compareTo.
		else if (getTaskListName().compareTo(taskList.getTaskListName()) > 0) {
			return 1;
		}
		else {
			return 0;	// this TaskList and param TaskList have same name.
		}
	}
}
