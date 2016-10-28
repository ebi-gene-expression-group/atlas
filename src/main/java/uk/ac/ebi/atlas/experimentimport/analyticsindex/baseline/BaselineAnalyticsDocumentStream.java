package uk.ac.ebi.atlas.experimentimport.analyticsindex.baseline;

import com.google.common.base.Joiner;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsDocument;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.trader.SpeciesFactory;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Deprecated
public class BaselineAnalyticsDocumentStream implements Iterable<AnalyticsDocument> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineAnalyticsDocumentStream.class);

    private final String experimentAccession;
    private final ExperimentType experimentType;
    private final Species species;
    private final String defaultQueryFactorType;
    private final Iterable<BaselineAnalytics> inputStream;
    private final SetMultimap<String, String> conditionSearchTermsByAssayAccessionId;
    private final Map<String, String> bioentityIdToIdentifierSearch;

    public BaselineAnalyticsDocumentStream(BaselineExperiment experiment,
                                           Iterable<BaselineAnalytics> inputStream,
                                           SetMultimap<String, String> conditionSearchTermsByAssayAccessionId,
                                           Map<String, String> bioentityIdToIdentifierSearch) {
        this.experimentAccession = experiment.getAccession();
        this.experimentType = experiment.getType();
        this.species = experiment.getSpecies();
        this.defaultQueryFactorType = experiment.getExperimentalFactors().getDefaultQueryFactorType();
        this.inputStream = inputStream;
        this.conditionSearchTermsByAssayAccessionId = conditionSearchTermsByAssayAccessionId;
        this.bioentityIdToIdentifierSearch = bioentityIdToIdentifierSearch;
    }

    @Override
    public Iterator<AnalyticsDocument> iterator() {
        return new AnalyticsDocumentIterator(inputStream);
    }

    private final class AnalyticsDocumentIterator implements Iterator<AnalyticsDocument> {

        private Iterator<BaselineAnalytics> inputIterator;
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
            String identifierSearch =
                    geneId +
                    (isEmpty(bioentityIdToIdentifierSearch.get(geneId)) ?
                            ""
                            :
                            " " + bioentityIdToIdentifierSearch.get(geneId));

            String assayGroupId = baselineAnalytics.getAssayGroupId();
            String conditionSearch = getConditionSearchTerms(assayGroupId);

            AnalyticsDocument.Builder builder = AnalyticsDocument.builder();
            builder.experimentAccession(experimentAccession)
                    .experimentType(experimentType)
                    .defaultQueryFactorType(defaultQueryFactorType)
                    .species(species.mappedName)
                    .kingdom(species.kingdom)
                    .bioentityIdentifier(geneId)
                    .expressionLevel(baselineAnalytics.getExpressionLevel())
                    .identifierSearch(identifierSearch)
                    .assayGroupId(assayGroupId)
                    .conditionsSearch(conditionSearch);

            return builder.build();
        }

        private String getConditionSearchTerms(String assayGroupId) {
            Set<String> searchTerms = conditionSearchTermsByAssayAccessionId.get(assayGroupId);

            if (searchTerms.isEmpty() && !assaysSeen.contains(assayGroupId)) {
                assaysSeen.add(assayGroupId);
                LOGGER.warn("No condition search terms found for {}", assayGroupId);
            }

            return Joiner.on(" ").join(searchTerms);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
