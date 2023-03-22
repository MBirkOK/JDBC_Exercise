package application;

import domain.Expedition;
import domain.Group;
import domain.Participant;
import jakarta.persistence.Tuple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Printer {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * This is the default selection of actions the user can perform with the stored data. On the left side are the data types (teacher,
     * student, class) and in the second to the fifth column are the different actions that can be performed. Exceptions are the commands
     * for sorting, exporting and importing the data. Here, too, you can see that the terms are arranged on the left side and the commands
     * are right next to them.
     */
    public static String printTable() throws IOException {
        System.out.println("Tippe das Wort ein um die Aktion auszuführen.");
        final Object[][] table = new String[4][];
        table[0] = new String[]{"Expedition", "expedition"};
        table[1] = new String[]{"Gruppe", "gruppe"};
        table[2] = new String[]{"Teilnehmer", "teil"};
        table[3] = new String[]{"Beenden", "ende"};

        for (final Object[] row : table) {
            System.out.format("%-30s%-35s%n", row);
        }
        return reader.readLine();
    }

    public static String printExpeditionOptions() throws IOException {
        System.out.println("Tippe das Wort ein um die Aktion auszuführen.");
        final Object[][] table = new String[4][];
        table[0] = new String[]{"Erstellen", "create"};
        table[1] = new String[]{"Alle auflisten", "all"};
        table[2] = new String[]{"Zukünftige auflisten", "zukunft"};
        table[3] = new String[]{"Zurück", "back"};

        for (final Object[] row : table) {
            System.out.format("%-30s%-35s%n", row);
        }
        return reader.readLine();
    }

    public static String printGroupOptions() throws IOException {
        System.out.println("Tippe das Wort ein um die Aktion auszuführen.");
        final Object[][] table = new String[4][];
        table[0] = new String[]{"Erstellen", "create"};
        table[1] = new String[]{"Alle auflisten", "all"};
        table[2] = new String[]{"Zur Expedition gehörig", "expedition"};
        table[3] = new String[]{"Zurück", "back"};

        for (final Object[] row : table) {
            System.out.format("%-30s%-35s%n", row);
        }
        return reader.readLine();
    }

    public static String printParticipantOptions() throws IOException {
        System.out.println("Tippe das Wort ein um die Aktion auszuführen.");
        final Object[][] table = new String[5][];
        table[0] = new String[]{"Erstellen", "create"};
        table[1] = new String[]{"Alle zu Expedition", "all"};
        table[2] = new String[]{"Wieviele Expeditionen", "anzahl"};
        table[3] = new String[]{"Wirklich alle", "really"};
        table[4] = new String[]{"Zurück", "back"};

        for (final Object[] row : table) {
            System.out.format("%-30s%-35s%n", row);
        }
        return reader.readLine();
    }

    public static void printAllExpeditions(List<Expedition> expeditionList) {
        final Object[][] table = new String[expeditionList.size() + 1][];
        table[0] = new String[]{"ID", "Startdatum", "Enddatum", "Leiter"};
        for (int i = 0; i < expeditionList.size(); i++) {
            Expedition expedition = expeditionList.get(i);
            table[i + 1] = new String[]{String.valueOf(expedition.getId()), String.valueOf(expedition.getStartDate()), String.valueOf(expedition.getEndDate()), expedition.getLeader().getFirstName() + " " + expedition.getLeader().getLastName()};
        }

        for (final Object[] row : table) {
            System.out.format("%-30s%-35s%-35s%-35s%n", row);
        }
    }

    public static void printAllGroups(List<Group> groupList) {
        final Object[][] table = new String[groupList.size() + 1][];
        table[0] = new String[]{"ID", "Gruppenname", "Leiter", "Expedition"};
        for (int i = 0; i < groupList.size(); i++) {
            Group group = groupList.get(i);
            table[i + 1] = new String[]{String.valueOf(group.getId()), group.getName(), group.getLeader().getFirstName() + " " + group.getLeader().getLastName(), String.valueOf(group.getExpedition().getId())};
        }

        for (final Object[] row : table) {
            System.out.format("%-30s%-35s%-35s%-35s%n", row);
        }
    }

    public static void printAllParticipants(List<Participant> participantList) {
        final Object[][] table = new String[participantList.size() + 1][];
        table[0] = new String[]{"ID", "Name", "Mail", "Gruppe"};
        for (int i = 0; i < participantList.size(); i++) {
            Participant participant = participantList.get(i);
            if (participant.getGroup() != null) {
                table[i + 1] = new String[]{String.valueOf(participant.getId()), participant.getFirstName() + " " + participant.getLastName(), participant.getMail(), String.valueOf(participant.getGroup().getId())};
            } else {
                table[i + 1] = new String[]{String.valueOf(participant.getId()), participant.getFirstName() + " " + participant.getLastName(), participant.getMail(), ""};
            }
        }

        for (final Object[] row : table) {
            System.out.format("%-30s%-35s%-35s%-35s%n", row);
        }
    }

    public static int findExpeditionId() {
        try {
            System.out.println("Bitte gib die ExpeditionsID ein:");
            return Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            return findExpeditionId();
        }
    }

    public static void printParticipantsWithNumberOfExpeditions(List<Tuple> participantIntegerMap) {
        final Object[][] table = new String[participantIntegerMap.size() + 1][];
        table[0] = new String[]{"ID", "Name", "Mail", "Anzahl Expeditionen"};
        for (int i = 0; i < participantIntegerMap.size(); i++) {
            Participant participant = (Participant)participantIntegerMap.get(i).get(0);
            table[i + 1] = new String[]{String.valueOf(participant.getId()), participant.getFirstName() + " " + participant.getLastName(), participant.getMail(), String.valueOf(participantIntegerMap.get(i).get(1))};
        }
        for (final Object[] row : table) {
            System.out.format("%-30s%-35s%-35s%-35s%n", row);
        }
    }

    public static List<String> getGroupData() throws IOException {
        System.out.println("Bitte gib Daten ein:");
        List<String> groupData = new ArrayList<>();
        groupData.add(getGroupName());
        groupData.add(getLeaderId());
        groupData.add(getExpeditionId());
        return groupData;
    }

    public static List<String> getParticipantData() throws IOException {
        System.out.println("Bitte gib Daten ein:");
        List<String> personalData = new ArrayList<>();
        personalData.add(getFirstName());
        personalData.add(getLastName());
        personalData.add(getMail());
        System.out.println("Möchtest du den Teilnehmer einer Gruppe hinzufügen? (Y/N)");
        if (reader.readLine().toUpperCase(Locale.ROOT).equals("Y")) {
            personalData.add(getGroup());
        }
        return personalData;
    }

    public static List<String> getExpeditionData() throws IOException {
        System.out.println("Bitte gib Daten ein:");
        List<String> expeditionData = new ArrayList<>();
        expeditionData.add(getStartDate());
        expeditionData.add(getEndDate());
        expeditionData.add(getLeaderId());

        return expeditionData;
    }

    //TODO Möglichkeit der Validierung auf Alphanummerisch
    public static String getFirstName() throws IOException {
        System.out.println("Bitte gib einen Vornamen ein:");
        return reader.readLine();
    }

    //TODO Möglichkeit der Validierung auf Alphanummerisch
    public static String getLastName() throws IOException {
        System.out.println("Bitte gib einen Nachnamen ein:");
        return reader.readLine();
    }

    //TODO Möglichkeit der Validierung auf Alphanummerisch
    public static String getMail() throws IOException {
        System.out.println("Bitte gib deine Mail-Adresse ein:");
        return reader.readLine();
    }

    //TODO Sollte man prüfen ob Gruppe existiert!
    public static String getGroup() throws IOException {
        System.out.println("Bitte gib die ID der Gruppe ein:");
        return reader.readLine();
    }

    //TODO Sollte man prüfen ob Leader existiert!
    public static String getLeaderId() throws IOException {
        System.out.println("Bitte gib die ID des Leitenden ein:");
        return reader.readLine();
    }

    //TODO Möglichkeit der Validierung auf Alphanummerisch
    public static String getGroupName() throws IOException {
        System.out.println("Bitte gib den Namen der Gruppe ein:");
        return reader.readLine();
    }

    public static String getExpeditionId() throws IOException {
        System.out.println("Bitte gib die ID der Expedition ein:");
        return reader.readLine();
    }

    public static String getStartDate() throws IOException {
        System.out.println("Bitte gib das Startdatum (YYYY-MM-DD):");
        String date = reader.readLine();
        try {
            LocalDate.parse(date);
        }catch (Exception e){
            return getStartDate();
        }
        return date;
    }

    public static String getEndDate() throws IOException {
        System.out.println("Bitte gib das Enddatum (YYYY-MM-DD):");
        String date = reader.readLine();
        try {
            LocalDate.parse(date);
        }catch (Exception e){
            return getEndDate();
        }
        return date;
    }
}
