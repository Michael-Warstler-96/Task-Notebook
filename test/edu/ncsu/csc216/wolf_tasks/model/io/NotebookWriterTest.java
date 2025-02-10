package edu.ncsu.csc216.wolf_tasks.model.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_tasks.model.notebook.Notebook;

/**
 * Tests the NotebookWriter class.
 * 
 * @author Michael Warstler
 */
public class NotebookWriterTest {

	/**
	 * Test writeNotebookFile() method.
	 */
	@Test
	public void testWriteNotebookFile() {
		try {
			// Create a Notebook with valid (expected) records.
			Notebook notebook = NotebookReader.readNotebookFile(new File("test-files/valid_record.txt"));
			File outputFile = new File("test-files/actual_output.txt");
			
			// Must use Notebook.saveNotebook(File fileName) method to get access to ISortedList of TaskLists.
			notebook.saveNotebook(outputFile);
		} catch (IllegalArgumentException e) {
			fail("Unexpected error reading test-files/valid_record.txt"); // Failed due error reading file.
		}
		
		// Send to comparison method to check if file output matches expected.
		checkFiles("test-files/valid_record.txt", "test-files/actual_output.txt");
	}

	/**
	 * Citing method created from ActivityRecordIO in the WolfScheduler project.
	 * 
	 * Helper method to compare two files for the same contents
	 * 
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	public void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new File(expFile));
				Scanner actScanner = new Scanner(new File(actFile));) {

			// Check that contents of expected matches actual.
			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
}
