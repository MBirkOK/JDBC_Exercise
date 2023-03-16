package infrastructure;

import domain.Group;
import domain.Participant;

import java.util.List;
import java.util.UUID;

public interface ParticipantRepository {

    Participant findParticipantById(int uuid);

    List<Participant> findAll();

    List<Participant> findParticipantByFirstName(String name);

    List<Participant> findParticipantByLastName(String name);

    Participant findParticipantByMail(String mail);

    List<Participant> findParticipantByGroup(Group group);

    int saveParticipant(Participant participant);

    int updateParticipant(Participant participant);
}
