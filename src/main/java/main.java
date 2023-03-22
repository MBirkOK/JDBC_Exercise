import application.ExpeditionService;
import application.GroupService;
import application.ParticipantService;
import application.Printer;
import domain.Expedition;
import domain.Group;
import domain.Participant;
import jakarta.persistence.Tuple;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class main {

    private static ExpeditionService expeditionService;
    private static GroupService groupService;
    private static ParticipantService participantService;

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        participantService = new ParticipantService();
        expeditionService = new ExpeditionService();
        groupService = new GroupService();
        if (participantService.getParticipantById(1).isEmpty()) {
            generateTestData();
        }
        String input = "";
        while (!input.equals("ende")) {
            input = Printer.printTable();
            switch (input) {
                case "expedition" -> {
                    expeditionOptions();
                }
                case "gruppe" -> {
                    groupOptions();
                }
                case "teil" -> {
                    participantOptions();
                }
            }
        }
    }


    private static void generateTestData() throws SQLException, ClassNotFoundException {
        List<Participant> participants = generateParticipants();
        Expedition expedition = generateExpedition(participants.get(0));
        Group group = generateGroup(participants, expedition);
        for (Participant participant : participants) {
            participantService.createParticipant(participant);
        }
        expeditionService.createExpedition(expedition);
        groupService.createGroup(group);
        for (Participant participant : participants) {
            participant.changeGroup(group);
            participantService.update(participant);
        }
    }

    private static List generateParticipants() throws SQLException, ClassNotFoundException {
        ArrayList<Participant> participants = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Participant participant = new Participant("Test", "Test", "test@test.de");
            participants.add(participant);
        }
        return participants;
    }

    private static Group generateGroup(List<Participant> participants, Expedition expedition) throws
        SQLException, ClassNotFoundException {
        Group group = new Group("Testgroup", participants.get(0), expedition);

        return group;
    }

    private static Expedition generateExpedition(Participant leader) {
        Expedition expedition = new Expedition(LocalDate.now(), LocalDate.now().plusDays(1), leader);
        return expedition;
    }

    private static void expeditionOptions() throws IOException {
        String expedition = "";
        while (!expedition.equals("back")) {
            expedition = Printer.printExpeditionOptions();
            if (expedition.equals("create")) {
                List<String> expeditionData = Printer.getExpeditionData();
                Optional<Participant> optional;
                String participantId = expeditionData.get(2);
                do {
                    optional = participantService.getParticipantById(
                        Integer.parseInt(participantId));
                    if(optional.isEmpty()){
                        participantId = Printer.getLeaderId();
                    }
                } while (optional.isEmpty());
                expeditionService.createExpedition(new Expedition(LocalDate.parse(expeditionData.get(0)),
                    LocalDate.parse(expeditionData.get(1)), optional.get()));

            } else if (expedition.equals("all")) {
                List<Expedition> allExpeditions = expeditionService.getAllExpeditions();
                Printer.printAllExpeditions(allExpeditions);
            } else if (expedition.equals("zukunft")) {
                List<Expedition> futureExpeditions = expeditionService.getFutureExpeditions();
                Printer.printAllExpeditions(futureExpeditions);
            }
        }
    }

    private static void groupOptions() throws IOException {
        String group = "";
        while (!group.equals("back")) {
            group = Printer.printGroupOptions();
            if (group.equals("create")) {
                List<String> groupData = Printer.getGroupData();
                Optional<Participant> optional;
                String leaderId = groupData.get(1);
                do {
                    optional = participantService.getParticipantById(
                        Integer.parseInt(leaderId));
                    if(optional.isEmpty()){
                        leaderId = Printer.getLeaderId();
                    }
                } while (optional.isEmpty());
                Optional<Expedition> expedition;
                String expeditionId = groupData.get(2);
                do {
                    expedition = expeditionService.getExpeditionById(
                        Integer.parseInt(expeditionId));
                    if(expedition.isEmpty()){
                        expeditionId = Printer.getExpeditionId();
                    }
                } while (expedition.isEmpty());
                groupService.createGroup(new Group(groupData.get(0), optional.get(), expedition.get()));
            } else if (group.equals("all")) {
                List<Group> allGroups = groupService.getAllGroupsCriteria();
                Printer.printAllGroups(allGroups);
            } else if (group.equals("expedition")) {
                Optional<Expedition> expedition;
                do {
                    expedition = expeditionService.getExpeditionById(Integer.parseInt(Printer.getExpeditionId()));
                } while (expedition.isEmpty());
                List<Group> groups = groupService.findGroupWithExpeditionId(expedition.get());
                Printer.printAllGroups(groups);
            }
        }
    }

    private static void participantOptions() throws IOException {
        String participant = "";
        while (!participant.equals("back")) {
            participant = Printer.printParticipantOptions();
            if (participant.equals("create")) {
                List<String> participantData = Printer.getParticipantData();
                Participant newParticipant = new Participant(participantData.get(0), participantData.get(1), participantData.get(2));
                if (participantData.size() == 4) {
                    Group group = groupService.findGroupById(Integer.parseInt(participantData.get(3)));
                    newParticipant.changeGroup(group);
                }
                participantService.createParticipant(newParticipant);
            } else if (participant.equals("all")) {
                Optional<Expedition> optional;
                do {
                    optional = expeditionService.getExpeditionById(Integer.parseInt(Printer.getExpeditionId()));
                } while (optional.isEmpty());
                List<Participant> allParticipants = participantService.getParticipantsToExpedition(optional.get());
                Printer.printAllParticipants(allParticipants);
            } else if (participant.equals("anzahl")) {
                List<Tuple> participants = participantService.getParticipantsWithNumberOfExpeditions();
                Printer.printParticipantsWithNumberOfExpeditions(participants);
            } else if (participant.equals("really")) {
                List<Participant> participants = participantService.getAllParticipants();
                Printer.printAllParticipants(participants);
            }
        }
    }
}
