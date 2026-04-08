package category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    private final String URL  = "jdbc:mysql://localhost:3306/librarydb";
    private final String USER = "root";
    private final String PASS = "nisse";

    // Används av BookService för att fylla på categories på en bok
    public List<Category> findByBookId(int bookId) {
        List<Category> categories = new ArrayList<>();
        String sql = """
                SELECT c.id, c.name, c.description
                FROM categories c
                JOIN book_categories bc ON c.id = bc.category_id
                WHERE bc.book_id = ?
                """;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                categories.add(new Category(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Fel vid hämtning av kategorier: " + e.getMessage());
        }
        return categories;
    }

    public void save(Category category) {
        String sql = "INSERT INTO categories (name, description) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fel vid sparande av kategori: " + e.getMessage());
        }
    }
}
