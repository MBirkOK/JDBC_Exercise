package domain.employment;

import domain.Patient;
import domain.premises.Ward;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue(value = "Nurse")
public class Nurse extends Employee {

    public Nurse(int personalnumber, String firstName, String lastName, LocalDate birthdate, Ward myWard, Double salary) {
        super(personalnumber, firstName, lastName, birthdate, myWard, salary);
    }

    public Nurse(Employee employee) {
        super(employee.getPersonalnumber(), employee.getFirstName(), employee.getLastName(), employee.getBirthdate(), employee.getWard(), employee.getSalary());
    }

    public Nurse(String firstName, String lastName, LocalDate birthdate, Ward myWard, Double salary) {
        super(firstName, lastName, birthdate, myWard, salary);
    }

    protected Nurse() {
        //for JPA
    }
}
