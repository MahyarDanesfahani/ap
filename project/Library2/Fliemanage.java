package project.Library2;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Fliemanage {
    private static final String Name_File1 = "Student.txt";
    private static final String Name_File2 = "Book.txt";
    private static final String Name_File3 = "Student_borrowed.txt";
    private static final String Name_File4 = "ReportsOfLibrarian.txt";

    public static void saveStudentOfLibrary(ArrayList<Student> students) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(Name_File1));) {
            for (Student student : students) {
                    writer.println(student.getFirstLastName() + "," +
                            student.getFieldOfStudy() + "," +
                            student.getStudentNumber() + "," +
                            student.getMembershipDate());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveBookOfLibrary(ArrayList<Book> books) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(Name_File2));) {
            for (Book book : books) {
                writer.println(book.getBookName() + "," +
                        book.getAuthorName() + "," +
                        book.getYearPublication() + "," +
                        book.getQuantityAvailable());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveStudentBorrowedOfLibrary(ArrayList<BorrowRecord> records) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(Name_File3));) {
            for (BorrowRecord record : records) {
                writer.println(record.getStudent().getFirstLastName() + "," +
                        record.getStudent().getStudentNumber() + "," +
                        record.getStudent().getMembershipDate() + "," +
                        record.getBook().getBookName() + "," +
                        record.getBook().getAuthorName());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveReportToFile(String reportContent){
        try (PrintWriter writer = new PrintWriter(new FileWriter(Name_File4))){
            writer.println(reportContent);
        }catch (IOException e) {
            System.out.println("Error saving report: " + e.getMessage());
        }
    }

    public static ArrayList<Student> loadStudentOfLibrary() throws FileNotFoundException {
        ArrayList<Student> students = new ArrayList<>();
        File file = new File(Name_File1);
        if (!file.exists()){
            try {
                file.createNewFile();
                return students;
            } catch (IOException e) {
                e.printStackTrace();
                return students;
            }
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(Name_File1))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String name = parts[0];
                    String field = parts[1];
                    Long number = Long.parseLong(parts[2]);
                    LocalDate member = LocalDate.parse(parts[3]);
                    students.add(new Student(name, field, number));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static ArrayList<Book> loadBookOfLibrary() {
        ArrayList<Book> books = new ArrayList<>();
        File file = new File(Name_File2);
        if (!file.exists()){
            try {
                file.createNewFile();
                return books;
            } catch (IOException e) {
                e.printStackTrace();
                return books;
            }
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(Name_File2))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String name = parts[0];
                    String author = parts[1];
                    int year = Integer.parseInt(parts[2]);
                    int qty = Integer.parseInt(parts[3]);
                    books.add(new Book(name, author, year,qty));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    public static ArrayList<BorrowRecord> loadStudentBorrowedOfLibrary(ArrayList<Student> students, ArrayList<Book> books) {
        ArrayList<BorrowRecord> records = new ArrayList<>();
        File file = new File(Name_File3);
        if (!file.exists()){
            try {
                file.createNewFile();
                return records;
            } catch (IOException e) {
                e.printStackTrace();
                return records;
            }
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(Name_File3))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 5)
                    continue;
                String nameStudent = parts[0];
                Long number = Long.parseLong(parts[1]);
                LocalDate member = LocalDate.parse(parts[2]);
                String nameBook = parts[3];
                String author = parts[4];

                Student student2 = null;
                for (Student s : students) {
                    if (s.getFirstLastName().equalsIgnoreCase(nameStudent)
                            && s.getStudentNumber() == number
                            && s.getMembershipDate().equals(member)) {
                        student2 = s;
                        break;
                    }
                }

                Book book2 = null;
                for (Book b : books) {
                    if (b.getBookName().equals(nameBook) && b.getAuthorName().equals(author)) {
                        book2 = b;
                        break;
                    }
                }

                if (student2 != null && book2 != null) {
                    BorrowRecord record = new BorrowRecord(student2, book2);
                    records.add(record);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }
}