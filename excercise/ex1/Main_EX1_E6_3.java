package excercise.ex1;

import java.util.Scanner;

public class Main_EX1_E6_3 {
    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        System.out.println("please enter the strings : ");
        String input = scanner.nextLine();
        processString(input);
    }
    public static void processString(String input) {
        StringBuilder uppercaseLetters = new StringBuilder();
        StringBuilder everySecondChar = new StringBuilder();
        StringBuilder replacedVowels = new StringBuilder();
        StringBuilder vowelPositions = new StringBuilder();
        int vowelCount = 0;
        String vowels = "AEIOUaeiou";

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            if (Character.isUpperCase(ch)) {
                uppercaseLetters.append(ch);
            }
            if (i % 2 == 1) {
                everySecondChar.append(ch);
            }
            if (vowels.indexOf(ch) != -1) {
                replacedVowels.append('_');
                vowelCount++;
                vowelPositions.append(i).append(" ");
            } else {
                replacedVowels.append(ch);
            }
        }
        System.out.println("\na.Only the uppercase letters in the string : " + (uppercaseLetters.length() > 0 ? uppercaseLetters.toString() : "nothing"));
        System.out.println("b.Every second letter of the string : " + (everySecondChar.length() > 0 ? everySecondChar.toString() : "nothing"));
        System.out.println("c.the string, with all vowels replaced by an underscore : " + replacedVowels.toString());
        System.out.println("d.the number of vowels in the string : " + vowelCount);
        System.out.println("e.the positions of all vowels in the string : " + (vowelPositions.length() > 0 ? vowelPositions.toString().trim() : "nothing"));
    }
}
