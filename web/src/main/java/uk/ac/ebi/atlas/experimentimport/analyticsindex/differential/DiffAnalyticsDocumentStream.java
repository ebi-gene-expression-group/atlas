package uk.ac.ebi.atlas.experimentimport.analyticsindex.differential;

import com.google.common.base.Joiner;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsDocument;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.support.IdentifierSearchTermsDao;
import uk.ac.ebi.atlas.model.ExperimentType;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class DiffAnalyticsDocumentStream implements Iterable<AnalyticsDocument> {

    private static final Logger LOGGER = Logger.getLogger(DiffAnalyticsDocumentStream.class);

    private final String experimentAccession;
    private final ExperimentType experimentType;
    private final Map<String, String> ensemblSpeciesGroupedByContrastId;
    private final Iterable<? extends DifferentialAnalytics> inputStream;
    private final SetMultimap<String, String> conditionSearchTermsByAssayAccessionId;
    private final IdentifierSearchTermsDao identifierSearchTermsDao;
    private final Set<String> factors;

    public DiffAnalyticsDocumentStream(String experimentAccession,
                                       ExperimentType experimentType,
                                       Set<String> factors,
                                       Map<String, String> ensemblSpeciesGroupedByContrastId,
                                       Iterable<? extends DifferentialAnalytics> inputStream,
                                       SetMultimap<String, String> conditionSearchTermsByAssayAccessionId,
                                       IdentifierSearchTermsDao identifierSearchTermsDao) {
        this.experimentAccession = experimentAccession;
        this.experimentType = experimentType;
        this.factors = factors;
        this.ensemblSpeciesGroupedByContrastId = ensemblSpeciesGroupedByContrastId;
        this.inputStream = inputStream;
        this.conditionSearchTermsByAssayAccessionId = conditionSearchTermsByAssayAccessionId;
        this.identifierSearchTermsDao = identifierSearchTermsDao;
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
                    .bioentityIdentifier(geneId)
                    .identifierSearch(identifierSearch)
                    .contrastId(contrastId)
                    .factors(factors)
                    .foldChange(analytics.getFoldChange())
                    .numReplicates(-1)
                    .conditionsSearch(conditionSearch);

            return builder.build();
        }

        private String getEnsemblSpecies(String contrastId) {
            String ensemblSpecies = DiffAnalyticsDocumentStream.this.ensemblSpeciesGroupedByContrastId.get(contrastId);
            checkNotNull(ensemblSpecies, "No species for assay group " + contrastId);
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
