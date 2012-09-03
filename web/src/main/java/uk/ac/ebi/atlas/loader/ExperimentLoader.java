package uk.ac.ebi.atlas.loader;

import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.model.ExperimentRun;

import javax.inject.Named;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;

@Named("experimentLoader")
public class ExperimentLoader {

    private static final Logger logger = Logger.getLogger(ExperimentLoader.class);


    @Value("#{webappProperties['magetab.idf.url.template']}")
    private String idfFileUrlTemplate;

    @Value("#{webappProperties['magetab.test.datafile.url']}")
    private String dataFileURL;


    public ExpressionLevelsInputStream getExpressionLevelsInputStream(String experimentAccession) {
        String idfFileLocation = String.format(idfFileUrlTemplate, experimentAccession, experimentAccession);

        URL mageTabURL = buildURL(idfFileLocation);

        List<ExperimentRun> experimentRuns = Lists.newArrayList(MageTabInvestigation.parse(mageTabURL).extractExperimentRuns());

        URL dataFileURL = buildURL(this.dataFileURL);

        try {

            Reader dataFileReader = new InputStreamReader(dataFileURL.openStream());

            return new ExpressionLevelsInputStream(dataFileReader, experimentRuns);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Error while parsing dataFileURL stream: " + e.getMessage());
        }
    }

    private URL buildURL(String location) {
        try {
            return new URL(location);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Error while building URL for location " + location + ". Error details: " + e.getMessage());
        }

    }

    public ExperimentLoader setDataFileURL(String dataFileURL) {
        this.dataFileURL = dataFileURL;
        return this;
    }

}
