package infrastructure;

import domain.employment.Employee;
import domain.employment.MedicalOfficer;
import jakarta.persistence.EntityManager;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

    int safeEmployee(Employee employee, EntityManager entityManager);

    Optional<Employee> findEmployeeById(int id, EntityManager entityManager);

    Optional<MedicalOfficer> findMedicalById(int id, EntityManager entityManager) throws SQLException;

    List<Employee> findEmployeesByWardId(int wardId, EntityManager entityManager) throws SQLException;

    Employee[] findAllEmployees(EntityManager entityManager) throws SQLException;
}
