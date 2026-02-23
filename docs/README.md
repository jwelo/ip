# Martin User Guide

// Product screenshot goes here

Martin is a sleek, CLI-based task manager designed to help you organize your life with the efficiency of a personal butler. Whether you're tracking daily errands, project deadlines, or upcoming events, Martin is here to take notes and keep you on schedule.

## Types of Tasks Available:
1. Todo - Great for quick and dirty to-do tasks
2. Deadline - For tasks that have a certain deadline
3. Event - For tasks that occur between a period of time

## Adding Todo task
Usage: `todo (task description)`

A successful addition will result in the following acknowledgement:

```
________________________________________
Martin:
Yes Sir, I have added this task to my list:
    [T][ ] newTask
I currently have 1 tasks in the list.
________________________________________
```

## Adding Deadline task
Usage: `deadline (taskDescription) /by (taskDeadline)`

A successful addition will result in the following acknowledgement:

```
________________________________________
Martin:
Yes Sir, I have added this task to my list:
    [D][ ] newTask
I currently have 1 tasks in the list.
________________________________________
```

## Adding Event task
Usage: `event (taskDescription) /from (startDate) /to (endDate)`

A successful addition will result in the following acknowledgement:

```
________________________________________
Martin:
Yes Sir, I have added this task to my list:
    [E][ ] newTask
I currently have 1 tasks in the list.
________________________________________
```

## Displaying List of Tasks
To output all existing tasks with its respective completion status.

Usage: `list`


## Marking/Unmarking Completion of Tasks
To mark or unmark an existing task as complete.

Usage: `mark (taskIndex)` or `unmark (taskIndex)`

## Deleting Tasks from List
To delete a task from the saved list of tasks.

Usage: `delete (taskIndex)`

## Finding Tasks with a Keyword
To find and list tasks with a given keyword.

Usage: `find (keyword)`