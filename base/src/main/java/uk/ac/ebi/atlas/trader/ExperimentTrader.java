package uk.ac.ebi.atlas.trader;

import com.google.common.collect.ImmutableSet;
import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import java.util.List;
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

    public Set<Experiment> getPublicExperiments(ExperimentType... experimentTypes){
        ImmutableSet.Builder<Experiment> b = ImmutableSet.builder();
        for(String accession : getPublicExperimentAccessions(experimentTypes)){
            b.add(getPublicExperiment(accession));
        }
        return b.build();
    }

    public Set<String> getPublicExperimentAccessions(ExperimentType... experimentTypes) {
        return experimentDAO.findPublicExperimentAccessions(experimentTypes);
    }
}
