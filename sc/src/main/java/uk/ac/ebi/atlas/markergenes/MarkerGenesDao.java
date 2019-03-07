package uk.ac.ebi.atlas.markergenes;

import com.google.common.collect.ImmutableMap;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.TupleStreamer;
import uk.ac.ebi.atlas.solr.cloud.collections.BioentitiesCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.source.SearchStreamBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static uk.ac.ebi.atlas.solr.cloud.collections.BioentitiesCollectionProxy.BIOENTITY_IDENTIFIER;
import static uk.ac.ebi.atlas.solr.cloud.collections.BioentitiesCollectionProxy.PROPERTY_NAME;
import static uk.ac.ebi.atlas.solr.cloud.collections.BioentitiesCollectionProxy.PROPERTY_VALUE;
import static uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder.SOLR_MAX_ROWS;

@Component
public class MarkerGenesDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final BioentitiesCollectionProxy bioentitiesCollectionProxy;

    public MarkerGenesDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                          SolrCloudCollectionProxyFactory collectionProxyFactory) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        bioentitiesCollectionProxy = collectionProxyFactory.create(BioentitiesCollectionProxy.class);
    }

    private static final String SELECT_MARKER_GENES_WITH_AVERAGES_PER_CLUSTER =
            "SELECT * " +
                    "FROM scxa_marker_gene_stats " +
                    "WHERE k_where_marker =:k and experiment_accession = :experiment_accession AND marker_p_value<0.05";
    List<MarkerGene> getMarkerGenesWithAveragesPerCluster(String experimentAccession, int k) {
        Map<String, Object> namedParameters =
                ImmutableMap.of(
                        "experiment_accession", experimentAccession,
                        "k", k);

        return namedParameterJdbcTemplate.query(SELECT_MARKER_GENES_WITH_AVERAGES_PER_CLUSTER,
                namedParameters,
                (resultSet, rowNumber) -> MarkerGene.create(
                            resultSet.getString("gene_id"),
                            resultSet.getInt("k_where_marker"),
                            resultSet.getInt("cluster_id_where_marker"),
                            resultSet.getDouble("marker_p_value"),
                            resultSet.getInt("cluster_id"),
                            resultSet.getDouble("median_expression"),
                            resultSet.getDouble("mean_expression")
                ));
    }

    Map<String, String> getSymbolsForGeneIds(List<String> geneIds) {
        SolrQueryBuilder<BioentitiesCollectionProxy> bioentitiesQueryBuilder =
                new SolrQueryBuilder<BioentitiesCollectionProxy>()
                        .addQueryFieldByTerm(BIOENTITY_IDENTIFIER, geneIds)
                        .addQueryFieldByTerm(PROPERTY_NAME, BioentityPropertyName.SYMBOL.name())
                        .setFieldList(Arrays.asList(BIOENTITY_IDENTIFIER, PROPERTY_VALUE))
                        .sortBy(BIOENTITY_IDENTIFIER, SolrQuery.ORDER.asc)
                        .setRows(SOLR_MAX_ROWS);

        SearchStreamBuilder<BioentitiesCollectionProxy> bioentitiesSearchBuilder =
                new SearchStreamBuilder<>(bioentitiesCollectionProxy, bioentitiesQueryBuilder);

        try (TupleStreamer tupleStreamer = TupleStreamer.of(bioentitiesSearchBuilder.build())) {
            return tupleStreamer
                    .get()
                    .collect(Collectors.toMap(
                            tuple -> tuple.getString("bioentity_identifier"),
                            tuple -> tuple.getString("property_value")));
        }
    }

    private static final String SELECT_DISTINCT_KS_WITH_MARKER_GENES =
            "SELECT DISTINCT k_where_marker " +
                    "FROM scxa_marker_gene_stats " +
                    "WHERE experiment_accession = :experiment_accession AND marker_p_value < 0.05 " +
                    "ORDER BY k_where_marker ASC";
    public List<Integer> getKsWithMarkerGenes(String experimentAccession) {
        Map<String, Object> namedParameters =
                ImmutableMap.of("experiment_accession", experimentAccession);

        return namedParameterJdbcTemplate.queryForList(
                SELECT_DISTINCT_KS_WITH_MARKER_GENES,
                namedParameters,
                Integer.class);
    }

}
