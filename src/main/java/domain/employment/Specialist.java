package domain.employment;

import domain.premises.Ward;

import java.time.LocalDate;

public class Specialist extends Employee{
    public Specialist(int personalnumber, String firstName, String lastName, LocalDate birthdate, Ward myWard, Double salary) {
        super(personalnumber, firstName, lastName, birthdate, myWard, salary);
    }
}
