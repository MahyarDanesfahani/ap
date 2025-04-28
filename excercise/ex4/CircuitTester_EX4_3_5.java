package excercise.ex4;

public class CircuitTester_EX4_3_5 {
    public static void main(String[] args) {
        Light_EX4_3_6 lightEX436 = new Light_EX4_3_6();

        int[][] switchCombinations = {
                {0, 0},
                {0, 1},
                {1, 0},
                {1, 1}
        };

        System.out.printf("%-10s %-10s %-15s %-15s%n", "Switch1", "Switch2", "Actual Lamp", "Expected Lamp");
        System.out.println("----------------------------------------------------------");

        for (int[] combination : switchCombinations) {
            int switch1 = combination[0];
            int switch2 = combination[1];

            setSwitchState(lightEX436, switch1, switch2);

            int actualLamp = lightEX436.getLampState();

            int expectedLamp = (switch1 != switch2) ? 1 : 0;

            System.out.printf("%-10d %-10d %-15d %-15d%n", switch1, switch2, actualLamp, expectedLamp);
        }
    }

    private static void setSwitchState(Light_EX4_3_6 lightEX436, int desiredSwitch1, int desiredSwitch2) {
        resetSwitches(lightEX436);
        if (desiredSwitch1 == 1) {
            lightEX436.toggleFirstSwitch();
        }
        if (desiredSwitch2 == 1) {
            lightEX436.toggleSecondSwitch();
        }
    }

    private static void resetSwitches(Light_EX4_3_6 lightEX436) {
        if (lightEX436.getFirstSwitchState() == 1) {
            lightEX436.toggleFirstSwitch();
        }
        if (lightEX436.getSecondSwitchState() == 1) {
            lightEX436.toggleSecondSwitch();
        }
    }
}
