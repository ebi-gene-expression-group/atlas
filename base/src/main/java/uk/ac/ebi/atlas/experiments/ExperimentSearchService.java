package uk.ac.ebi.atlas.experiments;

import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

// Some methods to find public experiment accessions by their attributes. In all fairness, we’re cheating a bit since
// we know that all experiments will be cached and this will be faster than going to the DB (and if we’re not then
// we’re doing the rest of the app a favour by pre-loading some experiments anyway).
@Component
public abstract class ExperimentSearchService {
    private final ExperimentTrader experimentTrader;

    public ExperimentSearchService(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    public ImmutableSet<Experiment> searchPublicExperimentsBySpecies(String speciesSubstring) {
        return experimentTrader.getPublicExperiments()
                .stream()
                .filter(experiment ->
                        containsIgnoreCase(experiment.getSpecies().getName(), speciesSubstring) ||
                        containsIgnoreCase(experiment.getSpecies().getReferenceName(), speciesSubstring) ||
                        containsIgnoreCase(experiment.getSpecies().getEnsemblName(), speciesSubstring))
                .collect(toImmutableSet());
    }

    public ImmutableSet<Experiment> searchPublicExperimentsByAccession(String accessionSubstring) {
        return experimentTrader.getPublicExperiments()
                .stream()
                .filter(experiment -> containsIgnoreCase(experiment.getAccession(), accessionSubstring))
                .collect(toImmutableSet());
    }
}
