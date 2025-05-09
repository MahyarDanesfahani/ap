package project.Library2;

class Menu {
    public static void menuWelcome() {
        System.out.println("** Welcome to the Library Management System **\n" +
                "Please select your role:\n" +
                "1_ Student\n" +
                "2_ Curator\n" +
                "3_ Administrator\n" +
                "0_ Exit");
    }

    public static void menuStudent() {
        System.out.println("Student Menu:\n" +
                "1_ Register\n" +
                "2_ View Book List\n" +
                "3_ Search Book\n" +
                "4_ Lend/Return Book");
    }

    public static void lendingReceiving() {
        System.out.println("1_ Lend Book\n2_ Return Book");
    }

    public static void searchBook() {
        System.out.println("Search by:\n1_ Title\n2_ Author\n3_ Year");
    }

    public static void menuCurator() {
        System.out.println("Curator Menu:\n1_ Add Book\n2_ View Borrow Records\n3_ View Book List");
    }

    public static void menuAdministrator() {
        System.out.println("Administrator Menu:\n1_ Librarian Reports\n2_ Popular Books\n3_Show all overdue books");
    }
}