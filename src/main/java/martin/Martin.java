package martin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import martin.task.Deadline;
import martin.task.Event;
import martin.task.Task;
import martin.task.Todo;

public class Martin {
    public static final int HORIZONTAL_LINE = 40;
    private static List<Task> tasks = new ArrayList<>();
    private static boolean isBye = false;

    public static void main(String[] args) {
        Scanner in = getFirstScanner();
        try {
            getSavedTasks();
        } catch (IOException e) {
            System.err.println("Could not load saved tasks. Reason: " + e.getMessage());
        }

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
                case "delete":
                    int itemIndex = getItemIndex(userCommandArray);
                    printer(String.format("Martin:\nUnderstood Sir, I have deleted this task - %d. %s.", itemIndex, tasks.get(itemIndex - 1).getTaskDescription()));
                    tasks.remove(itemIndex - 1);;
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

    private static void getSavedTasks() throws IOException {
        Path savedList = Path.of("savedList.txt");
        if (Files.notExists(savedList)) {
            System.out.println("No save file found. Creating 'savedList.txt'...");
            Files.createFile(savedList);
            System.out.println("File created successfully!");
        } else {
            System.out.println("Save file detected. Loading data...");
        }
        List<String> savedTasks = Files.readAllLines(savedList);
        for (String line : savedTasks) {
            String[] parts = line.split(";");
            //String typeOfTask = parts[0]; // currently not needed as list does not include type of task
            String done = parts[1];
            String description = parts[2];
            Task task = new Task(description);
            if (done.equals("1")) {
                task.markAsDone();
            }
            tasks.add(task);
        }
    }

    private static void storeActivity(Task activity) {
        Path path = Path.of("savedList.txt");
        String formattedTaskAsString = activity.getTypeOfTask() + ";" + activity.getTaskDone() + ";" + activity.getTaskDescription();
        formattedTaskAsString += "\n";
        try {
            Files.writeString(path, formattedTaskAsString,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Could not save to file: " + e.getMessage());
        }
        tasks.add(activity);
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
     * Displays list of tasks from savedList.txt
     */
    private static void displayListOfTasks() {
        printHorizontalLine();
        System.out.println("Martin's To-do List:");
        for (int i = 0; i < tasks.size(); i++) {
            String marked = tasks.get(i).getTaskDone() ? "X" : " ";
            System.out.printf("%d. [%s] %s\n", i + 1, marked, tasks.get(i).getTaskDescription());
        }
        printHorizontalLine();
    }

    /**
     * Mark task as done. Task index is second argument in user input
     */
    private static void markTask(String[] userCommandArray) {
        int itemIndex = getItemIndex(userCommandArray);
        tasks.get(itemIndex - 1).markAsDone();
        printer(String.format("Martin:\nGood news Sir! I have marked %d. %s as done.", itemIndex, tasks.get(itemIndex - 1).getTaskDescription()));
    }

    /**
     * Unmark task as done. Task index is second argument in user input
     */
    private static void unmarkTask(String[] userCommandArray) {
        int itemIndex = getItemIndex(userCommandArray);
        tasks.get(itemIndex - 1).unmarkAsDone();
        printer(String.format("Martin:\nSorry Sir, I have to remark %d. %s as undone.", itemIndex, tasks.get(itemIndex - 1).getTaskDescription()));
    }

    private static int getItemIndex(String[] userCommandArray) {
        int itemIndex = 0;
        try {
            itemIndex = Integer.parseInt(userCommandArray[1]);
        } catch (NumberFormatException e) {
            throwError("please key in an integer for the task index!");
        }
        if (itemIndex > tasks.size()) {
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
        printer("Martin:\nYes Sir, I have added this task to my list:\n" + "    [" + task.getTypeOfTask() + "]" + "[ ] " + task.getTaskDescription() + "\nI currently have " + tasks.size() + " tasks in the list.");
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
        printer("Martin:\nYes Sir, I have added this task to my list:\n" + "    [" + task.getTypeOfTask() + "]" + "[ ] " + task.getTaskDescription() + "\nI currently have " + tasks.size() + " tasks in the list.");
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
        printer("Martin:\nYes Sir, I have added this task to my list:\n" + "    [" + task.getTypeOfTask() + "]" + "[ ] " + task.getTaskDescription() + "\nI currently have " + tasks.size() + " tasks in the list.");
    }

    private static void throwError(String message) {
        throw new IllegalArgumentException(message);
    }
}
