package infrastructure;

import domain.Treatment;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TreatmentRepository {
    private DatabaseHandler databaseHandler = new DatabaseHandler();

    public TreatmentRepository() throws SQLException, ClassNotFoundException {
    }

    public Treatment safeTreatment(Treatment treatment) throws SQLException {
        String sql = "INSERT INTO tab_exercise_treatment(treatment, employee_id, patient_id) VALUES (?,?,?)";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
        preparedStatement.setString(1, treatment.getTreatment());
        preparedStatement.setInt(2, treatment.getResponsible().getPersonalnumber());
        preparedStatement.setInt(3, treatment.getPatient().getId());

        try {
            preparedStatement.executeUpdate();
            return treatment;
        } catch (SQLException e) {
            throw e;
        }
    }
}
