package uk.ac.ebi.atlas.model.analyticsindex;

import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalytics;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;

public class MicroarrayExperimentDataPoint extends DifferentialExperimentDataPoint {

    public MicroarrayExperimentDataPoint(MicroarrayExperiment experiment,
                                         MicroarrayDifferentialAnalytics microarrayDifferentialAnalytics,
                                         String conditionSearch,
                                         int numReplicates) {
        super(experiment, microarrayDifferentialAnalytics, conditionSearch, numReplicates);
        propertyMap.put("t_statistic", microarrayDifferentialAnalytics.getTStatistic());
    }
}
