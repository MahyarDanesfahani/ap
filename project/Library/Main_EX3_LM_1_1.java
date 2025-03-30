package project.Library;

import java.util.Scanner;

public class Main_EX3_LM_1_1 {
    final static int numBook = 1000 ;
    final static int numStudent = 1000 ;
    final  static int featureBook = 5 ;
    final  static int featureStudent = 5 ;

    final static String[][] Book = new String[numBook][featureBook] ;
    final static String[][] Student = new String[numStudent][featureStudent] ;

    static int bookCount = 0;
    static int studentCount = 0;

    public static void main(String[] args) {
        Samples();

        try (Scanner scanner = new Scanner(System.in)) {
            int choice;
            do {
                System.out.println("Library Management System");
                System.out.println("1. Add Books");
                System.out.println("2. Add Students");
                System.out.println("3. Show Books");
                System.out.println("4. Show Students");
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
                    case 0:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice!!! Please try again . ");
                }
            } while (choice != 0);
        }
    }

    public static void Samples() {

        Book[bookCount++] = new String[]{"The Alchemist", "Paulo Coelho", "208", "1988", "978-0061122415"};
        Book[bookCount++] = new String[]{"1984", "George Orwell", "328", "1949", "978-0451524935"};

        Student[studentCount++] = new String[]{"mahyar", "danesfahani", "403463115", "Computer Science", "09910243739"};
        Student[studentCount++] = new String[]{"Sara", "Ahmadi", "403463111", "Mathematics", "09255557890"};
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
            System.out.println("No students available!! ");
            return;
        }
        System.out.println("\nList of Students : ");
        for (int i = 0; i < studentCount; i++) {
            System.out.println((i + 1) + ". " + Student[i][0] + " " + Student[i][1] + " - " + Student[i][3] + " (ID: " + Student[i][2] + ")");
        }
    }
}
