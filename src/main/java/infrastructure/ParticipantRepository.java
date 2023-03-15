package infrastructure;

import domain.Group;
import domain.Participant;

import java.util.List;
import java.util.UUID;

public interface ParticipantRepository {

    Participant findParticipantById(UUID uuid);

    List<Participant> findParticipantByFirstName(String name);

    List<Participant> findParticipantByLastName(String name);

    Participant findParticipantByMail(String mail);

    List<Participant> findParticipantByGroup(Group group);

    UUID saveParticipant(Participant participant);
}
