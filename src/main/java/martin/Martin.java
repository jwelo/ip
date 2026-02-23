package martin;

import java.io.IOException;
//import java.util.Scanner;

import martin.task.Deadline;
import martin.task.Event;
import martin.task.Task;
import martin.task.Todo;

public class Martin {
    private static Ui ui = new Ui();
    private static Storage storage = new Storage("savedList.txt");
    private static TaskList tasks;
    private static boolean isBye = false;

    public static void main(String[] args) {
        try {
            tasks = new TaskList(storage.loadSavedTasks());
            ui.showFileSaveFound();
        } catch (IOException e) {
            ui.showFileLoadingError();
        }


        while (!isBye) {
            // Ask for next command
            String line = ui.getNextLine();
            String commandWord = Parser.getCommandWord(line);
            String stringAfterCommand = Parser.getInputArguments(line);

            // Check command
            try {
                switch (commandWord) {
                case "bye":
                    ui.exitMartin();
                    isBye = true;
                    break;
                case "list":
                    ui.displayListOfTasks(tasks.getAllTasks());
                    break;
                case "mark":
                    markTask(stringAfterCommand);
                    break;
                case "unmark":
                    unmarkTask(stringAfterCommand);
                    break;
                case "delete":
                    deleteTask(stringAfterCommand);
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
            } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showFileSaveError(e);
            }
        }
    }

    private static void deleteTask(String userArgument) throws IOException {
        int itemIndex = Parser.parseIndex(userArgument);
        Task deletedTask = tasks.deleteTask(itemIndex - 1);
        ui.showTaskDeleteSuccess(deletedTask, itemIndex);
        storage.saveAllTasks(tasks.getAllTasks());
    }

    private static void storeTask(Task task) {
        try {
            tasks.addTask(task);
            storage.appendTask(task);
        } catch (IOException e) {
            ui.showFileSaveError(e);
        }
    }

    /**
     * Mark task as done. Task index is second argument in user input
     */
    private static void markTask(String userArgument) {
        int itemIndex = Parser.parseIndex(userArgument);
        Task task = tasks.getTask(itemIndex);
        task.markAsDone();
        ui.showMarkedTask(task, itemIndex);
    }

    /**
     * Unmark task as done. Task index is second argument in user input
     */
    private static void unmarkTask(String userArgument) {
        int itemIndex = Parser.parseIndex(userArgument);
        Task task = tasks.getTask(itemIndex);
        task.unmarkAsDone();
        ui.showUnmarkedTask(task,itemIndex);
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
        ui.showTaskAddSuccess(task, tasks.getTasksSize());
    }

    /**
     * Add a Deadline task to tasks[]
     */
    private static void addDeadlineTask(String stringAfterCommand) {
        String[] deadlineTask = Parser.parseDeadline(stringAfterCommand);
        String description = deadlineTask[0];
        String deadline =  deadlineTask[1];
        Deadline task = new Deadline(description, deadline);
        storeTask(task);
        ui.showTaskAddSuccess(task, tasks.getTasksSize());
    }

    /**
     * Add a Event task to tasks[]
     */
    private static void addEventTask(String stringAfterCommand) {
        String[] eventTask = Parser.parseEvent(stringAfterCommand);
        String description = eventTask[0];
        String startDate = eventTask[1];
        String endDate = eventTask[2];
        Event task = new Event(description, startDate, endDate);
        storeTask(task);
        ui.showTaskAddSuccess(task, tasks.getTasksSize());
    }
}
