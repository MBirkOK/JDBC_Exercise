package domain;

import infrastructure.DatabaseHandler;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

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

    public static List<Employee> findEmployeeByFirstName(String firstName) throws SQLException, ClassNotFoundException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        return databaseHandler.findByFirstName(firstName);
    }
}
