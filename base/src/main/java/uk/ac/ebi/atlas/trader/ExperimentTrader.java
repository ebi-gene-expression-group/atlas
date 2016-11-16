package uk.ac.ebi.atlas.trader;

import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentType;

import java.util.Set;

public abstract class ExperimentTrader {
    protected ExperimentDAO experimentDAO;

    public ExperimentTrader(ExperimentDAO experimentDAO) {
        this.experimentDAO = experimentDAO;
    }

    public abstract Experiment getPublicExperiment(String experimentAccession);

    public abstract Experiment getExperiment(String experimentAccession, String accessKey);

    public abstract void removeExperimentFromCache(String experimentAccession);

    public abstract void removeAllExperimentsFromCache();

    public abstract Experiment getExperimentFromCache(String experimentAccession, ExperimentType experimentType);

    public Set<String> getPublicExperimentAccessions(ExperimentType experimentType) {
        return experimentDAO.findPublicExperimentAccessions(experimentType);
    }
}
