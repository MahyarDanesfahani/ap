package project.Library2;

import java.time.LocalDate;
import java.util.Scanner;

public class Student {
    private String firstLastName ;
    private long studentNumber ;
    private String fieldOfStudy ;
    private LocalDate membershipDate ;

    public  Student(Scanner scanner){
        System.out.println("Enter your name : ");
        this.firstLastName = scanner.next();
        System.out.println("Enter your student number : ");
        this.studentNumber = scanner.nextLong();
        System.out.println("Enter your field of study : ");
        this.fieldOfStudy = scanner.next();
        this.membershipDate = LocalDate.now();
    }

    public String getFirstLastName() {
        return firstLastName;
    }

    @Override
    public String toString(){
        return "Student{" +
        ".Name : " + firstLastName + '\'' +
        ",Number : " + studentNumber + '\'' +
        ",Field : " + fieldOfStudy + '\'' +
        ",Membership : " + membershipDate +
        '}';
    }

}