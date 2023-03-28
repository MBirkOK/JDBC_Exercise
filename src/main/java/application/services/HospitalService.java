package application.services;

import domain.Patient;
import domain.Treatment;
import domain.employment.Employee;
import domain.employment.Nurse;
import domain.premises.Room;
import domain.premises.Ward;
import infrastructure.DatabaseHandler;
import infrastructure.EmployeeRepositoryImpl;
import infrastructure.PatientRepositoryImpl;
import infrastructure.RoomRepositoryImpl;
import infrastructure.TreatmentRepositoryImpl;
import infrastructure.WardRepositoryImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class HospitalService {

    private DatabaseHandler databaseHandler = new DatabaseHandler();
    private RoomRepositoryImpl roomRepositoryImpl;
    private PatientRepositoryImpl patientRepositoryImpl;
    private EmployeeRepositoryImpl employeeRepositoryImpl;
    private WardRepositoryImpl wardRepositoryImpl;
    private TreatmentRepositoryImpl treatmentRepositoryImpl;

    public HospitalService() throws SQLException, ClassNotFoundException {
        wardRepositoryImpl = new WardRepositoryImpl();
        roomRepositoryImpl = new RoomRepositoryImpl();
        patientRepositoryImpl = new PatientRepositoryImpl();
        employeeRepositoryImpl = new EmployeeRepositoryImpl();
        treatmentRepositoryImpl = new TreatmentRepositoryImpl();
    }

    public Patient createStay(String[] patientData) throws SQLException, IOException {
        //TODO findNurseById
        Employee employee = employeeRepositoryImpl.findEmployeeById(Integer.parseInt(patientData[4])).get();
        List<Patient> patientList = patientRepositoryImpl.getPatientsFromNurseId(employee.getPersonalnumber());
        Nurse nurse = new Nurse(employee.getPersonalnumber(), employee.getFirstName(), employee.getLastName(),
            employee.getBirthdate(), employee.getWard(), employee.getSalary(), patientList);
        //TODO give Nurse all her/his patients
        Room patientRoom = roomRepositoryImpl.findRoomWithIdWithoutPatients(Integer.parseInt(patientData[3]));
        //TODO findRoomById
        Patient patient = new Patient(0, patientData[0], patientData[1], LocalDate.parse(patientData[2]),
            null, patientRoom, nurse, LocalDate.parse(patientData[5]), LocalDate.parse(patientData[6]), patientData[7]);
        patientRepositoryImpl.safePatient(patient);
        return patientRepositoryImpl.findPatientById(patient.getId()).get();

    }

    public Ward createWard(Ward ward) throws SQLException {
        int id = wardRepositoryImpl.safeWard(ward);
        return wardRepositoryImpl.findWardById(id).get();
    }

    public int createRoom(Room room) throws SQLException {
        return roomRepositoryImpl.safeRoom(room);
    }

    public int createEmployee(Employee employee) throws SQLException {
        return employeeRepositoryImpl.safeEmployee(employee);
    }

    public Treatment createTreatment(Treatment treatment) throws SQLException, IOException {
        int id = treatmentRepositoryImpl.safeTreatment(treatment);
        return treatmentRepositoryImpl.findTreatmentById(id).get();
    }

    public Employee findEmployeeWithId(int id) {
        return employeeRepositoryImpl.findEmployeeById(id).get();
    }

    public Patient[] getPatientsAtDate(LocalDate startDate, LocalDate endDate) throws SQLException, IOException {
        List<Patient> patients = patientRepositoryImpl.getPatientBetweenDates(startDate, endDate);
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
        int[] usedBeds = roomRepositoryImpl.calculateUsedBedsInWard(wardId, date);
        return usedBeds;
    }

    public List<String[]> getMedInTreatmentUsage() throws SQLException {
        return treatmentRepositoryImpl.calculateAmountOfMedicationInTreamtment();
    }

    public List<Integer> getGenderDivision() throws SQLException {
        return patientRepositoryImpl.calculateGenderDivision();
    }
}
