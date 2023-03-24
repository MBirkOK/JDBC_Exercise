package application;

import application.print.Printer;
import application.services.LiteraturService;
import domain.Literature;
import domain.Person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.IllegalCharsetNameException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class main {
    private static LiteraturService literaturService = new LiteraturService();

    public main() {
    }

    public static void main(String[] args) throws IOException {
        if (literaturService.getALlLiterature().isEmpty()) {
            generateSampleData();
        }


        String input = "";
        while (!input.equals("ende")) {
            Printer.printTable();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            input = reader.readLine();

            if (input.equals("create")) {
                List<String> information = Printer.getAllLiteratureInformation();
                literaturService.createLiteratureFromList(information);
            } else if (input.equals("all")) {
                Printer.printAllLiterature(literaturService.getALlLiterature());
            } else if (input.equals("id")) {
                String id = Printer.getLiteratureId();
                if (isValidUUID(id)) {
                    Literature literature = literaturService.findLiteratureWithId(UUID.fromString(id));
                    Printer.printDetailedInformation(literature);
                } else {
                    System.out.println("Keine valide UUID, zurück ins Hauptmenü!");
                }
            } else if (input.equals("edit")) {
                String literature = Printer.getLiteratureId();
                if (isValidUUID(literature)) {
                    List<String> information = Printer.getAllLiteratureInformation();
                    literaturService.updateLiterature(literature, information);
                } else {
                    System.out.println("Keine valide UUID, zurück ins Hauptmenü!");
                }
            } else if (input.equals("delete")){
                String literature = Printer.getLiteratureId();
            if (isValidUUID(literature)) {
                literaturService.delete(literature);
            } else {
                System.out.println("Keine valide UUID, zurück ins Hauptmenü!");
            }

            }
        }
    }

    private static void generateSampleData(){
        for (int i = 0; i < 4; i++) {
            Literature literature = new Literature("Test", new Person("Test",
                "Test"), LocalDate.now(), "1.", "Test");
            literaturService.createLiterature(literature);
        }
    }

    private static boolean isValidUUID(String id) {
        try {
            UUID.fromString(id);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
