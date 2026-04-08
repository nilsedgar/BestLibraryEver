package book;

import java.util.List;

public class BookSummaryDTO {
    private int id;
    private String title;
    private List<String> authorNames;
    private int availableCopies;

    public BookSummaryDTO(int id, String title, List<String> authorNames, int availableCopies) {
        this.id = id;
        this.title = title;
        this.authorNames = authorNames;
        this.availableCopies = availableCopies;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public List<String> getAuthorNames() { return authorNames; }
    public int getAvailableCopies() { return availableCopies; }
}
