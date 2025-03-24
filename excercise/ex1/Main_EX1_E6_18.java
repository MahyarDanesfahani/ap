package excercise.ex1;

import java.util.Scanner;

public class Main_EX1_E6_18 {
    public static void main(String[] args) {
        star();
    }
    public static void star(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter the number : ");
        int y = scanner.nextInt();
        for (int i = 1; i <= y; i++) {
            for (int j = i; j < y; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= (2 * i - 1); j++) {
                System.out.print("*");
            }
            System.out.println();
        }
        for (int i = y - 1; i >= 1; i--) {
            for (int j = y; j > i; j--) {
                System.out.print(" ");
            }
            for (int j = 1; j <= (2 * i - 1); j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
