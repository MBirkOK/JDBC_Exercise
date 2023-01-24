package infrastructure;

import domain.Inventory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileConverter {

    public String[][] readCSV(String filePath) {
        String line = "";
        String cvsSplitBy = ",";
        List<String[]> row = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                row.add(line.split(cvsSplitBy));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[][] values = new String[row.size()][];
        for (int i = 0; i < row.size(); i++) {
            values[i] = row.get(i);
        }
        return values;
    }

    public void writeCSV(String filePath, String[][] values) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            for (String[] innerValue : values) {
                for (String value : innerValue) {
                    sb.append(value);
                    sb.append(",");
                }
                sb.append("\n");
            }
            sb.deleteCharAt(sb.length() - 1);
            bw.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
