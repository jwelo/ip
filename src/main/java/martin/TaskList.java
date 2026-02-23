package martin;

import java.util.ArrayList;
import java.util.List;
import martin.task.Task;

public class TaskList {
    private List<Task> tasks;

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task deleteTask(int index) {
        return tasks.remove(index - 1);
    }

    public Task getTask(int index) {
        return tasks.get(index - 1);
    }

    public int getTasksSize() {
        return tasks.size();
    }

    public List<Task> getAllTasks() {
        return tasks;
    }
}