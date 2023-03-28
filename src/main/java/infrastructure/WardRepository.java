package infrastructure;

import domain.premises.Ward;

import java.sql.SQLException;
import java.util.Optional;

public interface WardRepository {
    int safeWard(Ward ward) throws SQLException;

    Optional<Ward> findWardById(int id) throws SQLException;
}
