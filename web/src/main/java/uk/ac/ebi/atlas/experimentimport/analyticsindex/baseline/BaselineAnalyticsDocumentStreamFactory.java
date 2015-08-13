package uk.ac.ebi.atlas.experimentimport.analyticsindex.baseline;

import com.google.common.collect.SetMultimap;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.support.IdentifierSearchTermsDAO;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

@Named
public class BaselineAnalyticsDocumentStreamFactory {

    private final IdentifierSearchTermsDAO identifierSearchTermsDAO;
    private final SpeciesKingdomTrader speciesKingdomTrader;

    @Inject
    public BaselineAnalyticsDocumentStreamFactory(IdentifierSearchTermsDAO identifierSearchTermsDAO, SpeciesKingdomTrader speciesKingdomTrader) {
        this.identifierSearchTermsDAO = identifierSearchTermsDAO;
        this.speciesKingdomTrader = speciesKingdomTrader;
    }

    public BaselineAnalyticsDocumentStream create(String experimentAccession,
                                          ExperimentType experimentType,
                                          Map<String, String> ensemblSpeciesGroupedByAssayGroupId,
                                          String defaultQueryFactorType,
                                          Iterable<BaselineAnalytics> inputStream,
                                          SetMultimap<String, String> conditionSearchTermsByAssayAccessionId) {
        return new BaselineAnalyticsDocumentStream(experimentAccession, experimentType, ensemblSpeciesGroupedByAssayGroupId, defaultQueryFactorType,
                inputStream, conditionSearchTermsByAssayAccessionId, identifierSearchTermsDAO, speciesKingdomTrader);
    }

}
