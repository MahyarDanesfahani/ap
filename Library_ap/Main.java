package project.Library3;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        Library_Match library_match = Library_Match.loadAllData();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            library_match.saveAllData();
            Database.close();
        }));
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            Ui.applyGlobalFont();
            JFrame frame = new LibraryFrame(library_match);
            frame.setVisible(true);
        });
    }
}
