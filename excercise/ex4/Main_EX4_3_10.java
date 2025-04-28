package excercise.ex4;

public class Main_EX4_3_10 {
    public static void main(String[] args) {
        CashRegister register = new CashRegister();
        register.addItem(122.99);
        register.addItem(533.49);
        register.addItem(32.50);
        register.printReceipt();
    }
}
