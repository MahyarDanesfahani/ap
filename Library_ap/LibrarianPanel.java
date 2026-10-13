package project.Library3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/** Librarian dashboard : manage books , approve borrows , returns ,
 *  students , per-student history and the librarian's own account . */
public class LibrarianPanel extends JPanel {

    private final LibraryFrame frame;
    private final JLabel greeting = Ui.label("");

    // ---- books tab ----
    private final JTextField bookFilter = new JTextField(16);
    private final DefaultTableModel bookModel = Ui.model(
            "کد", "عنوان", "نویسنده", "سال انتشار", "نسخه موجود", "وضعیت");
    private final JTable bookTable = Ui.table(bookModel);
    private List<Book> visibleBooks = new ArrayList<>();

    // ---- pending requests tab ----
    private final DefaultTableModel pendingModel = Ui.model("دانشجو", "کتاب", "شروع", "پایان");
    private final JTable pendingTable = Ui.table(pendingModel);
    private List<BorrowRequest> pendingList = new ArrayList<>();

    // ---- returns tab ----
    private final DefaultTableModel activeModel = Ui.model("دانشجو", "کتاب", "شروع", "پایان");
    private final JTable activeTable = Ui.table(activeModel);
    private List<BorrowRequest> activeList = new ArrayList<>();

    // ---- students tab ----
    private final DefaultTableModel studentModel = Ui.model(
            "نام و نام خانوادگی", "نام کاربری", "شماره دانشجویی", "رشته", "وضعیت");
    private final JTable studentTable = Ui.table(studentModel);

    // ---- student history tab ----
    private final JTextField historyUser = new JTextField(16);
    private final DefaultTableModel historyModel = Ui.model(
            "کتاب", "شروع", "پایان", "تایید", "تحویل", "تاریخ تحویل");
    private final JTable historyTable = Ui.table(historyModel);
    private final JLabel sumTotal = Ui.label("تعداد کل امانت‌ها : 0");
    private final JLabel sumOpen = Ui.label("تحویل‌نشده : 0");
    private final JLabel sumLate = Ui.label("با دیرکرد : 0");

    // ---- account tab ----
    private final JPasswordField oldPass = new JPasswordField(18);
    private final JPasswordField newPass = new JPasswordField(18);
    private final JPasswordField repeatPass = new JPasswordField(18);

    public LibrarianPanel(LibraryFrame frame){
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Ui.PAGE_BG);

        JPanel header = Ui.pageHeader("پنل کتابدار");
        greeting.setForeground(Color.WHITE);
        greeting.setBorder(new EmptyBorder(0, 40, 0, 40));
        header.add(greeting, BorderLayout.CENTER);
        JButton logout = Ui.button("خروج از حساب");
        logout.addActionListener(e -> frame.logout());
        header.add(logout, BorderLayout.LINE_END);
        add(header, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(Ui.NORMAL_FONT);
        tabs.addTab("کتاب‌ها", booksTab());
        tabs.addTab("درخواست‌های امانت", pendingTab());
        tabs.addTab("عودت کتاب", returnsTab());
        tabs.addTab("دانشجوها", studentsTab());
        tabs.addTab("تاریخچه دانشجو", historyTab());
        tabs.addTab("حساب کاربری", accountTab());
        add(tabs, BorderLayout.CENTER);
    }

    // Called every time a librarian logs in .
    public void onLogin(){
        Librarian l = frame.currentLibrarian();
        greeting.setText("کاربر : " + l.getName_Librarian());
        refreshBooks();
        refreshPending();
        refreshActive();
        refreshStudents();
        historyModel.setRowCount(0);
        historyUser.setText("");
        oldPass.setText("");
        newPass.setText("");
        repeatPass.setText("");
    }

    // ================= books =================

    private JPanel booksTab(){
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(Ui.PAGE_BG);
        p.setBorder(new EmptyBorder(14, 16, 14, 16));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        top.setOpaque(false);
        top.add(Ui.label("جستجو بر اساس کد یا عنوان :"));
        top.add(bookFilter);
        JButton filterBtn = Ui.button("نمایش");
        filterBtn.addActionListener(e -> refreshBooks());
        top.add(filterBtn);
        JButton addBtn = Ui.button("افزودن کتاب");
        addBtn.addActionListener(e -> addBook());
        top.add(addBtn);
        JButton editBtn = Ui.button("ویرایش کتاب انتخاب‌شده");
        editBtn.addActionListener(e -> editBook());
        top.add(editBtn);
        p.add(top, BorderLayout.NORTH);

        p.add(new JScrollPane(bookTable), BorderLayout.CENTER);
        return p;
    }

    private void refreshBooks(){
        String filter = bookFilter.getText().trim().toLowerCase();
        visibleBooks = new ArrayList<>();
        for (Book b : frame.lib().getBooks()){
            if (filter.isEmpty()
                    || b.getCode_Book().toLowerCase().contains(filter)
                    || b.getBook_Name().toLowerCase().contains(filter)){
                visibleBooks.add(b);
            }
        }
        bookModel.setRowCount(0);
        for (Book b : visibleBooks){
            bookModel.addRow(new Object[]{
                    b.getCode_Book(),
                    b.getBook_Name(),
                    b.getAuthor_Name(),
                    b.getYearPublication(),
                    b.getQuantityAvailable(),
                    b.isAvailable() ? "موجود" : "ناموجود"});
        }
    }

    private void addBook(){
        JTextField code = new JTextField(16);
        JTextField name = new JTextField(16);
        JTextField author = new JTextField(16);
        JTextField year = new JTextField(16);
        JTextField copies = new JTextField(16);
        JPanel form = Ui.formPanel("مشخصات کتاب جدید",
                Ui.fieldRow("کد کتاب : ", code),
                Ui.fieldRow("نام کتاب : ", name),
                Ui.fieldRow("نویسنده : ", author),
                Ui.fieldRow("سال انتشار : ", year),
                Ui.fieldRow("تعداد نسخه : ", copies));
        if (!Ui.showForm(this, "افزودن کتاب", form)){
            return;
        }
        int yearValue;
        int copiesValue;
        try {
            yearValue = Integer.parseInt(year.getText().trim());
            copiesValue = Integer.parseInt(copies.getText().trim());
        } catch (NumberFormatException ex){
            Ui.error(this, "سال انتشار و تعداد نسخه باید عدد باشند .");
            return;
        }
        String error = frame.lib().addBook(code.getText(), name.getText(), author.getText(), yearValue, copiesValue);
        if (error == null){
            Ui.info(this, "کتاب با موفقیت اضافه شد .");
            refreshBooks();
        } else {
            Ui.error(this, error);
        }
    }

    private void editBook(){
        int viewRow = bookTable.getSelectedRow();
        if (viewRow < 0){
            Ui.error(this, "ابتدا یک کتاب را از جدول انتخاب کنید .");
            return;
        }
        Book book = visibleBooks.get(bookTable.convertRowIndexToModel(viewRow));

        JTextField name = new JTextField(book.getBook_Name());
        JTextField author = new JTextField(book.getAuthor_Name());
        JTextField code = new JTextField(book.getCode_Book());
        JTextField year = new JTextField(String.valueOf(book.getYearPublication()));
        JTextField copies = new JTextField(String.valueOf(book.getQuantityAvailable()));
        JPanel form = Ui.formPanel("ویرایش کتاب (کد فعلی : " + book.getCode_Book() + ")",
                Ui.fieldRow("نام کتاب : ", name),
                Ui.fieldRow("نویسنده : ", author),
                Ui.fieldRow("کد کتاب : ", code),
                Ui.fieldRow("سال انتشار : ", year),
                Ui.fieldRow("تعداد نسخه : ", copies));
        if (!Ui.showForm(this, "ویرایش کتاب", form)){
            return;
        }
        int yearValue;
        int copiesValue;
        try {
            yearValue = Integer.parseInt(year.getText().trim());
            copiesValue = Integer.parseInt(copies.getText().trim());
        } catch (NumberFormatException ex){
            Ui.error(this, "سال انتشار و تعداد نسخه باید عدد باشند .");
            return;
        }
        String error = frame.lib().updateBook(book, name.getText(), author.getText(),
                code.getText(), yearValue, copiesValue);
        if (error == null){
            Ui.info(this, "کتاب با موفقیت ویرایش شد .");
            refreshBooks();
        } else {
            Ui.error(this, error);
        }
    }

    // ================= pending requests =================

    private JPanel pendingTab(){
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(Ui.PAGE_BG);
        p.setBorder(new EmptyBorder(14, 16, 14, 16));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
        top.setOpaque(false);
        JButton refresh = Ui.button("بروزرسانی");
        refresh.addActionListener(e -> refreshPending());
        top.add(refresh);
        JButton approve = Ui.button("تایید درخواست انتخاب‌شده");
        approve.addActionListener(e -> approveRequest());
        top.add(approve);
        p.add(top, BorderLayout.NORTH);

        p.add(new JScrollPane(pendingTable), BorderLayout.CENTER);
        return p;
    }

    private void refreshPending(){
        pendingList = frame.lib().getPendingBorrowRequests();
        pendingModel.setRowCount(0);
        for (BorrowRequest br : pendingList){
            pendingModel.addRow(new Object[]{
                    br.getStudent().getFirst_last_Name(),
                    br.getBook().getBook_Name(),
                    br.getStartDate(),
                    br.getEndDate()});
        }
    }

    private void approveRequest(){
        int viewRow = pendingTable.getSelectedRow();
        if (viewRow < 0){
            Ui.error(this, "ابتدا یک درخواست را از جدول انتخاب کنید .");
            return;
        }
        BorrowRequest request = pendingList.get(pendingTable.convertRowIndexToModel(viewRow));
        String question = "درخواست امانت کتاب «" + request.getBook().getBook_Name()
                + "» توسط «" + request.getStudent().getFirst_last_Name() + "» تایید شود ؟";
        if (!Ui.confirm(this, question)){
            return;
        }
        String error = frame.lib().approveBorrowRequest(request);
        if (error == null){
            Ui.info(this, "درخواست با موفقیت تایید شد .");
        } else {
            Ui.error(this, error);
        }
        refreshPending();
        refreshBooks();
        refreshActive();
    }

    // ================= returns =================

    private JPanel returnsTab(){
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(Ui.PAGE_BG);
        p.setBorder(new EmptyBorder(14, 16, 14, 16));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
        top.setOpaque(false);
        top.add(Ui.label("امانت‌های فعال (تحویل‌نشده) :"));
        p.add(top, BorderLayout.NORTH);

        p.add(new JScrollPane(activeTable), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.setOpaque(false);
        JButton refresh = Ui.button("بروزرسانی");
        refresh.addActionListener(e -> refreshActive());
        bottom.add(refresh);
        JButton returnBtn = Ui.button("ثبت عودت کتاب انتخاب‌شده");
        returnBtn.addActionListener(e -> returnBook());
        bottom.add(returnBtn);
        p.add(bottom, BorderLayout.SOUTH);
        return p;
    }

    private void refreshActive(){
        activeList = frame.lib().getActiveBorrows();
        activeModel.setRowCount(0);
        for (BorrowRequest br : activeList){
            activeModel.addRow(new Object[]{
                    br.getStudent().getFirst_last_Name(),
                    br.getBook().getBook_Name(),
                    br.getStartDate(),
                    br.getEndDate()});
        }
    }

    private void returnBook(){
        int viewRow = activeTable.getSelectedRow();
        if (viewRow < 0){
            Ui.error(this, "ابتدا یک امانت فعال را از جدول انتخاب کنید .");
            return;
        }
        BorrowRequest request = activeList.get(activeTable.convertRowIndexToModel(viewRow));
        String question = "عودت کتاب «" + request.getBook().getBook_Name()
                + "» از «" + request.getStudent().getFirst_last_Name() + "» ثبت شود ؟";
        if (!Ui.confirm(this, question)){
            return;
        }
        String error = frame.lib().returnBook(request);
        if (error == null){
            Ui.info(this, "عودت کتاب با موفقیت ثبت شد .");
        } else {
            Ui.error(this, error);
        }
        refreshActive();
        refreshBooks();
    }

    // ================= students =================

    private JPanel studentsTab(){
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(Ui.PAGE_BG);
        p.setBorder(new EmptyBorder(14, 16, 14, 16));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
        top.setOpaque(false);
        JButton refresh = Ui.button("بروزرسانی");
        refresh.addActionListener(e -> refreshStudents());
        top.add(refresh);
        JButton toggle = Ui.button("تغییر وضعیت (فعال/غیرفعال)");
        toggle.addActionListener(e -> toggleStudent());
        top.add(toggle);
        p.add(top, BorderLayout.NORTH);

        p.add(new JScrollPane(studentTable), BorderLayout.CENTER);
        return p;
    }

    private void refreshStudents(){
        studentModel.setRowCount(0);
        for (Student s : frame.lib().getStudents()){
            studentModel.addRow(new Object[]{
                    s.getFirst_last_Name(),
                    s.getUsername(),
                    s.getStudentNumber(),
                    s.getFieldOfStudy(),
                    s.isActive() ? "فعال" : "غیرفعال"});
        }
    }

    private void toggleStudent(){
        int viewRow = studentTable.getSelectedRow();
        if (viewRow < 0){
            Ui.error(this, "ابتدا یک دانشجو را از جدول انتخاب کنید .");
            return;
        }
        int modelRow = studentTable.convertRowIndexToModel(viewRow);
        String username = String.valueOf(studentModel.getValueAt(modelRow, 1));
        String name = String.valueOf(studentModel.getValueAt(modelRow, 0));
        if (!Ui.confirm(this, "وضعیت حساب «" + name + "» برعکس شود ؟")){
            return;
        }
        String error = frame.lib().toggleStudentStatus(username);
        if (error == null){
            Ui.info(this, "وضعیت دانشجو بروزرسانی شد .");
        } else {
            Ui.error(this, error);
        }
        refreshStudents();
    }

    // ================= student history =================

    private JPanel historyTab(){
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(Ui.PAGE_BG);
        p.setBorder(new EmptyBorder(14, 16, 14, 16));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        top.setOpaque(false);
        top.add(Ui.label("نام کاربری دانشجو :"));
        top.add(historyUser);
        JButton show = Ui.button("نمایش تاریخچه");
        show.addActionListener(e -> loadHistory());
        top.add(show);
        p.add(top, BorderLayout.NORTH);

        p.add(new JScrollPane(historyTable), BorderLayout.CENTER);

        JPanel summary = new JPanel(new FlowLayout(FlowLayout.CENTER, 24, 4));
        summary.setOpaque(false);
        summary.add(sumTotal);
        summary.add(sumOpen);
        summary.add(sumLate);
        p.add(summary, BorderLayout.SOUTH);
        return p;
    }

    private void loadHistory(){
        String username = historyUser.getText().trim();
        if (username.isEmpty()){
            Ui.error(this, "نام کاربری دانشجو را وارد کنید .");
            return;
        }
        List<BorrowRequest> history = frame.lib().getStudentBorrowHistory(username);
        historyModel.setRowCount(0);
        int open = 0;
        int late = 0;
        for (BorrowRequest br : history){
            historyModel.addRow(new Object[]{
                    br.getBook().getBook_Name(),
                    br.getStartDate(),
                    br.getEndDate(),
                    br.isApproved() ? "بله" : "خیر",
                    br.isReturned() ? "بله" : "خیر",
                    br.getReturnDate() == null ? "-" : br.getReturnDate().toString()});
            if (br.isApproved() && !br.isReturned()){
                open++;
            }
            if (br.isApproved() && br.getReturnDate() != null
                    && br.getReturnDate().isAfter(br.getEndDate())){
                late++;
            }
        }
        sumTotal.setText("تعداد کل امانت‌ها : " + history.size());
        sumOpen.setText("تحویل‌نشده : " + open);
        sumLate.setText("با دیرکرد : " + late);
        if (history.isEmpty()){
            Ui.info(this, "امانتی برای این نام کاربری پیدا نشد .");
        }
    }

    // ================= account =================

    private JPanel accountTab(){
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Ui.PAGE_BG);
        p.setBorder(new EmptyBorder(20, 16, 16, 16));

        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));
        center.setOpaque(false);

        JButton change = Ui.button("تغییر رمز عبور");
        change.addActionListener(e -> changePassword());
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRow.setOpaque(false);
        btnRow.add(change);

        JPanel form = Ui.formPanel("تغییر رمز عبور",
                Ui.fieldRow("رمز فعلی : ", oldPass),
                Ui.fieldRow("رمز جدید : ", newPass),
                Ui.fieldRow("تکرار رمز جدید : ", repeatPass),
                btnRow);
        center.add(form);
        p.add(center, BorderLayout.CENTER);
        return p;
    }

    private void changePassword(){
        String newPassword = new String(newPass.getPassword());
        if (!newPassword.equals(new String(repeatPass.getPassword()))){
            Ui.error(this, "رمز جدید و تکرار آن یکسان نیستند .");
            return;
        }
        String error = frame.lib().changeLibrarianPassword(
                frame.currentLibrarian(),
                new String(oldPass.getPassword()),
                newPassword);
        if (error == null){
            Ui.info(this, "رمز عبور با موفقیت تغییر کرد .");
            oldPass.setText("");
            newPass.setText("");
            repeatPass.setText("");
        } else {
            Ui.error(this, error);
        }
    }
}
