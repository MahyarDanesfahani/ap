package project.packman;

import java.util.Random;
import java.util.Scanner;

public class Main_EX2_PM_1_5 {
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

        int xrow = 1;
        int xcol = 1;
        star[xrow][xcol] = 'X';
        Main_EX2_PM_1_2.print(star);

        Random random = new Random();
        while (true) {
            star[xrow][xcol] = ' ';
            int direction = random.nextInt(4);
            int newx = xrow;
            int newy = xcol;
            switch (direction) {
                case 0:
                    if (xrow - 1 > 0){
                        newx--;
                    }
                    break;
                case 1:
                    if (xcol - 1 > 0) {
                        newy--;
                    }
                    break;
                case 2:
                    if (xrow + 1 < a - 1){
                        newx++;
                    }
                    break;
                case 3:
                    if (xcol + 1 < a - 1){
                        newy++;
                    }
                    break;
            }
            if (star[newx][newy] == ' ') {
                xrow = newx;
                xcol = newy;
            } else {
                System.out.println("Hitting the game wall!!");
            }
            star[xrow][xcol] = 'X';

            Main_EX2_PM_1_2.print(star);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}