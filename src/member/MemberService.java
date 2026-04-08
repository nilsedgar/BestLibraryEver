package member;

import loan.LoanService;
import loan.LoanSummaryDTO;
import java.time.LocalDate;
import java.util.List;

public class MemberService {

    private final MemberRepository memberRepository = new MemberRepository();
    private final LoanService loanService = new LoanService();

    public MemberProfileDTO getMemberProfile(int memberId) {
        Member member = memberRepository.findById(memberId);
        if (member == null) throw new MemberNotFoundException(memberId);

        // Hämtar aktiva lån via LoanService
        List<LoanSummaryDTO> activeLoans = loanService.getActiveLoansByMember(memberId);
        return MemberMapper.toProfileDTO(member, activeLoans);
    }

    // Bibliotekarie skapar nytt medlemskonto
    public void createMember(String firstName, String lastName, String email, String membershipType) {
        Member member = new Member(
                0,
                firstName,
                lastName,
                email,
                LocalDate.now(),
                membershipType,
                "ACTIVE"
        );
        memberRepository.save(member);
    }
}