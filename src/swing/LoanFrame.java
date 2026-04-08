package swing;

import loan.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LoanFrame extends JFrame {

    private final LoanService loanService = new LoanService();
    private final DefaultTableModel tableModel;

    public LoanFrame() {
        setTitle("Lån");
        setSize(800, 600);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel(new String[]{"ID", "Bok", "Medlem", "Förfaller", "Status"}, 0);
        JTable table = new JTable(tableModel);

        JButton showAllButton = new JButton("Visa alla aktiva lån");
        JButton createButton = new JButton("Skapa lån");

        showAllButton.addActionListener(e -> loadAllActiveLoans());
        createButton.addActionListener(e -> showCreateLoanDialog());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(showAllButton);
        buttonPanel.add(createButton);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadAllActiveLoans();
    }

    private void loadAllActiveLoans() {
        tableModel.setRowCount(0);
        List<ActiveLoanDTO> loans = loanService.getAllActiveLoans();
        for (ActiveLoanDTO loan : loans) {
            tableModel.addRow(new Object[]{
                    loan.getId(),
                    loan.getBookTitle(),
                    loan.getMemberName(),
                    loan.getDueDate(),
                    loan.isOverdue() ? "FÖRSENAD" : "Aktiv"
            });
        }
    }

    private void showCreateLoanDialog() {
        JTextField memberIdField = new JTextField(10);
        JTextField bookIdField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Medlem-id:")); panel.add(memberIdField);
        panel.add(new JLabel("Bok-id:")); panel.add(bookIdField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Skapa lån", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                loanService.createLoan(
                        Integer.parseInt(memberIdField.getText()),
                        Integer.parseInt(bookIdField.getText())
                );
                loadAllActiveLoans();
                JOptionPane.showMessageDialog(this, "Lån skapat!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Fel: " + e.getMessage());
            }
        }
    }
}