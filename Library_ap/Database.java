package project.Library3;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * SQLite persistence layer .
 * One shared connection on library.db ; every mutation is written to the
 * database immediately , and saveAll(...) rewrites everything inside a single
 * transaction ( used by the shutdown hook and the one-time JSON migration ) .
 */
public final class Database {

    private static final String DB_URL = "jdbc:sqlite:library.db";
    private static Connection conn;

    private Database() {
    }

    public static void init() {
        if (conn != null) {
            return;
        }
        try {
            conn = DriverManager.getConnection(DB_URL);
            createTables();
        } catch (SQLException e) {
            throw new RuntimeException("Could not open library.db : " + e.getMessage(), e);
        }
    }

    public static void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ignored) {
        }
        conn = null;
    }

    private static void createTables() throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS students (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "full_name TEXT NOT NULL," +
                    "username TEXT NOT NULL UNIQUE COLLATE NOCASE," +
                    "password TEXT NOT NULL," +
                    "student_number TEXT," +
                    "field_of_study TEXT," +
                    "active INTEGER NOT NULL DEFAULT 1)");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS books (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "code TEXT NOT NULL UNIQUE COLLATE NOCASE," +
                    "name TEXT NOT NULL," +
                    "author TEXT," +
                    "year_published INTEGER," +
                    "quantity_available INTEGER NOT NULL DEFAULT 0)");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS librarians (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "username TEXT NOT NULL UNIQUE COLLATE NOCASE," +
                    "password TEXT," +
                    "book_added INTEGER NOT NULL DEFAULT 0," +
                    "book_borrowed INTEGER NOT NULL DEFAULT 0," +
                    "book_returned INTEGER NOT NULL DEFAULT 0)");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS borrow_requests (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "student_username TEXT NOT NULL," +
                    "book_code TEXT NOT NULL," +
                    "start_date TEXT," +
                    "end_date TEXT," +
                    "return_date TEXT," +
                    "returned INTEGER NOT NULL DEFAULT 0," +
                    "approved INTEGER NOT NULL DEFAULT 0)");
        }
    }

    // ---------- loads ----------

    public static ArrayList<Student> loadStudents() {
        ArrayList<Student> list = new ArrayList<>();
        String sql = "SELECT full_name, username, password, student_number, field_of_study, active FROM students";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Student(
                        rs.getString("full_name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("student_number"),
                        rs.getString("field_of_study"),
                        rs.getInt("active") == 1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Book> loadBooks() {
        ArrayList<Book> list = new ArrayList<>();
        String sql = "SELECT code, name, author, year_published, quantity_available FROM books";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Book(
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getString("code"),
                        rs.getInt("year_published"),
                        rs.getInt("quantity_available")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Librarian> loadLibrarians() {
        ArrayList<Librarian> list = new ArrayList<>();
        String sql = "SELECT name, username, password, book_added, book_borrowed, book_returned FROM librarians";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Librarian(
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("book_added"),
                        rs.getInt("book_borrowed"),
                        rs.getInt("book_returned")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Requests are stored by student username + book code ; they are relinked
    // to the canonical Student/Book objects of the loaded lists .
    public static ArrayList<BorrowRequest> loadBorrowRequests(List<Student> students, List<Book> books) {
        ArrayList<BorrowRequest> list = new ArrayList<>();
        String sql = "SELECT id, student_username, book_code, start_date, end_date, return_date, returned, approved FROM borrow_requests";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Student student = findStudentByUsername(students, rs.getString("student_username"));
                Book book = findBookByCode(books, rs.getString("book_code"));
                if (student == null || book == null) {
                    continue;
                }
                BorrowRequest br = new BorrowRequest(student, book,
                        date(rs.getString("start_date")), date(rs.getString("end_date")));
                br.setId(rs.getInt("id"));
                br.setApproved(rs.getInt("approved") == 1);
                br.setReturned(rs.getInt("returned") == 1);
                br.setReturnDate(date(rs.getString("return_date")));
                list.add(br);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ---------- writes ----------

    public static void insertStudent(Student s) {
        String sql = "INSERT INTO students(full_name, username, password, student_number, field_of_study, active) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getFirst_last_Name());
            ps.setString(2, s.getUsername());
            ps.setString(3, s.getPassword());
            ps.setString(4, s.getStudentNumber());
            ps.setString(5, s.getFieldOfStudy());
            ps.setInt(6, s.isActive() ? 1 : 0);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertBook(Book b) {
        String sql = "INSERT INTO books(code, name, author, year_published, quantity_available) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getCode_Book());
            ps.setString(2, b.getBook_Name());
            ps.setString(3, b.getAuthor_Name());
            ps.setInt(4, b.getYearPublication());
            ps.setInt(5, b.getQuantityAvailable());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertLibrarian(Librarian l) {
        String sql = "INSERT INTO librarians(name, username, password, book_added, book_borrowed, book_returned) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, l.getName_Librarian());
            ps.setString(2, l.getUsername_Librarian());
            ps.setString(3, l.getPassword_Librarian());
            ps.setInt(4, l.getBookAdded());
            ps.setInt(5, l.getBookBorrowed());
            ps.setInt(6, l.getBookReturned());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Also assigns the generated row id to the request ( needed for updates ) .
    public static void insertBorrowRequest(BorrowRequest br) {
        String sql = "INSERT INTO borrow_requests(student_username, book_code, start_date, end_date, return_date, returned, approved) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, br.getStudent().getUsername());
            ps.setString(2, br.getBook().getCode_Book());
            setNullableDate(ps, 3, br.getStartDate());
            setNullableDate(ps, 4, br.getEndDate());
            setNullableDate(ps, 5, br.getReturnDate());
            ps.setInt(6, br.isReturned() ? 1 : 0);
            ps.setInt(7, br.isApproved() ? 1 : 0);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    br.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Username is the key : it never changes for a student .
    public static void updateStudent(Student s) {
        String sql = "UPDATE students SET full_name=?, password=?, student_number=?, field_of_study=?, active=? WHERE username=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getFirst_last_Name());
            ps.setString(2, s.getPassword());
            ps.setString(3, s.getStudentNumber());
            ps.setString(4, s.getFieldOfStudy());
            ps.setInt(5, s.isActive() ? 1 : 0);
            ps.setString(6, s.getUsername());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // oldCode is passed separately because the librarian can edit the book's code .
    public static void updateBook(Book b, String oldCode) {
        String sql = "UPDATE books SET code=?, name=?, author=?, year_published=?, quantity_available=? WHERE code=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getCode_Book());
            ps.setString(2, b.getBook_Name());
            ps.setString(3, b.getAuthor_Name());
            ps.setInt(4, b.getYearPublication());
            ps.setInt(5, b.getQuantityAvailable());
            ps.setString(6, oldCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBook(Book b) {
        updateBook(b, b.getCode_Book());
    }

    // Keeps borrow rows pointing at the right book after a code edit .
    public static void updateBookCodeReferences(String oldCode, String newCode) {
        String sql = "UPDATE borrow_requests SET book_code=? WHERE book_code=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newCode);
            ps.setString(2, oldCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateLibrarian(Librarian l) {
        String sql = "UPDATE librarians SET name=?, password=?, book_added=?, book_borrowed=?, book_returned=? WHERE username=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, l.getName_Librarian());
            ps.setString(2, l.getPassword_Librarian());
            ps.setInt(3, l.getBookAdded());
            ps.setInt(4, l.getBookBorrowed());
            ps.setInt(5, l.getBookReturned());
            ps.setString(6, l.getUsername_Librarian());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBorrowRequest(BorrowRequest br) {
        String sql = "UPDATE borrow_requests SET start_date=?, end_date=?, return_date=?, returned=?, approved=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            setNullableDate(ps, 1, br.getStartDate());
            setNullableDate(ps, 2, br.getEndDate());
            setNullableDate(ps, 3, br.getReturnDate());
            ps.setInt(4, br.isReturned() ? 1 : 0);
            ps.setInt(5, br.isApproved() ? 1 : 0);
            ps.setInt(6, br.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------- full rewrite ( shutdown hook + JSON migration ) ----------

    public static void saveAll(List<Student> students, List<Book> books,
                               List<Librarian> librarians, List<BorrowRequest> borrowRequests) {
        try {
            conn.setAutoCommit(false);
            try (Statement st = conn.createStatement()) {
                st.executeUpdate("DELETE FROM borrow_requests");
                st.executeUpdate("DELETE FROM students");
                st.executeUpdate("DELETE FROM books");
                st.executeUpdate("DELETE FROM librarians");
            }
            for (Student s : students) {
                insertStudent(s);
            }
            for (Book b : books) {
                insertBook(b);
            }
            for (Librarian l : librarians) {
                insertLibrarian(l);
            }
            for (BorrowRequest br : borrowRequests) {
                insertBorrowRequest(br);
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ignored) {
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
    }

    // ---------- helpers ----------

    private static void setNullableDate(PreparedStatement ps, int index, LocalDate date) throws SQLException {
        if (date == null) {
            ps.setNull(index, Types.VARCHAR);
        } else {
            ps.setString(index, date.toString());
        }
    }

    private static LocalDate date(String text) {
        return text == null ? null : LocalDate.parse(text);
    }

    private static Student findStudentByUsername(List<Student> students, String username) {
        for (Student s : students) {
            if (s.getUsername().equalsIgnoreCase(username)) {
                return s;
            }
        }
        return null;
    }

    private static Book findBookByCode(List<Book> books, String code) {
        for (Book b : books) {
            if (b.getCode_Book().equalsIgnoreCase(code)) {
                return b;
            }
        }
        return null;
    }
}
