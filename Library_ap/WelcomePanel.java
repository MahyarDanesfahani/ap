package project.Library3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/** First page : choosing the role to continue with . */
public class WelcomePanel extends JPanel {

    public WelcomePanel(LibraryFrame frame){
        setLayout(new BorderLayout());
        setBackground(Ui.PAGE_BG);

        add(Ui.pageHeader("سیستم مدیریت کتابخانه"), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(Ui.PAGE_BG);
        JPanel box = new JPanel(new GridLayout(2, 2, 20, 20));
        box.setOpaque(false);
        box.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        box.add(roleButton("دانشجو", e -> frame.show(LibraryFrame.STUDENT_AUTH)));
        box.add(roleButton("مهمان", e -> frame.show(LibraryFrame.GUEST)));
        box.add(roleButton("کتابدار", e -> frame.show(LibraryFrame.LIBRARIAN_AUTH)));
        box.add(roleButton("مدیر کتابخانه", e -> frame.show(LibraryFrame.MANAGER_AUTH)));
        center.add(box);
        add(center, BorderLayout.CENTER);

        JButton exit = Ui.button("خروج از برنامه");
        exit.addActionListener(e -> {
            if (Ui.confirm(this, "از برنامه خارج می‌شوید ؟")){
                System.exit(0);
            }
        });
        add(Ui.bottomBar(exit), BorderLayout.SOUTH);
    }

    private JButton roleButton(String text, ActionListener action){
        JButton b = Ui.button(text);
        b.setFont(Ui.HEADER_FONT);
        b.setPreferredSize(new Dimension(240, 70));
        b.addActionListener(action);
        return b;
    }
}
