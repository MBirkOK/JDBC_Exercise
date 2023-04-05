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
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

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

    public static EntityManager entityManager;

    public main() throws SQLException, ClassNotFoundException {
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, EmployeeTypeNotDefinedException {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("postgres");
        entityManager = entityManagerFactory.createEntityManager();
        personalService = new PersonalService();
        hospitalService = new HospitalService();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welches Repository soll genutzt werden? jdbc/jpa");
        String input = reader.readLine().toLowerCase();

        if (hospitalService.findEmployeeWithId(1).getPersonalnumber() == 0) {
            generateSampleData();
        }
        while (!input.equals("jdbc") || !input.equals("jpa")) {
            if (input.equals("jdbc")) {
                useJdbcRepository();
            }
            if (input.equals("jpa")) {
                useJpaRespository();
            }
            System.out.println("Welches Repository soll genutzt werden? jdbc/jpa");
            input = reader.readLine().toLowerCase();
        }
    }

    private static void useJpaRespository() throws IOException, SQLException, EmployeeTypeNotDefinedException {
        String input = "";
        while (!input.equals("ende")) {
            Printer.printTable();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            input = reader.readLine();
            input.toLowerCase();
            if (input.equals("create")) {
                String[] patientData = Printer.getPatientData();
                Patient patient = hospitalService.createStayJpa(patientData, entityManager);
                System.out.println("Bitte gib die Behandlungsart ein:");
                String treatmentString = reader.readLine();
                System.out.println("Welcher Oberarzt ist zuständig? (ID)");
                Employee officer = hospitalService.findEmployeeWithIdJpa(Integer.parseInt(reader.readLine()), entityManager);
                SeniorOfficer seniorOfficer = new SeniorOfficer(officer);
                Treatment treatment = new Treatment(treatmentString, seniorOfficer, patient);
                seniorOfficer.setTreatment(treatment);
                hospitalService.createTreatmentJpa(treatment, entityManager);
            } else if (input.equals("personal")) {
                String[] personalData = Printer.getPersonalData();
                personalService.createPersonalJpa(personalData[0], personalData[1], LocalDate.parse(personalData[2]),
                    new Ward(Integer.parseInt(personalData[3])), Double.valueOf(personalData[4]), personalData[5], entityManager);
            } else if (input.equals("payment")) {
                Printer.printPaymentTable(personalService.showPaymentsJpa(entityManager));
            } else if (input.equals("patients")) {
                LocalDate startDate = Printer.getStartDate();
                LocalDate endDate = Printer.getEndDate();
                Printer.printPatientTable(hospitalService.getPatientsAtDateJpa(startDate, endDate, entityManager));
            } else if (input.equals("beds")) {
                Printer.printBedUsage(hospitalService.getUsedBedsJpa(Integer.parseInt(Printer.getWard()), Printer.getDate(), entityManager));
            } else if (input.equals("meds")) {
                Printer.printMedUsage(hospitalService.getMedInTreatmentUsageJpa(entityManager));
            } else if (input.equals("genders")) {
                Printer.printGenderTable(hospitalService.getGenderDivisionJpa(entityManager));
            }
        }
    }

    private static void useJdbcRepository() throws IOException, SQLException, EmployeeTypeNotDefinedException {
        String input = "";
        while (!input.equals("ende")) {
            Printer.printTable();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            input = reader.readLine();
            input.toLowerCase();
            if (input.equals("create")) {
                String[] patientData = Printer.getPatientData();
                Patient patient = hospitalService.createStay(patientData, entityManager);
                System.out.println("Bitte gib die Behandlungsart ein:");
                String treatmentString = reader.readLine();
                System.out.println("Welcher Oberarzt ist zuständig? (ID)");
                Employee officer = hospitalService.findEmployeeWithId(Integer.parseInt(reader.readLine()));
                SeniorOfficer seniorOfficer = new SeniorOfficer(officer);
                Treatment treatment = new Treatment(treatmentString, seniorOfficer, patient);
                seniorOfficer.setTreatment(treatment);
                hospitalService.createTreatment(treatment, entityManager);
            } else if (input.equals("personal")) {
                String[] personalData = Printer.getPersonalData();
                personalService.createPersonal(personalData[0], personalData[1], LocalDate.parse(personalData[2]),
                    new Ward(Integer.parseInt(personalData[3])), Double.valueOf(personalData[4]), personalData[5]);
            } else if (input.equals("payment")) {
                Printer.printPaymentTable(personalService.showPayments());
            } else if (input.equals("patients")) {
                LocalDate startDate = Printer.getStartDate();
                LocalDate endDate = Printer.getEndDate();
                Printer.printPatientTable(hospitalService.getPatientsAtDate(startDate, endDate, entityManager));
            } else if (input.equals("beds")) {
                Printer.printBedUsage(hospitalService.getUsedBeds(Integer.parseInt(Printer.getWard()), Printer.getDate()));
            } else if (input.equals("meds")) {
                Printer.printMedUsage(hospitalService.getMedInTreatmentUsage(entityManager));
            } else if (input.equals("genders")) {
                Printer.printGenderTable(hospitalService.getGenderDivision(entityManager));
            }
        }
    }


    private static void generateSampleData() throws SQLException, IOException {
        Patient patient = new Patient(1, "Marius", "Müller", LocalDate.now(), null, null, null, LocalDate.now(), LocalDate.now(), "M");
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

        Nurse nurse = new Nurse(1, "Test", "Test", LocalDate.now(), ward, 1100.2);

        hospitalService.createWard(ward);
        hospitalService.createRoom(room);
        hospitalService.createEmployee(nurse);

        List<Employee> employees = new ArrayList<>();
        patient.setNurse(nurse);
        employees.add(nurse);

        SeniorOfficer seniorOfficer = new SeniorOfficer(2, "Christian", "Lindner", LocalDate.now(), ward, 11100.00, null);

        Treatment treatment = new Treatment(1, "Ibuprofen 200", seniorOfficer, patient);
        seniorOfficer.setTreatment(treatment);


        String[] patientData = new String[8];
        patientData[0] = patient.getFirstName();
        patientData[1] = patient.getLastName();
        patientData[2] = patient.getBirthdate().toString();
        patientData[3] = String.valueOf(patient.getRoom().getId());
        patientData[4] = String.valueOf(patient.getNurse().getPersonalnumber());
        patientData[5] = patient.getStartStay().toString();
        patientData[6] = patient.getEndStay().toString();
        patientData[7] = patient.getGender();
        hospitalService.createStay(patientData, entityManager);
        hospitalService.createEmployee(seniorOfficer);
        hospitalService.createTreatment(treatment, entityManager);
    }

}
