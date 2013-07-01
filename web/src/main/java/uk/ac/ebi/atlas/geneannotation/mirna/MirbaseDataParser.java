package uk.ac.ebi.atlas.geneannotation.mirna;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class MirbaseDataParser {

    public static final String ID = "ID";

    public static final String DR = "DR";

    private String miRnaDataLocation;

    protected void setMiRnaDataLocation(String miRnaDataLocation) {
        this.miRnaDataLocation = miRnaDataLocation;
    }

    public Map<String, String> extractAnnotationsMap() {

        Map<String, String> result = Maps.newHashMap();

        try (BufferedReader br = new BufferedReader(new FileReader(miRnaDataLocation))) {

            readData(result, br);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    protected void readData(Map<String, String> result, BufferedReader br) throws IOException {
        String line = br.readLine();

        String id;
        String name;

        while (line != null) {
            id = getID(line);
            if (id != null) {
                name = getName(line);
                if (name != null) {
                    result.put(id, name);
                }
            }

            line = br.readLine();
        }
    }

    protected String getName(String entry) {
        if (entry.startsWith(DR)) {
            ArrayList<String> words = Lists.newArrayList(Splitter.on("; ").trimResults().split(entry));
            String nameWithDot = words.get(words.size() - 1);
            return nameWithDot.substring(0, nameWithDot.length() - 1);
        }
        return null;
    }


    protected String getID(String entry) {
        if (entry.startsWith(ID)) {
            ArrayList<String> words = Lists.newArrayList(Splitter.on(" ").trimResults().omitEmptyStrings().split(entry));
            if (words.size() > 2) {
                return words.get(1);
            }
        }
        return null;
    }
}
