package martin;

public class Parser {

    /**
     * Extracts the main command word from the user input.
     */
    public static String getCommandWord(String fullCommand) {
        return fullCommand.split(" ", 2)[0];
    }

    /**
     * Extracts the arguments (everything after the command word).
     */
    public static String getInputArguments(String fullCommand) {
        String[] parts = fullCommand.split(" ", 2);
        return parts.length > 1 ? parts[1].trim() : "";
    }

    /**
     * Parses the string argument into an integer index.
     */
    public static int parseIndex(String args) {
        try {
            return Integer.parseInt(args.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("please key in an integer for the task index!");
        }
    }

    /**
     * Parses the arguments for a Deadline task.
     * Returns a String array where [0] is description and [1] is the deadline.
     */
    public static String[] parseDeadline(String args) {
        if (!args.contains("/by")) {
            throw new IllegalArgumentException("please follow the Deadline task format: deadline <description> /by <deadline>.");
        }
        try {
            String description = args.substring(0, args.indexOf("/by")).trim();
            String by = args.substring(args.indexOf("/by") + 3).trim();

            if (description.isEmpty() || by.isEmpty()) {
                throw new IndexOutOfBoundsException();
            }
            return new String[] {description, by};
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("please follow the Deadline task format: deadline <description> /by <deadline>.");
        }
    }

    /**
     * Parses the arguments for an Event task.
     * Returns a String array where [0] is description, [1] is startDate, and [2] is endDate.
     */
    public static String[] parseEvent(String args) {
        if (!(args.contains("/from") && args.contains("/to"))) {
            throw new IllegalArgumentException("please follow the Event task format: event <description> /from <startDate> /to <endDate>.");
        }
        try {
            String description = args.substring(0, args.indexOf("/from")).trim();
            String from = args.substring(args.indexOf("/from") + 5, args.indexOf("/to")).trim();
            String to = args.substring(args.indexOf("/to") + 3).trim();

            if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new IndexOutOfBoundsException();
            }
            return new String[]{description, from, to};
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("please follow the Event task format: event <description> /from <startDate> /to <endDate>.");
        }
    }
}