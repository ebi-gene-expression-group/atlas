package uk.ac.ebi.atlas.experimentimport.analytics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalyticsLoader;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsLoader;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class GxaAnalyticsLoaderFactory implements AnalyticsLoaderFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(GxaAnalyticsLoaderFactory.class);

    private final RnaSeqDifferentialAnalyticsLoader rnaSeqDifferentialAnalyticsLoader;
    private final MicroarrayDifferentialAnalyticsLoader microarrayDifferentialAnalyticsLoader;

    @Inject
    public GxaAnalyticsLoaderFactory(RnaSeqDifferentialAnalyticsLoader rnaSeqDifferentialAnalyticsLoader,
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
        } else {
            LOGGER.warn("No analytics loader for experiment type {} (skipping)",  experimentType);
            return new AnalyticsLoader() {};
        }
    }
}
