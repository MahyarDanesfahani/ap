package project.packman;

import java.util.Scanner;

public class Main_EX2_PM_1_4 {
    public static boolean packman_Direction(char[][] star, int[] positionOfx, char s, Scanner scanner) {
        star[positionOfx[0]][positionOfx[1]] = ' ';
        System.out.print("Enter direction : ");
        String answer = scanner.next();
        if (answer.equals("0")) {
            System.out.println("Exiting game!!!!....");
            return true;
        }
        int newRow = positionOfx[0];
        int newCol = positionOfx[1];
        switch (answer.toLowerCase()) {
            case "w":
                newRow--; break;
            case "s":
                newRow++; break;
            case "a":
                newCol--; break;
            case "d":
                newCol++; break;
            default:
                System.out.println("Invalid input!!! Enter W, A, S, D or 0 .");
                return false;
        }
        if (star[newRow][newCol] == '*') {
            System.out.println("You hit the wall!!! Game Over!!!");
            return true;
        }
        if (star[newRow][newCol] == '.') {
            Main_EX2_PM_2_3.increaseScore();
            star[newRow][newCol] = ' ';
        }
        positionOfx[0] = newRow;
        positionOfx[1] = newCol;
        star[positionOfx[0]][positionOfx[1]] = s;
        return false;
    }
}