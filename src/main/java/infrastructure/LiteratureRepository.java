package infrastructure;

import domain.Literature;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public class LiteratureRepository {
    private EntityManager entityManager;

    public LiteratureRepository() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("postgres");
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public void safeLiterature(Literature literature){
        this.entityManager.getTransaction().begin();

        this.entityManager.persist(literature);

        this.entityManager.getTransaction().commit();
    }

    public List<Literature> findAll(){
        TypedQuery<Literature> query = this.entityManager.createQuery("SELECT l FROM Literature l", Literature.class);
        return query.getResultList();
    }
}
