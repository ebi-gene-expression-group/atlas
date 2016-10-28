package uk.ac.ebi.atlas.experimentimport.analyticsindex.differential;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import org.apache.hadoop.hdfs.util.Diff;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.baseline.BaselineExperimentDataPointStream;
import uk.ac.ebi.atlas.experimentimport.efo.EFOLookupService;
import uk.ac.ebi.atlas.model.analyticsindex.BaselineExperimentDataPoint;
import uk.ac.ebi.atlas.model.analyticsindex.DifferentialExperimentDataPoint;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialCondition;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialConditionsBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Map;

@Named
@Deprecated
public class RnaSeqDiffAnalyticsIndexerService {

    private final EFOLookupService efoParentsLookupService;
    private final DifferentialConditionsBuilder diffConditionsBuilder;
    private final RnaSeqDifferentialAnalyticsInputStreamFactory rnaSeqDifferentialAnalyticsInputStreamFactory;
    private final RnaSeqDiffAnalyticsDocumentStreamIndexer rnaSeqDiffAnalyticsDocumentStreamIndexer;


    @Inject
    public RnaSeqDiffAnalyticsIndexerService(EFOLookupService efoParentsLookupService, DifferentialConditionsBuilder
            diffConditionsBuilder, RnaSeqDifferentialAnalyticsInputStreamFactory rnaSeqDifferentialAnalyticsInputStreamFactory,
                                             RnaSeqDiffAnalyticsDocumentStreamIndexer rnaSeqDiffAnalyticsDocumentStreamIndexer) {
        this.efoParentsLookupService = efoParentsLookupService;
        this.diffConditionsBuilder = diffConditionsBuilder;
        this.rnaSeqDifferentialAnalyticsInputStreamFactory = rnaSeqDifferentialAnalyticsInputStreamFactory;
        this.rnaSeqDiffAnalyticsDocumentStreamIndexer = rnaSeqDiffAnalyticsDocumentStreamIndexer;
    }

    public Iterable<DifferentialExperimentDataPoint> openStream(DifferentialExperiment experiment){

        return new DifferentialExperimentDataPointStream(experiment,
                new IterableObjectInputStream<>(
                    rnaSeqDifferentialAnalyticsInputStreamFactory.create(experiment.getAccession())
                ),
                buildConditionSearchTermsByAssayGroupId(experiment,
                        efoParentsLookupService
                        .expandOntologyTerms(experiment.getExperimentDesign().getAllOntologyTermIdsByAssayAccession())
                ),
                buildNumReplicatesByContrastId(experiment)
        );
    }

    public int index(DifferentialExperiment experiment, Map<String, String> bioentityIdToIdentifierSearch, int batchSize) {

        ImmutableSetMultimap<String, String> ontologyTermIdsByAssayAccession = efoParentsLookupService
                .expandOntologyTerms(experiment.getExperimentDesign().getAllOntologyTermIdsByAssayAccession());
        ImmutableSetMultimap<String, String> conditionSearchTermsByContrastId = buildConditionSearchTermsByAssayGroupId(experiment, ontologyTermIdsByAssayAccession);
        Map<String, Integer> numReplicatesByContrastId = buildNumReplicatesByContrastId(experiment);

        return  rnaSeqDiffAnalyticsDocumentStreamIndexer.index(
                experiment, conditionSearchTermsByContrastId, numReplicatesByContrastId, bioentityIdToIdentifierSearch, batchSize);
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