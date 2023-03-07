package application;

import application.print.Printer;
import application.services.LiteraturService;
import domain.Literature;
import domain.Person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class main {
    private static LiteraturService literaturService = new LiteraturService();

    public main() {
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
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
                Literature literature = literaturService.findLiteratureWithId(UUID.fromString(Printer.getLiteratureId()));
                Printer.printDetailedInformation(literature);
            } else if (input.equals("edit")) {
                String literature = Printer.getLiteratureId();
                List<String> information = Printer.getAllLiteratureInformation();
                literaturService.updateLiterature(literature, information);
            } else if (input.equals("delete")) {
                literaturService.delete(Printer.getLiteratureId());
            }
        }
    }

    private static void generateSampleData() throws SQLException, IOException {
        for (int i = 0; i < 4; i++) {
            Literature literature = new Literature("Test", new Person("Test",
                "Test"), 2023, "1.", "Test");
            literaturService.createLiterature(literature);
        }
    }

}
