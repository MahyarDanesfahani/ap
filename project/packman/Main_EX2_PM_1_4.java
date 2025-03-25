package project.packman;

import java.util.Scanner;

public class Main_EX2_PM_1_4 {
    public static boolean packman_Direction(char[][] star, int[] positionOfx, char s, Scanner scanner) {
        System.out.println("The direction of game\n" +
                "char of 'W' = up\n" +
                "char of 'A' = left\n" +
                "char of 'D' = right\n" +
                "char of 'S' = down\n" +
                "char of '0' = Exit");

        while (true) {
            star[positionOfx[0]][positionOfx[1]] = ' ';
            System.out.print("Enter direction : ");
            String answer = scanner.next();
            if (answer.equals("0")) {
                System.out.println("Exiting game!!!!");
                return true;
            }
            int newRow = positionOfx[0];
            int newCol = positionOfx[1];
            switch (answer.toLowerCase()) {
                case "w":
                    newRow--;
                    break;
                case "s":
                    newRow++;
                    break;
                case "a":
                    newCol--;
                    break;
                case "d":
                    newCol++;
                    break;
                default:
                    System.out.println("Warning!! Invalid input!!! Please enter W, A, S, D or 0 .");
                    continue;
            }

            if (star[newRow][newCol] == '*') {
                System.out.println("Hitting the game wall!!!! Game Over!!!");
                System.exit(0);
            }
            positionOfx[0] = newRow;
            positionOfx[1] = newCol;
            star[positionOfx[0]][positionOfx[1]] = s;

            Main_EX2_PM_1_2.print(star);
        }
    }
}