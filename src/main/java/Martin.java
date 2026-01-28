import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;

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

        // Ask for next command
        Scanner in = new Scanner(System.in);
        System.out.println("User Command: ");
        String line = in.nextLine();

        while (!line.equals("bye")) {
            if (line.equals("list")) {
                printHorizontalLine();
                System.out.println("Martin's To-do List:");
                for (int i = 0; i < listOfActivities.size(); i++) {
                    String marked = " ";
                    if (markedAsDone.get(i)) {
                        marked = "X";
                    }
                    System.out.printf("%d. [%s] %s\n", i+1, marked, listOfActivities.get(i));
                }
                printHorizontalLine();
            } else if (line.startsWith("mark")) {
                String[] words = line.split(" ");
                int markIndex = Integer.parseInt(words[1]) - 1;
                    markedAsDone.set(markIndex, true);
            } else if (line.startsWith("unmark")) {
                String[] words = line.split(" ");
                int markIndex = Integer.parseInt(words[1]) - 1;
                markedAsDone.set(markIndex, false);
            }
            else {
                // echo
                printer("Martin:\n" + "I will add this to my To-Do List: " + line);

                // Add to listOfActivities
                storeActivity(line);
            }
            // Prompt for next command
            System.out.println("User Command: ");
            line = in.nextLine();
        }
        // Bye
        printer("Martin:\nBye Sir, see you tomorrow!");
    }
}
