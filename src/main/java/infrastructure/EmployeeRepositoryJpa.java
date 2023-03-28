package infrastructure;

import domain.employment.Employee;
import domain.employment.MedicalOfficer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeRepositoryJpa implements EmployeeRepository{
    private EntityManager entityManager = new EntityManagerFactoryBuilderImpl("persistence.xml").build().createEntityManager();
    @Override
    public int safeEmployee(Employee employee) {
        return 0;
    }

    @Override
    public Optional<Employee> findEmployeeById(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<MedicalOfficer> findMedicalById(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Employee> findEmployeesByWardId(int wardId) throws SQLException {
        return null;
    }

    @Override
    public Employee[] findAllEmployees() throws SQLException {
        return new Employee[0];
    }
}
