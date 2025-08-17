package project.Library3;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

public class BorrowRequest implements Serializable {
    private Student student;
    private Book book;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate returnDate;
    private boolean returned;
    private boolean approved;

    public BorrowRequest(Student student,Book book,LocalDate startDate,LocalDate endDate){
        this.student=student;
        this.book=book;
        this.startDate=startDate;
        this.endDate=endDate;
        this.approved=false;
    }
    public BorrowRequest(Student student,Book book,LocalDate startDate,LocalDate endDate,boolean returned){
        this.student=student;
        this.book=book;
        this.startDate=startDate;
        this.endDate=endDate;
        this.returned=returned;
        this.approved=false;
    }

    public Student getStudent() {
        return student;
    }
    public Book getBook() {
        return book;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public boolean isApproved() {
        return approved;
    }
    public void approve() { this.approved = true; }
    public void setApproved(boolean approved) {
        this.approved = approved;
    }
    public boolean isReturned() {
        return returned;
    }
    public void setReturned(boolean returned) {
        this.returned = returned;
    }
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }


    @Override
    public String toString(){
        return "BorrowRequest{" +
                "Student=" + student.getFirst_last_Name() +
                ", Book=" + book.getBook_Name() +
                ", Start=" + startDate +
                ", End=" + endDate +
                ", Approved=" + approved +
                '}';
    }

}
