package uk.ac.ebi.atlas.trader;

import com.google.common.collect.ImmutableSet;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.experimentimport.ExperimentDao;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import java.util.Set;
import java.util.concurrent.ExecutionException;

public abstract class ExperimentTrader {
    protected final ExperimentDao experimentDao;

    public ExperimentTrader(ExperimentDao experimentDao) {
        this.experimentDao = experimentDao;
    }

    public Experiment getPublicExperiment(String experimentAccession) {
        return getExperiment(experimentAccession, "");
    }

    public abstract Experiment getExperiment(String experimentAccession, String accessKey);

    public abstract void removeExperimentFromCache(String experimentAccession);

    public abstract Experiment getExperimentFromCache(String experimentAccession, ExperimentType experimentType) throws ExecutionException;

    public Set<Experiment> getPublicExperiments(ExperimentType... experimentTypes) {
        ImmutableSet.Builder<Experiment> b = ImmutableSet.builder();
        for(String accession : getPublicExperimentAccessions(experimentTypes)) {
            try {
                b.add(getPublicExperiment(accession));
            } catch (ResourceNotFoundException e) {/*Yum!*/}
        }
        return b.build();
    }

    public Set<String> getPublicExperimentAccessions(ExperimentType... experimentTypes) {
        return experimentDao.findPublicExperimentAccessions(experimentTypes);
    }
}
