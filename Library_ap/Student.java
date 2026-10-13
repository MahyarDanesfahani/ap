package project.Library3;

public class Student {

    private String first_last_Name;
    private String username;
    private String password;
    private String studentNumber;
    private String fieldOfStudy;
    private int Count_Student = 0;
    private boolean active = true;

    public Student(){}

    // used when a student is registered or a row is loaded from the database
    public Student(String first_last_Name, String username, String password,
                   String studentNumber, String fieldOfStudy, boolean active){
        this.first_last_Name = first_last_Name;
        this.username = username;
        this.password = password;
        this.studentNumber = studentNumber;
        this.fieldOfStudy = fieldOfStudy;
        this.active = active;
    }

    public String getFirst_last_Name() {
        return first_last_Name;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getStudentNumber() {
        return studentNumber;
    }
    public String getFieldOfStudy() {
        return fieldOfStudy;
    }
    public int getCount_Student() {
        return Count_Student;
    }
    public boolean isActive(){ return active;}
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString(){
        return ("Student { " +
                "\nThe Name : " + this.first_last_Name +
                "\nThe Student_Number : " + this.studentNumber +
                "\nThe Field of study : " + this.fieldOfStudy +
                "\nThe Active is : " + this.active +
                "\n }");
    }
}
