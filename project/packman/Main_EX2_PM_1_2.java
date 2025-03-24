package project.packman;

import java.util.Scanner;

public class Main_EX2_PM_1_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the number ? ");
        int k = scanner.nextInt();
        int a = k + 2;
        char[][] star = new char[a][a];
        for (int i = 0; i < star.length; i++) {
            for (int j = 0; j < star[i].length; j++) {
                if (i == 0 || i == star.length - 1 || j == 0 || j == star[i].length - 1) {
                    star[i][j] = '*';
                } else {
                    star[i][j] = ' ';
                }
            }
            System.out.println();
        }
        Main_EX2_PM_1_2.print(star);
        scanner.close();
    }
    public static void print(char[][] star) {
        int a = star.length;
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < a; j++) {
                System.out.print(star[i][j]);
            }
            System.out.println();
        }
    }

}
