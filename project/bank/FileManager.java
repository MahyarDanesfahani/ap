package project.bank;

import java.io.*;
import java.util.ArrayList;

public class FileManager {
    private static final String FILE_NAME = "accounts.txt";

    public static void saveAccountsToFile(ArrayList<BankingManagement> accounts) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (BankingManagement acc : accounts) {
                writer.println(acc.getName() + "," +
                        acc.getNationalCode() + "," +
                        acc.getPhoneNumber() + "," +
                        acc.getCardNumber() + "," +
                        acc.getPassword() + "," +
                        acc.getAccountBalance());
            }
            System.out.println("Accounts saved to file . ");
        } catch (IOException e) {
            System.out.println("Error saving accounts : " + e.getMessage());
        }
    }

    public static ArrayList<BankingManagement> loadAccountsFromFile() {
        ArrayList<BankingManagement> accounts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    BankingManagement acc = new BankingManagement();
                    acc.setName(parts[0]);
                    acc.setNationalCode(Integer.parseInt(parts[1]));
                    acc.setPhoneNumber(Integer.parseInt(parts[2]));
                    acc.setCardNumber(Integer.parseInt(parts[3]));
                    acc.setPassword(Integer.parseInt(parts[4]));
                    acc.setAmount(Float.parseFloat(parts[5]));
                    accounts.add(acc);
                }
            }
            System.out.println("Accounts loaded from file . ");
        } catch (IOException e) {
            System.out.println("No file found , starting with empty account list . ");
        }
        return accounts;
    }
}
