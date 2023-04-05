package application.print;

import domain.Patient;
import domain.employment.Employee;

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
        final Object[][] table = new String[8][];
        table[0] = new String[]{"Aufenthalt erstellen", "create"};
        table[1] = new String[]{"Personal hinzufügen", "personal"};
        table[2] = new String[]{"Gehälter abfragen", "payment"};
        table[3] = new String[]{"Patienten zu Zeitpunkt", "patients"};
        table[4] = new String[]{"Bettenauslastung", "beds"};
        table[5] = new String[]{"Medikamentennutzung", "meds"};
        table[6] = new String[]{"Geschlechteraufteilung", "genders"};
        table[7] = new String[]{"Beenden", "ende"};

        for (final Object[] row : table) {
            System.out.format("%-30s%-35s%n", row);
        }
    }

    public static String[] getPersonalData() throws IOException {
        List<String> personalData = new ArrayList<>();
        personalData.addAll(List.of(getPersonData()));
        personalData.add(getWard());
        personalData.add(getSalary());
        personalData.add(getType());

        return personalData.toArray(new String[0]);
    }

    public static String[] getPatientData() throws IOException {
        String[] patientPersonalData = getPersonData();
        String[] patientData = new String[8];
        for (int i = 0; i < patientPersonalData.length; i++) {
            patientData[i] = patientPersonalData[i];
        }
        patientData[3] = getRoom();
        patientData[4] = getNurse();
        patientData[5] = getStayBegin();
        patientData[6] = getStayEnd();
        patientData[7] = getGender();
        return patientData;
    }

    public static String[] getPersonData() throws IOException {
        String[] personData = new String[3];
        personData[0] = getFirstName();
        personData[1] = getLastName();
        personData[2] = getBirthday();
        return personData;
    }

    private static String getFirstName() throws IOException {
        System.out.println("Bitte gib den Vornamen ein:");
        return reader.readLine();
    }

    private static String getLastName() throws IOException {
        System.out.println("Bitte gib den Nachnamen ein:");
        return reader.readLine();
    }

    private static String getBirthday() throws IOException {
        System.out.println("Bitte gib das Geburtsdatum eingeben: (YYYY-MM-DD)");
        String birthday = reader.readLine();
        if (isValidDate(birthday)) {
            return birthday;
        } else {
            return getBirthday();
        }
    }

    public static String getWard() throws IOException {
        System.out.println("Bitte gib die StationsID ein:");
        return reader.readLine();
    }

    private static String getSalary() throws IOException {
        System.out.println("Bitte gib das Gehalt ein:");
        return reader.readLine();
    }

    public static String getType() throws IOException {
        System.out.println("Bitte gib die Anstellung ein:");
        return reader.readLine();
    }

    public static String getRoom() throws IOException {
        System.out.println("Bitte die Raumnummer angeben:");
        return reader.readLine();
    }

    private static String getNurse() throws IOException {
        System.out.println("Bitte die ID des Pflegenden angeben:");
        return reader.readLine();
    }

    private static String getStayBegin() throws IOException {
        System.out.println("Wann beginnt der Aufenthalt? (YYYY-MM-DD)");
        String date = reader.readLine();
        if (isValidDate(date)) {
            return date;
        } else {
            return getStayBegin();
        }
    }

    private static String getStayEnd() throws IOException {
        System.out.println("Wann endet der Aufenthalt? (YYYY-MM-DD)");
        String date = reader.readLine();
        if (isValidDate(date)) {
            return date;
        } else {
            return getStayEnd();
        }
    }

    private static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    public static LocalDate getStartDate() throws IOException {
        System.out.println("Bitte gib das Datum zum Anfang des Aufenthalts an: (YYYY-MM-DD)");
        String date = reader.readLine();
        if (isValidDate(date)) {
            return LocalDate.parse(date);
        } else {
            return getStartDate();
        }
    }

    public static LocalDate getEndDate() throws IOException {
        System.out.println("Bitte gib das Datum zum Ende des Aufenthalts an: (YYYY-MM-DD)");
        String date = reader.readLine();
        if (isValidDate(date)) {
            return LocalDate.parse(date);
        } else {
            return getEndDate();
        }
    }

    public static void printPaymentTable(Employee[] employees) {
        final Object[][] table = new String[employees.length + 1][];
        table[0] = new String[]{"PNr", "Vorname", "Nachname", "Gehalt"};
        for (int i = 1; i < employees.length + 1; i++) {
            table[i] = new String[]{String.valueOf(employees[i - 1].getPersonalnumber()), employees[i - 1].getFirstName(),
                employees[i - 1].getLastName(), String.valueOf(employees[i - 1].getSalary())};
        }
        for (final Object[] row : table) {
            System.out.format("%-30s%-35s%-35s%-35s%n", row);
        }
    }

    public static void printPatientTable(Patient[] patients) {
        final Object[][] table = new String[patients.length + 1][];
        table[0] = new String[]{"ID", "Vorname", "Nachname", "Ankunft", "Abreise"};
        for (int i = 1; i < patients.length + 1; i++) {
            table[i] = new String[]{String.valueOf(patients[i - 1].getId()), patients[i - 1].getFirstName(),
                patients[i - 1].getLastName(), String.valueOf(patients[i - 1].getStartStay()), String.valueOf(patients[i - 1].getEndStay())};
        }

        for (final Object[] row : table) {
            System.out.format("%-30s%-35s%-35s%-35s%-35s%n", row);
        }
    }

    public static LocalDate getDate() throws IOException {
        System.out.println("Bitte gib das Datum: (YYYY-MM-DD)");
        String date = reader.readLine();
        try {
            if (isValidDate(date)) {
                return LocalDate.parse(date);
            } else {
                return getDate();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return getDate();
    }

    public static void printBedUsage(int[] bed) {
        final Object[][] table = new String[2][];
        table[0] = new String[]{"Frei", "Belegt", "Insgesamt"};
        table[1] = new String[]{String.valueOf(bed[0]), String.valueOf(bed[1] - bed[0]), String.valueOf(bed[1])};

        for (final Object[] row : table) {
            System.out.format("%-30s%-35s%-35s%n", row);
        }
    }

    public static void printMedUsage(List<String[]> medData) {
        final Object[][] table = new String[medData.size() + 1][];
        table[0] = new String[]{"Anzahl", "Medikamention", "Behandlung"};
        for (int i = 0; i < medData.size(); i++) {
            table[i + 1] = new String[]{
                medData.get(i)[0],
                medData.get(i)[1],
            medData.get(i)[2]};
        }
        for (final Object[] row : table) {
            System.out.format("%-30s%-35s%-35s%n", row);
        }
    }

    private static String getGender() throws IOException {
        System.out.println("Bitte Geschlecht angeben: M/W/D");
        return reader.readLine();
    }

    public static void printGenderTable(List<Integer> genderData) {
        final Object[][] table = new String[2][];
        table[0] = new String[]{"W", "M", "D", "SUMM"};
        int men = 0, women = 0, divers = 0;
        if (genderData.size() == 0) {
            men = 0;
            women = 0;
            divers = 0;
        }
        if (genderData.size() == 1) {
            men = Integer.parseInt(String.valueOf(genderData.get(0)));
            women = 0;
            divers = 0;
        }
        if (genderData.size() == 2) {
            men = Integer.parseInt(String.valueOf(genderData.get(0)));
            women = Integer.parseInt(String.valueOf(genderData.get(1)));
            divers = 0;
        }
        if (genderData.size() == 3) {
            men = Integer.parseInt(String.valueOf(genderData.get(0)));
            women = Integer.parseInt(String.valueOf(genderData.get(1)));
            divers = Integer.parseInt(String.valueOf(genderData.get(2)));
        }
        table[1] = new String[]{String.valueOf(men), String.valueOf(women), String.valueOf(divers), String.valueOf(men + women + divers)};
        for (final Object[] row : table) {
            System.out.format("%-30s%-35s%-35s%-35s%n", row);
        }
    }
}
