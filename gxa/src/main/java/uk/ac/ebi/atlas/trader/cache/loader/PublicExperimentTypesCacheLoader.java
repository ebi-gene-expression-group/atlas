package uk.ac.ebi.atlas.trader.cache.loader;

import com.google.common.cache.CacheLoader;
import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class PublicExperimentTypesCacheLoader extends CacheLoader<String, ExperimentType> {

    private final ExperimentDAO experimentDAO;

    @Inject
    public PublicExperimentTypesCacheLoader(ExperimentDAO experimentDAO) {
        this.experimentDAO = experimentDAO;
    }

    @Override
    public ExperimentType load(@Nonnull String experimentAccession) {
        ExperimentDTO experimentDTO = experimentDAO.findPublicExperiment(experimentAccession);

        return experimentDTO.getExperimentType();
    }
}
