package swing;

import book.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookFrame extends JFrame {

    private final BookService bookService = new BookService();
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField searchField;

    public BookFrame() {
        setTitle("Böcker");
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Tabell
        tableModel = new DefaultTableModel(new String[]{"ID", "Titel", "Författare", "Tillgängliga"}, 0);
        table = new JTable(tableModel);

        // Sökfält
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Sök");
        JButton showAllButton = new JButton("Visa alla");
        JButton detailButton = new JButton("Visa detaljer");
        JButton addButton = new JButton("Lägg till bok");

        searchButton.addActionListener(e -> searchBooks());
        showAllButton.addActionListener(e -> loadAllBooks());
        detailButton.addActionListener(e -> showBookDetails());
        addButton.addActionListener(e -> showAddBookDialog());

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Sök:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(detailButton);
        buttonPanel.add(addButton);

        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadAllBooks();
    }

    private void loadAllBooks() {
        tableModel.setRowCount(0);
        List<BookSummaryDTO> books = bookService.getAllBooks();
        for (BookSummaryDTO book : books) {
            tableModel.addRow(new Object[]{
                    book.getId(),
                    book.getTitle(),
                    book.getAuthorNames(),
                    book.getAvailableCopies()
            });
        }
    }

    private void searchBooks() {
        tableModel.setRowCount(0);
        List<BookSummaryDTO> books = bookService.searchByTitle(searchField.getText());
        for (BookSummaryDTO book : books) {
            tableModel.addRow(new Object[]{
                    book.getId(),
                    book.getTitle(),
                    book.getAuthorNames(),
                    book.getAvailableCopies()
            });
        }
    }

    private void showBookDetails() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Välj en bok först");
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        try {
            BookDetailDTO book = bookService.getBookById(id);
            String details = """
                    Titel: %s
                    ISBN: %s
                    År: %d
                    Språk: %s
                    Sidor: %d
                    Sammanfattning: %s
                    Författare: %s
                    Kategorier: %s
                    """.formatted(
                    book.getTitle(), book.getIsbn(), book.getYearPublished(),
                    book.getLanguage(), book.getPageCount(), book.getSummary(),
                    book.getAuthors(), book.getCategories()
            );
            JOptionPane.showMessageDialog(this, details, "Bokdetaljer", JOptionPane.INFORMATION_MESSAGE);
        } catch (BookNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void showAddBookDialog() {
        JTextField titleField = new JTextField(20);
        JTextField isbnField = new JTextField(20);
        JTextField yearField = new JTextField(20);
        JTextField copiesField = new JTextField(20);
        JTextField summaryField = new JTextField(20);
        JTextField languageField = new JTextField(20);
        JTextField pagesField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));
        panel.add(new JLabel("Titel:")); panel.add(titleField);
        panel.add(new JLabel("ISBN:")); panel.add(isbnField);
        panel.add(new JLabel("År:")); panel.add(yearField);
        panel.add(new JLabel("Exemplar:")); panel.add(copiesField);
        panel.add(new JLabel("Sammanfattning:")); panel.add(summaryField);
        panel.add(new JLabel("Språk:")); panel.add(languageField);
        panel.add(new JLabel("Sidor:")); panel.add(pagesField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Lägg till bok", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                bookService.addBook(new BookFormDTO(
                        titleField.getText(),
                        isbnField.getText(),
                        Integer.parseInt(yearField.getText()),
                        Integer.parseInt(copiesField.getText()),
                        summaryField.getText(),
                        languageField.getText(),
                        Integer.parseInt(pagesField.getText())
                ));
                loadAllBooks();
                JOptionPane.showMessageDialog(this, "Bok tillagd!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Fel: " + e.getMessage());
            }
        }
    }
}