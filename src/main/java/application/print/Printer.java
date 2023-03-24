package application.print;

import domain.Literature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Printer {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * This is the default selection of actions the user can perform with the stored data. On the left side are the data types (teacher,
     * student, class) and in the second to the fifth column are the different actions that can be performed. Exceptions are the commands
     * for sorting, exporting and importing the data. Here, too, you can see that the terms are arranged on the left side and the commands
     * are right next to them.
     */
    public static void printTable() {
        System.out.println("Tippe das Wort ein um die Aktion auszuführen.");
        final Object[][] table = new String[6][];
        table[0] = new String[]{"Literatur erstellen", "create"};
        table[1] = new String[]{"Jede Literatur finden", "all"};
        table[2] = new String[]{"Literatur mit ID finden", "id"};
        table[3] = new String[]{"Literatur bearbeiten", "edit"};
        table[4] = new String[]{"Literatur löschen", "delete"};
        table[5] = new String[]{"Beenden", "ende"};

        for (final Object[] row : table) {
            System.out.format("%-30s%-35s%n", row);
        }
    }

    public static void printAllLiterature(List<Literature> literatureList) {
        final Object[][] table = new String[literatureList.size() + 1][];
        table[0] = new String[]{"ID", "Titel"};
        int counter = 1;
        for (Literature literature : literatureList) {
            table[counter] = new String[]{literature.getId().toString(), literature.getTitle()};
            counter++;
        }

        for (final Object[] row : table) {
            System.out.format("%-40s%-25s%n", row);
        }
    }

    public static List<String> getAllLiteratureInformation() throws IOException {
        List<String> information = new ArrayList<>();
        information.add(getLiteratureTitle());
        information.add(getLiteratureAuthorFirstName());
        information.add(getLiteratureAuthorLastName());
        information.add(getLiteratureRelease());
        information.add(getLiteratureEdition());
        information.add(getLiteraturePublisher());

        return information;
    }

    private static String getLiteratureTitle() throws IOException {
        System.out.println("Bitte gib den Titel ein:");
        return reader.readLine();
    }

    private static String getLiteratureAuthorFirstName() throws IOException {
        System.out.println("Bitte gib den Vornamen des Autors ein:");
        return reader.readLine();
    }

    private static String getLiteratureAuthorLastName() throws IOException {
        System.out.println("Bitte gib den Nachnamen des Autors ein:");
        return reader.readLine();
    }

    private static String getLiteratureRelease() throws IOException {
        try{
            System.out.println("Bitte gib das Veröffentlichungsdatum ein:");
            String date = reader.readLine();
            LocalDate.parse(date);
            return date;
        } catch (Exception e){
            return getLiteratureRelease();
        }
    }

    private static String getLiteratureEdition() throws IOException {
        System.out.println("Bitte gib die Edition ein:");
        return reader.readLine();
    }

    private static String getLiteraturePublisher() throws IOException {
        System.out.println("Bitte gib Namen des Verlags ein:");
        return reader.readLine();
    }

    public static String getLiteratureId() throws IOException {
        System.out.println("Bitte gib die ID der Literatur an:");
        return reader.readLine();
    }

    public static void printDetailedInformation(Literature literature) {
        final Object[][] table = new String[2][];
        table[0] = new String[]{"ID", "Titel", "Autor", "Erscheinungsdatum", "Edition", "Verlag"};
        table[1] = new String[]{literature.getId().toString(), literature.getTitle(), literature.getAuthor().getFirstName() + " " + literature.getAuthor().getLastName(),
            String.valueOf(literature.getRelease()), literature.getEdition(), literature.getPublisher()};

        for (final Object[] row : table) {
            System.out.format("%-40s%-25s%-25s%-25s%-25s%-25s%n", row);
        }
    }
}
