package infrastructure;

import domain.Patient;
import domain.employment.Employee;
import domain.employment.Nurse;
import domain.premises.Room;
import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientRepositoryImpl implements PatientRepository {
    private DatabaseHandler databaseHandler = new DatabaseHandler();
    private RoomRepositoryImpl roomRepositoryImpl = new RoomRepositoryImpl();
    private EmployeeRepositoryImpl employeeRepositoryImpl = new EmployeeRepositoryImpl();

    public PatientRepositoryImpl() throws SQLException, ClassNotFoundException {
    }

    @Override
    public int safePatient(Patient patient, EntityManager entityManager) throws SQLException {
        try {
            String sql = "INSERT INTO tab_exercise_patient(first_name, last_name, birthday, room_id, nurse_id, stay_start, stay_end, gender) VALUES( ?, ?, ?, ?, ?, ?, ?,?)";
            PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, patient.getFirstName());
            preparedStatement.setString(2, patient.getLastName());
            preparedStatement.setDate(3, Date.valueOf(patient.getBirthdate()));
            preparedStatement.setInt(4, patient.getRoom().getId());
            preparedStatement.setInt(5, patient.getNurse().getPersonalnumber());
            preparedStatement.setDate(6, Date.valueOf(patient.getStartStay()));
            preparedStatement.setDate(7, Date.valueOf(patient.getEndStay()));
            preparedStatement.setString(8, patient.getGender());

            preparedStatement.executeUpdate();
            preparedStatement.getGeneratedKeys().next();
            patient.setId(preparedStatement.getGeneratedKeys().getInt("id"));

            return patient.getId();
        } catch (SQLException e) {
            throw e;
        }
    }
    @Override
    public Optional<Patient> findPatientById(int id, EntityManager entityManager) throws SQLException, IOException {
        String sql = "SELECT id, first_name, last_name, birthday, room_id, nurse_id, stay_start, stay_end, gender FROM tab_exercise_patient WHERE id = ?";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Room room = roomRepositoryImpl.findRoomWithIdWithoutPatients(resultSet.getInt("room_id"), null);
        Employee employeeNurse = employeeRepositoryImpl.findEmployeeById(resultSet.getInt("nurse_id"), null).get();
        Nurse nurse = new Nurse(employeeNurse.getPersonalnumber(), employeeNurse.getFirstName(),
            employeeNurse.getLastName(), employeeNurse.getBirthdate(), employeeNurse.getWard(),
            employeeNurse.getSalary());

        return Optional.of(new Patient(resultSet.getInt("id"), resultSet.getString("first_name"),
            resultSet.getString("last_name"), databaseHandler.convertDateToLocalDate(resultSet.getDate("birthday")), null,
            room, nurse, databaseHandler.convertDateToLocalDate(resultSet.getDate("stay_start")),
            databaseHandler.convertDateToLocalDate(resultSet.getDate("stay_end")), resultSet.getString("gender")));
    }
    @Override
    public List<Patient> getPatientsFromNurseId(int nurseId, EntityManager entityManager) throws SQLException, IOException {
        String sql = "SELECT id, first_name, last_name, birthday, room_id, stay_start, stay_end, gender FROM tab_exercise_patient WHERE nurse_id = ?";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
        preparedStatement.setInt(1, nurseId);

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Patient> patients = new ArrayList<>();
        while (resultSet.next()) {
            Room room = roomRepositoryImpl.findRoomWithIdWithoutPatients(resultSet.getInt("room_id"), null);
            patients.add(new Patient(resultSet.getInt("id"), resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                databaseHandler.convertDateToLocalDate(resultSet.getDate("birthday")),
                null, room, null,
                databaseHandler.convertDateToLocalDate(resultSet.getDate("stay_start")),
                databaseHandler.convertDateToLocalDate(resultSet.getDate("stay_end")), resultSet.getString("gender")));
        }
        return patients;
    }
    @Override
    public List<Patient> getPatientBetweenDates(LocalDate startDate, LocalDate endDate, EntityManager entityManager) throws SQLException, IOException {
        String sql = "SELECT id, first_name, last_name, birthday, room_id, nurse_id, stay_start, stay_end, gender FROM tab_exercise_patient WHERE stay_start >=? AND stay_end <=?";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
        preparedStatement.setDate(1, Date.valueOf(startDate));
        preparedStatement.setDate(2, Date.valueOf(endDate));

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Patient> patients = new ArrayList<>();
        while (resultSet.next()) {
            Room room = roomRepositoryImpl.findRoomWithIdWithoutPatients(resultSet.getInt("room_id"), null);
            Employee employeeNurse = employeeRepositoryImpl.findEmployeeById(resultSet.getInt("nurse_id"), null).get();
            Nurse nurse = new Nurse(employeeNurse.getPersonalnumber(), employeeNurse.getFirstName(),
                employeeNurse.getLastName(), employeeNurse.getBirthdate(), employeeNurse.getWard(),
                employeeNurse.getSalary());
            patients.add(new Patient(resultSet.getInt("id"), resultSet.getString("first_name"),
                resultSet.getString("last_name"), databaseHandler.convertDateToLocalDate(resultSet.getDate("birthday")), null,
                room, nurse, databaseHandler.convertDateToLocalDate(resultSet.getDate("stay_start")),
                databaseHandler.convertDateToLocalDate(resultSet.getDate("stay_end")), resultSet.getString("gender")));
        }
        return patients;
    }
    @Override
    public List<Integer> calculateGenderDivision(EntityManager entityManager) throws SQLException {
        String sql = "SELECT COUNT(*) FROM tab_exercise_patient GROUP BY gender";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Integer> genderData = new ArrayList<>();
        while (resultSet.next()) {
            genderData.add(resultSet.getInt(1));
        }
        return genderData;
    }
}
