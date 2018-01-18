package uk.ac.ebi.atlas.model.analyticsindex;

import com.google.common.base.Joiner;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

public class BaselineExperimentDataPointStream implements ObjectInputStream<BaselineExperimentDataPoint> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineExperimentDataPointStream.class);

    private final BaselineExperiment experiment;
    private final ObjectInputStream<BaselineAnalytics> inputStream;
    private final Multimap<String, String> conditionSearchTermsByAssayAccessionId;
    private final Set<String> assaysSeen = Sets.newHashSet();

    public BaselineExperimentDataPointStream(BaselineExperiment experiment,
                                             ObjectInputStream<BaselineAnalytics> inputStream,
                                             Multimap<String, String> conditionSearchTermsByAssayAccessionId) {
        this.experiment = experiment;
        this.inputStream = inputStream;
        this.conditionSearchTermsByAssayAccessionId = conditionSearchTermsByAssayAccessionId;
    }


    private String getConditionSearchTerms(String assayGroupId) {
        Collection<String> searchTerms = conditionSearchTermsByAssayAccessionId.get(assayGroupId);

        if (searchTerms.isEmpty() && !assaysSeen.contains(assayGroupId)) {
            assaysSeen.add(assayGroupId);
            LOGGER.warn("No condition search terms found for {}", assayGroupId);
        }

        return Joiner.on(" ").join(searchTerms);
    }

    @Override
    public BaselineExperimentDataPoint readNext() {
        BaselineAnalytics baselineAnalytics = inputStream.readNext();

        if (baselineAnalytics != null) {
            return new BaselineExperimentDataPoint(
                    experiment, baselineAnalytics, getConditionSearchTerms(baselineAnalytics.getAssayGroupId()));
        } else {
            return null;
        }

    }

    @Override
    public void close() throws Exception {
        inputStream.close();
    }

}
