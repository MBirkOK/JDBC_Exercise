package domain;

import java.time.LocalDate;

public class Employee {

    private int personalnumber;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;

    public Employee(int personalnumber, String firstName, String lastName, LocalDate birthdate) {
        this.personalnumber = personalnumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
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
}
