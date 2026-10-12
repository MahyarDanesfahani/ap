package project.Library3;

import java.util.Scanner;

public class Manager {
    private static final String MANAGER_USERNAME = "admin";
    private static final String MANAGER_PASSWORD = "admin123";

    public static boolean login(Scanner scanner){
        System.out.println("=== Manager Login ===");
        System.out.println("Enter username : ");
        String username = scanner.nextLine();
        System.out.println("Enter password : ");
        String password = scanner.nextLine();

        if (MANAGER_USERNAME.equals(username) && MANAGER_PASSWORD.equals(password)){
            System.out.println("*! Welcome Manager !*");
            return true;
        }
        System.out.println("Invalid username or password . Returning to main menu .");
        return false;
    }
}
