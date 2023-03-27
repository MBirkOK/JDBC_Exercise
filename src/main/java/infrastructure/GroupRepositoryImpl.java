package infrastructure;

import domain.Expedition;
import domain.Group;
import domain.Participant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class GroupRepositoryImpl implements GroupRepository {
    private EntityManager entityManager = Persistence.createEntityManagerFactory("postgres").createEntityManager();

    @Override
    public Group findGroupById(int uuid) {
        Query query = this.entityManager.createQuery("SELECT g FROM Group g WHERE g.id = :id");
        query.setParameter("id", uuid);
        return (Group)query.getResultList().stream().findFirst().get();
    }

    @Override
    public Group findGroupByName(String name) {
        return null;
    }

    @Override
    public List<Group> findGroupByLeader(Participant leader) {
        return null;
    }

    @Override
    public List<Group> findGroupsByExpedition(Expedition expedition) {
        Query query = this.entityManager.createQuery("SELECT g FROM Group g WHERE g.expedition.id = :id");
        query.setParameter("id", expedition.getId());

        return query.getResultList();
    }

    @Override
    public int saveGroup(Group group) {
        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(group);
            this.entityManager.getTransaction().commit();
            return group.getId();
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }

    public List<Group> findAllGroupsCriteria(){
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> cq = criteriaBuilder.createQuery(Group.class);
        Root<Group> root = cq.from(Group.class);
        cq.select(root);
        TypedQuery<Group> typedQuery = this.entityManager.createQuery(cq);
        return typedQuery.getResultList();
    }
}
