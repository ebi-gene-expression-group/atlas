package uk.ac.ebi.atlas.experimentimport.analyticsindex.differential;

import com.google.common.base.Joiner;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsDocument;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.support.IdentifierSearchTermsDao;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class DiffAnalyticsDocumentStream implements Iterable<AnalyticsDocument> {

    private static final Logger LOGGER = Logger.getLogger(DiffAnalyticsDocumentStream.class);

    private final String experimentAccession;
    private final ExperimentType experimentType;
    private final Map<String, String> ensemblSpeciesByContrastId;
    private final Map<String, Integer> numReplicatesByContrastId;
    private final Iterable<? extends DifferentialAnalytics> inputStream;
    private final SetMultimap<String, String> conditionSearchTermsByContrastId;
    private final IdentifierSearchTermsDao identifierSearchTermsDao;
    private final Set<String> factors;
    private final SpeciesKingdomTrader speciesKingdomTrader;

    public DiffAnalyticsDocumentStream(String experimentAccession,
                                       ExperimentType experimentType,
                                       Set<String> factors,
                                       Map<String, String> ensemblSpeciesByContrastId,
                                       Iterable<? extends DifferentialAnalytics> inputStream,
                                       SetMultimap<String, String> conditionSearchTermsByContrastId,
                                       Map<String, Integer> numReplicatesByContrastId,
                                       IdentifierSearchTermsDao identifierSearchTermsDao,
                                       SpeciesKingdomTrader speciesKingdomTrader) {
        this.experimentAccession = experimentAccession;
        this.experimentType = experimentType;
        this.factors = factors;
        this.ensemblSpeciesByContrastId = ensemblSpeciesByContrastId;
        this.inputStream = inputStream;
        this.conditionSearchTermsByContrastId = conditionSearchTermsByContrastId;
        this.identifierSearchTermsDao = identifierSearchTermsDao;
        this.numReplicatesByContrastId = numReplicatesByContrastId;
        this.speciesKingdomTrader = speciesKingdomTrader;
    }

    @Override
    public Iterator<AnalyticsDocument> iterator() {
        return new DiffAnalyticsDocumentIterator(inputStream);
    }

    private final class DiffAnalyticsDocumentIterator implements Iterator<AnalyticsDocument> {

        private Iterator<? extends DifferentialAnalytics> inputIterator;
        private String lastSeenGeneId = "";
        private String lastGeneIdSearchTerms;
        private Set<String> assaysSeen = Sets.newHashSet();

        private DiffAnalyticsDocumentIterator(Iterable<? extends DifferentialAnalytics> inputStream) {
            inputIterator = inputStream.iterator();
        }

        @Override
        public boolean hasNext() {
            return inputIterator.hasNext();
        }

        @Override
        public AnalyticsDocument next() {
            DifferentialAnalytics analytics = inputIterator.next();

            String geneId = analytics.getGeneId();
            String identifierSearch = fetchIdentifierSearchTerms(geneId);

            String contrastId = analytics.getContrastId();
            String conditionSearch = getConditionSearchTerms(contrastId);

            AnalyticsDocument.Builder builder = AnalyticsDocument.builder();
            builder.experimentAccession(experimentAccession)
                    .experimentType(experimentType)
                    .species(getEnsemblSpecies(contrastId))
                    .kingdom(speciesKingdomTrader.getKingdom(getEnsemblSpecies(contrastId)))
                    .ensemblDB(speciesKingdomTrader.getEnsemblDB(getEnsemblSpecies(contrastId)))
                    .bioentityIdentifier(geneId)
                    .identifierSearch(identifierSearch)
                    .contrastId(contrastId)
                    .factors(factors)
                    .foldChange(analytics.getFoldChange())
                    .numReplicates(getNumReplicates(contrastId))
                    .conditionsSearch(conditionSearch);

            return builder.build();
        }

        private int getNumReplicates(String contrastId) {
            int numReplicates = numReplicatesByContrastId.get(contrastId);
            checkNotNull(numReplicates, "No replicates for contrast " + contrastId);
            return numReplicates;
        }

        private String getEnsemblSpecies(String contrastId) {
            String ensemblSpecies = ensemblSpeciesByContrastId.get(contrastId);
            checkNotNull(ensemblSpecies, "No species for contrast " + contrastId);
            return ensemblSpecies;
        }

        private String getConditionSearchTerms(String contrastId) {
            Set<String> searchTerms = conditionSearchTermsByContrastId.get(contrastId);

            if (searchTerms.isEmpty() && !assaysSeen.contains(contrastId)) {
                assaysSeen.add(contrastId);
                LOGGER.warn("No condition search terms found for " + contrastId);
            }

            return Joiner.on(" ").join(searchTerms);
        }

        private String fetchIdentifierSearchTerms(String geneId) {
            if (!lastSeenGeneId.equals(geneId)) {
                lastSeenGeneId = geneId;
                Set<String> searchTerms = identifierSearchTermsDao.fetchSearchTerms(geneId);

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
