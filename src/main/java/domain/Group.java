package domain;

import jakarta.persistence.Entity;

@Entity
@Table(name = "tab_exercise_group")
public class Group {

    private int id;
    private String name;
    private Participant leader;
    private Expedition expedition;

    public Group(String name, Participant leader, Expedition expedition) {
        this.name = name;
        this.leader = leader;
        this.expedition = expedition;
    }

    public Group() {
        //forJPA
    }
}
