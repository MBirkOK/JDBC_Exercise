package domain;

import domain.employment.Nurse;
import domain.premises.Room;

import java.time.LocalDate;
import java.util.List;

public class Patient {
    private int id;

    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private List<Treatment> treatmentList;
    private Room laysIn;
    private Nurse getsTreatedBy;

    private LocalDate startStay;

    private LocalDate endStay;

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
