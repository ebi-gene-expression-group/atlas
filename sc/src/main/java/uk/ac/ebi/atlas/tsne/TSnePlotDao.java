package uk.ac.ebi.atlas.tsne;

import com.google.common.collect.ImmutableMap;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.experimentpage.tsne.TSnePoint;

import java.util.List;
import java.util.Map;

@Component
public class TSnePlotDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TSnePlotDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static final String SELECT_T_SNE_PLOT_WITH_EXPRESSION_STATEMENT =
            "SELECT tsne.cell_id, tsne.x, tsne.y, analytics.expression_level " +
            "FROM scxa_tsne AS tsne " +
                "LEFT JOIN " +
                "(SELECT * FROM scxa_analytics WHERE gene_id=:gene_id) AS analytics " +
                "ON analytics.cell_id=tsne.cell_id AND analytics.experiment_accession=tsne.experiment_accession " +
            "WHERE tsne.experiment_accession=:experiment_accession AND tsne.perplexity=:perplexity";
    @Transactional(transactionManager = "txManager", readOnly = true)
    public List<TSnePoint.Dto> fetchTSnePlotWithExpression(String experimentAccession, int perplexity, String geneId) {
        Map<String, Object> namedParameters =
                ImmutableMap.of(
                        "experiment_accession", experimentAccession,
                        "perplexity", perplexity,
                        "gene_id", geneId);
        return namedParameterJdbcTemplate.query(
                SELECT_T_SNE_PLOT_WITH_EXPRESSION_STATEMENT,
                namedParameters,
                (rs, rowNum) -> TSnePoint.Dto.create(
                        rs.getDouble("x"),
                        rs.getDouble("y"),
                        rs.getDouble("expression_level"),
                        rs.getString("cell_id")));
    }

    private static final String SELECT_T_SNE_PLOT_WITH_CLUSTERS_STATEMENT =
            "SELECT tsne.cell_id, tsne.x, tsne.y, clusters.cluster_id " +
            "FROM scxa_tsne AS tsne " +
                "LEFT JOIN " +
                "(SELECT * FROM scxa_cell_clusters WHERE k=:k) AS clusters " +
                "ON clusters.cell_id=tsne.cell_id AND clusters.experiment_accession=tsne.experiment_accession " +
            "WHERE tsne.experiment_accession=:experiment_accession AND tsne.perplexity=:perplexity";
    @Transactional(transactionManager = "txManager", readOnly = true)
    public List<TSnePoint.Dto> fetchTSnePlotWithClusters(String experimentAccession, int perplexity, int k) {
        Map<String, Object> namedParameters =
                ImmutableMap.of(
                        "experiment_accession", experimentAccession,
                        "perplexity", perplexity,
                        "k", k);
        return namedParameterJdbcTemplate.query(
                SELECT_T_SNE_PLOT_WITH_CLUSTERS_STATEMENT,
                namedParameters,
                (rs, rowNum) -> TSnePoint.Dto.create(
                        rs.getDouble("x"), rs.getDouble("y"), rs.getInt("cluster_id"), rs.getString("cell_id")));
    }

    private static final String SELECT_T_SNE_PLOT_WITHOUT_CLUSTERS_STATEMENT =
            "SELECT tsne.cell_id, tsne.x, tsne.y " +
                    "FROM scxa_tsne AS tsne " +
                    "WHERE tsne.experiment_accession=:experiment_accession AND tsne.perplexity=:perplexity";
    public List<TSnePoint.Dto> fetchTSnePlotForPerplexity(String experimentAccession, int perplexity) {
        Map<String, Object> namedParameters =
                ImmutableMap.of(
                        "experiment_accession", experimentAccession,
                        "perplexity", perplexity);
        return namedParameterJdbcTemplate.query(
                SELECT_T_SNE_PLOT_WITHOUT_CLUSTERS_STATEMENT,
                namedParameters,
                (rs, rowNum) -> TSnePoint.Dto.create(
                        rs.getDouble("x"), rs.getDouble("y"), rs.getString("cell_id")));
    }

    private static final String SELECT_DISTINCT_PERPLEXITIES_STATEMENT =
            "SELECT DISTINCT perplexity FROM scxa_tsne WHERE experiment_accession=:experiment_accession";
    public List<Integer> fetchPerplexities(String experimentAccession) {
        Map<String, Object> namedParameters = ImmutableMap.of("experiment_accession", experimentAccession);

        return namedParameterJdbcTemplate.queryForList(
                SELECT_DISTINCT_PERPLEXITIES_STATEMENT,
                namedParameters,
                Integer.class
        );
    }

    private static final String COUNT_CELLS_BY_EXPERIMENT_ACCESSION =
            "SELECT COUNT(DISTINCT(cell_id)) FROM scxa_tsne WHERE experiment_accession=:experiment_accession";
    public Integer fetchNumberOfCellsByExperimentAccession(String experimentAccession) {
        Map<String, Object> namedParameters = ImmutableMap.of("experiment_accession", experimentAccession);
        return namedParameterJdbcTemplate.queryForObject(
                COUNT_CELLS_BY_EXPERIMENT_ACCESSION,
                namedParameters,
                Integer.class
        );
    }
}
