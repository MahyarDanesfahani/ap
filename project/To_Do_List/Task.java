package project.To_Do_List;

public class Task {
    private String taskName;
    private int estimatedTime;
    private int actualTime;
    private boolean isDone;

    public Task(String taskName, int estimatedTime, int actualTime, boolean isDone) {
        this.taskName = taskName;
        this.estimatedTime = estimatedTime;
        this.actualTime = actualTime;
        this.isDone = isDone;
    }

    public String getTaskName() {
        return taskName;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public int getActualTime() {
        return actualTime;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setActualTime(int actualTime) {
        this.actualTime = actualTime;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
