package domain.employment;

import domain.premises.Ward;

import java.time.LocalDate;

public class Employee {

    private int personalnumber;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private Ward ward;

    private Double salary;

    public Employee(int personalnumber, String firstName, String lastName, LocalDate birthdate, Ward myWard, Double salary) {
        this.personalnumber = personalnumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.ward = myWard;
        this.salary = salary;
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
            case "domain.employment.MedicalOfficer":
                return new MedicalOfficer(personalnumber, firstName, lastName, birthdate, myWard, salary);
            case "domain.employment.Nurse":
                //TODO query the list of patients to care for
                return new Nurse(personalnumber, firstName, lastName, birthdate, myWard, salary, null);
            case "domain.employment.Resident":
                return new Resident(personalnumber, firstName, lastName, birthdate, myWard, salary);
            case "domain.employent.SeniorOfficer":
                //TODO Query the Treatments
                return new SeniorOfficer(personalnumber, firstName, lastName, birthdate, myWard, salary, null);
            case "domain.employment.Specialist":
                return new Specialist(personalnumber, firstName, lastName, birthdate, myWard, salary);
            default:
                return new Employee(personalnumber, firstName, lastName, birthdate, myWard, salary);
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

}
