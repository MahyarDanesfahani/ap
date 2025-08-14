package project.Library3;

import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Path;

public class Librarian implements Serializable {

    private String Name_Librarian;
    private String username_Librarian;
    private String password_Librarian;

    public Librarian(String Name ,String username_Librarian , String password_Librarian){
        this.Name_Librarian=Name;
        this.username_Librarian=username_Librarian;
        this.password_Librarian=password_Librarian;
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

    @Override
    public String toString(){
        return "Librarian { " +
                "\nThe Name : " + this.Name_Librarian +
                "\n} " ;
    }
}
