package domain;

import jakarta.persistence.Column;
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
    String author;
    @Column(name = "release_year")
    int release;
    String edition;
    String publisher;

    public Literature() {
        //for JPA
    }

    public Literature(UUID id, String title, String author, int release, String edition, String publisher) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.release = release;
        this.edition = edition;
        this.publisher = publisher;
    }

    public Literature(String title, String author, int release, String edition, String publisher) {
        this.title = title;
        this.author = author;
        this.release = release;
        this.edition = edition;
        this.publisher = publisher;
    }
}
