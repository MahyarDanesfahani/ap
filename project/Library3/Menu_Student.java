package project.Library3;

public class Menu_Student {

    public static void Welcome_student() {
        System.out.println("**** Welcome Zanjan_Library ****" +
                "\n1_Sign up" +
                "\n2_Login" +
                "\n3_Search Book" +
                "\n4_Borrow Book (by ID)");
    }

    public static void Student_Login_Menu(String studentName) {
        System.out.println("\n=== Student Menu (" + studentName + ") ===" +
                "\n1_Search Book" +
                "\n2_Borrow Book" +
                "\n3_View My Borrow History" +
                "\n0_Logout");
    }

    public static void Search_Book_Student() {
        System.out.println("** Search Book **" +
                "\n1_By Book Name" +
                "\n2_By Author Name" +
                "\n3_By Year of Publication");
    }
}

