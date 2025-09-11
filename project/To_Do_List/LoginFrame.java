package project.To_Do_List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private UserManager userManager;

    public LoginFrame() {
        userManager = new UserManager();

        setTitle("TO-DO");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel userLabel = new JLabel("نام کاربری : ");
        JTextField userText = new JTextField();
        JLabel passLabel = new JLabel("رمز عبور : ");
        JPasswordField passText = new JPasswordField();

        JButton loginButton = new JButton("ورود ");
        JLabel messageLabel = new JLabel("");

        panel.add(userLabel);
        panel.add(userText);
        panel.add(passLabel);
        panel.add(passText);
        panel.add(new JLabel(""));
        panel.add(loginButton);
        panel.add(new JLabel(""));
        panel.add(messageLabel);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passText.getPassword());

                User user = userManager.login(username, password);
                if (user != null) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "ورود موفقیت‌آمیز بود! خوش آمدید " + user.getFullName());
                    new MainMenuFrame(user, userManager);
                    dispose();
                } else {
                    messageLabel.setText("نام کاربری یا رمز اشتباه است !!! ");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
}

