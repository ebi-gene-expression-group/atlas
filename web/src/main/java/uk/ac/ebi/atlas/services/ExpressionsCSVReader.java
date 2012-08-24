package uk.ac.ebi.atlas.services;


import au.com.bytecode.opencsv.CSVReader;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.ExpressionLevel;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

public class ExpressionsCSVReader implements ExpressionLevelStream {

    private static final Logger logger = Logger.getLogger(ExpressionsCSVReader.class);

    private CSVReader csvReader;

    private ExpressionLevelBuffer expressionLevelBuffer;

    protected ExpressionsCSVReader(CSVReader csvReader, Map<String, ExperimentRun> experimentRuns) {
        this.csvReader = csvReader;

        initializeBuffer(experimentRuns);
    }

    public ExpressionsCSVReader(Reader reader, Map<String, ExperimentRun> experimentRuns) {
        this(new CSVReader(reader), experimentRuns);
    }


    @Override
    public ExpressionLevel readNext() {
        ExpressionLevel expressionLevel = expressionLevelBuffer.poll();

        if (expressionLevel == null) {

            String[] values = readNextLine();
            if (values == null) {
                return null;
            }
            expressionLevelBuffer.reload(values);
            expressionLevel = expressionLevelBuffer.poll();
        }

        return expressionLevel;
    }

    protected void initializeBuffer(Map<String, ExperimentRun> experimentRuns) {
        String[] accessions = readNextLine();

        checkArgument(accessions.length < experimentRuns.size(), "Data file has less runs than expected");

        Map<Integer, ExperimentRun> indexedRuns = new HashMap<>();
        //first column must be skipped because it represents the transcript id
        for (int i = 1; i < accessions.length; i++) {
            ExperimentRun experimentRun = experimentRuns.get(accessions[i]);
            if (experimentRun == null) {
                throw new IllegalArgumentException("Experiment run not found for " + accessions[i]);
            }
            indexedRuns.put(i, experimentRun);
        }

        expressionLevelBuffer = new ExpressionLevelBuffer(indexedRuns);
    }

    protected String[] readNextLine() {
        try {
            return csvReader.readNext();

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Exception thrown while processing the input reader: " + e.getMessage());
        }
    }


    @Override
    public void close() throws IOException {
        csvReader.close();
    }
}
