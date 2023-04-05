package infrastructure;

import domain.Patient;
import domain.Treatment;
import domain.employment.SeniorOfficer;
import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.xml.transform.Result;

public class TreatmentRepositoryImpl implements TreatmentRepository {
    private DatabaseHandler databaseHandler = new DatabaseHandler();

    private PatientRepository patientRepository = new PatientRepositoryImpl();

    private EmployeeRepository employeeRepository = new EmployeeRepositoryImpl();

    public TreatmentRepositoryImpl() throws SQLException, ClassNotFoundException {
    }

    @Override
    public int safeTreatment(Treatment treatment, EntityManager entityManager) throws SQLException {
        String sql = "INSERT INTO tab_exercise_treatment(treatment, pers_nr, patient_id) VALUES (?,?,?)";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, treatment.getTreatment());
        preparedStatement.setInt(2, treatment.getResponsible().getPersonalnumber());
        preparedStatement.setInt(3, treatment.getPatient().getId());

        try {
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            return generatedKeys.getInt(1);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<String[]> calculateAmountOfMedicationInTreamtment(EntityManager entityManager) throws SQLException {
        String sql = "SELECT COUNT(medication_id), description, treat.treatment FROM tab_exercise_treatment treat " +
            "LEFT OUTER JOIN tab_exercise_med med ON treat.medication_id = med.id group by treat.treatment, description";
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
    public Optional<Treatment> findTreatmentById(int id, EntityManager entityManager) throws SQLException, IOException {
        String sql = "SELECT id, treatment, responsibleFor_id, patient_id, medication_id FROM tab_exercise_treatment where id = ?";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Patient patient = patientRepository.findPatientById(resultSet.getInt("patient_id"), entityManager).get();
        //TODO what if Employee is not Senior?
        SeniorOfficer senior = (SeniorOfficer)employeeRepository.findEmployeeById(resultSet.getInt("employee_id"), entityManager).get();

        Treatment treatment = new Treatment(resultSet.getInt("id"), resultSet.getString("treatment"), senior, patient);

        return Optional.of(treatment);
    }
}
