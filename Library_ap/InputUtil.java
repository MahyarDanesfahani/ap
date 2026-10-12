package project.Library3;

import java.util.Scanner;

public class InputUtil {

    // Single shared Scanner for the whole app : several Scanners on System.in
    // swallow each other's buffered input .
    public static final Scanner SCANNER = new Scanner(System.in);

    public static int readInt(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input . Please enter a number : ");
            }
        }
    }
}
