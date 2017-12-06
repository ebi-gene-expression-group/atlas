package uk.ac.ebi.atlas.trader;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.experimentimport.GxaExperimentDao;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.trader.cache.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Named
public class ExpressionAtlasExperimentTrader extends ExperimentTrader {

    private final ImmutableMap<ExperimentType, ExperimentsCache<? extends Experiment>> experimentCachesPerType;

    @Inject
    public ExpressionAtlasExperimentTrader(GxaExperimentDao experimentDao,
                                           RnaSeqBaselineExperimentsCache rnaSeqBaselineExperimentsCache,
                                           RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache,
                                           MicroarrayExperimentsCache microarrayExperimentsCache,
                                           ProteomicsBaselineExperimentsCache proteomicsBaselineExperimentsCache) {
        super(experimentDao);

        ImmutableMap.Builder<ExperimentType, ExperimentsCache<? extends Experiment>> builder = ImmutableMap.builder();

        builder.put(ExperimentType.RNASEQ_MRNA_BASELINE, rnaSeqBaselineExperimentsCache)
                .put(ExperimentType.PROTEOMICS_BASELINE, proteomicsBaselineExperimentsCache)
                .put(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL, rnaSeqDiffExperimentsCache);

        for (ExperimentType type : ExperimentType.values()) {
            if (type.isMicroarray()) {
                builder.put(type, microarrayExperimentsCache);
            }
        }
        experimentCachesPerType = builder.build();

    }

    private ConcurrentHashMap<Pair<String, String>, ExperimentType> experimentTypes = new ConcurrentHashMap<>();

    private ExperimentType getExperimentType(String experimentAccession, String accessKey) {
        Pair<String, String> k = Pair.of(experimentAccession, accessKey);
        experimentTypes.computeIfAbsent(k, k_ -> experimentDao.findExperiment(k_.getLeft(), k_.getRight()).getExperimentType());
        return experimentTypes.get(k);
    }

    @Override
    public Experiment getExperiment(String experimentAccession, String accessKey) {
        return getExperimentFromCache(experimentAccession, getExperimentType(experimentAccession, accessKey));
    }

    @Override
    public Experiment getExperimentFromCache(String experimentAccession, ExperimentType experimentType) {
        return experimentCachesPerType.get(experimentType).getExperiment(experimentAccession);
    }

    @Override
    public void removeExperimentFromCache(String experimentAccession) {
        for (ExperimentsCache cache : experimentCachesPerType.values()) {
            cache.evictExperiment(experimentAccession);
        }
        experimentTypes.clear();
    }


    public Set<String> getAllBaselineExperimentAccessions() {
        return getPublicExperimentAccessions(ExperimentType.RNASEQ_MRNA_BASELINE, ExperimentType.PROTEOMICS_BASELINE);
    }


    public Set<String> getRnaSeqDifferentialExperimentAccessions() {
        return getPublicExperimentAccessions(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL);
    }

    public Set<String> getMicroarrayExperimentAccessions() {
        Set<String> identifiers = Sets.newHashSet();
        identifiers.addAll(getPublicExperimentAccessions(ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL));
        // as two colour is a subtype of micro array, they need to be added here
        identifiers.addAll((getPublicExperimentAccessions(ExperimentType.MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL)));
        identifiers.addAll((getPublicExperimentAccessions(ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL)));
        return identifiers;
    }
}
