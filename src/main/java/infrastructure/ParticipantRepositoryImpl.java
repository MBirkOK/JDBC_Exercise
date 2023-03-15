package infrastructure;

import domain.Group;
import domain.Participant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;

import java.util.List;
import java.util.UUID;

public class ParticipantRepositoryImpl implements ParticipantRepository {
    private EntityManager entityManager = EntityManagerFactoryBuilder.build().createEntityManager();

    public ParticipantRepositoryImpl() {

    }

    @Override
    public Participant findParticipantById(UUID uuid) {
        return null;
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
    public Participant saveParticipant(Participant participant){

    }
}
