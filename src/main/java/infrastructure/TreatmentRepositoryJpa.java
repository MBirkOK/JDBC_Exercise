package infrastructure;

import domain.Medication;
import domain.Treatment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TreatmentRepositoryJpa implements TreatmentRepository {

    @Override
    public int safeTreatment(Treatment treatment, EntityManager entityManager) throws SQLException {
        entityManager.persist(treatment);
        return treatment.getId();
    }

    @Override
    public List<String[]> calculateAmountOfMedicationInTreamtment(EntityManager entityManager) throws SQLException {
        Query query = entityManager.createQuery("SELECT t FROM Treatment t ");
        List<Treatment> treatments = query.getResultList();
        query = entityManager.createQuery("SELECT m FROM Medication m");
        List<Medication> medications = query.getResultList();
        List<String[]> medData = new ArrayList<>();

        for (Medication medication : medications) {
            int useCounter = 0;
            String treatmentDescription = "";
            for (Treatment treatment : treatments) {
                if (medication.getId() == treatment.getMedication().getId()) {
                    useCounter = +1;
                    treatmentDescription = treatment.getTreatment();
                }
            }
            medData.add(new String[]{String.valueOf(useCounter), medication.getDescription(), treatmentDescription});
        }
        return medData;
    }

    @Override
    public Optional<Treatment> findTreatmentById(int id, EntityManager entityManager) throws SQLException, IOException {
        Treatment treatment = entityManager.find(Treatment.class, id);
        return Optional.ofNullable(treatment);
    }
}
