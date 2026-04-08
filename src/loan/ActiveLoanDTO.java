package loan;

import java.time.LocalDate;

public class ActiveLoanDTO {
    private int id;
    private String bookTitle;
    private String memberName;
    private LocalDate dueDate;
    private boolean isOverdue;

    public ActiveLoanDTO(int id, String bookTitle, String memberName,
                         LocalDate dueDate, boolean isOverdue) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.memberName = memberName;
        this.dueDate = dueDate;
        this.isOverdue = isOverdue;
    }

    public int getId() { return id; }
    public String getBookTitle() { return bookTitle; }
    public String getMemberName() { return memberName; }
    public LocalDate getDueDate() { return dueDate; }
    public boolean isOverdue() { return isOverdue; }
}
