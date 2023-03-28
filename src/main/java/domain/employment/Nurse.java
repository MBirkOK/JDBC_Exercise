package domain.employment;

import domain.Patient;
import domain.premises.Ward;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Nurse extends Employee {

    @OneToMany(mappedBy = "getsTreatedBy")
    @JoinColumn(referencedColumnName = "pers_nr")
    private List<Patient> takesCareOf;

    public Nurse(int personalnumber, String firstName, String lastName, LocalDate birthdate, Ward myWard, Double salary, List<Patient> patients) {
        super(personalnumber, firstName, lastName, birthdate, myWard, salary);
        this.takesCareOf = patients;
    }

    protected Nurse() {
        //for JPA
    }
}
