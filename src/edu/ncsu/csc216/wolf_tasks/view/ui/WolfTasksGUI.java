package edu.ncsu.csc216.wolf_tasks.view.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import edu.ncsu.csc216.wolf_tasks.model.io.NotebookReader;
import edu.ncsu.csc216.wolf_tasks.model.notebook.Notebook;
import edu.ncsu.csc216.wolf_tasks.model.tasks.AbstractTaskList;
import edu.ncsu.csc216.wolf_tasks.model.tasks.ActiveTaskList;
import edu.ncsu.csc216.wolf_tasks.model.tasks.Task;
import edu.ncsu.csc216.wolf_tasks.model.tasks.TaskList;


/**
 * Container for the WolfTasks project that provides the user the ability
 * to interact with notebooks, task lists, and tasks.
 * 
 * @author Dr. Sarah Heckman
 */
public class WolfTasksGUI extends JFrame implements ActionListener {
	
	/** ID number used for object serialization. */
	private static final long serialVersionUID = 1L;
	/** Title for top of GUI. */
	private static final String APP_TITLE = "WolfTasks";
	/** Text for the File Menu. */
	private static final String FILE_MENU_TITLE = "File";
	/** Text for the New menu item. */
	private static final String NEW_TITLE = "New Notebook";
	/** Text for the Load menu item. */
	private static final String LOAD_TITLE = "Load Notebook";
	/** Text for the Save menu item. */
	private static final String SAVE_TITLE = "Save Notebook";
	/** Text for the Quit menu item. */
	private static final String QUIT_TITLE = "Quit";
	/** Menu bar for the GUI that contains Menus. */
	private JMenuBar menuBar;
	/** Menu for the GUI. */
	private JMenu menu;
	/** Menu item for creating a new notebook. */
	private JMenuItem itemNew;
	/** Menu item for loading a notebook file. */
	private JMenuItem itemLoad;
	/** Menu item for saving a notebook to a file. */
	private JMenuItem itemSave;
	/** Menu item for quitting the program. */
	private JMenuItem itemQuit;
	/** JPanel for the TaskLists */
	private TaskListPanel pnlTaskList;
	/** Border for Notebook and Task Lists */
	private TitledBorder notebookBorder;
	/** JPanel for Tasks */
	private TaskPanel pnlTask;
	
	/** Current notebook - null if no notebook created. */
	private Notebook notebook;
	
	/**
	 * Constructs a ServiceWolfGUI object that will contain a JMenuBar and a
	 * JPanel that will hold different possible views of the data in
	 * the ServiceWolf.
	 */
	public WolfTasksGUI() {
		super();
		
		//Set up general GUI info
		setSize(1200, 700);
		setLocation(50, 50);
		setTitle(APP_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUpMenuBar();
		
		//Add panel to the container
		Container c = getContentPane();
		c.setLayout(new GridBagLayout());
		
		pnlTaskList = new TaskListPanel();
		Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		notebookBorder = BorderFactory.createTitledBorder(lowerEtched, "Notebook");
		pnlTaskList.setBorder(notebookBorder);
		pnlTaskList.setToolTipText("Notebook");
		
		pnlTask = new TaskPanel();
		lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder border = BorderFactory.createTitledBorder(lowerEtched, "Task");
		pnlTask.setBorder(border);
		pnlTask.setToolTipText("Task");
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = .5;
		constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.fill = GridBagConstraints.BOTH;
		c.add(pnlTaskList, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.fill = GridBagConstraints.BOTH;
		c.add(pnlTask, constraints);
		
		//Set the GUI visible
		setVisible(true);
	}
	
	/**
	 * Makes the GUI Menu bar that contains options working with a file
	 * containing service groups and incidents or for quitting the application.
	 */
	private void setUpMenuBar() {
		//Construct Menu items
		menuBar = new JMenuBar();
		menu = new JMenu(FILE_MENU_TITLE);
		itemNew = new JMenuItem(NEW_TITLE);
		itemLoad = new JMenuItem(LOAD_TITLE);
		itemSave = new JMenuItem(SAVE_TITLE);
		itemQuit = new JMenuItem(QUIT_TITLE);
		itemNew.addActionListener(this);
		itemLoad.addActionListener(this);
		itemSave.addActionListener(this);
		itemQuit.addActionListener(this);
		
		//Start with save button disabled
		itemSave.setEnabled(false);
		
		//Build Menu and add to GUI
		menu.add(itemNew);
		menu.add(itemLoad);
		menu.add(itemSave);
		menu.add(itemQuit);
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == itemNew) {
			if (notebook != null && notebook.isChanged()) {
				int select = JOptionPane.showConfirmDialog(null, "Current Notebook is unsaved. Would you like to save before creating a new Notebook?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (select == 1) {
					promptForNotebookName();
				} 
			} else {
				promptForNotebookName();
			}
			
		} else if (e.getSource() == itemLoad) {
			try {
				if (notebook != null && notebook.isChanged()) {
					int select = JOptionPane.showConfirmDialog(null, "Current Notebook is unsaved. Would you like to save before creating a new Notebook?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (select == 1) {
						notebook = NotebookReader.readNotebookFile(new File(getFileName(true)));
						pnlTaskList.updateTaskLists();
					}
				} else {
					notebook = NotebookReader.readNotebookFile(new File(getFileName(true)));
					pnlTaskList.updateTaskLists();
				}
			} catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(this, "Unable to load file.");
			} catch (IllegalStateException ise) {
				//ignore the exception
			}
		} else if (e.getSource() == itemSave) {
			//Save current service group and incidents
			try {
				notebook.saveNotebook(new File(getFileName(false)));
			} catch (IllegalArgumentException exp) {
				JOptionPane.showMessageDialog(this, "Unable to save file.");
			} catch (IllegalStateException exp) {
				//Don't do anything - user canceled (or error)
			}
		} else if (e.getSource() == itemQuit) {
			if (notebook != null && notebook.isChanged()) {
				//Quit the program
				try {
					notebook.saveNotebook(new File(getFileName(false)));
					System.exit(0);  //Ignore SpotBugs warning here - this is the only place to quit the program!
				} catch (IllegalArgumentException exp) {
					JOptionPane.showMessageDialog(this, "Unable to save file.");
				} catch (IllegalStateException exp) {
					//Don't do anything - user canceled (or error)
				}
			} else {
				System.exit(0);
			}
		}
		if (notebook != null) {
			notebookBorder.setTitle("Notebook: " + notebook.getNotebookName());
		}
		
		pnlTaskList.updateTaskLists();
		
		itemSave.setEnabled(notebook != null && notebook.isChanged());
		repaint();
		validate();
		
	}
	
	/**
	 * Prompts the user for the notebook name.
	 */
	private void promptForNotebookName() {
		String notebookName = (String) JOptionPane.showInputDialog(this, "Notebook Name?");
		if (notebookName == null) {
			return; //no need to do anything
		}
		notebook = new Notebook(notebookName);
	}
	
	/**
	 * Returns a file name generated through interactions with a JFileChooser
	 * object.
	 * @param load true if loading a file, false if saving
	 * @return the file name selected through JFileChooser
	 * @throws IllegalStateException if no file name provided
	 */
	private String getFileName(boolean load) {
		JFileChooser fc = new JFileChooser("./");  //Open JFileChooser to current working directory
		int returnVal = Integer.MIN_VALUE;
		if (load) {
			returnVal = fc.showOpenDialog(this);
		} else {
			returnVal = fc.showSaveDialog(this);
		}
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			//Error or user canceled, either way no file name.
			throw new IllegalStateException();
		}
		File gameFile = fc.getSelectedFile();
		return gameFile.getAbsolutePath();
	}
	
	/**
	 * Starts the GUI for the WolfTasks application.
	 * @param args command line arguments
	 */
	public static void main(String [] args) {
		new WolfTasksGUI();
	}
	
	/**
	 * JPanel for TaskList.
	 */
	private class TaskListPanel extends JPanel implements ActionListener {
		
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		
		/** Leading text for completes tasks label */
		private static final String COMPLETED_TASKS_LABEL = "Number of Completed Tasks: ";
		
		/** Label for selecting current service group */
		private JLabel lblActiveTaskList;
		/** Combo box for ServiceGroup list */
		private JComboBox<String> comboTaskLists;
		/** Label for number of completed tasks */
		private JLabel lblCompletedTasks;
		/** Button to add a task list */
		private JButton btnAddTaskList;
		/** Button to edit the selected task list */
		private JButton btnEditTaskList;
		/** Button to remove the selected task list */
		private JButton btnRemoveTaskList;

		/** JTable for displaying the list of tasks */
		private JTable tableTasks;
		/** TableModel for Tasks */
		private TaskTableModel tableModel;
		
		/**
		 * Creates the notebook of tasks lists and displays the current task list.
		 */
		public TaskListPanel() {
			super(new GridBagLayout());
			
			lblActiveTaskList = new JLabel("Current Task List");
			comboTaskLists = new JComboBox<String>();
			comboTaskLists.addActionListener(this);
			
			lblCompletedTasks = new JLabel(COMPLETED_TASKS_LABEL);
			
			btnAddTaskList = new JButton("Add Task List");
			btnEditTaskList = new JButton("Edit Task List");
			btnRemoveTaskList = new JButton("Remove Task List");
			
			btnAddTaskList.addActionListener(this);
			btnEditTaskList.addActionListener(this);
			btnRemoveTaskList.addActionListener(this);
			
			tableModel = new TaskTableModel();
			tableTasks = new JTable(tableModel);
			tableTasks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableTasks.setPreferredScrollableViewportSize(new Dimension(500, 500));
			tableTasks.setFillsViewportHeight(true);
			tableTasks.getColumnModel().getColumn(0).setPreferredWidth(5);
			tableTasks.getColumnModel().getColumn(1).setPreferredWidth(300);
			tableTasks.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
					int idx = tableTasks.getSelectedRow();
					pnlTask.setTask(idx);
				}
				
			});
			
			JScrollPane listScrollPane = new JScrollPane(tableTasks);
			
			JPanel pnlActiveTaskList = new JPanel();
			pnlActiveTaskList.setLayout(new GridLayout(1, 2));
			pnlActiveTaskList.add(lblActiveTaskList);
			pnlActiveTaskList.add(comboTaskLists);
			
			JPanel pnlTaskListActions = new JPanel();
			pnlTaskListActions.setLayout(new GridLayout(1, 3));
			pnlTaskListActions.add(btnAddTaskList);
			pnlTaskListActions.add(btnEditTaskList);
			pnlTaskListActions.add(btnRemoveTaskList);
			
			GridBagConstraints c = new GridBagConstraints();
			
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			add(pnlActiveTaskList, c);
			
			c.gridx = 0;
			c.gridy = 1;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			add(lblCompletedTasks, c);
			
			c.gridx = 0;
			c.gridy = 2;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			add(pnlTaskListActions, c);
			
			c.gridx = 0;
			c.gridy = 3;
			c.weightx = 1;
			c.weighty = 20;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			c.gridheight = GridBagConstraints.REMAINDER;
			c.gridwidth = GridBagConstraints.REMAINDER;
			add(listScrollPane, c);
			
			updateTaskLists();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == comboTaskLists) {
				int idx = comboTaskLists.getSelectedIndex();
				
				if (idx == -1) {
					updateTaskLists();
				} else {
					String taskListName = comboTaskLists.getItemAt(idx);
					notebook.setCurrentTaskList(taskListName);
					updateTaskLists();
				}
			} else if (e.getSource() == btnAddTaskList) {
				try {
					String taskListName = (String) JOptionPane.showInputDialog(this, "Task List Name?", "Create New Task List", JOptionPane.QUESTION_MESSAGE);
					notebook.addTaskList(new TaskList(taskListName, 0));
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(WolfTasksGUI.this, iae.getMessage());
				}
			} else if (e.getSource() == btnEditTaskList) {
				try {
					if (notebook.getCurrentTaskList() instanceof ActiveTaskList) {
						JOptionPane.showMessageDialog(WolfTasksGUI.this, "The Active Tasks list may not be edited.");
					} else {
						String taskListName = (String) JOptionPane.showInputDialog(this, "Update Task List Name Name?", "Edit Task List", JOptionPane.QUESTION_MESSAGE, null, null, notebook.getCurrentTaskList().getTaskListName());
						notebook.editTaskList(taskListName);
					}
					
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(WolfTasksGUI.this, iae.getMessage());
				}
			} else if (e.getSource() == btnRemoveTaskList) {
				try {
					if (notebook.getCurrentTaskList() instanceof ActiveTaskList) {
						JOptionPane.showMessageDialog(WolfTasksGUI.this, "The Active Tasks list may not be deleted.");
					} else {
						int selection = JOptionPane.showOptionDialog(this, "Are you sure you want to delete " + notebook.getCurrentTaskList().getTaskListName() + "?", "Delete Task List", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
						if (selection == 0) { //Yes
							notebook.removeTaskList();
						}
					}
					
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(WolfTasksGUI.this, iae.getMessage());
				}
			}
			updateTaskLists();
			WolfTasksGUI.this.repaint();
			WolfTasksGUI.this.validate();
		}
		
		public void updateTaskLists() {
			if (notebook == null) {
				btnAddTaskList.setEnabled(false);
				btnEditTaskList.setEnabled(false);
				btnRemoveTaskList.setEnabled(false);

			} else {
				AbstractTaskList taskList = notebook.getCurrentTaskList();
				
				String taskListName = taskList.getTaskListName();
				btnAddTaskList.setEnabled(true);
				btnEditTaskList.setEnabled(true);
				btnRemoveTaskList.setEnabled(true);
				
				comboTaskLists.removeAllItems();
				String [] taskListNames = notebook.getTaskListsNames();
				for (int i = 0; i < taskListNames.length; i++) {
					comboTaskLists.addItem(taskListNames[i]);
				}
				
				comboTaskLists.setSelectedItem(taskListName);
				
				if (comboTaskLists.getSelectedIndex() == 0) { //active tasks
					pnlTask.enableButtons(false);
					pnlTask.btnComplete.setEnabled(true);
				} else {
					pnlTask.enableButtons(true);
				}
				
				lblCompletedTasks.setText(COMPLETED_TASKS_LABEL + taskList.getCompletedCount());
			}
			
			itemSave.setEnabled(notebook != null && notebook.isChanged());
			tableModel.updateData();
		}
		
		/**
		 * IncidentTableModel is the object underlying the JTable object that displays
		 * the list of Incidents to the user.
		 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
		 */
		private class TaskTableModel extends AbstractTableModel {
			
			/** ID number used for object serialization. */
			private static final long serialVersionUID = 1L;
			/** Column names for the table */
			private String [] columnNames = {"Task List", "Task Title"};
			/** Data stored in the table */
			private Object [][] data;
			
			/**
			 * Constructs the IncidentTableModel by requesting the latest information
			 * from the IncidentTableModel.
			 */
			public TaskTableModel() {
				updateData();
			}

			/**
			 * Returns the number of columns in the table.
			 * @return the number of columns in the table.
			 */
			public int getColumnCount() {
				return columnNames.length;
			}

			/**
			 * Returns the number of rows in the table.
			 * @return the number of rows in the table.
			 */
			public int getRowCount() {
				if (data == null) 
					return 0;
				return data.length;
			}
			
			/**
			 * Returns the column name at the given index.
			 * @param col the column index
			 * @return the column name at the given column.
			 */
			public String getColumnName(int col) {
				return columnNames[col];
			}

			/**
			 * Returns the data at the given {row, col} index.
			 * @param row the row index
			 * @param col the column index
			 * @return the data at the given location.
			 */
			public Object getValueAt(int row, int col) {
				if (data == null)
					return null;
				return data[row][col];
			}
			
			/**
			 * Sets the given value to the given {row, col} location.
			 * @param value Object to modify in the data.
			 * @param row the row index
			 * @param col the column index
			 */
			public void setValueAt(Object value, int row, int col) {
				data[row][col] = value;
				fireTableCellUpdated(row, col);
			}
			
			/**
			 * Updates the given model with Tasks information from a TaskList.
			 */
			private void updateData() {
				if (notebook != null) {
					AbstractTaskList currentTaskList = notebook.getCurrentTaskList();
					data = currentTaskList.getTasksAsArray();
				}
			}
		}
		
	}
	
	/**
	 * Inner class that creates the look and behavior for interacting with a Task.
	 * @author Dr. Sarah Heckman
	 */
	private class TaskPanel extends JPanel implements ActionListener {
		
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		
		/** Label - task name */
		private JLabel lblTaskName;
		/** TextField - task name */
		private JTextField txtTaskName;
		
		/** Label - recurring */
		private JLabel lblRecurring;
		/** Checkbox - recurring */
		private JCheckBox checkRecurring;
		
		/** Label - active */
		private JLabel lblActive;
		/** Checkbox - active */
		private JCheckBox checkActive;
		
		/** Label - description */
		private JLabel lblDescription;
		/** Text Area - description */
		private JTextArea txtDescription;
		
		/** Button - add/edit */
		private JButton btnAddEdit;
		/** Button - remove */
		private JButton btnRemove;
		/** Button - complete */
		private JButton btnComplete;
		/** Button - clear */
		private JButton btnClear;
		
		/** Button - move up */
		private JButton btnMoveUp;
		/** Button - move down */
		private JButton btnMoveDown;
		/** Button - move to front */
		private JButton btnMoveToFront;
		/** Button - move to back */
		private JButton btnMoveToBack;
		
		public TaskPanel() {
			super(new GridBagLayout());
			
			lblTaskName = new JLabel("Task Name");
			txtTaskName = new JTextField(25);
			
			lblRecurring = new JLabel("Recurring");
			checkRecurring = new JCheckBox();
			
			lblActive = new JLabel("Active");
			checkActive = new JCheckBox();
					
			lblDescription = new JLabel("Description");
			txtDescription = new JTextArea(10, 50);
			
			btnAddEdit = new JButton("Add / Edit");
			btnRemove = new JButton("Remove");
			btnComplete = new JButton("Complete Task");
			btnClear = new JButton("Clear Task Information");
			
			btnMoveUp = new JButton("Move Up");
			btnMoveDown = new JButton("Move Down");
			btnMoveToFront = new JButton("Move to Front");
			btnMoveToBack = new JButton("Move to Back");
			
			btnAddEdit.addActionListener(this);
			btnRemove.addActionListener(this);
			btnComplete.addActionListener(this);
			btnClear.addActionListener(this);
			
			btnMoveUp.addActionListener(this);
			btnMoveDown.addActionListener(this);
			btnMoveToFront.addActionListener(this);
			btnMoveToBack.addActionListener(this);
			
			enableButtons(false);
			
			JScrollPane scrollDescription = new JScrollPane(txtDescription);
			scrollDescription.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollDescription.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			
			GridBagConstraints c = new GridBagConstraints();
			
			//Row 1 - name
			JPanel row1 = new JPanel();
			row1.setLayout(new GridLayout(2, 1));
			row1.add(lblTaskName);
			row1.add(txtTaskName);
			
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(row1, c);
			
			//Row 2 - recurring/completed
			JPanel row2 = new JPanel();
			row2.setLayout(new GridLayout(1, 4));
			row2.add(lblRecurring);
			row2.add(checkRecurring);
			row2.add(lblActive);
			row2.add(checkActive);
						
			c.gridx = 0;
			c.gridy = 1;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(row2, c);
			
			//Row 3 - description
			JPanel row3 = new JPanel();
			row3.setLayout(new GridLayout(2, 1));
			row3.add(lblDescription);
			row3.add(scrollDescription);
			
			c.gridx = 0;
			c.gridy = 2;
			c.weightx = 1;
			c.weighty = 2;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(row3, c);
			
			//Row 4 - add/edit/remove
			JPanel row4 = new JPanel();
			row4.setLayout(new GridLayout(1, 2));
			row4.add(btnAddEdit);
			row4.add(btnRemove);
			
			c.gridx = 0;
			c.gridy = 3;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(row4, c);
			
			//Row 5 - complete/clear
			JPanel row5 = new JPanel();
			row5.setLayout(new GridLayout(1, 2));
			row5.add(btnComplete);
			row5.add(btnClear);
			
			c.gridx = 0;
			c.gridy = 4;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(row5, c);
			
			//Row 6 - move up/down
			JPanel row6 = new JPanel();
			row6.setLayout(new GridLayout(1, 2));
			row6.add(btnMoveUp);
			row6.add(btnMoveDown);
			
			c.gridx = 0;
			c.gridy = 5;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(row6, c);
			
			//Row 7 - move front/back
			JPanel row7 = new JPanel();
			row7.setLayout(new GridLayout(1, 2));
			row7.add(btnMoveToFront);
			row7.add(btnMoveToBack);
			
			c.gridx = 0;
			c.gridy = 6;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(row7, c);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int idx = pnlTaskList.tableTasks.getSelectedRow();
			
			if (e.getSource() == btnAddEdit) {
				try {
					if (idx == -1) {
						Task t = new Task(txtTaskName.getText(), txtDescription.getText(), checkRecurring.isSelected(), checkActive.isSelected());
						notebook.addTask(t);
					} else {
						notebook.editTask(idx, txtTaskName.getText(), txtDescription.getText(), checkRecurring.isSelected(), checkActive.isSelected());
					}
					setTask(-1);
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(WolfTasksGUI.this, iae.getMessage());
				}
			} else if (e.getSource() == btnRemove) {
				try {
					notebook.getCurrentTaskList().removeTask(idx);
					setTask(-1);
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(WolfTasksGUI.this, iae.getMessage());
				} catch (IndexOutOfBoundsException ioobe) {
					JOptionPane.showMessageDialog(WolfTasksGUI.this, "No task selected.");
				}
			} else if (e.getSource() == btnComplete) {
				try {
					Task t = notebook.getCurrentTaskList().getTask(idx);
					t.completeTask();
					setTask(-1);
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(WolfTasksGUI.this, iae.getMessage());
				} catch (IndexOutOfBoundsException ioobe) {
					JOptionPane.showMessageDialog(WolfTasksGUI.this, "No task selected.");
				}
			} else if (e.getSource() == btnClear) {
				try {
					setTask(-1);
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(WolfTasksGUI.this, iae.getMessage());
				} catch (IndexOutOfBoundsException ioobe) {
					JOptionPane.showMessageDialog(WolfTasksGUI.this, "No task selected.");
				}
			} else if (e.getSource() == btnMoveUp) {
				try {
					notebook.getCurrentTaskList().getTasks().moveUp(idx);
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(WolfTasksGUI.this, iae.getMessage());
				} catch (IndexOutOfBoundsException ioobe) {
					JOptionPane.showMessageDialog(WolfTasksGUI.this, "No task selected.");
				}
			} else if (e.getSource() == btnMoveDown) {
				try {
					notebook.getCurrentTaskList().getTasks().moveDown(idx);
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(WolfTasksGUI.this, iae.getMessage());
				} catch (IndexOutOfBoundsException ioobe) {
					JOptionPane.showMessageDialog(WolfTasksGUI.this, "No task selected.");
				}
			} else if (e.getSource() == btnMoveToFront) {
				try {
					notebook.getCurrentTaskList().getTasks().moveToFront(idx);
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(WolfTasksGUI.this, iae.getMessage());
				} catch (IndexOutOfBoundsException ioobe) {
					JOptionPane.showMessageDialog(WolfTasksGUI.this, "No task selected.");
				}
			} else if (e.getSource() == btnMoveToBack) {
				try {
					notebook.getCurrentTaskList().getTasks().moveToBack(idx);
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(WolfTasksGUI.this, iae.getMessage());
				} catch (IndexOutOfBoundsException ioobe) {
					JOptionPane.showMessageDialog(WolfTasksGUI.this, "No task selected.");
				}
			} 
			itemSave.setEnabled(notebook != null && notebook.isChanged());
			pnlTaskList.updateTaskLists();
		}
		
		/**
		 * Sets the information for the selected task and enables the buttons.
		 * @param idx index of selected task
		 */
		public void setTask(int idx) {
			try {
				Task t = notebook.getCurrentTaskList().getTask(idx);
				
				txtTaskName.setText(t.getTaskName());
				txtDescription.setText(t.getTaskDescription());
				
				checkActive.setSelected(t.isActive());
				checkRecurring.setSelected(t.isRecurring());
				
			} catch (IndexOutOfBoundsException e) {
				txtTaskName.setText("");
				txtDescription.setText("");
				checkActive.setSelected(false);
				checkRecurring.setSelected(false);
				
				pnlTaskList.tableTasks.getSelectionModel().clearSelection();
				
				enableButtons(false);
			}
		}
		
		/**
		 * Enable or disable all buttons 
		 * @param enable true if enable, false if disabled
		 */
		public void enableButtons(boolean enable) {
			btnAddEdit.setEnabled(enable); 
			btnRemove.setEnabled(enable);
			btnComplete.setEnabled(enable);
			btnClear.setEnabled(enable);
			
			btnMoveUp.setEnabled(enable);
			btnMoveDown.setEnabled(enable);
			btnMoveToFront.setEnabled(enable);
			btnMoveToBack.setEnabled(enable);
		}
		
	}

}