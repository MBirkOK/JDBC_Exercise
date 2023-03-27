package domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "tab_exercise_participant")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participant_generator")
    @SequenceGenerator(name = "participant_generator", sequenceName = "tab_exercise_participant_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String mail;

    @ManyToOne
    @JoinColumn(name = "group_id")
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

    public int getId() {
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
