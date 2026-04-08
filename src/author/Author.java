package author;

import java.time.LocalDate;

public class Author {
    private int id;
    private String firstName;
    private String lastName;
    private String nationality;
    private LocalDate birthDate;
    private String biography;
    private String website;

    public Author(int id, String firstName, String lastName,
                  String nationality, LocalDate birthDate,
                  String biography, String website) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.birthDate = birthDate;
        this.biography = biography;
        this.website = website;
    }

    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getNationality() { return nationality; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getBiography() { return biography; }
    public String getWebsite() { return website; }
    public String getFullName() { return firstName + " " + lastName; }
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
