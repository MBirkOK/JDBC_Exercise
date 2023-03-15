package infrastructure;

import domain.Expedition;
import domain.Participant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ExpeditionRepositoryImpl implements ExpeditionRepository {

    private EntityManager entityManager;

    public ExpeditionRepositoryImpl() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("postgres");
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public Expedition safeExpedition(Expedition expedition) {
        this.entityManager.getTransaction().begin();

        return null;
    }

    @Override
    public Expedition findExpeditionById(UUID uuid) {
        return null;
    }

    @Override
    public Expedition findExpeditionByDate(LocalDate date) {
        return null;
    }

    @Override
    public List<Expedition> findExpeditionByLeader(Participant participant) {
        return null;
    }

    @Override
    public List<Expedition> findFutureExpeditions(LocalDate startDate) {
        return null;
    }

    @Override
    public List<Expedition> findAllExpeditions() {
        return null;
    }
}
