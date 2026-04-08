package member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberRepository {

    private final String URL  = "jdbc:mysql://localhost:3306/librarydb";
    private final String USER = "root";
    private final String PASS = "nisse";

    public Member findById(int id) {
        String sql = "SELECT * FROM members WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Member(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getDate("membership_date").toLocalDate(),
                        rs.getString("membership_type"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            System.err.println("Fel vid hämtning av member: " + e.getMessage());
        }
        return null; // medlem hittades inte
    }

    public void save(Member member) {
        String sql = """
                INSERT INTO members (first_name, last_name, email, membership_date, membership_type, status)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, member.getFirstName());
            stmt.setString(2, member.getLastName());
            stmt.setString(3, member.getEmail());
            stmt.setDate(4, Date.valueOf(member.getMembershipDate()));
            stmt.setString(5, member.getMembershipType());
            stmt.setString(6, member.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fel vid sparande av member: " + e.getMessage());
        }
    }
}
