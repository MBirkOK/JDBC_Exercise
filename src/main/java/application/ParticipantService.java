package application;

import domain.Participant;
import infrastructure.ParticipantRepositoryImpl;

import java.util.UUID;

public class ParticipantService {
    private ParticipantRepositoryImpl participantRepository;

    public ParticipantService(ParticipantRepositoryImpl participantRepository) {
        this.participantRepository = participantRepository;
    }

    public ParticipantService() {
        this.participantRepository = new ParticipantRepositoryImpl();
    }

    public UUID createParticipant(Participant participant){
        return this.participantRepository.saveParticipant(participant);
    }
}
