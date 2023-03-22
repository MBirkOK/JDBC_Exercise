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
    public Optional findParticipantById(int id) {

        Query query = this.entityManager.createQuery("SELECT p FROM Participant p WHERE p.id = :id");
        query.setParameter("id", id);

        return query.getResultStream().findFirst();
    }

    @Override
    public List findAll() {
        return this.entityManager.createQuery("SELECT p FROM Participant p").getResultList();
    }

    @Override
    public List<Participant> findParticipantByFirstName(String name) {
        return null;
    }

    @Override
    public List<Participant> findParticipantByLastName(String name) {
        return null;
    }

    @Override
    public Participant findParticipantByMail(String mail) {
        return null;
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
        return query.getResultList();
    }
}
