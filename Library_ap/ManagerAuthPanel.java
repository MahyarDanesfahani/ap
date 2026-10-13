package project.Library3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** Manager login page ( fixed admin account ) . */
public class ManagerAuthPanel extends JPanel {

    private final LibraryFrame frame;
    private final JTextField username = new JTextField(18);
    private final JPasswordField password = new JPasswordField(18);

    public ManagerAuthPanel(LibraryFrame frame){
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Ui.PAGE_BG);

        add(Ui.pageHeader("بخش مدیر کتابخانه"), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(Ui.PAGE_BG);

        JButton login = Ui.button("ورود");
        login.addActionListener(e -> doLogin());
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRow.setOpaque(false);
        btnRow.add(login);

        JPanel form = Ui.formPanel("ورود مدیر",
                Ui.fieldRow("نام کاربری : ", username),
                Ui.fieldRow("رمز عبور : ", password),
                btnRow);
        center.add(form);
        add(center, BorderLayout.CENTER);

        JButton back = Ui.button("بازگشت");
        back.addActionListener(e -> frame.show(LibraryFrame.WELCOME));
        add(Ui.bottomBar(back), BorderLayout.SOUTH);
    }

    private void doLogin(){
        String user = username.getText().trim();
        String pass = new String(password.getPassword());
        if (user.isEmpty() || pass.isEmpty()){
            Ui.error(this, "نام کاربری و رمز عبور را وارد کنید .");
            return;
        }
        if (!Manager.authenticate(user, pass)){
            Ui.error(this, "نام کاربری یا رمز عبور اشتباه است .");
            return;
        }
        username.setText("");
        password.setText("");
        frame.showManagerHome();
    }
}
