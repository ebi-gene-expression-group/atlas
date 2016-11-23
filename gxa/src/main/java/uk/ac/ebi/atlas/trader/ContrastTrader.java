package uk.ac.ebi.atlas.trader;

import uk.ac.ebi.atlas.model.experiment.Experiment;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;

import javax.inject.Inject;
import javax.inject.Named;

@Named()
@Scope("singleton")
public class ContrastTrader {

    private ExperimentTrader experimentTrader;

    @Inject
    public ContrastTrader(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    public Contrast getContrast(String experimentAccession, String contrastId) {
        Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);
        //ToDo: we have to class cast here, to fix we need to have a common type for all diff experiments
        return ((DifferentialExperiment)experiment).getContrast(contrastId);
    }

    public Contrast getContrastFromCache(String experimentAccession, ExperimentType experimentType, String contrastId) {
        Experiment experiment = experimentTrader.getExperimentFromCache(experimentAccession, experimentType);
        return ((DifferentialExperiment)experiment).getContrast(contrastId);
    }
}
