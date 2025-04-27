package excercise.ex4;

public class E3_4 {
    public static void main(String[] args) {
        Light light = new Light();

        System.out.println("Initial Lamp State : " + light.getLampState());

        light.toggleFirstSwitch();
        System.out.println("After toggling first switch , Lamp State : " + light.getLampState());

        light.toggleSecondSwitch();
        System.out.println("After toggling second switch , Lamp State : " + light.getLampState());
    }

}
