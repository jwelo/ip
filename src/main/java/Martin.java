package src.main.java;

import java.util.Scanner;

public class Martin {
    public static final int HORIZONTAL_LINE = 40;
    private static Task[] tasks = new Task[100];
    private static int taskCounter = 0;

    public static void main(String[] args) {
        Scanner in = getFirstScanner();

        label:
        while (true) {
            // Ask for next command
            System.out.println("User Command:");
            String line = in.nextLine();
            String stringAfterCommand = line.substring(line.indexOf(' ') + 1);
            String[] userCommandArray = line.split(" ");

            // Check command
            switch (userCommandArray[0]) {
            case "bye":
                exitMartin();
                break label;
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
            }
        }
    }

    private static void storeActivity(Task activity) {
        tasks[taskCounter] = activity;
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
        return;
    }

    /**
     * Displays list of tasks from tasks[]
     */
    private static void displayListOfTasks() {
        printHorizontalLine();
        System.out.println("Martin's To-do List:");
        for (int i = 0; i < taskCounter; i++) {
            String marked = tasks[i].getTaskDone() ? "X" : " ";
            System.out.printf("%d. [%s] %s\n", i + 1, marked, tasks[i].getTask());
        }
        printHorizontalLine();
    }

    /**
     * Mark task as done. Task index is second argument in user input
     */
    private static void markTask(String[] userCommandArray) {
        int itemIndex = Integer.parseInt(userCommandArray[1]);
        if (isInvalidIndex(itemIndex)) return;
        int markIndex = itemIndex - 1;
        tasks[markIndex].markAsDone();
        printer(String.format("Martin:\nGood news Sir! I have marked %d. %s as done.", itemIndex, tasks[markIndex].getTask()));
    }
    /**
     * Unmark task as done. Task index is second argument in user input
     */
    private static void unmarkTask(String[] userCommandArray) {
        int itemIndex = Integer.parseInt(userCommandArray[1]);
        if (isInvalidIndex(itemIndex)) return;
        int markIndex = itemIndex - 1;
        tasks[markIndex].unmarkAsDone();
        printer(String.format("Martin:\nSorry Sir, I have to remark %d. %s as undone.", itemIndex, tasks[markIndex].getTask()));
    }

    /**
     * Check if user input argument is a valid task index
     * @return true if invalid, false if valid
     */
    private static boolean isInvalidIndex(int itemIndex) {
        if (itemIndex > tasks.length) {
            System.out.println("Martin:\nSir, you have keyed in an invalid task index. Please try again");
            return true;
        }
        return false;
    }

    /**
     * Add a Todo task to tasks[]
     */
    private static void addTodoTask(String stringAfterCommand) {
        Todo task = new Todo(stringAfterCommand);
        storeActivity(task);
        printer("Martin:\nYes Sir, I have added this task to my list:\n" + "    [" + task.getTypeOfTask() + "]" + "[ ] " + task.getTask() + "\nI currently have " + taskCounter + " tasks in the list.");
    }

    /**
     * Add a Event task to tasks[]
     */
    private static void addEventTask(String stringAfterCommand) {
        String description = stringAfterCommand.substring(0, stringAfterCommand.indexOf("/from") - 1);
        String startDate = stringAfterCommand.substring(stringAfterCommand.indexOf("/from") + 6, stringAfterCommand.indexOf("/to") - 1);
        String endDate = stringAfterCommand.substring(stringAfterCommand.indexOf("/to") + 4);
        Event task = new Event(description,startDate,endDate);
        storeActivity(task);
        printer("Martin:\nYes Sir, I have added this task to my list:\n" + "    [" + task.getTypeOfTask() + "]" + "[ ] " + task.getTask() + "\nI currently have " + taskCounter + " tasks in the list.");
    }

    /**
     * Add a Deadline task to tasks[]
     */
    private static void addDeadlineTask(String stringAfterCommand) {
        String description = stringAfterCommand.substring(0, stringAfterCommand.indexOf("/by") - 1);
        String deadline = stringAfterCommand.substring(stringAfterCommand.indexOf("/by") + 4);
        Deadline task = new Deadline(description,deadline);
        storeActivity(task);
        printer("Martin:\nYes Sir, I have added this task to my list:\n" + "    [" + task.getTypeOfTask() + "]" + "[ ] " + task.getTask() + "\nI currently have " + taskCounter + " tasks in the list.");
    }
}
