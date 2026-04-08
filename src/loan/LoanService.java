package loan;

import book.Book;
import book.BookNotAvailableException;
import book.BookNotFoundException;
import book.BookRepository;
import member.Member;
import member.MemberNotFoundException;
import member.MemberRepository;
import member.MemberSuspendedException;
import java.time.LocalDate;
import java.util.List;

public class LoanService {

    private final LoanRepository loanRepository = new LoanRepository();
    private final BookRepository bookRepository = new BookRepository();
    private final MemberRepository memberRepository = new MemberRepository();

    // Skapar ett nytt lån med affärslogik
    public void createLoan(int memberId, int bookId) {
        Member member = memberRepository.findById(memberId);
        if (member == null) throw new MemberNotFoundException(memberId);
        if (member.getStatus().equals("SUSPENDED")) throw new MemberSuspendedException(memberId);

        Book book = bookRepository.findById(bookId);
        if (book == null) throw new BookNotFoundException(bookId);
        if (book.getAvailableCopies() == 0) throw new BookNotAvailableException(bookId);

        Loan loan = new Loan(
                0,
                book.getId(),
                member.getId(),
                book,
                member,
                LocalDate.now(),
                LocalDate.now().plusWeeks(2),
                null
        );

        loanRepository.save(loan);
        bookRepository.updateAvailableCopies(bookId, -1);
    }

    // Hämtar aktiva lån för en specifik medlem
    public List<LoanSummaryDTO> getActiveLoansByMember(int memberId) {
        Member member = memberRepository.findById(memberId);
        if (member == null) throw new MemberNotFoundException(memberId);

        List<Loan> loans = loanRepository.findActiveByMemberId(memberId);
        for (Loan loan : loans) {
            loan.setBook(bookRepository.findById(loan.getBookId()));
            loan.setMember(member);
        }
        return loans.stream()
                .map(LoanMapper::toSummaryDTO)
                .toList();
    }

    // Bibliotekariens vy över alla aktiva lån
    public List<ActiveLoanDTO> getAllActiveLoans() {
        List<Loan> loans = loanRepository.findAllActive();
        for (Loan loan : loans) {
            loan.setBook(bookRepository.findById(loan.getBookId()));
            loan.setMember(memberRepository.findById(loan.getMemberId()));
        }
        return loans.stream()
                .map(LoanMapper::toActiveLoanDTO)
                .toList();
    }
}