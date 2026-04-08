package swing;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Best Library Ever");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JButton bookButton = new JButton("Böcker");
        JButton loanButton = new JButton("Lån");
        JButton memberButton = new JButton("Medlemmar");

        bookButton.addActionListener(e -> new BookFrame().setVisible(true));
        loanButton.addActionListener(e -> new LoanFrame().setVisible(true));
        memberButton.addActionListener(e -> new MemberFrame().setVisible(true));

        panel.add(bookButton);
        panel.add(loanButton);
        panel.add(memberButton);

        add(panel);
    }
}