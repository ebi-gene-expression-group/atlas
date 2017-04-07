package uk.ac.ebi.atlas.model.analyticsindex;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;

public class BaselineExperimentDataPoint extends ExperimentDataPoint{


    public BaselineExperimentDataPoint
            (BaselineExperiment experiment, BaselineAnalytics baselineAnalytics, String conditionSearch) {
        super(baselineAnalytics.getGeneId(), experiment, conditionSearch);
        propertyMap.put("defaultQueryFactorType", experiment.getDisplayDefaults().defaultQueryFactorType());
        propertyMap.put("assayGroupId", baselineAnalytics.getAssayGroupId());
        propertyMap.put("expressionLevel", baselineAnalytics.getExpressionLevel());

    }

    @Override
    public ImmutableList<BioentityPropertyName> getRelevantBioentityPropertyNames() {
        return bioentityPropertyNames.subList(0, bioentityPropertyNames.size()-2);
    }
}
