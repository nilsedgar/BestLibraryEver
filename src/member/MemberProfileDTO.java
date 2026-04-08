package member;

import loan.LoanSummaryDTO;
import java.util.List;

public class MemberProfileDTO {
    private String fullName;
    private String email;
    private String membershipType;
    private List<LoanSummaryDTO> activeLoans;

    public MemberProfileDTO(String fullName, String email,
                            String membershipType, List<LoanSummaryDTO> activeLoans) {
        this.fullName = fullName;
        this.email = email;
        this.membershipType = membershipType;
        this.activeLoans = activeLoans;
    }

    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getMembershipType() { return membershipType; }
    public List<LoanSummaryDTO> getActiveLoans() { return activeLoans; }
}