package project.To_Do_List;

import java.lang.invoke.StringConcatFactory;

public class User {
    private String userName;
    private String password;
    private String fullName;
    private String major;
    private String city;

    public User(String userName, String password, String fullName,String major,String city){
        this.userName=userName;
        this.password=password;
        this.fullName=fullName;
        this.major=major;
        this.city=city;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMajor() {
        return major;
    }

    public String getCity() {
        return city;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toCSV(){
        return userName+","+password+","+fullName+","+major+","+city;
    }
}
