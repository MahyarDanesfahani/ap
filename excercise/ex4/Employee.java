package excercise.ex4;

public class Employee {
    private String employeeName;
    private double currentSalary;

    public Employee(String employeeName , double currentSalary){
        this.employeeName = employeeName ;
        this.currentSalary = currentSalary;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public double getCurrentSalary() {
        return currentSalary;
    }

    public void raiseSalary(double byPercent){
        currentSalary += (currentSalary*byPercent/100);
        System.out.println(getEmployeeName() + " : " +getCurrentSalary());
    }
}
