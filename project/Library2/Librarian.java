package project.Library2;

import java.util.ArrayList;

public class Librarian {
    private String firstLastName ;
    private String userName ;
    private int password ;
    private ArrayList<BorrowRecord> borrowRecords = new ArrayList<>();

    public Librarian(String firstLastName , String userName , int password){
        this.firstLastName = firstLastName;
        this.userName = userName;
        this.password = password ;
    }

    public void addBorrowRecord(BorrowRecord record){
        borrowRecords.add(record);
    }

    public void removeBorrowRecord(BorrowRecord record){
        borrowRecords.remove(record);
    }

    public ArrayList<BorrowRecord> getBorrowRecords(){
        return borrowRecords;
    }

    public String getFirstLastName() {
        return firstLastName;
    }

    public String getUserName() {
        return userName;
    }

    public int getPassword() {
        return password;
    }
}