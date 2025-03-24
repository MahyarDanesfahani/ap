package excercise.ex1;

import java.util.Scanner;

public class Main_EX1_E5_15 {
    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        System.out.println("please enter your income : ");
        int income = scanner.nextInt();
        tax(income);
    }
    public static void tax(int income){
        double tax ;
        if ( income <= 50000){
            tax = income / 100;
            System.out.println("the tax of your income is : " + tax);
        } else if ( income > 50000 && income <= 75000) {
            tax = 2 *(income / 100);
            System.out.println("the tax of your income is : " + tax);
        } else if (income >75000 && income <= 100000) {
            tax =  3 * (income / 100);
            System.out.println("the tax of your income is : " + tax);
        } else if ( income > 100000 && income <= 250000) {
            tax = 4 * (income / 100);
            System.out.println("the tax of your income is : " + tax);
        } else if ( income > 250000 && income <= 500000) {
            tax = 5 * (income / 100);
            System.out.println("the tax of your income is : " + tax);
        } else {
            tax = 6 * (income / 100);
            System.out.println("the tax of your income is : " + tax);
        }
    }
}
