package member;

import loan.LoanSummaryDTO;
import java.util.List;

public class MemberMapper {

    // Entity -> profilDTO, activeLoans skickas in färdigmappade från service
    public static MemberProfileDTO toProfileDTO(Member member, List<LoanSummaryDTO> activeLoans) {
        return new MemberProfileDTO(
                member.getFullName(),
                member.getEmail(),
                member.getMembershipType(),
                activeLoans
        );
    }
}
