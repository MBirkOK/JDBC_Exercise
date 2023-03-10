package infrastructure;

import domain.Expedition;
import domain.Group;
import domain.Participant;

import java.util.List;
import java.util.UUID;

public class GroupRepositoryImpl implements GroupRepository{
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
}
