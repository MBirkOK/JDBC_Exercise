package domain.employment;

import domain.premises.Ward;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;


public class MedicalOfficer extends Employee {
    public MedicalOfficer(int personalnumber, String firstName, String lastName, LocalDate birthdate, Ward myWard, Double salary) {
        super(personalnumber, firstName, lastName, birthdate, myWard, salary);
    }
}
