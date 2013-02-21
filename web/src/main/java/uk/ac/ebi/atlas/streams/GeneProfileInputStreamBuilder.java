package uk.ac.ebi.atlas.streams;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.GeneExpressions;
import uk.ac.ebi.atlas.model.GeneProfile;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;

import static com.google.common.base.Preconditions.checkNotNull;

@Named("geneProfilesInputStreamBuilder")
@Scope("prototype")
public class GeneProfileInputStreamBuilder {

    private static final Logger logger = Logger.getLogger(GeneProfileInputStreamBuilder.class);

    @Value("#{configuration['experiment.magetab.path.template']}")
    private String tsvFileUrlTemplate;

    private ExpressionsBuffer.Builder expressionsBufferBuilder;
    private GeneProfile.Builder geneProfileBuilder;
    private CSVReader csvReader;
    private String experimentAccession;


    @Inject
    public GeneProfileInputStreamBuilder(ExpressionsBuffer.Builder expressionsBufferBuilder, GeneProfile.Builder geneProfileBuilder) {
        this.expressionsBufferBuilder = expressionsBufferBuilder;
        this.geneProfileBuilder = geneProfileBuilder;

    }

    protected GeneProfileInputStreamBuilder forExperiment(String experimentAccession, InputStream expressionLevelsInputStream) {
        this.experimentAccession = experimentAccession;
        csvReader = buildCsvReader(expressionLevelsInputStream);

        return this;
    }

    protected CSVReader buildCsvReader(InputStream expressionLevelsInputStream) {
        Reader dataFileReader = new InputStreamReader(expressionLevelsInputStream);
        return new CSVReader(dataFileReader, '\t');
    }

    public GeneProfileInputStreamBuilder forExperiment(String experimentAccession) {
        String tsvFileUrl = MessageFormat.format(tsvFileUrlTemplate, experimentAccession);
        try {
            Path filePath = FileSystems.getDefault().getPath(checkNotNull(tsvFileUrl));
            return forExperiment(experimentAccession, Files.newInputStream(filePath));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Error while building GeneProfileInputStream.", e);
        }
    }

    public ObjectInputStream<GeneProfile> createGeneProfileInputStream() {
        return new GeneProfilesInputStream(csvReader, experimentAccession, expressionsBufferBuilder, geneProfileBuilder);
    }

    public ObjectInputStream<GeneExpressions> createCompleteGeneProfileInputStream() {
       return new GeneExpressionsInputStream(csvReader, experimentAccession, expressionsBufferBuilder);
    }

}
