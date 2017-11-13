package uk.ac.ebi.atlas.trader;

import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ExecutionException;

@Named
public class ContrastTrader {

    private ExperimentTrader experimentTrader;

    @Inject
    public ContrastTrader(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    public Contrast getContrast(String experimentAccession, String contrastId) {
        Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);
        //ToDo: we have to class cast here, to fix we need to have a common type for all diff experiments
        return ((DifferentialExperiment)experiment).getDataColumnDescriptor(contrastId);
    }

    public Contrast getContrastFromCache(String experimentAccession, ExperimentType experimentType, String contrastId) {
        try {
            Experiment experiment = experimentTrader.getExperimentFromCache(experimentAccession, experimentType);
            return ((DifferentialExperiment)experiment).getDataColumnDescriptor(contrastId);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
