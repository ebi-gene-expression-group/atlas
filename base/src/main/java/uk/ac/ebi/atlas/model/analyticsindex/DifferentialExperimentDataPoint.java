package uk.ac.ebi.atlas.model.analyticsindex;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.Regulation;

public class DifferentialExperimentDataPoint extends ExperimentDataPoint{

    public DifferentialExperimentDataPoint(DifferentialExperiment experiment,
                                           DifferentialAnalytics differentialAnalytics,
                                           String conditionSearch,
                                           int numReplicates) {
        super(differentialAnalytics.getGeneId(), experiment,conditionSearch);

        propertyMap.put("factors", experiment.getExperimentDesign().getFactorHeaders());
        propertyMap.put("regulation", Regulation.valueOf(differentialAnalytics.getFoldChange()).toString());
        propertyMap.put("contrast_id", differentialAnalytics.getContrastId());
        propertyMap.put("num_replicates", numReplicates);
        propertyMap.put("fold_change", differentialAnalytics.getFoldChange());
        propertyMap.put("p_value", differentialAnalytics.getpValue());
    }

    @Override
    public ImmutableList<BioentityPropertyName> getRelevantBioentityPropertyNames(){
        return bioentityPropertyNames.subList(0, bioentityPropertyNames.size()-2);
    }
}
