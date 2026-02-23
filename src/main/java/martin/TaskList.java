package martin;

import java.util.ArrayList;
import java.util.List;
import martin.task.Task;

/**
 * Represents a list of tasks and provides methods to manipulate the tasks.
 * This class acts as the in-memory data store for the application during runtime.
 */
public class TaskList {
    private List<Task> tasks;

    /**
     * Constructs a TaskList with an existing list of tasks.
     * Useful for loading tasks from storage.
     *
     * @param tasks A list of Task objects.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Constructs an empty TaskList.
     * Useful for starting a fresh session without saved data.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the list.
     *
     * @param task The Task object to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a task from the list based on its display index.
     *
     * @param index The 1-based index of the task to be deleted.
     * @return The Task object that was removed.
     * @throws IndexOutOfBoundsException If the index is out of the list range.
     */
    public Task deleteTask(int index) {
        return tasks.remove(index - 1);
    }

    /**
     * Retrieves a task from the list based on its display index.
     *
     * @param index The 1-based index of the task to retrieve.
     * @return The Task object at the specified index.
     * @throws IndexOutOfBoundsException If the index is out of the list range.
     */
    public Task getTask(int index) {
        return tasks.get(index - 1);
    }

    /**
     * Returns the number of tasks currently in the list.
     *
     * @return The size of the task list.
     */
    public int getTasksSize() {
        return tasks.size();
    }

    /**
     * Returns the underlying list containing all Task objects.
     *
     * @return A List of Task objects.
     */
    public List<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Searches for tasks whose descriptions contain the specified keyword.
     * Returns a list of formatted strings representing the matching tasks.
     *
     * @param keyword The string to search for within task descriptions.
     * @return A list of formatted task strings containing the keyword.
     */
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