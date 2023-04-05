package infrastructure;

import domain.premises.Ward;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.sql.SQLException;
import java.util.Optional;

public class WardRepositoryJpa implements WardRepository {
    @Override
    public int safeWard(Ward ward, EntityManager entityManager) throws SQLException {
        entityManager.getTransaction().begin();
        entityManager.persist(ward);
        entityManager.getTransaction().commit();
        return ward.getId();
    }

    @Override
    public Optional<Ward> findWardById(int id, EntityManager entityManager) throws SQLException {
        Ward ward = entityManager.find(Ward.class, id);
        return Optional.ofNullable(ward);
    }
}
