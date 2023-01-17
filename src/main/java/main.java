import domain.Employee;
import domain.Inventory;
import infrastructure.DatabaseHandler;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //List<Employee> employeeList = generateEmployees();
        //generateInventory(employeeList);

        List<Employee> findHim = Employee.findEmployeeByFirstName("Dieter");

        for(Employee employee: findHim){
            System.out.println(employee.getPersonalnumber()+", "+employee.getFirstName()+", "+employee.getLastName()+", "+employee.getBirthdate().toString());
        }

        List<Inventory> findIt = Inventory.findInventoryByName("Fee Fii");
        System.out.println("---------------");
        for(Inventory inventory: findIt){
            System.out.println(inventory.getDescription()+", "+inventory.getEmployee().getPersonalnumber()+", "+inventory.getEmployee().getLastName());
        }
    }



    private static List generateEmployees() throws SQLException, ClassNotFoundException {
        Random rand = new Random();
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(rand.nextInt(Integer.SIZE - 1), "Marius", "Birk", LocalDate.now()));
        employeeList.add(new Employee(rand.nextInt(Integer.SIZE - 1), "Klaus", "Kobinski", LocalDate.now()));
        employeeList.add(new Employee(rand.nextInt(Integer.SIZE - 1), "Jürgen", "Jürgenson", LocalDate.now()));
        employeeList.add(new Employee(rand.nextInt(Integer.SIZE - 1), "Dieter", "Schmidt", LocalDate.now()));

        DatabaseHandler databaseHandler = new DatabaseHandler();
        for (Employee employee : employeeList) {
            databaseHandler.safeObject(employee);
        }
        return employeeList;
    }

    private static void generateInventory(List<Employee> employeeList) throws SQLException, ClassNotFoundException{
        Random rand = new Random();
        List<Inventory> inventoryList = new ArrayList<>();
        inventoryList.add(new Inventory(rand.nextInt(Integer.SIZE-1), "Faa Foo", LocalDate.now(), 12.5, employeeList.get(0)));
        inventoryList.add(new Inventory(rand.nextInt(Integer.SIZE-1), "Fii Fee", LocalDate.now(), 1.0, employeeList.get(1)));
        inventoryList.add(new Inventory(rand.nextInt(Integer.SIZE-1), "Fuu Faa", LocalDate.now(), 64000, employeeList.get(0)));
        inventoryList.add(new Inventory(rand.nextInt(Integer.SIZE-1), "Fee Fii", LocalDate.now(), 1542.2, employeeList.get(2)));

        DatabaseHandler databaseHandler = new DatabaseHandler();
        for (Inventory inventory: inventoryList) {
            databaseHandler.safeObject(inventory);
        }
    }
}
