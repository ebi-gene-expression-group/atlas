package uk.ac.ebi.atlas.solr.cloud.fullanalytics;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import org.apache.solr.client.solrj.io.Tuple;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.tuplestreamers.BaselineTupleStreamerFactory;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.tuplestreamers.DifferentialTupleStreamerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Stream;

import static com.google.common.collect.Ordering.natural;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.BIOENTITY_IDENTIFIER;

@Named
public class BioentityIdentifierSearchService {
    public final static String GENE_KEY = "bioentity_identifier";
    public final static String AVERAGE_EXPRESSION_KEY = "avg_expression";
    public final static String SPECIFICITY_KEY = "specificity";

    private final BaselineTupleStreamerFactory baselineTupleStreamerFactory;
    private final DifferentialTupleStreamerFactory differentialTupleStreamerFactory;

    @Inject
    public BioentityIdentifierSearchService(BaselineTupleStreamerFactory baselineTupleStreamerFactory,
                                            DifferentialTupleStreamerFactory differentialTupleStreamerFactory) {
        this.baselineTupleStreamerFactory = baselineTupleStreamerFactory;
        this.differentialTupleStreamerFactory = differentialTupleStreamerFactory;
    }

    // TODO Query with SemanticQuery (?)

    public List<String> searchSpecificGenesInBaselineExperiment(String experimentAccession,
                                                                double expressionLevelCutoff,
                                                                int maxSize,
                                                                String... assayGroupIds) {
        return mapBySpecifictyAndSortByAverageExpression(
                baselineTupleStreamerFactory.createForBaselineSpecific(
                        experimentAccession, expressionLevelCutoff, assayGroupIds)
                        .get(),
                maxSize);
    }

    public List<String> searchSpecificGenesInDifferentialExperiment(String experimentAccession,
                                                                    double log2FoldChangeCutoff,
                                                                    double adjustedPValueCutoff,
                                                                    int maxSize,
                                                                    String... contrastIds) {
        return mapBySpecifictyAndSortByAverageExpression(
                differentialTupleStreamerFactory.createForDifferentialSpecific(
                        experimentAccession, log2FoldChangeCutoff, adjustedPValueCutoff, contrastIds)
                        .get(),
                maxSize);
    }

    private List<String> mapBySpecifictyAndSortByAverageExpression(Stream<Tuple> stream, int maxSize) {
        Multimap<Long, Tuple> mostExpressedGenesOnAverageGroupedBySpecificity =
                TreeMultimap.create(
                        natural(),
                        comparing(t -> t.getDouble(AVERAGE_EXPRESSION_KEY)));

        stream.collect(groupingBy(tuple -> tuple.getLong(SPECIFICITY_KEY)))
                .forEach(mostExpressedGenesOnAverageGroupedBySpecificity::putAll);

        return mostExpressedGenesOnAverageGroupedBySpecificity.values().stream()
                .map(tuple -> tuple.getString(GENE_KEY))
                .limit(maxSize)
                .collect(toList());
    }

    public List<String> searchMostExpressedGenesInBaselineExperiment(String experimentAccession,
                                                                     double expressionLevelCutoff,
                                                                     int maxSize,
                                                                     String... assayGroupIds) {
        return baselineTupleStreamerFactory.createForBaselineNonSpecific(
                experimentAccession, expressionLevelCutoff, maxSize, assayGroupIds)
                .get()
                .map(tuple -> tuple.getString(BIOENTITY_IDENTIFIER))
                .collect(toList());
    }

    public List<String> searchMostExpressedGenesInDifferentialExperiment(String experimentAccession,
                                                                         double log2FoldChangeCutoff,
                                                                         double adjustedPValueCutoff,
                                                                         int maxSize,
                                                                         String... contrastIds) {
        return differentialTupleStreamerFactory.createForDifferentialNonSpecific(
                experimentAccession, log2FoldChangeCutoff, adjustedPValueCutoff, maxSize, contrastIds)
                .get()
                .map(tuple -> tuple.getString(BIOENTITY_IDENTIFIER))
                .collect(toList());
    }
}
