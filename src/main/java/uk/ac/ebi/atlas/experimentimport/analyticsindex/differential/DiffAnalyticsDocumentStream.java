package uk.ac.ebi.atlas.experimentimport.analyticsindex.differential;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalytics;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsDocument;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.trader.SpeciesFactory;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Deprecated
public class DiffAnalyticsDocumentStream implements Iterable<AnalyticsDocument> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiffAnalyticsDocumentStream.class);

    private final String experimentAccession;
    private final ExperimentType experimentType;
    private final Species species;
    private final Map<String, Integer> numReplicatesByContrastId;
    private final Iterable<? extends DifferentialAnalytics> inputStream;
    private final SetMultimap<String, String> conditionSearchTermsByContrastId;
    private final Set<String> factors;
    private final Map<String, String> bioentityIdToIdentifierSearch;

    public DiffAnalyticsDocumentStream(DifferentialExperiment experiment,
                                       Iterable<? extends DifferentialAnalytics> inputStream,
                                       SetMultimap<String, String> conditionSearchTermsByContrastId,
                                       Map<String, Integer> numReplicatesByContrastId,
                                       Map<String, String> bioentityIdToIdentifierSearch) {
        this.experimentAccession = experiment.getAccession();
        this.experimentType = experiment.getType();
        this.factors = experiment.getExperimentDesign().getFactorHeaders();
        this.species = experiment.getSpecies();
        this.inputStream = inputStream;
        this.conditionSearchTermsByContrastId = conditionSearchTermsByContrastId;
        this.numReplicatesByContrastId = numReplicatesByContrastId;
        this.bioentityIdToIdentifierSearch = bioentityIdToIdentifierSearch;
    }

    @Override
    public Iterator<AnalyticsDocument> iterator() {
        return new DiffAnalyticsDocumentIterator(inputStream);
    }

    private final class DiffAnalyticsDocumentIterator implements Iterator<AnalyticsDocument> {

        private Iterator<? extends DifferentialAnalytics> inputIterator;
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
            String identifierSearch =  geneId +
                    (isEmpty(bioentityIdToIdentifierSearch.get(geneId)) ?
                            ""
                            :
                            " " + bioentityIdToIdentifierSearch.get(geneId));

            String contrastId = analytics.getContrastId();
            String conditionSearch = getConditionSearchTerms(contrastId, factors);

            return AnalyticsDocument.builder()
                    .experimentAccession(experimentAccession)
                    .experimentType(experimentType)
                    .species(species.mappedName)
                    .kingdom(species.kingdom)
                    .bioentityIdentifier(geneId)
                    .identifierSearch(identifierSearch)
                    .contrastId(contrastId)
                    .factors(factors)
                    .foldChange(analytics.getFoldChange())
                    .pValue(analytics.getpValue())
                    .numReplicates(getNumReplicates(contrastId))
                    .conditionsSearch(conditionSearch)
                    .tStatistics(analytics.getTStatistic())
                    .build();
        }

        private int getNumReplicates(String contrastId) {
            int numReplicates = numReplicatesByContrastId.get(contrastId);
            checkNotNull(numReplicates, "No replicates for contrast " + contrastId);
            return numReplicates;
        }

        private String getConditionSearchTerms(String contrastId, Set<String> factors) {
            Set<String> searchTerms = conditionSearchTermsByContrastId.get(contrastId);

            if (searchTerms.isEmpty() && !assaysSeen.contains(contrastId)) {
                assaysSeen.add(contrastId);
                LOGGER.warn("No condition search terms found for {}", contrastId);
            }

            ImmutableList.Builder<String> conditionSearchTermsBuilder = new ImmutableList.Builder<>();
            return Joiner.on(" ").join(conditionSearchTermsBuilder.addAll(searchTerms).addAll(factors).build());
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
