package infrastructure;

import domain.Group;
import domain.Participant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;

import java.util.List;
import java.util.UUID;

public class ParticipantRepositoryImpl implements ParticipantRepository {
    private EntityManager entityManager = Persistence.createEntityManagerFactory("postgres").createEntityManager();

    @Override
    public Participant findParticipantById(UUID uuid) {
        return this.entityManager.find(Participant.class, uuid);
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
    public UUID saveParticipant(Participant participant) {
        try{
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(participant);
            this.entityManager.getTransaction().commit();
            return participant.getId();
        } catch (Exception e){
            System.out.println(e);
        }
        //TODO dont return null
        return null;
    }
}
