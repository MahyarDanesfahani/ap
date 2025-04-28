package excercise.ex4;

public class Main_EX4_3_4 {
    public static void main(String[] args) {
        Light_EX4_3_6 lightEX436 = new Light_EX4_3_6();

        System.out.println("Initial Lamp State : " + lightEX436.getLampState());

        lightEX436.toggleFirstSwitch();
        System.out.println("After toggling first switch , Lamp State : " + lightEX436.getLampState());

        lightEX436.toggleSecondSwitch();
        System.out.println("After toggling second switch , Lamp State : " + lightEX436.getLampState());
    }

}
