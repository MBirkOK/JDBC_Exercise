package infrastructure;

import domain.premises.Ward;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WardRepository {
    private DatabaseHandler databaseHandler = new DatabaseHandler();

    public WardRepository() throws SQLException, ClassNotFoundException {
    }

    public Ward safeWard(Ward ward) throws SQLException {
        String sql = "INSERT INTO tab_exercise_ward(id, description) VALUES (?,?)";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
        preparedStatement.setInt(1, ward.getId());
        preparedStatement.setString(2, ward.getDescription());

        try {
            preparedStatement.executeUpdate();
            return ward;
        } catch (SQLException e) {
            throw e;
        }
    }

    public Ward findWardById(int id) throws SQLException {
        String sql = "SELECT * FROM tab_exercise_ward WHERE id = ?";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next() == false) {
            //TODO dont return null
            return null;
        }

        return new Ward(resultSet.getInt("id"), resultSet.getString("description"));
    }

}
