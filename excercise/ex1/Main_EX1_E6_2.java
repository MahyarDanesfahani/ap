package excercise.ex1;

import java.util.Scanner;

public class Main_EX1_E6_2 {
    public static void main(String[] args) {
        processNumbers();
    }
    public static void processNumbers() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter the numbers (the end please enter the 0) : ");
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        int evenCount = 0, oddCount = 0;
        int cumulativeSum = 0;
        boolean firstInput = true;
        int previousNumber = Integer.MIN_VALUE;
        boolean duplicatePrinted = false;
        StringBuilder cumulativeOutput = new StringBuilder();
        StringBuilder duplicatesOutput = new StringBuilder();
        while (true) {
            int num = scanner.nextInt();
            if (num == 0) break;

            if (num < min) min = num;
            if (num > max) max = num;

            if (num % 2 == 0) {
                evenCount++;
            } else {
                oddCount++;
            }

            cumulativeSum += num;
            cumulativeOutput.append(cumulativeSum).append(" ");

            if (!firstInput && num == previousNumber) {
                if (!duplicatePrinted) {
                    duplicatesOutput.append(num).append(" ");
                    duplicatePrinted = true;
                }
            } else {
                duplicatePrinted = false;
            }

            previousNumber = num;
            firstInput = false;
        }

        System.out.println("\nthe min of number : " + min);
        System.out.println("the max of number : " + max );
        System.out.println("the number of even : " + evenCount);
        System.out.println("the number of odd : " + oddCount);
        System.out.println("the Cumulative totals : "  + cumulativeOutput.toString().trim());
        System.out.println("the All adjacent duplicates : " + (duplicatesOutput.length() > 0 ? duplicatesOutput.toString().trim() : "nothing"));

        scanner.close();
    }
}
