import java.util.ArrayList;
import java.util.Scanner;

public class Martin {
    private static ArrayList<String> listOfActivities = new ArrayList<String>();
    private static ArrayList<Boolean> markedAsDone = new ArrayList<Boolean>();

    private static void storeActivity(String activity) {
        listOfActivities.add(activity);
        markedAsDone.add(false);
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
        printer("Martin: \nHello sir my name is Martin.\nWhat can I do for you today?");

        Scanner in = new Scanner(System.in);
        String line = "";

        while (true) {
            // Ask for next command
            System.out.println("User Command: ");
            line = in.nextLine();

            // Check command
            if (line.equals("bye")) {
                // Bye
                printer("Martin:\nBye Sir, see you tomorrow!");
                break;
            }
            if (line.equals("list")) {
                // List Activities
                printHorizontalLine();
                System.out.println("Martin's To-do List:");
                for (int i = 0; i < listOfActivities.size(); i++) {
                    String marked = " ";
                    if (markedAsDone.get(i)) {
                        marked = "X";
                    }
                    System.out.printf("%d. [%s] %s\n", i + 1, marked, listOfActivities.get(i));
                }
                printHorizontalLine();
            } else if (line.startsWith("mark")) {
                // Mark activity as done
                String[] words = line.split(" ");
                int itemIndex = Integer.parseInt(words[1]);
                if (itemIndex > listOfActivities.size()) {
                    System.out.println("Sir, you have keyed in an invalid task index. Please try again");
                    continue;
                }
                int markIndex = itemIndex - 1;
                markedAsDone.set(markIndex, true);
                System.out.printf("Good news Sir! I have marked %d. %s as done.\n", itemIndex, listOfActivities.get(markIndex));
            } else if (line.startsWith("unmark")) {
                // Unmark activity as done
                String[] words = line.split(" ");
                int itemIndex = Integer.parseInt(words[1]);
                if (itemIndex > listOfActivities.size()) {
                    System.out.println("Sir, you have keyed in an invalid task index. Please try again");
                    continue;
                }
                int markIndex = itemIndex - 1;
                markedAsDone.set(markIndex, false);
                System.out.printf("Sorry Sir, I have to remark %d. %s as undone.\n", itemIndex, listOfActivities.get(markIndex));
            } else {
                // Add to listOfActivities
                printer("Martin:\n" + "I will add this to my To-Do List: " + line);
                storeActivity(line);
            }
        }
    }
}
