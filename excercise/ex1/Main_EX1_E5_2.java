package excercise.ex1;

import java.util.Scanner;

public class Main_EX1_E5_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter the double number : ");
        double x = scanner.nextDouble();
        check(x);
    }
    public static void check(double x){
        if ( x == 0 ){
            System.out.println(x);
        } else if (x > 1000000) {
            System.out.println("the number is positive and large ");
        }
        else {
            System.out.println("the number is negative and small");
        }
    }
}
