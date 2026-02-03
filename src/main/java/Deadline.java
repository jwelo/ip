package src.main.java;

public class Deadline extends Task {
    private String deadline;

    public void changeDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Deadline(String description, String deadline) {
        super(description);
        changeTypeOfTask("D");
        changeTaskDescription(description + " (by: " + deadline + ")");
    }
}
