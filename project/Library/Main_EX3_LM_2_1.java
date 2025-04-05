package project.Library;

import java.io.*;
import java.util.Scanner;

public class Main_EX3_LM_2_1 {
    final static int numBook = 1000;
    final static int numStudent = 1000;
    final static int featureBook = 5;
    final static int featureStudent = 5;
    final static int featureLending = 4;

    final static String[][] Book = new String[numBook][featureBook];
    final static String[][] Student = new String[numStudent][featureStudent];
    final static String[][] Lending = new String[numBook][featureLending];

    static int bookCount = 0;
    static int studentCount = 0;
    static int lendingCount = 0;

    public static void main(String[] args) {
        loadBooksFromFile();
        loadStudentsFromFile();
        loadLendingFromFile();

        try (Scanner scanner = new Scanner(System.in)) {
            int choice;
            do {
                System.out.println("\nLibrary Management System");
                System.out.println("1. Add Books");
                System.out.println("2. Add Students");
                System.out.println("3. Show Books");
                System.out.println("4. Show Students");
                System.out.println("5. search Students");
                System.out.println("6. Lending Book");
                System.out.println("7. Show Lending Book");
                System.out.println("8. Save Data to File");
                System.out.println("0. Exit");
                System.out.print("Enter your choice : ");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        addBooks(scanner);
                        break;
                    case 2:
                        addStudents(scanner);
                        break;
                    case 3:
                        showBooks();
                        break;
                    case 4:
                        showStudents();
                        break;
                    case 5:
                        searchStudentByName(scanner);
                        break;
                    case 6:
                        LendingBook(scanner);
                        break;
                    case 7:
                        showLending();
                        break;
                    case 8:
                        saveBooksToFile();
                        saveStudentsToFile();
                        saveLendingToFile();
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice!!! Please try again . ");
                }
            } while (choice != 0);
        }
    }

    public static void addBooks(Scanner scanner) {
        System.out.println("How many books do you want to add ? ");
        int num = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < num && bookCount < numBook; i++) {
            System.out.println("Enter details for book " + (bookCount + 1) + " : ");

            System.out.print("Book Name : ");
            Book[bookCount][0] = scanner.nextLine();

            System.out.print("Author Name : ");
            Book[bookCount][1] = scanner.nextLine();

            System.out.print("Number of Pages : ");
            Book[bookCount][2] = scanner.nextLine();

            System.out.print("Year of Publication : ");
            Book[bookCount][3] = scanner.nextLine();

            System.out.print("ISBN : ");
            Book[bookCount][4] = scanner.nextLine();

            bookCount++;
        }
    }

    public static void addStudents(Scanner scanner) {
        System.out.println("How many students do you want to add ? ");
        int num = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < num && studentCount < numStudent; i++) {
            System.out.println("Enter details for student " + (studentCount + 1) + " : ");

            System.out.print("First Name : ");
            Student[studentCount][0] = scanner.nextLine();

            System.out.print("Last Name : ");
            Student[studentCount][1] = scanner.nextLine();

            System.out.print("Student Number : ");
            Student[studentCount][2] = scanner.nextLine();

            System.out.print("Field of Study : ");
            Student[studentCount][3] = scanner.nextLine();

            System.out.print("Phone Number : ");
            Student[studentCount][4] = scanner.nextLine();

            studentCount++;
        }
    }

    public static void showBooks() {
        if (bookCount == 0) {
            System.out.println("No books available!! ");
            return;
        }
        System.out.println("\nList of Books : ");
        for (int i = 0; i < bookCount; i++) {
            System.out.println((i + 1) + ". " + Book[i][0] + " by " + Book[i][1] + " (" + Book[i][3] + "), ISBN: " + Book[i][4]);
        }
    }

    public static void showStudents() {
        if (studentCount == 0) {
            System.out.println("No students available!!! ");
            return;
        }
        System.out.println("\nList of Students : ");
        for (int i = 0; i < studentCount; i++) {
            System.out.println((i + 1) + ". " + Student[i][0] + " " + Student[i][1] + " - " + Student[i][3] + " (ID: " + Student[i][2] + ")");
        }
    }

    public static void searchStudentByName(Scanner scanner) {
        System.out.print("Enter the first name of the student to search : ");
        String searchName = scanner.nextLine().trim();
        boolean found = false;
        for (int i = 0; i < studentCount; i++) {
            if (Student[i][0].equalsIgnoreCase(searchName)) {
                System.out.println("\nStudent Found : ");
                System.out.println("First Name : " + Student[i][0]);
                System.out.println("Last Name : " + Student[i][1]);
                System.out.println("Student Number : " + Student[i][2]);
                System.out.println("Field of Study : " + Student[i][3]);
                System.out.println("Phone Number : " + Student[i][4]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No student found with the name : " + searchName);
        }
    }

    public static void LendingBook(Scanner scanner) {
        System.out.print("Please enter the name of the book : ");
        String name = scanner.nextLine();

        boolean found = false;
        for (int i = 0; i < bookCount; i++) {
            if (Book[i][0].equalsIgnoreCase(name)) {
                found = true;

                Lending[lendingCount][0] = name;

                System.out.print("Enter student first name : ");
                Lending[lendingCount][1] = scanner.nextLine();

                System.out.print("Enter student number : ");
                Lending[lendingCount][2] = scanner.nextLine();

                System.out.print("Enter phone number : ");
                Lending[lendingCount][3] = scanner.nextLine();

                lendingCount++;
                System.out.println("Book successfully lent . ");
                break;
            }
        }

        if (!found) {
            System.out.println("Book not found . ");
        }
    }

    public static void showLending(){
        if (lendingCount == 0){
            System.out.println("No Book for lending available!!! ");
            return;
        }
        System.out.println("\nList of Book lending");
        for (int i = 0; i < lendingCount; i++) {
            System.out.println("The name Book " + (i+1) + " : " + Lending[i][0] + "\nname student : " + Lending[i][1] +
                    "\nstudent number : " + Lending[i][2] + "\nphone number : " + Lending[i][3]);
        }
    }

    public static void saveLendingToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Lending.txt"))) {
            for (int i = 0; i < lendingCount; i++) {
                writer.println(String.join(",", Lending[i]));
            }
            System.out.println("Lending records saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving lending records: " + e.getMessage());
        }
    }

    public static void loadLendingFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Lending.txt"))) {
            String line;
            while ((line = reader.readLine()) != null && lendingCount < numBook) {
                Lending[lendingCount++] = line.split(",");
            }
            System.out.println("Lending records loaded successfully.");
        } catch (IOException e) {
            System.out.println("No existing lending records found.");
        }
    }

    public static void saveBooksToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Book.txt"))) {
            for (int i = 0; i < bookCount; i++) {
                writer.println(String.join(",", Book[i]));
            }
            System.out.println("Books saved successfully . ");
        } catch (IOException e) {
            System.out.println("Error saving books : " + e.getMessage());
        }
    }

    public static void saveStudentsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Student.txt"))) {
            for (int i = 0; i < studentCount; i++) {
                writer.println(String.join(",", Student[i]));
            }
            System.out.println("Students saved successfully . ");
        } catch (IOException e) {
            System.out.println("Error saving students : " + e.getMessage());
        }
    }

    public static void loadBooksFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Book.txt"))) {
            String line;
            while ((line = reader.readLine()) != null && bookCount < numBook) {
                Book[bookCount++] = line.split(",");
            }
            System.out.println("Books loaded successfully . ");
        } catch (IOException e) {
            System.out.println("No existing books file found . ");
        }
    }

    public static void loadStudentsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Student.txt"))) {
            String line;
            while ((line = reader.readLine()) != null && studentCount < numStudent) {
                Student[studentCount++] = line.split(",");
            }
            System.out.println("Students loaded successfully . ");
        } catch (IOException e) {
            System.out.println("No existing students file found . ");
        }
    }

}
