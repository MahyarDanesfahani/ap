package excercise.ex4;
/*
public class Light {
    private int switch1; // 0 for down, 1 for up
    private int switch2; // 0 for down, 1 for up
    private int lampState; // 0 for off, 1 for on

    public Light() {
        switch1 = 0;
        switch2 = 0;
        updateLampState();
    }

    public int getFirstSwitchState() {
        return switch1;
    }

    public int getSecondSwitchState() {
        return switch2;
    }

    public int getLampState() {
        return lampState;
    }

    public void toggleFirstSwitch() {
        switch1 = (switch1 == 0) ? 1 : 0;
        updateLampState();
    }

    public void toggleSecondSwitch() {
        switch2 = (switch2 == 0) ? 1 : 0;
        updateLampState();
    }

    private void updateLampState() {
        lampState = (switch1 != switch2) ? 1 : 0;
    }
}
*/
public class Light {
    private int[] switches;
    private int lampState;

    public Light() {
        switches = new int[2];
        switches[0] = 0;
        switches[1] = 0;
        updateLampState();
    }

    public int getSwitchState(int switchNum) {
        if (switchNum == 1 || switchNum == 2) {
            return switches[switchNum - 1];
        } else {
            throw new IllegalArgumentException("Switch number must be 1 or 2 .");
        }
    }

    public int getLampState() {
        return lampState;
    }

    public void toggleSwitch(int switchNum) {
        if (switchNum == 1 || switchNum == 2) {
            int index = switchNum - 1;
            switches[index] = (switches[index] == 0) ? 1 : 0;
            updateLampState();
        } else {
            throw new IllegalArgumentException("Switch number must be 1 or 2 .");
        }
    }

    private void updateLampState() {
        lampState = (switches[0] != switches[1]) ? 1 : 0;
    }
}

