package infrastructure;

import domain.Group;
import domain.Participant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;

import java.util.List;
import java.util.Optional;

public class ParticipantRepositoryImpl implements ParticipantRepository {
    private EntityManager entityManager = Persistence.createEntityManagerFactory("postgres").createEntityManager();

    @Override
    public Optional<Participant> findParticipantById(int id) {

        Query query = this.entityManager.createQuery("SELECT p FROM Participant p WHERE p.id = :id", Participant.class);
        query.setParameter("id", id);

        return query.getResultList().stream().findFirst();
    }

    @Override
    public List<Participant> findAll() {
        return this.entityManager.createQuery("SELECT p FROM Participant p").getResultList();
    }

    @Override
    public List findParticipantByGroup(Group group) {
        Query query = this.entityManager.createQuery("SELECT p FROM Participant p WHERE p.group = :group");
        query.setParameter("group", group);
        return query.getResultList();
    }

    @Override
    public int saveParticipant(Participant participant) {
        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(participant);
            this.entityManager.getTransaction().commit();
            return participant.getId();
        } catch (Exception e) {
            System.out.println(e);
        }
        //TODO dont return 0
        return 0;
    }

    @Override
    public int updateParticipant(Participant participant) {
        try {
            this.entityManager.getTransaction().begin();
            Query query = this.entityManager.createQuery("UPDATE Participant p SET p.firstName = :first, p.lastName = :last, p.mail= :mail, p.group= :group WHERE p.id = :id");
            query.setParameter("id", participant.getId());
            query.setParameter("first", participant.getFirstName());
            query.setParameter("last", participant.getLastName());
            query.setParameter("mail", participant.getMail());
            query.setParameter("group", participant.getGroup());

            int update = query.executeUpdate();
            this.entityManager.getTransaction().commit();
            return update;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }

    public List<Tuple> findParticipantsAndNumberOfExpeditions() {
        Query query = this.entityManager.createQuery("SELECT p, COUNT(e) FROM Participant p, Expedition e where p.group.expedition.id= e.id group by p.id", Tuple.class);
        //TODO Native Query schreiben
        this.entityManager.createNativeQuery("SELECT p.id, COUNT(g.expedition_id) FROM tab_exercise_participant as p, tab_exercise_group as g, tab_exercise_expedition as e WHERE p.group_id = g.id AND g.expedition_id = e.id group by p.id", Tuple.class);
        return query.getResultList();
    }
}
