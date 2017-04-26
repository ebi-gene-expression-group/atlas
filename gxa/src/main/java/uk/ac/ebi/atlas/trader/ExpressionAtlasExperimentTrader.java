package uk.ac.ebi.atlas.trader;

import org.slf4j.Logger;
import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.trader.cache.ExperimentsCache;
import uk.ac.ebi.atlas.trader.cache.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.trader.cache.ProteomicsBaselineExperimentsCache;
import uk.ac.ebi.atlas.trader.cache.PublicExperimentTypesCache;
import uk.ac.ebi.atlas.trader.cache.RnaSeqBaselineExperimentsCache;
import uk.ac.ebi.atlas.trader.cache.RnaSeqDiffExperimentsCache;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.UncheckedExecutionException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Named
public class ExpressionAtlasExperimentTrader extends ExperimentTrader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressionAtlasExperimentTrader.class);

    private final PublicExperimentTypesCache publicExperimentTypesCache;
    private final ImmutableMap<ExperimentType, ExperimentsCache<? extends Experiment>> experimentCachesPerType;

    @Inject
    public ExpressionAtlasExperimentTrader(ExperimentDAO experimentDAO,
                                           RnaSeqBaselineExperimentsCache rnaSeqBaselineExperimentsCache,
                                           RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache,
                                           MicroarrayExperimentsCache microarrayExperimentsCache,
                                           ProteomicsBaselineExperimentsCache proteomicsBaselineExperimentsCache,
                                           PublicExperimentTypesCache publicExperimentTypesCache) {
        super(experimentDAO);

        this.publicExperimentTypesCache = publicExperimentTypesCache;
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
    }

    @Override
    public Experiment getPublicExperiment(String experimentAccession) {
        ExperimentType experimentType;
        try {
            experimentType = publicExperimentTypesCache.getExperimentType(experimentAccession);
            return getExperimentFromCache(experimentAccession, experimentType);
        } catch (ExecutionException e) {
            LOGGER.error(e.getMessage());
            throw new ResourceNotFoundException("Failed to load <em>" + experimentAccession + "</em>.");
        }
        catch (UncheckedExecutionException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new ResourceNotFoundException("Experiment <em>" + experimentAccession + "</em> not found.");
        }
    }

    @Override
    public Experiment getExperiment(String experimentAccession, String accessKey) {
        if (StringUtils.isBlank(accessKey)){
            return getPublicExperiment(experimentAccession);
        }
        ExperimentDTO experimentDTO = experimentDAO.findExperiment(experimentAccession, accessKey);

        try {
            return getExperimentFromCache(experimentAccession, experimentDTO.getExperimentType());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeExperimentFromCache(String experimentAccession) {
        for(ExperimentsCache cache: experimentCachesPerType.values()){
            cache.evictExperiment(experimentAccession);
        }
        publicExperimentTypesCache.evictExperiment(experimentAccession);
    }

    @Override
    public void removeAllExperimentsFromCache() {
        for(ExperimentsCache cache: experimentCachesPerType.values()){
            cache.evictAll();
        }
        publicExperimentTypesCache.evictAll();
    }

    @Override
    public Experiment getExperimentFromCache(String experimentAccession, ExperimentType experimentType) throws ExecutionException {
        return experimentCachesPerType.get(experimentType).getExperiment(experimentAccession);
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
