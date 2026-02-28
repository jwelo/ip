package martin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import martin.task.Task;

/**
 * Handles loading and saving tasks to a file on the hard drive.
 * This class encapsulates all file I/O operations, ensuring that the
 * TaskList state is persisted between application sessions.
 */
public class Storage {
    private Path path;

    /**
     * Constructs a Storage object with a specified file path.
     *
     * @param filePath The string path of the save file (e.g., "data/martin.txt").
     */
    public Storage(String filePath) {
        this.path = Path.of(filePath);
    }

    /**
     * Reads the save file and parses each line into a Task object.
     * If the file does not exist, it will be created and an empty list will be returned.
     *
     * @return A List of Task objects retrieved from the file.
     * @throws IOException If there is an error reading or creating the file.
     */
    public List<Task> loadSavedTasks() throws IOException {
        if (Files.notExists(path)) {
            Files.createFile(path);
            return new ArrayList<>();
        }

        List<String> lines = Files.readAllLines(path);
        List<Task> loadedTasks = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(";");
            String taskType = parts[0];
            String done = parts[1];
            String description = parts[2];
            Task task = new Task(taskType,done,description);
            if (done.equals("1")) {
                task.markAsDone();
            }
            loadedTasks.add(task);
        }
        return loadedTasks;
    }

    /**
     * Overwrites the save file with the current state of the task list.
     * This is typically used after a task is deleted or a mass update occurs.
     *
     * @param tasks The current list of Task objects to save.
     * @throws IOException If there is an error writing to the file.
     */
    public void saveAllTasks(List<Task> tasks) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Task t : tasks) {
            String line = t.getTypeOfTask() + ";" + (t.getTaskDone() ? "1" : "0") + ";" + t.getTaskDescription();
            lines.add(line);
        }
        Files.write(path, lines,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Appends a single task to the end of the save file.
     * This is more efficient than overwriting the whole file when a new task is added.
     *
     * @param task The Task object to be appended to the file.
     * @throws IOException If there is an error writing to the file.
     */
    public void appendTask(Task task) throws IOException {
        String formattedTaskAsString = task.getTypeOfTask() + ";" + task.getTaskDone() + ";" + task.getTaskDescription() + "\n";
            Files.writeString(path, formattedTaskAsString,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
    }
}
