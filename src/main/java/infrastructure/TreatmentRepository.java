package infrastructure;

import domain.Treatment;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TreatmentRepository {

    int safeTreatment(Treatment treatment) throws SQLException;

    List<String[]> calculateAmountOfMedicationInTreamtment() throws SQLException;

    Optional<Treatment> findTreatmentById(int id) throws SQLException, IOException;
}
