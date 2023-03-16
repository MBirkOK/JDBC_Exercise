package infrastructure;

import domain.Expedition;
import domain.Group;
import domain.Participant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.UUID;

public class GroupRepositoryImpl implements GroupRepository {
    private EntityManager entityManager = Persistence.createEntityManagerFactory("postgres").createEntityManager();

    @Override
    public Group findGroupById(UUID uuid) {
        return null;
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
        return null;
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
        CriteriaQuery cq = criteriaBuilder.createQuery(Group.class);
        Root root = cq.from(Group.class);
        cq.select(root);
        TypedQuery typedQuery = this.entityManager.createQuery(cq);
        return typedQuery.getResultList();
    }
}
