package infrastructure;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Properties;

public class DatabaseHandler {

    protected final String URL = "jdbc:postgresql://localhost:5432/postgres?user=cqacdkfw&password=U4b4yfEAc8lrVgNst4YQ_799WBJ27wXn&ssl=false";

    public DatabaseHandler() throws SQLException, ClassNotFoundException {
        establishConnection();
    }

    public Connection establishConnection() throws SQLException {
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

    public LocalDate convertDateToLocalDate(Date dateToConvert) {
        return dateToConvert.toLocalDate();
    }

    int getSizeResultSet(ResultSet set) throws SQLException {
        set.last();
        return set.getRow();
    }
}
