package domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "tab_exercise_expidition")
public class Expedition {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    private Participant leader;

    public Expedition() {
        //for JPA
    }

    public Expedition(LocalDate startDate, LocalDate endDate, Participant leader) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.leader = leader;
    }

    public int getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Participant getLeader() {
        return leader;
    }
}
