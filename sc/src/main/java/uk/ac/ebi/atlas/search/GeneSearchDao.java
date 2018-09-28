package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParserOutput;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.collections.SingleCellAnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.collections.SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;
import static uk.ac.ebi.atlas.solr.cloud.collections.SingleCellAnalyticsCollectionProxy.CELL_ID;
import static uk.ac.ebi.atlas.solr.cloud.collections.SingleCellAnalyticsCollectionProxy.EXPERIMENT_ACCESSION;

@Component
public class GeneSearchDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneSearchDao.class);
    private static final double MARKER_GENE_P_VALUE_THRESHOLD = 0.005;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SingleCellAnalyticsCollectionProxy singleCellAnalyticsCollectionProxy;
    private IdfParser idfParser;

    public GeneSearchDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                         SolrCloudCollectionProxyFactory solrCloudCollectionProxyFactory,
                         IdfParser idfParser) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.singleCellAnalyticsCollectionProxy =
                solrCloudCollectionProxyFactory.create(SingleCellAnalyticsCollectionProxy.class);
        this.idfParser = idfParser;
    }

    private static final String SELECT_CELL_IDS_FOR_GENE_STATEMENT =
            "SELECT experiment_accession, cell_id FROM scxa_analytics AS analytics " +
                    "JOIN scxa_experiment AS experiments ON analytics.experiment_accession = experiments.accession " +
                    "WHERE gene_id=:gene_id AND private=FALSE";
    @Transactional(readOnly = true)
    public Map<String, List<String>> fetchCellIds(String geneId) {
        LOGGER.debug("Fetching cell IDs for {}", geneId);

        Map<String, Object> namedParameters =
                ImmutableMap.of(
                        "gene_id", geneId);

        return namedParameterJdbcTemplate.query(
                SELECT_CELL_IDS_FOR_GENE_STATEMENT,
                namedParameters,
                (ResultSet resultSet) -> {
                    Map<String, List<String>> result = new HashMap<>();
                    while (resultSet.next()) {
                        String experimentAccession = resultSet.getString("experiment_accession");
                        String cellId = resultSet.getString("cell_id");

                        List<String> cellIds = result.getOrDefault(experimentAccession, new ArrayList<>());
                        cellIds.add(cellId);
                        result.put(experimentAccession, cellIds);
                    }

                    return result;
                }
        );
    }

    private static final String SELECT_PREFFERED_K_STATEMENT =
            "SELECT experiment_accession FROM scxa_marker_genes AS markers "+
            "JOIN scxa_experiment AS experiments ON markers.experiment_accession = experiments.accession "+
            "WHERE gene_id=:gene_id "+
            "GROUP BY experiment_accession";

    public Map<String, Map<Integer, List<Integer>>> preferredKAndExperiment (String geneId) {
        Map<String, Object> namedParameters =
                ImmutableMap.of(
                        "gene_id", geneId);

        return namedParameterJdbcTemplate.query(
                SELECT_PREFFERED_K_STATEMENT,
                namedParameters,
                (ResultSet resultSet)->{
                    Map<String, Map<Integer, List<Integer>>>  result = new HashMap<>();

                    while(resultSet.next()){
                        String experimentAccession = resultSet.getString("experiment_accession");
                        IdfParserOutput idfParserOutput = idfParser.parse(experimentAccession);
                        Integer preferredK = idfParserOutput.getExpectedClusters();
                        result.putAll(fetchKAndClusterIds(geneId, experimentAccession, preferredK));
                    }

                    return result;

                }
        );
    }

    //In marker gene dataset files, one and only one clusterID for each preferred cluster K value,
    //which is supposed to have the lowest marker_probability,
    //so we get one pair from searching (experiment_accesion, k).
    //If dataset has changed, i.e. there are multiple gene clusterID in one preferred cluster K file,
    //we probably need to order and limit the searching result based on marker_propability
    private static final String SELECT_K_AND_CLUSTER_ID_FOR_GENE_STATEMENT =
            "SELECT experiment_accession, k, marker_probability, cluster_id FROM scxa_marker_genes AS markers " +
                    "JOIN scxa_experiment AS experiments ON markers.experiment_accession = experiments.accession " +
                    //"WHERE gene_id=:gene_id AND marker_probability<=:threshold AND private=FALSE " +
                    "WHERE private=FALSE AND (marker_probability IN (SELECT MIN(marker_probability) "+
                                                                    "FROM scxa_marker_genes " +
                                                                    "WHERE gene_id=:gene_id " +
                                                                    "GROUP BY experiment_accession) " +
                                                "AND experiment_accession= :experiment_accession " +
                                                "AND marker_probability<=:threshold) " +
                    "OR ((experiment_accession, k) IN ((:experiment_accession, :experiment_and_K)) " +
                        "AND marker_probability<=:threshold AND gene_id=:gene_id)";
    @Transactional(readOnly = true)
    public Map<String, Map<Integer, List<Integer>>> fetchKAndClusterIds(String geneId, String experiment_accession, Integer experimentAndK) {
        Map<String, Object> namedParameters =
                ImmutableMap.of(
                        "gene_id", geneId,
                        "threshold", MARKER_GENE_P_VALUE_THRESHOLD,
                        "experiment_and_K", experimentAndK,
                        "experiment_accession", experiment_accession);

        return namedParameterJdbcTemplate.query(
                SELECT_K_AND_CLUSTER_ID_FOR_GENE_STATEMENT,
                namedParameters,
                (ResultSet resultSet) -> {
                    Map<String, Map<Integer, List<Integer>>> result = new HashMap<>();

                    while (resultSet.next()) {
                        String experimentAccession = resultSet.getString("experiment_accession");
                        Integer k = resultSet.getInt("k");
                        Integer clusterId = resultSet.getInt("cluster_id");
                        Float marker_probability = resultSet.getFloat("marker_probability");

                        Map<Integer, List<Integer>> kAndClusterIds =
                                result.getOrDefault(experimentAccession, new HashMap<>());
                        List<Integer> clusterIds = kAndClusterIds.getOrDefault(k, new ArrayList<>());
                        clusterIds.add(clusterId);

                        kAndClusterIds.put(k, clusterIds);
                        result.put(experimentAccession, kAndClusterIds);
                    }

                    return result;
                }
        );
    }

    // Returns inferred cell types and organism parts for each experiment accession
    public Map<String, Map<String, List<String>>>
    getFacets(List<String> cellIds, SingleCellAnalyticsSchemaField... singleCellAnalyticsSchemaFields) {
        List<SingleCellAnalyticsSchemaField> subFacetFields = Arrays.asList(singleCellAnalyticsSchemaFields);

        SolrQueryBuilder<SingleCellAnalyticsCollectionProxy> queryBuilder =
                new SolrQueryBuilder<SingleCellAnalyticsCollectionProxy>()
                        .addQueryFieldByTerm(CELL_ID, cellIds)
                        .setFacetField(EXPERIMENT_ACCESSION)
                        .setSubFacetList(subFacetFields)
                        .setRows(0);

        List<SimpleOrderedMap> resultsByExperiment =
                extractSimpleOrderdMaps(
                        singleCellAnalyticsCollectionProxy
                                .query(queryBuilder)
                                .getResponse()
                                .findRecursive("facets", EXPERIMENT_ACCESSION.name(), "buckets"));


        return resultsByExperiment == null ?
                emptyMap() :
                resultsByExperiment.stream()
                .collect(Collectors.toMap(
                        subFacetValues -> subFacetValues.get("val").toString(),
                        subFacetValues -> subFacetFields
                                .stream()
                                .collect(Collectors.toMap(
                                        SingleCellAnalyticsSchemaField::displayName,
                                        subFacetField -> getValuesForFacetField(subFacetValues, subFacetField.name())
                                ))
                ));
    }

    private List<String> getValuesForFacetField(SimpleOrderedMap map, String facetField) {
        List<SimpleOrderedMap> results = extractSimpleOrderdMaps(map.findRecursive(facetField, "buckets"));

        return results
                .stream()
                .map(x -> x.get("val").toString())
                .collect(Collectors.toList());
    }

    private static List<SimpleOrderedMap> extractSimpleOrderdMaps(Object o) {
        ImmutableList.Builder<SimpleOrderedMap> builder = ImmutableList.builder();
        if (o instanceof ArrayList) {
            for (Object element : (ArrayList) o) {
                if (element instanceof SimpleOrderedMap) {
                    builder.add((SimpleOrderedMap) element);
                }
            }
        }
        return builder.build();
    }
}
