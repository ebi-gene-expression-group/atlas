package uk.ac.ebi.atlas.solr.index;

import au.com.bytecode.opencsv.CSVReader;

import java.io.IOException;

public class PropertyStream {

    private CSVReader csvReader;

    PropertyStream(CSVReader csvReader){
        this.csvReader = csvReader;
    }

    public PropertyDocument next(){
        try {
            String[] csvValues = csvReader.readNext();
            return new PropertyDocument();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
