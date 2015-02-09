package uk.ac.ebi.atlas.experimentimport.analyticsindex.baseline;

import com.google.common.collect.SetMultimap;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.support.IdentifierSearchTermsDao;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

@Named
public class BaselineAnalyticsDocumentStreamFactory {

    private final IdentifierSearchTermsDao identifierSearchTermsDao;

    @Inject
    public BaselineAnalyticsDocumentStreamFactory(IdentifierSearchTermsDao identifierSearchTermsDao) {
        this.identifierSearchTermsDao = identifierSearchTermsDao;
    }

    public BaselineAnalyticsDocumentStream create(String experimentAccession,
                                          ExperimentType experimentType,
                                          Map<String, String> ensemblSpeciesGroupedByAssayGroupId,
                                          String defaultQueryFactorType,
                                          Iterable<BaselineAnalytics> inputStream,
                                          SetMultimap<String, String> conditionSearchTermsByAssayAccessionId) {
        return new BaselineAnalyticsDocumentStream(experimentAccession, experimentType, ensemblSpeciesGroupedByAssayGroupId, defaultQueryFactorType,
                inputStream, conditionSearchTermsByAssayAccessionId, identifierSearchTermsDao);
    }

}
