package uk.ac.ebi.atlas.model.analyticsindex;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class DifferentialExperimentDataPointStream implements Iterable<DifferentialExperimentDataPoint> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DifferentialExperimentDataPointStream.class);

    private final DifferentialExperiment experiment;
     private final Map<String, Integer> numReplicatesByContrastId;
    private final Iterable<? extends DifferentialAnalytics> inputStream;
    private final SetMultimap<String, String> conditionSearchTermsByContrastId;

    public DifferentialExperimentDataPointStream(DifferentialExperiment experiment,
                                                 Iterable<? extends DifferentialAnalytics> inputStream,
                                                 SetMultimap<String, String> conditionSearchTermsByContrastId,
                                                 Map<String, Integer> numReplicatesByContrastId) {
        this.experiment = experiment;
        this.inputStream = inputStream;
        this.conditionSearchTermsByContrastId = conditionSearchTermsByContrastId;
        this.numReplicatesByContrastId = numReplicatesByContrastId;
    }

    @Override
    public Iterator<DifferentialExperimentDataPoint> iterator() {
        return new DiffAnalyticsDocumentIterator(inputStream);
    }

    private final class DiffAnalyticsDocumentIterator implements Iterator<DifferentialExperimentDataPoint> {

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
        public DifferentialExperimentDataPoint next() {
            DifferentialAnalytics analytics = inputIterator.next();

            return new DifferentialExperimentDataPoint(experiment,analytics, getConditionSearchTerms(analytics
                    .getContrastId(), experiment.getExperimentDesign().getFactorHeaders()),getNumReplicates(analytics
                    .getContrastId()) );

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
