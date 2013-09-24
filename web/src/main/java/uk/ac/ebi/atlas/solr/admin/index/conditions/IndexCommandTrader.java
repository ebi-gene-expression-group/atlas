package uk.ac.ebi.atlas.solr.admin.index.conditions;

import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentTrader;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.solr.admin.index.conditions.baseline.BaselineConditionsIndex;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialConditionsIndex;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class IndexCommandTrader {

    private ExperimentTrader experimentTrader;

    private BaselineConditionsIndex baselineConditionIndex;
    private DifferentialConditionsIndex differentialConditionIndex;

    @Inject
    public IndexCommandTrader(ExperimentTrader experimentTrader, BaselineConditionsIndex baselineConditionIndex, DifferentialConditionsIndex differentialConditionIndex) {
        this.experimentTrader = experimentTrader;
        this.baselineConditionIndex = baselineConditionIndex;
        this.differentialConditionIndex = differentialConditionIndex;
    }

    public IndexCommand getIndexCommand(String experimentAccession, IndexOperation indexOperation) {
        Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);
        return getIndexCommand(experiment, indexOperation);
    }

    protected IndexCommand getIndexCommand(Experiment experiment, IndexOperation indexOperation) {
        if (experiment instanceof DifferentialExperiment) {
            return indexOperation.getIndexCommand((DifferentialExperiment) experiment, differentialConditionIndex);
        } else if (experiment instanceof BaselineExperiment) {
            return indexOperation.getIndexCommand((BaselineExperiment) experiment, baselineConditionIndex);
        }

        throw new IllegalStateException("Experiment " + experiment.getAccession() + " cannot be indexed");
    }


}
