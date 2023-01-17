package domain;

import infrastructure.DatabaseHandler;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Inventory {
    private int id;
    private String description;
    private LocalDate procurement;
    private double worth;
    private Employee employee;

    public Inventory(int id, String description, LocalDate procurement, double worth, Employee employee) {
        this.id = id;
        this.description = description;
        this.procurement = procurement;
        this.worth = worth;
        this.employee = employee;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getProcurement() {
        return procurement;
    }

    public double getWorth() {
        return worth;
    }

    public Employee getEmployee() {
        return employee;
    }

    public static List<Inventory> findInventoryByName(String name) throws SQLException, ClassNotFoundException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        return databaseHandler.findInventoryByName(name);
    }
}
