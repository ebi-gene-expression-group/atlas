package uk.ac.ebi.atlas.streams;


import au.com.bytecode.opencsv.CSVReader;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.GeneProfile;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static uk.ac.ebi.atlas.streams.ExpressionsBuffer.GENE_ID_COLUMN;

@Named("geneProfilesInputStream")
@Scope("prototype")
public class GeneProfilesInputStream implements ObjectInputStream<GeneProfile> {

    private static final Logger logger = Logger.getLogger(GeneProfilesInputStream.class);

    private CSVReader csvReader;

    private ExpressionsBuffer expressionsBuffer;

    private Double cutoff;

    protected GeneProfilesInputStream() {
    }

    protected GeneProfilesInputStream setExpressionBuffer(ExpressionsBuffer expressionsBuffer) {
        this.expressionsBuffer = expressionsBuffer;
        return this;
    }

    protected GeneProfilesInputStream setCsvReader(CSVReader csvReader) {
        this.csvReader = csvReader;
        return this;
    }

    protected GeneProfilesInputStream setCutoff(Double cutoff) {
        this.cutoff = cutoff;
        return this;
    }

    @Override
    public GeneProfile readNext() {
        GeneProfile geneProfile;

        do {
            String[] values = readCsvLine();

            if (values == null) {
                return null;
            }
            geneProfile = buildGeneProfile(values);
        } while (geneProfile == null);

        return geneProfile;

    }

    private GeneProfile buildGeneProfile(String[] values) {


        GeneProfile.Builder builder = GeneProfile.forGeneId(values[GENE_ID_COLUMN]);

        if (cutoff != null) {
            builder.withCutoff(cutoff);
        }

        expressionsBuffer.reload(values);

        Expression expression;

        while ((expression = expressionsBuffer.poll()) != null) {

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
        logger.debug("<close> close invoked on GeneProfilesInputStream");
    }


    @Named("geneProfilesInputStreamBuilder")
    @Scope("prototype")
    public static class Builder {

        private GeneProfilesInputStream geneProfilesInputStream;

        private ExpressionsBuffer.Builder expressionsBufferBuilder;

        private ExpressionsBuffer expressionsBuffer;

        @Inject
        public Builder(GeneProfilesInputStream geneProfilesInputStream, ExpressionsBuffer.Builder expressionsBufferBuilder){
            this.expressionsBufferBuilder = expressionsBufferBuilder;
            this.geneProfilesInputStream = geneProfilesInputStream;
        }

        protected Builder forDataFileInputStream(InputStream expressionLevelsInputStream) {

            Reader dataFileReader = new InputStreamReader(expressionLevelsInputStream);
            CSVReader csvReader = new CSVReader(dataFileReader, '\t');

            geneProfilesInputStream.setCsvReader(csvReader);

            return this;
        }

        public Builder forDataFileURL(String dataFileURL) {
            try{
                return forDataFileInputStream(new URL(checkNotNull(dataFileURL)).openStream());
            } catch (MalformedURLException e) {
                logger.error(e.getMessage(), e);
                throw new IllegalArgumentException("Error while building URL for location " + dataFileURL + ". Error details: " + e.getMessage());
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new IllegalArgumentException("Error while building GeneProfileInputStream. Error details: " + e.getMessage());
            }
        }

        public Builder withExperimentAccession(String experimentAccession) {

            String[] dataFileHeaders = geneProfilesInputStream.readCsvLine();

            expressionsBuffer = expressionsBufferBuilder.forExperiment(experimentAccession)
                                .withHeaders(dataFileHeaders).create();

            return this;

        }

        //required for testability - mock injection
        Builder injectCsvReader(CSVReader csvReader) {
            geneProfilesInputStream.setCsvReader(csvReader);
            return this;
        }

        public Builder withCutoff(Double cutoff) {
            geneProfilesInputStream.cutoff = cutoff;
            return this;
        }

        public ObjectInputStream<GeneProfile> create() {
            checkState(expressionsBuffer != null, "Builder not in the right state for GeneProfilesInputStream creation.");
            geneProfilesInputStream.setExpressionBuffer(expressionsBuffer);
            return geneProfilesInputStream;
        }


    }


}
