package loan;

import java.time.LocalDate;

public class LoanSummaryDTO {
    private int id;
    private String bookTitle;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private boolean isOverdue;

    public LoanSummaryDTO(int id, String bookTitle, LocalDate loanDate,
                          LocalDate dueDate, boolean isOverdue) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.isOverdue = isOverdue;
    }

    public int getId() { return id; }
    public String getBookTitle() { return bookTitle; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getDueDate() { return dueDate; }
    public boolean isOverdue() { return isOverdue; }
}