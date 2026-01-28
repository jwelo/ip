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

    public static void main(String[] args) {
        printHorizontalLine();
        System.out.println("Martin: \nHello sir my name is Martin.\nWhat can I do for you today?");
        printHorizontalLine();
        //System.out.println("Martin: Sorry Sir, Today is my off day. See you tomorrow!");

        Scanner in = new Scanner(System.in);
        System.out.println("User Command: ");
        String line = in.nextLine();

        while (!line.equals("bye")) {
            // echo
            printHorizontalLine();
            System.out.println("Martin:\n" + line);
            printHorizontalLine();

            System.out.println("User Command: ");
            line = in.nextLine();
        }
        printHorizontalLine();
        System.out.println("Martin:\nBye Sir, see you tomorrow!");
        printHorizontalLine();
    }
}
