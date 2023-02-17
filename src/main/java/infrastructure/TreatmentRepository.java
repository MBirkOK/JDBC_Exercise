package infrastructure;

import domain.Treatment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<String[]> calculateAmountOfMedicationInTreamtment() throws SQLException {
        String sql = "SELECT COUNT(med_id), description, treat.treatment FROM tab_exercise_treatment treat " +
            "LEFT OUTER JOIN tab_exercise_med med ON treat.med_id = med.id group by treat.treatment, description";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String[]> medData = new ArrayList<>();
        while(resultSet.next()){
            String[] data = new String[3];
            data[0] = String.valueOf(resultSet.getInt(1));
            data[1] = resultSet.getString(2);
            data[2] = resultSet.getString(3);
            medData.add(data);
        }

        return medData;
    }
}
