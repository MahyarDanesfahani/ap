package project.To_Do_List;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainMenuFrame extends JFrame {
    private User currentUser;
    private UserManager userManager;
    private TaskManager taskManager;

    public MainMenuFrame(User user, UserManager userManager) {
        this.currentUser = user;
        this.userManager = userManager;
        this.taskManager = new TaskManager();

        setTitle("منوی اصلی - TO-DO APP");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));

        JButton infoButton = new JButton("نمایش اطلاعات کاربر");
        JButton changePassButton = new JButton("تغییر رمز عبور");
        JButton showTasksButton = new JButton("نمایش تمام کارها");
        JButton showDoneTasksButton = new JButton("نمایش کارهای انجام شده");
        JButton addTaskButton = new JButton("افزودن کار جدید");
        JButton editTaskButton = new JButton("ویرایش کار");
        JButton exitButton = new JButton("خروج");

        panel.add(infoButton);
        panel.add(changePassButton);
        panel.add(showTasksButton);
        panel.add(showDoneTasksButton);
        panel.add(addTaskButton);
        panel.add(editTaskButton);
        panel.add(exitButton);

        add(panel);

        infoButton.addActionListener(e -> showUserInfo());
        changePassButton.addActionListener(e -> changePassword());
        showTasksButton.addActionListener(e -> showAllTasks());
        showDoneTasksButton.addActionListener(e -> showDoneTasks());
        addTaskButton.addActionListener(e -> addTask());
        editTaskButton.addActionListener(e -> editTask());
        exitButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void showUserInfo() {
        String info = "نام کامل: " + currentUser.getFullName() + "\n" +
                "رشته: " + currentUser.getMajor() + "\n" +
                "شهر: " + currentUser.getCity();
        JOptionPane.showMessageDialog(this, info);
    }

    private void changePassword() {
        String newPass = JOptionPane.showInputDialog(this, "رمز عبور جدید را وارد کنید:");
        if (newPass != null && !newPass.isEmpty()) {
            userManager.changePassword(currentUser, newPass);
            JOptionPane.showMessageDialog(this, "رمز عبور با موفقیت تغییر کرد.");
        }
    }

    private void showAllTasks() {
        java.util.List<Task> tasks = taskManager.getTasks();
        String[] columns = {"نام کار", "زمان تخمینی", "زمان انجام شده", "وضعیت"};
        String[][] data = new String[tasks.size()][4];

        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            data[i][0] = t.getTaskName();
            data[i][1] = String.valueOf(t.getEstimatedTime());
            data[i][2] = String.valueOf(t.getActualTime());
            data[i][3] = t.isDone() ? "✔" : "✖";
        }

        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(this, scrollPane, "لیست تمام کارها", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showDoneTasks() {
        java.util.List<Task> tasks = taskManager.getTasks();
        List<String[]> doneList = new ArrayList<>();
        for (Task t : tasks) {
            if (t.isDone()) {
                doneList.add(new String[]{t.getTaskName(),
                        String.valueOf(t.getEstimatedTime()),
                        String.valueOf(t.getActualTime())});
            }
        }

        if (doneList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "هیچ کاری انجام نشده است.");
            return;
        }

        String[] columns = {"نام کار", "زمان تخمینی", "زمان انجام شده"};
        String[][] data = doneList.toArray(new String[0][]);
        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(this, scrollPane, "لیست کارهای انجام شده", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addTask() {
        String name = JOptionPane.showInputDialog(this, "نام کار جدید:");
        if (name == null || name.trim().isEmpty()) return;
        String estStr = JOptionPane.showInputDialog(this, "زمان تخمینی (ساعت):");
        try {
            int est = Integer.parseInt(estStr);
            Task newTask = new Task(name, est, 0, false);
            taskManager.addTask(newTask);
            JOptionPane.showMessageDialog(this, "کار جدید با موفقیت اضافه شد.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "عدد معتبر وارد کنید!");
        }
    }

    private void editTask() {
        String name = JOptionPane.showInputDialog(this, "نام کار برای ویرایش:");
        if (name == null || name.trim().isEmpty()) return;

        for (Task t : taskManager.getTasks()) {
            if (t.getTaskName().equalsIgnoreCase(name)) {
                try {
                    String actualStr = JOptionPane.showInputDialog(this, "زمان واقعی انجام (ساعت):", t.getActualTime());
                    int actual = Integer.parseInt(actualStr);
                    t.setActualTime(actual);
                    int confirm = JOptionPane.showConfirmDialog(this, "آیا کار انجام شده است؟", "وضعیت", JOptionPane.YES_NO_OPTION);
                    t.setDone(confirm == JOptionPane.YES_OPTION);
                    taskManager.saveTasks();
                    JOptionPane.showMessageDialog(this, "کار با موفقیت ویرایش شد.");
                    return;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "عدد معتبر وارد کنید!");
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(this, "کاری با این نام پیدا نشد.");
    }
}
