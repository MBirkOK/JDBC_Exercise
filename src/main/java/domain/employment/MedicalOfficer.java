package domain.employment;

import domain.premises.Ward;

import java.time.LocalDate;

public class MedicalOfficer extends Employee{
    private Ward leading;
    public MedicalOfficer(int personalnumber, String firstName, String lastName, LocalDate birthdate, Ward myWard, Double salary, Ward leading) {
        super(personalnumber, firstName, lastName, birthdate, myWard, salary);
        this.leading = leading;
    }
}
