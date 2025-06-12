package project.To_Do_List;

import java.io.File;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private final String FILE_NAME = "tasks.xlsx";
    private java.util.List<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            createSampleTasks();
        }
        loadTasks();
    }

    private void createSampleTasks() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Tasks");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("TaskName");
            header.createCell(1).setCellValue("EstimatedTime");
            header.createCell(2).setCellValue("ActualTime");
            header.createCell(3).setCellValue("IsDone");

            for (int i = 1; i <= 20; i++) {
                Row row = sheet.createRow(i);
                row.createCell(0).setCellValue("Task " + i);
                row.createCell(1).setCellValue(2 * i);
                row.createCell(2).setCellValue(0);
                row.createCell(3).setCellValue(false);
            }
            try (FileOutputStream fos = new FileOutputStream(FILE_NAME)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    private void loadTasks() {
        try (FileInputStream fis = new FileInputStream(FILE_NAME);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            tasks.clear();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                String name = row.getCell(0).getStringCellValue();
                int estimated = (int) row.getCell(1).getNumericCellValue();
                int actual = (int) row.getCell(2).getNumericCellValue();
                boolean done = row.getCell(3).getBooleanCellValue();
                tasks.add(new Task(name, estimated, actual, done));
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public void saveTasks() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Tasks");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("TaskName");
            header.createCell(1).setCellValue("EstimatedTime");
            header.createCell(2).setCellValue("ActualTime");
            header.createCell(3).setCellValue("IsDone");

            for (int i = 0; i < tasks.size(); i++) {
                Row row = sheet.createRow(i + 1);
                Task task = tasks.get(i);
                row.createCell(0).setCellValue(task.getTaskName());
                row.createCell(1).setCellValue(task.getEstimatedTime());
                row.createCell(2).setCellValue(task.getActualTime());
                row.createCell(3).setCellValue(task.isDone());
            }
            try (FileOutputStream fos = new FileOutputStream(FILE_NAME)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
        saveTasks();
    }
}
