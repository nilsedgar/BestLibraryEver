package book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private final String URL  = "jdbc:mysql://localhost:3306/librarydb";
    private final String USER = "root";
    private final String PASS = "nisse";

    // Hämtar alla böcker utan authors/categories - fylls på i service
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = """
                SELECT b.id, b.title, b.isbn, b.year_published,
                       b.total_copies, b.available_copies,
                       bd.summary, bd.language, bd.page_count
                FROM books b
                LEFT JOIN book_descriptions bd ON b.id = bd.book_id
                """;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("isbn"),
                        rs.getInt("year_published"),
                        rs.getInt("total_copies"),
                        rs.getInt("available_copies"),
                        rs.getString("summary"),
                        rs.getString("language"),
                        rs.getInt("page_count"),
                        new ArrayList<>(),
                        new ArrayList<>()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Fel vid hämtning av böcker: " + e.getMessage());
        }
        return books;
    }

    public List<Book> findByTitle(String title) {
        List<Book> books = new ArrayList<>();
        String sql = """
                SELECT b.id, b.title, b.isbn, b.year_published,
                       b.total_copies, b.available_copies,
                       bd.summary, bd.language, bd.page_count
                FROM books b
                LEFT JOIN book_descriptions bd ON b.id = bd.book_id
                WHERE b.title LIKE ?
                """;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + title + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("isbn"),
                        rs.getInt("year_published"),
                        rs.getInt("total_copies"),
                        rs.getInt("available_copies"),
                        rs.getString("summary"),
                        rs.getString("language"),
                        rs.getInt("page_count"),
                        new ArrayList<>(),
                        new ArrayList<>()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Fel vid sökning av böcker: " + e.getMessage());
        }
        return books;
    }

    // Används av service för att uppdatera available_copies vid lån/återlämning
    public void updateAvailableCopies(int bookId, int delta) {
        String sql = "UPDATE books SET available_copies = available_copies + ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, delta);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fel vid uppdatering av available_copies: " + e.getMessage());
        }
    }

    public Book findById(int id) {
        String sql = """
            SELECT b.id, b.title, b.isbn, b.year_published,
                   b.total_copies, b.available_copies,
                   bd.summary, bd.language, bd.page_count
            FROM books b
            LEFT JOIN book_descriptions bd ON b.id = bd.book_id
            WHERE b.id = ?
            """;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("isbn"),
                        rs.getInt("year_published"),
                        rs.getInt("total_copies"),
                        rs.getInt("available_copies"),
                        rs.getString("summary"),
                        rs.getString("language"),
                        rs.getInt("page_count"),
                        new ArrayList<>(),
                        new ArrayList<>()
                );
            }
        } catch (SQLException e) {
            System.err.println("Fel vid hämtning av bok: " + e.getMessage());
        }
        return null;
    }

    public void save(Book book) {
        String sql = """
            INSERT INTO books (title, isbn, year_published, total_copies, available_copies)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getIsbn());
            stmt.setInt(3, book.getYearPublished());
            stmt.setInt(4, book.getTotalCopies());
            stmt.setInt(5, book.getAvailableCopies());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fel vid sparande av bok: " + e.getMessage());
        }
    }
}