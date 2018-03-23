package uk.ac.ebi.atlas.model.analyticsindex;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Doubles;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;

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
            propertyMap.put("expression_levels", Doubles.asList(baselineAnalytics.expressionLevels()));
        }
        if (baselineAnalytics.expressionLevelFpkm() != 0) {
            propertyMap.put("expression_level_fpkm", baselineAnalytics.expressionLevelFpkm());
            propertyMap.put("expression_levels_fpkm", Doubles.asList(baselineAnalytics.expressionLevelsFpkm()));
        }
    }

    @Override
    public ImmutableList<BioentityPropertyName> getRelevantBioentityPropertyNames() {
        return bioentityPropertyNames.subList(0, bioentityPropertyNames.size() - 1);
    }

}
