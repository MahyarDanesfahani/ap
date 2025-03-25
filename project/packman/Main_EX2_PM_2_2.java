package project.packman;

import java.util.Scanner;

public class Main_EX2_PM_2_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the number ? ");
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

        System.out.print("Please enter the number of points ? ");
        int numOfPoints = scanner.nextInt();
        int totalPoints = numOfPoints;
        for (int i = 0; i < numOfPoints; i++) {
            boolean validPosition = false;
            while (!validPosition) {
                System.out.print("Enter the position of point " + (i + 1) + " (row and column between 1 and " + k + ") ? ");
                int row = scanner.nextInt();
                int col = scanner.nextInt();
                if (row >= 1 && row <= k && col >= 1 && col <= k) {
                    if (star[row][col] == ' ') {
                        star[row][col] = '.';
                        validPosition = true;
                    } else {
                        System.out.println("This position is already occupied . Try another .");
                    }
                } else {
                    System.out.println("Invalid position!!! Please try again .");
                }
            }
        }

        Main_EX2_PM_1_2.print(star);

        int[] positionOfx = {1, 1};
        star[positionOfx[0]][positionOfx[1]] = 'X';

        Main_EX2_PM_1_2.print(star);

        long start = System.currentTimeMillis();
        int score = 0;
        System.out.println("The direction of game:\n" +
                "'W' = up\n" +
                "'A' = left\n" +
                "'D' = right\n" +
                "'S' = down\n" +
                "'0' = Exit");

        while (true) {
            boolean exit = Main_EX2_PM_1_4.packman_Direction(star, positionOfx, 'X', scanner);
            if (exit) {
                System.out.println("Game Stopped by User!!! ");
                break;
            }
            if (star[positionOfx[0]][positionOfx[1]] == '.') {
                score++;
                star[positionOfx[0]][positionOfx[1]] = ' ';
            }
            if (score >= totalPoints) {
                System.out.println("All points collected!!! ");
                break;
            }

            Main_EX2_PM_1_2.print(star);
        }

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Game Over!!!");
        System.out.println("Your score : " + score);
        System.out.println("Time elapsed : " + timeElapsed + " milliseconds .");

        scanner.close();
    }
}
