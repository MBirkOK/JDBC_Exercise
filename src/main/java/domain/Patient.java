package domain;

import domain.employment.Nurse;
import domain.premises.Room;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tab_exercise_patient")
public class Patient {

    @Id
    private int id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birthday")
    private LocalDate birthdate;
    @OneToMany(mappedBy = "patient_id")
    private List<Treatment> treatmentList;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room laysIn;

    @ManyToOne
    @JoinColumn(name = "nurse_id")
    private Nurse getsTreatedBy;

    @Column(name = "stay_start")
    private LocalDate startStay;

    @Column(name = "stay_end")
    private LocalDate endStay;

    @Column(name = "gender")
    private String gender;

    public Patient(int id, String firstName, String lastName, LocalDate birthdate, List<Treatment> treatmentList, Room laysIn, Nurse getsTreatedBy, LocalDate startStay, LocalDate endStay, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.treatmentList = treatmentList;
        this.laysIn = laysIn;
        this.getsTreatedBy = getsTreatedBy;
        this.startStay = startStay;
        this.endStay = endStay;
        this.gender = gender;
    }

    protected Patient(){
        //for JPA
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public List<Treatment> getTreatmentList() {
        return treatmentList;
    }

    public Room getRoom() {
        return laysIn;
    }

    public Nurse getNurse() {
        return getsTreatedBy;
    }

    public LocalDate getStartStay() {
        return startStay;
    }

    public LocalDate getEndStay() {
        return endStay;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setTreatmentList(List<Treatment> treatmentList) {
        this.treatmentList = treatmentList;
    }

    public void setRoom(Room laysIn) {
        this.laysIn = laysIn;
    }

    public void setNurse(Nurse getsTreatedBy) {
        this.getsTreatedBy = getsTreatedBy;
    }

    public void setStartStay(LocalDate startStay) {
        this.startStay = startStay;
    }

    public void setEndStay(LocalDate endStay) {
        this.endStay = endStay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
