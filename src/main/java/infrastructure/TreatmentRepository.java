package infrastructure;

import domain.Treatment;
import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TreatmentRepository {

    int safeTreatment(Treatment treatment, EntityManager entityManager) throws SQLException;

    List<String[]> calculateAmountOfMedicationInTreamtment(EntityManager entityManager) throws SQLException;

    Optional<Treatment> findTreatmentById(int id, EntityManager entityManager) throws SQLException, IOException;
}
