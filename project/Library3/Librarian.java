package project.Library3;

import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Path;

public class Librarian implements Serializable {

    private String Name_Librarian;
    private String username_Librarian;
    private String password_Librarian;
    private int bookAdded;
    private int bookBorrowed;
    private int bookReturned;

    public Librarian(String Name ,String username_Librarian , String password_Librarian){
        this.Name_Librarian=Name;
        this.username_Librarian=username_Librarian;
        this.password_Librarian=password_Librarian;
        this.bookAdded=0;
        this.bookBorrowed=0;
        this.bookReturned=0;
    }
    public Librarian(String username_Librarian , String password_Librarian){
        this.username_Librarian=username_Librarian;
        this.password_Librarian=password_Librarian;
        this.bookAdded=0;
        this.bookBorrowed=0;
        this.bookReturned=0;
    }
    public Librarian(){

    }

    public String getName_Librarian() {
        return Name_Librarian;
    }
    public String getUsername_Librarian() {
        return username_Librarian;
    }
    public String getPassword_Librarian() {
        return password_Librarian;
    }
    public void setUsername_Librarian(String username_Librarian) {
        this.username_Librarian = username_Librarian;
    }
    public void setPassword_Librarian(String password_Librarian) {
        this.password_Librarian = password_Librarian;
    }
    public void incrementBookAdded(){ bookAdded++;}
    public void incrementBookBorrowed(){ bookBorrowed++;}
    public void incrementBookReturned(){ bookReturned++;}
    public int getBookAdded() {
        return bookAdded;
    }
    public int getBookBorrowed() {
        return bookBorrowed;
    }
    public int getBookReturned() {
        return bookReturned;
    }

    public String getReport(){
        return "Librarian : " + this.Name_Librarian +
                "\tUsername : " + this.username_Librarian +
                "\n1_Books added : " + this.bookAdded +
                "\n2_Books borrowed : " + this.bookBorrowed +
                "\n3_Books returned : " + this.bookReturned ;
    }

    @Override
    public String toString(){
        return "Librarian { " +
                "\nThe Name : " + this.Name_Librarian +
                "\n} " ;
    }
}
