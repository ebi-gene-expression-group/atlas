package uk.ac.ebi.atlas.streams;


import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
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

    private Double rpkmCutOff;

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

        List<ExperimentRun> filteredExperimentRuns = Lists.newArrayList(Collections2.filter(experimentRuns, new Predicate<ExperimentRun>() {
            @Override
            public boolean apply(ExperimentRun input) {
                return orderSpecification.contains(input.getRunAccession());
            }
        }));

        Collections.sort(filteredExperimentRuns, buildExperimentRunComparator(orderSpecification));
        expressionLevelBuffer = new ExpressionLevelsBuffer(filteredExperimentRuns);
    }


    void setRpkmCutOff(Double rpkmCutOff) {
        this.rpkmCutOff = rpkmCutOff;
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
        TranscriptProfile transcriptProfile;

        do {
            String[] values = readCsvLine();

            if (values == null) {
                return null;
            }
            transcriptProfile = buildTranscriptProfile(values);
        } while (transcriptProfile == null);

        return transcriptProfile;

    }

    private TranscriptProfile buildTranscriptProfile(String[] values) {


        TranscriptProfile.Builder builder = TranscriptProfile.forTranscriptId(values[TRANSCRIPT_ID_COLUMN]);

        if (rpkmCutOff != null) {
            builder.withRpkmCutOff(rpkmCutOff);
        }

        expressionLevelBuffer.reload(values);

        ExpressionLevel expressionLevel;

        while ((expressionLevel = expressionLevelBuffer.poll()) != null) {

            builder.addExpressionLevel(expressionLevel);
        }

        if (builder.containsExpressionLevels()) {
            return builder.create();
        }
        return null;

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
