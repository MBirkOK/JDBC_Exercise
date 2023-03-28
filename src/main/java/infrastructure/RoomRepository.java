package infrastructure;

import domain.premises.Room;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface RoomRepository {

    int safeRoom(Room room) throws SQLException;

    List<Room> findRoomsByWardId(int wardId) throws SQLException;

    List<Room> findAllRoomsWithOutPatients() throws SQLException;

    Room findRoomWithIdWithoutPatients(int id) throws SQLException, IOException;

    int[] calculateUsedBedsInWard(int wardId, LocalDate date) throws SQLException;

    int getAmountAllBedsFromStation(int wardId) throws SQLException;
}
