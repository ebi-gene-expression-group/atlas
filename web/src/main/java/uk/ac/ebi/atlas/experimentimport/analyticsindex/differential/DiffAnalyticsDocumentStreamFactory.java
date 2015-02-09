package uk.ac.ebi.atlas.experimentimport.analyticsindex.differential;

import com.google.common.collect.SetMultimap;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.support.IdentifierSearchTermsDao;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.Set;

@Named
public class DiffAnalyticsDocumentStreamFactory {

    private final IdentifierSearchTermsDao identifierSearchTermsDao;

    @Inject
    public DiffAnalyticsDocumentStreamFactory(IdentifierSearchTermsDao identifierSearchTermsDao) {
        this.identifierSearchTermsDao = identifierSearchTermsDao;
    }

    public DiffAnalyticsDocumentStream create(String experimentAccession,
                                          ExperimentType experimentType,
                                          Set<String> factors,
                                          Map<String, String> ensemblSpeciesGroupedByAssayGroupId,
                                          Iterable<? extends DifferentialAnalytics> inputStream,
                                          SetMultimap<String, String> conditionSearchTermsByAssayAccessionId) {
        return new DiffAnalyticsDocumentStream(experimentAccession, experimentType, factors, ensemblSpeciesGroupedByAssayGroupId,
                inputStream, conditionSearchTermsByAssayAccessionId, identifierSearchTermsDao);
    }

}
