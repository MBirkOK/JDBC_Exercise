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

    public Room(int id, int amound_beds, List<Patient> patientsInRoom) {
        this.id = id;
        this.amound_beds = amound_beds;
        this.patientsInRoom = patientsInRoom;
    }

    public Room(int id, int amound_beds) {
        this.id = id;
        this.amound_beds = amound_beds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public int getAmound_beds() {
        return amound_beds;
    }

    public void setAmound_beds(int amound_beds) {
        this.amound_beds = amound_beds;
    }

    public List<Patient> getPatientsInRoom() {
        return patientsInRoom;
    }

    public void setPatientsInRoom(List<Patient> patientsInRoom) {
        this.patientsInRoom = patientsInRoom;
    }


}
