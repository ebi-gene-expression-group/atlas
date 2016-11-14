package uk.ac.ebi.atlas.trader.cache.loader;

import com.google.common.cache.CacheLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.trader.ExperimentDesignParser;
import uk.ac.ebi.atlas.utils.ArrayExpressClient;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.IOException;

public abstract class ExperimentsCacheLoader<T extends Experiment> extends CacheLoader<String, T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperimentsCacheLoader.class);

    private ArrayExpressClient arrayExpressClient;

    private ExperimentDesignParser experimentDesignParser;

    private ExperimentDAO experimentDAO;

    protected ExperimentsCacheLoader() {
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
    public T load(@Nonnull String experimentAccession) throws IOException {

        LOGGER.info("loading experiment with accession: {}", experimentAccession);

        ExperimentDesign experimentDesign = experimentDesignParser.parse(experimentAccession);

        ExperimentDTO experimentDTO = experimentDAO.findExperiment(experimentAccession, true);

        String experimentDescription = fetchExperimentNameFromArrayExpress(experimentAccession, experimentDTO);

        return load(experimentDTO, experimentDescription, experimentDesign);

    }

    protected abstract T load(ExperimentDTO experimentDTO, String experimentDescription,
                              ExperimentDesign experimentDesign) throws IOException;

    private String fetchExperimentNameFromArrayExpress(String experimentAccession, ExperimentDTO experimentDTO) {
        try {
            return arrayExpressClient.fetchExperimentName(experimentAccession);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return experimentDTO.getTitle();
        }
    }

}
