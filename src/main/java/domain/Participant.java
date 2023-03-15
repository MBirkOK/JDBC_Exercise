package domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "tab_exercise_participants")
public class Participant {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String mail;

    @ManyToOne
    private Group group;

    public Participant() {
        //for JPA
    }

    public Participant(String firstName, String lastName, String mail, Group group) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.group = group;
    }

    public Participant(String firstName, String lastName, String mail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }

    public Group getGroup() {
        return group;
    }

    public void changeGroup(Group newGroup){
        this.group = newGroup;
    }
}
