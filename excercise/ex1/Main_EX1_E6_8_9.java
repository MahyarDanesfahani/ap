package excercise.ex1;

import java.util.Scanner;

public class Main_EX1_E6_8_9 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter the world : ");
        String world = scanner.nextLine();
        character(world);
    }
    public static void character(String name){
        char[] show = new char[name.length()];
        for (int i = 0; i < name.length(); i++) {
            show[i] = name.charAt(i);
        }
        for (int i = 0; i < name.length(); i++) {
            System.out.println(show[i]);
        }
        System.out.print("Reversed word: ");
        for (int i = show.length - 1; i >= 0; i--) {
            System.out.print(show[i]);
        }
    }
}
