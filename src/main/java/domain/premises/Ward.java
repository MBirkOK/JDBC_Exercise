package domain.premises;

public class Ward {
    private int id;
    private String description;

    public Ward(int id, String description) {
        this.id = id;
        this.description = description;
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
