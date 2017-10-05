package uk.ac.ebi.atlas.model.analyticsindex;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class DifferentialExperimentDataPointStream implements ObjectInputStream<DifferentialExperimentDataPoint> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DifferentialExperimentDataPointStream.class);

    protected final DifferentialExperiment experiment;
    protected final Map<String, Integer> numReplicatesByContrastId;
    protected final ObjectInputStream<? extends DifferentialAnalytics> inputStream;
    protected final Multimap<String, String> conditionSearchTermsByContrastId;
    protected final Set<String> assaysSeen = Sets.newHashSet();

    public DifferentialExperimentDataPointStream(DifferentialExperiment experiment,
                                                 ObjectInputStream<? extends DifferentialAnalytics> inputStream,
                                                 Multimap<String, String> conditionSearchTermsByContrastId,
                                                 Map<String, Integer> numReplicatesByContrastId) {
        this.experiment = experiment;
        this.inputStream = inputStream;
        this.conditionSearchTermsByContrastId = conditionSearchTermsByContrastId;
        this.numReplicatesByContrastId = numReplicatesByContrastId;
    }

    @Override
    public DifferentialExperimentDataPoint readNext() {
        DifferentialAnalytics analytics = inputStream.readNext();

        if (analytics != null) {
            return new DifferentialExperimentDataPoint(experiment, analytics, getConditionSearchTerms(analytics
                    .getContrastId(), experiment.getExperimentDesign().getFactorHeaders()), getNumReplicates(analytics
                    .getContrastId()));
        } else {
            return null;
        }
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
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
