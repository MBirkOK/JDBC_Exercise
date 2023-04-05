package infrastructure;

import domain.Patient;
import domain.employment.Employee;
import domain.employment.Nurse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.SQLInsert;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PatientRepositoryJpa implements PatientRepository {

    public PatientRepositoryJpa() {
    }

    @Override
    public int safePatient(Patient patient, EntityManager entityManager) throws SQLException {
        entityManager.persist(patient);
        return patient.getId();
    }

    @Override
    public Optional<Patient> findPatientById(int id, EntityManager entityManager) throws SQLException, IOException {
        return Optional.of(entityManager.find(Patient.class, id));
    }

    @Override
    public List<Patient> getPatientsFromNurseId(int nurseId, EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT p from Patient p WHERE p.getsTreatedBy.personalnumber = :id");
        query.setParameter("id", nurseId);
        return query.getResultList();
    }

    @Override
    public List<Patient> getPatientBetweenDates(LocalDate startDate, LocalDate endDate, EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT p FROM Patient p WHERE p.startStay >= :start AND p.endStay <= :end");
        query.setParameter("start", startDate);
        query.setParameter("end", endDate);
        return query.getResultList();
    }

    @Override
    public List<Integer> calculateGenderDivision(EntityManager entityManager) throws SQLException {
        Query query = entityManager.createQuery("SELECT COUNT(p) FROM Patient p WHERE p.gender LIKE 'W' GROUP BY p.gender");
        List<Integer> integerList = new ArrayList<>();
        List resultList = query.getResultList();
        if (resultList.size() == 0) {
            integerList.add(0);
        } else {
            integerList.add(Integer.parseInt(String.valueOf(resultList.get(0))));
        }
        Query men = entityManager.createQuery("SELECT COUNT(p) FROM Patient p WHERE p.gender LIKE 'M' GROUP BY p.gender");
        resultList = men.getResultList();
        if (resultList.size() == 0) {
            integerList.add(0);
        } else {
            integerList.add(Integer.parseInt(String.valueOf(resultList.get(0))));
        }
        Query divers = entityManager.createQuery("SELECT COUNT(p) FROM Patient p WHERE p.gender LIKE 'D' GROUP BY p.gender");
        resultList = divers.getResultList();
        if (resultList.size() == 0) {
            integerList.add(0);
        } else {
            integerList.add(Integer.parseInt(String.valueOf(resultList.get(0))));
        }

        return integerList;
    }
}
