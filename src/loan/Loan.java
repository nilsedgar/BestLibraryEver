package loan;

import book.Book;
import member.Member;
import java.time.LocalDate;

public class Loan {
    private int id;
    private Book book;
    private Member member;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private int bookId;
    private int memberId;

    public Loan(int id, int bookId, int memberId,
                Book book, Member member,
                LocalDate loanDate, LocalDate dueDate, LocalDate returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.memberId = memberId;
        this.book = book;
        this.member = member;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public int getId() { return id; }
    public Book getBook() { return book; }
    public Member getMember() { return member; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public boolean isReturned() { return returnDate != null; }
    public boolean isOverdue() { return !isReturned() && LocalDate.now().isAfter(dueDate); }
    // Behövs för att hämta IDs innan book/member är påfyllda
    public int getBookId() { return book != null ? book.getId() : bookId; }
    public int getMemberId() { return member != null ? member.getId() : memberId; }

    public void setBook(Book book) { this.book = book; }
    public void setMember(Member member) { this.member = member; }

}
