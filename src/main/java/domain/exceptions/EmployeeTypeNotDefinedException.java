package domain.exceptions;

public class EmployeeTypeNotDefinedException extends Throwable {

    public EmployeeTypeNotDefinedException() {
        super("Employeetype must be defined");
    }
}
