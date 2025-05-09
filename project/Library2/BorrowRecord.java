package project.Library2;

import java.time.LocalDate;

public class BorrowRecord {
    private Student student;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    public BorrowRecord(Student student, Book book) {
        this.student = student;
        this.book = book;
        this.borrowDate = LocalDate.now();
        this.returnDate = borrowDate.plusMonths(1);
    }

    public Student getStudent() {
        return student;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    @Override
    public String toString() {
        return "BorrowRecord{" +
                "student = " + student.getFirstLastName() +
                ", book = " + book.getBookName() +
                ", borrowDate = " + borrowDate +
                ", returnDate = " + returnDate +
                '}';
    }
}