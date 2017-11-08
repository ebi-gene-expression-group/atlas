package uk.ac.ebi.atlas.trader;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.trader.cache.ExperimentsCache;
import uk.ac.ebi.atlas.trader.cache.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.trader.cache.ProteomicsBaselineExperimentsCache;
import uk.ac.ebi.atlas.trader.cache.RnaSeqBaselineExperimentsCache;
import uk.ac.ebi.atlas.trader.cache.RnaSeqDiffExperimentsCache;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Named
public class ExpressionAtlasExperimentTrader extends ExperimentTrader {

    private final ImmutableMap<ExperimentType, ExperimentsCache<? extends Experiment>> experimentCachesPerType;

    private final LoadingCache<Pair<String, String>, ExperimentType> experimentTypes;

    @Inject
    public ExpressionAtlasExperimentTrader(ExperimentDAO experimentDAO,
                                           RnaSeqBaselineExperimentsCache rnaSeqBaselineExperimentsCache,
                                           RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache,
                                           MicroarrayExperimentsCache microarrayExperimentsCache,
                                           ProteomicsBaselineExperimentsCache proteomicsBaselineExperimentsCache) {
        super(experimentDAO);

        ImmutableMap.Builder<ExperimentType, ExperimentsCache<? extends Experiment>> builder = ImmutableMap.builder();

        builder.put(ExperimentType.RNASEQ_MRNA_BASELINE, rnaSeqBaselineExperimentsCache)
               .put(ExperimentType.PROTEOMICS_BASELINE, proteomicsBaselineExperimentsCache)
               .put(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL, rnaSeqDiffExperimentsCache);

        for (ExperimentType type: ExperimentType.values()) {
            if (type.isMicroarray()) {
                builder.put(type, microarrayExperimentsCache);
            }
        }
        experimentCachesPerType = builder.build();
        experimentTypes = CacheBuilder.newBuilder().build(new CacheLoader<Pair<String, String>, ExperimentType>() {
            @Override
            public ExperimentType load(Pair<String, String> p) throws Exception {
                return experimentDAO.findExperiment(p.getLeft(), p.getRight()).getExperimentType();
            }
        });
    }

    @Override
    public Experiment getExperiment(String experimentAccession, String accessKey) {
        try {
            return getExperimentFromCache(experimentAccession, experimentTypes.get(Pair.of(experimentAccession, accessKey)));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Experiment getExperimentFromCache(String experimentAccession, ExperimentType experimentType) {
        try {
            return experimentCachesPerType.get(experimentType).getExperiment(experimentAccession);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeExperimentFromCache(String experimentAccession) {
        for(ExperimentsCache cache: experimentCachesPerType.values()){
            cache.evictExperiment(experimentAccession);
        }
        experimentTypes.invalidateAll();
    }


    public Set<String> getAllBaselineExperimentAccessions() {
        return getPublicExperimentAccessions(ExperimentType.RNASEQ_MRNA_BASELINE,ExperimentType.PROTEOMICS_BASELINE );
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
