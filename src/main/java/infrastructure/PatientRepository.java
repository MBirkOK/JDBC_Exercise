package infrastructure;

import domain.Patient;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PatientRepository {

    int safePatient(Patient patient) throws SQLException;

    Optional<Patient> findPatientById(int id) throws SQLException, IOException;

    List<Patient> getPatientsFromNurseId(int nurseId) throws SQLException, IOException;

    List<Patient> getPatientBetweenDates(LocalDate startDate, LocalDate endDate) throws SQLException, IOException;

    List<Integer> calculateGenderDivision() throws SQLException;
}
