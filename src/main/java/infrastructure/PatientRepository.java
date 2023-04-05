package infrastructure;

import domain.Patient;
import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PatientRepository {

    int safePatient(Patient patient, EntityManager entityManager) throws SQLException;

    Optional<Patient> findPatientById(int id, EntityManager entityManager) throws SQLException, IOException;

    List<Patient> getPatientsFromNurseId(int nurseId, EntityManager entityManager) throws SQLException, IOException;

    List<Patient> getPatientBetweenDates(LocalDate startDate, LocalDate endDate, EntityManager entityManager) throws SQLException, IOException;

    List<Integer> calculateGenderDivision(EntityManager entityManager) throws SQLException;
}
