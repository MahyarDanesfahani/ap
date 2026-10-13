package project.Library3;

public class Book {
    private String Book_Name;
    private String Author_Name;
    private String Code_Book;
    private int yearPublication;
    private int quantityAvailable;
    private int Count_Book;

    public Book(String bookName , String authorName,String code_Book ,int yearPublication , int quantityAvailable){
        this.Book_Name=bookName;
        this.Author_Name=authorName;
        this.Code_Book=code_Book;
        this.yearPublication=yearPublication;
        this.quantityAvailable=quantityAvailable;
    }

    public String getBook_Name() {
        return Book_Name;
    }
    public String getAuthor_Name() {
        return Author_Name;
    }
    public int getYearPublication() {
        return yearPublication;
    }
    public int getQuantityAvailable() {
        return quantityAvailable;
    }
    public int getCount_Book() {
        return Count_Book;
    }
    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }
    public void setBook_Name(String book_Name) {
        Book_Name = book_Name;
    }
    public void setAuthor_Name(String author_Name) {
        Author_Name = author_Name;
    }
    public void setCode_Book(String code_Book) {
        Code_Book = code_Book;
    }
    public void setYearPublication(int yearPublication) {
        this.yearPublication = yearPublication;
    }
    public String getCode_Book() {
        return Code_Book;
    }
    public boolean isAvailable() { return quantityAvailable > 0; }

    @Override
    public String toString(){
        return "Book { " +
                "\nThe Name : " + this.Book_Name +
                "\nThe Author_Name : " + this.Author_Name +
                "\nThe Code_Book : " + this.Code_Book +
                "\nThe yaer Publication : " + this.yearPublication +
                "\nThe Quantity available : " + this.getQuantityAvailable() +
                "\nThe Available is : " + (isAvailable() ? "Yes" : "No") +
                "\n }";
    }
}
