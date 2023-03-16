package application;

import domain.Expedition;
import domain.Group;
import domain.GroupComparator;
import infrastructure.GroupRepositoryImpl;
import infrastructure.ParticipantRepositoryImpl;

import java.util.Collections;
import java.util.List;

public class GroupService {
    private GroupRepositoryImpl groupRepository;
    private ParticipantRepositoryImpl participantRepository;

    public GroupService(GroupRepositoryImpl groupRepository) {
        this.groupRepository = groupRepository;
    }

    public GroupService() {
        this.groupRepository = new GroupRepositoryImpl();
    }

    public int createGroup(Group group) {
        int groupId = this.groupRepository.saveGroup(group);
        return groupId;
    }

    public List<Group> findGroupWithExpeditionId(Expedition expedition) {
        List<Group> groups = this.groupRepository.findGroupsByExpedition(expedition);
        Collections.sort(groups, new GroupComparator());
        return groups;
    }

    public List<Group> getAllGroupsCriteria() {
        return this.groupRepository.findAllGroupsCriteria();
    }

}
