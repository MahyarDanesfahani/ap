package project.Library2;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        String storageType = ConfigReader.readStorageType();
        int threadCount = ConfigReader.readThreadCount();

        LibraryStorage storage ;
        if (storageType.equalsIgnoreCase("json")){
            storage = new LibraryStorageJson("library.json");
        } else {
            throw new RuntimeException("Unsupported storage type: " + storageType);
        }

        Library loaded = storage.loadLibrary();
        Library library ;
        if (loaded == null){
            library = new Library("Central Library");
        } else {
            if (threadCount <= 0) {
                library = loaded;
            } else {
                library = new Library("Central Library");
                ExecutorService executor = Executors.newFixedThreadPool(threadCount);
                for (Student student : loaded.getStudents()){
                    executor.submit(() -> library.addStudentFromLoad(student));
                }
                for (Book book : loaded.getBooks()){
                    executor.submit(() -> library.addBookFromLoad(book));
                }
                for (BorrowRecord borrowRecord : loaded.getBorrowRecords()){
                    executor.submit(() ->  library.addBorrowRecordFromLoad(borrowRecord));
                }
                executor.shutdown();
                executor.awaitTermination(1, TimeUnit.MINUTES);
            }
        }

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
                            System.out.println("Invalid option.");
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
                                System.out.println("Invalid option.");
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
                            System.out.println("Invalid option.");
                    }
                    break;
                case 0:
                    storage.saveLibrary(library);
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}