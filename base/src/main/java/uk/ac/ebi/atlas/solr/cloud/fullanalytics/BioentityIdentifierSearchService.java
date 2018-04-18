package uk.ac.ebi.atlas.solr.cloud.fullanalytics;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import org.apache.solr.client.solrj.io.Tuple;
import uk.ac.ebi.atlas.solr.cloud.TupleStreamer;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.tuplestreamers.BaselineTupleStreamerFactory;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.tuplestreamers.DifferentialTupleStreamerFactory;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static com.google.common.collect.Ordering.natural;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

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

    public List<String> searchSpecificGenesInBaselineExperiment(String experimentAccession,
                                                                BaselineRequestPreferences<?> preferences) {
        try (TupleStreamer tupleStreamer =
                     baselineTupleStreamerFactory.createForBaselineSpecific(experimentAccession, preferences)) {
            return mapBySpecifictyAndSortByAverageExpression(tupleStreamer.get(), preferences.getHeatmapMatrixSize());
        }
    }

    public List<String> searchSpecificGenesInDifferentialExperiment(String experimentAccession,
                                                                    DifferentialRequestPreferences preferences) {
        try (TupleStreamer tupleStreamer =
                     differentialTupleStreamerFactory.createForDifferentialSpecific(experimentAccession, preferences)) {
            return mapBySpecifictyAndSortByAverageExpression(tupleStreamer.get(), preferences.getHeatmapMatrixSize());
        }
    }

    private List<String> mapBySpecifictyAndSortByAverageExpression(Stream<Tuple> stream, int maxSize) {
        Comparator<Tuple> avgExpressionAscending =
                Comparator.comparing(t -> t.getDouble(AVERAGE_EXPRESSION_KEY));

        Multimap<Long, Tuple> mostExpressedGenesOnAverageGroupedBySpecificity =
                TreeMultimap.create(
                        natural(),
                        avgExpressionAscending.reversed());

        stream.collect(groupingBy(tuple -> tuple.getLong(SPECIFICITY_KEY)))
                .forEach(mostExpressedGenesOnAverageGroupedBySpecificity::putAll);

        return mostExpressedGenesOnAverageGroupedBySpecificity.values().stream()
                .map(tuple -> tuple.getString(GENE_KEY))
                .limit(maxSize)
                .collect(toList());
    }



    public List<String> searchMostExpressedGenesInBaselineExperiment(String experimentAccession,
                                                                     BaselineRequestPreferences<?> preferences) {
        try (TupleStreamer tupleStreamer =
                     baselineTupleStreamerFactory.createForBaselineNonSpecific(experimentAccession, preferences)) {
            return tupleStreamer.get()
                                .map(tuple -> tuple.getString(GENE_KEY))
                                .collect(toList());
        }
    }

    public List<String> searchMostExpressedGenesInDifferentialExperiment(String experimentAccession,
                                                                         DifferentialRequestPreferences preferences) {
        try (TupleStreamer tupleStreamer =
                     differentialTupleStreamerFactory.createForDifferentialNonSpecific(
                             experimentAccession, preferences)) {
            return tupleStreamer.get()
                                .map(tuple -> tuple.getString(GENE_KEY))
                                .collect(toList());
        }
    }
}
