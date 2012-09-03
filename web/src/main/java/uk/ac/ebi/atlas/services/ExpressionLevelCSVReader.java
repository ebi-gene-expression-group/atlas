package uk.ac.ebi.atlas.services;


import au.com.bytecode.opencsv.CSVReader;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.ExpressionLevel;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class ExpressionLevelCSVReader implements ExpressionLevelStream{

    private Map<String, ExperimentRun> experimentRuns = new HashMap<>();

    private CSVReader csvReader;

    protected ExpressionLevelCSVReader(CSVReader csvReader, Map<String, ExperimentRun> experimentRuns) {
        this.csvReader = csvReader;
        this.experimentRuns = experimentRuns;
    }

    public ExpressionLevelCSVReader(Reader reader, Map<String, ExperimentRun> experimentRuns){
        this(new CSVReader(reader), experimentRuns);
    }


    @Override
    public ExpressionLevel readNext() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
