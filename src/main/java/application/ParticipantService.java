package application;

import domain.Expedition;
import domain.Group;
import domain.Participant;
import infrastructure.ParticipantRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class ParticipantService {
    private ParticipantRepositoryImpl participantRepository;
    private GroupService groupService;

    public ParticipantService(ParticipantRepositoryImpl participantRepository) {
        this.participantRepository = participantRepository;
    }

    public ParticipantService() {
        this.participantRepository = new ParticipantRepositoryImpl();
    }

    public int createParticipant(Participant participant) {
        return this.participantRepository.saveParticipant(participant);
    }

    public void update(Participant participant) {
        this.participantRepository.updateParticipant(participant);
    }

    public Participant getParticipantById(int id){
        return this.participantRepository.findParticipantById(id);
    }

    public List<Participant> findAllParticipantsOfExpedition(Expedition expedition){
        List<Group> groups = this.groupService.findGroupWithExpeditionId(expedition);
        List<Participant> participants = new ArrayList<>();

        for(Group group: groups){
            participants.addAll(this.participantRepository.findParticipantByGroup(group));
        }

        return participants;
    }
}
