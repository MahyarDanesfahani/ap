package project.Library3;

public class Manager {
    private static final String MANAGER_USERNAME = "admin";
    private static final String MANAGER_PASSWORD = "admin123";

    public static boolean authenticate(String username, String password){
        return MANAGER_USERNAME.equals(username) && MANAGER_PASSWORD.equals(password);
    }
}
