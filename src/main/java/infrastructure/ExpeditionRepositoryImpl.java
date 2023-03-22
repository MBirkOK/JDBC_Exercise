package infrastructure;

import domain.Expedition;
import domain.Participant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ExpeditionRepositoryImpl implements ExpeditionRepository {

    private EntityManager entityManager;

    public ExpeditionRepositoryImpl() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("postgres");
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public int saveExpedition(Expedition expedition) {
        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(expedition);
            this.entityManager.getTransaction().commit();
            return expedition.getId();
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }

    @Override
    public Optional findExpeditionById(int id) {
        Query query = this.entityManager.createQuery("SELECT e FROM Expedition e WHERE e.id = :id");
        query.setParameter("id", id);

        return query.getResultStream().findFirst();
    }

    @Override
    public List<Expedition> findExpeditionByDate(LocalDate date) {
        Query query = this.entityManager.createQuery("SELECT e FROM Expedition e WHERE e.startDate > :date");
        query.setParameter("date", date);
        return query.getResultList();
    }

    @Override
    public List<Expedition> findExpeditionByLeader(Participant participant) {
        return null;
    }

    @Override
    public List findFutureExpeditions(LocalDate startDate) {
        Query query = this.entityManager.createQuery("SELECT e FROM Expedition e WHERE e > :now");
        query.setParameter("now", startDate);
        return query.getResultList();
    }

    @Override
    public List findAllExpeditions() {
        Query query = this.entityManager.createQuery("SELECT e FROM Expedition e");

        return query.getResultList();
    }
}
