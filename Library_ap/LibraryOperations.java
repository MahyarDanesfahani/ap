package project.Library3;

import java.time.LocalDate;
import java.util.List;

/**
 * Contract of the pure business-logic layer .
 * Mutation methods return a String error message ; null means success .
 * Query methods return data for the GUI to display .
 */
public interface LibraryOperations {
    void saveAllData();

    // Student
    String registerStudent(String fullName, String studentNumber,
                           String fieldOfStudy, String username, String password);
    Student authenticateStudent(String username, String password);
    List<Book> searchBooks(String title, String author, Integer year);
    String requestBorrow(Student student, Book book, LocalDate endDate);
    List<BorrowRequest> getStudentBorrowHistory(Student student);
    List<BorrowRequest> getStudentBorrowHistory(String studentUsername);
    String toggleStudentStatus(String username);

    // Guest
    int getStudentCount();
    List<BorrowRequest> getRecentBorrows(int n);

    // Librarian
    Librarian authenticateLibrarian(String username, String password);
    String changeLibrarianPassword(Librarian librarian, String oldPassword, String newPassword);
    String addBook(String code, String name, String author, int year, int copies);
    Book findBook(String codeOrName);
    String updateBook(Book book, String newName, String newAuthor,
                      String newCode, Integer newYear, Integer newCopies);
    List<BorrowRequest> getPendingBorrowRequests();
    String approveBorrowRequest(BorrowRequest request);
    List<BorrowRequest> getActiveBorrows();
    String returnBook(BorrowRequest request);

    // Manager
    String addLibrarian(String name, String username, String password);
    double getAverageBorrowDurationDays();
    long countApprovedBorrows();
}
