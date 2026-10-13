package project.Library3;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Pure business-logic layer of the library .
 * No console I/O here : every method either returns data ( for showing in the
 * GUI ) or a String error message ( null means the operation succeeded ) .
 */
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

    public String getName_Library(){
        return Name_Library;
    }
    public ArrayList<Student> getStudents(){
        return students;
    }
    public ArrayList<Book> getBooks(){
        return books;
    }
    public ArrayList<Librarian> getLibrarians(){
        return librarians;
    }
    public List<BorrowRequest> getBorrowRequest(){
        return borrowRequests;
    }
    public Librarian getCurrentLibrarian() {
        return currentLibrarian;
    }
    public void setCurrentLibrarian(Librarian currentLibrarian) {
        this.currentLibrarian = currentLibrarian;
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
            System.err.println("Could not import old " + DATA_FILE + " : " + e.getMessage());
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


    // ================= Student =================

    @Override
    public String registerStudent(String fullName, String studentNumber,
                                  String fieldOfStudy, String username, String password){
        if (isBlank(fullName) || isBlank(studentNumber) || isBlank(fieldOfStudy)
                || isBlank(username) || isBlank(password)){
            return "همه فیلدها باید پر شوند .";
        }
        if (!password.matches("\\d{4}")){
            return "رمز عبور باید دقیقا ۴ رقم باشد .";
        }
        for (Student s : students){
            if (s.getUsername().equalsIgnoreCase(username.trim())){
                return "این نام کاربری قبلا ثبت شده است .";
            }
        }
        Student newStudent = new Student(fullName.trim(), username.trim(), password,
                studentNumber.trim(), fieldOfStudy.trim(), true);
        students.add(newStudent);
        Database.insertStudent(newStudent);
        return null;
    }

    // Returns the student when username+password match , null otherwise .
    // The caller decides what to do with an inactive account .
    @Override
    public Student authenticateStudent(String username, String password){
        for (Student s : students){
            if (s.getUsername().equalsIgnoreCase(username) && s.getPassword().equals(password)){
                return s;
            }
        }
        return null;
    }

    // All filters are optional : null or blank means " do not filter " .
    @Override
    public List<Book> searchBooks(String title, String author, Integer year){
        List<Book> result = new ArrayList<>();
        String t = (title == null) ? "" : title.trim().toLowerCase();
        String a = (author == null) ? "" : author.trim().toLowerCase();
        for (Book b : books){
            boolean match = true;
            if (!t.isEmpty() && !b.getBook_Name().toLowerCase().contains(t)) match = false;
            if (!a.isEmpty() && !b.getAuthor_Name().toLowerCase().contains(a)) match = false;
            if (year != null && b.getYearPublication() != year) match = false;
            if (match){
                result.add(b);
            }
        }
        return result;
    }

    @Override
    public String requestBorrow(Student student, Book book, LocalDate endDate){
        if (student == null || book == null){
            return "درخواست نامعتبر است .";
        }
        if (!student.isActive()){
            return "حساب شما غیرفعال است و امکان امانت گرفتن ندارید .";
        }
        if (!book.isAvailable()){
            return "موجودی این کتاب تمام شده است .";
        }
        if (endDate == null || endDate.isBefore(LocalDate.now())){
            return "تاریخ پایان باید امروز یا بعد از امروز باشد .";
        }
        BorrowRequest request = new BorrowRequest(student, book, LocalDate.now(), endDate);
        borrowRequests.add(request);
        Database.insertBorrowRequest(request);
        return null;
    }

    @Override
    public List<BorrowRequest> getStudentBorrowHistory(Student student){
        List<BorrowRequest> history = new ArrayList<>();
        for (BorrowRequest br : borrowRequests){
            if (br.getStudent().getUsername().equalsIgnoreCase(student.getUsername())){
                history.add(br);
            }
        }
        return history;
    }

    @Override
    public List<BorrowRequest> getStudentBorrowHistory(String studentUsername){
        List<BorrowRequest> history = new ArrayList<>();
        for (BorrowRequest br : borrowRequests){
            if (br.getStudent().getUsername().equalsIgnoreCase(studentUsername)){
                history.add(br);
            }
        }
        return history;
    }

    public Student findStudentByUsername(String username){
        for (Student s : students){
            if (s.getUsername().equalsIgnoreCase(username)){
                return s;
            }
        }
        return null;
    }

    public Student findStudentByID(String id){
        for (Student s : students){
            if (s.getStudentNumber().equals(id)){
                return s;
            }
        }
        return null;
    }


    // ================= Guest =================

    @Override
    public int getStudentCount(){
        return students.size();
    }

    // The last n borrow requests , newest first .
    @Override
    public List<BorrowRequest> getRecentBorrows(int n){
        List<BorrowRequest> recent = new ArrayList<>();
        for (int i = borrowRequests.size() - 1 ; i >= 0 && recent.size() < n ; i--){
            recent.add(borrowRequests.get(i));
        }
        return recent;
    }


    // ================= Librarian =================

    @Override
    public Librarian authenticateLibrarian(String username, String password){
        for (Librarian l : librarians){
            if (l.getUsername_Librarian().equals(username) && l.getPassword_Librarian().equals(password)){
                currentLibrarian = l;
                return l;
            }
        }
        return null;
    }

    @Override
    public String changeLibrarianPassword(Librarian librarian, String oldPassword, String newPassword){
        if (isBlank(oldPassword) || isBlank(newPassword)){
            return "رمزها نمی‌توانند خالی باشند .";
        }
        if (!librarian.getPassword_Librarian().equals(oldPassword)){
            return "رمز فعلی اشتباه است .";
        }
        librarian.setPassword_Librarian(newPassword);
        Database.updateLibrarian(librarian);
        return null;
    }

    @Override
    public String addBook(String code, String name, String author, int year, int copies){
        if (isBlank(code) || isBlank(name)){
            return "کد و نام کتاب الزامی است .";
        }
        for (Book b : books){
            if (b.getCode_Book().equalsIgnoreCase(code.trim())){
                return "کتابی با این کد قبلا ثبت شده است .";
            }
        }
        if (year < 0 || copies < 0){
            return "سال انتشار و تعداد نسخه باید عددی مثبت باشند .";
        }
        Book newBook = new Book(name.trim(), author == null ? "" : author.trim(), code.trim(), year, copies);
        books.add(newBook);
        Database.insertBook(newBook);
        if (currentLibrarian != null){
            currentLibrarian.incrementBookAdded();
            Database.updateLibrarian(currentLibrarian);
        }
        return null;
    }

    // Finds a book by exact code or exact name .
    @Override
    public Book findBook(String codeOrName){
        for (Book b : books){
            if (b.getCode_Book().equalsIgnoreCase(codeOrName) ||
                b.getBook_Name().equalsIgnoreCase(codeOrName)){
                return b;
            }
        }
        return null;
    }

    // The book object is edited in place so every borrow request that points
    // to it stays consistent .
    @Override
    public String updateBook(Book book, String newName, String newAuthor,
                             String newCode, Integer newYear, Integer newCopies){
        if (isBlank(newName) || isBlank(newCode)){
            return "کد و نام کتاب نمی‌توانند خالی باشند .";
        }
        for (Book b : books){
            if (b != book && b.getCode_Book().equalsIgnoreCase(newCode.trim())){
                return "کتاب دیگری با این کد وجود دارد .";
            }
        }
        if (newYear != null && newYear < 0){
            return "سال انتشار باید عددی مثبت باشد .";
        }
        if (newCopies != null && newCopies < 0){
            return "تعداد نسخه باید عددی مثبت باشد .";
        }
        String oldCode = book.getCode_Book();
        book.setBook_Name(newName.trim());
        book.setAuthor_Name(newAuthor == null ? "" : newAuthor.trim());
        book.setCode_Book(newCode.trim());
        if (newYear != null) book.setYearPublication(newYear);
        if (newCopies != null) book.setQuantityAvailable(newCopies);
        Database.updateBook(book, oldCode);
        if (!oldCode.equalsIgnoreCase(book.getCode_Book())){
            Database.updateBookCodeReferences(oldCode, book.getCode_Book());
        }
        return null;
    }

    @Override
    public List<BorrowRequest> getPendingBorrowRequests(){
        List<BorrowRequest> pending = new ArrayList<>();
        for (BorrowRequest b : borrowRequests){
            if (!b.isApproved()){
                pending.add(b);
            }
        }
        return pending;
    }

    @Override
    public String approveBorrowRequest(BorrowRequest request){
        if (request.isApproved()){
            return "این درخواست قبلا تایید شده است .";
        }
        Book book = findBookByCode(request.getBook().getCode_Book());
        if (book == null || book.getQuantityAvailable() <= 0){
            return "موجودی این کتاب تمام شده است ; درخواست قابل تایید نیست .";
        }
        request.setApproved(true);
        book.setQuantityAvailable(book.getQuantityAvailable() - 1);
        Database.updateBorrowRequest(request);
        Database.updateBook(book);
        if (currentLibrarian != null){
            currentLibrarian.incrementBookBorrowed();
            Database.updateLibrarian(currentLibrarian);
        }
        return null;
    }

    // Approved borrows that have not been returned yet .
    @Override
    public List<BorrowRequest> getActiveBorrows(){
        List<BorrowRequest> active = new ArrayList<>();
        for (BorrowRequest b : borrowRequests){
            if (b.isApproved() && !b.isReturned()){
                active.add(b);
            }
        }
        return active;
    }

    @Override
    public String returnBook(BorrowRequest request){
        if (request.isReturned()){
            return "این کتاب قبلا تحویل داده شده است .";
        }
        request.setReturnDate(LocalDate.now());
        request.setReturned(true);
        Database.updateBorrowRequest(request);
        Book book = findBookByCode(request.getBook().getCode_Book());
        if (book != null){
            book.setQuantityAvailable(book.getQuantityAvailable() + 1);
            Database.updateBook(book);
        }
        if (currentLibrarian != null){
            currentLibrarian.incrementBookReturned();
            Database.updateLibrarian(currentLibrarian);
        }
        return null;
    }

    @Override
    public String toggleStudentStatus(String username){
        for (Student student : students){
            if (student.getUsername().equalsIgnoreCase(username)){
                student.setActive(!student.isActive());
                Database.updateStudent(student);
                return null;
            }
        }
        return "دانشجویی با این نام کاربری پیدا نشد .";
    }


    // ================= Manager =================

    @Override
    public String addLibrarian(String name, String username, String password){
        if (isBlank(name) || isBlank(username) || isBlank(password)){
            return "همه فیلدها باید پر شوند .";
        }
        for (Librarian l : librarians){
            if (l.getUsername_Librarian().equalsIgnoreCase(username.trim())){
                return "کتابداری با این نام کاربری وجود دارد .";
            }
        }
        Librarian librarian = new Librarian(name.trim(), username.trim(), password);
        librarians.add(librarian);
        Database.insertLibrarian(librarian);
        return null;
    }

    public Librarian findLibrarian(String username){
        for (Librarian l : librarians){
            if (l.getUsername_Librarian().equalsIgnoreCase(username)){
                return l;
            }
        }
        return null;
    }

    // Average days between start and return over finished ( returned ) borrows .
    @Override
    public double getAverageBorrowDurationDays(){
        long totalDays = 0;
        long finishedBorrows = 0;
        for (BorrowRequest b : borrowRequests){
            if (b.isApproved() && b.getReturnDate() != null){
                totalDays += java.time.temporal.ChronoUnit.DAYS.between(b.getStartDate(), b.getReturnDate());
                finishedBorrows++;
            }
        }
        return (finishedBorrows > 0) ? (double) totalDays / finishedBorrows : 0.0;
    }

    @Override
    public long countApprovedBorrows(){
        return borrowRequests.stream().filter(BorrowRequest::isApproved).count();
    }


    // ================= helpers =================

    private static boolean isBlank(String s){
        return s == null || s.trim().isEmpty();
    }

    private Book findBookByCode(String code){
        for (Book b : books){
            if (b.getCode_Book().equalsIgnoreCase(code)){
                return b;
            }
        }
        return null;
    }
}
