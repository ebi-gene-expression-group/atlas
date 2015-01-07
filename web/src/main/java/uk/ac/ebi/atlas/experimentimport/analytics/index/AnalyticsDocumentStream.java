package uk.ac.ebi.atlas.experimentimport.analytics.index;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Sets;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.model.ExperimentType;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class AnalyticsDocumentStream implements Iterable<AnalyticsDocument> {

    private static final Logger LOGGER = Logger.getLogger(AnalyticsDocumentStream.class);

    private final String experimentAccession;
    private final ExperimentType experimentType;
    private final String ensemblSpecies;
    private final String defaultQueryFactorType;
    private final Iterable<BaselineAnalytics> inputStream;
    private final ImmutableSetMultimap<String, String> conditionSearchTermsByAssayAccessionId;
    private final IdentifierSearchTermsDao identifierSearchTermsDao;

    public AnalyticsDocumentStream(String experimentAccession,
                                   ExperimentType experimentType,
                                   String ensemblSpecies,
                                   String defaultQueryFactorType,
                                   Iterable<BaselineAnalytics> inputStream,
                                   ImmutableSetMultimap<String, String> conditionSearchTermsByAssayAccessionId,
                                   IdentifierSearchTermsDao identifierSearchTermsDao) {
        this.experimentAccession = experimentAccession;
        this.experimentType = experimentType;
        this.ensemblSpecies = ensemblSpecies;
        this.defaultQueryFactorType = defaultQueryFactorType;
        this.inputStream = inputStream;
        this.conditionSearchTermsByAssayAccessionId = conditionSearchTermsByAssayAccessionId;
        this.identifierSearchTermsDao = identifierSearchTermsDao;
    }

    @Override
    public Iterator<AnalyticsDocument> iterator() {
        return new AnalyticsDocumentIterator(inputStream);
    }

    private final class AnalyticsDocumentIterator implements Iterator<AnalyticsDocument> {

        private Iterator<BaselineAnalytics> inputIterator;
        private String lastSeenGeneId = "";
        private String lastGeneIdSearchTerms;
        private HashSet<String> assaysSeen = Sets.newHashSet();

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
            String identifierSearch = fetchIdentifierSearchTerms(geneId);

            String assayGroupId = baselineAnalytics.getAssayGroupId();
            String conditionSearch = getConditionSearchTerms(assayGroupId);

            AnalyticsDocument.Builder builder = AnalyticsDocument.builder();
            builder.experimentAccession(experimentAccession)
                    .experimentType(experimentType)
                    .defaultQueryFactorType(defaultQueryFactorType)
                    .species(ensemblSpecies)
                    .bioentityIdentifier(geneId)
                    .expressionLevel(baselineAnalytics.getExpressionLevel())
                    .identifierSearch(identifierSearch)
                    .assayGroupId(assayGroupId)
                    .conditionsSearch(conditionSearch);

            return builder.build();
        }

        private String getConditionSearchTerms(String assayGroupId) {
            ImmutableSet<String> searchTerms = conditionSearchTermsByAssayAccessionId.get(assayGroupId);


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
                lastGeneIdSearchTerms = geneId + " " + Joiner.on(" ").join(searchTerms);
            }

            return lastGeneIdSearchTerms;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
