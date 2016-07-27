package uk.ac.ebi.atlas.experimentimport.analyticsindex.differential;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import uk.ac.ebi.atlas.experimentimport.efo.EFOLookupService;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.support.SpeciesGrouper;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialCondition;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialConditionsBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Named
public class RnaSeqDiffAnalyticsIndexerService {

    private final EFOLookupService efoParentsLookupService;
    private final DifferentialConditionsBuilder diffConditionsBuilder;
    private final RnaSeqDiffAnalyticsDocumentStreamIndexer rnaSeqDiffAnalyticsDocumentStreamIndexer;

    @Inject
    public RnaSeqDiffAnalyticsIndexerService(EFOLookupService efoParentsLookupService, DifferentialConditionsBuilder diffConditionsBuilder, RnaSeqDiffAnalyticsDocumentStreamIndexer rnaSeqDiffAnalyticsDocumentStreamIndexer) {
        this.efoParentsLookupService = efoParentsLookupService;
        this.diffConditionsBuilder = diffConditionsBuilder;
        this.rnaSeqDiffAnalyticsDocumentStreamIndexer = rnaSeqDiffAnalyticsDocumentStreamIndexer;
    }

    public int index(DifferentialExperiment experiment, Map<String, String> bioentityIdToIdentifierSearch, int batchSize) {
        String experimentAccession = experiment.getAccession();
        ExperimentType experimentType = experiment.getType();
        ExperimentDesign experimentDesign = experiment.getExperimentDesign();

        ImmutableMap<String, String> ensemblSpeciesGroupedByContrastId = SpeciesGrouper.buildEnsemblSpeciesGroupedByContrastId(experiment);
        ImmutableSetMultimap<String, String> ontologyTermIdsByAssayAccession = efoParentsLookupService
                .expandOntologyTerms(experimentDesign.getAllOntologyTermIdsByAssayAccession());
        ImmutableSetMultimap<String, String> conditionSearchTermsByContrastId = buildConditionSearchTermsByAssayGroupId(experiment, ontologyTermIdsByAssayAccession);
        Set<String> factors = experimentDesign.getFactorHeaders();
        Map<String, Integer> numReplicatesByContrastId = buildNumReplicatesByContrastId(experiment);

        return  rnaSeqDiffAnalyticsDocumentStreamIndexer.index(experimentAccession, experimentType, factors,
                conditionSearchTermsByContrastId, ensemblSpeciesGroupedByContrastId, numReplicatesByContrastId, bioentityIdToIdentifierSearch, batchSize);
    }

    private Map<String, Integer> buildNumReplicatesByContrastId(DifferentialExperiment experiment) {
        ImmutableMap.Builder<String, Integer> builder = ImmutableMap.builder();

        for (Contrast contrast : experiment.getContrasts()) {
            int numReplicates = Math.min(contrast.getReferenceAssayGroup().getReplicates(), contrast.getTestAssayGroup().getReplicates());
            builder.put(contrast.getId(), numReplicates);
        }

        return builder.build();
    }

    private ImmutableSetMultimap<String, String> buildConditionSearchTermsByAssayGroupId(DifferentialExperiment experiment, SetMultimap<String, String> ontologyTermIdsByAssayAccession) {

        Collection<DifferentialCondition> conditions = diffConditionsBuilder.buildProperties(experiment, ontologyTermIdsByAssayAccession);

        ImmutableSetMultimap.Builder<String, String> builder = ImmutableSetMultimap.builder();

        for (DifferentialCondition condition : conditions) {
            builder.putAll(condition.getContrastId(), condition.getValues());
        }

        return builder.build();

    }

}