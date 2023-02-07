package infrastructure;

import domain.employment.Employee;
import domain.employment.MedicalOfficer;
import domain.premises.Room;
import domain.premises.Ward;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

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
            return new Employee(0, "", "", LocalDate.now(), null, 0.0);
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
            Ward ward = findWardById(result.getInt("ward_id"));
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


    public Ward findWardById(int id) throws SQLException {
        String sql = "SELECT * FROM tab_exercise_ward WHERE id = ?";
        PreparedStatement preparedStatement = establishConnection().prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next() == false) {
            //TODO dont return null
            return null;
        }
        MedicalOfficer medicalOfficer = findMedicalById(resultSet.getInt("officer_id"));
        List<Room> roomList = findRoomsByWardId(id);
        List<Employee> worksIn = findEmployeesByWardId(id);
        Ward ward = new Ward(resultSet.getInt("id"), medicalOfficer, roomList, worksIn);
        ward.getMedicalOfficer().setWard(ward);
        worksIn.stream().filter(e -> e.getPersonalnumber() != ward.getMedicalOfficer().getPersonalnumber())
            .collect(Collectors.toList());
        for(Employee employee: worksIn){
            employee.setWard(ward);
        }

        return ward;
    }

    public MedicalOfficer findMedicalById(int id) throws SQLException {
        String sql = "SELECT pers_nr, first_name, last_name, birthday FROM tab_exercise_employee WHERE pers_nr = ?";
        PreparedStatement preparedStatement = establishConnection().prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next() == false) {
            //TODO dont return null
            return null;
        }
        //TODO salary missing in DB
        return new MedicalOfficer(resultSet.getInt("pers_nr"), resultSet.getString("first_name"),
            resultSet.getString("last_name"), convertToLocalDateViaInstant(resultSet.getDate("birthday")),
            null, null);
    }

    public List<Room> findRoomsByWardId(int wardId) throws SQLException {
        String sql = "SELECT id, amount_beds, ward_id FROM tab_exercise_room WHERE ward_id = ?";
        PreparedStatement preparedStatement = establishConnection().prepareStatement(sql);
        preparedStatement.setInt(1, wardId);
        ResultSet resultSet = preparedStatement.executeQuery();
        int lastRow = getSizeResultSet(resultSet);
        List<Room> rooms = new ArrayList<>();
        for (int i = 0; i < lastRow; i++) {
            rooms.add(new Room(resultSet.getInt("id"), resultSet.getInt("amount_beds")));
        }
        return rooms;
    }

    private int getSizeResultSet(ResultSet set) throws SQLException {
        set.last();
        return set.getRow();
    }

    public List<Employee> findEmployeesByWardId(int wardId) throws SQLException {
        String sql = "SELECT pers_nr, first_name, last_name, birthday FROM tab_exercise_employee WHERE ward_id = ?";
        PreparedStatement preparedStatement = establishConnection().prepareStatement(sql);
        preparedStatement.setInt(1, wardId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next() == false) {
            //TODO dont return null
            return null;
        }
        int lastRow = getSizeResultSet(resultSet);
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < lastRow; i++) {
            //TODO salary still doesnt exists in DB
            employees.add(new Employee(resultSet.getInt("pers_nr"), resultSet.getString("first_name"), resultSet.getString("last_name"),
                convertToLocalDateViaInstant(resultSet.getDate("birthday")), null, null));

        }
        return employees;
    }
}
