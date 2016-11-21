package uk.ac.ebi.atlas.experimentimport.analytics;

import uk.ac.ebi.atlas.experimentimport.analytics.baseline.ProteomicsBaselineAnalyticsLoader;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.RnaSeqBaselineAnalyticsLoader;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalyticsLoader;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsLoader;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class AnalyticsLoaderFactory {

    private final RnaSeqBaselineAnalyticsLoader rnaSeqBaselineAnalyticsLoader;
    private final ProteomicsBaselineAnalyticsLoader proteomicsBaselineAnalyticsLoader;
    private final RnaSeqDifferentialAnalyticsLoader rnaSeqDifferentialAnalyticsLoader;
    private final MicroarrayDifferentialAnalyticsLoader microarrayDifferentialAnalyticsLoader;

    @Inject
    public AnalyticsLoaderFactory(RnaSeqBaselineAnalyticsLoader rnaSeqBaselineAnalyticsLoader,
                                  ProteomicsBaselineAnalyticsLoader proteomicsBaselineAnalyticsLoader,
                                  RnaSeqDifferentialAnalyticsLoader rnaSeqDifferentialAnalyticsLoader,
                                  MicroarrayDifferentialAnalyticsLoader microarrayDifferentialAnalyticsLoader) {

        this.rnaSeqBaselineAnalyticsLoader = rnaSeqBaselineAnalyticsLoader;
        this.proteomicsBaselineAnalyticsLoader = proteomicsBaselineAnalyticsLoader;
        this.rnaSeqDifferentialAnalyticsLoader = rnaSeqDifferentialAnalyticsLoader;
        this.microarrayDifferentialAnalyticsLoader = microarrayDifferentialAnalyticsLoader;
    }



    public AnalyticsLoader getLoader(ExperimentType experimentType) {
        if (experimentType == ExperimentType.RNASEQ_MRNA_BASELINE) {
            return rnaSeqBaselineAnalyticsLoader;
        } else if (experimentType == ExperimentType.PROTEOMICS_BASELINE) {
            return proteomicsBaselineAnalyticsLoader;
        } else if (experimentType == ExperimentType.RNASEQ_MRNA_DIFFERENTIAL) {
            return rnaSeqDifferentialAnalyticsLoader;
        } else if (experimentType.isMicroarray()) {
            return microarrayDifferentialAnalyticsLoader;
        }
        throw new UnsupportedOperationException("No analytics loader for experiment type " + experimentType);
    }
}
