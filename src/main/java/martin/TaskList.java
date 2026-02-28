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

    public List<String> findTasksContainingKeyword(String keyword) {
        List<String> tasksContainingKeyword = new ArrayList<>();
        for (int i = 0; i < getTasksSize(); i++) {
            String description = tasks.get(i).getTaskDescription();
            if (description.contains(keyword)) {
                String isDone = " ";
                if (tasks.get(i).getTaskDone()) {
                    isDone = "X";
                }
                tasksContainingKeyword.add("[" + tasks.get(i).getTypeOfTask() + "]" + "[" + isDone + "] " + description);
            }
        }
        return tasksContainingKeyword;
    }
}