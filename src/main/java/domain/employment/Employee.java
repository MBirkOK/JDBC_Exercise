package domain.employment;

import domain.AbstractEntity;
import domain.premises.Ward;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.time.LocalDate;

@Entity
@Table(name = "tab_exercise_employee")
@DiscriminatorColumn(name = "type")
public class Employee extends AbstractEntity {
    @Id
    @Column(name = "pers_nr")
    private int personalnumber;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birthday")
    private LocalDate birthdate;
    @ManyToOne
    private Ward ward;
    @Column(name = "salary")
    private Double salary;

    @Transient
    private String type;

    public Employee(int personalnumber, String firstName, String lastName, LocalDate birthdate, Ward myWard, Double salary) {
        this.personalnumber = personalnumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.ward = myWard;
        this.salary = salary;
    }

    public Employee(String firstName, String lastName, LocalDate birthdate, Ward myWard, Double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.ward = myWard;
        this.salary = salary;
    }

    protected Employee() {
        //for JPA
    }

    public int getPersonalnumber() {
        return personalnumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public Ward getWard() {
        return ward;
    }

    public Double getSalary() {
        return salary;
    }

    public static Employee createEmployeeByType(int personalnumber, String firstName, String lastName, LocalDate birthdate, Ward myWard, Double salary, String type) {
        //TODO Query the Wards
        switch (type) {
            case "class domain.employment.MedicalOfficer":
                return new MedicalOfficer(personalnumber, firstName, lastName, birthdate, myWard, salary);
            case "class domain.employment.Nurse":
                //TODO query the list of patients to care for
                return new Nurse(personalnumber, firstName, lastName, birthdate, myWard, salary);
            case "class domain.employment.Resident":
                return new Resident(personalnumber, firstName, lastName, birthdate, myWard, salary);
            case "class domain.employment.SeniorOfficer":
                //TODO Query the Treatments
                return new SeniorOfficer(personalnumber, firstName, lastName, birthdate, myWard, salary, null);
            case "class domain.employment.Specialist":
                return new Specialist(personalnumber, firstName, lastName, birthdate, myWard, salary);
            default:
                return null;
        }
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getType() {
        return type;
    }

    @Override
        protected Object getId() {
            return this.personalnumber;
    }
}
