package uk.ac.ebi.atlas.experimentimport.analytics;

import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalyticsDAO;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.ProteomicsBaselineAnalyticsLoader;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.RnaSeqBaselineAnalyticsLoader;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalyticsDao;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalyticsLoader;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsDao;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsLoader;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class AnalyticsLoaderFactory {

    private RnaSeqBaselineAnalyticsLoader rnaSeqBaselineAnalyticsLoader;
    private ProteomicsBaselineAnalyticsLoader proteomicsBaselineAnalyticsLoader;
    private RnaSeqDifferentialAnalyticsLoader rnaSeqDifferentialAnalyticsLoader;
    private MicroarrayDifferentialAnalyticsLoader microarrayDifferentialAnalyticsLoader;

    @Inject
    public AnalyticsLoaderFactory(BaselineAnalyticsDAO baselineAnalyticsDAO,
                                  BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory,
                                  RnaSeqDifferentialAnalyticsDao rnaSeqDifferentialAnalyticsDao,
                                  RnaSeqDifferentialAnalyticsInputStreamFactory
                                              rnaSeqDifferentialAnalyticsInputStreamFactory,
                                  MicroarrayDifferentialAnalyticsDao microarrayDifferentialAnalyticsDao,
                                  MicroarrayDifferentialAnalyticsInputStreamFactory
                                              microarrayDifferentialAnalyticsInputStreamFactory,
                                  ConfigurationTrader configurationTrader

                                  ){
        this.rnaSeqBaselineAnalyticsLoader = new RnaSeqBaselineAnalyticsLoader(baselineAnalyticsDAO,
                baselineAnalyticsInputStreamFactory);
        this.proteomicsBaselineAnalyticsLoader = new ProteomicsBaselineAnalyticsLoader(baselineAnalyticsDAO,
                baselineAnalyticsInputStreamFactory);
        this.rnaSeqDifferentialAnalyticsLoader = new RnaSeqDifferentialAnalyticsLoader
                (rnaSeqDifferentialAnalyticsDao,rnaSeqDifferentialAnalyticsInputStreamFactory);
        this.microarrayDifferentialAnalyticsLoader = new MicroarrayDifferentialAnalyticsLoader
                (microarrayDifferentialAnalyticsDao,microarrayDifferentialAnalyticsInputStreamFactory,
                        configurationTrader);
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
