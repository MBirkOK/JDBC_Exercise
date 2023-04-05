package infrastructure;

import domain.premises.Ward;
import jakarta.persistence.EntityManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class WardRepositoryImpl implements WardRepository {
    private DatabaseHandler databaseHandler = new DatabaseHandler();

    public WardRepositoryImpl() throws SQLException, ClassNotFoundException {
    }

    @Override
    public int safeWard(Ward ward, EntityManager entityManager) throws SQLException {
        String sql = "INSERT INTO tab_exercise_ward(id, description) VALUES (?,?)";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
        preparedStatement.setInt(1, ward.getId());
        preparedStatement.setString(2, ward.getDescription());

        try {
            preparedStatement.executeUpdate();
            return ward.getId();
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Optional<Ward> findWardById(int id, EntityManager entityManager) throws SQLException {
        String sql = "SELECT * FROM tab_exercise_ward WHERE id = ?";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next() == false) {
            //TODO dont return null
            return null;
        }
        return Optional.of(new Ward(resultSet.getInt("id"), resultSet.getString("description")));
    }

}
