package infrastructure;

import domain.Literature;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.UUID;

public class LiteratureRepository {
    private EntityManager entityManager;

    public LiteratureRepository() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("postgres");
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public void safeLiterature(Literature literature) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(literature);
        this.entityManager.getTransaction().commit();
    }

    public List<Literature> findAll() {
        TypedQuery<Literature> query = this.entityManager.createQuery("SELECT l FROM Literature l", Literature.class);
        return query.getResultList();
    }

    public Literature findLiteratureById(UUID id) {
        return this.entityManager.find(Literature.class, id);
    }

    public void update(Literature literature) {
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(literature);
        this.entityManager.getTransaction().commit();
    }

    public void delete(Literature literature) {
        this.entityManager.getTransaction().begin();
        this.entityManager.remove(literature);
        this.entityManager.getTransaction().commit();
    }
}
