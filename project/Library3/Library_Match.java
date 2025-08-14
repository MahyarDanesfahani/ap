package project.Library3;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Library_Match {

    String Name_Library ;
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<BorrowRequest> borrowRequests = new ArrayList<>();
    private ArrayList<Librarian> librarian = new ArrayList<>();
    private static final String STUDENT_FILE = "students.json" ;

    private Scanner scanner = new Scanner(System.in);


    public Library_Match(String Name_Library){
        this.Name_Library= Name_Library;
        loadStudents();
    }
    public Library_Match(){

    }

    public void register_Student(){
        Student new_Student = new Student(scanner);
        for (Student s : students){
            if (s.getUsername().equalsIgnoreCase(new_Student.getUsername())){
                System.out.println("Username already exists. Registration failed . ");
                return;
            }
        }
        students.add(new_Student);
        saveStudents();
        System.out.println("Student registered successfully . ");
    }

    private void saveStudents(){
        try(Writer writer = new FileWriter(STUDENT_FILE)){
            new Gson().toJson(students,writer);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void loadStudents(){
        try (Reader reader = new FileReader(STUDENT_FILE)){
            students = new Gson().fromJson(reader,new TypeToken<ArrayList<Student>>(){}.getType());
            if (students == null){
                students = new ArrayList<>();
            }
        } catch (IOException e){
            students = new ArrayList<>();
        }
    }

    public void loginStudent(){
        System.out.println("Enter username : ");
        String username = scanner.nextLine();
        System.out.println("Enter password : ");
        String passInput = scanner.nextLine();

        for (Student s : students){
            if (s.getUsername().equalsIgnoreCase(username)){
                if (!s.isActive()){
                    System.out.println("Your account is inactive . Please contact the library .");
                    return;
                }
                if (String.valueOf(s.getPassword()).equals(passInput)){
                    System.out.println("Login successful . Welcome, " + s.getFirst_last_Name() + "!");
                    return;
                } else {
                    System.out.println("Incorrect password .");
                    return;
                }
            }
        }
        System.out.println("No student found with that username . ");
    }

    public void searchBook_Student(){
        System.out.println("\n*** Search Book ***" +
                "\nLeave any field empty if you don't want to filter by it ." +
                "\nEnter book title (or press Enter to skip) : ");
        String title = scanner.nextLine().trim().toLowerCase();
        System.out.println("Enter author name (or press Enter to skip) : ");
        String author = scanner.nextLine().trim().toLowerCase();
        System.out.println("Enter year of publication (or press Enter to skip) : ");
        String yearInput = scanner.nextLine().trim();

        boolean found = false;
        for (Book b : books){
            boolean match =true;

            if (!title.isEmpty() && !b.getBook_Name().toLowerCase().contains(title)) match = false;
            if (!author.isEmpty() && !b.getAuthor_Name().toLowerCase().contains(author)) match = false;
            if (!yearInput.isEmpty()){
                try {
                    int year = Integer.parseInt(yearInput);
                    if (b.getYearPublication() != year) match = false;
                } catch (NumberFormatException e){
                    System.out.println("Invalid year format . Skipping year filter .");
                }
            }
            if (match){
                System.out.println(b);
                found = true;
            }
        }

        if (!found){
            System.out.println("No books found matching your criteria .");
        }

    }

    public void requestBorrowBook_Student(Student currentStudent){
        System.out.println("\n### Borrow Book Request ###");

        for (int i=0 ; i<books.size() ; i++){
            System.out.println((i+1) + ". " + books.get(i));
        }
        System.out.println("Enter book number to request : ");
        int choice = Integer.parseInt(scanner.nextLine()) - 1;

        if (choice < 0 || choice >= books.size()){
            System.out.println("Invalid selection .");
            return;
        }

        Book selectedBook = books.get(choice);
        if (!selectedBook.isAvailable()){
            System.out.println("Sorry, this book is not available right now .");
            return;
        }

        System.out.println("Enter start date (YYYY-MM-DD) : ");
        LocalDate start = LocalDate.parse(scanner.nextLine());
        System.out.println("Enter end date (YYYY-MM-DD) : ");
        LocalDate end = LocalDate.parse(scanner.nextLine());

        BorrowRequest request = new BorrowRequest(currentStudent,selectedBook,start,end);
        borrowRequests.add(request);
        System.out.println("Your request has been submitted and is pending approval .");

    }

    public Student findStudentByID(String id){
        for (Student s : students){
            if (s.getStudentNumber().equals(id)){
                return s;
            }
        }
        return null;
    }

    public List<BorrowRequest> getBorrowRequest(){
        return borrowRequests;
    }


    public void viewStudentCount_Guest(){
        System.out.println("Total registered students: " + students.size());
    }

    public void searchBook_Guest(){
        System.out.println("Enter the book title to search: ");
        String title = scanner.nextLine();
        boolean found = false;

        for (Book book : books){
            if (book.getBook_Name().equalsIgnoreCase(title)){
                System.out.println("** The Book is : **");
                book.toString();
                found = true;
            }
        }
        if (!found){
            System.out.println("No book found with that title . ");
        }
    }

    public void viewLibraryStatistics_Guest(){
        System.out.println("===== Library Statistics =====" +
                "\nTotal Students : " + students.size() +
                "\nTotal Books : " + books.size() +
                "\nTotal Borrows : " + borrowRequests.size());
        if (borrowRequests.isEmpty()){
            System.out.println("No books are currently borrowed . ");
        } else {
            System.out.println("Last Borrowed Books : ");
            int count = 0 ;
            for (int i = borrowRequests.size()-1 ; i>=0 && count < 10 ; i--){
                BorrowRequest b = borrowRequests.get(i);
                System.out.println("_ " + b.getBook().getBook_Name() +
                        "(Borrowed by : " + b.getStudent().getFirst_last_Name()  + ")");
                count++;
            }
        }
    }


    public Librarian login_Librarian(){
        Scanner s = new Scanner(System.in);
        System.out.println("Enter username : ");
        String username = s.nextLine();
        System.out.println("Enter password : ");
        String password = s.nextLine();

        for (Librarian l : librarian){
            if (l.getUsername_Librarian().equals(username) && l.getPassword_Librarian().equals(password)){
                System.out.println("*! Welcome " + l.getName_Librarian() +" !*");
                return l;
            }
        }
        System.out.println("Invalid username or password.");
        return null;
    }

    public void saveLibrarianToFile(){
        try (FileWriter writer =new FileWriter("librarians.json")){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(librarian,writer);
        } catch (IOException e){
            System.out.println("Error saving librarians : " + e.getMessage());
        }
    }

    public void changePassword_Librarian(Librarian librarian,Scanner scanner){
        System.out.println("Enter current password: ");
        String oldPassword = scanner.nextLine();

        if (!librarian.getPassword_Librarian().equals(oldPassword)){
            System.out.println("Incorrect current password . Password not changed . ");
            return;
        }
        System.out.println("Enter new password : ");
        String newPassword = scanner.nextLine();
        librarian.setPassword_Librarian(newPassword);
        System.out.println("Password updated successfully !!!");

        saveLibrarianToFile();
    }

}
