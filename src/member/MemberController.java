package member;

import java.util.Scanner;

public class MemberController {

    private final MemberService memberService = new MemberService();
    private final Scanner scanner = new Scanner(System.in);

    public void showMemberMenu() {
        boolean active = true;

        while (active) {
            System.out.println("------ Medlemsmeny ------");
            System.out.println("1. Visa medlemsprofil");
            System.out.println("2. Skapa ny medlem");
            System.out.println("0. Tillbaka");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> showMemberProfile();
                case 2 -> createMember();
                case 0 -> active = false;
                default -> System.out.println("Ogiltigt val");
            }
        }
    }

    private void showMemberProfile() {
        System.out.print("Ange medlem-id: ");
        int id = scanner.nextInt();

        try {
            MemberProfileDTO profile = memberService.getMemberProfile(id);
            System.out.println("Namn: " + profile.getFullName());
            System.out.println("Email: " + profile.getEmail());
            System.out.println("Medlemstyp: " + profile.getMembershipType());
            System.out.println("Aktiva lån:");
            profile.getActiveLoans().forEach(loan ->
                    System.out.println("  - " + loan.getBookTitle() +
                            " (förfaller: " + loan.getDueDate() +
                            (loan.isOverdue() ? " - FÖRSENAD" : "") + ")")
            );
        } catch (MemberNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createMember() {
        scanner.nextLine();
        System.out.print("Förnamn: ");
        String firstName = scanner.nextLine();
        System.out.print("Efternamn: ");
        String lastName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Medlemstyp (STANDARD/PREMIUM): ");
        String membershipType = scanner.nextLine();

        memberService.createMember(firstName, lastName, email, membershipType);
        System.out.println("Medlem skapad!");
    }
}