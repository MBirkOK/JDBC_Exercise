package infrastructure;

import domain.Group;
import domain.Participant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParticipantRepository {

    Optional<Participant> findParticipantById(int uuid);

    List<Participant> findAll();

    List<Participant> findParticipantByGroup(Group group);

    int saveParticipant(Participant participant);

    int updateParticipant(Participant participant);
}
