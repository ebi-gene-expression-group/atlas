package uk.ac.ebi.atlas.experimentimport.analytics.singlecell;

import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;
import uk.ac.ebi.atlas.experimentimport.analytics.singlecell.analytics.AnalyticsDao;
import uk.ac.ebi.atlas.experimentimport.analytics.singlecell.analytics.AnalyticsStreamer;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SingleCellAnalyticsLoader implements AnalyticsLoader {
    private final DataFileHub dataFileHub;
    private final AnalyticsDao analyticsDao;

    @Inject
    SingleCellAnalyticsLoader(DataFileHub dataFileHub, AnalyticsDao analyticsDao) {
        this.dataFileHub = dataFileHub;
        this.analyticsDao = analyticsDao;
    }

    @Transactional
    @Override
    public void loadAnalytics(String experimentAccession) {
        loadExpression(experimentAccession);
    }

    @Transactional
    @Override
    public void deleteAnalytics(String experimentAccession) {
        deleteExpression(experimentAccession);
    }

    @Transactional
    public void reloadExpression(String experimentAccession) {
        deleteExpression(experimentAccession);
        loadExpression(experimentAccession);
    }

    private void loadExpression(String experimentAccession) {
        DataFileHub.SingleCellExperimentFiles files = dataFileHub.getSingleCellExperimentFiles(experimentAccession);

        try (AnalyticsStreamer analyticsStreamer =
                     new AnalyticsStreamer(files.tpmsMatrix, files.geneIdsTsv, files.cellIdsTsv)) {

            analyticsDao.loadAnalytics(experimentAccession, analyticsStreamer.get());
        }
    }

    private void deleteExpression(String experimentAccession) {
        analyticsDao.deleteAnalytics(experimentAccession);
    }
}
