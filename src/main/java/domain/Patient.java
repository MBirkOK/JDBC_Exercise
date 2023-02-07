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

    public Patient(int id, String firstName, String lastName, LocalDate birthdate, List<Treatment> treatmentList, Room laysIn, Nurse getsTreatedBy) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.treatmentList = treatmentList;
        this.laysIn = laysIn;
        this.getsTreatedBy = getsTreatedBy;
    }
}
