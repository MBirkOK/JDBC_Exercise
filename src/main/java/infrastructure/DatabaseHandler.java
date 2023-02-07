package infrastructure;

import domain.employment.Employee;
import domain.employment.Resident;
import domain.premises.Ward;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Properties;

public class DatabaseHandler {

    protected final String URL = "jdbc:postgresql://localhost:5432/postgres?user=cqacdkfw&password=U4b4yfEAc8lrVgNst4YQ_799WBJ27wXn&ssl=false";

    public DatabaseHandler() throws SQLException, ClassNotFoundException {
        establishConnection();
    }

    private Connection establishConnection() throws SQLException {
        try {
            Properties props = new Properties();
            props.setProperty("user", "cqacdkfw");
            props.setProperty("password", "U4b4yfEAc8lrVgNst4YQ_799WBJ27wXn");
            props.setProperty("ssl", "false");

            Connection connection = DriverManager.getConnection(URL, props);
            return connection;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method gets an object either of type employee or of type inventory. Depens on of which type of class
     * the object is, the method to safe it in persistence differs.
     *
     * @param employee
     * @return the original Object
     * @throws SQLException
     */
    public Employee safeEmployee(Employee employee) throws SQLException {
        try {
            String sql = "INSERT INTO tab_exercise_employee(pers_nr, first_name, last_name, birthday, ward_id, type) VALUES  (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = establishConnection().prepareStatement(sql);
            preparedStatement.setInt(1, employee.getPersonalnumber());
            preparedStatement.setString(2, employee.getFirstName());
            preparedStatement.setString(3, employee.getLastName());
            preparedStatement.setDate(4, Date.valueOf(employee.getBirthdate()));
            preparedStatement.setInt(5, employee.getWard().getId());
            preparedStatement.setString(6, employee.getClass().toString());
            preparedStatement.executeUpdate();
            return employee;
        } catch (Exception e) {
            System.out.println("Saving in Database failed");
            return new Employee(0, "", "", LocalDate.now(), 0, 0.0, 0);
        }
    }

    public Employee findEmployeeById(int id) {
        try {
            String sql = "SELECT pers_nr, first_name, last_name, birthday, ward_id, type from tab_exercise_employee e INNER JOIN tab_exercise_ward on ward_id = tab_exercise_ward.id where pers_nr=?";
            PreparedStatement preparedStatement = establishConnection().prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next() == false) {
                //TODO don't return null
                return null;
            }
            //TODO salary missing in DB
            Ward ward;
            if(result.getInt("pers_nr") == result.getInt("officer_id")){
                ward = new Ward(result.getInt("ward_id"), null, null, null);
            }
            return Employee.createEmployeeByType(result.getInt("pers_nr"), result.getString("first_name"),
                result.getString("last_name"), convertToLocalDateViaInstant(result.getDate("birthday")),
                ward, null, result.getString("type"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    }
}
