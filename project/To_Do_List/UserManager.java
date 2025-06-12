package project.To_Do_List;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users;
    private final String FILE_NAME = "users.csv";

    public UserManager(){
        users = new ArrayList<>();
        loadUsers();
    }

    private void loadUsers(){
        File file = new File(FILE_NAME);
        if (!file.exists()){
            CreateSampleUsers();
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))){
            String line;
            while ((line = br.readLine()) != null){
                String[] parts = line.split(",");
                if (parts.length == 5){
                    User user = new User(parts[0],parts[1],parts[2],parts[3],parts[4]);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void CreateSampleUsers(){
        users.add(new User("Mahyar", "1234", "Mahyar Danesfahani", "Computer", "Qazvin"));
        users.add(new User("reza", "reza", "Reza Hosseini", "Software", "Tabriz"));
        users.add(new User("Mahi", "star", "Mahi Valiie", "Doctor", "Qazvin"));
        users.add(new User("maryam", "0000", "Maryam Jafari", "AI", "Mashhad"));
        saveUsers();
    }

    private void saveUsers(){
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))){
            for (User user : users){
                pw.println(user.toCSV());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    User login(String username, String password){
        for (User user : users){
            if (user.getUserName().equals(username) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    public void changePassword(User user,String newpassword){
        user.setPassword(newpassword);
        saveUsers();
    }
}
