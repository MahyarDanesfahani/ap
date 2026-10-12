package project.Library3;

import java.util.Scanner;

public class Student {

    private String first_last_Name;
    private String username;
    private String password;
    private String studentNumber;
    private String fieldOfStudy;
    private int Count_Student = 0;
    private boolean active = true;

    public Student(){}

    // used when loading a student row from the database
    public Student(String first_last_Name, String username, String password,
                   String studentNumber, String fieldOfStudy, boolean active){
        this.first_last_Name = first_last_Name;
        this.username = username;
        this.password = password;
        this.studentNumber = studentNumber;
        this.fieldOfStudy = fieldOfStudy;
        this.active = active;
    }

    public Student(Scanner scanner){
        System.out.println("Enter full Name : ");
        this.first_last_Name = scanner.nextLine();
        System.out.println("Enter the Student_Number : ");
        this.studentNumber = scanner.nextLine();
        System.out.println("Enter the Field of study : ");
        this.fieldOfStudy = scanner.nextLine();
        System.out.println("Enter the Username : ");
        this.username = scanner.nextLine();
        System.out.println("Enter the Password (Please 4 number): ");
        String passInput = scanner.nextLine();
        while (!passInput.matches("\\d{4}")){
            System.out.println("Invalid password. Please enter exactly 4 digits :");
            passInput = scanner.nextLine();
        }
        this.password=passInput;
        this.active=true;
        this.Count_Student += 1;
    }

    public String getFirst_last_Name() {
        return first_last_Name;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getStudentNumber() {
        return studentNumber;
    }
    public String getFieldOfStudy() {
        return fieldOfStudy;
    }
    public int getCount_Student() {
        return Count_Student;
    }
    public boolean isActive(){ return active;}
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString(){
        return ("Student { " +
                "\nThe Name : " + this.first_last_Name +
                "\nThe Student_Number : " + this.studentNumber +
                "\nThe Field of study : " + this.fieldOfStudy +
                "\nThe Active is : " + this.active +
                "\n }");
    }
}
