package uk.ac.ebi.atlas.experiments;

import com.atlassian.util.concurrent.LazyReference;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class LatestExperimentsService {

    private final LatestExperimentsDao latestExperimentsDao;
    private final ExperimentTrader experimentTrader;
    private final ImmutableSet<ExperimentType> experimentTypes;

    private final LazyReference<ImmutableMap<String, Object>> latestExperimentsAttributes = new LazyReference<ImmutableMap<String, Object>>() {
        @Override
        protected ImmutableMap<String, Object> create() throws Exception {

            long experimentCount = latestExperimentsDao.fetchPublicExperimentsCount(experimentTypes);

            List<ExperimentInfo> latestExperimentInfo =
                    FluentIterable.from(latestExperimentsDao.fetchLatestExperimentAccessions(experimentTypes))
                            .transform(new Function<String, Experiment>() {
                                @Override
                                public Experiment apply(String experimentAccession) {
                                    return experimentTrader.getPublicExperiment(experimentAccession);
                                }})
                            .transform(new Function<Experiment, ExperimentInfo>() {
                                @Override
                                public ExperimentInfo apply(Experiment experiment) {
                                    return experiment.buildExperimentInfo();
                                }
                            }).toList();

            return ImmutableMap.of(
                    "experimentCount", experimentCount,
                    "formattedExperimentCount", NumberFormat.getNumberInstance(Locale.US).format(experimentCount),
                    "latestExperiments", latestExperimentInfo);
        }
    };

    public LatestExperimentsService(LatestExperimentsDao latestExperimentsDao,
                                    ExperimentTrader experimentTrader,
                                    Set<ExperimentType> experimentTypes) {
        this.latestExperimentsDao = latestExperimentsDao;
        this.experimentTrader = experimentTrader;
        this.experimentTypes = ImmutableSet.copyOf(experimentTypes);
    }

    public ImmutableMap<String, Object> fetchLatestExperimentsAttributes() {
        return latestExperimentsAttributes.get();
    }
}
