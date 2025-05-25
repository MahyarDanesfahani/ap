package project.bank;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Dollar_Rate {
    private static final String File_Name = "exchange.txt";
    private Scanner scanner = new Scanner(System.in);
    private final Map<LocalDateTime, Integer> rate = new HashMap<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    public Dollar_Rate() {
        rate.put(LocalDateTime.of(2024, 8, 23, 13, 0), 590000);
        rate.put(LocalDateTime.of(2024, 11, 21, 13, 0), 690000);
        rate.put(LocalDateTime.of(2025, 3, 19, 12, 0), 790000);
        rate.put(LocalDateTime.of(2025, 3, 19, 15, 0), 890000);
        rate.put(LocalDateTime.of(2025, 4, 21, 13, 0), 990000);
    }

    public void saveDollarRate() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(File_Name))) {
            for (Map.Entry<LocalDateTime, Integer> entry : rate.entrySet()) {
                writer.println(entry.getKey().format(formatter) + "," + entry.getValue());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exchange(File file) {
        System.out.println("Select an option ؟ (number)" +
                "\n1_Dollar to Rial" +
                "\n2_Rial to Dollar");
        int answer = scanner.nextByte();
        switch (answer) {
            case 1:
                Dollar_Rial();
                break;
            case 2:
                Rial_Dollar();
                break;
            default:
                System.out.println("Not find!!");
                break;
        }
    }

    private LocalDateTime selectDateTime() {
        int index = 1;
        List<LocalDateTime> keys = new ArrayList<>(rate.keySet());
        System.out.println("Select the Date : ");
        for (LocalDateTime date : keys) {
            System.out.println(index++ + " ." + date.format(formatter));
        }
        int choice = scanner.nextByte();
        if (choice < 1 || choice > keys.size()) {
            System.out.println("Invalid date selection .");
            return null;
        }
        return keys.get(choice - 1);
    }

    public void Dollar_Rial() {
        System.out.println("Please enter your amount ? ");
        float price = scanner.nextFloat();
        LocalDateTime select = selectDateTime();
        if (select == null) {
            return;
        }
        int rateRial = rate.get(select);
        float result = price * rateRial;
        System.out.println("Converted to Rial : " + result);
    }

    public  void Rial_Dollar(){
        System.out.println("Please enter your amount ? ");
        float price = scanner.nextFloat();
        LocalDateTime select = selectDateTime();
        if (select == null) {
            return;
        }
        int rateRial = rate.get(select);
        float result = price / rateRial;
        System.out.println("Converted to Dollar : " + result);
    }
}
