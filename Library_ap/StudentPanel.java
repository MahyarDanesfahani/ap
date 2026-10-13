package project.Library3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/** Home page of a logged-in student : search , borrow request and history . */
public class StudentPanel extends JPanel {

    private final LibraryFrame frame;
    private final JLabel greeting = Ui.label("");

    private final JTextField searchTitle = new JTextField(14);
    private final JTextField searchAuthor = new JTextField(14);
    private final JTextField searchYear = new JTextField(8);

    private final DefaultTableModel bookModel = Ui.model(
            "کد", "عنوان", "نویسنده", "سال انتشار", "نسخه موجود", "وضعیت");
    private final JTable bookTable = Ui.table(bookModel);
    private List<Book> visibleBooks = new ArrayList<>();

    private final DefaultTableModel historyModel = Ui.model(
            "کتاب", "شروع", "پایان", "تایید شده", "تحویل شده", "تاریخ تحویل");
    private final JTable historyTable = Ui.table(historyModel);

    public StudentPanel(LibraryFrame frame){
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Ui.PAGE_BG);

        JPanel header = Ui.pageHeader("پنل دانشجو");
        greeting.setForeground(Color.WHITE);
        greeting.setBorder(new EmptyBorder(0, 40, 0, 40));
        header.add(greeting, BorderLayout.CENTER);
        JButton logout = Ui.button("خروج از حساب");
        logout.addActionListener(e -> frame.logout());
        header.add(logout, BorderLayout.LINE_END);
        add(header, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(Ui.NORMAL_FONT);
        tabs.addTab("کتاب‌ها و امانت", booksTab());
        tabs.addTab("تاریخچه امانت‌های من", historyTab());
        add(tabs, BorderLayout.CENTER);
    }

    // Called every time a student logs in .
    public void onLogin(){
        greeting.setText("خوش آمدید، " + frame.currentStudent().getFirst_last_Name());
        loadBooks();
        loadHistory();
    }

    private JPanel booksTab(){
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(Ui.PAGE_BG);
        p.setBorder(new EmptyBorder(14, 16, 14, 16));

        JPanel search = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        search.setOpaque(false);
        search.add(Ui.label("عنوان :"));
        search.add(searchTitle);
        search.add(Ui.label("نویسنده :"));
        search.add(searchAuthor);
        search.add(Ui.label("سال :"));
        search.add(searchYear);
        JButton searchBtn = Ui.button("جستجو");
        searchBtn.addActionListener(e -> loadBooks());
        search.add(searchBtn);
        JButton allBtn = Ui.button("نمایش همه");
        allBtn.addActionListener(e -> {
            searchTitle.setText("");
            searchAuthor.setText("");
            searchYear.setText("");
            loadBooks();
        });
        search.add(allBtn);
        p.add(search, BorderLayout.NORTH);

        p.add(new JScrollPane(bookTable), BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actions.setOpaque(false);
        JButton borrowBtn = Ui.button("درخواست امانت کتاب انتخاب‌شده");
        borrowBtn.addActionListener(e -> requestBorrow());
        actions.add(borrowBtn);
        p.add(actions, BorderLayout.SOUTH);
        return p;
    }

    private JPanel historyTab(){
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(Ui.PAGE_BG);
        p.setBorder(new EmptyBorder(14, 16, 14, 16));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
        top.setOpaque(false);
        JButton refresh = Ui.button("بروزرسانی");
        refresh.addActionListener(e -> loadHistory());
        top.add(refresh);
        p.add(top, BorderLayout.NORTH);

        p.add(new JScrollPane(historyTable), BorderLayout.CENTER);
        return p;
    }

    private void loadBooks(){
        Integer year = null;
        String yearInput = searchYear.getText().trim();
        if (!yearInput.isEmpty()){
            try {
                year = Integer.parseInt(yearInput);
            } catch (NumberFormatException ex){
                Ui.error(this, "سال را به صورت عدد وارد کنید .");
                return;
            }
        }
        List<Book> found = frame.lib().searchBooks(searchTitle.getText(), searchAuthor.getText(), year);
        visibleBooks = found;
        bookModel.setRowCount(0);
        for (Book b : found){
            bookModel.addRow(new Object[]{
                    b.getCode_Book(),
                    b.getBook_Name(),
                    b.getAuthor_Name(),
                    b.getYearPublication(),
                    b.getQuantityAvailable(),
                    b.isAvailable() ? "موجود" : "ناموجود"});
        }
    }

    private void requestBorrow(){
        int viewRow = bookTable.getSelectedRow();
        if (viewRow < 0){
            Ui.error(this, "ابتدا یک کتاب را از جدول انتخاب کنید .");
            return;
        }
        Book book = visibleBooks.get(bookTable.convertRowIndexToModel(viewRow));
        if (!book.isAvailable()){
            Ui.error(this, "موجودی این کتاب تمام شده است .");
            return;
        }

        JTextField endField = new JTextField(LocalDate.now().plusDays(14).toString());
        JPanel form = new JPanel(new GridLayout(0, 1, 6, 6));
        form.setBorder(new EmptyBorder(4, 10, 10, 10));
        form.add(Ui.label("کتاب : " + book.getBook_Name()));
        form.add(Ui.label("تاریخ شروع : امروز (" + LocalDate.now() + ")"));
        form.add(Ui.label("تاریخ پایان (YYYY-MM-DD) :"));
        form.add(endField);

        JOptionPane pane = new JOptionPane(form, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog d = Ui.dialog(pane, this, "درخواست امانت");
        d.setVisible(true);
        if (!Integer.valueOf(JOptionPane.OK_OPTION).equals(pane.getValue())){
            return;
        }
        LocalDate end;
        try {
            end = LocalDate.parse(endField.getText().trim());
        } catch (Exception ex){
            Ui.error(this, "قالب تاریخ درست نیست (YYYY-MM-DD) .");
            return;
        }
        String error = frame.lib().requestBorrow(frame.currentStudent(), book, end);
        if (error == null){
            Ui.info(this, "درخواست امانت ثبت شد و در انتظار تایید کتابدار است .");
        } else {
            Ui.error(this, error);
        }
        loadHistory();
    }

    private void loadHistory(){
        historyModel.setRowCount(0);
        for (BorrowRequest br : frame.lib().getStudentBorrowHistory(frame.currentStudent())){
            historyModel.addRow(new Object[]{
                    br.getBook().getBook_Name(),
                    br.getStartDate(),
                    br.getEndDate(),
                    br.isApproved() ? "بله" : "خیر",
                    br.isReturned() ? "بله" : "خیر",
                    br.getReturnDate() == null ? "-" : br.getReturnDate().toString()});
        }
    }
}
