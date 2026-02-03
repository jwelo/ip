package src.main.java;

import java.util.Scanner;

public class Martin {
    //private static ArrayList<String> listOfActivities = new ArrayList<String>();
    //private static ArrayList<Boolean> markedAsDone = new ArrayList<Boolean>();
    private static Task[] tasks = new Task[100];
    private static int taskCounter = 0;

    private static void storeActivity(Task activity) {
        tasks[taskCounter] = activity;
        taskCounter++;
        //markedAsDone.add(false);
    }

    private static void printHorizontalLine() {
        System.out.println("_".repeat(40));
    }

    private static void printer(String line) {
        printHorizontalLine();
        System.out.println(line);
        printHorizontalLine();
    }

    public static void main(String[] args) {
        printer("Martin:\nHello sir my name is Martin.\nWhat can I do for you today?");

        Scanner in = new Scanner(System.in);
        String line = "";

        while (true) {
            // Ask for next command
            System.out.println("User Command:");
            line = in.nextLine();
            String stringAfterCommand = line.substring(line.indexOf(' ') + 1);
            String[] userCommandArray = line.split(" ");

            // Check command
            if (userCommandArray[0].equals("bye")) {
                // Bye
                printer("Martin:\nBye Sir, see you tomorrow!");
                break;
            }
            if (userCommandArray[0].equals("list")) {
                // List Activities
                printHorizontalLine();
                System.out.println("Martin's To-do List:");
                for (int i = 0; i < taskCounter; i++) {
                    String marked = tasks[i].getTaskDone() ? "X" : " ";
                    System.out.printf("%d. [%s] %s\n", i + 1, marked, tasks[i].getTask());
                }
                printHorizontalLine();
            } else if (userCommandArray[0].equals("mark")) {
                // Mark activity as done
                int itemIndex = Integer.parseInt(userCommandArray[1]);
                if (itemIndex > tasks.length) {
                    System.out.println("Martin:\nSir, you have keyed in an invalid task index. Please try again");
                    continue;
                }
                int markIndex = itemIndex - 1;
                tasks[markIndex].markAsDone();
                printer(String.format("Martin:\nGood news Sir! I have marked %d. %s as done.", itemIndex, tasks[markIndex].getTask()));
            } else if (userCommandArray[0].equals("unmark")) {
                // Unmark activity as done
                int itemIndex = Integer.parseInt(userCommandArray[1]);
                if (itemIndex > tasks.length) {
                    System.out.println("Martin:\nSir, you have keyed in an invalid task index. Please try again");
                    continue;
                }
                int markIndex = itemIndex - 1;
                tasks[markIndex].unmarkAsDone();
                printer(String.format("Martin:\nSorry Sir, I have to remark %d. %s as undone.", itemIndex, tasks[markIndex].getTask()));
            } else if (userCommandArray[0].equals("todo")) {
                Todo task = new Todo(stringAfterCommand);
                storeActivity(task);
                printer("Martin:\nYes Sir, I have added this task to my list:\n" + "    [" + task.getTypeOfTask() + "]" + "[ ] " + task.getTask() + "\nI currently have " + taskCounter + " tasks in the list.");
            } else if (userCommandArray[0].equals("deadline")) {
                String description = stringAfterCommand.substring(0,stringAfterCommand.indexOf("/by") - 1);
                String deadline = stringAfterCommand.substring(stringAfterCommand.indexOf("/by") + 4);
                Deadline task = new Deadline(description,deadline);
                storeActivity(task);
                printer("Martin:\nYes Sir, I have added this task to my list:\n" + "    [" + task.getTypeOfTask() + "]" + "[ ] " + task.getTask() + "\nI currently have " + taskCounter + " tasks in the list.");
            } else if (userCommandArray[0].equals("event")) {
                String description = stringAfterCommand.substring(0,stringAfterCommand.indexOf("/from") - 1);
                String startDate = stringAfterCommand.substring(stringAfterCommand.indexOf("/from") + 6, stringAfterCommand.indexOf("/to") - 1);
                String endDate = stringAfterCommand.substring(stringAfterCommand.indexOf("/to") + 4);
                Event task = new Event(description,startDate,endDate);
                storeActivity(task);
                printer("Martin:\nYes Sir, I have added this task to my list:\n" + "    [" + task.getTypeOfTask() + "]" + "[ ] " + task.getTask() + "\nI currently have " + taskCounter + " tasks in the list.");
            }
        }
    }
}
