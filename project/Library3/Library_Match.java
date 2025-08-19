package project.Library3;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Library_Match {

    String Name_Library ;
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<BorrowRequest> borrowRequests = new ArrayList<>();
    private ArrayList<Librarian> librarians = new ArrayList<>();
    private Librarian currentLibrarian;
    private static final String STUDENT_FILE = "students.json" ;

    private Scanner scanner = new Scanner(System.in);


    public Library_Match(String Name_Library){
        this.Name_Library= Name_Library;
        loadStudents();
    }
    public Library_Match(){

    }
    public Librarian getCurrentLibrarian() {
        return currentLibrarian;
    }

    public void register_Student(){
        Student new_Student = new Student(scanner);
        for (Student s : students){
            if (s.getUsername().equalsIgnoreCase(new_Student.getUsername())){
                System.out.println("Username already exists. Registration failed . ");
                return;
            }
        }
        students.add(new_Student);
        saveStudents();
        System.out.println("Student registered successfully . ");
    }

    private void saveStudents(){
        try(Writer writer = new FileWriter(STUDENT_FILE)){
            new Gson().toJson(students,writer);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void loadStudents(){
        try (Reader reader = new FileReader(STUDENT_FILE)){
            students = new Gson().fromJson(reader,new TypeToken<ArrayList<Student>>(){}.getType());
            if (students == null){
                students = new ArrayList<>();
            }
        } catch (IOException e){
            students = new ArrayList<>();
        }
    }

    public void loginStudent(){
        System.out.println("Enter username : ");
        String username = scanner.nextLine();
        System.out.println("Enter password : ");
        String passInput = scanner.nextLine();

        for (Student s : students){
            if (s.getUsername().equalsIgnoreCase(username)){
                if (!s.isActive()){
                    System.out.println("Your account is inactive . Please contact the library .");
                    return;
                }
                if (String.valueOf(s.getPassword()).equals(passInput)){
                    System.out.println("Login successful . Welcome, " + s.getFirst_last_Name() + "!");
                    return;
                } else {
                    System.out.println("Incorrect password .");
                    return;
                }
            }
        }
        System.out.println("No student found with that username . ");
    }

    public void searchBook_Student(){
        System.out.println("\n*** Search Book ***" +
                "\nLeave any field empty if you don't want to filter by it ." +
                "\nEnter book title (or press Enter to skip) : ");
        String title = scanner.nextLine().trim().toLowerCase();
        System.out.println("Enter author name (or press Enter to skip) : ");
        String author = scanner.nextLine().trim().toLowerCase();
        System.out.println("Enter year of publication (or press Enter to skip) : ");
        String yearInput = scanner.nextLine().trim();

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

    public void requestBorrowBook_Student(Student currentStudent){
        System.out.println("\n### Borrow Book Request ###");
        if (!currentStudent.isActive()){
            System.out.println("This student is inactive and cannot borrow books . ");
        }
        for (int i=0 ; i<books.size() ; i++){
            System.out.println((i+1) + ". " + books.get(i));
        }
        System.out.println("Enter book number to request : ");
        int choice = Integer.parseInt(scanner.nextLine()) - 1;

        if (choice < 0 || choice >= books.size()){
            System.out.println("Invalid selection .");
            return;
        }

        Book selectedBook = books.get(choice);
        if (!selectedBook.isAvailable()){
            System.out.println("Sorry, this book is not available right now .");
            return;
        }

        System.out.println("Enter start date (YYYY-MM-DD) : ");
        LocalDate start = LocalDate.parse(scanner.nextLine());
        System.out.println("Enter end date (YYYY-MM-DD) : ");
        LocalDate end = LocalDate.parse(scanner.nextLine());

        BorrowRequest request = new BorrowRequest(currentStudent,selectedBook,start,end);
        borrowRequests.add(request);
        System.out.println("Your request has been submitted and is pending approval .");

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

    public boolean toggleStudentStatus(String userName){
        for (Student student : students){
            if (student.getUsername().equalsIgnoreCase(userName)){
                student.setActive(!student.isActive());
                saveStudents();
                return true;
            }
        }
        return false;
    }


    public void viewStudentCount_Guest(){
        System.out.println("Total registered students: " + students.size());
    }

    public void searchBook_Guest(){
        System.out.println("Enter the book title to search: ");
        String title = scanner.nextLine();
        boolean found = false;

        for (Book book : books){
            if (book.getBook_Name().equalsIgnoreCase(title)){
                System.out.println("** The Book is : **");
                book.toString();
                found = true;
            }
        }
        if (!found){
            System.out.println("No book found with that title . ");
        }
    }

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


    public Librarian login_Librarian(){
        Scanner s = new Scanner(System.in);
        System.out.println("Enter username : ");
        String username = s.nextLine();
        System.out.println("Enter password : ");
        String password = s.nextLine();

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

    public void saveLibrarianToFile(){
        try (FileWriter writer =new FileWriter("librarians.json")){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(librarians,writer);
        } catch (IOException e){
            System.out.println("Error saving librarians : " + e.getMessage());
        }
    }

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
        System.out.println("Password updated successfully !!!");

        saveLibrarianToFile();
    }

    public void saveBookTofile(){
        try (FileWriter writer =new FileWriter("books.json")){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(books,writer);
        } catch (IOException e){
            System.out.println("Error saving books : " + e.getMessage());
        }
    }

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
        Librarian logged = getCurrentLibrarian();
        if (logged != null){
            logged.incrementBookAdded();
        }
        saveBookTofile();
        System.out.println("Book added successfully !!!");
    }

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
        }
        System.out.println("= Book found =" +
        "\n" + foundBook.toString());

        System.out.println("** Select field to edit **" +
                "\n1_Book name" +
                "\n2_Author name" +
                "\n3_Code Book" +
                "\n4_Year publication" +
                "\n5_Number of copies" +
                "\n0_Cancel");
        byte an_Librarian = scanner.nextByte();
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
                    foundBook.setQuantityAvailable(year);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number! No change made . ");
                }
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
        saveBookTofile();
        System.out.println("Book updated successfully !!!");
    }

    public void saveBorrowRecordsToFile(){
        try (BufferedWriter writer =  new BufferedWriter(new FileWriter("borrowRecords.txt"))){
            for ( BorrowRequest request : borrowRequests){
                writer.write(
                        request.toString()
                );
                writer.newLine();
            }
        } catch (IOException e){
            System.out.println("Error : " + e.getMessage());
        }
    }

    public void approveBorrowRequest_Librarian(Scanner scanner){
        LocalDate today = LocalDate.now();

        List<BorrowRequest> pendingRequest = new ArrayList<>();

        for (BorrowRequest b : borrowRequests){
            if (!b.isApproved()){
                LocalDate startDate = b.getStartDate();
                if (startDate.equals(today) || startDate.equals(today.minusDays(1))){
                    pendingRequest.add(b);
                }
            }
        }

        if (pendingRequest.isEmpty()){
            System.out.println("No pending borrow requests for today or yesterday . ");
            return;
        }
        System.out.println("** Pending Borrow Requests : ");
        for (int i=0 ; i<pendingRequest.size() ; i++){
            BorrowRequest br = pendingRequest.get(i);
            System.out.println((i+1) + ". " + br.getStudent().getFirst_last_Name() +
                    " | Book : " + br.getBook().getBook_Name() +
                    " | Start Date : " + br.getStartDate());
        }

        System.out.println("Enter the request number to approve (0 to cancel) : ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input . ");
            return;
        }
        if (choice == 0){
            System.out.println("Approval cancelled . ");
            return;
        }
        if ( choice < 1 || choice > pendingRequest.size()){
            System.out.println("Invalid choice . ");
            return;
        }
        BorrowRequest selected = pendingRequest.get(choice-1);
        selected.setApproved(true);
        Librarian logged = getCurrentLibrarian();
        if (logged != null) {
            logged.incrementBookBorrowed();
        }
        saveBorrowRecordsToFile();
        System.out.println("Borrow request approved successfully !!!");
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

    public boolean returnBook(String username,String bookName){
        for (BorrowRequest request : borrowRequests){
            if (request.getStudent().getUsername().equalsIgnoreCase(username) &&
                request.getBook().getBook_Name().equalsIgnoreCase(bookName) &&
                request.getReturnDate() == null){

                request.setReturnDate(LocalDate.now());
                request.getBook().setAvailable(true);
                Librarian logged = getCurrentLibrarian();
                if (logged != null) {
                    logged.incrementBookReturned();
                }
                saveBorrowRecordsToFile();
                return true;
            }
        }
        return false;
    }

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



    public void addLibrarian_Manager(Scanner scanner){
        System.out.println("Enter Librarian username : ");
        String libUser = scanner.nextLine();
        System.out.println("Enter Librarian password : ");
        String libpass = scanner.nextLine();

        for (Librarian l : librarians){
            if (l.getUsername_Librarian().equalsIgnoreCase(libUser) && l.getPassword_Librarian().equalsIgnoreCase(libpass)){
                System.out.println("Librarian already exists !!!");
                return;
            }
        }
        Librarian librarian = new Librarian(libUser,libpass);
        librarians.add(librarian);
        System.out.println("Librarian added successfully : " + libUser);
        saveLibrarianToFile();
    }

    public void loadLibrariansFromFile(){
        librarians.clear();
        try (java.util.Scanner sc = new Scanner("librarians.txt")){
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2){
                    librarians.add(new Librarian(parts[0] , parts[1]));
                }
            }
        } catch (Exception e) {

        }
    }

    public Librarian findLibrarian(String username){
        for (Librarian l : librarians){
            if (l.getUsername_Librarian().equalsIgnoreCase(username)){
                return l;
            }
        }
        return null;
    }

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

}