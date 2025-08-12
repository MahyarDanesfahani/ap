package project.Library3;

import java.util.Scanner;

public class Book {
    private String Book_Name;
    private String Author_Name;
    private int yearPublication;
    private int quantityAvailable;
    private int Count_Book;

    public Book(Scanner scanner){
        System.out.println("Enter the book title : ");
        this.Book_Name = scanner.nextLine();
        System.out.println("Enter the author's name : ");
        this.Author_Name = scanner.nextLine();
        System.out.println("Enter the year of publication : ");
        this.yearPublication = scanner.nextInt();
        System.out.println("Enter the number of copies available : ");
        this.quantityAvailable = scanner.nextInt();
        this.Count_Book += 1 ;
    }

    public Book(String bookName , String authorName ,int yearPublication , int quantityAvailable){
        this.Book_Name=bookName;
        this.Author_Name=authorName;
        this.yearPublication=yearPublication;
        this.quantityAvailable=quantityAvailable;
    }

    public String getBook_Name() {
        return Book_Name;
    }
    public String getAuthor_Name() {
        return Author_Name;
    }
    public int getYearPublication() {
        return yearPublication;
    }
    public int getQuantityAvailable() {
        return quantityAvailable;
    }
    public int getCount_Book() {
        return Count_Book;
    }
    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }
    public boolean isAvailable() { return quantityAvailable > 0;}

    @Override
    public String toString(){
        return "Book { " +
                "\nThe Name : " + this.Book_Name +
                "\nThe Author_Name : " + this.Author_Name +
                "\nThe yaer Publication : " + this.yearPublication +
                "\nThe Quantity available : " + this.getQuantityAvailable() +
                "\nThe Available is : " + (isAvailable() ? "Yes" : "No") +
                "\n }";
    }
}
