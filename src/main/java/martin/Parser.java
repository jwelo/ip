package martin;

/**
 * Handles the logic for making sense of user input.
 * The Parser class extracts command words, parses arguments for different task types,
 * and converts string inputs into appropriate data types like integers.
 */
public class Parser {

    /**
     * Extracts the main command word from the user's full input string.
     * For example, "todo read book" returns "todo".
     *
     * @param fullCommand The raw input string from the user.
     * @return The first word of the command in lowercase.
     */
    public static String getCommandWord(String fullCommand) {
        return fullCommand.split(" ", 2)[0];
    }

    /**
     * Extracts all text following the main command word.
     * For example, "deadline submit report /by tonight" returns "submit report /by tonight".
     *
     * @param fullCommand The raw input string from the user.
     * @return The arguments following the command word, trimmed of leading/trailing whitespace.
     */
    public static String getArgumentsAfterCommand(String fullCommand) {
        String[] parts = fullCommand.split(" ", 2);
        return parts.length > 1 ? parts[1].trim() : "";
    }

    /**
     * Converts a string argument into an integer index.
     * This is primarily used for identifying which task to mark, unmark, or delete.
     *
     * @param args The string representation of the index (e.g., "5").
     * @return The parsed integer.
     * @throws IllegalArgumentException If the input is not a valid integer.
     */
    public static int parseIndex(String args) {
        try {
            return Integer.parseInt(args.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("please key in an integer for the task index!");
        }
    }

    /**
     * Splits the arguments for a Deadline task into description and deadline components.
     *
     * @param args The arguments string following the "deadline" command.
     * @return A String array where index 0 is the description and index 1 is the deadline.
     * @throws IllegalArgumentException If the format is invalid or missing the "/by" keyword.
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
     * Splits the arguments for an Event task into description, start time, and end time.
     *
     * @param args The arguments string following the "event" command.
     * @return A String array where index 0 is the description, 1 is the start time, and 2 is the end time.
     * @throws IllegalArgumentException If the format is invalid or missing the "/from" or "/to" keywords.
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