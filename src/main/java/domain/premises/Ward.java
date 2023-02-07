package domain.premises;

import domain.employment.Employee;
import domain.employment.MedicalOfficer;

import java.util.List;

public class Ward {
    private int id;
    private MedicalOfficer leading;
    private List<Room> rooms;
    private List<Employee> worksIn;

    public Ward(int id, MedicalOfficer leading, List<Room> rooms, List<Employee> worksIn) {
        this.id = id;
        this.leading = leading;
        this.rooms = rooms;
        this.worksIn = worksIn;
    }

    public int getId() {
        return id;
    }
}
