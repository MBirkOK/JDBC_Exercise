package infrastructure;

import domain.Employee;
import domain.Inventory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileManagement {

    private final String DEFAULT_PATH_EMPLOYEE = "C:\\Users\\Marius.Birk\\Documents\\JDBC_Exercise\\exportEmployee.csv";
    private final String DEFAULT_PATH_INVENTORY = "C:\\Users\\Marius.Birk\\Documents\\JDBC_Exercise\\exportInventory.csv";


    private FileConverter fileConverter;

    public FileManagement() {
        this.fileConverter = new FileConverter();
    }

    public List<Employee> importEmployeeFromCsv() {
        String[][] employees = fileConverter.readCSV(DEFAULT_PATH_EMPLOYEE);
        List<Employee> employeeList = new ArrayList<>();
        for (String[] row : employees) {
            employeeList.add(new Employee(Integer.parseInt(row[0]), row[1], row[2], LocalDate.parse(row[3])));
        }
        return employeeList;
    }

    public List<Inventory> importInventoryFromCsv() {
        String[][] inventory = fileConverter.readCSV(DEFAULT_PATH_INVENTORY);
        List<Inventory> inventoryList = new ArrayList<>();
        for (String[] row : inventory) {
            List<Employee> employeeList = importEmployeeFromCsv();
            for (int i = 0; i < employeeList.size(); i++) {
                if (Integer.parseInt(row[4]) == employeeList.get(i).getPersonalnumber()) {
                    inventoryList.add(new Inventory(Integer.parseInt(row[0]), row[1], LocalDate.parse(row[2]),
                        Double.parseDouble(row[3]), employeeList.get(i)));
                }
            }
        }
        return inventoryList;
    }

    public void exportInventoryToCsv(List<Inventory> inventoryList) {
        String[][] array = new String[inventoryList.size()][];
        for (int i = 0; i < inventoryList.size(); i++) {
            Inventory inventory = inventoryList.get(i);
            String[] innervalue = new String[5];
            innervalue[0] = String.valueOf(inventory.getId());
            innervalue[1] = inventory.getDescription();
            innervalue[2] = inventory.getProcurement().toString();
            innervalue[3] = String.valueOf(inventory.getWorth());
            innervalue[4] = String.valueOf(inventory.getEmployee().getPersonalnumber());
            array[i] = innervalue;
        }
        fileConverter.writeCSV(DEFAULT_PATH_INVENTORY, array);
    }

    public void exportEmployeeToCsv(List<Employee> employeeList) {
        String[][] array = new String[employeeList.size()][];
        for (int i = 0; i < employeeList.size(); i++) {
            Employee employee = employeeList.get(i);
            String[] innervalue = new String[4];
            innervalue[0] = String.valueOf(employee.getPersonalnumber());
            innervalue[1] = employee.getFirstName();
            innervalue[2] = employee.getLastName();
            innervalue[3] = employee.getBirthdate().toString();
            array[i] = innervalue;
        }
        fileConverter.writeCSV(DEFAULT_PATH_EMPLOYEE, array);
    }
}
