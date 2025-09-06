package project.Library3;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library_Match library_match = Library_Match.loadAllData();
        Runtime.getRuntime().addShutdownHook(new Thread(library_match::saveAllData));
        boolean f = false;
        while (!f){
            Menu_Welcome.Chose_Char();
            System.out.println("Select option : ");
            byte answer = scanner.nextByte();
            scanner.nextLine();

            switch (answer){
                case 1:
                    studentMenu(scanner,library_match);
                    break;
                case 2:
                    GuestMenu(scanner,library_match);
                    break;
                case 3:
                    LibrarianMenu(scanner,library_match);
                    break;
                case 4:
                    ManagerMenu(scanner,library_match);
                    break;
                case 0:
                    f=true;
                    System.out.println("Exit....");
                    break;
                default:
                    System.out.println("Invalid choice . Please try again .");
            }
        }
    }


    private static void studentMenu(Scanner scanner, Library_Match library_match) {
        boolean f =false;
        while (!f) {
            Menu_Student.Welcome_student();
            System.out.println("Select option : ");
            byte answer_Student = scanner.nextByte();
            scanner.nextLine();

            switch (answer_Student) {
                case 1:
                    library_match.register_Student();
                    break;
                case 2:
                    Student current = library_match.loginStudent();
                    if (current != null) {
                        library_match.studentLoggedInMenu(scanner, current);
                    }
                    break;
                case 3:
                    library_match.searchBook_Student();
                    break;
                case 4:
                    System.out.println("Enter your student ID: ");
                    String studentID = scanner.nextLine();
                    Student student = library_match.findStudentByID(studentID);
                    if (student != null) {
                        library_match.requestBorrowBook_Student(student);
                    } else {
                        System.out.println("Student not found !");
                    }
                    break;
                case 0:
                    f=true;
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println("Invalid choice . Please try again .");
                    break;
            }
        }
    }

    private static void GuestMenu(Scanner scanner,Library_Match library_match){
        boolean f= false;
        while (!f){
            Menu_Guest.Welcome_guest();
            System.out.println("Your Choose : ");
            byte answer_Guest = scanner.nextByte();
            scanner.nextLine();
            switch (answer_Guest){
                case 1:
                    library_match.viewStudentCount_Guest();
                    break;
                case 2:
                    library_match.searchBook_Guest();
                    break;
                case 3:
                    library_match.viewLibraryStatistics_Guest();
                    break;
                case 0:
                    f=true;
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println("Invalid choice .");
            }
        }

    }

    private static void LibrarianMenu(Scanner scanner , Library_Match library_match){
        boolean f= false;
        while (!f){
            Librarian currentLibrarian =  library_match.login_Librarian();
            if (currentLibrarian != null) {
                Menu_Librarian.Menu_Librarians();
                byte answer_Librarian = scanner.nextByte();
                scanner.nextLine();
                switch (answer_Librarian){
                    case 1:
                        library_match.changePassword_Librarian(currentLibrarian,scanner);
                        break;
                    case 2:
                        library_match.addBook_Librarian(scanner);
                        break;
                    case 3:
                        library_match.searchAndEditeBook_Librarian(scanner);
                        break;
                    case 4:
                        library_match.approveBorrowRequest_Librarian(scanner);
                        break;
                    case 5:
                        library_match.showStudentBorrowHistory_Librarian(scanner);
                        break;
                    case 6:
                        library_match.toggleStatus_Librarian(scanner);
                        break;
                    case 7:
                        library_match.returnBookBorrowAndSaveReturnDate_Librarian(scanner);
                        break;
                    case 0:
                        f=true;
                        System.out.println("Exit");
                        break;
                    default:
                        System.out.println("Invalid choose .");
                }
            }
        }

    }

    private static void ManagerMenu(Scanner scanner, Library_Match library_match){
        boolean f=false;
        while (!f){
            Menu_Manager.menuWelcome();
            System.out.println("Your choose : ");
            int choose = scanner.nextByte();
            scanner.nextLine();
            switch (choose){
                case 1:
                    library_match.addLibrarian_Manager(scanner);
                    break;
                case 2:
                    library_match.showLibrarianReports_Manager(scanner);
                    break;
                case 3:
                    library_match.viewBorrowingStatistics_Manager();
                    break;
                case 4:
                    library_match.viewAllStudentStatistics_Manager();
                    break;
                case 0:
                    f=true;
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println("Invalid choice .");
            }
        }

    }
}
