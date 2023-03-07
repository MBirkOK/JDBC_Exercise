package domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "tab_exercise_literature")
public class Literature {
    @Id
    @GeneratedValue
    UUID id;
    String title;
    @Embedded
    Person author;
    @Column(name = "release_year")
    int release;
    String edition;
    String publisher;

    public Literature() {
        //for JPA
    }

    public Literature(UUID id, String title, Person author, int release, String edition, String publisher) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.release = release;
        this.edition = edition;
        this.publisher = publisher;
    }

    public Literature(String title, Person author, int release, String edition, String publisher) {
        this.title = title;
        this.author = author;
        this.release = release;
        this.edition = edition;
        this.publisher = publisher;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Person getAuthor() {
        return author;
    }

    public int getRelease() {
        return release;
    }

    public String getEdition() {
        return edition;
    }

    public String getPublisher() {
        return publisher;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeAuthor(Person author) {
        this.author = author;
    }

    public void changeRelease(int release) {
        this.release = release;
    }

    public void changeEdition(String edition) {
        this.edition = edition;
    }

    public void changePublisher(String publisher) {
        this.publisher = publisher;
    }
}
