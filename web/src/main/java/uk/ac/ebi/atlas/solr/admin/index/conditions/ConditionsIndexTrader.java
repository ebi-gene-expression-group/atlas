package uk.ac.ebi.atlas.solr.admin.index.conditions;

import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.solr.admin.index.conditions.baseline.BaselineConditionsIndex;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialConditionsIndex;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ConditionsIndexTrader {

    private BaselineConditionsIndex baselineConditionIndex;
    private DifferentialConditionsIndex differentialConditionIndex;

    @Inject
    public ConditionsIndexTrader(BaselineConditionsIndex baselineConditionIndex, DifferentialConditionsIndex differentialConditionIndex) {
        this.baselineConditionIndex = baselineConditionIndex;
        this.differentialConditionIndex = differentialConditionIndex;
    }

    public ConditionsIndex getIndex(ExperimentType experimentType) {
        if (experimentType.isBaseline()) {
            return baselineConditionIndex;
        }
        return differentialConditionIndex;
    }
}
