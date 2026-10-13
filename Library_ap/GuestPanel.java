package project.Library3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/** Guest page : book search and public library statistics . */
public class GuestPanel extends JPanel {

    private final LibraryFrame frame;

    private final JTextField searchTitle = new JTextField(18);
    private final DefaultTableModel bookModel = Ui.model(
            "کد", "عنوان", "نویسنده", "سال انتشار", "نسخه موجود", "وضعیت");
    private final JTable bookTable = Ui.table(bookModel);

    private final JLabel statStudents = Ui.headerLabel("0");
    private final JLabel statBooks = Ui.headerLabel("0");
    private final JLabel statBorrows = Ui.headerLabel("0");
    private final DefaultTableModel recentModel = Ui.model("کتاب", "امانت‌گیرنده", "شروع", "پایان");

    public GuestPanel(LibraryFrame frame){
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Ui.PAGE_BG);

        add(Ui.pageHeader("بخش مهمان"), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(Ui.NORMAL_FONT);
        tabs.addTab("جستجوی کتاب", searchTab());
        tabs.addTab("آمار کتابخانه", statsTab());
        add(tabs, BorderLayout.CENTER);

        JButton back = Ui.button("بازگشت");
        back.addActionListener(e -> frame.show(LibraryFrame.WELCOME));
        add(Ui.bottomBar(back), BorderLayout.SOUTH);

        loadBooks();
        refreshStats();
    }

    private JPanel searchTab(){
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(Ui.PAGE_BG);
        p.setBorder(new EmptyBorder(14, 16, 14, 16));

        JPanel search = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        search.setOpaque(false);
        search.add(Ui.label("عنوان کتاب :"));
        search.add(searchTitle);
        JButton searchBtn = Ui.button("جستجو");
        searchBtn.addActionListener(e -> loadBooks());
        search.add(searchBtn);
        JButton allBtn = Ui.button("نمایش همه");
        allBtn.addActionListener(e -> {
            searchTitle.setText("");
            loadBooks();
        });
        search.add(allBtn);
        p.add(search, BorderLayout.NORTH);

        p.add(new JScrollPane(bookTable), BorderLayout.CENTER);
        return p;
    }

    private JPanel statsTab(){
        JPanel p = new JPanel(new BorderLayout(0, 12));
        p.setBackground(Ui.PAGE_BG);
        p.setBorder(new EmptyBorder(14, 16, 14, 16));

        JPanel stats = new JPanel(new GridLayout(1, 3, 14, 0));
        stats.setOpaque(false);
        stats.add(statBox("دانشجوهای ثبت‌نام‌شده", statStudents));
        stats.add(statBox("کتاب‌های کتابخانه", statBooks));
        stats.add(statBox("کل درخواست‌های امانت", statBorrows));
        p.add(stats, BorderLayout.NORTH);

        JPanel recentPanel = new JPanel(new BorderLayout(0, 6));
        recentPanel.setOpaque(false);
        recentPanel.setBorder(BorderFactory.createTitledBorder("آخرین امانت‌ها (۱۰ مورد)"));
        recentPanel.add(new JScrollPane(Ui.table(recentModel)), BorderLayout.CENTER);
        p.add(recentPanel, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.setOpaque(false);
        JButton refresh = Ui.button("بروزرسانی آمار");
        refresh.addActionListener(e -> refreshStats());
        bottom.add(refresh);
        p.add(bottom, BorderLayout.SOUTH);
        return p;
    }

    private JPanel statBox(String title, JLabel value){
        JPanel box = new JPanel(new BorderLayout());
        box.setBackground(java.awt.Color.WHITE);
        box.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new java.awt.Color(0xD5DCE4)),
                new EmptyBorder(14, 16, 14, 16)));
        JLabel t = Ui.label(title);
        t.setForeground(new java.awt.Color(0x5B6B7B));
        box.add(t, BorderLayout.NORTH);
        box.add(value, BorderLayout.CENTER);
        return box;
    }

    private void loadBooks(){
        bookModel.setRowCount(0);
        for (Book b : frame.lib().searchBooks(searchTitle.getText(), null, null)){
            bookModel.addRow(new Object[]{
                    b.getCode_Book(),
                    b.getBook_Name(),
                    b.getAuthor_Name(),
                    b.getYearPublication(),
                    b.getQuantityAvailable(),
                    b.isAvailable() ? "موجود" : "ناموجود"});
        }
    }

    private void refreshStats(){
        statStudents.setText(String.valueOf(frame.lib().getStudentCount()));
        statBooks.setText(String.valueOf(frame.lib().getBooks().size()));
        statBorrows.setText(String.valueOf(frame.lib().getBorrowRequest().size()));
        recentModel.setRowCount(0);
        List<BorrowRequest> recent = frame.lib().getRecentBorrows(10);
        for (BorrowRequest br : recent){
            recentModel.addRow(new Object[]{
                    br.getBook().getBook_Name(),
                    br.getStudent().getFirst_last_Name(),
                    br.getStartDate(),
                    br.getEndDate()});
        }
    }
}
