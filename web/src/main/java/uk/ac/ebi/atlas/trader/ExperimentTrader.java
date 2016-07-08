package uk.ac.ebi.atlas.trader;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.UncheckedExecutionException;
import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.trader.cache.*;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Named
public class ExperimentTrader {

    private RnaSeqBaselineExperimentsCache rnaSeqBaselineExperimentsCache;
    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache;
    private MicroarrayExperimentsCache microarrayExperimentsCache;
    private ExperimentDAO experimentDAO;
    private ProteomicsBaselineExperimentsCache proteomicsBaselineExperimentsCache;
    private PublicExperimentTypesCache publicExperimentTypesCache;


    @Inject
    public ExperimentTrader(ExperimentDAO experimentDAO,
                            RnaSeqBaselineExperimentsCache rnaSeqBaselineExperimentsCache,
                            RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache,
                            MicroarrayExperimentsCache microarrayExperimentsCache,
                            ProteomicsBaselineExperimentsCache proteomicsBaselineExperimentsCache,
                            PublicExperimentTypesCache publicExperimentTypesCache) {

        this.experimentDAO = experimentDAO;
        this.rnaSeqBaselineExperimentsCache = rnaSeqBaselineExperimentsCache;
        this.rnaSeqDiffExperimentsCache = rnaSeqDiffExperimentsCache;
        this.microarrayExperimentsCache = microarrayExperimentsCache;
        this.proteomicsBaselineExperimentsCache = proteomicsBaselineExperimentsCache;
        this.publicExperimentTypesCache = publicExperimentTypesCache;
    }


    public Experiment getPublicExperiment(String experimentAccession) {
        ExperimentType experimentType;
        try {
            experimentType = publicExperimentTypesCache.getExperimentType(experimentAccession);
        } catch (UncheckedExecutionException | NullPointerException e) {
            throw new ResourceNotFoundException("Experiment: " + experimentAccession + " not found");
        }

        return getExperimentFromCache(experimentAccession, experimentType);
    }


    public Experiment getExperiment(String experimentAccession, String accessKey) {
        if (StringUtils.isBlank(accessKey)){
            return getPublicExperiment(experimentAccession);
        }
        ExperimentDTO experimentDTO = experimentDAO.findExperiment(experimentAccession, accessKey);

        return getExperimentFromCache(experimentAccession, experimentDTO.getExperimentType());
    }


    public void removeExperimentFromCache(String experimentAccession, ExperimentType type) {

        switch (type) {
            case RNASEQ_MRNA_BASELINE:
                rnaSeqBaselineExperimentsCache.evictExperiment(experimentAccession);
                break;
            case RNASEQ_MRNA_DIFFERENTIAL:
                rnaSeqDiffExperimentsCache.evictExperiment(experimentAccession);
                break;
            case MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL:
            case MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL:
            case MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL:
                microarrayExperimentsCache.evictExperiment(experimentAccession);
                break;
            case PROTEOMICS_BASELINE:
                proteomicsBaselineExperimentsCache.evictExperiment(experimentAccession);
                break;
            default:
                throw new IllegalStateException("invalid enum value: " + type);
        }
        publicExperimentTypesCache.evictExperiment(experimentAccession);

    }


    public void removeAllExperimentsFromCache() {
        rnaSeqBaselineExperimentsCache.evictAll();
        rnaSeqDiffExperimentsCache.evictAll();
        microarrayExperimentsCache.evictAll();
        proteomicsBaselineExperimentsCache.evictAll();
        publicExperimentTypesCache.evictAll();
    }


    public Experiment getExperimentFromCache(String experimentAccession, ExperimentType experimentType) {

        try {
            switch (experimentType) {
                case RNASEQ_MRNA_BASELINE:
                    return rnaSeqBaselineExperimentsCache.getExperiment(experimentAccession);
                case RNASEQ_MRNA_DIFFERENTIAL:
                    return rnaSeqDiffExperimentsCache.getExperiment(experimentAccession);
                case MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL:
                case MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL:
                case MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL:
                    return microarrayExperimentsCache.getExperiment(experimentAccession);
                case PROTEOMICS_BASELINE:
                    return proteomicsBaselineExperimentsCache.getExperiment(experimentAccession);
                default:
                    throw new IllegalStateException("Invalid enum value: " + experimentType);
            }
        } catch (ExecutionException e) {
            throw new IllegalStateException("Failed to load experiment from cache: " + experimentAccession, e);
        }
    }

    public Set<String> getBaselineExperimentAccessions() {
        return getPublicExperimentAccessions(ExperimentType.RNASEQ_MRNA_BASELINE);
    }


    public Set<String> getProteomicsBaselineExperimentAccessions() {
        return getPublicExperimentAccessions(ExperimentType.PROTEOMICS_BASELINE);
    }


    public Set<String> getAllBaselineExperimentAccessions() {
        return ImmutableSet.<String>builder().
                addAll(getBaselineExperimentAccessions()).
                addAll(getProteomicsBaselineExperimentAccessions()).
                build();

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


    public Set<String> getPublicExperimentAccessions(ExperimentType... experimentType) {
        return experimentDAO.findPublicExperimentAccessions(experimentType.length == 0 ? ExperimentType.values() : experimentType);
    }

}
