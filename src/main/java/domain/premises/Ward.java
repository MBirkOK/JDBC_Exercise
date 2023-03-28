package domain.premises;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Ward {
    @Id
    private int id;
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
