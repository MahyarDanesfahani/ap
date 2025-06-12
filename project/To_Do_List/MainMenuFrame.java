package project.To_Do_List;

import javax.swing.*;
import java.awt.*;

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
        showDoneTasksButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "این بخش به زودی اضافه می‌شود."));
        addTaskButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "این بخش به زودی اضافه می‌شود."));
        editTaskButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "این بخش به زودی اضافه می‌شود."));
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
}
