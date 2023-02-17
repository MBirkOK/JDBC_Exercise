package domain.employment;

import domain.premises.Ward;

import java.time.LocalDate;

public class Resident extends Employee {

    public Resident(int personalnumber, String firstName, String lastName, LocalDate birthdate, Ward myWard, Double salary) {
        super(personalnumber, firstName, lastName, birthdate, myWard, salary);
    }
}
