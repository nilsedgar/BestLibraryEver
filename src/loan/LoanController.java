package loan;

import java.util.List;
import java.util.Scanner;

public class LoanController {

    private final LoanService loanService = new LoanService();
    private final Scanner scanner = new Scanner(System.in);

    public void showLoanMenu() {
        boolean active = true;

        while (active) {
            System.out.println("------ Lånemeny ------");
            System.out.println("1. Låna bok");
            System.out.println("2. Visa alla aktiva lån");
            System.out.println("3. Visa mina aktiva lån");
            System.out.println("0. Tillbaka");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> createLoan();
                case 2 -> showAllActiveLoans();
                case 3 -> showActiveLoansByMember();
                case 0 -> active = false;
                default -> System.out.println("Ogiltigt val");
            }
        }
    }

    private void createLoan() {
        System.out.print("Ange medlem-id: ");
        int memberId = scanner.nextInt();
        System.out.print("Ange bok-id: ");
        int bookId = scanner.nextInt();

        try {
            loanService.createLoan(memberId, bookId);
            System.out.println("Lån skapat!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void showAllActiveLoans() {
        List<ActiveLoanDTO> loans = loanService.getAllActiveLoans();
        for (ActiveLoanDTO loan : loans) {
            System.out.println(loan.getId() + ". " + loan.getBookTitle() +
                    " - " + loan.getMemberName() +
                    " (förfaller: " + loan.getDueDate() +
                    (loan.isOverdue() ? " - FÖRSENAD" : "") + ")");
        }
    }

    private void showActiveLoansByMember() {
        System.out.print("Ange medlem-id: ");
        int memberId = scanner.nextInt();

        try {
            List<LoanSummaryDTO> loans = loanService.getActiveLoansByMember(memberId);
            for (LoanSummaryDTO loan : loans) {
                System.out.println(loan.getId() + ". " + loan.getBookTitle() +
                        " (förfaller: " + loan.getDueDate() +
                        (loan.isOverdue() ? " - FÖRSENAD" : "") + ")");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}