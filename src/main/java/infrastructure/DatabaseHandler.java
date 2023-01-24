package infrastructure;

import domain.Employee;
import domain.Inventory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DatabaseHandler {
    private Connection connection;

    protected final String URL = "jdbc:postgresql://localhost:5432/postgres?user=cqacdkfw&password=U4b4yfEAc8lrVgNst4YQ_799WBJ27wXn&ssl=false";

    public DatabaseHandler() throws SQLException, ClassNotFoundException {
        this.connection = establishConnection();
    }

    private Connection establishConnection() throws SQLException {
        try {
            Properties props = new Properties();
            props.setProperty("user", "cqacdkfw");
            props.setProperty("password", "U4b4yfEAc8lrVgNst4YQ_799WBJ27wXn");
            props.setProperty("ssl", "false");

            Connection connection = DriverManager.getConnection(URL, props);
            connection.setAutoCommit(false);
            return connection;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method gets an object either of type employee or of type inventory. Depens on of which type of class
     * the object is, the method to safe it in persistence differs.
     *
     * @param o
     * @return the original Object
     * @throws SQLException
     */
    public Object saveObject(Object o) throws SQLException {
        try {
            switch (o.getClass().toString()) {
                case "class domain.Employee":
                    String sql = "INSERT INTO tab_exercise_employee(pers_nr, first_name, last_name, birthday) VALUES  (?,?,?,?)";
                    PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
                    preparedStatement.setInt(1, ((Employee)o).getPersonalnumber());
                    preparedStatement.setString(2, ((Employee)o).getFirstName());
                    preparedStatement.setString(3, ((Employee)o).getLastName());
                    preparedStatement.setDate(4, Date.valueOf(((Employee)o).getBirthdate()));
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    return o;
                case "class domain.Inventory":
                    String inventorySql = "INSERT INTO tab_exercise_inventory(description, procurement, worth, employee_nr) VALUES  (?,?,?,?)";
                    PreparedStatement inventoryPreparedStatement = this.connection.prepareStatement(inventorySql);
                    inventoryPreparedStatement.setString(1, ((Inventory)o).getDescription());
                    inventoryPreparedStatement.setDate(2, Date.valueOf(((Inventory)o).getProcurement()));
                    inventoryPreparedStatement.setDouble(3, ((Inventory)o).getWorth());
                    inventoryPreparedStatement.setInt(4, ((Inventory)o).getEmployee().getPersonalnumber());
                    inventoryPreparedStatement.executeUpdate();
                    inventoryPreparedStatement.close();
                    return o;
            }
        } catch (Exception e) {
            System.out.println("Saving in Database failed");
            return 0;
        }
        System.out.println("Saving failed");
        return 0;
    }

    public boolean saveEmployeeList(List<Employee> employeeList) {
        try {
            for (Object o : employeeList) {
                Object result = this.saveObject(o);
            }
            this.connection.commit();
            return true;
        } catch (SQLException e) {
            if (this.connection != null) {
                try {
                    this.connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return false;
    }

    public boolean saveInventoryList(List<Inventory> inventoryList) {
        try {
            for (Inventory o : inventoryList) {
                this.saveObject(o);
            }
            this.connection.commit();
            return true;
        } catch (SQLException e) {
            if (this.connection != null) {
                try {
                    this.connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return false;
    }

    public List<Employee> findByFirstName(String name) throws SQLException {
        String findEmployee = "SELECT pers_nr, first_name, last_name, birthday from tab_exercise_employee where first_name like ? ORDER BY first_name, last_name";
        PreparedStatement preparedStatement = establishConnection().prepareStatement(findEmployee);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Employee> employeeList = new ArrayList<>();
        while (resultSet.next()) {
            employeeList.add(new Employee(resultSet.getInt(1), resultSet.getString(2),
                resultSet.getString(3), resultSet.getDate(4).toLocalDate()));
        }
        return employeeList;
    }

    public List<Inventory> findInventoryByName(String name) throws SQLException {
        String findInventory = "SELECT id, description, procurement, worth, pers_nr, first_name, last_name, birthday from tab_exercise_inventory, " +
            "tab_exercise_employee where description like ? AND tab_exercise_employee.pers_nr = tab_exercise_inventory.employee_nr order by first_name, last_name";
        PreparedStatement preparedStatement = establishConnection().prepareStatement(findInventory);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Inventory> inventories = new ArrayList<>();
        while (resultSet.next()) {
            Employee found = new Employee(resultSet.getInt(5), resultSet.getString(6), resultSet.getString(7), resultSet.getDate(8).toLocalDate());
            inventories.add(new Inventory(resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3).toLocalDate(),
                resultSet.getDouble(4), found));
        }
        return inventories;
    }

    public void wipeDatabase() throws SQLException {
        String wipeEmployee = "DELETE FROM tab_exercise_employee";
        String wipeInventory = "DELETE FROM tab_exercise_inventory";
        PreparedStatement inventoryStatement = establishConnection().prepareStatement(wipeInventory);
        inventoryStatement.execute();
        this.connection.commit();
        inventoryStatement.close();
        PreparedStatement employeeStatement = establishConnection().prepareStatement(wipeEmployee);
        employeeStatement.execute();
        this.connection.commit();
        employeeStatement.close();
    }

    public List<Employee> findAllEmployees() throws SQLException {
        String findEmployee = "SELECT pers_nr, first_name, last_name, birthday from tab_exercise_employee ORDER BY first_name, last_name";
        PreparedStatement preparedStatement = establishConnection().prepareStatement(findEmployee);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Employee> employeeList = new ArrayList<>();
        while (resultSet.next()) {
            employeeList.add(new Employee(resultSet.getInt(1), resultSet.getString(2),
                resultSet.getString(3), resultSet.getDate(4).toLocalDate()));
        }
        return employeeList;
    }

    public List<Inventory> findAllInventory() throws SQLException {
        String findInventory = "SELECT id, description, procurement, worth, pers_nr, first_name, last_name, birthday from tab_exercise_inventory, " +
            "tab_exercise_employee where tab_exercise_employee.pers_nr = tab_exercise_inventory.employee_nr order by first_name, last_name";
        PreparedStatement preparedStatement = establishConnection().prepareStatement(findInventory);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Inventory> inventories = new ArrayList<>();
        while (resultSet.next()) {
            Employee found = new Employee(resultSet.getInt(5), resultSet.getString(6), resultSet.getString(7), resultSet.getDate(8).toLocalDate());
            inventories.add(new Inventory(resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3).toLocalDate(),
                resultSet.getDouble(4), found));
        }
        return inventories;
    }
}
