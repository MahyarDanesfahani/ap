package excercise.ex4;

public class CashRegister {
    private double[] item;
    private int itemCount;
    private static final int MAX_ITEMS = 100;

    public CashRegister() {
        item = new double[MAX_ITEMS];
        itemCount = 0;
    }

    public void addItem(double price) {
        if (itemCount < MAX_ITEMS) {
            item[itemCount] = price;
            itemCount++;
        } else {
            System.out.println("Cannot add more items . Register is full .");
        }
    }

    public double getTotal() {
        double total = 0;
        for (int i = 0; i < itemCount; i++) {
            total += item[i];
        }
        return total;
    }

    public void printReceipt() {
        String receipt = "Items :\n";
        for (int i = 0; i < itemCount; i++) {
            receipt = receipt.concat(String.valueOf(item[i])).concat("\n");
        }
        receipt = receipt.concat("Total : ").concat(String.valueOf(getTotal()));
        System.out.println(receipt);
    }
}
