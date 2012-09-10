package uk.ac.ebi.atlas.streams;


import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.TranscriptExpressionLevel;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExpressionLevelInputStream implements ObjectInputStream<TranscriptExpressionLevel> {

    private static final Logger logger = Logger.getLogger(ExpressionLevelInputStream.class);
    public static final int TRANSCRIPT_ID_COLUMN = 0;

    private CSVReader csvReader;

    private ExpressionLevelsBuffer expressionLevelBuffer;

    ExpressionLevelInputStream(CSVReader csvReader, List<ExperimentRun> experimentRuns) {
        this.csvReader = csvReader;
        initializeBuffer(experimentRuns);
    }


    public ExpressionLevelInputStream(Reader reader, List<ExperimentRun> experimentRuns) {
        this(new CSVReader(reader, '\t'), experimentRuns);
    }


    void initializeBuffer(List<ExperimentRun> experimentRuns) {
        String[] firstLine = readCsvLine();
        final List<String> orderSpecification = Arrays.asList(ArrayUtils.remove(firstLine, TRANSCRIPT_ID_COLUMN));

        Collections.sort(experimentRuns, buildExperimentRunComparator(orderSpecification));
        expressionLevelBuffer = new ExpressionLevelsBuffer(experimentRuns);
    }


    Comparator<ExperimentRun> buildExperimentRunComparator(final List<String> orderSpecification) {

        return Ordering.natural().onResultOf(new Function<ExperimentRun, Integer>() {
            @Override
            public Integer apply(ExperimentRun experimentRun) {
                int orderIndexOfRun = orderSpecification.indexOf(experimentRun.getRunAccession());
                return orderIndexOfRun;
            }
        });
    }


    @Override
    public TranscriptExpressionLevel readNext() {
        TranscriptExpressionLevel transcriptExpressionLevel = expressionLevelBuffer.poll();

        if (transcriptExpressionLevel == null) {

            String[] values = readCsvLine();
            if (values == null) {
                return null;
            }
            expressionLevelBuffer.reload(values);
            transcriptExpressionLevel = expressionLevelBuffer.poll();
        }

        return transcriptExpressionLevel;
    }


    String[] readCsvLine() {
        try {
            return csvReader.readNext();

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("Exception thrown while reading next csv line: " + e.getMessage());
        }
    }

    ExpressionLevelInputStream setExpressionLevelBuffer(ExpressionLevelsBuffer buffer) {
        this.expressionLevelBuffer = buffer;
        return this;
    }


    @Override
    public void close() throws IOException {
        csvReader.close();
    }

}
