package uk.ac.ebi.atlas.streams;

import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.TranscriptProfile;

import javax.inject.Named;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Named("experimentLoader")
@Scope("prototype")
public class TranscriptProfilesInputStreamBuilder {

    private static final Logger logger = Logger.getLogger(TranscriptProfilesInputStreamBuilder.class);


    @Value("#{webappProperties['magetab.idf.url.template']}")
    private String idfFileUrlTemplate;

    @Value("#{webappProperties['magetab.test.datafile.url']}")
    private String dataFileURL;

    private Double rpkmCutOffValue;

    public ObjectInputStream<TranscriptProfile> createFor(String experimentAccession) throws IOException {

        String idfFileLocation = String.format(idfFileUrlTemplate, experimentAccession, experimentAccession);

        URL mageTabURL = buildURL(idfFileLocation);

        List<ExperimentRun> experimentRuns = Lists.newArrayList(MageTabInvestigation.parse(mageTabURL).extractExperimentRuns());

        TranscriptProfilesInputStream objectInputStream = TranscriptProfilesInputStream.forFile(dataFileURL)
                                                            .withExperimentRuns(experimentRuns)
                                                            .withRpkmCutOff(rpkmCutOffValue).create();

        return objectInputStream;
    }

    URL buildURL(String location) {
        try {
            return new URL(location);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Error while building URL for location " + location + ". Error details: " + e.getMessage());
        }

    }

    TranscriptProfilesInputStreamBuilder setDataFileURL(String dataFileURL) {

        this.dataFileURL = dataFileURL;
        return this;

    }

    public TranscriptProfilesInputStreamBuilder setRpkmCutOff(double rpkmCutOffValue) {

        this.rpkmCutOffValue = rpkmCutOffValue;
        return this;

    }
}
