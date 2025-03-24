package excercise.ex1;

import java.util.Scanner;

public class Main_EX1_E5_18 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter the 3 string : ");
        String n1 = scanner.nextLine();
        String n2 = scanner.nextLine();
        String n3 = scanner.nextLine();
        sort(n3, n2, n1);
    }

    public static void sort(String n1, String n2, String n3) {
        String first, second, third;
        if (n1.compareTo(n2) <= 0 && n1.compareTo(n3) <= 0) {
            first = n1;
            if (n2.compareTo(n3) <= 0) {
                second = n2;
                third = n3;
            } else {
                second = n3;
                third = n2;
            }
        } else if (n2.compareTo(n1) <= 0 && n2.compareTo(n3) <= 0) {
            first = n2;
            if (n1.compareTo(n3) <= 0) {
                second = n1;
                third = n3;
            } else {
                second = n3;
                third = n1;
            }
        } else {
            first = n3;
            if (n1.compareTo(n2) <= 0) {
                second = n1;
                third = n2;
            } else {
                second = n2;
                third = n1;
            }
        }
    }
}
