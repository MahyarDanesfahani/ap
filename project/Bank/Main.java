package project.Bank;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<BankingManagement> accounts = FileManager.loadAccountsFromFile();

        System.out.println("Welcome to Banking Management System");
        boolean running = true;
        while (running) {
            System.out.println("\n1. Create Account\n2. Deposit or Withdraw\n3. Check Balance\n4. Transfer Money\n5. Exit");
            System.out.print("Choose an option : ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    BankingManagement newAcc = new BankingManagement();
                    newAcc.fillInformationForm();

                    System.out.print("Enter password (4-digit) : ");
                    int password = scanner.nextInt();

                    System.out.print("Enter initial deposit : ");
                    float amount = scanner.nextFloat();

                    newAcc.createAccount(password, amount, accounts);
                    accounts.add(newAcc);
                }
                case 2 -> {
                    System.out.print("Enter your card number : ");
                    int cardNumber = scanner.nextInt();
                    BankingManagement acc = findAccountByCardNumber(accounts, cardNumber);

                    if (acc != null) {
                        acc.depositOrWithdraw();
                    } else {
                        System.out.println("Account not found . ");
                    }
                }
                case 3 -> {
                    System.out.print("Enter your card number : ");
                    int cardNumber = scanner.nextInt();
                    BankingManagement acc = findAccountByCardNumber(accounts, cardNumber);

                    if (acc != null) {
                        System.out.print("Enter your password : ");
                        int password = scanner.nextInt();
                        if (acc.checkPassword(password)) {
                            System.out.println("Your account balance is : " + acc.getAccountBalance());
                        } else {
                            System.out.println("Incorrect password . ");
                        }
                    } else {
                        System.out.println("Account not found . ");
                    }
                }
                case 4 -> {
                    System.out.print("Enter your card number : ");
                    int senderCard = scanner.nextInt();
                    BankingManagement sender = findAccountByCardNumber(accounts, senderCard);

                    if (sender == null) {
                        System.out.println("Sender account not found . ");
                        continue;
                    }

                    System.out.print("Enter your password : ");
                    int password = scanner.nextInt();
                    if (!sender.checkPassword(password)) {
                        System.out.println("Incorrect password . ");
                        continue;
                    }

                    System.out.print("Enter recipient's card number : ");
                    int receiverCard = scanner.nextInt();
                    BankingManagement receiver = findAccountByCardNumber(accounts, receiverCard);

                    if (receiver == null) {
                        System.out.println("Receiver account not found . ");
                        continue;
                    }

                    System.out.print("Enter amount to transfer : ");
                    float amount = scanner.nextFloat();
                    if (sender.transferTo(receiver, amount)) {
                        System.out.println("Transfer successful . ");
                    } else {
                        System.out.println("Insufficient balance . ");
                    }
                }
                case 5 -> {
                    FileManager.saveAccountsToFile(accounts);
                    running = false;
                    System.out.println("Goodbye");
                }
                default -> System.out.println("Invalid choice . ");
            }
        }
    }

    private static BankingManagement findAccountByCardNumber(ArrayList<BankingManagement> accounts, int cardNumber) {
        for (BankingManagement acc : accounts) {
            if (acc.getCardNumber() == cardNumber) {
                return acc;
            }
        }
        return null;
    }
}