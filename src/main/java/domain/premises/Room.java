package domain.premises;

import domain.Patient;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "tab_exercise_room")
public class Room {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "ward_id")
    @OneToOne
    private Ward ward;

    @Column(name = "amount_beds")
    private int amound_beds;

    @OneToMany(mappedBy = "laysIn")
    @JoinColumn(referencedColumnName = "id")
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

    protected Room(){
        //for JPA
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
