
package uk.ac.ebi.atlas.experimentimport.analyticsindex.differential;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentimport.EFOParentsLookupService;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.support.SpeciesGrouper;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialCondition;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialConditionsBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named
@Scope("singleton")
public class MicroArrayDiffAnalyticsIndexerService {
    private final EFOParentsLookupService efoParentsLookupService;
    private final DifferentialConditionsBuilder diffConditionsBuilder;
    private final MicroArrayDiffAnalyticsDocumentStreamIndexer microArrayDiffAnalyticsDocumentStreamIndexer;

    @Inject
    public MicroArrayDiffAnalyticsIndexerService(EFOParentsLookupService efoParentsLookupService, DifferentialConditionsBuilder diffConditionsBuilder, MicroArrayDiffAnalyticsDocumentStreamIndexer microArrayDiffAnalyticsDocumentStreamIndexer) {
        this.efoParentsLookupService = efoParentsLookupService;
        this.diffConditionsBuilder = diffConditionsBuilder;
        this.microArrayDiffAnalyticsDocumentStreamIndexer = microArrayDiffAnalyticsDocumentStreamIndexer;
    }

    public int index(MicroarrayExperiment experiment, Map<String, String> bioentityIdToIdentifierSearch, int batchSize) {
        String experimentAccession = experiment.getAccession();

        ExperimentType experimentType = experiment.getType();

        SortedSet<String> arrayDesignAccessions = experiment.getArrayDesignAccessions();

        ExperimentDesign experimentDesign = experiment.getExperimentDesign();

        ImmutableMap<String, String> ensemblSpeciesGroupedByContrastId = SpeciesGrouper.buildEnsemblSpeciesGroupedByContrastId(experiment);

        ImmutableSetMultimap<String, String> ontologyTermIdsByAssayAccession = expandOntologyTerms(experimentDesign.getAllOntologyTermIdsByAssayAccession());

        ImmutableSetMultimap<String, String> conditionSearchTermsByContrastId = buildConditionSearchTermsByAssayGroupId(experiment, ontologyTermIdsByAssayAccession);

        Set<String> factors = experimentDesign.getFactorHeaders();

        Map<String, Integer> numReplicatesByContrastId = buildNumReplicatesByContrastId(experiment);

        return  microArrayDiffAnalyticsDocumentStreamIndexer.index(experimentAccession, arrayDesignAccessions, experimentType, factors,
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

    private ImmutableSetMultimap<String, String> expandOntologyTerms(ImmutableSetMultimap<String, String> termIdsByAssayAccession) {

        ImmutableSetMultimap.Builder<String, String> builder = ImmutableSetMultimap.builder();
        for (String assayAccession : termIdsByAssayAccession.keys()) {
            Set<String> expandedOntologyTerms = new HashSet<>();

            expandedOntologyTerms.addAll(efoParentsLookupService.getAllParents(termIdsByAssayAccession.get(assayAccession)));
            expandedOntologyTerms.addAll(termIdsByAssayAccession.get(assayAccession));

            builder.putAll(assayAccession, expandedOntologyTerms);
        }

        return builder.build();
    }

    ImmutableSetMultimap<String, String> buildConditionSearchTermsByAssayGroupId(DifferentialExperiment experiment, SetMultimap<String, String> ontologyTermIdsByAssayAccession) {

        Collection<DifferentialCondition> conditions = diffConditionsBuilder.buildProperties(experiment, ontologyTermIdsByAssayAccession);

        ImmutableSetMultimap.Builder<String, String> builder = ImmutableSetMultimap.builder();

        for (DifferentialCondition condition : conditions) {
            builder.putAll(condition.getContrastId(), condition.getValues());
        }

        return builder.build();

    }

}