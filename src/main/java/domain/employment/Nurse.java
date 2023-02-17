package domain.employment;

import domain.Patient;
import domain.premises.Ward;

import java.time.LocalDate;
import java.util.List;

public class Nurse extends Employee {
    private List<Patient> takesCareOf;

    public Nurse(int personalnumber, String firstName, String lastName, LocalDate birthdate, Ward myWard, Double salary, List<Patient> patients) {
        super(personalnumber, firstName, lastName, birthdate, myWard, salary);
        this.takesCareOf = patients;
    }
}
