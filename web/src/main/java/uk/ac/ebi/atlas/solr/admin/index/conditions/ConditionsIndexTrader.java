package uk.ac.ebi.atlas.solr.admin.index.conditions;

import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.solr.admin.index.conditions.baseline.BaselineConditionsIndex;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialConditionsIndex;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ConditionsIndexTrader {

    private BaselineConditionsIndex baselineConditionsIndex;
    private DifferentialConditionsIndex differentialConditionsIndex;

    @Inject
    public ConditionsIndexTrader(BaselineConditionsIndex baselineConditionsIndex, DifferentialConditionsIndex differentialConditionsIndex) {
        this.baselineConditionsIndex = baselineConditionsIndex;
        this.differentialConditionsIndex = differentialConditionsIndex;
    }

    public ConditionsIndex getIndex(ExperimentType experimentType) {
        if (experimentType.isBaseline()) {
            return baselineConditionsIndex;
        }
        return differentialConditionsIndex;
    }

}
