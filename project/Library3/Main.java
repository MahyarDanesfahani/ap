package project.Library3;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library_Match library_match = new Library_Match("Zanjan Library");

        while (true){
            Menu_Welcome.Chose_Char();
            System.out.println("Select option : ");
            byte answer = scanner.nextByte();
            scanner.nextLine();

            switch (answer){
                case 1:
                    studentMenu(scanner,library_match);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Invalid choice . Please try again .");
            }
        }
    }


    private static void studentMenu(Scanner scanner,Library_Match library_match) {
        while (true) {
            Menu_Student.Welcome_student();
            System.out.println("Select option : ");
            byte answer_Student = scanner.nextByte();
            switch (answer_Student) {
                case 1:
                    library_match.register_Student();
                    break;
                case 2:
                    library_match.loginStudent();
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Invalid choice . Please try again . ");
            }
        }
    }
}
