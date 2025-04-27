package excercise.ex4;

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

