package uk.ac.ebi.atlas.streams;


import au.com.bytecode.opencsv.CSVReader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.model.GeneProfileBuilderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

import static com.google.common.base.Preconditions.checkNotNull;
import static uk.ac.ebi.atlas.streams.ExpressionsBuffer.GENE_ID_COLUMN;

@Named("geneProfilesInputStream")
@Scope("prototype")
public class GeneProfilesInputStream implements ObjectInputStream<GeneProfile> {

    private static final Logger logger = Logger.getLogger(GeneProfilesInputStream.class);

    private GeneProfileBuilderFactory geneProfileBuilderFactory;

    private CSVReader csvReader;

    private ExpressionsBuffer expressionsBuffer;

    private double cutoff;

    @Inject
    protected GeneProfilesInputStream(GeneProfileBuilderFactory geneProfileBuilderFactory) {
        this.geneProfileBuilderFactory = geneProfileBuilderFactory;
    }

    protected GeneProfilesInputStream setExpressionBuffer(ExpressionsBuffer expressionsBuffer) {
        this.expressionsBuffer = expressionsBuffer;
        return this;
    }

    protected GeneProfilesInputStream setCsvReader(CSVReader csvReader) {
        this.csvReader = csvReader;
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


        GeneProfile.Builder geneProfileBuilder = geneProfileBuilderFactory.with(values[GENE_ID_COLUMN], cutoff);

        expressionsBuffer.reload(values);

        Expression expression;

        while ((expression = expressionsBuffer.poll()) != null) {

            geneProfileBuilder.addExpression(expression);
        }

        if (geneProfileBuilder.containsExpressions()) {
            return geneProfileBuilder.create();
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

        @Value("#{configuration['magetab.tsvfile.url.template']}")
        private String tsvFileUrlTemplate;

        private GeneProfilesInputStream geneProfilesInputStream;

        private ExpressionsBuffer.Builder expressionsBufferBuilder;

        private ExpressionsBuffer expressionsBuffer;

        @Inject
        public Builder(GeneProfilesInputStream geneProfilesInputStream, ExpressionsBuffer.Builder expressionsBufferBuilder){
            this.expressionsBufferBuilder = expressionsBufferBuilder;
            this.geneProfilesInputStream = geneProfilesInputStream;
        }

        protected Builder forExperiment(String experimentAccession, InputStream expressionLevelsInputStream) {

            CSVReader csvReader = buildCsvReader(expressionLevelsInputStream);

            geneProfilesInputStream.setCsvReader(csvReader);

            String[] dataFileHeaders = geneProfilesInputStream.readCsvLine();

            expressionsBuffer = expressionsBufferBuilder.forExperiment(experimentAccession)
                    .withHeaders(dataFileHeaders).create();

            return this;
        }

        protected CSVReader buildCsvReader(InputStream expressionLevelsInputStream) {
            Reader dataFileReader = new InputStreamReader(expressionLevelsInputStream);
            return new CSVReader(dataFileReader, '\t');
        }

        public Builder forExperiment(String experimentAccession) {
            String tsvFileUrl = String.format(tsvFileUrlTemplate, experimentAccession);
            try{
                return forExperiment(experimentAccession, new URL(checkNotNull(tsvFileUrl)).openStream());
            } catch (MalformedURLException e) {
                logger.error(e.getMessage(), e);
                throw new IllegalArgumentException("Error while building URL for location " + tsvFileUrl + ". Error details: " + e.getMessage());
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new IllegalArgumentException("Error while building GeneProfileInputStream. Error details: " + e.getMessage());
            }
        }

        public Builder withCutoff(double cutoff) {
            geneProfilesInputStream.cutoff = cutoff;
            return this;
        }

        public ObjectInputStream<GeneProfile> create() {
            geneProfilesInputStream.setExpressionBuffer(expressionsBuffer);
            return geneProfilesInputStream;
        }


    }


}
