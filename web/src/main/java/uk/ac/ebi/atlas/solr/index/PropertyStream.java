package uk.ac.ebi.atlas.solr.index;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Charsets;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PropertyStream {

    @Value("#{configuration['bioentity.properties']}")
    private String dataDirectory;

    PropertyStream(){
    }

    private CSVReader buildCsvReader() throws IOException {Path bioentityFilePath = Paths.get(dataDirectory + "anopheles_gambiae.A-AFFY-102.tsv");
        Reader fileReader = Files.newBufferedReader(bioentityFilePath, Charsets.UTF_8);
        return new CSVReader(fileReader);
    }

    public PropertyDocument next(){
        try(CSVReader csvReader = buildCsvReader()) {
            String[] csvValues = csvReader.readNext();
            return new PropertyDocument("gene", "anopheles_gambiae", "design_element", csvValues);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

}
