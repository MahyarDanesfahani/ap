package project.packman;

import java.util.Scanner;

public class Main_EX2_PM_1_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the number: ");
        int k = scanner.nextInt();
        int a = k + 2;
        char[][] star = new char[a][a];
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < a; j++) {
                if (i == 0 || i == a - 1 || j == 0 || j == a - 1) {
                    star[i][j] = '*';
                } else {
                    star[i][j] = ' ';
                }
            }
        }

        Main_EX2_PM_1_2.print(star);

        System.out.print("Please enter a number between 1 and " + (k * k) + ": ");
        int c = scanner.nextInt();
        while (c < 1 || c > k * k) {
            System.out.print("Error! Please enter a valid number ? ");
            c = scanner.nextInt();
        }
        int row = (c - 1) / k + 1;
        int col = (c - 1) % k + 1;
        if (star[row][col] == ' ') {
            star[row][col] = '.';
        }

        Main_EX2_PM_1_2.print(star);

        scanner.close();
    }
}