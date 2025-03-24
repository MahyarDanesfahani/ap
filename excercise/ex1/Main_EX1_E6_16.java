package excercise.ex1;

public class Main_EX1_E6_16 {
    public static void main(String[] args) {
        Table();
    }
    public static void Table(){
        for (int i = 1; i <= 10; i++) {
            for (int j=1 ; j <=10 ; j++){
                System.out.print(i*j + "\t");
            }
            System.out.print("\n");
        }
    }
}
