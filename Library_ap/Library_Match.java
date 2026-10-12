package project.Library3;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Library_Match implements LibraryOperations {

    private String Name_Library = "Zanjan Library";
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<BorrowRequest> borrowRequests = new ArrayList<>();
    private ArrayList<Librarian> librarians = new ArrayList<>();
    private transient Librarian currentLibrarian;
    private static final String DATA_FILE = "library.json" ;


    public Library_Match(String Name_Library){
        this.Name_Library= Name_Library;
    }
    public Library_Match(){

    }
    public Librarian getCurrentLibrarian() {
        return currentLibrarian;
    }


    private static Gson gson(){
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class , new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
    }
    @Override
    public void saveAllData(){
        Database.saveAll(students, books, librarians, borrowRequests);
    }
    public static Library_Match loadAllData(){
        Database.init();
        Library_Match lib = new Library_Match("Zanjan Library");
        lib.students = Database.loadStudents();
        lib.books = Database.loadBooks();
        lib.librarians = Database.loadLibrarians();
        lib.borrowRequests = Database.loadBorrowRequests(lib.students, lib.books);
        migrateFromJsonIfNeeded(lib);
        return lib;
    }

    // One-time import : if the database is empty but the old Gson file
    // (library.json) exists , its data is moved into library.db and the old
    // file is renamed to library.json.bak .
    private static void migrateFromJsonIfNeeded(Library_Match lib){
        boolean dbEmpty = lib.students.isEmpty() && lib.books.isEmpty()
                && lib.librarians.isEmpty() && lib.borrowRequests.isEmpty();
        if (!dbEmpty || !new File(DATA_FILE).exists()){
            return;
        }
        Library_Match old;
        try (Reader r = new FileReader(DATA_FILE)){
            old = gson().fromJson(r, Library_Match.class);
        } catch (IOException | RuntimeException e){
            System.out.println("Could not import old " + DATA_FILE + " : " + e.getMessage());
            return;
        }
        if (old == null){
            return;
        }
        if (old.students != null) lib.students = old.students;
        if (old.books != null) lib.books = old.books;
        if (old.librarians != null) lib.librarians = old.librarians;
        if (old.borrowRequests != null) lib.borrowRequests = old.borrowRequests;
        lib.saveAllData();
        File bak = new File(DATA_FILE + ".bak");
        if (bak.exists()){
            bak.delete();
        }
        if (new File(DATA_FILE).renameTo(bak)){
            System.out.println("Old data imported from " + DATA_FILE + " into library.db .");
        } else {
            System.out.println("Old data imported from " + DATA_FILE + " , but the file could not be renamed .");
        }
    }


    @Override
    public void register_Student(){
        Student new_Student = new Student(InputUtil.SCANNER);
        for (Student s : students){
            if (s.getUsername().equalsIgnoreCase(new_Student.getUsername())){
                System.out.println("Username already exists. Registration failed . ");
                return;
            }
        }
        students.add(new_Student);
        Database.insertStudent(new_Student);
        System.out.println("Student registered successfully . ");
    }

    @Override
    public Student login_Student() {
        System.out.println("Enter username : ");
        String username = InputUtil.SCANNER.nextLine();
        System.out.println("Enter password : ");
        String passInput = InputUtil.SCANNER.nextLine();

        for (Student s : students) {
            if (s.getUsername().equalsIgnoreCase(username) && s.getPassword().equals(passInput)) {
                if (!s.isActive()) {
                    System.out.println("Your account is inactive . Please contact the library .");
                    return null;
                }
                System.out.println("Login successful. Welcome, " + s.getFirst_last_Name());
                return s;
            }
        }
        System.out.println("No student found with that username . ");
        return null;
    }

    public void viewStudentHistory(Student student){
        System.out.println("=== Borrow History for "+student.getFirst_last_Name() + "===");
        boolean found = false;

        for (BorrowRequest br : borrowRequests){
            if (br.getStudent().getUsername().equals(student.getUsername())){
                found=true;
                System.out.println("Book : " + br.getBook().getBook_Name() +
                        "\n | Stsrt : " + br.getStartDate() +
                        "\n | End : " + br.getEndDate() +
                        "\n | Returned : " + (br.getReturnDate() != null ? br.getReturnDate() : "Not yet" ));
            }
        }
        if (!found){
            System.out.println("No borrow records found for this student .");
        }
    }

    public void studentLoggedInMenu(Scanner scanner , Student student){
        boolean exit = false;
        while (!exit){
            Menu_Student.Student_Login_Menu(student.getFirst_last_Name());
            System.out.println("Select option : ");
            int choice = InputUtil.readInt(scanner);
            switch (choice){
                case 1:
                    searchBook_Student();
                    break;
                case 2:
                    requestBorrowBook_Student(student);
                    break;
                case 3:
                    viewStudentHistory(student);
                    break;
                case 0:
                    exit = true;
                    System.out.println("Logged out ....");
                    break;
                default:
                    System.out.println("Invalid option . Try again .");
            }
        }
    }
    @Override
    public void searchBook_Student(){
        System.out.println("\n*** Search Book ***" +
                "\nLeave any field empty if you don't want to filter by it ." +
                "\nEnter book title (or press Enter to skip) : ");
        String title = InputUtil.SCANNER.nextLine().trim().toLowerCase();
        System.out.println("Enter author name (or press Enter to skip) : ");
        String author = InputUtil.SCANNER.nextLine().trim().toLowerCase();
        System.out.println("Enter year of publication (or press Enter to skip) : ");
        String yearInput = InputUtil.SCANNER.nextLine().trim();

        boolean found = false;
        for (Book b : books){
            boolean match =true;

            if (!title.isEmpty() && !b.getBook_Name().toLowerCase().contains(title)) match = false;
            if (!author.isEmpty() && !b.getAuthor_Name().toLowerCase().contains(author)) match = false;
            if (!yearInput.isEmpty()){
                try {
                    int year = Integer.parseInt(yearInput);
                    if (b.getYearPublication() != year) match = false;
                } catch (NumberFormatException e){
                    System.out.println("Invalid year format . Skipping year filter .");
                }
            }
            if (match){
                System.out.println(b);
                found = true;
            }
        }

        if (!found){
            System.out.println("No books found matching your criteria .");
        }

    }
    @Override
    public void requestBorrowBook_Student(Student currentStudent) {
        System.out.println("\n### Borrow Book Request ###");

        if (!currentStudent.isActive()) {
            System.out.println("This student is inactive and cannot borrow books.");
            return;
        }

        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }

        System.out.println("Enter book number to request: ");
        int choice;
        try {
            choice = Integer.parseInt(InputUtil.SCANNER.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        if (choice < 0 || choice >= books.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Book selectedBook = books.get(choice);

        if (!selectedBook.isAvailable()) {
            System.out.println("Sorry, this book is not available right now.");
            return;
        }

        LocalDate start = LocalDate.now();
        System.out.println("Start date is set to today: " + start);

        System.out.println("Enter end date (YYYY-MM-DD): ");
        LocalDate end;
        try {
            end = LocalDate.parse(InputUtil.SCANNER.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid date format. Request cancelled.");
            return;
        }

        BorrowRequest request = new BorrowRequest(currentStudent, selectedBook, start, end);
        borrowRequests.add(request);
        Database.insertBorrowRequest(request);

        System.out.println("Your request has been submitted and is pending approval.");
    }


    public Student findStudentByID(String id){
        for (Student s : students){
            if (s.getStudentNumber().equals(id)){
                return s;
            }
        }
        return null;
    }

    public List<BorrowRequest> getBorrowRequest(){
        return borrowRequests;
    }
    @Override
    public boolean toggleStudentStatus(String userName){
        for (Student student : students){
            if (student.getUsername().equalsIgnoreCase(userName)){
                student.setActive(!student.isActive());
                Database.updateStudent(student);
                return true;
            }
        }
        return false;
    }


    @Override
    public void viewStudentCount_Guest(){
        System.out.println("Total registered students: " + students.size());
    }
    @Override
    public void searchBook_Guest(){
        System.out.println("Enter the book title to search: ");
        String title = InputUtil.SCANNER.nextLine();
        boolean found = false;

        for (Book book : books){
            if (book.getBook_Name().equalsIgnoreCase(title)){
                System.out.println("** The Book is : **");
                System.out.println(book);
                found = true;
            }
        }
        if (!found){
            System.out.println("No book found with that title . ");
        }
    }
    @Override
    public void viewLibraryStatistics_Guest(){
        System.out.println("===== Library Statistics =====" +
                "\nTotal Students : " + students.size() +
                "\nTotal Books : " + books.size() +
                "\nTotal Borrows : " + borrowRequests.size());
        if (borrowRequests.isEmpty()){
            System.out.println("No books are currently borrowed . ");
        } else {
            System.out.println("Last Borrowed Books : ");
            int count = 0 ;
            for (int i = borrowRequests.size()-1 ; i>=0 && count < 10 ; i--){
                BorrowRequest b = borrowRequests.get(i);
                System.out.println("_ " + b.getBook().getBook_Name() +
                        "(Borrowed by : " + b.getStudent().getFirst_last_Name()  + ")");
                count++;
            }
        }
    }


    @Override
    public Librarian login_Librarian(){
        System.out.println("Enter username : ");
        String username = InputUtil.SCANNER.nextLine();
        System.out.println("Enter password : ");
        String password = InputUtil.SCANNER.nextLine();

        for (Librarian l : librarians){
            if (l.getUsername_Librarian().equals(username) && l.getPassword_Librarian().equals(password)){
                System.out.println("*! Welcome " + l.getName_Librarian() +" !*");
                currentLibrarian = l;
                return l;
            }
        }
        System.out.println("Invalid username or password.");
        return null;
    }
    @Override
    public void changePassword_Librarian(Librarian librarian,Scanner scanner){
        System.out.println("Enter current password: ");
        String oldPassword = scanner.nextLine();

        if (!librarian.getPassword_Librarian().equals(oldPassword)){
            System.out.println("Incorrect current password . Password not changed . ");
            return;
        }
        System.out.println("Enter new password : ");
        String newPassword = scanner.nextLine();
        librarian.setPassword_Librarian(newPassword);
        Database.updateLibrarian(librarian);
        System.out.println("Password updated successfully !!!");
    }

    @Override
    public void addBook_Librarian(Scanner scanner){
        System.out.println("=== Add New Book ===");
        System.out.println("Enter the code_Book : ");
        String code  = scanner.nextLine();

        for (Book b : books){
            if (b.getCode_Book().equalsIgnoreCase(code)){
                System.out.println("Book with this code already exists !!!");
                return;
            }
        }
        System.out.println("Enter Book name : ");
        String nameBook = scanner.nextLine();
        System.out.println("Enter Author name : ");
        String author = scanner.nextLine();
        System.out.println("Enter number of copies : ");
        int copies;
        try {
            copies = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number ! Book not added . ");
            return;
        }
        System.out.println("Enter year of publication : ");
        int year;
        try {
            year = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number ! Book not added . ");
            return;
        }

        Book newBook = new Book(nameBook,author,code,year,copies);
        books.add(newBook);
        Database.insertBook(newBook);
        Librarian logged = getCurrentLibrarian();
        if (logged != null){
            logged.incrementBookAdded();
            Database.updateLibrarian(logged);
        }
        System.out.println("Book added successfully !!!");
    }
    @Override
    public void searchAndEditeBook_Librarian(Scanner scanner){
        System.out.println("Enter code or name Book : ");
        String  key = scanner.nextLine();

        Book foundBook = null;
        for (Book b : books){
            if (b.getCode_Book().equalsIgnoreCase(key) ||
                b.getBook_Name().equalsIgnoreCase(key)) {
                foundBook = b;
                break;
            }
        }

        if (foundBook == null){
            System.out.println("No book found with given code or name . ");
            return;
        }
        System.out.println("= Book found =" +
        "\n" + foundBook.toString());
        String oldCode = foundBook.getCode_Book();

        System.out.println("** Select field to edit **" +
                "\n1_Book name" +
                "\n2_Author name" +
                "\n3_Code Book" +
                "\n4_Year publication" +
                "\n5_Number of copies" +
                "\n0_Cancel");
        int an_Librarian = InputUtil.readInt(scanner);
        switch (an_Librarian){
            case 1:
                System.out.println("Enter new name Book : ");
                foundBook.setBook_Name(scanner.nextLine());
                break;
            case 2:
                System.out.println("Enter new Author name : ");
                foundBook.setAuthor_Name(scanner.nextLine());
                break;
            case 3:
                System.out.println("Enter new Code_Book : ");
                foundBook.setCode_Book(scanner.nextLine());
                break;
            case 4:
                System.out.println("Enter new Year of publication : ");
                try {
                    int year = Integer.parseInt(scanner.nextLine());
                    foundBook.setYearPublication(year);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number! No change made . ");
                }
                break;
            case 5:
                System.out.println("Enter new Number of copies : ");
                try {
                    int copy = Integer.parseInt(scanner.nextLine());
                    foundBook.setQuantityAvailable(copy);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number! No change made . ");
                }
                break;
            case 0:
                System.out.println("Edit canceled . ");
                return;
            default:
                System.out.println("Invalid choice . ");
                return;
        }
        Database.updateBook(foundBook, oldCode);
        if (!oldCode.equalsIgnoreCase(foundBook.getCode_Book())){
            Database.updateBookCodeReferences(oldCode, foundBook.getCode_Book());
        }
        System.out.println("Book updated successfully !!!");
    }

    private Book findBookByCode(String code){
        for (Book b : books){
            if (b.getCode_Book().equalsIgnoreCase(code)){
                return b;
            }
        }
        return null;
    }

    @Override
    public void approveBorrowRequest_Librarian(Scanner scanner) {
        List<BorrowRequest> pendingRequest = new ArrayList<>();

        for (BorrowRequest b : borrowRequests) {
            if (!b.isApproved()) {
                pendingRequest.add(b);
            }
        }

        if (pendingRequest.isEmpty()) {
            System.out.println("No pending borrow requests.");
            return;
        }

        System.out.println("** Pending Borrow Requests: ");
        for (int i = 0; i < pendingRequest.size(); i++) {
            BorrowRequest br = pendingRequest.get(i);
            System.out.println((i + 1) + ". " + br.getStudent().getFirst_last_Name() +
                    " | Book: " + br.getBook().getBook_Name() +
                    " | Start Date: " + br.getStartDate());
        }

        System.out.println("Enter the request number to approve (0 to cancel): ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        if (choice == 0) {
            System.out.println("Approval cancelled.");
            return;
        }
        if (choice < 1 || choice > pendingRequest.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        BorrowRequest selected = pendingRequest.get(choice - 1);
        Book book = findBookByCode(selected.getBook().getCode_Book());
        if (book == null || book.getQuantityAvailable() <= 0) {
            System.out.println("No copies of this book are available right now . Request cannot be approved .");
            return;
        }
        selected.setApproved(true);
        book.setQuantityAvailable(book.getQuantityAvailable() - 1);
        Database.updateBorrowRequest(selected);
        Database.updateBook(book);

        Librarian logged = getCurrentLibrarian();
        if (logged != null) {
            logged.incrementBookBorrowed();
            Database.updateLibrarian(logged);
        }

        System.out.println("Borrow request approved successfully!");
    }



    public void showStudentBorrowHistory_Librarian(Scanner scanner){
        int totalBorrow = 0;
        int notReturned = 0;
        int lateReturned = 0;

        System.out.println("Enter The username : ");
        String studentUsername = scanner.nextLine();

        System.out.println("*** Student Borrow History : " + studentUsername);
        for (BorrowRequest request : borrowRequests){
            if (request.getStudent().getUsername().equalsIgnoreCase(studentUsername)){
                totalBorrow++;
                System.out.println("The Book name : " + request.getBook().getBook_Name() +
                        "\n | Start : " + request.getStartDate() +
                        "\n | End : " + request.getEndDate() +
                        "\n | Approve is : " + request.isApproved() +
                        "\n | is Returned : " + request.isReturned());
                if (!request.isReturned()){
                    notReturned++;
                } else {
                    if (request.isReturned()
                            && request.getReturnDate() != null
                            && request.getReturnDate().isAfter(request.getEndDate())){
                        lateReturned++;
                    }
                }
            }
        }
        System.out.println("*** The final : " +
                "\n1_Total Borrows : " + totalBorrow +
                "\n2_Total Borrows not return : " + notReturned +
                "\n3_Total Borrow late return : " + lateReturned);

        if (totalBorrow == 0){
            System.out.println("The Student nothing Borrowing . ");
        }
    }
    @Override
    public void toggleStatus_Librarian(Scanner scanner){
        System.out.println("Enter student username to toggle status : ");
        String stuUser = scanner.nextLine();
        boolean result = toggleStudentStatus(stuUser);
        if (result){
            System.out.println("Student status updated successfully . ");
        } else {
            System.out.println("Student not found . ");
        }
    }
    @Override
    public boolean returnBook(String username,String bookName){
        for (BorrowRequest request : borrowRequests){
            if (request.getStudent().getUsername().equalsIgnoreCase(username) &&
                request.getBook().getBook_Name().equalsIgnoreCase(bookName) &&
                request.isApproved() &&
                request.getReturnDate() == null){

                request.setReturnDate(LocalDate.now());
                request.setReturned(true);
                Database.updateBorrowRequest(request);
                Book book = findBookByCode(request.getBook().getCode_Book());
                if (book != null) {
                    book.setQuantityAvailable(book.getQuantityAvailable() + 1);
                    Database.updateBook(book);
                }
                Librarian logged = getCurrentLibrarian();
                if (logged != null) {
                    logged.incrementBookReturned();
                    Database.updateLibrarian(logged);
                }
                return true;
            }
        }
        return false;
    }
    @Override
    public void returnBookBorrowAndSaveReturnDate_Librarian(Scanner scanner){
        System.out.println("Enter Student username : ");
        String stuUser = scanner.nextLine();
        System.out.println("Enter Book title to return : ");
        String bookTitle = scanner.nextLine();

        boolean success = returnBook(stuUser,bookTitle);
        if (success){
            System.out.println("Book returned successfully . ");
        } else {
            System.out.println("No matching borrow record found . ");
        }
    }


    @Override
    public void addLibrarian_Manager(Scanner scanner){
        System.out.print("Enter librarian name: ");
        String name = scanner.nextLine();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();


        for (Librarian l : librarians){
            if (l.getUsername_Librarian().equalsIgnoreCase(username)){
                System.out.println("Librarian already exists !!!");
                return;
            }
        }
        Librarian librarian = new Librarian(name ,username,password);
        librarians.add(librarian);
        Database.insertLibrarian(librarian);
        System.out.println("Librarian added successfully : " + name);
    }

    public Librarian findLibrarian(String username){
        for (Librarian l : librarians){
            if (l.getUsername_Librarian().equalsIgnoreCase(username)){
                return l;
            }
        }
        return null;
    }
    @Override
    public void showLibrarianReports_Manager(Scanner scanner){
        System.out.println("### Enter the username Librarian : ");
        String useLib = scanner.nextLine();
        Librarian librarian = findLibrarian(useLib);
        if (librarian != null) {
            System.out.println(librarian.getReport());
        } else {
            System.out.println("Librarian not found !!!");
        }
    }
    @Override
    public void viewBorrowingStatistics_Manager(){
        System.out.println("=== Borrowing Statistics ===");

        int totalRequest = borrowRequests.size();
        long totalBorrow = borrowRequests.stream()
                .filter(BorrowRequest :: isApproved)
                .count();

        long totalDays = 0;
        long finishedBorrows =0;

        for (BorrowRequest b : borrowRequests){
            if (b.isApproved() && b.getReturnDate() != null){
                long days = java.time.temporal.ChronoUnit.DAYS.between(
                        b.getStartDate(),
                        b.getReturnDate()
                );
                totalDays += days;
                finishedBorrows ++;
            }
        }

        double avgDays = (finishedBorrows > 0 ) ? (double) totalDays/finishedBorrows : 0.0;
        System.out.println("Total borrow request : " + totalRequest +
                "\nTotal approved borrow : " + totalBorrow);
        System.out.printf("\nAvarage borrow duration (days) : %.2f%n" , avgDays);
    }
    @Override
    public void viewAllStudentStatistics_Manager(){
        System.out.println("=== All Students Statistics ===");

        if (students.isEmpty()){
            System.out.println("No students registered .");
            return;
        }

        Map<Student,Long> delayedCountMap = new HashMap<>();

        for (Student s : students){
            long totalBorrow = borrowRequests.stream()
                    .filter(br -> br.getStudent().getUsername().equals(s.getUsername()))
                    .count();
            long notReturned = borrowRequests.stream()
                    .filter(br -> br.getStudent().getUsername().equals(s.getUsername())
                        && br.isApproved()
                        && br.getReturnDate() == null)
                    .count();
            long delayedReturns = borrowRequests.stream()
                    .filter(br -> br.getStudent().getUsername().equals(s.getUsername())
                        && br.isApproved()
                        && br.getReturnDate() != null
                        && br.getReturnDate().isAfter(br.getEndDate()))
                    .count();

            delayedCountMap.put(s,delayedReturns);

            System.out.println("\n*** Student : ***" + s.getFirst_last_Name() + "(" + s.getUsername() + ") ---" +
                    "\nTotal borrow requests : " + totalBorrow +
                    "\nTotal not returned books : " + notReturned +
                    "\nTotal delayed returns : " + delayedReturns);
        }

        System.out.println("\n=== Top 10 Students with Most Delayed Returns ===");
        delayedCountMap.entrySet().stream()
                .sorted(Map.Entry.<Student,Long>comparingByValue().reversed())
                .limit(10)
                .forEach(entry -> {
                    Student st = entry.getKey();
                    long count = entry.getValue();
                    System.out.println(st.getFirst_last_Name() + " (" +st.getUsername() + " )  - Delayed Returns : " + count);
                });
    }

}
