package book;

import author.AuthorRepository;
import category.CategoryRepository;
import java.util.List;

public class BookService {

    private final BookRepository bookRepository = new BookRepository();
    private final AuthorRepository authorRepository = new AuthorRepository();
    private final CategoryRepository categoryRepository = new CategoryRepository();

    // Hämtar alla böcker och fyller på authors/categories
    public List<BookSummaryDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            book.getAuthors().addAll(authorRepository.findByBookId(book.getId()));
        }
        return books.stream()
                .map(BookMapper::toSummaryDTO)
                .toList();
    }

    public List<BookSummaryDTO> searchByTitle(String title) {
        List<Book> books = bookRepository.findByTitle(title);
        for (Book book : books) {
            book.getAuthors().addAll(authorRepository.findByBookId(book.getId()));
        }
        return books.stream()
                .map(BookMapper::toSummaryDTO)
                .toList();
    }

    public BookDetailDTO getBookById(int id) {
        Book book = bookRepository.findById(id);
        if (book == null) throw new BookNotFoundException(id);

        book.getAuthors().addAll(authorRepository.findByBookId(id));
        book.getCategories().addAll(categoryRepository.findByBookId(id));
        return BookMapper.toDetailDTO(book);
    }

    // Bibliotekarie lägger till en bok
    public void addBook(BookFormDTO dto) {
        Book book = BookMapper.fromFormDTO(dto);
        bookRepository.save(book);
    }
}