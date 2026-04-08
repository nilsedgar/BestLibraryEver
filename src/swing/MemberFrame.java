package swing;

import member.*;
import javax.swing.*;
import java.awt.*;

public class MemberFrame extends JFrame {

    private final MemberService memberService = new MemberService();

    public MemberFrame() {
        setTitle("Medlemmar");
        setSize(800, 600);
        setLocationRelativeTo(null);

        JButton profileButton = new JButton("Visa medlemsprofil");
        JButton createButton = new JButton("Skapa ny medlem");

        profileButton.addActionListener(e -> showMemberProfile());
        createButton.addActionListener(e -> showCreateMemberDialog());

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        panel.add(profileButton);
        panel.add(createButton);

        add(panel);
    }

    private void showMemberProfile() {
        String input = JOptionPane.showInputDialog(this, "Ange medlem-id:");
        if (input == null) return;
        try {
            MemberProfileDTO profile = memberService.getMemberProfile(Integer.parseInt(input));
            StringBuilder sb = new StringBuilder();
            sb.append("Namn: ").append(profile.getFullName()).append("\n");
            sb.append("Email: ").append(profile.getEmail()).append("\n");
            sb.append("Medlemstyp: ").append(profile.getMembershipType()).append("\n\n");
            sb.append("Aktiva lån:\n");
            profile.getActiveLoans().forEach(loan ->
                    sb.append("  - ").append(loan.getBookTitle())
                            .append(" (förfaller: ").append(loan.getDueDate())
                            .append(loan.isOverdue() ? " - FÖRSENAD" : "")
                            .append(")\n")
            );
            JOptionPane.showMessageDialog(this, sb.toString(), "Medlemsprofil", JOptionPane.INFORMATION_MESSAGE);
        } catch (MemberNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void showCreateMemberDialog() {
        JTextField firstNameField = new JTextField(20);
        JTextField lastNameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField membershipTypeField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Förnamn:")); panel.add(firstNameField);
        panel.add(new JLabel("Efternamn:")); panel.add(lastNameField);
        panel.add(new JLabel("Email:")); panel.add(emailField);
        panel.add(new JLabel("Medlemstyp (STANDARD/PREMIUM):")); panel.add(membershipTypeField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Skapa medlem", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                memberService.createMember(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        emailField.getText(),
                        membershipTypeField.getText()
                );
                JOptionPane.showMessageDialog(this, "Medlem skapad!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Fel: " + e.getMessage());
            }
        }
    }
}