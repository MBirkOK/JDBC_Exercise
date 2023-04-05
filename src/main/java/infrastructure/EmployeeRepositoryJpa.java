package infrastructure;

import domain.employment.Employee;
import domain.employment.MedicalOfficer;
import domain.employment.Nurse;
import domain.employment.SeniorOfficer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeRepositoryJpa implements EmployeeRepository {
    @Override
    public int safeEmployee(Employee employee, EntityManager entityManager) {
        entityManager.getTransaction().begin();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
        return employee.getPersonalnumber();
    }

    @Override
    public Optional<Employee> findEmployeeById(int id, EntityManager entityManager) {
        return Optional.of(entityManager.find(Employee.class, id));
    }

    @Override
    public Optional<MedicalOfficer> findMedicalById(int id, EntityManager entityManager) throws SQLException {
        Employee employee = entityManager.find(Employee.class, id);
        if (employee != null && employee.getType().equals("MedicalOfficer")) {
            return Optional.of(new MedicalOfficer(employee));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Employee> findEmployeesByWardId(int wardId, EntityManager entityManager) throws SQLException {
        Query query = entityManager.createQuery("SELECT e FROM Employee e WHERE e.ward.id = :ward");
        query.setParameter(1, wardId);
        return query.getResultList();
    }

    @Override
    public Employee[] findAllEmployees(EntityManager entityManager) throws SQLException {
        Query query = entityManager.createQuery("SELECT e FROM Employee e");
        List<Employee> employees = query.getResultList();

        Employee[] employeeArray = new Employee[employees.size()];
        for (int i = 0; i < employeeArray.length; i++) {
            employeeArray[i] = employees.get(i);
        }
        return employeeArray;
    }

    public Nurse findNurseById(int id, EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT e FROM Nurse e WHERE e.personalnumber = :id", Nurse.class);
        query.setParameter("id", id);
        return (Nurse)query.getSingleResult();
    }
}
