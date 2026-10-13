package project.Library3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/** Manager dashboard : librarians , borrowing statistics and student statistics . */
public class ManagerPanel extends JPanel {

    private final LibraryFrame frame;
    private final JLabel greeting = Ui.label("کاربر : مدیر");

    // ---- librarians tab ----
    private final DefaultTableModel librarianModel = Ui.model(
            "نام", "نام کاربری", "کتاب اضافه‌شده", "امانت داده", "تحویل گرفته");
    private final JTable librarianTable = Ui.table(librarianModel);

    // ---- borrowing statistics tab ----
    private final JLabel statRequests = Ui.headerLabel("0");
    private final JLabel statApproved = Ui.headerLabel("0");
    private final JLabel statAverage = Ui.headerLabel("0");

    // ---- student statistics tab ----
    private final DefaultTableModel studentModel = Ui.model(
            "دانشجو", "نام کاربری", "کل درخواست‌ها", "تحویل‌نداده", "دیرکرد");
    private final JTable studentTable = Ui.table(studentModel);
    private final DefaultTableModel topModel = Ui.model("دانشجو", "تعداد دیرکرد");
    private final JTable topTable = Ui.table(topModel);

    public ManagerPanel(LibraryFrame frame){
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Ui.PAGE_BG);

        JPanel header = Ui.pageHeader("پنل مدیر کتابخانه");
        greeting.setForeground(Color.WHITE);
        greeting.setBorder(new EmptyBorder(0, 40, 0, 40));
        header.add(greeting, BorderLayout.CENTER);
        JButton logout = Ui.button("خروج از حساب");
        logout.addActionListener(e -> frame.logout());
        header.add(logout, BorderLayout.LINE_END);
        add(header, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(Ui.NORMAL_FONT);
        tabs.addTab("کتابدارها", librariansTab());
        tabs.addTab("آمار امانت‌ها", borrowStatsTab());
        tabs.addTab("آمار دانشجوها", studentStatsTab());
        add(tabs, BorderLayout.CENTER);
    }

    // Called every time the manager logs in .
    public void refresh(){
        refreshLibrarians();
        refreshBorrowStats();
        refreshStudentStats();
    }

    // ================= librarians =================

    private JPanel librariansTab(){
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(Ui.PAGE_BG);
        p.setBorder(new EmptyBorder(14, 16, 14, 16));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
        top.setOpaque(false);
        JButton refresh = Ui.button("بروزرسانی");
        refresh.addActionListener(e -> refreshLibrarians());
        top.add(refresh);
        JButton add = Ui.button("افزودن کتابدار");
        add.addActionListener(e -> addLibrarian());
        top.add(add);
        p.add(top, BorderLayout.NORTH);

        p.add(new JScrollPane(librarianTable), BorderLayout.CENTER);
        return p;
    }

    private void refreshLibrarians(){
        librarianModel.setRowCount(0);
        for (Librarian l : frame.lib().getLibrarians()){
            librarianModel.addRow(new Object[]{
                    l.getName_Librarian(),
                    l.getUsername_Librarian(),
                    l.getBookAdded(),
                    l.getBookBorrowed(),
                    l.getBookReturned()});
        }
    }

    private void addLibrarian(){
        JTextField name = new JTextField(16);
        JTextField username = new JTextField(16);
        JTextField password = new JTextField(16);
        JPanel form = Ui.formPanel("مشخصات کتابدار جدید",
                Ui.fieldRow("نام و نام خانوادگی : ", name),
                Ui.fieldRow("نام کاربری : ", username),
                Ui.fieldRow("رمز عبور : ", password));
        if (!Ui.showForm(this, "افزودن کتابدار", form)){
            return;
        }
        String error = frame.lib().addLibrarian(name.getText(), username.getText(), password.getText());
        if (error == null){
            Ui.info(this, "کتابدار با موفقیت اضافه شد .");
            refreshLibrarians();
        } else {
            Ui.error(this, error);
        }
    }

    // ================= borrowing statistics =================

    private JPanel borrowStatsTab(){
        JPanel p = new JPanel(new BorderLayout(0, 12));
        p.setBackground(Ui.PAGE_BG);
        p.setBorder(new EmptyBorder(14, 16, 14, 16));

        JPanel stats = new JPanel(new GridLayout(1, 3, 14, 0));
        stats.setOpaque(false);
        stats.add(statBox("کل درخواست‌های امانت", statRequests));
        stats.add(statBox("درخواست‌های تاییدشده", statApproved));
        stats.add(statBox("میانگین مدت امانت (روز)", statAverage));
        p.add(stats, BorderLayout.NORTH);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.setOpaque(false);
        JButton refresh = Ui.button("بروزرسانی آمار");
        refresh.addActionListener(e -> refreshBorrowStats());
        bottom.add(refresh);
        p.add(bottom, BorderLayout.CENTER);
        return p;
    }

    private void refreshBorrowStats(){
        statRequests.setText(String.valueOf(frame.lib().getBorrowRequest().size()));
        statApproved.setText(String.valueOf(frame.lib().countApprovedBorrows()));
        statAverage.setText(String.format("%.1f", frame.lib().getAverageBorrowDurationDays()));
    }

    private JPanel statBox(String title, JLabel value){
        JPanel box = new JPanel(new BorderLayout());
        box.setBackground(java.awt.Color.WHITE);
        box.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0xD5DCE4)),
                new EmptyBorder(14, 16, 14, 16)));
        JLabel t = Ui.label(title);
        t.setForeground(new java.awt.Color(0x5B6B7B));
        box.add(t, BorderLayout.NORTH);
        box.add(value, BorderLayout.CENTER);
        return box;
    }

    // ================= student statistics =================

    private JPanel studentStatsTab(){
        JPanel p = new JPanel(new GridLayout(2, 1, 0, 10));
        p.setBackground(Ui.PAGE_BG);
        p.setBorder(new EmptyBorder(14, 16, 14, 16));

        JPanel studentsPanel = new JPanel(new BorderLayout(0, 6));
        studentsPanel.setOpaque(false);
        studentsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("آمار تک‌تک دانشجوها"));
        studentsPanel.add(new JScrollPane(studentTable), BorderLayout.CENTER);
        p.add(studentsPanel);

        JPanel topPanel = new JPanel(new BorderLayout(0, 6));
        topPanel.setOpaque(false);
        topPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("۱۰ دانشجو با بیشترین دیرکرد"));
        topPanel.add(new JScrollPane(topTable), BorderLayout.CENTER);
        p.add(topPanel);

        return p;
    }

    private void refreshStudentStats(){
        Map<Student, Long> lateCount = new HashMap<>();
        studentModel.setRowCount(0);
        for (Student s : frame.lib().getStudents()){
            String username = s.getUsername();
            long total = 0;
            long open = 0;
            long late = 0;
            for (BorrowRequest br : frame.lib().getBorrowRequest()){
                if (!br.getStudent().getUsername().equalsIgnoreCase(username)){
                    continue;
                }
                total++;
                if (br.isApproved() && br.getReturnDate() == null){
                    open++;
                }
                if (br.isApproved() && br.getReturnDate() != null
                        && br.getReturnDate().isAfter(br.getEndDate())){
                    late++;
                }
            }
            lateCount.put(s, late);
            studentModel.addRow(new Object[]{
                    s.getFirst_last_Name(),
                    s.getUsername(),
                    total,
                    open,
                    late});
        }

        topModel.setRowCount(0);
        List<Map.Entry<Student, Long>> sorted = new ArrayList<>(lateCount.entrySet());
        sorted.sort((a, b) -> Long.compare(b.getValue(), a.getValue()));
        for (int i = 0 ; i < sorted.size() && i < 10 ; i++){
            Map.Entry<Student, Long> entry = sorted.get(i);
            topModel.addRow(new Object[]{
                    entry.getKey().getFirst_last_Name() + " (" + entry.getKey().getUsername() + ")",
                    entry.getValue()});
        }
    }
}
