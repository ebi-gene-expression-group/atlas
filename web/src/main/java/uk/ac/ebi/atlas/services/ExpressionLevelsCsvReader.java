package uk.ac.ebi.atlas.services;


import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.ExpressionLevel;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

public class ExpressionLevelsCsvReader implements ExpressionLevelStream {

    private static final Logger logger = Logger.getLogger(ExpressionLevelsCsvReader.class);
    public static final int TRANSACTION_ID_COLUMN = 0;

    private CSVReader csvReader;

    private ExpressionLevelsBuffer expressionLevelBuffer;

    protected ExpressionLevelsCsvReader(CSVReader csvReader, Map<String, ExperimentRun> experimentRuns) {
        this.csvReader = csvReader;

        initializeBuffer(experimentRuns);
    }


    public ExpressionLevelsCsvReader(Reader reader, Map<String, ExperimentRun> experimentRuns) {
        this(new CSVReader(reader, '\t'), experimentRuns);
    }


    @Override
    public ExpressionLevel readNext() {
        ExpressionLevel expressionLevel = expressionLevelBuffer.poll();

        if (expressionLevel == null) {

            String[] values = readCsvLine();
            if (values == null) {
                return null;
            }
            expressionLevelBuffer.reload(values);
            expressionLevel = expressionLevelBuffer.poll();
        }

        return expressionLevel;
    }


    protected void initializeBuffer(Map<String, ExperimentRun> experimentRuns) {
        String[] firstLine = readCsvLine();

        String[] firstLineExcludingTransactionIdColumn = ArrayUtils.remove(firstLine, TRANSACTION_ID_COLUMN);

        expressionLevelBuffer = new ExpressionLevelsBuffer(readCsvLine(), experimentRuns);
    }


    protected String[] readCsvLine() {
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
