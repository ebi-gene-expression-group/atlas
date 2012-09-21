package uk.ac.ebi.atlas.streams;


import au.com.bytecode.opencsv.CSVReader;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.TranscriptProfile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static uk.ac.ebi.atlas.streams.ExpressionsBuffer.TRANSCRIPT_ID_COLUMN;

public class
    TranscriptProfilesInputStream implements ObjectInputStream<TranscriptProfile> {

    private static final Logger logger = Logger.getLogger(TranscriptProfilesInputStream.class);

    private CSVReader csvReader;

    private ExpressionsBuffer expressionBuffer;

    private Double cutoff;

    public static Builder forFile(String dataFileURL) {
        try {
            return new Builder(dataFileURL);
        } catch (MalformedURLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Error while building URL for location " + dataFileURL + ". Error details: " + e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Error while building TranscriptProfileInputStream. Error details: " + e.getMessage());
        }
    }

    public static Builder forInputStream(InputStream expressionLevelsInputStream) {
        return new Builder(expressionLevelsInputStream);
    }

    private TranscriptProfilesInputStream() {
    }

    private TranscriptProfilesInputStream setExpressionBuffer(ExpressionsBuffer expressionBuffer) {
        this.expressionBuffer = expressionBuffer;
        return this;
    }

    private TranscriptProfilesInputStream setCsvReader(CSVReader csvReader) {
        this.csvReader = csvReader;
        return this;
    }

    private TranscriptProfilesInputStream setCutoff(Double cutoff) {
        this.cutoff = cutoff;
        return this;
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

        if (cutoff != null) {
            builder.withCutoff(cutoff);
        }

        expressionBuffer.reload(values);

        Expression expression;

        while ((expression = expressionBuffer.poll()) != null) {

            builder.addExpression(expression);
        }

        if (builder.containsExpressions()) {
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

    @Override
    public void close() throws IOException {
        csvReader.close();
    }


    public static class Builder {

        private TranscriptProfilesInputStream transcriptProfileInputStream;

        private Builder(InputStream expressionLevelsInputStream) {

            Reader dataFileReader = new InputStreamReader(expressionLevelsInputStream);
            CSVReader csvReader = new CSVReader(dataFileReader, '\t');

            this.transcriptProfileInputStream = new TranscriptProfilesInputStream().setCsvReader(csvReader);

        }

        private Builder(String dataFileURL) throws IOException {
            this(new URL(checkNotNull(dataFileURL)).openStream());
        }

        public Builder withExperimentRuns(List<ExperimentRun> experimentRuns) {

            String[] dataFileHeaders = transcriptProfileInputStream.readCsvLine();

            ExpressionsBuffer expressionsBuffer = ExpressionsBuffer.forExperimentRuns(experimentRuns)
                    .withHeaders(dataFileHeaders).create();

            return withExpressionsBuffer(expressionsBuffer);

        }

        //required for testability - mock injection
        Builder withExpressionsBuffer(ExpressionsBuffer expressionsBuffer) {
            transcriptProfileInputStream.setExpressionBuffer(expressionsBuffer);

            return this;
        }

        //required for testability - mock injection
        Builder withCsvReader(CSVReader csvReader) {
            transcriptProfileInputStream.setCsvReader(csvReader);
            return this;
        }

        public Builder withCutoff(Double cutoff) {
            transcriptProfileInputStream.cutoff = cutoff;
            return this;
        }

        public TranscriptProfilesInputStream create() {
            checkState(transcriptProfileInputStream.expressionBuffer != null, "Please assign the experimentRun list using the withExperimentRuns method before invoking create!");
            return transcriptProfileInputStream;
        }


    }


}
