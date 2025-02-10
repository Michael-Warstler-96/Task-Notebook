package edu.ncsu.csc216.wolf_tasks.model.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import edu.ncsu.csc216.wolf_tasks.model.tasks.TaskList;
import edu.ncsu.csc216.wolf_tasks.model.util.ISortedList;

/**
 * Saves contents of a Notebook to a file location.
 * 
 * @author Michael Warstler
 */
public class NotebookWriter {

	/**
	 * Writes contents of a Notebook to a file. Utilizes a Task's toString() method to properly format output.
	 * 
	 * @param fileName     is name of file to write/save to.
	 * @param notebookName is the name of the Notebook object.
	 * @param taskLists    is the lists of TaskLists in the Notebook.
	 * @throws IllegalArgumentException if unable to save to file.
	 */
	public static void writeNotebookFile(File fileName, String notebookName, ISortedList<TaskList> taskLists) {
		try {
			PrintStream output = new PrintStream(fileName);
			
			// Set up first line of output file.
			output.print("! " + notebookName + "\n");
			
			// Go through each TaskList
			for (int i = 0; i < taskLists.size(); i++) {
				// TaskList header
				TaskList taskList = taskLists.get(i);
				output.print("# " + taskList.getTaskListName() + "," + taskList.getCompletedCount() + "\n");
				// Go through each Task from the TaskList
				for (int k = 0; k < taskList.getTasks().size(); k++) {
					output.print(taskList.getTasks().get(k).toString() + "\n");
				}
			}
			output.close();
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to save file.");
		}
	}
}
