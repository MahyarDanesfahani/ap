package project.Library3;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Library_Match {

    String Name_Library ;
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<BorrowRecord> borrowRecords = new ArrayList<>();
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


}
