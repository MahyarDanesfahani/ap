package project.Library2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library("Central Library");

        while (true) {
            Menu.menuWelcome();
            byte answer = scanner.nextByte();
            scanner.nextLine();

            switch (answer) {
                case 1:
                    Menu.menuStudent();
                    byte studentChoice = scanner.nextByte();
                    scanner.nextLine();
                    switch (studentChoice) {
                        case 1:
                            library.addStudent();
                            break;
                        case 2:
                            library.showBook();
                            break;
                        case 3:
                            library.searchBook();
                            break;
                        case 4:
                            library.lendOrReturn();
                            break;
                        default:
                            System.out.println("Invalid option . ");
                    }
                    break;
                case 2:
                    if (library.librarianLogin(scanner)) {
                        Menu.menuCurator();
                        byte curatorChoice = scanner.nextByte();
                        scanner.nextLine();
                        switch (curatorChoice) {
                            case 1:
                                library.addBook();
                                break;
                            case 2:
                                library.showBorrowReports();
                                break;
                            case 3:
                                library.showBook();
                                break;
                            default:
                                System.out.println("Invalid option . ");
                        }
                    }
                    break;
                case 3:
                    Menu.menuAdministrator();
                    byte adminChoice = scanner.nextByte();
                    scanner.nextLine();
                    switch (adminChoice) {
                        case 1:
                            library.showLibrarianReports(scanner);
                            break;
                        case 2:
                            library.showPopularBooks();
                            break;
                        case 3:
                            library.showBorrowedBooksAndOverdue();
                            break;
                        default:
                            System.out.println("Invalid option . ");
                    }
                    break;
                case 0:
                    System.out.println("Exiting program... ");
                    return;
                default:
                    System.out.println("Invalid option . Try again . ");
            }
        }
    }
}