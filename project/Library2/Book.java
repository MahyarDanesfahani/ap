package project.Library2;

import java.util.Scanner;

public class Book {
    private String bookName ;
    private  String authorName ;
    private int yearPublication ;
    private int numberPages ;
     private int quantityAvailable ;

    public Book(Scanner scanner){
        System.out.println("Enter the book title : ");
        this.bookName = scanner.nextLine();
        System.out.println("Enter the author's name :");
        this.authorName = scanner.nextLine();
        System.out.println("Enter the year of publication :");
        this.yearPublication = scanner.nextInt();
        System.out.println("Enter the number of pages :");
        this.numberPages = scanner.nextInt();
        System.out.println("Enter the number of copies available :");
        this.quantityAvailable = scanner.nextInt();
    }

    public Book(String bookName , String authorName ,int yearPublication , int quantityAvailable){
        this.bookName=bookName;
        this.authorName=authorName;
        this.yearPublication=yearPublication;
        this.quantityAvailable=quantityAvailable;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getYearPublication() {
        return yearPublication;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    @Override
    public String toString(){
        return "Book : " +
                "Name : " + bookName + '\'' +
                ", Author : " + authorName + '\'' +
                ", Year : " + yearPublication + '\'' +
                ", Page : " + numberPages + '\'' +
                ", Available : " + quantityAvailable + '\'' +
                '}' ;
    }
}