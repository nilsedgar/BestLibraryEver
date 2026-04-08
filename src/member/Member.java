package member;

import java.time.LocalDate;

public class Member {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate membershipDate;
    private String membershipType;
    private String status;

    public Member(int id, String firstName, String lastName, String email,
                  LocalDate membershipDate, String membershipType, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.membershipDate = membershipDate;
        this.membershipType = membershipType;
        this.status = status;
    }

    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public LocalDate getMembershipDate() { return membershipDate; }
    public String getMembershipType() { return membershipType; }
    public String getStatus() { return status; }
    public String getFullName() { return firstName + " " + lastName; }
}
