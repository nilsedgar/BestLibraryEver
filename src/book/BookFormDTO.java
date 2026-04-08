package book;

public class BookFormDTO {
    private String title;
    private String isbn;
    private int yearPublished;
    private int totalCopies;
    private String summary;
    private String language;
    private int pageCount;

    public BookFormDTO(String title, String isbn, int yearPublished,
                       int totalCopies, String summary, String language, int pageCount) {
        this.title = title;
        this.isbn = isbn;
        this.yearPublished = yearPublished;
        this.totalCopies = totalCopies;
        this.summary = summary;
        this.language = language;
        this.pageCount = pageCount;
    }

    public String getTitle() { return title; }
    public String getIsbn() { return isbn; }
    public int getYearPublished() { return yearPublished; }
    public int getTotalCopies() { return totalCopies; }
    public String getSummary() { return summary; }
    public String getLanguage() { return language; }
    public int getPageCount() { return pageCount; }
}