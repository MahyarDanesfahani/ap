package excercise.ex4;

public class CircuitTester_E3_5 {
    public static void main(String[] args) {
        Light light = new Light();

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

            setSwitchState(light, switch1, switch2);

            int actualLamp = light.getLampState();

            int expectedLamp = (switch1 != switch2) ? 1 : 0;

            System.out.printf("%-10d %-10d %-15d %-15d%n", switch1, switch2, actualLamp, expectedLamp);
        }
    }

    private static void setSwitchState(Light light, int desiredSwitch1, int desiredSwitch2) {
        resetSwitches(light);
        if (desiredSwitch1 == 1) {
            light.toggleFirstSwitch();
        }
        if (desiredSwitch2 == 1) {
            light.toggleSecondSwitch();
        }
    }

    private static void resetSwitches(Light light) {
        if (light.getFirstSwitchState() == 1) {
            light.toggleFirstSwitch();
        }
        if (light.getSecondSwitchState() == 1) {
            light.toggleSecondSwitch();
        }
    }
}
