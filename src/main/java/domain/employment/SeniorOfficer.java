package domain.employment;

import domain.Treatment;
import domain.premises.Ward;

import java.time.LocalDate;

public class SeniorOfficer extends Employee {
    private Treatment responisbleFor;

    public SeniorOfficer(int personalnumber, String firstName, String lastName, LocalDate birthdate, Ward myWard, Double salary, Treatment treatment) {
        super(personalnumber, firstName, lastName, birthdate, myWard, salary);
        this.responisbleFor = treatment;
    }

    public Treatment getTreatment() {
        return responisbleFor;
    }

    public void setTreatment(Treatment responisbleFor) {
        this.responisbleFor = responisbleFor;
    }
}
