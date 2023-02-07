package domain.premises;

import domain.Patient;

import java.util.List;

public class Room {
    private int id;
    private Ward ward;
    private int amound_beds;
    private List<Patient> patientsInRoom;

    public Room(int id, Ward ward, int amound_beds, List<Patient> patientsInRoom) {
        this.id = id;
        this.ward = ward;
        this.amound_beds = amound_beds;
        this.patientsInRoom = patientsInRoom;
    }
}
