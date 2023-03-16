package infrastructure;

import domain.Group;
import domain.Participant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;

import java.util.List;

public class ParticipantRepositoryImpl implements ParticipantRepository {
    private EntityManager entityManager = Persistence.createEntityManagerFactory("postgres").createEntityManager();

    @Override
    public Participant findParticipantById(int id) {
        return this.entityManager.find(Participant.class, id);
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
    public List<Participant> findParticipantByGroup(Group group) {
        return null;
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

    public List<Tuple> getParticipantsAndNumberOfExpeditions(){
        Query query = this.entityManager.createQuery("SELECT p, COUNT(e) FROM Participant p, Expedition e, Group g where p.group.expedition.id= e.id", Tuple.class);
        return query.getResultList();
    }
}
