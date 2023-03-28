package domain.premises;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tab_exercise_ward")
public class Ward {
    @Id
    private int id;
    @Column(name = "description")
    private String description;

    public Ward(int id, String description) {
        this.id = id;
        this.description = description;
    }

    protected Ward(){
        //for JPA
    }
    public Ward(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
