package domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tab_exercise_med")
public class Medication {
    @Id
    private int id;

    @Column(name = "description")
    private String description;

    public Medication(int id, String description) {
        this.id = id;
        this.description = description;
    }
    protected Medication(){
        //for JPA
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
