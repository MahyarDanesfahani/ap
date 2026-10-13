package project.Library3;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Main window : swaps one page per role / screen using a CardLayout .
 * Holds the shared service ( Library_Match ) and the current logged-in user .
 */
public class LibraryFrame extends JFrame {

    public static final String WELCOME        = "welcome";
    public static final String STUDENT_AUTH   = "studentAuth";
    public static final String STUDENT_HOME   = "studentHome";
    public static final String GUEST          = "guest";
    public static final String LIBRARIAN_AUTH = "librarianAuth";
    public static final String LIBRARIAN_HOME = "librarianHome";
    public static final String MANAGER_AUTH   = "managerAuth";
    public static final String MANAGER_HOME   = "managerHome";

    private final Library_Match lib;
    private Student currentStudent;
    private Librarian currentLibrarian;

    private final CardLayout cards = new CardLayout();
    private final JPanel cardPanel = new JPanel(cards);

    private StudentPanel studentPanel;
    private LibrarianPanel librarianPanel;
    private ManagerPanel managerPanel;

    public LibraryFrame(Library_Match lib){
        this.lib = lib;
        setTitle("سیستم مدیریت کتابخانه — " + lib.getName_Library());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1040, 680);
        setMinimumSize(new Dimension(920, 580));
        setLocationRelativeTo(null);
        cardPanel.setOpaque(true);
        cardPanel.add(new WelcomePanel(this), WELCOME);
        cardPanel.add(new StudentAuthPanel(this), STUDENT_AUTH);
        studentPanel = new StudentPanel(this);
        cardPanel.add(studentPanel, STUDENT_HOME);
        cardPanel.add(new GuestPanel(this), GUEST);
        cardPanel.add(new LibrarianAuthPanel(this), LIBRARIAN_AUTH);
        librarianPanel = new LibrarianPanel(this);
        cardPanel.add(librarianPanel, LIBRARIAN_HOME);
        cardPanel.add(new ManagerAuthPanel(this), MANAGER_AUTH);
        managerPanel = new ManagerPanel(this);
        cardPanel.add(managerPanel, MANAGER_HOME);
        add(cardPanel);

        // must run AFTER every panel is added so the whole tree becomes RTL
        applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        show(WELCOME);
    }

    public void show(String name){
        cards.show(cardPanel, name);
    }

    public Library_Match lib(){
        return lib;
    }

    public Student currentStudent(){
        return currentStudent;
    }

    public Librarian currentLibrarian(){
        return currentLibrarian;
    }

    public void showStudentHome(Student student){
        currentStudent = student;
        studentPanel.onLogin();
        show(STUDENT_HOME);
    }

    public void showLibrarianHome(Librarian librarian){
        currentLibrarian = librarian;
        lib.setCurrentLibrarian(librarian);
        librarianPanel.onLogin();
        show(LIBRARIAN_HOME);
    }

    public void showManagerHome(){
        managerPanel.refresh();
        show(MANAGER_HOME);
    }

    public void logout(){
        currentStudent = null;
        currentLibrarian = null;
        lib.setCurrentLibrarian(null);
        show(WELCOME);
    }
}
