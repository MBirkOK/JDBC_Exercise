package infrastructure;

import domain.premises.Ward;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;

import java.sql.SQLException;
import java.util.Optional;

public interface WardRepository {
    int safeWard(Ward ward, EntityManager entityManager) throws SQLException;

    Optional<Ward> findWardById(int id, EntityManager entityManager) throws SQLException;
}
