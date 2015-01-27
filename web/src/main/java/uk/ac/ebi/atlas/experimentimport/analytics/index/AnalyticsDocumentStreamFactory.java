package uk.ac.ebi.atlas.experimentimport.analytics.index;

import com.google.common.collect.SetMultimap;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

@Named
public class AnalyticsDocumentStreamFactory {

    private final IdentifierSearchTermsDao identifierSearchTermsDao;

    @Inject
    public AnalyticsDocumentStreamFactory(IdentifierSearchTermsDao identifierSearchTermsDao) {
        this.identifierSearchTermsDao = identifierSearchTermsDao;
    }

    public AnalyticsDocumentStream create(String experimentAccession,
                                          ExperimentType experimentType,
                                          Map<String, String> ensemblSpeciesGroupedByAssayGroupId,
                                          String defaultQueryFactorType,
                                          Iterable<BaselineAnalytics> inputStream,
                                          SetMultimap<String, String> conditionSearchTermsByAssayAccessionId) {
        return new AnalyticsDocumentStream(experimentAccession, experimentType, ensemblSpeciesGroupedByAssayGroupId, defaultQueryFactorType,
                inputStream, conditionSearchTermsByAssayAccessionId, identifierSearchTermsDao);
    }

}
