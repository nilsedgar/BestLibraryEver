package loan;

public class LoanMapper {

    // Entity -> DTO för låntagarens profilsida
    public static LoanSummaryDTO toSummaryDTO(Loan loan) {
        return new LoanSummaryDTO(
                loan.getId(),
                loan.getBook().getTitle(),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.isOverdue()
        );
    }

    // Entity -> DTO för bibliotekariens vy över alla aktiva lån
    public static ActiveLoanDTO toActiveLoanDTO(Loan loan) {
        return new ActiveLoanDTO(
                loan.getId(),
                loan.getBook().getTitle(),
                loan.getMember().getFullName(),
                loan.getDueDate(),
                loan.isOverdue()
        );
    }
}