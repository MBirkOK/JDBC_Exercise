package application.print;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
        String[] personalData = new String[6];
        personalData = getPersonData();
        personalData[3] = getWard();
        personalData[4] = getSalary();
        personalData[5] = getType();

        return personalData;
    }

    public static String[] getPatientData() throws IOException {
        String [] patientPersonalData = getPersonData();
        String[] patientData = new String[7];
        for (int i =0; i<patientPersonalData.length; i++){
            patientData[i] = patientPersonalData[i];
        }
        patientData[3] = getRoom();
        patientData[4] = getNurse();
        patientData[5] = getStayBegin();
        patientData[6] = getStayEnd();

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
        if(isValidDate(birthday)){
            return birthday;
        } else{
            return getBirthday();
        }
    }

    public static String getWard() throws IOException {
        System.out.println("Bitte gib die StationsID ein:");
        return reader.readLine();
    }

    private static String getSalary() throws IOException{
        System.out.println("Bitte gib das Gehalt ein:");
        return reader.readLine();
    }

    public static String getType() throws IOException{
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
        if(isValidDate(date)){
            return date;
        } else{
            return getStayBegin();
        }
    }

    private static String getStayEnd() throws IOException {
        System.out.println("Wann endet der Aufenthalt? (YYYY-MM-DD)");
        String date = reader.readLine();
        if(isValidDate(date)){
            return date;
        } else{
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
}
