package project.Library2;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Library {
    private String name;
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Librarian> librarians = new ArrayList<>();
    private ArrayList<BorrowRecord> borrowRecords = new ArrayList<>();

    private Scanner scanner = new Scanner(System.in);
    private Random random = new Random();

    public Library(String name){
        this.name = name ;
        librarians.add(new Librarian("ali","ALI" , 1234));
        librarians.add(new Librarian("reza","REZA",5678));
    }

    public void addStudent(){
        Student newStudent = new Student(scanner);
        students.add(newStudent);
        System.out.println("Student registered successfully . ");
    }

    public boolean librarianLogin(Scanner scanner){
        System.out.println("Enter username : ");
        String user = scanner.nextLine();
        System.out.println("Enter password : ");
        int pass = scanner.nextInt();

        for (Librarian librarian : librarians){
            if (librarian.getUserName().equalsIgnoreCase(user)){
                if (librarian.getPassword() == pass){
                    return true;
                } else {
                    System.out.println("Wrong password!!! ");
                    return false ;
                }
            }
        }
        System.out.println("Username not found . ");
        return false ;
    }

    public void addBook(){
        Book newBook = new Book(scanner);
        books.add(newBook);
        System.out.println("Book added successfully . ");
    }

    public void showBook(){
        if (books.isEmpty()){
            System.out.println("No books available . ");
        }else {
            for (Book book : books){
                System.out.println("Title : " + book.getBookName() +
                        " | Author : " + book.getAuthorName() +
                        " | Year : " + book.getYearPublication() +
                        " | Available : " + book.getQuantityAvailable());
            }
        }
    }

    public void searchBook(){
        Menu.searchBook();
        byte choice = scanner.nextByte();
        scanner.nextLine();

        switch (choice){
            case 1 :
                System.out.println("Enter Book title : ");
                String title = scanner.nextLine();
                for (Book book : books){
                    if (book.getBookName().equalsIgnoreCase(title)){
                        System.out.println(book.getBookName());
                    }
                }
                break;
            case 2 :
                System.out.println("Enter Author name : ");
                String author = scanner.nextLine();
                for (Book book :books){
                    if (book.getAuthorName().equalsIgnoreCase(author)){
                        System.out.println(book.getBookName());
                    }
                }
                break;
            case  3 :
                System.out.println("Enter Year of publication : ");
                int year = scanner.nextInt();
                for (Book book : books){
                    if (book.getYearPublication() == year){
                        System.out.println(book.getBookName());
                    }
                }
                break;
        }
    }

    public void lendBook(){
        System.out.println("Enter the name of the book : ");
        scanner.nextLine();
        String name = scanner.nextLine();

        for (Book book : books){
            if (book.getBookName().equalsIgnoreCase(name)){
                if (book.getQuantityAvailable() > 0 ){
                    Student student = new Student(scanner);
                    students.add(student);
                    Librarian selected = librarians.get(random.nextInt(librarians.size()));
                    BorrowRecord record = new BorrowRecord(student,book);
                    selected.addBorrowRecord(record);
                    borrowRecords.add(record);
                    book.setQuantityAvailable(book.getQuantityAvailable()-1);
                    System.out.println("Book lent successfully . ");
                    return;
                } else {
                    System.out.println("Book is not available . ");
                    return;
                }
            }
        }
        System.out.println("Book not found . ");
    }

    public void lendOrReturn(){
        Menu.lendingReceiving();
        byte option = scanner.nextByte();
        switch (option){
            case 1 :
                lendBook();
                break;
            case 2 :
                returnBook();
                break;
            default :
                System.out.println("Invalid option . ");
        }
    }

    public void returnBook(){
        System.out.println("Enter the name of the book to return : ");
        scanner.nextLine();
        String name = scanner.nextLine();

        for (Librarian librarian : librarians){
            for (BorrowRecord record : librarian.getBorrowRecords()){
                if (record.getBook().getBookName().equalsIgnoreCase(name)){
                    record.getBook().setQuantityAvailable(record.getBook().getQuantityAvailable()+1);
                    librarian.removeBorrowRecord(record);
                    borrowRecords.remove(record);
                    System.out.println("Book returned successfully . ");
                    return;
                }
            }
        }
        System.out.println("Borrow record not found . ");
    }

    public void showBorrowedBooksAndOverdue(){
        LocalDate today = LocalDate.now();
        System.out.println("=== Borrowed Books ===");
        for (BorrowRecord record : borrowRecords){
            if (record.getReturnDate().isBefore(today)){
                System.out.println("Overdue -> " + record.getStudent().getFirstLastName() +
                        ", Book : " + record.getBook().getBookName());
            }
        }
    }

    public void showLibrarianReports(Scanner scanner){
        scanner.nextLine();
        System.out.println("Enter librarian username : ");
        String username = scanner.nextLine();

        for (Librarian librarian : librarians){
            if (librarian.getUserName().equalsIgnoreCase(username)){
                ArrayList<BorrowRecord> records = librarian.getBorrowRecords();
                if (records.isEmpty()){
                    System.out.println("No borrowed books . ");
                } else {
                    System.out.println("Reports for " + librarian.getFirstLastName() + ":");
                    for (BorrowRecord record : records){
                        System.out.println("Student: " + record.getStudent().getFirstLastName());
                        System.out.println("Book: " + record.getBook().getBookName());
                        System.out.println("Borrowed on: " + record.getBorrowDate());
                        System.out.println("Return by: " + record.getReturnDate());
                    }
                }
                LocalDate today = LocalDate.now();
                System.out.println("=== Overdue Books ===");
                for (BorrowRecord record : records){
                    if (record.getReturnDate().isBefore(today)){
                        System.out.println("Student: " + record.getStudent().getFirstLastName() +
                                ", Book: " + record.getBook().getBookName() +
                                ", Due: " + record.getReturnDate());
                    }
                }
                return;
            }
        }
        System.out.println("Librarian not found . ");
    }

    public void showBorrowReports(){
        for (Librarian librarian : librarians){
            System.out.println("Librarian : " + librarian.getFirstLastName());
            ArrayList<BorrowRecord> records = librarian.getBorrowRecords();
            if (records.isEmpty()){
                System.out.println("No borrowed books . ");
            } else {
                for (BorrowRecord record : records){
                    System.out.println("Student: " + record.getStudent().getFirstLastName());
                    System.out.println("Book: " + record.getBook().getBookName());
                    System.out.println("Borrowed on: " + record.getBorrowDate());
                    System.out.println("Return by: " + record.getReturnDate());
                    System.out.println();
                }
            }
        }
    }

    public void showPopularBooks(){
        ArrayList<Book> counted = new ArrayList<>();
        ArrayList<Integer> count = new ArrayList<>();

        for (BorrowRecord record : borrowRecords){
            Book book = record.getBook();
            int index = counted.indexOf(book);
            if (index != -1){
                count.set(index,count.get(index)+1);
            }else {
                counted.add(book);
                count.add(1);
            }
        }
        System.out.println("=== Popular Books ===");
        for (int i=0 ; i<counted.size() ; i++){
            System.out.println(counted.get(i).getBookName() + " -> Borrowed" + count.get(i) + "times . ");
        }
    }

    public ArrayList<Librarian> getLibrarians(){
        return librarians;
    }
}