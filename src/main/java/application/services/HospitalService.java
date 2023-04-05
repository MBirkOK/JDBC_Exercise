package application.services;

import domain.Patient;
import domain.Treatment;
import domain.employment.Employee;
import domain.employment.Nurse;
import domain.premises.Room;
import domain.premises.Ward;
import infrastructure.DatabaseHandler;
import infrastructure.EmployeeRepositoryImpl;
import infrastructure.EmployeeRepositoryJpa;
import infrastructure.PatientRepositoryImpl;
import infrastructure.PatientRepositoryJpa;
import infrastructure.RoomRepositoryImpl;
import infrastructure.RoomRepositoryJpa;
import infrastructure.TreatmentRepositoryImpl;
import infrastructure.TreatmentRepositoryJpa;
import infrastructure.WardRepositoryImpl;
import infrastructure.WardRepositoryJpa;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class HospitalService {

    private DatabaseHandler databaseHandler = new DatabaseHandler();
    private RoomRepositoryImpl roomRepositoryImpl;
    private RoomRepositoryJpa roomRepositoryJpa;
    private PatientRepositoryImpl patientRepositoryImpl;
    private PatientRepositoryJpa patientRepositoryJpa;
    private EmployeeRepositoryImpl employeeRepositoryImpl;
    private EmployeeRepositoryJpa employeeRepositoryJpa;
    private WardRepositoryImpl wardRepositoryImpl;
    private WardRepositoryJpa wardRepositoryJpa;
    private TreatmentRepositoryImpl treatmentRepositoryImpl;
    private TreatmentRepositoryJpa treatmentRepositoryJpa;


    public HospitalService() throws SQLException, ClassNotFoundException {
        wardRepositoryImpl = new WardRepositoryImpl();
        roomRepositoryImpl = new RoomRepositoryImpl();
        patientRepositoryImpl = new PatientRepositoryImpl();
        employeeRepositoryImpl = new EmployeeRepositoryImpl();
        treatmentRepositoryImpl = new TreatmentRepositoryImpl();

        roomRepositoryJpa = new RoomRepositoryJpa();
        patientRepositoryJpa = new PatientRepositoryJpa();
        employeeRepositoryJpa = new EmployeeRepositoryJpa();
        wardRepositoryJpa = new WardRepositoryJpa();
        treatmentRepositoryJpa = new TreatmentRepositoryJpa();
    }

    public Patient createStay(String[] patientData, EntityManager entityManager) throws SQLException, IOException {
        //TODO findNurseById
        Employee employee = employeeRepositoryImpl.findEmployeeById(Integer.parseInt(patientData[4]), null).get();
        List<Patient> patientList = patientRepositoryImpl.getPatientsFromNurseId(employee.getPersonalnumber(), entityManager);
        Nurse nurse = new Nurse(employee.getPersonalnumber(), employee.getFirstName(), employee.getLastName(),
            employee.getBirthdate(), employee.getWard(), employee.getSalary());
        //TODO give Nurse all her/his patients
        Room patientRoom = roomRepositoryImpl.findRoomWithIdWithoutPatients(Integer.parseInt(patientData[3]), null);
        //TODO findRoomById
        Patient patient = new Patient(0, patientData[0], patientData[1], LocalDate.parse(patientData[2]),
            null, patientRoom, nurse, LocalDate.parse(patientData[5]), LocalDate.parse(patientData[6]), patientData[7]);
        patientRepositoryImpl.safePatient(patient, entityManager);
        return patientRepositoryImpl.findPatientById(patient.getId(), entityManager).get();
    }


    public Patient createStayJpa(String[] patientData, EntityManager entityManager) throws SQLException, IOException {
        entityManager.getTransaction().begin();
        //TODO findNurseById
        Nurse employee = employeeRepositoryJpa.findNurseById(Integer.parseInt(patientData[4]), entityManager);
        //TODO give Nurse all her/his patients
        Room patientRoom = roomRepositoryJpa.findRoomWithIdWithoutPatients(Integer.parseInt(patientData[3]), entityManager);
        //TODO findRoomById
        Patient patient = new Patient(0, patientData[0], patientData[1], LocalDate.parse(patientData[2]),
            null, patientRoom, employee, LocalDate.parse(patientData[5]), LocalDate.parse(patientData[6]), patientData[7]);
        patientRepositoryJpa.safePatient(patient, entityManager);
        entityManager.getTransaction().commit();
        return patientRepositoryJpa.findPatientById(patient.getId(), entityManager).get();
    }

    public Ward createWard(Ward ward) throws SQLException {
        int id = wardRepositoryImpl.safeWard(ward, null);
        return wardRepositoryImpl.findWardById(id, null).get();
    }

    public Ward createWardJpa(Ward ward) throws SQLException {
        int id = wardRepositoryJpa.safeWard(ward, null);
        return wardRepositoryJpa.findWardById(id, null).get();
    }

    public int createRoom(Room room) throws SQLException {
        return roomRepositoryImpl.safeRoom(room, null);
    }

    public int createRoomJpa(Room room, EntityManager entityManager) throws SQLException {
        return roomRepositoryJpa.safeRoom(room, entityManager);
    }

    public int createEmployee(Employee employee) throws SQLException {
        return employeeRepositoryImpl.safeEmployee(employee, null);
    }

    public int createEmployeeJpa(Employee employee, EntityManager entityManager) throws SQLException {
        return employeeRepositoryJpa.safeEmployee(employee, entityManager);
    }

    public Treatment createTreatment(Treatment treatment, EntityManager entityManager) throws SQLException, IOException {
        int id = treatmentRepositoryImpl.safeTreatment(treatment, entityManager);
        return treatmentRepositoryImpl.findTreatmentById(id, entityManager).get();
    }

    public Treatment createTreatmentJpa(Treatment treatment, EntityManager entityManager) throws SQLException, IOException {
        int id = treatmentRepositoryJpa.safeTreatment(treatment, entityManager);
        return treatmentRepositoryJpa.findTreatmentById(id, entityManager).get();
    }

    public Employee findEmployeeWithId(int id) {
        Optional<Employee> employee = employeeRepositoryImpl.findEmployeeById(id, null);
        return employee.orElseGet(() -> new Employee(0, "", "", LocalDate.now(), null, 0.0));
    }

    public Employee findEmployeeWithIdJpa(int id, EntityManager entityManager) {
        Optional<Employee> employee = employeeRepositoryJpa.findEmployeeById(id, entityManager);
        return employee.orElseGet(() -> new Employee(0, "", "", LocalDate.now(), null, 0.0));
    }

    public Patient[] getPatientsAtDate(LocalDate startDate, LocalDate endDate, EntityManager entityManager) throws SQLException, IOException {
        List<Patient> patients = patientRepositoryImpl.getPatientBetweenDates(startDate, endDate, entityManager);
        return toArray(patients);
    }

    public Patient[] getPatientsAtDateJpa(LocalDate startDate, LocalDate endDate, EntityManager entityManager) throws SQLException, IOException {
        List<Patient> patients = patientRepositoryJpa.getPatientBetweenDates(startDate, endDate, entityManager);
        return toArray(patients);
    }

    private Patient[] toArray(List<Patient> patients) {
        Patient[] patientArray = new Patient[patients.size()];
        for (int i = 0; i < patients.size(); i++) {
            patientArray[i] = patients.get(i);
        }
        return patientArray;
    }

    public int[] getUsedBeds(int wardId, LocalDate date) throws SQLException {
        int[] usedBeds = roomRepositoryImpl.calculateUsedBedsInWard(wardId, date, null);
        return usedBeds;
    }

    public int[] getUsedBedsJpa(int wardId, LocalDate date, EntityManager entityManager) throws SQLException {
        int[] usedBeds = roomRepositoryJpa.calculateUsedBedsInWard(wardId, date, entityManager);
        return usedBeds;
    }

    public List<String[]> getMedInTreatmentUsage(EntityManager entityManager) throws SQLException {
        return treatmentRepositoryImpl.calculateAmountOfMedicationInTreamtment(entityManager);
    }

    public List<String[]> getMedInTreatmentUsageJpa(EntityManager entityManager) throws SQLException {
        return treatmentRepositoryJpa.calculateAmountOfMedicationInTreamtment(entityManager);
    }

    public List<Integer> getGenderDivision(EntityManager entityManager) throws SQLException {
        return patientRepositoryImpl.calculateGenderDivision(entityManager);
    }

    public List<Integer> getGenderDivisionJpa(EntityManager entityManager) throws SQLException {
        return patientRepositoryJpa.calculateGenderDivision(entityManager);
    }
}
