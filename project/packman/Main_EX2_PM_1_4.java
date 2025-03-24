package project.packman;

import java.util.Scanner;

public class Main_EX2_PM_1_4 {

    public static void packman_Direction (char[][] star , int row, int col , char s) {
        Scanner scanner =  new Scanner(System.in);
        System.out.println("The direction of game\n" +
                "char of 'W' = up\n" +
                "char of 'A' = left\n" +
                "char of 'D' = right\n" +
                "char of 'S' = down");
        while (true) {
            System.out.print("Enter direction : ");
            String answer = scanner.next();
            if (answer.equals("0")) {
                System.out.println("Exiting game...");
                break;
            }
            star[row][col] = ' ';
            switch (answer) {
                case "w":
                    if (row > 1){
                        row--;
                    }
                    break;
                case "s":
                    if (row < star.length - 2){
                        row++;
                    }
                    break;
                case "a":
                    if (col > 1){
                        col--;
                    }
                    break;
                case "d":
                    if (col < star[0].length - 2){
                        col++;
                    }
                    break;
                default:
                    System.out.println("Warning!! invalid input! Please enter W, A, S, D or 0.");
            }
            star[row][col] = s;

            Main_EX2_PM_1_2.print(star);
        }
        scanner.close();
    }
}