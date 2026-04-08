package book;

import author.Author;
import category.Category;
import java.util.List;

public class Book {
    private int id;
    private String title;
    private String isbn;
    private int yearPublished;
    private int totalCopies;
    private int availableCopies;
    private String summary;
    private String language;
    private int pageCount;
    private List<Author> authors;
    private List<Category> categories;

    public Book(int id, String title, String isbn, int yearPublished,
                int totalCopies, int availableCopies,
                String summary, String language, int pageCount,
                List<Author> authors, List<Category> categories) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.yearPublished = yearPublished;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
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
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }
    public String getSummary() { return summary; }
    public String getLanguage() { return language; }
    public int getPageCount() { return pageCount; }
    public List<Author> getAuthors() { return authors; }
    public List<Category> getCategories() { return categories; }
}