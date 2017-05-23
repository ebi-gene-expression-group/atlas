package uk.ac.ebi.atlas.experimentimport.analytics.singlecell;

import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class SingleCellBaselineLoader implements AnalyticsLoader {

    private SingleCellBaselineDao singleCellBaselineDao;
    private SingleCellBaselineInputStreamFactory singleCellBaselineInputStreamFactory;

    @Inject
    public void setSingleCellBaselineDao(SingleCellBaselineDao singleCellBaselineDao) {
        this.singleCellBaselineDao = singleCellBaselineDao;
    }

    @Inject
    public void setSingleCellBaselineInputStreamFactory(SingleCellBaselineInputStreamFactory singleCellBaselineInputStreamFactory) {
        this.singleCellBaselineInputStreamFactory = singleCellBaselineInputStreamFactory;
    }

    @Override
    public void loadAnalytics(String accession) throws IOException {
        SingleCellBaselineInputStream scInputStream = singleCellBaselineInputStreamFactory.create(accession);
        singleCellBaselineDao.loadAnalytics(accession, scInputStream);
    }

    @Override
    public void deleteAnalytics(String accession) {
        singleCellBaselineDao.deleteAnalytics(accession);
    }

}
