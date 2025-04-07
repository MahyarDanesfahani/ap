package project.packman;
import java.util.*;
import java.io.*;

public class PacmanEngine {
    private int k, c, score = 0;
    private char[][] matrix;
    private int[] position = {1, 1};
    private long startTime;
    private final String SAVE_FILE = " pacman_save.txt ";

    public PacmanEngine(int k, int c) {
        this.k = k;
        this.c = c;
        matrix = new char[k + 2][k + 2];
        initializeMatrix();
        placePoints(c);
        matrix[position[0]][position[1]] = 'X';
        startTime = System.currentTimeMillis();
    }

    private void initializeMatrix() {
        for (int i = 0; i < k + 2; i++) {
            for (int j = 0; j < k + 2; j++) {
                if (i == 0 || i == k + 1 || j == 0 || j == k + 1) {
                    matrix[i][j] = '*';
                } else {
                    matrix[i][j] = ' ';
                }
            }
        }
    }

    private void placePoints(int count) {
        Random rand = new Random();
        int placed = 0;
        while (placed < count) {
            int row = rand.nextInt(k) + 1;
            int col = rand.nextInt(k) + 1;
            if (matrix[row][col] == ' ') {
                matrix[row][col] = '.';
                placed++;
            }
        }
    }

    public void move(int direction) {
        int[] newPos = {position[0], position[1]};
        switch (direction) {
            case 0:
                newPos[0]--;
                break;
            case 1:
                newPos[1]--;
                break;
            case 2:
                newPos[1]++;
                break;
            case 3:
                newPos[0]++;
                break;
        }

        if (matrix[newPos[0]][newPos[1]] == '*')
            return;

        if (matrix[newPos[0]][newPos[1]] == '.') {
            score++;
        }

        matrix[position[0]][position[1]] = ' ';
        position = newPos;
        matrix[position[0]][position[1]] = 'X';
    }

    public void printMatrix() {
        for (char[] row : matrix) {
            for (char cell : row) System.out.print(cell + " ");
            System.out.println();
        }
    }

    public void printScore() {
        System.out.println("Score: " + score);
    }

    public void printRemainTime() {
        long now = System.currentTimeMillis();
        long elapsed = (now - startTime) / 1000;
        System.out.println("Elapsed Time: " + elapsed + " seconds");
    }

    public void save() {
        try (PrintWriter out = new PrintWriter(new FileWriter(SAVE_FILE))) {
            out.println(k);
            out.println(c);
            out.println(score);
            out.println(position[0] + " " + position[1]);
            for (char[] row : matrix) {
                out.println(new String(row));
            }
        } catch (IOException e) {
            System.out.println("Error saving game!");
        }
    }

    public void load() {
        try (BufferedReader in = new BufferedReader(new FileReader(SAVE_FILE))) {
            k = Integer.parseInt(in.readLine());
            c = Integer.parseInt(in.readLine());
            score = Integer.parseInt(in.readLine());
            String[] pos = in.readLine().split(" ");
            position[0] = Integer.parseInt(pos[0]);
            position[1] = Integer.parseInt(pos[1]);
            matrix = new char[k + 2][k + 2];
            for (int i = 0; i < k + 2; i++) {
                matrix[i] = in.readLine().toCharArray();
            }
        } catch (IOException e) {
            System.out.println("No saved game found.");
        }
    }

    public static void main(String[] args) {
        int k=9;
        int c=15;
        Random rnd = new Random();

        PacmanEngine pacmanEngine = new PacmanEngine(k,c);

        while(true) {
            pacmanEngine.printMatrix();
            pacmanEngine.printScore();
            pacmanEngine.printRemainTime();

            try {
                Thread.sleep(2000);
            } catch (Exception e) {}

            int direction=rnd.nextInt(4);
            pacmanEngine.move(direction);
            pacmanEngine.save();}
    }
}