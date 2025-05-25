package project.bank;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class BankingManagement {
    private static Random random = new Random();

    private String name;
    private long nationalCode;
    private long phoneNumber;


    private float amount;
    private int password;
    private int cardNumber;

    public void fillInformationForm() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter full name : ");
        name = scanner.nextLine();

        System.out.print("Enter national code : ");
        nationalCode = scanner.nextLong();

        System.out.print("Enter phone number : ");
        phoneNumber = scanner.nextLong();
    }

    public void createAccount(int password, float amount, ArrayList<BankingManagement> allAccounts) {
        this.cardNumber = generateUniqueCardNumber(allAccounts);
        this.password = password;
        this.amount = amount;

        System.out.println("Card number : " + cardNumber);

        int expirationMonth = 1 + random.nextInt(12);
        System.out.println("Expiration date : 1408/" + expirationMonth);

        System.out.println("Initial deposit : " + amount);
    }

    private int generateUniqueCardNumber(ArrayList<BankingManagement> allAccounts) {
        int number;
        boolean isUnique;

        do {
            number = 100000 + random.nextInt(900000);
            isUnique = true;

            for (BankingManagement acc : allAccounts) {
                if (acc.cardNumber == number) {
                    isUnique = false;
                    break;
                }
            }
        } while (!isUnique);

        return number;
    }

    public void depositOrWithdraw() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter password : ");
        int enteredPassword = scanner.nextInt();

        if (enteredPassword != password) {
            System.out.println("Incorrect password . ");
            return;
        }

        System.out.print("Enter amount : ");
        float money = scanner.nextFloat();

        System.out.print("1.Deposit  2.Withdraw : ");
        int action = scanner.nextInt();

        if (action == 1) {
            amount += money;
            System.out.println("Deposited . New balance : " + amount);
        } else if (action == 2) {
            if (money > amount) {
                System.out.println("Insufficient balance .");
            } else {
                amount -= money;
                System.out.println("Withdrawn . New balance : " + amount);
            }
        } else {
            System.out.println("Invalid action . ");
        }
    }

    public float getAccountBalance() {
        return amount;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public boolean checkPassword(int enteredPassword) {
        return enteredPassword == password;
    }

    public boolean transferTo(BankingManagement receiver, float money) {
        if (money > amount) {
            return false;
        }
        this.amount -= money;
        receiver.amount += money;
        return true;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public long getNationalCode() { return nationalCode; }
    public void setNationalCode(long nationalCode) { this.nationalCode = nationalCode; }

    public long getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(long phoneNumber) { this.phoneNumber = phoneNumber; }


    public int getPassword() { return password; }
    public void setPassword(int password) { this.password = password; }

    public void setCardNumber(int cardNumber) { this.cardNumber = cardNumber; }
    public void setAmount(float amount) { this.amount = amount; }
}