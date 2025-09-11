package project.To_Do_List;

import java.io.*;
import java.util.*;

public class TaskManager {
    private final String FILE_NAME = "tasks.csv";
    private List<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            createSampleTasks();
        }
        loadTasks();
    }

    private void createSampleTasks() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            pw.println("TaskName,EstimatedTime,ActualTime,IsDone");
            for (int i = 1; i <= 20; i++) {
                pw.println("Task " + i + "," + (2 * i) + ",0,false");
            }
        } catch (IOException e) {
            System.out.println("Error creating sample tasks.");
        }
    }

    private void loadTasks() {
        tasks.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String name = parts[0];
                    int estimated = Integer.parseInt(parts[1]);
                    int actual = Integer.parseInt(parts[2]);
                    boolean done = Boolean.parseBoolean(parts[3]);
                    tasks.add(new Task(name, estimated, actual, done));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks.");
        }
    }

    public void saveTasks() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            pw.println("TaskName,EstimatedTime,ActualTime,IsDone");
            for (Task task : tasks) {
                pw.println(task.getTaskName() + "," +
                        task.getEstimatedTime() + "," +
                        task.getActualTime() + "," +
                        task.isDone());
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }

    public List<Task> getTasks() { return tasks; }

    public void addTask(Task task) {
        tasks.add(task);
        saveTasks();
    }
}

