package excercise.ex1;

import java.util.Scanner;

public class Main_EX1_E6_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        sum100();
        System.out.println("please enter the int number : ");
        int number = scanner.nextInt();
        sumnumberodd(number);
    }
    public static void sum100(){
        int sum = 0;
        for(int i=2 ; i <= 100 ; i++){
            if ( i % 2 == 0){
                sum += i ;
            }
        }
        System.out.println("the sum of even number between 2 and 100 : " + sum);
    }
    public static int sumnumberodd(int number){
        int s = 0 ;
        while (number != 0) {
            int digit = number % 10;
            if (digit % 2 != 0) {
                s += digit;
            }
            number /= 10;
        }
        System.out.println("the sum of odd number is : " + s);
        return  s ;
    }
}
