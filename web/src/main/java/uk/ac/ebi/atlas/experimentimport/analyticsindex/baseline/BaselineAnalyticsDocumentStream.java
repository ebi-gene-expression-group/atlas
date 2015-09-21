package uk.ac.ebi.atlas.experimentimport.analyticsindex.baseline;

import com.google.common.base.Joiner;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsDocument;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.support.IdentifierSearchTermsDAO;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class BaselineAnalyticsDocumentStream implements Iterable<AnalyticsDocument> {

    private static final Logger LOGGER = Logger.getLogger(BaselineAnalyticsDocumentStream.class);

    private final String experimentAccession;
    private final ExperimentType experimentType;
    private final Map<String, String> ensemblSpeciesGroupedByAssayGroupId;
    private final String defaultQueryFactorType;
    private final Iterable<BaselineAnalytics> inputStream;
    private final SetMultimap<String, String> conditionSearchTermsByAssayAccessionId;
    private final IdentifierSearchTermsDAO identifierSearchTermsDAO;
    private final SpeciesKingdomTrader speciesKingdomTrader;

    public BaselineAnalyticsDocumentStream(String experimentAccession,
                                           ExperimentType experimentType,
                                           Map<String, String> ensemblSpeciesGroupedByAssayGroupId,
                                           String defaultQueryFactorType,
                                           Iterable<BaselineAnalytics> inputStream,
                                           SetMultimap<String, String> conditionSearchTermsByAssayAccessionId,
                                           IdentifierSearchTermsDAO identifierSearchTermsDAO,
                                           SpeciesKingdomTrader speciesKingdomTrader) {
        this.experimentAccession = experimentAccession;
        this.experimentType = experimentType;
        this.ensemblSpeciesGroupedByAssayGroupId = ensemblSpeciesGroupedByAssayGroupId;
        this.defaultQueryFactorType = defaultQueryFactorType;
        this.inputStream = inputStream;
        this.conditionSearchTermsByAssayAccessionId = conditionSearchTermsByAssayAccessionId;
        this.identifierSearchTermsDAO = identifierSearchTermsDAO;
        this.speciesKingdomTrader = speciesKingdomTrader;
    }

    @Override
    public Iterator<AnalyticsDocument> iterator() {
        return new AnalyticsDocumentIterator(inputStream);
    }

    private final class AnalyticsDocumentIterator implements Iterator<AnalyticsDocument> {

        private Iterator<BaselineAnalytics> inputIterator;
        private String lastSeenGeneId = "";
        private String lastGeneIdSearchTerms;
        private Set<String> assaysSeen = Sets.newHashSet();

        private AnalyticsDocumentIterator(Iterable<BaselineAnalytics> inputStream) {
            inputIterator = inputStream.iterator();
        }

        @Override
        public boolean hasNext() {
            return inputIterator.hasNext();
        }

        @Override
        public AnalyticsDocument next() {
            BaselineAnalytics baselineAnalytics = inputIterator.next();

            String geneId = baselineAnalytics.getGeneId();
//            String identifierSearch = fetchIdentifierSearchTerms(geneId);

            String assayGroupId = baselineAnalytics.getAssayGroupId();
            String conditionSearch = getConditionSearchTerms(assayGroupId);

            AnalyticsDocument.Builder builder = AnalyticsDocument.builder();
            builder.experimentAccession(experimentAccession)
                    .experimentType(experimentType)
                    .defaultQueryFactorType(defaultQueryFactorType)
                    .species(getEnsemblSpecies(assayGroupId))
                    .kingdom(speciesKingdomTrader.getKingdom(getEnsemblSpecies(assayGroupId)))
                    .bioentityIdentifier(geneId)
                    .expressionLevel(baselineAnalytics.getExpressionLevel())
//                    .identifierSearch(identifierSearch)
                    .assayGroupId(assayGroupId)
                    .conditionsSearch(conditionSearch);

            return builder.build();
        }

        private String getEnsemblSpecies(String assayGroupId) {
            String ensemblSpecies = ensemblSpeciesGroupedByAssayGroupId.get(assayGroupId);
            checkNotNull(ensemblSpecies, "No species for assay group " + assayGroupId);
            return ensemblSpecies;
        }

        private String getConditionSearchTerms(String assayGroupId) {
            Set<String> searchTerms = conditionSearchTermsByAssayAccessionId.get(assayGroupId);

            if (searchTerms.isEmpty() && !assaysSeen.contains(assayGroupId)) {
                assaysSeen.add(assayGroupId);
                LOGGER.warn("No condition search terms found for " + assayGroupId);
            }

            return Joiner.on(" ").join(searchTerms);
        }

        private String fetchIdentifierSearchTerms(String geneId) {
            if (!lastSeenGeneId.equals(geneId)) {
                lastSeenGeneId = geneId;
                Set<String> searchTerms = identifierSearchTermsDAO.fetchSearchTerms(geneId);

                if (searchTerms.isEmpty()) {
                    LOGGER.warn("No identifier search terms found for " + geneId);
                }
                lastGeneIdSearchTerms = geneId + (searchTerms.isEmpty() ? "" : " " + Joiner.on(" ").join(searchTerms));
            }

            return lastGeneIdSearchTerms;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
