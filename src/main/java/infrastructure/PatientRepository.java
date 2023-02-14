package infrastructure;

import domain.Patient;
import domain.premises.Room;

import java.awt.geom.RectangularShape;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientRepository {
    private DatabaseHandler databaseHandler = new DatabaseHandler();
    private RoomRepository roomRepository = new RoomRepository();

    public PatientRepository() throws SQLException, ClassNotFoundException {
    }

    public Patient safePatient(Patient patient) throws SQLException {
        try {
            String sql = "INSERT INTO tab_exercise_patient(first_name, last_name, birthday, room_id, nurse_id, stay_start, stay_end) VALUES( ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
            preparedStatement.setString(1, patient.getFirstName());
            preparedStatement.setString(2, patient.getLastName());
            preparedStatement.setDate(3, Date.valueOf(patient.getBirthdate()));
            preparedStatement.setInt(4, patient.getRoom().getId());
            preparedStatement.setInt(5, patient.getNurse().getPersonalnumber());
            preparedStatement.setDate(6, Date.valueOf(patient.getStartStay()));
            preparedStatement.setDate(7, Date.valueOf(patient.getEndStay()));

            preparedStatement.executeUpdate();
            return patient;
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<Patient> getPatientsFromNurseId(int nurseId) throws SQLException, IOException {
        String sql = "SELECT id, first_name, last_name, birthday, room_id, stay_start, stay_end FROM tab_exercise_patient WHERE nurse_id = ?";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
        preparedStatement.setInt(1, nurseId);

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Patient> patients = new ArrayList<>();
        while(resultSet.next()){
            Room room = roomRepository.findRoomWithIdWithoutPatients(resultSet.getInt("room_id"));
            patients.add(new Patient(resultSet.getInt("id"), resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                databaseHandler.convertDateToLocalDate(resultSet.getDate("birthday")),
                null, room,null,
                databaseHandler.convertDateToLocalDate(resultSet.getDate("stay_start")),
                databaseHandler.convertDateToLocalDate(resultSet.getDate("stay_end"))));
        }
        return patients;
    }
}
