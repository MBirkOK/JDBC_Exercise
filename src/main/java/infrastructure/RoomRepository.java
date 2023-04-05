package infrastructure;

import domain.premises.Room;
import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface RoomRepository {

    int safeRoom(Room room, EntityManager entityManager) throws SQLException;

    List<Room> findRoomsByWardId(int wardId, EntityManager entityManager) throws SQLException;

    List<Room> findAllRoomsWithOutPatients(EntityManager entityManager) throws SQLException;

    Room findRoomWithIdWithoutPatients(int id, EntityManager entityManager) throws SQLException, IOException;

    int[] calculateUsedBedsInWard(int wardId, LocalDate date, EntityManager entityManager) throws SQLException;

    int getAmountAllBedsFromStation(int wardId, EntityManager entityManager) throws SQLException;
}
