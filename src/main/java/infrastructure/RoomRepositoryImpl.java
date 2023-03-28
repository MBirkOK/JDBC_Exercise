package infrastructure;

import application.print.Printer;
import domain.premises.Room;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomRepositoryImpl implements RoomJpaRepository {
    private DatabaseHandler databaseHandler = new DatabaseHandler();

    public RoomRepositoryImpl() throws SQLException, ClassNotFoundException {
    }

    public int safeRoom(Room room) throws SQLException {
        String sql = "INSERT INTO tab_exercise_room(id, amount_beds, ward_id) VALUES (?,?,?)";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
        preparedStatement.setInt(1, room.getId());
        preparedStatement.setInt(2, room.getAmound_beds());
        preparedStatement.setInt(3, room.getWard().getId());

        try {
            preparedStatement.executeUpdate();
            return room.getId();
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<Room> findRoomsByWardId(int wardId) throws SQLException {
        String sql = "SELECT id, amount_beds, ward_id FROM tab_exercise_room WHERE ward_id = ?";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
        preparedStatement.setInt(1, wardId);
        ResultSet resultSet = preparedStatement.executeQuery();
        int lastRow = databaseHandler.getSizeResultSet(resultSet);
        //TODO Add Ward to Room for Return
        List<Room> rooms = new ArrayList<>();
        for (int i = 0; i < lastRow; i++) {
            rooms.add(new Room(resultSet.getInt("id"), resultSet.getInt("amount_beds")));
        }
        return rooms;
    }


    public List<Room> findAllRoomsWithOutPatients() throws SQLException {
        String sql = "SELECT id, amount_beds, ward_id FROM tab_exercise_room";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Room> rooms = new ArrayList<>();
        while (resultSet.next()) {
            Room room = new Room(resultSet.getInt("id"), resultSet.getInt("amount_beds"));
            rooms.add(room);
        }
        return rooms;
    }

    public Room findRoomWithIdWithoutPatients(int id) throws SQLException, IOException {
        String sql = "SELECT id, amount_beds, ward_id FROM tab_exercise_room WHERE id = ?";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next() == false) {
            System.out.println("Raum nicht gefunden, bitte existierenden Raum angeben!");
            return findRoomWithIdWithoutPatients(Integer.parseInt(Printer.getRoom()));
        }
        return new Room(resultSet.getInt("id"), resultSet.getInt("amount_beds"), null);
    }

    public int[] calculateUsedBedsInWard(int wardId, LocalDate date) throws SQLException {
        String sql = "SELECT (SELECT SUM(amount_beds) " +
            "FROM tab_exercise_room " +
            "WHERE ward_id = ?) - COUNT(patient)" +
            "FROM tab_exercise_room room " +
            "LEFT OUTER JOIN tab_exercise_patient patient ON room.id = patient.room_id " +
            "WHERE ? BETWEEN patient.stay_start AND stay_end AND ward_id = ?";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
        preparedStatement.setInt(1, wardId);
        preparedStatement.setDate(2, Date.valueOf(date));
        preparedStatement.setInt(3, wardId);
        ResultSet resultSet = preparedStatement.executeQuery();
        int[] result = new int[2];
        resultSet.next();
        result[0] = resultSet.getInt(1);
        result[1] = getAmountAllBedsFromStation(wardId);

        return result;
    }

    public int getAmountAllBedsFromStation(int wardId) throws SQLException {
        String sql = "SELECT SUM(amount_beds) FROM tab_exercise_room WHERE ward_id = ?";
        PreparedStatement preparedStatement = databaseHandler.establishConnection().prepareStatement(sql);
        preparedStatement.setInt(1, wardId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int result = resultSet.getInt(1);
        return result;
    }
}
