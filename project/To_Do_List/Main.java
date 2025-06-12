package project.To_Do_List;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();
        TaskManager taskManager = new TaskManager();

        System.out.println("#####  Welcome TO_DO  ######");
        User currentUser = null;
        while (currentUser == null) {
            System.out.print("Username : ");
            String username = scanner.nextLine();
            System.out.print("Password : ");
            String password = scanner.nextLine();
            currentUser = userManager.login(username, password);
            if (currentUser == null) {
                System.out.println("Username or Password not find!!");
            }
        }

        boolean exit = false;
        while (!exit) {
            System.out.println("*** Menu ***");
            System.out.println("1_Show info User");
            System.out.println("2_Change Password");
            System.out.println("3_Show all tasks");
            System.out.println("4_Show completed tasks");
            System.out.println("5_Add new task");
            System.out.println("6_Edit task");
            System.out.println("7_Exit");
            System.out.print("Enter number : ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("Full Name : " + currentUser.getFullName());
                    System.out.println("Major : " + currentUser.getMajor());
                    System.out.println("City : " + currentUser.getCity());
                    break;
                case 2:
                    System.out.print("New Password : ");
                    String newPass = scanner.nextLine();
                    userManager.changePassword(currentUser, newPass);
                    System.out.println("Done!!! ");
                    break;
                case 3:
                    for (Task t : taskManager.getTasks()) {
                        System.out.println(t.getTaskName() + " Estimated Time: | " + t.getEstimatedTime() + " Done in: | " + t.getActualTime() + " Status: | " + (t.isDone() ? "✔" : "✖"));
                    }
                    break;
                case 4:
                    for (Task t : taskManager.getTasks()) {
                        if (t.isDone()) {
                            System.out.println(t.getTaskName() + " Done in: | " + t.getActualTime() + "Clock . ");
                        }
                    }
                    break;
                case 5:
                    System.out.print("New task name :");
                    String name = scanner.nextLine();
                    System.out.print("Estimated Time (clock) :");
                    int est = Integer.parseInt(scanner.nextLine());
                    Task newTask = new Task(name, est, 0, false);
                    taskManager.addTask(newTask);
                    System.out.println("Done!!!");
                    break;
                case 6:
                    System.out.print("Name task for Edit : ");
                    String editName = scanner.nextLine();
                    boolean found = false;
                    for (Task t : taskManager.getTasks()) {
                        if (t.getTaskName().equalsIgnoreCase(editName)) {
                            System.out.print("Time to do the task (clock) : ");
                            int actual = Integer.parseInt(scanner.nextLine());
                            t.setActualTime(actual);
                            System.out.print("Is the work done ? (true/false): ");
                            boolean done = Boolean.parseBoolean(scanner.nextLine());
                            t.setDone(done);
                            taskManager.saveTasks();
                            System.out.println("Change Done!!!");
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Not find task!!!!!");
                    }
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Not find!!!");
            }
        }
        System.out.println("Exit.....(Good Lock)");
    }
}
