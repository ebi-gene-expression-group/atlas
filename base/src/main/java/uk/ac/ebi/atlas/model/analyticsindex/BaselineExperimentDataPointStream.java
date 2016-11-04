package uk.ac.ebi.atlas.model.analyticsindex;

import com.google.common.base.Joiner;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.baseline.BaselineExperiment;

import java.util.Iterator;
import java.util.Set;

public class BaselineExperimentDataPointStream implements Iterable<BaselineExperimentDataPoint> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineExperimentDataPointStream.class);

    private final BaselineExperiment experiment;
    private final Iterable<BaselineAnalytics> inputStream;
    private final SetMultimap<String, String> conditionSearchTermsByAssayAccessionId;

    public BaselineExperimentDataPointStream(BaselineExperiment experiment,
                                             Iterable<BaselineAnalytics> inputStream,
                                             SetMultimap<String, String> conditionSearchTermsByAssayAccessionId) {
        this.experiment = experiment;
        this.inputStream = inputStream;
        this.conditionSearchTermsByAssayAccessionId = conditionSearchTermsByAssayAccessionId;
    }

    @Override
    public Iterator<BaselineExperimentDataPoint> iterator() {
        return new AnalyticsDocumentIterator(inputStream);
    }

    private final class AnalyticsDocumentIterator implements Iterator<BaselineExperimentDataPoint> {

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
        public BaselineExperimentDataPoint next() {
            BaselineAnalytics baselineAnalytics = inputIterator.next();

            return new BaselineExperimentDataPoint(experiment, baselineAnalytics,getConditionSearchTerms
                    (baselineAnalytics.getAssayGroupId()));
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
