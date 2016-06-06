
package uk.ac.ebi.atlas.trader.loader;

import com.google.common.cache.CacheLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.trader.ExperimentDesignParser;
import uk.ac.ebi.atlas.utils.ArrayExpressClient;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

public abstract class ExperimentsCacheLoader<T extends Experiment> extends CacheLoader<String, T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperimentsCacheLoader.class);

    private String extraInfoPathTemplate;

    private ArrayExpressClient arrayExpressClient;

    private ExperimentDesignParser experimentDesignParser;

    private ExperimentDAO experimentDAO;

    protected ExperimentsCacheLoader() {
    }

    @Value("#{configuration['experiment.extra-info-image.path.template']}")
    public void setExtraInfoPathTemplate(String extraInfoPathTemplate) {
        this.extraInfoPathTemplate = extraInfoPathTemplate;
    }

    @Inject
    public void setExperimentDAO(ExperimentDAO experimentDAO) {
        this.experimentDAO = experimentDAO;
    }

    @Inject
    public void setArrayExpressClient(ArrayExpressClient arrayExpressClient) {
        this.arrayExpressClient = arrayExpressClient;
    }

    @Inject
    public void setExperimentDesignParser(ExperimentDesignParser experimentDesignParser) {
        this.experimentDesignParser = experimentDesignParser;
    }

    @Override
    public T load(String experimentAccession) throws IOException {

        LOGGER.info("loading experiment with accession: {}", experimentAccession);

        boolean hasExtraInfoFile = extraInfoFileExists(experimentAccession);

        ExperimentDesign experimentDesign = experimentDesignParser.parse(experimentAccession);

        ExperimentDTO experimentDTO = experimentDAO.findExperiment(experimentAccession, true);

        String experimentDescription = fetchExperimentNameFromArrayExpress(experimentAccession, experimentDTO);

        return load(experimentDTO, experimentDescription, hasExtraInfoFile, experimentDesign);

    }

    private boolean extraInfoFileExists(String experimentAccession) {
        String extraInfoFileLocation = MessageFormat.format(extraInfoPathTemplate, experimentAccession);
        return Files.exists(Paths.get(extraInfoFileLocation));
    }

    protected abstract T load(ExperimentDTO experimentDTO, String experimentDescription,
                              boolean hasExtraInfoFile, ExperimentDesign experimentDesign) throws IOException;

    final String fetchExperimentNameFromArrayExpress(String experimentAccession, ExperimentDTO experimentDTO) {
        try {
            return arrayExpressClient.fetchExperimentName(experimentAccession);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return experimentDTO.getTitle();
        }
    }

}
