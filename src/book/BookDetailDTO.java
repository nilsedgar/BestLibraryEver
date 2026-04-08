package book;

import author.Author;
import category.Category;
import java.util.List;

public class BookDetailDTO {
    private int id;
    private String title;
    private String isbn;
    private int yearPublished;
    private String summary;
    private String language;
    private int pageCount;
    private List<Author> authors;
    private List<Category> categories;

    public BookDetailDTO(int id, String title, String isbn, int yearPublished,
                         String summary, String language, int pageCount,
                         List<Author> authors, List<Category> categories) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.yearPublished = yearPublished;
        this.summary = summary;
        this.language = language;
        this.pageCount = pageCount;
        this.authors = authors;
        this.categories = categories;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getIsbn() { return isbn; }
    public int getYearPublished() { return yearPublished; }
    public String getSummary() { return summary; }
    public String getLanguage() { return language; }
    public int getPageCount() { return pageCount; }
    public List<Author> getAuthors() { return authors; }
    public List<Category> getCategories() { return categories; }
}