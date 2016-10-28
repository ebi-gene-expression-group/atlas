package uk.ac.ebi.atlas.model.analyticsindex;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.Regulation;

public class DifferentialExperimentDataPoint extends ExperimentDataPoint{

    private final boolean isMicroarray;

    public DifferentialExperimentDataPoint(DifferentialExperiment experiment,
                                           DifferentialAnalytics differentialAnalytics,
                                           String conditionSearch,
                                           int numReplicates) {
        super(differentialAnalytics.getGeneId(), experiment,conditionSearch);
        isMicroarray = experiment.getType().isMicroarray();

        propertyMap.put("factors", experiment.getExperimentDesign().getFactorHeaders());

        //TODO differentialConditions -> ConditionsSearch
        propertyMap.put("regulation", Regulation.valueOf(differentialAnalytics.getFoldChange()));
        propertyMap.put("contrastId", differentialAnalytics.getContrastId());
        propertyMap.put("numReplicates", numReplicates);
        propertyMap.put("foldChange", differentialAnalytics.getFoldChange());
        propertyMap.put("pValue", differentialAnalytics.getpValue());
        if(differentialAnalytics.getTStatistic()!=0){
            propertyMap.put("tStatistics", differentialAnalytics.getTStatistic());
        }

    }

    @Override
    public ImmutableList<BioentityPropertyName> getRelevantBioentityPropertyNames(){
        return isMicroarray
                ? bioentityPropertyNames
                : bioentityPropertyNames.subList(0, bioentityPropertyNames.size()-2);
    }
}
