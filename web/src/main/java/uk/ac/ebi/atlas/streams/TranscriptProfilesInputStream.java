package uk.ac.ebi.atlas.streams;


import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.ExpressionLevel;
import uk.ac.ebi.atlas.model.TranscriptProfile;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TranscriptProfilesInputStream implements ObjectInputStream<TranscriptProfile> {

    private static final Logger logger = Logger.getLogger(TranscriptProfilesInputStream.class);
    public static final int TRANSCRIPT_ID_COLUMN = 0;

    private CSVReader csvReader;

    private ExpressionLevelsBuffer expressionLevelBuffer;

    TranscriptProfilesInputStream(CSVReader csvReader, List<ExperimentRun> experimentRuns) {
        this.csvReader = csvReader;
        initializeBuffer(experimentRuns);
    }


    public TranscriptProfilesInputStream(Reader reader, List<ExperimentRun> experimentRuns) {
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
    public TranscriptProfile readNext() {
        String[] values = readCsvLine();

        if (values == null) {
            return null;
        }

        TranscriptProfile.Builder builder = TranscriptProfile.forTranscriptId(values[TRANSCRIPT_ID_COLUMN]);

        expressionLevelBuffer.reload(values);

        ExpressionLevel expressionLevel;

        while ((expressionLevel = expressionLevelBuffer.poll()) != null) {

            //ToDo: Filter comes here
            builder.addExpressionLevel(expressionLevel);
        }


        //ToDo: if no expression levels pass the filter, discard current TranscriptProfile and read next recursively
        return builder.create();
    }

    String[] readCsvLine() {
        try {
            return csvReader.readNext();

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("Exception thrown while reading next csv line: " + e.getMessage());
        }
    }

    TranscriptProfilesInputStream setExpressionLevelBuffer(ExpressionLevelsBuffer buffer) {
        this.expressionLevelBuffer = buffer;
        return this;
    }


    @Override
    public void close() throws IOException {
        csvReader.close();
    }

}
