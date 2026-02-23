package martin;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import martin.task.Task;

/**
 * Handles all user interactions for the Martin application.
 * This class is responsible for reading user input and displaying messages,
 * tasks, and errors to the console.
 */
public class Ui {

    /** The width of the horizontal divider line used in console output. */
    public static final int HORIZONTAL_LINE = 40;
    private Scanner in;

    /**
     * Initializes a new Ui instance, sets up the input scanner,
     * and displays the initial welcome message to the user.
     */
    public Ui() {
        in = new Scanner(System.in);
        showWelcomeMessage();
    }

    private void showWelcomeMessage() {
        printer("Martin:\nHello sir my name is Martin.\nWhat can I do for you today?");
    }

    /**
     * Prompts the user for input and reads the next line of text.
     *
     * @return The full string entered by the user.
     */
    public String getNextLine() {
        System.out.println("User Command:");
        return in.nextLine();
    }

    /**
     * Informs the user that no existing save file was found on the system.
     */
    public void showFileLoadingError() {
        System.out.println("No previous save file found.");
    }

    /**
     * Confirms that an existing save file was detected and is being processed.
     */
    public void showFileSaveFound() {
        System.out.println("Save file detected. Loading data...");
    }

    /**
     * Displays an error message specifically related to file saving failures.
     *
     * @param e The IOException that occurred during the save operation.
     */
    public void showFileSaveError(IOException e) {
        System.err.println("Could not save to file: " + e.getMessage());
    }

    /**
     * Displays a general error message formatted within Martin's printer borders.
     *
     * @param message The error message to be displayed.
     */
    public void showError(String message) {
        printer("Error: " + message);
    }

    /**
     * Prints a message to the console wrapped between two horizontal lines.
     *
     * @param line The string message to print.
     */
    public void printer(String line) {
        printHorizontalLine();
        System.out.println(line);
        printHorizontalLine();
    }

    /**
     * Prints a divider line consisting of underscores.
     */
    public void printHorizontalLine() {
        System.out.println("_".repeat(HORIZONTAL_LINE));
    }

    /**
     * Displays the exit message when the user closes the application.
     */
    public void exitMartin() {
        printer("Martin:\nBye Sir, see you tomorrow!");
    }

    /**
     * Iterates through a list of tasks and displays them in a numbered format.
     *
     * @param tasks The list of Task objects to be displayed.
     */
    public void displayListOfTasks(List<Task> tasks) {
        printHorizontalLine();
        System.out.println("Martin's To-do List:");
        for (int i = 0; i < tasks.size(); i++) {
            String marked = tasks.get(i).getTaskDone() ? "X" : " ";
            System.out.printf("%d. [%s] %s\n", i + 1, marked, tasks.get(i).getTaskDescription());
        }
        printHorizontalLine();
    }

    /**
     * Displays a success message when a task is successfully marked as done.
     *
     * @param task      The Task that was marked.
     * @param itemIndex The 1-based index of the task for display purposes.
     */
    public void showMarkedTask(Task task, int itemIndex) {
        String message = String.format("Martin:\nGood news Sir! I have marked %d. %s as done.",
                itemIndex, task.getTaskDescription());
        printer(message);
    }

    /**
     * Displays a message when a task is successfully unmarked (set back to incomplete).
     *
     * @param task      The Task that was unmarked.
     * @param itemIndex The 1-based index of the task for display purposes.
     */
    public void showUnmarkedTask(Task task, int itemIndex) {
        String message = String.format("Martin:\nSorry Sir, I have to remark %d. %s as undone.",
                itemIndex, task.getTaskDescription());
        printer(message);
    }

    /**
     * Displays a success message after a new task has been added to the list.
     *
     * @param task         The newly added Task object.
     * @param taskListSize The current total number of tasks in the list.
     */
    public void showTaskAddSuccess(Task task, int taskListSize) {
        printer("Martin:\nYes Sir, I have added this task to my list:\n" + "    [" + task.getTypeOfTask() + "]" + "[ ] " + task.getTaskDescription() + "\nI currently have " + taskListSize + " tasks in the list.");
    }

    /**
     * Displays a confirmation message after a task has been successfully deleted.
     *
     * @param task      The Task object that was removed.
     * @param itemIndex The 1-based index of the task before it was deleted.
     */
    public void showTaskDeleteSuccess(Task task, int itemIndex) {
        printer(String.format("Martin:\nUnderstood Sir, I have deleted this task - %d. %s.", itemIndex, task.getTaskDescription()));
    }

    /**
     * Displays the results of a keyword search.
     * If no matches are found, a "not found" message is shown.
     *
     * @param tasksContainingKeyword A list of pre-formatted strings representing matching tasks.
     */
    public void showTasksContainingKeyword(List<String> tasksContainingKeyword) {
        if (tasksContainingKeyword.isEmpty()) {
            printer("Martin:\nThere are no tasks in the list containing this keyword.");
        } else {
            printHorizontalLine();
            System.out.println("Martin:\nHere are a list of tasks containing this keyword:");
            int taskIndex = 0;
            for (String tasks : tasksContainingKeyword) {
                System.out.println((taskIndex + 1) + ". " + tasksContainingKeyword.get(taskIndex));
                taskIndex++;
            }
            printHorizontalLine();
        }
    }
}
