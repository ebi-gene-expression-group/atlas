package uk.ac.ebi.atlas.geneannotation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class MiRnaParser {

    public static final String ID = "ID";

    public static final String ACCESSION = "ACC";

    private String miRnaDataLocation;

    protected void setMiRnaDataLocation(String miRnaDataLocation) {
        this.miRnaDataLocation = miRnaDataLocation;
    }

    public Map<String, String> extractAnnotationsMap() {


        try (BufferedReader br = new BufferedReader(new FileReader(miRnaDataLocation))) {

            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            String everything = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    protected String getID(String entry) {
        if (entry.startsWith(ID)) {
        }
        return "";
    }
}
