package application.services;

import application.print.Printer;
import domain.employment.Employee;
import domain.employment.MedicalOfficer;
import domain.employment.Nurse;
import domain.employment.Resident;
import domain.employment.SeniorOfficer;
import domain.employment.Specialist;
import domain.exceptions.EmployeeTypeNotDefinedException;
import domain.premises.Ward;
import infrastructure.DatabaseHandler;
import infrastructure.EmployeeRepositoryImpl;
import infrastructure.WardRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class PersonalService {

    private DatabaseHandler databaseHandler = new DatabaseHandler();
    private WardRepository wardRepository = new WardRepository();
    private EmployeeRepositoryImpl employeeRepositoryImpl = new EmployeeRepositoryImpl();

    public PersonalService() throws SQLException, ClassNotFoundException {
    }

    public int createPersonal(String firstName, String lastName, LocalDate birthday, Ward ward, Double salary, String typ) throws SQLException, EmployeeTypeNotDefinedException, IOException {
        switch (typ) {
            case "MedicalOfficer":
                MedicalOfficer medicalOfficer = new MedicalOfficer(0, firstName, lastName, birthday, ward, salary);
                if (doesWardExist(ward.getId())) {
                    return employeeRepositoryImpl.safeEmployee(medicalOfficer);
                } else {
                    System.out.println("Die Station existiert nicht. Bitte richtige Station angeben.");
                    return createPersonal(firstName, lastName, birthday, new Ward(Integer.parseInt(Printer.getWard())), salary, typ);
                }
            case "Nurse":
                Nurse nurse = new Nurse(0, firstName, lastName, birthday, ward, salary, null);
                if (doesWardExist(ward.getId())) {
                    return employeeRepositoryImpl.safeEmployee(nurse);
                } else {
                    System.out.println("Die Station existiert nicht. Bitte richtige Station angeben.");
                    return createPersonal(firstName, lastName, birthday, new Ward(Integer.parseInt(Printer.getWard())), salary, typ);
                }
            case "Resident":
                Resident resident = new Resident(0, firstName, lastName, birthday, ward, salary);
                if (doesWardExist(ward.getId())) {
                    return employeeRepositoryImpl.safeEmployee(resident);
                } else {
                    System.out.println("Die Station existiert nicht. Bitte richtige Station angeben.");
                    return createPersonal(firstName, lastName, birthday, new Ward(Integer.parseInt(Printer.getWard())), salary, typ);
                }
            case "SeniorOfficer":
                SeniorOfficer seniorOfficer = new SeniorOfficer(0, firstName, lastName, birthday, ward, salary, null);
                if (doesWardExist(ward.getId())) {
                    return employeeRepositoryImpl.safeEmployee(seniorOfficer);
                } else {
                    System.out.println("Die Station existiert nicht. Bitte richtige Station angeben.");
                    return createPersonal(firstName, lastName, birthday, new Ward(Integer.parseInt(Printer.getWard())), salary, typ);
                }
            case "Specialist":
                Specialist specialist = new Specialist(0, firstName, lastName, birthday, ward, salary);
                if (doesWardExist(ward.getId())) {
                    return employeeRepositoryImpl.safeEmployee(specialist);
                } else {
                    System.out.println("Die Station existiert nicht. Bitte richtige Station angeben.");
                    return createPersonal(firstName, lastName, birthday, new Ward(Integer.parseInt(Printer.getWard())), salary, typ);
                }
            default:
                System.out.println("Anstellung konnte nicht gefunden werden. Bitte nochmal einmal versuchen");
                return createPersonal(firstName, lastName, birthday, ward, salary, Printer.getType());
        }
    }

    private boolean doesWardExist(int wardId) throws SQLException {
        Ward existingWard = wardRepository.findWardById(wardId);
        //TODO dont do null checks
        if (existingWard != null) {
            return true;
        } else {
            return false;
        }
    }

    public Employee[] showPayments() throws SQLException {
        Employee[] employees = employeeRepositoryImpl.findAllEmployees();
        return employees;
    }
}
