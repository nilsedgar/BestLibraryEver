package book;

import java.util.List;
import java.util.Scanner;

public class BookController {

    private final BookService bookService = new BookService();
    private final Scanner scanner = new Scanner(System.in);

    public void showBookMenu() {
        boolean active = true;

        while (active) {
            System.out.println("------ Bokmeny ------");
            System.out.println("1. Visa alla böcker");
            System.out.println("2. Sök efter bok");
            System.out.println("3. Visa bokdetaljer");
            System.out.println("4. Lägg till bok");
            System.out.println("0. Tillbaka");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> showAllBooks();
                case 2 -> searchBooks();
                case 3 -> showBookDetails();
                case 4 -> addBook();
                case 0 -> active = false;
                default -> System.out.println("Ogiltigt val");
            }
        }
    }

    private void showAllBooks() {
        List<BookSummaryDTO> books = bookService.getAllBooks();
        for (BookSummaryDTO book : books) {
            System.out.println(book.getTitle() + " - " + book.getAuthorNames() +
                    " (" + book.getAvailableCopies() + " tillgängliga)");
        }
    }

    private void searchBooks() {
        System.out.print("Sökterm: ");
        scanner.nextLine();
        String query = scanner.nextLine();
        List<BookSummaryDTO> books = bookService.searchByTitle(query);
        for (BookSummaryDTO book : books) {
            System.out.println(book.getId() + ". " + book.getTitle() + " - " + book.getAuthorNames());
        }
    }

    private void showBookDetails() {
        System.out.print("Ange bok-id: ");
        int id = scanner.nextInt();
        try {
            BookDetailDTO book = bookService.getBookById(id);
            System.out.println("Titel: " + book.getTitle());
            System.out.println("ISBN: " + book.getIsbn());
            System.out.println("År: " + book.getYearPublished());
            System.out.println("Språk: " + book.getLanguage());
            System.out.println("Sidor: " + book.getPageCount());
            System.out.println("Sammanfattning: " + book.getSummary());
            System.out.println("Författare: " + book.getAuthors());
            System.out.println("Kategorier: " + book.getCategories());
        } catch (BookNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addBook() {
        scanner.nextLine();
        System.out.print("Titel: ");
        String title = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Utgivningsår: ");
        int year = scanner.nextInt();
        System.out.print("Antal exemplar: ");
        int copies = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Sammanfattning: ");
        String summary = scanner.nextLine();
        System.out.print("Språk: ");
        String language = scanner.nextLine();
        System.out.print("Antal sidor: ");
        int pages = scanner.nextInt();

        bookService.addBook(new BookFormDTO(title, isbn, year, copies, summary, language, pages));
        System.out.println("Bok tillagd!");
    }
}