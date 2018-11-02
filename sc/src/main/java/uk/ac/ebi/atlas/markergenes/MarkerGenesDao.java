package uk.ac.ebi.atlas.markergenes;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MarkerGenesDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(MarkerGenesDao.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MarkerGenesDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static final String SELECT_MARKER_GENES_WITH_AVERAGES_PER_CLUSTER =
            "SELECT * " +
                    "FROM top_50_marker_genes_stats " +
                    "WHERE k_where_marker =:k and experiment_accession = :experiment_accession";
    public List<MarkerGene> getMarkerGenesWithAveragesPerCluster(String experimentAccession, int k) {
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
}
