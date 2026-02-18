package martin;

import java.util.Scanner;
import java.util.ArrayList;

import martin.task.Deadline;
import martin.task.Event;
import martin.task.Task;
import martin.task.Todo;

public class Martin {
    public static final int HORIZONTAL_LINE = 40;
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static int taskCounter = 0;
    private static boolean isBye = false;

    public static void main(String[] args) {
        Scanner in = getFirstScanner();

        while (!isBye) {
            // Ask for next command
            System.out.println("User Command:");
            String line = in.nextLine();
            String[] userCommandArray = line.split(" ", 2);
            String stringAfterCommand = userCommandArray.length > 1 ? userCommandArray[1] : "";

            // Check command
            try {
                switch (userCommandArray[0]) {
                case "bye":
                    exitMartin();
                    break;
                case "list":
                    displayListOfTasks();
                    break;
                case "mark":
                    markTask(userCommandArray);
                    break;
                case "unmark":
                    unmarkTask(userCommandArray);
                    break;
                case "todo":
                    addTodoTask(stringAfterCommand);
                    break;
                case "deadline":
                    addDeadlineTask(stringAfterCommand);
                    break;
                case "event":
                    addEventTask(stringAfterCommand);
                    break;
                default:
                    throw new IllegalArgumentException("you have keyed in an unrecognised command.");
                }
            } catch (IllegalArgumentException e) {
                printer("Martin:\nSir, " + e.getMessage() + " Please try again.");
                continue;
            }
        }
    }

    private static void storeActivity(Task activity) {
        tasks.add(taskCounter,activity);
        taskCounter++;
        //markedAsDone.add(false);
    }

    private static void printHorizontalLine() {
        System.out.println("_".repeat(HORIZONTAL_LINE));
    }

    private static void printer(String line) {
        printHorizontalLine();
        System.out.println(line);
        printHorizontalLine();
    }

    private static Scanner getFirstScanner() {
        printer("Martin:\nHello sir my name is Martin.\nWhat can I do for you today?");

        Scanner in = new Scanner(System.in);
        String line = "";
        return in;
    }

    /**
     * Display end message and quits
     */
    private static void exitMartin() {
        printer("Martin:\nBye Sir, see you tomorrow!");
        isBye = true;
        return;
    }

    /**
     * Displays list of tasks from tasks[]
     */
    private static void displayListOfTasks() {
        printHorizontalLine();
        System.out.println("Martin's To-do List:");
        for (int i = 0; i < taskCounter; i++) {
            String marked = tasks.get(i).getTaskDone() ? "X" : " ";
            System.out.printf("%d. [%s] %s\n", i + 1, marked, tasks.get(i).getTask());
        }
        printHorizontalLine();
    }

    /**
     * Mark task as done. Task index is second argument in user input
     */
    private static void markTask(String[] userCommandArray) {
        int itemIndex = getItemIndex(userCommandArray);
        tasks.get(itemIndex - 1).markAsDone();
        printer(String.format("Martin:\nGood news Sir! I have marked %d. %s as done.", itemIndex, tasks.get(itemIndex - 1).getTask()));
    }

    /**
     * Unmark task as done. Task index is second argument in user input
     */
    private static void unmarkTask(String[] userCommandArray) {
        int itemIndex = getItemIndex(userCommandArray);
        tasks.get(itemIndex - 1).unmarkAsDone();
        printer(String.format("Martin:\nSorry Sir, I have to remark %d. %s as undone.", itemIndex, tasks.get(itemIndex - 1).getTask()));
    }

    private static int getItemIndex(String[] userCommandArray) {
        int itemIndex = 0;
        try {
            itemIndex = Integer.parseInt(userCommandArray[1]);
        } catch (NumberFormatException e) {
            throwError("please key in an integer for the task index!");
        }
        if (itemIndex > taskCounter) {
            throwError("you have keyed in an index that does not exist.");
        }
        return itemIndex;
    }

    /**
     * Add a Todo task to tasks[]
     */
    private static void addTodoTask(String stringAfterCommand) {
        if (stringAfterCommand.isBlank()) {
            throwError("please follow the Todo task format: todo <description>.");
        }
        Todo task = new Todo(stringAfterCommand);
        storeActivity(task);
        printer("Martin:\nYes Sir, I have added this task to my list:\n" + "    [" + task.getTypeOfTask() + "]" + "[ ] " + task.getTask() + "\nI currently have " + taskCounter + " tasks in the list.");
    }

    /**
     * Add a Event task to tasks[]
     */
    private static void addEventTask(String stringAfterCommand) {
        if (!(stringAfterCommand.contains("/from") && (stringAfterCommand.contains("/to")))) {
            throwError("please follow the Event task format: event <description> /from <startDate> /to <endDate>.");
        }
        String description = null;
        String startDate = null;
        String endDate = null;
        try {
            description = stringAfterCommand.substring(0, stringAfterCommand.indexOf("/from") - 1);
            startDate = stringAfterCommand.substring(stringAfterCommand.indexOf("/from") + 6, stringAfterCommand.indexOf("/to") - 1);
            endDate = stringAfterCommand.substring(stringAfterCommand.indexOf("/to") + 4);
        } catch (IndexOutOfBoundsException e) {
            throwError("please follow the Event task format: event <description> /from <startDate> /to <endDate>.");
        }
        Event task = new Event(description, startDate, endDate);
        storeActivity(task);
        printer("Martin:\nYes Sir, I have added this task to my list:\n" + "    [" + task.getTypeOfTask() + "]" + "[ ] " + task.getTask() + "\nI currently have " + taskCounter + " tasks in the list.");
    }

    /**
     * Add a Deadline task to tasks[]
     */
    private static void addDeadlineTask(String stringAfterCommand) {
        if (!stringAfterCommand.contains("/by")) {
            throwError("please follow the Deadline task format: deadline <description> /by <deadline>.");
        }
        String description = null;
        String deadline = null;
        try {
            description = stringAfterCommand.substring(0, stringAfterCommand.indexOf("/by") - 1);
            deadline = stringAfterCommand.substring(stringAfterCommand.indexOf("/by") + 4);
        } catch (IndexOutOfBoundsException e) {
            throwError("please follow the Deadline task format: deadline <description> /by <deadline>.");
        }
        Deadline task = new Deadline(description, deadline);
        storeActivity(task);
        printer("Martin:\nYes Sir, I have added this task to my list:\n" + "    [" + task.getTypeOfTask() + "]" + "[ ] " + task.getTask() + "\nI currently have " + taskCounter + " tasks in the list.");
    }

    private static void throwError(String message) {
        throw new IllegalArgumentException(message);
    }
}
