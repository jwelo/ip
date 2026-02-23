package martin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import martin.task.Task;

public class Storage {
    private Path path;

    public Storage(String filePath) {
        this.path = Path.of(filePath);
    }

    /**
     * Loads tasks from the hard drive.
     * If file doesn't exist, it returns an empty list.
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
     * Overwrites the file with the current list of tasks.
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

    public void appendTask(Task task) throws IOException {
        String formattedTaskAsString = task.getTypeOfTask() + ";" + task.getTaskDone() + ";" + task.getTaskDescription() + "\n";
            Files.writeString(path, formattedTaskAsString,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
    }
}
