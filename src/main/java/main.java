import domain.Employee;
import domain.Inventory;
import infrastructure.DatabaseHandler;
import infrastructure.FileManagement;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        FileManagement fileManagement = new FileManagement();

        databaseHandler.wipeDatabase();

        List<Employee> employeeList = generateEmployees();
        generateInventory(employeeList, databaseHandler);

        fileManagement.exportEmployeeToCsv(databaseHandler.findAllEmployees());
        fileManagement.exportInventoryToCsv(databaseHandler.findAllInventory());
        databaseHandler.wipeDatabase();

        try {
            List<Employee> employees = fileManagement.importEmployeeFromCsv();
            databaseHandler.saveEmployeeList(employees);

            List<Inventory> inventoryList = fileManagement.importInventoryFromCsv();
            databaseHandler.saveInventoryList(inventoryList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static List<Employee> generateEmployees() throws SQLException, ClassNotFoundException {
        Random rand = new Random();
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(rand.nextInt(Integer.SIZE - 1), "Marius", "Birk", LocalDate.now()));
        employeeList.add(new Employee(rand.nextInt(Integer.SIZE - 1), "Klaus", "Kobinski", LocalDate.now()));
        employeeList.add(new Employee(rand.nextInt(Integer.SIZE - 1), "Jürgen", "Jürgenson", LocalDate.now()));
        employeeList.add(new Employee(rand.nextInt(Integer.SIZE - 1), "Dieter", "Schmidt", LocalDate.now()));

        DatabaseHandler databaseHandler = new DatabaseHandler();
        databaseHandler.saveEmployeeList(employeeList);

        return employeeList;
    }

    private static void generateInventory(List<Employee> employeeList, DatabaseHandler handler) throws SQLException, ClassNotFoundException {
        Random rand = new Random();
        List<Inventory> inventoryList = new ArrayList<>();
        inventoryList.add(new Inventory(rand.nextInt(Integer.SIZE - 1), "Faa Foo", LocalDate.now(), 12.5, employeeList.get(0)));
        inventoryList.add(new Inventory(rand.nextInt(Integer.SIZE - 1), "Fii Fee", LocalDate.now(), 1.0, employeeList.get(1)));
        inventoryList.add(new Inventory(rand.nextInt(Integer.SIZE - 1), "Fuu Faa", LocalDate.now(), 64000, employeeList.get(0)));
        inventoryList.add(new Inventory(rand.nextInt(Integer.SIZE - 1), "Fee Fii", LocalDate.now(), 1542.2, employeeList.get(2)));

        handler.saveInventoryList(inventoryList);
    }
}
