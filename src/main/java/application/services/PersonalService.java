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
import infrastructure.EmployeeRepositoryJpa;
import infrastructure.WardRepositoryImpl;
import infrastructure.WardRepositoryJpa;
import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PersonalService {

    private WardRepositoryImpl wardRepositoryImpl;
    private WardRepositoryJpa wardRepositoryJpa;
    private EmployeeRepositoryImpl employeeRepositoryImpl;
    private EmployeeRepositoryJpa employeeRepositoryJpa;

    public PersonalService() throws SQLException, ClassNotFoundException {
        wardRepositoryImpl = new WardRepositoryImpl();
        employeeRepositoryImpl = new EmployeeRepositoryImpl();

        wardRepositoryJpa = new WardRepositoryJpa();
        employeeRepositoryJpa = new EmployeeRepositoryJpa();
    }

    public int createPersonal(String firstName, String lastName, LocalDate birthday, Ward ward, Double salary, String typ) throws SQLException, EmployeeTypeNotDefinedException, IOException {
        switch (typ) {
            case "MedicalOfficer":
                MedicalOfficer medicalOfficer = new MedicalOfficer(0, firstName, lastName, birthday, ward, salary);
                if (doesWardExist(ward.getId())) {
                    return employeeRepositoryImpl.safeEmployee(medicalOfficer, null);
                } else {
                    System.out.println("Die Station existiert nicht. Bitte richtige Station angeben.");
                    return createPersonal(firstName, lastName, birthday, new Ward(Integer.parseInt(Printer.getWard())), salary, typ);
                }
            case "Nurse":
                Nurse nurse = new Nurse(0, firstName, lastName, birthday, ward, salary);
                if (doesWardExist(ward.getId())) {
                    return employeeRepositoryImpl.safeEmployee(nurse, null);
                } else {
                    System.out.println("Die Station existiert nicht. Bitte richtige Station angeben.");
                    return createPersonal(firstName, lastName, birthday, new Ward(Integer.parseInt(Printer.getWard())), salary, typ);
                }
            case "Resident":
                Resident resident = new Resident(0, firstName, lastName, birthday, ward, salary);
                if (doesWardExist(ward.getId())) {
                    return employeeRepositoryImpl.safeEmployee(resident, null);
                } else {
                    System.out.println("Die Station existiert nicht. Bitte richtige Station angeben.");
                    return createPersonal(firstName, lastName, birthday, new Ward(Integer.parseInt(Printer.getWard())), salary, typ);
                }
            case "SeniorOfficer":
                SeniorOfficer seniorOfficer = new SeniorOfficer(0, firstName, lastName, birthday, ward, salary, null);
                if (doesWardExist(ward.getId())) {
                    return employeeRepositoryImpl.safeEmployee(seniorOfficer, null);
                } else {
                    System.out.println("Die Station existiert nicht. Bitte richtige Station angeben.");
                    return createPersonal(firstName, lastName, birthday, new Ward(Integer.parseInt(Printer.getWard())), salary, typ);
                }
            case "Specialist":
                Specialist specialist = new Specialist(0, firstName, lastName, birthday, ward, salary);
                if (doesWardExist(ward.getId())) {
                    return employeeRepositoryImpl.safeEmployee(specialist, null);
                } else {
                    System.out.println("Die Station existiert nicht. Bitte richtige Station angeben.");
                    return createPersonal(firstName, lastName, birthday, new Ward(Integer.parseInt(Printer.getWard())), salary, typ);
                }
            default:
                System.out.println("Anstellung konnte nicht gefunden werden. Bitte nochmal einmal versuchen");
                return createPersonal(firstName, lastName, birthday, ward, salary, Printer.getType());
        }
    }

    public int createPersonalJpa(String firstName, String lastName, LocalDate birthday, Ward ward, Double salary, String typ, EntityManager entityManager) throws SQLException, EmployeeTypeNotDefinedException, IOException {
        Employee[] employeeList = employeeRepositoryJpa.findAllEmployees(entityManager);
        int id = findHighesId(employeeList);
        switch (typ) {
            case "MedicalOfficer":
                MedicalOfficer medicalOfficer = new MedicalOfficer(id, firstName, lastName, birthday, ward, salary);
                if (doesWardExist(ward.getId())) {
                    return employeeRepositoryJpa.safeEmployee(medicalOfficer, entityManager);
                } else {
                    System.out.println("Die Station existiert nicht. Bitte richtige Station angeben.");
                    return createPersonal(firstName, lastName, birthday, new Ward(Integer.parseInt(Printer.getWard())), salary, typ);
                }
            case "Nurse":
                Nurse nurse = new Nurse(id, firstName, lastName, birthday, ward, salary);
                if (doesWardExist(ward.getId())) {
                    return employeeRepositoryJpa.safeEmployee(nurse, entityManager);
                } else {
                    System.out.println("Die Station existiert nicht. Bitte richtige Station angeben.");
                    return createPersonal(firstName, lastName, birthday, new Ward(Integer.parseInt(Printer.getWard())), salary, typ);
                }
            case "Resident":
                Resident resident = new Resident(id, firstName, lastName, birthday, ward, salary);
                if (doesWardExist(ward.getId())) {
                    return employeeRepositoryJpa.safeEmployee(resident, entityManager);
                } else {
                    System.out.println("Die Station existiert nicht. Bitte richtige Station angeben.");
                    return createPersonal(firstName, lastName, birthday, new Ward(Integer.parseInt(Printer.getWard())), salary, typ);
                }
            case "SeniorOfficer":
                SeniorOfficer seniorOfficer = new SeniorOfficer(id, firstName, lastName, birthday, ward, salary, null);
                if (doesWardExist(ward.getId())) {
                    return employeeRepositoryJpa.safeEmployee(seniorOfficer, entityManager);
                } else {
                    System.out.println("Die Station existiert nicht. Bitte richtige Station angeben.");
                    return createPersonal(firstName, lastName, birthday, new Ward(Integer.parseInt(Printer.getWard())), salary, typ);
                }
            case "Specialist":
                Specialist specialist = new Specialist(id, firstName, lastName, birthday, ward, salary);
                if (doesWardExist(ward.getId())) {
                    return employeeRepositoryJpa.safeEmployee(specialist, entityManager);
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
        Ward existingWard = wardRepositoryImpl.findWardById(wardId, null).get();
        //TODO dont do null checks
        if (existingWard != null) {
            return true;
        } else {
            return false;
        }
    }

    private boolean doesWardExistJpa(int wardId, EntityManager entityManager) throws SQLException {
        Ward existingWard = wardRepositoryJpa.findWardById(wardId, entityManager).get();
        //TODO dont do null checks
        if (existingWard != null) {
            return true;
        } else {
            return false;
        }
    }

    public Employee[] showPayments() throws SQLException {
        Employee[] employees = employeeRepositoryImpl.findAllEmployees(null);
        return employees;
    }

    public Employee[] showPaymentsJpa(EntityManager entityManager) throws SQLException {
        Employee[] employees = employeeRepositoryJpa.findAllEmployees(entityManager);
        return employees;
    }

    private int findHighesId(Employee[] employees) {
        int highest = 0;
        for(Employee employee: employees){
            if(employee.getPersonalnumber()>highest){
                highest = employee.getPersonalnumber();
            }
        }
        return highest+1;
    }
}
