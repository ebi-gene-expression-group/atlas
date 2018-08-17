package uk.ac.ebi.atlas.experimentpage.baseline.topgenes;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import org.apache.solr.client.solrj.io.Tuple;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.solr.cloud.TupleStreamer;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Stream;

import static com.google.common.collect.Ordering.natural;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

// Get tuple streams from the DAO class and sorts them by specificity/average expression and return the gene IDs.

@Component
public class BaselineExperimentTopGenesService {
    public static final String GENE_KEY = "bioentity_identifier";
    public static final String AVERAGE_EXPRESSION_KEY = "avg_expression";
    public static final String SPECIFICITY_KEY = "specificity";

    private final BaselineExperimentTopGenesDao baselineExperimentTopGenesDao;

    public BaselineExperimentTopGenesService(BaselineExperimentTopGenesDao baselineExperimentTopGenesDao) {
        this.baselineExperimentTopGenesDao = baselineExperimentTopGenesDao;
    }

    public List<String> searchSpecificGenesInBaselineExperiment(String experimentAccession,
                                                                BaselineRequestPreferences<?> preferences) {
        try (TupleStreamer tupleStreamer =
                     baselineExperimentTopGenesDao.aggregateGeneIdsAndSortBySpecificity(
                             experimentAccession, preferences)) {
            return mapBySpecifictyAndSortByAverageExpression(tupleStreamer.get(), preferences.getHeatmapMatrixSize());
        }
    }

    public List<String> searchMostExpressedGenesInBaselineExperiment(String experimentAccession,
                                                                     BaselineRequestPreferences<?> preferences) {
        try (TupleStreamer tupleStreamer =
                     baselineExperimentTopGenesDao.aggregateGeneIdsAndSortByAverageExpression(
                             experimentAccession, preferences)) {
            return tupleStreamer.get()
                    .map(tuple -> tuple.getString(GENE_KEY))
                    .collect(toList());
        }
    }

    private List<String> mapBySpecifictyAndSortByAverageExpression(Stream<Tuple> stream, int maxSize) {
        ToDoubleFunction<Tuple> avgExpressionExtractor = tuple -> tuple.getDouble(AVERAGE_EXPRESSION_KEY);
        Function<Tuple, String> geneIdExtractor = tuple -> tuple.getString(GENE_KEY);
        // I tried inlining the functions, but Java freaked out
        Comparator<Tuple> avgExpressionDescending =
                Comparator.comparingDouble(avgExpressionExtractor).reversed().thenComparing(geneIdExtractor);

        Multimap<Long, Tuple> mostExpressedGenesOnAverageGroupedBySpecificity =
                TreeMultimap.create(
                        natural(),
                        avgExpressionDescending);

        stream.collect(groupingBy(tuple -> tuple.getLong(SPECIFICITY_KEY)))
                .forEach(mostExpressedGenesOnAverageGroupedBySpecificity::putAll);

        return mostExpressedGenesOnAverageGroupedBySpecificity.values().stream()
                .map(tuple -> tuple.getString(GENE_KEY))
                .limit(maxSize)
                .collect(toList());
    }

}
