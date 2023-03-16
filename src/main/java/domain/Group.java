package domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Comparator;

@Entity
@Table(name = "tab_exercise_group")
public class Group implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_generator")
    @SequenceGenerator(name = "group_generator", sequenceName = "tab_exercise_group_id_seq", allocationSize = 1)
    private int id;
    private String name;

    @ManyToOne
    private Participant leader;

    @ManyToOne
    private Expedition expedition;

    public Group(String name, Participant leader, Expedition expedition) {
        this.name = name;
        this.leader = leader;
        this.expedition = expedition;
    }

    public Group() {
        //forJPA
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Participant getLeader() {
        return leader;
    }

    public Expedition getExpedition() {
        return expedition;
    }

}

