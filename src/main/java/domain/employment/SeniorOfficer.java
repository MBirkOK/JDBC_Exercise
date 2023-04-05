package domain.employment;

import domain.Treatment;
import domain.premises.Ward;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;

@Entity
public class SeniorOfficer extends Employee {

    @JoinColumn(referencedColumnName = "id")
    @OneToOne
    private Treatment responsibleFor;

    public SeniorOfficer(int personalnumber, String firstName, String lastName, LocalDate birthdate, Ward myWard, Double salary, Treatment treatment) {
        super(personalnumber, firstName, lastName, birthdate, myWard, salary);
        this.responsibleFor = treatment;
    }

    public SeniorOfficer(Employee employee) {
        super(employee.getPersonalnumber(), employee.getFirstName(), employee.getLastName(), employee.getBirthdate(), employee.getWard(), employee.getSalary());
        this.responsibleFor = null;
    }

    protected SeniorOfficer() {
        //for JPA
    }

    public Treatment getTreatment() {
        return responsibleFor;
    }

    public void setTreatment(Treatment responisbleFor) {
        this.responsibleFor = responisbleFor;
    }
}
