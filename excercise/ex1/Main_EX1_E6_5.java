package excercise.ex1;

import java.util.Scanner;

public class Main_EX1_E6_5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] array = new int[10];
        System.out.println("please enter the 10 numbers of array : ");
        for (int i = 0; i < array.length; i++) {
            array[i] = scanner.nextInt();
        }
        System.out.println("the smallest of number is : " + DataSet.getSmallest(array));
        System.out.println("the largest of number is : " + DataSet.getLargest(array));
        System.out.println("the average of numbers is : " + DataSet.getAverage(array));
        DataSet.getRange(array);
    }
}
