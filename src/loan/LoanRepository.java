package loan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanRepository {

    private final String URL  = "jdbc:mysql://localhost:3306/librarydb";
    private final String USER = "root";
    private final String PASS = "nisse";

    // Returnerar tunna Loan-objekt, book/member fylls på i service
    public List<Loan> findActiveByMemberId(int memberId) {
        List<Loan> loans = new ArrayList<>();
        String sql = """
                SELECT id, book_id, member_id, loan_date, due_date, return_date
                FROM loans
                WHERE member_id = ? AND return_date IS NULL
                """;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                loans.add(new Loan(
                        rs.getInt("id"),
                        null, // book fylls på i service
                        null, // member fylls på i service
                        rs.getDate("loan_date").toLocalDate(),
                        rs.getDate("due_date").toLocalDate(),
                        rs.getDate("return_date") != null
                                ? rs.getDate("return_date").toLocalDate()
                                : null
                ));
            }
        } catch (SQLException e) {
            System.err.println("Fel vid hämtning av lån: " + e.getMessage());
        }
        return loans;
    }

    public void save(Loan loan) {
        String sql = """
                INSERT INTO loans (book_id, member_id, loan_date, due_date)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, loan.getBook().getId());
            stmt.setInt(2, loan.getMember().getId());
            stmt.setDate(3, Date.valueOf(loan.getLoanDate()));
            stmt.setDate(4, Date.valueOf(loan.getDueDate()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fel vid sparande av lån: " + e.getMessage());
        }
    }
}
