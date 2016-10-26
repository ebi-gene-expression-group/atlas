package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.baseline.BaselineAnalyticsIndexerService;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.differential.RnaSeqDiffAnalyticsIndexerService;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.differential.MicroArrayDiffAnalyticsIndexerService;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

@Named
public class AnalyticsIndexerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsIndexerService.class);

    private final AnalyticsIndexDAO analyticsIndexDAO;
    private final BaselineAnalyticsIndexerService baselineAnalyticsIndexerService;
    private final RnaSeqDiffAnalyticsIndexerService diffAnalyticsIndexerService;
    private final MicroArrayDiffAnalyticsIndexerService microArrayDiffAnalyticsIndexerService;

    @Inject
    public AnalyticsIndexerService(AnalyticsIndexDAO analyticsIndexDAO, BaselineAnalyticsIndexerService baselineAnalyticsIndexerService, RnaSeqDiffAnalyticsIndexerService diffAnalyticsIndexerService, MicroArrayDiffAnalyticsIndexerService microArrayDiffAnalyticsIndexerService) {
        this.analyticsIndexDAO = analyticsIndexDAO;
        this.baselineAnalyticsIndexerService = baselineAnalyticsIndexerService;
        this.diffAnalyticsIndexerService = diffAnalyticsIndexerService;
        this.microArrayDiffAnalyticsIndexerService = microArrayDiffAnalyticsIndexerService;
    }

    public int index(Experiment experiment, Map<String, String> bioentityIdToIdentifierSearch, int batchSize) {
       ExperimentType experimentType = experiment.getType();

        if (experimentType.isBaseline()) {
            return baselineAnalyticsIndexerService.index((BaselineExperiment) experiment, bioentityIdToIdentifierSearch, batchSize);
        } else if (experimentType == ExperimentType.RNASEQ_MRNA_DIFFERENTIAL) {
            return diffAnalyticsIndexerService.index((DifferentialExperiment) experiment, bioentityIdToIdentifierSearch, batchSize);
        } else if (experimentType == ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL ||
                   experimentType == ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL ||
                   experimentType == ExperimentType.MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL) {
            return microArrayDiffAnalyticsIndexerService.index((MicroarrayExperiment) experiment, bioentityIdToIdentifierSearch, batchSize);
        }

        throw new UnsupportedOperationException("No analytics loader for experiment type " + experimentType);
    }

    // synchronized necessary because analyticsIndexDao#delete does an explicit commit
    public synchronized void deleteExperimentFromIndex(String accession) {
        LOGGER.info("Deleting documents for {}", accession);
        analyticsIndexDAO.deleteDocumentsForExperiment(accession);
        LOGGER.info("Done deleting documents for {}", accession);
    }

    public synchronized void deleteAll() {
        LOGGER.info("Deleting all documents");
        analyticsIndexDAO.deleteAllDocuments();
        LOGGER.info("Done deleting all documents");
    }

    public synchronized void optimize() {
        LOGGER.info("Optimizing index");
        analyticsIndexDAO.optimize();
        LOGGER.info("Index optimized successfully");
    }
}
