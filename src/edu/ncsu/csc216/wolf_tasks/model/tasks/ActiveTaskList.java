package edu.ncsu.csc216.wolf_tasks.model.tasks;

/**
 * Represents the ActiveTaskList, a type of AbstractTaskList only containing
 * Tasks that are "active" status. Contains all AbstractTaskList functionality,
 * except ActiveTaskList has a unique name...
 * 
 * @author Michael Warstler
 */
public class ActiveTaskList extends AbstractTaskList {

	/** ActiveTaskList name */
	public static final String ACTIVE_TASKS_NAME = "Active Tasks";

	/**
	 * Constructor for ActiveTaskList. Calls on super/parent, sets the taskListName
	 * to the constant and completed tasks to 0.
	 */
	public ActiveTaskList() {
		super(ACTIVE_TASKS_NAME, 0);
	}

	/**
	 * Overrides the method to check that the Task is active before adding to the
	 * end of the ISwapList.
	 * 
	 * @param t is the Task object to attempt to add.
	 * @throws IllegalArgumentException if the Task is not active.
	 */
	@Override
	public void addTask(Task t) {
		if (!t.isActive()) {
			throw new IllegalArgumentException("Cannot add task to Active Tasks.");
		} else {
			super.addTask(t);
		}
	}

	/**
	 * Overrides the super's method to ensure that the parameter value matches the
	 * expected name (constant). If param matches constant, then name is set through
	 * super, otherwise exception is thrown.
	 * 
	 * @param taskListName is name of task list to attempt to set.
	 * @throws IllegalArgumentException if the parameter does not match the
	 *                                  ACTIVE_TASKS_NAME constant.
	 */
	@Override
	public void setTaskListName(String taskListName) {
		if (taskListName.equals(ACTIVE_TASKS_NAME)) {
			super.setTaskListName(taskListName);
		} else {
			throw new IllegalArgumentException("The Active Tasks list may not be edited.");
		}
	}

	/**
	 * Provides a 2D String array where the first column is the name of the TaskList
	 * that the Task belongs to (or at least the TaskList at index 0) and the name
	 * of the Task.
	 * 
	 * @return is 2D String representation of Tasks in the ActiveTaskList.
	 */
	@Override
	public String[][] getTasksAsArray() {
		// Create 2D string array
		String[][] taskArray = new String[getTasks().size()][2];

		// For each Task in the ActiveTaskList, get the TaskListName and TaskName
		// associated.
		for (int i = 0; i < taskArray.length; i++) {
			taskArray[i][0] = getTasks().get(i).getTaskListName();
			taskArray[i][1] = getTasks().get(i).getTaskName();
		}
		return taskArray;
	}

	/**
	 * Clears the ActiveTaskList of all Task objects.
	 */
	public void clearTasks() {
		// Loop through tasks and continue to remove until none left.
		while(getTasks().size() != 0) {
			super.removeTask(0);	// removes the first index every time.
		}
	}
}
