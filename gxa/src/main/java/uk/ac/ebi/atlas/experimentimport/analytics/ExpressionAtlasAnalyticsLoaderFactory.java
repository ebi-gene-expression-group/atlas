package uk.ac.ebi.atlas.experimentimport.analytics;

import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalyticsLoader;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsLoader;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ExpressionAtlasAnalyticsLoaderFactory implements AnalyticsLoaderFactory {

    private final RnaSeqDifferentialAnalyticsLoader rnaSeqDifferentialAnalyticsLoader;
    private final MicroarrayDifferentialAnalyticsLoader microarrayDifferentialAnalyticsLoader;

    @Inject
    public ExpressionAtlasAnalyticsLoaderFactory(RnaSeqDifferentialAnalyticsLoader rnaSeqDifferentialAnalyticsLoader,
                                                 MicroarrayDifferentialAnalyticsLoader microarrayDifferentialAnalyticsLoader) {

        this.rnaSeqDifferentialAnalyticsLoader = rnaSeqDifferentialAnalyticsLoader;
        this.microarrayDifferentialAnalyticsLoader = microarrayDifferentialAnalyticsLoader;
    }



    @Override
    public AnalyticsLoader getLoader(ExperimentType experimentType) {
        if (experimentType == ExperimentType.RNASEQ_MRNA_DIFFERENTIAL) {
            return rnaSeqDifferentialAnalyticsLoader;
        } else if (experimentType.isMicroarray()) {
            return microarrayDifferentialAnalyticsLoader;
        }
        throw new UnsupportedOperationException("No analytics loader for experiment type " + experimentType);
    }
}
