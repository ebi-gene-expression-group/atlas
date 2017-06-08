package uk.ac.ebi.atlas.experimentimport.analytics;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class SingleCellBaselineAnalyticsLoader implements AnalyticsLoader {

    private final SingleCellBaselineDao singleCellBaselineDao;
    private final SingleCellBaselineInputStreamFactory singleCellBaselineInputStreamFactory;

    @Inject
    public SingleCellBaselineAnalyticsLoader(SingleCellBaselineDao singleCellBaselineDao,
                                             SingleCellBaselineInputStreamFactory singleCellBaselineInputStreamFactory){
        this.singleCellBaselineDao = singleCellBaselineDao;
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
