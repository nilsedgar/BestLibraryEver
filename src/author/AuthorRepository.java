package author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepository {

    private final String URL  = "jdbc:mysql://localhost:3306/librarydb";
    private final String USER = "root";
    private final String PASS = "nisse";

    // Används av BookService för att fylla på authors på en bok
    public List<Author> findByBookId(int bookId) {
        List<Author> authors = new ArrayList<>();
        String sql = """
                SELECT a.id, a.first_name, a.last_name, a.nationality, a.birth_date,
                       ad.biography, ad.website
                FROM authors a
                JOIN book_authors ba ON a.id = ba.author_id
                LEFT JOIN author_descriptions ad ON a.id = ad.author_id
                WHERE ba.book_id = ?
                """;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                authors.add(new Author(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("nationality"),
                        rs.getDate("birth_date").toLocalDate(),
                        rs.getString("biography"),
                        rs.getString("website")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Fel vid hämtning av authors: " + e.getMessage());
        }
        return authors;
    }

    public void save(Author author) {
        String sql = "INSERT INTO authors (first_name, last_name, nationality, birth_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, author.getFirstName());
            stmt.setString(2, author.getLastName());
            stmt.setString(3, author.getNationality());
            stmt.setDate(4, Date.valueOf(author.getBirthDate()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fel vid sparande av author: " + e.getMessage());
        }
    }
}
