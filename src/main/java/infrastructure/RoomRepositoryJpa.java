package infrastructure;

import domain.premises.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class RoomRepositoryJpa implements RoomRepository {
    @Override
    public int safeRoom(Room room, EntityManager entityManager) throws SQLException {
        entityManager.getTransaction().begin();
        entityManager.persist(room);
        entityManager.getTransaction().commit();
        return room.getId();
    }

    @Override
    public List<Room> findRoomsByWardId(int wardId, EntityManager entityManager) throws SQLException {
        Query query = entityManager.createQuery("SELECT r FROM Room r WHERE r.ward.id = :id");
        query.setParameter("id", wardId);
        return query.getResultList();
    }

    @Override
    public List<Room> findAllRoomsWithOutPatients(EntityManager entityManager) throws SQLException {
        Query query = entityManager.createQuery("SELECT r FROM Room r WHERE r.patientsInRoom.size = 0");
        return query.getResultList();
    }

    @Override
    public Room findRoomWithIdWithoutPatients(int id, EntityManager entityManager) throws SQLException, IOException {
        Query query = entityManager.createQuery("SELECT r FROM Room r WHERE r.id = :id");
        query.setParameter("id", id);
        return (Room)query.getResultList().get(0);
    }

    @Override
    public int[] calculateUsedBedsInWard(int wardId, LocalDate date, EntityManager entityManager) throws SQLException {
        Query sumQuery = entityManager.createQuery("SELECT SUM(r.amound_beds) FROM Room r WHERE r.ward.id = :ward");
        sumQuery.setParameter("ward", wardId);
        List resultList = sumQuery.getResultList();
        int sum = Integer.parseInt(String.valueOf(resultList.get(0)));
        Query query = entityManager.createQuery("SELECT COUNT(p) FROM Room r " +
            "LEFT OUTER JOIN Patient p ON r.id = p.laysIn.id WHERE :date " +
            "BETWEEN p.startStay AND p.endStay AND r.ward.id = :id");
        query.setParameter("id", wardId);
        query.setParameter("date", date);
        resultList = query.getResultList();
        int[] result = new int[2];
        if (Integer.parseInt(String.valueOf(resultList.get(0))) >= 1) {
            result[0] = sum - Integer.parseInt(String.valueOf(resultList.get(0)));
            result[1] = getAmountAllBedsFromStation(wardId, entityManager);
        } else {
            result[0] = getAmountAllBedsFromStation(wardId, entityManager);
            result[1] = getAmountAllBedsFromStation(wardId, entityManager);
        }
        return result;
    }

    @Override
    public int getAmountAllBedsFromStation(int wardId, EntityManager entityManager) throws SQLException {
        Query query = entityManager.createQuery("SELECT SUM(r.amound_beds) FROM Room r WHERE r.ward.id = :wardId");
        query.setParameter("wardId", wardId);
        List resultList = query.getResultList();
        int result = Integer.parseInt(String.valueOf(resultList.get(0)));
        return result;
    }
}
