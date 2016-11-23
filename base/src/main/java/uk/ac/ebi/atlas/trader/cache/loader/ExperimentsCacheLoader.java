package uk.ac.ebi.atlas.trader.cache.loader;

import com.google.common.cache.CacheLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.trader.ExperimentDesignParser;
import uk.ac.ebi.atlas.utils.ArrayExpressClient;

import javax.annotation.Nonnull;

public class ExperimentsCacheLoader<T extends Experiment> extends CacheLoader<String, T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperimentsCacheLoader.class);

    private final ArrayExpressClient arrayExpressClient;

    private final ExperimentDesignParser experimentDesignParser;

    private final ExperimentDAO experimentDAO;

    private final ExperimentFactory<T> experimentFactory;

    public ExperimentsCacheLoader(ArrayExpressClient arrayExpressClient, ExperimentDesignParser
            experimentDesignParser, ExperimentDAO experimentDAO,ExperimentFactory<T> experimentFactory ){
        this.arrayExpressClient = arrayExpressClient;
        this.experimentDesignParser = experimentDesignParser;
        this.experimentDAO = experimentDAO;
        this.experimentFactory = experimentFactory;
    }

    @Override
    public T load(@Nonnull String experimentAccession){

        LOGGER.info("loading experiment with accession: {}", experimentAccession);

        ExperimentDesign experimentDesign = experimentDesignParser.parse(experimentAccession);

        ExperimentDTO experimentDTO = experimentDAO.findExperiment(experimentAccession, true);

        String experimentDescription = fetchExperimentNameFromArrayExpress(experimentAccession, experimentDTO);

        return experimentFactory.create(experimentDTO, experimentDescription, experimentDesign);

    }

    private String fetchExperimentNameFromArrayExpress(String experimentAccession, ExperimentDTO experimentDTO) {
        try {
            return arrayExpressClient.fetchExperimentName(experimentAccession);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return experimentDTO.getTitle();
        }
    }

}
