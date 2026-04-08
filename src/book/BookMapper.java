package book;

import author.Author;

import java.util.List;

public class BookMapper {

    // Entity -> visningsDTO för boklistning
    public static BookSummaryDTO toSummaryDTO(Book book) {
        List<String> authorNames = book.getAuthors().stream()
                .map(Author::getFullName)
                .toList();

        return new BookSummaryDTO(
                book.getId(),
                book.getTitle(),
                authorNames,
                book.getAvailableCopies()
        );
    }

    // Entity -> detaljDTO för enskild bok
    public static BookDetailDTO toDetailDTO(Book book) {
        return new BookDetailDTO(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getYearPublished(),
                book.getSummary(),
                book.getLanguage(),
                book.getPageCount(),
                book.getAuthors(),
                book.getCategories()
        );
    }

    // FormDTO -> entity, används i service innan save
    public static Book fromFormDTO(BookFormDTO dto) {
        return new Book(
                0, // id sätts av databasen
                dto.getTitle(),
                dto.getIsbn(),
                dto.getYearPublished(),
                dto.getTotalCopies(),
                dto.getTotalCopies(), // available = total vid skapande
                dto.getSummary(),
                dto.getLanguage(),
                dto.getPageCount(),
                List.of(),
                List.of()
        );
    }
}