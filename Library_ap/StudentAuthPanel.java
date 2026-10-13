package project.Library3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/** Student login + registration page . */
public class StudentAuthPanel extends JPanel {

    private final LibraryFrame frame;
    private final JTabbedPane tabs = new JTabbedPane();

    private final JTextField loginUser = new JTextField(18);
    private final JPasswordField loginPass = new JPasswordField(18);

    private final JTextField regName = new JTextField(18);
    private final JTextField regNumber = new JTextField(18);
    private final JTextField regField = new JTextField(18);
    private final JTextField regUser = new JTextField(18);
    private final JPasswordField regPass = new JPasswordField(18);

    public StudentAuthPanel(LibraryFrame frame){
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Ui.PAGE_BG);

        add(Ui.pageHeader("بخش دانشجو"), BorderLayout.NORTH);

        tabs.setFont(Ui.NORMAL_FONT);
        tabs.addTab("ورود", loginTab());
        tabs.addTab("ثبت‌نام", registerTab());
        add(tabs, BorderLayout.CENTER);

        JButton back = Ui.button("بازگشت");
        back.addActionListener(e -> frame.show(LibraryFrame.WELCOME));
        add(Ui.bottomBar(back), BorderLayout.SOUTH);
    }

    private JPanel loginTab(){
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(Ui.PAGE_BG);

        JButton login = Ui.button("ورود");
        login.addActionListener(e -> doLogin());
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRow.setOpaque(false);
        btnRow.add(login);

        JPanel form = Ui.formPanel("ورود دانشجو",
                Ui.fieldRow("نام کاربری : ", loginUser),
                Ui.fieldRow("رمز عبور : ", loginPass),
                btnRow);
        center.add(form);
        return center;
    }

    private JPanel registerTab(){
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(Ui.PAGE_BG);

        JButton register = Ui.button("ثبت‌نام");
        register.addActionListener(e -> doRegister());
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRow.setOpaque(false);
        btnRow.add(register);

        JPanel form = Ui.formPanel("ثبت‌نام دانشجوی جدید",
                Ui.fieldRow("نام و نام خانوادگی : ", regName),
                Ui.fieldRow("شماره دانشجویی : ", regNumber),
                Ui.fieldRow("رشته تحصیلی : ", regField),
                Ui.fieldRow("نام کاربری : ", regUser),
                Ui.fieldRow("رمز عبور (۴ رقم) : ", regPass),
                btnRow);
        center.add(form);
        return center;
    }

    private void doLogin(){
        String username = loginUser.getText().trim();
        String password = new String(loginPass.getPassword());
        if (username.isEmpty() || password.isEmpty()){
            Ui.error(this, "نام کاربری و رمز عبور را وارد کنید .");
            return;
        }
        Student student = frame.lib().authenticateStudent(username, password);
        if (student == null){
            Ui.error(this, "نام کاربری یا رمز عبور اشتباه است .");
            return;
        }
        if (!student.isActive()){
            Ui.error(this, "حساب شما غیرفعال است ; با کتابخانه تماس بگیرید .");
            return;
        }
        frame.showStudentHome(student);
    }

    private void doRegister(){
        String error = frame.lib().registerStudent(
                regName.getText(),
                regNumber.getText(),
                regField.getText(),
                regUser.getText(),
                new String(regPass.getPassword()));
        if (error == null){
            Ui.info(this, "ثبت‌نام با موفقیت انجام شد . اکنون وارد شوید .");
            regName.setText("");
            regNumber.setText("");
            regField.setText("");
            regUser.setText("");
            regPass.setText("");
            tabs.setSelectedIndex(0);
        } else {
            Ui.error(this, error);
        }
    }
}
