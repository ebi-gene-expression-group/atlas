package uk.ac.ebi.atlas.model.analyticsindex;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalytics;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class MicroarrayExperimentDataPointStream implements ObjectInputStream<MicroarrayExperimentDataPoint> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MicroarrayExperimentDataPointStream.class);

    private final MicroarrayExperiment experiment;
    private final Map<String, Integer> numReplicatesByContrastId;
    private final ImmutableCollection<ObjectInputStream<? extends MicroarrayDifferentialAnalytics>> inputStreams;
    private final Multimap<String, String> conditionSearchTermsByContrastId;
    private final Set<String> assaysSeen = Sets.newHashSet();
    private final Iterator<ObjectInputStream<? extends MicroarrayDifferentialAnalytics>> arrayDesignStreamIterator;
    private ObjectInputStream<? extends MicroarrayDifferentialAnalytics> currentInputStream;

    public MicroarrayExperimentDataPointStream(MicroarrayExperiment experiment,
                                               ImmutableCollection<ObjectInputStream<? extends MicroarrayDifferentialAnalytics>> inputStreams,
                                               Multimap<String, String> conditionSearchTermsByContrastId,
                                               Map<String, Integer> numReplicatesByContrastId) {
        this.experiment = experiment;
        this.inputStreams = inputStreams;
        this.conditionSearchTermsByContrastId = conditionSearchTermsByContrastId;
        this.numReplicatesByContrastId = numReplicatesByContrastId;
        this.arrayDesignStreamIterator = inputStreams.iterator();
        this.currentInputStream = this.arrayDesignStreamIterator.next();
    }

    @Override
    public MicroarrayExperimentDataPoint readNext() {

        MicroarrayDifferentialAnalytics analytics = currentInputStream.readNext();
        if (analytics == null) {
            if (arrayDesignStreamIterator.hasNext()) {
                currentInputStream = arrayDesignStreamIterator.next();
                return readNext();
            } else {
                return null;
            }
        } else {
            return new MicroarrayExperimentDataPoint(
                    experiment, analytics, getConditionSearchTerms(analytics.getContrastId(),
                    experiment.getExperimentDesign().getFactorHeaders()), getNumReplicates(analytics.getContrastId()));

        }
    }

    @Override
    public void close() throws IOException {
        for (ObjectInputStream inputStream : inputStreams) {
            inputStream.close();
        }
    }

    private int getNumReplicates(String contrastId) {
        int numReplicates = numReplicatesByContrastId.get(contrastId);
        checkNotNull(numReplicates, "No replicates for contrast " + contrastId);
        return numReplicates;
    }

    private String getConditionSearchTerms(String contrastId, Set<String> factors) {
        Collection<String> searchTerms = conditionSearchTermsByContrastId.get(contrastId);

        if (searchTerms.isEmpty() && !assaysSeen.contains(contrastId)) {
            assaysSeen.add(contrastId);
            LOGGER.warn("No condition search terms found for {}", contrastId);
        }

        ImmutableList.Builder<String> conditionSearchTermsBuilder = new ImmutableList.Builder<>();
        return Joiner.on(" ").join(conditionSearchTermsBuilder.addAll(searchTerms).addAll(factors).build());
    }

}
