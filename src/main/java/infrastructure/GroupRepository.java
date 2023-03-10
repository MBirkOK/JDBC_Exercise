package infrastructure;

import domain.Expedition;
import domain.Group;
import domain.Participant;

import java.util.List;
import java.util.UUID;

public interface GroupRepository {

    Group findGroupById(UUID uuid);

    Group findGroupByName(String name);

    List<Group> findGroupByLeader(Participant leader);

    List<Group> findGroupsByExpedition(Expedition expedition);

}
