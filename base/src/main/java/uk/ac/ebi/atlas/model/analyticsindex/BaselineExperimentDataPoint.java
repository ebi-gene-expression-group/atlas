package uk.ac.ebi.atlas.model.analyticsindex;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;

public class BaselineExperimentDataPoint extends ExperimentDataPoint{

    public BaselineExperimentDataPoint(BaselineExperiment experiment,
                                       BaselineAnalytics baselineAnalytics,
                                       String conditionSearch) {
        super(baselineAnalytics.geneId(), experiment, conditionSearch);

        propertyMap.put("default_query_factor_type", experiment.getDisplayDefaults().defaultQueryFactorType());
        propertyMap.put("assay_group_id", baselineAnalytics.assayGroupId());

        // If there are only TPMs, FPKM will be 0, and vice versa
        if (baselineAnalytics.expressionLevel() != 0) {
            propertyMap.put("expression_level", baselineAnalytics.expressionLevel());
        }
        if (baselineAnalytics.expressionLevelFpkm() != 0) {
            propertyMap.put("expression_level_fpkm", baselineAnalytics.expressionLevelFpkm());
        }
    }

    @Override
    public ImmutableList<BioentityPropertyName> getRelevantBioentityPropertyNames() {
        return bioentityPropertyNames.subList(0, bioentityPropertyNames.size() - 1);
    }

}

