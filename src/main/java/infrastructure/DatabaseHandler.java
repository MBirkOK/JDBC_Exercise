package infrastructure;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseHandler {

    protected final String URL = "jdbc:postgresql://localhost:5432/postgres?user=cqacdkfw&password=U4b4yfEAc8lrVgNst4YQ_799WBJ27wXn&ssl=false";

    public DatabaseHandler() throws SQLException, ClassNotFoundException {
        establishConnection();
    }

    private Connection establishConnection() throws SQLException {
        try {
            Properties props = new Properties();
            props.setProperty("user", "cqacdkfw");
            props.setProperty("password", "U4b4yfEAc8lrVgNst4YQ_799WBJ27wXn");
            props.setProperty("ssl", "false");

            Connection connection = DriverManager.getConnection(URL, props);
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
    public Object safeObject(Object o) throws SQLException {
        try {
            switch (o.getClass().toString()) {
                case "class domain.Employee":
                    String sql = "INSERT INTO tab_exercise_employee(pers_nr, first_name, last_name, birthday) VALUES  (?,?,?,?)";
                    PreparedStatement preparedStatement = establishConnection().prepareStatement(sql);
                    preparedStatement.setInt(1, ((Employee) o).getPersonalnumber());
                    preparedStatement.setString(2, ((Employee)o).getFirstName());
                    preparedStatement.setString(3, ((Employee)o).getLastName());
                    preparedStatement.setDate(4, Date.valueOf(((Employee)o).getBirthdate()));
                    preparedStatement.executeUpdate();
                    return o;
                case "class domain.Inventory":
                    String inventorySql = "INSERT INTO tab_exercise_inventory(description, procurement, worth, employee_nr) VALUES  (?,?,?,?)";
                    PreparedStatement inventoryPreparedStatement = establishConnection().prepareStatement(inventorySql);
                    inventoryPreparedStatement.setString(1, ((Inventory)o).getDescription());
                    inventoryPreparedStatement.setDate(2, Date.valueOf(((Inventory)o).getProcurement()));
                    inventoryPreparedStatement.setDouble(3, ((Inventory)o).getWorth());
                    inventoryPreparedStatement.setInt(4, ((Inventory)o).getEmployee().getPersonalnumber());
                    inventoryPreparedStatement.executeUpdate();
                    return o;
            }
        } catch (Exception e) {
            System.out.println("Saving in Database failed");
            return 0;
        }
        System.out.println("Saving failed");
        return 0;
    }

}
