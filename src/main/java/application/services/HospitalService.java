package application.services;

import domain.Patient;
import domain.Treatment;
import domain.employment.Employee;
import domain.employment.Nurse;
import domain.premises.Room;
import domain.premises.Ward;
import infrastructure.DatabaseHandler;
import infrastructure.EmployeeRepository;
import infrastructure.PatientRepository;
import infrastructure.RoomRepository;
import infrastructure.TreatmentRepository;
import infrastructure.WardRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class HospitalService {

    private DatabaseHandler databaseHandler = new DatabaseHandler();
    private RoomRepository roomRepository;
    private PatientRepository patientRepository;
    private EmployeeRepository employeeRepository;
    private WardRepository wardRepository;
    private TreatmentRepository treatmentRepository;

    public HospitalService() throws SQLException, ClassNotFoundException {
        wardRepository = new WardRepository();
        roomRepository = new RoomRepository();
        patientRepository = new PatientRepository();
        employeeRepository = new EmployeeRepository();
        treatmentRepository = new TreatmentRepository();
    }

    public Patient createStay(String[] patientData) throws SQLException, IOException {

        //TODO findNurseById
        Employee employee = employeeRepository.findEmployeeById(Integer.parseInt(patientData[4]));
        List<Patient> patientList = patientRepository.getPatientsFromNurseId(employee.getPersonalnumber());
        Nurse nurse = new Nurse(employee.getPersonalnumber(), employee.getFirstName(), employee.getLastName(),
            employee.getBirthdate(), employee.getWard(), employee.getSalary(), patientList);
        //TODO give Nurse all her/his patients
        Room patientRoom = roomRepository.findRoomWithIdWithoutPatients(Integer.parseInt(patientData[3]));
        //TODO findRoomById
        Patient patient = new Patient(0, patientData[0], patientData[1], LocalDate.parse(patientData[2]),
            null, patientRoom, nurse, LocalDate.parse(patientData[5]), LocalDate.parse(patientData[6]));
        return patientRepository.safePatient(patient);
    }

    public Ward createWard(Ward ward) throws SQLException {
        return wardRepository.safeWard(ward);
    }

    public Room createRoom(Room room) throws SQLException {
        return roomRepository.safeRoom(room);
    }

    public Employee createEmployee(Employee employee) throws SQLException {
        return employeeRepository.safeEmployee(employee);
    }

    public Treatment createTreatment(Treatment treatment) throws SQLException {
        return treatmentRepository.safeTreatment(treatment);
    }
}
