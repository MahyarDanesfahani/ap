package project.packman;

import java.io.BufferedReader;
import java.io.*;
import java.util.Scanner;

public class Main_EX2_PM_2_3 {
    private static final String SAVE_FILE = "game_save.txt";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k, score = 0, totalPoints = 0;
        int[] positionOfx = {1, 1};
        char[][] star;
        long start = System.currentTimeMillis();
        if (new File(SAVE_FILE).exists()) {
            System.out.print("Saved game found!!! Do you want to continue ? (yes/no) : ");
            String response = scanner.next();
            if (response.equalsIgnoreCase("yes")) {
                Object[] gameData = loadGame();
                if (gameData != null) {
                    k = (int) gameData[0];
                    totalPoints = (int) gameData[1];
                    score = (int) gameData[2];
                    positionOfx = (int[]) gameData[3];
                    star = (char[][]) gameData[4];
                } else {
                    k = initializeGame(scanner);
                    totalPoints = getTotalPoints(scanner);
                    star = createBoard(k);
                    placePoints(scanner, star, k, totalPoints);
                }
            } else {
                k = initializeGame(scanner);
                totalPoints = getTotalPoints(scanner);
                star = createBoard(k);
                placePoints(scanner, star, k, totalPoints);
            }
        } else {
            k = initializeGame(scanner);
            totalPoints = getTotalPoints(scanner);
            star = createBoard(k);
            placePoints(scanner, star, k, totalPoints);
        }
        star[positionOfx[0]][positionOfx[1]] = 'X';

        Main_EX2_PM_1_2.print(star);

        System.out.println("Game Controls : " +
                "char of 'W' = Up" +
                "char of 'A' = Left" +
                "char of 'D' = Right" +
                "char of 'S' = Down" +
                " 0 = Exit");
        while (score < totalPoints) {
            boolean exit = Main_EX2_PM_1_4.packman_Direction(star, positionOfx, 'X', scanner);
            if (exit) {
                saveGame(k, totalPoints, score, positionOfx, star);
                System.out.println("Game Saved . Exiting...");
                return;
            }
            if (star[positionOfx[0]][positionOfx[1]] == '.') {
                score++;
                star[positionOfx[0]][positionOfx[1]] = ' ';
            }

            Main_EX2_PM_1_2.print(star);
        }
        System.out.println("All points collected!!! Game Over .");
        new File(SAVE_FILE).delete();
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Your Score : " + score);
        System.out.println("Time Elapsed : " + timeElapsed + " milliseconds . ");
        scanner.close();
    }

    private static int initializeGame(Scanner scanner) {
        System.out.print("Enter board size : ");
        return scanner.nextInt();
    }

    private static int getTotalPoints(Scanner scanner) {
        System.out.print("Enter number of points : ");
        return scanner.nextInt();
    }

    private static char[][] createBoard(int k) {
        int a = k + 2;
        char[][] board = new char[a][a];
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < a; j++) {
                board[i][j] = (i == 0 || i == a - 1 || j == 0 || j == a - 1) ? '*' : ' ';
            }
        }
        return board;
    }

    private static void placePoints(Scanner scanner, char[][] board, int k, int totalPoints) {
        for (int i = 0; i < totalPoints; i++) {
            boolean validPosition = false;
            while (!validPosition) {
                System.out.print("Enter row and column for point " + (i + 1) + " : ");
                int row = scanner.nextInt();
                int col = scanner.nextInt();
                if (row >= 1 && row <= k && col >= 1 && col <= k && board[row][col] == ' ') {
                    board[row][col] = '.';
                    validPosition = true;
                } else {
                    System.out.println("Invalid or occupied position . Try again . ");
                }
            }
        }
    }

    private static void saveGame(int k, int totalPoints, int score, int[] positionOfx, char[][] board) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SAVE_FILE))) {
            writer.println(k);
            writer.println(totalPoints);
            writer.println(score);
            writer.println(positionOfx[0] + " " + positionOfx[1]);
            for (char[] row : board) {
                writer.println(new String(row));
            }
        } catch (IOException e) {
            System.out.println("Error saving game!!! ");
        }
    }

    private static Object[] loadGame() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
            int k = Integer.parseInt(reader.readLine());
            int totalPoints = Integer.parseInt(reader.readLine());
            int score = Integer.parseInt(reader.readLine());
            String[] pos = reader.readLine().split(" ");
            int[] positionOfx = {Integer.parseInt(pos[0]), Integer.parseInt(pos[1])};
            char[][] board = new char[k + 2][k + 2];
            for (int i = 0; i < board.length; i++) {
                board[i] = reader.readLine().toCharArray();
            }
            return new Object[]{k, totalPoints, score, positionOfx, board};
        } catch (IOException e) {
            System.out.println("Error loading game!!!");
            return null;
        }
    }
}
