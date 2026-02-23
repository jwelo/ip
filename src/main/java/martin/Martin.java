package martin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
//import java.util.Scanner;

import martin.task.Deadline;
import martin.task.Event;
import martin.task.Task;
import martin.task.Todo;

public class Martin {
//    public static final int HORIZONTAL_LINE = 40;
    private static Ui ui = new Ui();
    private static Storage storage = new Storage("savedList.txt");
    private static List<Task> tasks = new ArrayList<>();
    private static boolean isBye = false;

    public static void main(String[] args) {
        try {
            tasks = storage.loadSavedTasks();
            ui.showFileSaveFound();
        } catch (IOException e) {
            ui.showFileLoadingError();
        }


        while (!isBye) {
            // Ask for next command
            System.out.println("User Command:");
            String line = ui.getNextLine();
            String[] userCommandArray = line.split(" ", 2);
            String stringAfterCommand = userCommandArray.length > 1 ? userCommandArray[1] : "";

            // Check command
            try {
                switch (userCommandArray[0]) {
                case "bye":
                    ui.exitMartin();
                    isBye = true;
                    break;
                case "list":
                    ui.displayListOfTasks(tasks);
                    break;
                case "mark":
                    markTask(userCommandArray);
                    break;
                case "unmark":
                    unmarkTask(userCommandArray);
                    break;
                case "delete":
                    deleteTask(userCommandArray);
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
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showFileSaveError(e);
            }
        }
    }

    private static void deleteTask(String[] userCommandArray) throws IOException {
        int itemIndex = getItemIndex(userCommandArray);
        ui.showTaskDeleteSuccess(tasks, itemIndex);
        tasks.remove(itemIndex - 1);
        storage.saveAllTasks(tasks);
    }

    private static void storeTask(Task task) {
        try {
            tasks.add(task);
            storage.appendTask(task);
        } catch (IOException e) {
            ui.showFileSaveError(e);
        }
    }

    /**
     * Mark task as done. Task index is second argument in user input
     */
    private static void markTask(String[] userCommandArray) {
        int itemIndex = getItemIndex(userCommandArray);
        Task task = tasks.get(itemIndex - 1);
        task.markAsDone();
        ui.showMarkedTask(task, itemIndex);
    }

    /**
     * Unmark task as done. Task index is second argument in user input
     */
    private static void unmarkTask(String[] userCommandArray) {
        int itemIndex = getItemIndex(userCommandArray);
        Task task = tasks.get(itemIndex - 1);
        task.unmarkAsDone();
        ui.showUnmarkedTask(task,itemIndex);
    }

    private static int getItemIndex(String[] userCommandArray) {
        int itemIndex = 0;
        try {
            itemIndex = Integer.parseInt(userCommandArray[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("please key in an integer for the task index!");
        }
        if (itemIndex > tasks.size()) {
            throw new IllegalArgumentException("you have keyed in an index that does not exist.");
        }
        return itemIndex;
    }

    /**
     * Add a Todo task to tasks[]
     */
    private static void addTodoTask(String stringAfterCommand) {
        if (stringAfterCommand.isBlank()) {
            throw new IllegalArgumentException("please follow the Todo task format: todo <description>.");
        }
        Todo task = new Todo(stringAfterCommand);
        storeTask(task);
        ui.showTaskAddSuccess(task, tasks.size());
    }

    /**
     * Add a Event task to tasks[]
     */
    private static void addEventTask(String stringAfterCommand) {
        if (!(stringAfterCommand.contains("/from") && (stringAfterCommand.contains("/to")))) {
            throw new IllegalArgumentException("please follow the Event task format: event <description> /from <startDate> /to <endDate>.");
        }
        String description = null;
        String startDate = null;
        String endDate = null;
        try {
            description = stringAfterCommand.substring(0, stringAfterCommand.indexOf("/from") - 1);
            startDate = stringAfterCommand.substring(stringAfterCommand.indexOf("/from") + 6, stringAfterCommand.indexOf("/to") - 1);
            endDate = stringAfterCommand.substring(stringAfterCommand.indexOf("/to") + 4);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("please follow the Event task format: event <description> /from <startDate> /to <endDate>.");
        }
        Event task = new Event(description, startDate, endDate);
        storeTask(task);
        ui.showTaskAddSuccess(task, tasks.size());
    }

    /**
     * Add a Deadline task to tasks[]
     */
    private static void addDeadlineTask(String stringAfterCommand) {
        if (!stringAfterCommand.contains("/by")) {
            throw new IllegalArgumentException("please follow the Deadline task format: deadline <description> /by <deadline>.");
        }
        String description;
        String deadline;
        try {
            description = stringAfterCommand.substring(0, stringAfterCommand.indexOf("/by") - 1);
            deadline = stringAfterCommand.substring(stringAfterCommand.indexOf("/by") + 4);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("please follow the Deadline task format: deadline <description> /by <deadline>.");
        }
        Deadline task = new Deadline(description, deadline);
        storeTask(task);
        ui.showTaskAddSuccess(task, tasks.size());
    }
}
