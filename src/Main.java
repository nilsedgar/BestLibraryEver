import book.BookController;
import member.MemberController;
import loan.LoanController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BookController bookController = new BookController();
        LoanController loanController = new LoanController();
        MemberController memberController = new MemberController();

        boolean active = true;
        Scanner scanner = new Scanner(System.in);

        while (active) {
            System.out.println("------ Huvudmeny ------");
            System.out.println("1. Böcker");
            System.out.println("2. Lån");
            System.out.println("3. Medlemmar");
            System.out.println("0. Avsluta");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> bookController.showBookMenu();
                case 2 -> loanController.showLoanMenu();
                case 3 -> memberController.showMemberMenu();
                case 0 -> active = false;
                default -> System.out.println("Ogiltigt val");
            }
        }
    }
}