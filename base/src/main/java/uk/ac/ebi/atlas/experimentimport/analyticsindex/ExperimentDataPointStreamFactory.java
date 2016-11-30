package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.model.analyticsindex.MicroarrayExperimentDataPoint;
import uk.ac.ebi.atlas.model.analyticsindex.MicroarrayExperimentDataPointStream;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.analyticsindex.BaselineExperimentDataPoint;
import uk.ac.ebi.atlas.model.analyticsindex.BaselineExperimentDataPointStream;
import uk.ac.ebi.atlas.model.analyticsindex.DifferentialExperimentDataPoint;
import uk.ac.ebi.atlas.model.analyticsindex.DifferentialExperimentDataPointStream;
import uk.ac.ebi.atlas.model.analyticsindex.ExperimentDataPoint;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.solr.admin.index.conditions.Condition;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsLookupService;
import uk.ac.ebi.atlas.solr.admin.index.conditions.DifferentialCondition;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

@Named
public class ExperimentDataPointStreamFactory {
    private final ConditionsLookupService conditionsLookupService;
    private final MicroarrayDifferentialAnalyticsInputStreamFactory microarrayDifferentialAnalyticsInputStreamFactory;
    private final RnaSeqDifferentialAnalyticsInputStreamFactory rnaSeqDifferentialAnalyticsInputStreamFactory;
    private final BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory;

    @Inject
    public ExperimentDataPointStreamFactory(ConditionsLookupService conditionsLookupService,
                                            MicroarrayDifferentialAnalyticsInputStreamFactory
                                                         microarrayDifferentialAnalyticsInputStreamFactory,
                                            RnaSeqDifferentialAnalyticsInputStreamFactory
                                                        rnaSeqDifferentialAnalyticsInputStreamFactory,
                                            BaselineAnalyticsInputStreamFactory
                                                        baselineAnalyticsInputStreamFactory) {

        this.conditionsLookupService = conditionsLookupService;
        this.microarrayDifferentialAnalyticsInputStreamFactory = microarrayDifferentialAnalyticsInputStreamFactory;
        this.rnaSeqDifferentialAnalyticsInputStreamFactory = rnaSeqDifferentialAnalyticsInputStreamFactory;
        this.baselineAnalyticsInputStreamFactory = baselineAnalyticsInputStreamFactory;

    }

    public ObjectInputStream<? extends ExperimentDataPoint> stream(Experiment experiment) throws IOException {
        if(experiment instanceof MicroarrayExperiment) {
            return stream((MicroarrayExperiment) experiment);
        } else if (experiment instanceof DifferentialExperiment) {
            return stream((DifferentialExperiment) experiment);
        } else if (experiment instanceof BaselineExperiment) {
            return stream((BaselineExperiment) experiment);
        } else {
            throw new IllegalStateException();
        }
    }

    private ObjectInputStream<BaselineExperimentDataPoint> stream(BaselineExperiment experiment) throws IOException {
        return new BaselineExperimentDataPointStream(
                experiment,
                baselineAnalyticsInputStreamFactory.create(experiment.getAccession(), experiment.getType()),
                buildAssayGroupIdToConditionsSearchTerms(experiment));
    }

    private ObjectInputStream<DifferentialExperimentDataPoint> stream(DifferentialExperiment experiment) throws IOException {
        return new DifferentialExperimentDataPointStream(
                experiment,
                rnaSeqDifferentialAnalyticsInputStreamFactory.create(experiment.getAccession()),
                buildConditionSearchTermsByAssayGroupId(experiment),
                buildNumReplicatesByContrastId(experiment));
    }

    private ObjectInputStream<MicroarrayExperimentDataPoint> stream(MicroarrayExperiment experiment) throws IOException {
        Iterator<String> it = experiment.getArrayDesignAccessions().iterator();
        ImmutableList.Builder<ObjectInputStream<? extends MicroarrayDifferentialAnalytics>> builder = ImmutableList.builder();
        while (it.hasNext()) {
            builder.add(microarrayDifferentialAnalyticsInputStreamFactory.create(experiment.getAccession(), it.next()));
        }

        return new MicroarrayExperimentDataPointStream(
                experiment,
                builder.build(),
                buildConditionSearchTermsByAssayGroupId(experiment),
                buildNumReplicatesByContrastId(experiment));
    }

    private Map<String, Integer> buildNumReplicatesByContrastId(DifferentialExperiment experiment) {
        ImmutableMap.Builder<String, Integer> builder = ImmutableMap.builder();

        for (Contrast contrast : experiment.getContrasts()) {
            int numReplicates = Math.min(contrast.getReferenceAssayGroup().getReplicates(), contrast.getTestAssayGroup().getReplicates());
            builder.put(contrast.getId(), numReplicates);
        }

        return builder.build();
    }

    private ImmutableSetMultimap<String, String> buildAssayGroupIdToConditionsSearchTerms(BaselineExperiment experiment) {
        Collection<Condition> conditions = conditionsLookupService.buildPropertiesForBaselineExperiment(experiment
                .getAccession(),
                experiment.getExperimentDesign(), experiment.getAssayGroups());
        ImmutableSetMultimap.Builder<String, String> builder = ImmutableSetMultimap.builder();

        for (Condition condition : conditions) {
            builder.putAll(condition.getAssayGroupId(), condition.getValues());
        }

        return builder.build();
    }

    private ImmutableSetMultimap<String, String> buildConditionSearchTermsByAssayGroupId(DifferentialExperiment experiment) {
        Collection<DifferentialCondition> conditions = conditionsLookupService.buildPropertiesForDifferentialExperiment
                (experiment.getAccession(), experiment
                .getExperimentDesign(), experiment.getContrasts());

        ImmutableSetMultimap.Builder<String, String> builder = ImmutableSetMultimap.builder();

        for (DifferentialCondition condition : conditions) {
            builder.putAll(condition.getContrastId(), condition.getValues());
        }

        return builder.build();
    }
}
