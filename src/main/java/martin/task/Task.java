package martin.task;


public class Task {

    private String typeOfTask;
    private boolean isDone;
    private String description;

    public String getTypeOfTask() {
        return typeOfTask;
    }

    public boolean getTaskDone() {
        return isDone;
    }

    public String getTaskDescription() {
        return description;
    }

    public void markAsDone() {
        isDone = true;
    }

    public void unmarkAsDone() {
        isDone = false;
    }

    public void changeTaskDescription(String description) {
        this.description = description;
    }

    public void changeTypeOfTask(String typeOfTask) {
        this.typeOfTask = typeOfTask;
    }

    public Task(String description) {
        this.typeOfTask = null;
        this.isDone = false;
        this.description = description;
    }
}
