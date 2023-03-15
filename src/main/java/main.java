import application.ExpeditionService;
import application.GroupService;
import application.ParticipantService;
import domain.Expedition;
import domain.Group;
import domain.Participant;

import java.sql.Array;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class main {

    private ExpeditionService expeditionService;
    private GroupService groupService;
    private static ParticipantService participantService;
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        participantService = new ParticipantService();


        List<Participant> participants = generateParticipants();
        Expedition expedition = generateExpedition(participants.get(0));
        Group group = generateGroup(participants, expedition);

        for(Participant participant: participants){
            participantService.createParticipant(participant);
        }

    }


    private static List generateParticipants() throws SQLException, ClassNotFoundException {
        ArrayList<Participant> participants = new ArrayList<>();
        for(int i =0; i<5; i++){
            Participant participant = new Participant("Test", "Test", "test@test.de");
            participants.add(participant);
        }
        return participants;
    }

    private static Group generateGroup(List<Participant> participants, Expedition expedition) throws SQLException, ClassNotFoundException {
        Group group = new Group("Testgroup", participants.get(0), expedition);

        return group;
    }

    private static Expedition generateExpedition(Participant leader){
        Expedition expedition = new Expedition(LocalDate.now(), LocalDate.now().plusDays(1), leader);
        return expedition;
    }
}
