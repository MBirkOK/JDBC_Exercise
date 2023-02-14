package application;

import application.print.Printer;
import application.services.HospitalService;
import application.services.PersonalService;
import domain.Patient;
import domain.Treatment;
import domain.employment.Employee;
import domain.employment.MedicalOfficer;
import domain.employment.Nurse;
import domain.employment.SeniorOfficer;
import domain.exceptions.EmployeeTypeNotDefinedException;
import domain.premises.Room;
import domain.premises.Ward;
import infrastructure.EmployeeRepository;
import infrastructure.RoomRepository;
import infrastructure.WardRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class main {

    public static PersonalService personalService;
    public static HospitalService hospitalService;

    public main() throws SQLException, ClassNotFoundException {
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, EmployeeTypeNotDefinedException {
        personalService = new PersonalService();
        hospitalService = new HospitalService();
        //TODO generate sample data
        generateSampleData();

        //TODO Hauptmenü basteln

        String input = "";
        while (!input.equals("ende")) {
            Printer.printTable();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            input = reader.readLine();

            if (input.equals("create")) {
                String[] patientData = Printer.getPatientData();
                hospitalService.createStay(patientData);
            } else if (input.equals("personal")) {
                String[] personalData = Printer.getPersonalData();
                personalService.createPersonal(personalData[0], personalData[1], LocalDate.parse(personalData[2]), new Ward(Integer.parseInt(personalData[3])), Double.valueOf(personalData[4]), personalData[5]);
            }
        }
    }
    //TODO jeder Aufenthalt hat ein Treatment mit Medi
    //TODO Personal kann erstellt werden
    //TODO Abfrage der Gehälter
    //TODO Abfrage der Patienten zu Zeitpunkt
    //TODO Bettenauslastung zu Zeitpunkt
    //TODO Welche Medi wie oft eingesetzt wird
    //TODO Wieviele Männers oder Frauens


    private static void generateSampleData() throws SQLException, IOException {

        Patient patient = new Patient(1, "Marius", "Müller", LocalDate.now(), null, null, null, LocalDate.now(), LocalDate.now());
        List<Patient> patients = new ArrayList<>();
        patients.add(patient);

        Room room = new Room(1, 12, patients);
        List<Room> rooms = new ArrayList<>();
        rooms.add(room);
        patient.setRoom(room);

        MedicalOfficer medicalOfficer = new MedicalOfficer(1, "Olaf", "Schulz", LocalDate.now(), null, 12000.0);
        Ward ward = new Ward(1, "Teststation");
        room.setWard(ward);
        medicalOfficer.setWard(ward);

        Nurse nurse = new Nurse(1, "Test", "Test", LocalDate.now(), ward, 1100.2, patients);
        List<Employee> employees = new ArrayList<>();
        patient.setNurse(nurse);
        employees.add(nurse);

        SeniorOfficer seniorOfficer = new SeniorOfficer(2, "Christian", "Lindner", LocalDate.now(), ward, 11100.00, null);

        Treatment treatment = new Treatment(1, "Ibuprofen 200", seniorOfficer, patient);
        seniorOfficer.setTreatment(treatment);

        hospitalService.createWard(ward);
        hospitalService.createRoom(room);
        hospitalService.createEmployee(nurse);

        String[] patientData = new String[7];
        patientData[0] = patient.getFirstName();
        patientData[1] = patient.getLastName();
        patientData[2] = patient.getBirthdate().toString();
        patientData[3] = String.valueOf(patient.getRoom().getId());
        patientData[4] = String.valueOf(patient.getNurse().getPersonalnumber());
        patientData[5] = patient.getStartStay().toString();
        patientData[6] = patient.getEndStay().toString();
        hospitalService.createStay(patientData);
        hospitalService.createEmployee(seniorOfficer);
        hospitalService.createTreatment(treatment);
    }
}
