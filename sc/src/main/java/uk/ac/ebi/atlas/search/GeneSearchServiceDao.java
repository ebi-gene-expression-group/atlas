package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableMap;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.bioentities.BioentitiesCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static uk.ac.ebi.atlas.solr.cloud.bioentities.BioentitiesCollectionProxy.BIOENTITY_IDENTIFIER;
import static uk.ac.ebi.atlas.solr.cloud.bioentities.BioentitiesCollectionProxy.PROPERTY_NAME;
import static uk.ac.ebi.atlas.solr.cloud.bioentities.BioentitiesCollectionProxy.PROPERTY_VALUE;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy.CELL_ID;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy.EXPERIMENT_ACCESSION;

@Component
public class GeneSearchServiceDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneSearchServiceDao.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SingleCellAnalyticsCollectionProxy singleCellAnalyticsCollectionProxy;

    private BioentitiesCollectionProxy bioentitiesCollectionProxy;

    public GeneSearchServiceDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate, SolrCloudCollectionProxyFactory solrCloudCollectionProxyFactory) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.singleCellAnalyticsCollectionProxy = solrCloudCollectionProxyFactory.createSingleCellAnalyticsCollectionProxy();
        this.bioentitiesCollectionProxy = solrCloudCollectionProxyFactory.createBioentitiesCollectionProxy();
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
                    while(resultSet.next()) {
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

    private static final String SELECT_K_AND_CLUSTER_ID_FOR_GENE_STATEMENT =
            "SELECT experiment_accession, k, cluster_id FROM scxa_marker_genes AS markers " +
                    "JOIN scxa_experiment AS experiments ON markers.experiment_accession = experiments.accession " +
                    "WHERE gene_id=:gene_id AND marker_probability<=:threshold AND private=FALSE";
    @Transactional(readOnly = true)
    public Map<String, Map<Integer, List<Integer>>> fetchKAndClusterIds(String geneId) {
        Map<String, Object> namedParameters =
                ImmutableMap.of(
                        "gene_id", geneId,
                        "threshold", 0.05);

        return namedParameterJdbcTemplate.query(
                SELECT_K_AND_CLUSTER_ID_FOR_GENE_STATEMENT,
                namedParameters,
                (ResultSet resultSet) -> {
                    Map<String, Map<Integer, List<Integer>>> result = new HashMap<>();

                    while (resultSet.next()) {
                        String experimentAccession = resultSet.getString("experiment_accession");
                        Integer k = resultSet.getInt("k");
                        Integer clusterId = resultSet.getInt("cluster_id");

                        Map<Integer, List<Integer>> kAndClusterIds = result.getOrDefault(experimentAccession, new HashMap<>());
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
    public Map<String, Map<String, List<String>>> getFacets(List<String> cellIds, SingleCellAnalyticsSchemaField... singleCellAnalyticsSchemaFields) {
        List<SingleCellAnalyticsSchemaField> subFacetFields = Arrays.asList(singleCellAnalyticsSchemaFields);

        SolrQueryBuilder<SingleCellAnalyticsCollectionProxy> queryBuilder =
                new SolrQueryBuilder<SingleCellAnalyticsCollectionProxy>()
                        .addQueryFieldByTerm(CELL_ID, cellIds)
                        .setFacetField(EXPERIMENT_ACCESSION)
                        .setSubFacetList(subFacetFields.toArray(new SingleCellAnalyticsSchemaField[0]))
                        .setRows(0);

        ArrayList<SimpleOrderedMap> resultsByExperiment = (ArrayList<SimpleOrderedMap>) singleCellAnalyticsCollectionProxy.query(queryBuilder).getResponse().findRecursive("facets", EXPERIMENT_ACCESSION.name(), "buckets");

        return resultsByExperiment
                .stream()
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

    public List<String> getGeneIdsForCategory(BioentityPropertyName category, String query) {
        SolrQueryBuilder<BioentitiesCollectionProxy> queryBuilder =
                new SolrQueryBuilder<BioentitiesCollectionProxy>()
                    .addQueryFieldByTerm(PROPERTY_NAME, category.name())
                    .addQueryFieldByTerm(PROPERTY_VALUE, query)
                    .setFieldList(BIOENTITY_IDENTIFIER);

        SolrDocumentList results = bioentitiesCollectionProxy.query(queryBuilder).getResults();

        return results.stream()
                .map(x -> x.get(BIOENTITY_IDENTIFIER.name()).toString())
                .collect(Collectors.toList());
    }

    private List<String> getValuesForFacetField(SimpleOrderedMap map, String facetField) {
        ArrayList<SimpleOrderedMap> results = (ArrayList<SimpleOrderedMap>) map.findRecursive(facetField, "buckets");

        return results
                .stream()
                .map(x -> x.get("val").toString())
                .collect(Collectors.toList());
    }
}
