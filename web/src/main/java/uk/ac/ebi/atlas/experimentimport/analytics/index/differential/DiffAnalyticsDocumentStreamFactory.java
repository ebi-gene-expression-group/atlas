package uk.ac.ebi.atlas.experimentimport.analytics.index.differential;

import com.google.common.collect.SetMultimap;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.index.support.IdentifierSearchTermsDao;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

@Named
public class DiffAnalyticsDocumentStreamFactory {

    private final IdentifierSearchTermsDao identifierSearchTermsDao;

    @Inject
    public DiffAnalyticsDocumentStreamFactory(IdentifierSearchTermsDao identifierSearchTermsDao) {
        this.identifierSearchTermsDao = identifierSearchTermsDao;
    }

    public DiffAnalyticsDocumentStream create(String experimentAccession,
                                          ExperimentType experimentType,
                                          Map<String, String> ensemblSpeciesGroupedByAssayGroupId,
                                          Iterable<? extends DifferentialAnalytics> inputStream,
                                          SetMultimap<String, String> conditionSearchTermsByAssayAccessionId) {
        return new DiffAnalyticsDocumentStream(experimentAccession, experimentType, ensemblSpeciesGroupedByAssayGroupId,
                inputStream, conditionSearchTermsByAssayAccessionId, identifierSearchTermsDao);
    }

}
