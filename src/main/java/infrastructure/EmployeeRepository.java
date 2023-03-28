package infrastructure;

import domain.employment.Employee;
import domain.employment.MedicalOfficer;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

    int safeEmployee(Employee employee);

    Optional<Employee> findEmployeeById(int id);

    Optional<MedicalOfficer> findMedicalById(int id) throws SQLException;

    List<Employee> findEmployeesByWardId(int wardId) throws SQLException;

    Employee[] findAllEmployees() throws SQLException;
}
