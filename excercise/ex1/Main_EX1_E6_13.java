package excercise.ex1;

import java.util.Scanner;

public class Main_EX1_E6_13 {
    public static void main(String[] args) {
        binary();
    }
    public static void binary(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter the number : ");
        int x = scanner.nextInt();
        while (x!=0){
            if (x%2==0){
                System.out.println("\n0");
            }else {
                System.out.println("\n1");
            }
            x/=2 ;
        }
    }
}
