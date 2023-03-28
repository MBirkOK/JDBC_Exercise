package infrastructure;

import domain.Patient;
import domain.Treatment;
import domain.employment.SeniorOfficer;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TreatmentRepositoryImpl implements TreatmentRepository {
    private DatabaseHandler databaseHandler = new DatabaseHandler();

    private PatientRepository patientRepository = new PatientRepositoryImpl();

    private EmployeeRepository employeeRepository = new EmployeeRepositoryImpl();

    public TreatmentRepositoryImpl() throws SQLException, ClassNotFoundException {
    }

    public int safeTreatment(Treatment treatment) throws SQLException {
        String sql = "INSERT INTO tab_exercise_treatment(treatment, employee_id, patient_id) VALUES (?,?,?)";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
        preparedStatement.setString(1, treatment.getTreatment());
        preparedStatement.setInt(2, treatment.getResponsible().getPersonalnumber());
        preparedStatement.setInt(3, treatment.getPatient().getId());

        try {
            preparedStatement.executeUpdate();
            return treatment.getId();
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<String[]> calculateAmountOfMedicationInTreamtment() throws SQLException {
        String sql = "SELECT COUNT(med_id), description, treat.treatment FROM tab_exercise_treatment treat " +
            "LEFT OUTER JOIN tab_exercise_med med ON treat.med_id = med.id group by treat.treatment, description";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String[]> medData = new ArrayList<>();
        while (resultSet.next()) {
            String[] data = new String[3];
            data[0] = String.valueOf(resultSet.getInt(1));
            data[1] = resultSet.getString(2);
            data[2] = resultSet.getString(3);
            medData.add(data);
        }

        return medData;
    }

    @Override
    public Optional<Treatment> findTreatmentById(int id) throws SQLException, IOException {
        String sql = "SELECT id, treatment, employee_id, patient_id, med_id FROM tab_exercise_treatment where id = ?";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Patient patient = patientRepository.findPatientById(resultSet.getInt("patient_id")).get();
        SeniorOfficer senior = (SeniorOfficer)employeeRepository.findEmployeeById(resultSet.getInt("employee_id")).get();

        Treatment treatment = new Treatment(resultSet.getInt("id"), resultSet.getString("treatment"), senior, patient);

        return Optional.of(treatment);
    }
}
