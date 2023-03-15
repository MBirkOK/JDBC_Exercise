package application;

import domain.Participant;
import infrastructure.ParticipantRepositoryImpl;

public class ParticipantService {
    private ParticipantRepositoryImpl participantRepository;

    public ParticipantService(ParticipantRepositoryImpl participantRepository) {
        this.participantRepository = participantRepository;
    }

    public ParticipantService() {
        this.participantRepository = new ParticipantRepositoryImpl();
    }

    public Participant createParticipant(Participant participant){
        this.participantRepository.
    }
}
