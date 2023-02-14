package infrastructure;

import domain.employment.Employee;
import domain.employment.MedicalOfficer;
import domain.premises.Ward;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    private DatabaseHandler databaseHandler = new DatabaseHandler();
    private WardRepository wardRepository = new WardRepository();


    public EmployeeRepository() throws SQLException, ClassNotFoundException {
    }

    /**
     * This method gets an object either of type employee or of type inventory. Depends on of which type of class
     * the object is, the method to safe it in persistence differs.
     *
     * @param employee
     * @return the original Object
     * @throws SQLException
     */
    public Employee safeEmployee(Employee employee) throws SQLException {
        try {
            String sql = "INSERT INTO tab_exercise_employee(first_name, last_name, birthday, ward_id, type, salary) VALUES  (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setDate(3, Date.valueOf(employee.getBirthdate()));
            preparedStatement.setInt(4, employee.getWard().getId());
            preparedStatement.setString(5, employee.getClass().toString());
            preparedStatement.setDouble(6, employee.getSalary());
            preparedStatement.executeUpdate();
            return employee;
        } catch (Exception e) {
            System.out.println("Saving in Database failed");
            System.out.println(e);
            return new Employee(0, "", "", LocalDate.now(), null, 0.0);
        }
    }

    public Employee findEmployeeById(int id) {
        try {
            String sql = "SELECT pers_nr, first_name, last_name, birthday, ward_id, type, salary from tab_exercise_employee e INNER JOIN tab_exercise_ward on ward_id = tab_exercise_ward.id where pers_nr=?";
            PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next() == false) {
                //TODO don't return null
                return null;
            }
            Ward ward = wardRepository.findWardById(result.getInt("ward_id"));
            return Employee.createEmployeeByType(result.getInt("pers_nr"), result.getString("first_name"),
                result.getString("last_name"), databaseHandler.convertDateToLocalDate(result.getDate("birthday")),
                ward, result.getDouble("salary"), result.getString("type"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public MedicalOfficer findMedicalById(int id) throws SQLException {
        String sql = "SELECT pers_nr, first_name, last_name, birthday, salary FROM tab_exercise_employee WHERE pers_nr = ?";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next() == false) {
            //TODO dont return null
            return null;
        }
        //TODO salary missing in DB
        return new MedicalOfficer(resultSet.getInt("pers_nr"), resultSet.getString("first_name"),
            resultSet.getString("last_name"), databaseHandler.convertDateToLocalDate(resultSet.getDate("birthday")),
            null, resultSet.getDouble("salary"));
    }

    public List<Employee> findEmployeesByWardId(int wardId) throws SQLException {
        String sql = "SELECT pers_nr, first_name, last_name, birthday FROM tab_exercise_employee WHERE ward_id = ?";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
        preparedStatement.setInt(1, wardId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next() == false) {
            //TODO dont return null
            return null;
        }
        int lastRow = databaseHandler.getSizeResultSet(resultSet);
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < lastRow; i++) {
            //TODO salary still doesnt exists in DB
            employees.add(new Employee(resultSet.getInt("pers_nr"), resultSet.getString("first_name"), resultSet.getString("last_name"),
                databaseHandler.convertDateToLocalDate(resultSet.getDate("birthday")), null, null));
        }
        return employees;
    }
}
