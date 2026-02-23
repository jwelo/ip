package martin;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import martin.task.Task;

public class Ui {

    public static final int HORIZONTAL_LINE = 40;
    private Scanner in;

    public Ui() {
        in = new Scanner(System.in);
        showWelcomeMessage();
    }

    private void showWelcomeMessage() {
        printer("Martin:\nHello sir my name is Martin.\nWhat can I do for you today?");
    }

    public String getNextLine() {
        return in.nextLine();
    }

    public void showFileLoadingError() {
        System.out.println("No previous save file found.");
    }

    public void showFileCreateSuccess() {
        System.out.println("File created successfully!");
    }

    public void showFileSaveFound() {
        System.out.println("Save file detected. Loading data...");
    }

    public void showFileSaveError(IOException e) {
        System.err.println("Could not save to file: " + e.getMessage());
    }

    public void showError(String message) {
        printer("Error: " + message);
    }

    public void printer(String line) {
        printHorizontalLine();
        System.out.println(line);
        printHorizontalLine();
    }

    public void printHorizontalLine() {
        System.out.println("_".repeat(HORIZONTAL_LINE));
    }

    public void exitMartin() {
        printer("Martin:\nBye Sir, see you tomorrow!");
    }

    public void displayListOfTasks(List<Task> tasks) {
        printHorizontalLine();
        System.out.println("Martin's To-do List:");
        for (int i = 0; i < tasks.size(); i++) {
            String marked = tasks.get(i).getTaskDone() ? "X" : " ";
            System.out.printf("%d. [%s] %s\n", i + 1, marked, tasks.get(i).getTaskDescription());
        }
        printHorizontalLine();
    }

    public void showMarkedTask(Task task, int itemIndex) {
        String message = String.format("Martin:\nGood news Sir! I have marked %d. %s as done.",
                itemIndex, task.getTaskDescription());
        printer(message);
    }

    public void showUnmarkedTask(Task task, int itemIndex) {
        String message = String.format("Martin:\nSorry Sir, I have to remark %d. %s as undone.",
                itemIndex, task.getTaskDescription());
        printer(message);
    }

    public void showTaskAddSuccess(Task task, int taskListSize) {
        printer("Martin:\nYes Sir, I have added this task to my list:\n" + "    [" + task.getTypeOfTask() + "]" + "[ ] " + task.getTaskDescription() + "\nI currently have " + taskListSize + " tasks in the list.");
    }

    public void showTaskDeleteSuccess(List<Task> tasks, int itemIndex) {
        printer(String.format("Martin:\nUnderstood Sir, I have deleted this task - %d. %s.", itemIndex, tasks.get(itemIndex - 1).getTaskDescription()));
    }

//    public void showInvalidTodoError() {
//        throwError("please follow the Todo task format: todo <description>.");
//    }
}
