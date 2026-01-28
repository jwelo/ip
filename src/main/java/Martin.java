import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;

public class Martin {
    private static ArrayList<String> listOfActivities = new ArrayList<>();

    private static void storeActivity(String activity) {
        listOfActivities.add(activity);
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
                    System.out.printf("%d. %s\n", i+1, listOfActivities.get(i));

                }
                printHorizontalLine();
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
