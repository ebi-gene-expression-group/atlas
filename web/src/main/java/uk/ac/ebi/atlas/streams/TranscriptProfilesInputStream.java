package uk.ac.ebi.atlas.streams;


import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.ExpressionLevel;
import uk.ac.ebi.atlas.model.TranscriptProfile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public class TranscriptProfilesInputStream implements ObjectInputStream<TranscriptProfile> {

    private static final Logger logger = Logger.getLogger(TranscriptProfilesInputStream.class);
    public static final int TRANSCRIPT_ID_COLUMN = 0;

    private CSVReader csvReader;

    private ExpressionLevelsBuffer expressionLevelsBuffer;

    private Double rpkmCutOff;

    public static Builder forFile(String dataFileURL) {
        try{
            return new Builder(dataFileURL);
        }catch(MalformedURLException e){
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Error while building URL for location " + dataFileURL + ". Error details: " + e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Error while building TranscriptProfileInputStream. Error details: " + e.getMessage());
        }
    }

    public static Builder forInputStream(InputStream rpkmDataInputStream) {
        return new Builder(rpkmDataInputStream);
    }

    private TranscriptProfilesInputStream(){
    }

    private TranscriptProfilesInputStream setExpressionLevelsBuffer(ExpressionLevelsBuffer expressionLevelsBuffer) {
        this.expressionLevelsBuffer = expressionLevelsBuffer;
        return this;
    }

    private TranscriptProfilesInputStream setCsvReader(CSVReader csvReader) {
        this.csvReader = csvReader;
        return this;
    }

    private TranscriptProfilesInputStream setRpkmCutOff(Double rpkmCutOff) {
        this.rpkmCutOff = rpkmCutOff;
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

        if (rpkmCutOff != null) {
            builder.withRpkmCutOff(rpkmCutOff);
        }

        expressionLevelsBuffer.reload(values);

        ExpressionLevel expressionLevel;

        while ((expressionLevel = expressionLevelsBuffer.poll()) != null) {

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

    @Override
    public void close() throws IOException {
        csvReader.close();
    }


    public static class Builder {

        private TranscriptProfilesInputStream transcriptProfileInputStream;

        private Builder(InputStream rpkmDataInputStream){

            Reader dataFileReader = new InputStreamReader(rpkmDataInputStream);
            CSVReader csvReader = new CSVReader(dataFileReader, '\t');

            this.transcriptProfileInputStream = new TranscriptProfilesInputStream().setCsvReader(csvReader);

        }

        private Builder(String dataFileURL) throws IOException{
            this(new URL(checkNotNull(dataFileURL)).openStream());
        }

        public Builder withExperimentRuns(List<ExperimentRun> experimentRuns) {

            String [] dataFileHeaders = transcriptProfileInputStream.readCsvLine();
            List<String> orderSpecification = Arrays.asList(ArrayUtils.remove(dataFileHeaders, TRANSCRIPT_ID_COLUMN));
            ExpressionLevelsBuffer expressionLevelsBuffer = ExpressionLevelsBuffer.forExperimentRuns(experimentRuns)
                                    .withOrderSpecification(orderSpecification).create();
            return withExpressionLevelsBuffer(expressionLevelsBuffer);

        }

        //required for testability - mock injection
        Builder withExpressionLevelsBuffer(ExpressionLevelsBuffer expressionLevelsBuffer) {
            transcriptProfileInputStream.setExpressionLevelsBuffer(expressionLevelsBuffer);

            return this;
        }

        //required for testability - mock injection
        Builder withCsvReader(CSVReader csvReader) {
            transcriptProfileInputStream.setCsvReader(csvReader);
            return this;
        }

        public Builder withRpkmCutOff(Double cutOff) {
            transcriptProfileInputStream.rpkmCutOff = cutOff;
            return this;
        }

        public TranscriptProfilesInputStream create() {
            checkState(transcriptProfileInputStream.expressionLevelsBuffer != null, "Please assign the experimentRun list using the withExperimentRuns method before invoking create!");
            return transcriptProfileInputStream;
        }



    }




}
