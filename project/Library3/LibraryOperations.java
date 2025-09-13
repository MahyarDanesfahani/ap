package project.Library3;

import java.util.Scanner;

public interface LibraryOperations {
    // for Library
    void saveAllData();
    // for Student
    void register_Student();
    Student login_Student();
    void searchBook_Student();
    void requestBorrowBook_Student(Student currentStudent);
    boolean toggleStudentStatus(String userName);
    // for Guest
    void viewStudentCount_Guest();
    void searchBook_Guest();
    void viewLibraryStatistics_Guest();
    // for Librarian
    Librarian login_Librarian();
    void changePassword_Librarian(Librarian librarian, Scanner scanner);
    void addBook_Librarian(Scanner scanner);
    void searchAndEditeBook_Librarian(Scanner scanner);
    void approveBorrowRequest_Librarian(Scanner scanner);
    void toggleStatus_Librarian(Scanner scanner);
    boolean returnBook(String username ,String bookName);
    void returnBookBorrowAndSaveReturnDate_Librarian(Scanner scanner);
    // for Manager
    void addLibrarian_Manager(Scanner scanner);
    void showLibrarianReports_Manager(Scanner scanner);
    void viewBorrowingStatistics_Manager();
    void viewAllStudentStatistics_Manager();
}
