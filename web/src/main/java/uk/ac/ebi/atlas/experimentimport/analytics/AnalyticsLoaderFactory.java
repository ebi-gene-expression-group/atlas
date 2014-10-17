package uk.ac.ebi.atlas.experimentimport.analytics;

import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class AnalyticsLoaderFactory {

    private AnalyticsLoader baselineAnalyticsLoader;
    private AnalyticsLoader rnaSeqDifferentialAnalyticsLoader;
    private AnalyticsLoader microarrayDifferentialAnalyticsLoader;
    private AnalyticsLoader baselineProteomicsAnalyticsLoader;

    @Inject
    public AnalyticsLoaderFactory(AnalyticsLoader baselineAnalyticsLoader,
                                  AnalyticsLoader rnaSeqDifferentialAnalyticsLoader,
                                  AnalyticsLoader microarrayDifferentialAnalyticsLoader,
                                  AnalyticsLoader baselineProteomicsAnalyticsLoader) {
        this.baselineAnalyticsLoader = baselineAnalyticsLoader;
        this.rnaSeqDifferentialAnalyticsLoader = rnaSeqDifferentialAnalyticsLoader;
        this.microarrayDifferentialAnalyticsLoader = microarrayDifferentialAnalyticsLoader;
        this.baselineProteomicsAnalyticsLoader = baselineProteomicsAnalyticsLoader;
    }

    public AnalyticsLoader getLoader(ExperimentType experimentType) {
        if (experimentType == ExperimentType.RNASEQ_MRNA_BASELINE) {
            return baselineAnalyticsLoader;
        } else if (experimentType == ExperimentType.PROTEOMICS_BASELINE) {
            return baselineProteomicsAnalyticsLoader;
        } else if (experimentType == ExperimentType.RNASEQ_MRNA_DIFFERENTIAL) {
            return rnaSeqDifferentialAnalyticsLoader;
        } else if (experimentType.isMicroarray()) {
            return microarrayDifferentialAnalyticsLoader;
        }
        throw new UnsupportedOperationException("No analytics loader for experiment type " + experimentType);
    }
}
