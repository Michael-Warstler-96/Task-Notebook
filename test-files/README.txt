Overview of test files for WolfTasks

notebook0.txt - valid file with only a notebook name
notebook1.txt - valid file with three task lists
notebook2.txt - valid file with items that can be missing
notebook3.txt - missing leading ! in file - IAE thrown with message Unable to load file.
notebook4.txt - task list without number of completed tasks - creates notebook with no task lists
notebook5.txt - task list with missing name - creates notebook with no tasks lists
notebook6.txt - task list with negative completed tasks - creates notebook with no tasks lists
notebook7.txt - task without a name - creates notebook with a task list - invalid task isn't added