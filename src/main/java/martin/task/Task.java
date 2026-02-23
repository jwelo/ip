package martin.task;

/**
 * Represents a general task in the Martin application.
 * This class serves as a base for specialized task types like Todo, Deadline, and Event.
 */
public class Task {

    private String typeOfTask;
    private boolean isDone;
    private String description;

    /**
     * Returns the code representing the type of task (e.g., "T", "D", "E").
     *
     * @return The task type identifier.
     */
    public String getTypeOfTask() {
        return typeOfTask;
    }

    /**
     * Returns whether the task has been completed.
     *
     * @return true if the task is marked as done, false otherwise.
     */
    public boolean getTaskDone() {
        return isDone;
    }

    /**
     * Returns the description of the task.
     *
     * @return The task description string.
     */
    public String getTaskDescription() {
        return description;
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void unmarkAsDone() {
        isDone = false;
    }

    /**
     * Updates the description of the task.
     *
     * @param description The new description string.
     */
    public void changeTaskDescription(String description) {
        this.description = description;
    }

    /**
     * Updates the type identifier of the task.
     *
     * @param typeOfTask The new type identifier string.
     */
    public void changeTypeOfTask(String typeOfTask) {
        this.typeOfTask = typeOfTask;
    }

    /**
     * Constructs a new Task with a description.
     * By default, the task is not done and has no type assigned.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.typeOfTask = null;
        this.isDone = false;
        this.description = description;
    }

    /**
     * Constructs a new Task with a specific type and description.
     *
     * @param typeOfTask  The type identifier (T, D, or E).
     * @param description The description of the task.
     */
    public Task(String typeOfTask, String description) {
        this.typeOfTask = typeOfTask;
        this.isDone = false;
        this.description = description;
    }

    /**
     * Constructs a new Task with a specific type, status, and description.
     * This constructor is primarily used when loading tasks from storage.
     *
     * @param typeOfTask  The type identifier.
     * @param isDone      A string "true" or "false" representing the task status.
     * @param description The description of the task.
     */
    public Task(String typeOfTask, String isDone, String description) {
        this.typeOfTask = typeOfTask;
        if (isDone.equals("true")) {
            this.isDone = true;
        } else {
            this.isDone = false;
        }
        this.description = description;
    }
}
