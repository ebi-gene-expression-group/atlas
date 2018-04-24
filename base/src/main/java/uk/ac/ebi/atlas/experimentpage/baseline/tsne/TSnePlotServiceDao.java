package uk.ac.ebi.atlas.experimentpage.baseline.tsne;

import com.google.common.collect.ImmutableMap;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
public class TSnePlotServiceDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TSnePlotServiceDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

//    private static final String SELECT_T_SNE_PLOT_STATEMENT=
//            "SELECT cell_id, x, y FROM scxa_tsne " +
//            "WHERE experiment_accession=:experiment_accession AND perplexity=:perplexity";
//    @Transactional(readOnly = true)
//    public List<TSnePoint.Dto> fetchTSnePlot(String experimentAccession, int perplexity) {
//        Map<String, Object> namedParameters =
//                ImmutableMap.of(
//                        "experiment_accession", experimentAccession,
//                        "perplexity", perplexity);
//        return namedParameterJdbcTemplate.query(
//                SELECT_T_SNE_PLOT_STATEMENT,
//                namedParameters,
//                (rs, rowNum) -> TSnePoint.Dto.create(rs.getDouble("x"), rs.getDouble("y"), rs.getString("cell_id")));
//    }

//    private static final String SELECT_T_SNE_PLOT_WITH_EXPRESSION =
//            "SELECT tsne.cell_id, tsne.x, tsne.y, analytics.expression_level " +
//            "FROM scxa_tsne AS tsne " +
//            "LEFT JOIN " +
//            "(SELECT * FROM scxa_analytics WHERE gene_id=:gene_id) " +
//            "AS analytics " +
//            "ON analytics.cell_id=tsne.cell_id AND analytics.experiment_accession=tsne.experiment_accession " +
//            "WHERE tsne.experiment_accession=:experiment_accession AND tsne.perplexity=:perplexity";
//    @Transactional(readOnly = true)
//    public List<TSnePoint.Dto> fetchTSnePlotWithExpression(String experimentAccession, int perplexity, String geneId) {
//        Map<String, Object> namedParameters =
//                ImmutableMap.of(
//                        "experiment_accession", experimentAccession,
//                        "perplexity", perplexity,
//                        "gene_id", geneId);
//        return namedParameterJdbcTemplate.query(
//                SELECT_T_SNE_PLOT_WITH_EXPRESSION,
//                namedParameters,
//                (rs, rowNum) -> TSnePoint.Dto.create(
//                        rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("expression_level"), rs.getString("cell_id")));
//    }

    private static final String SELECT_T_SNE_PLOT_WITH_CLUSTERS =
            "SELECT tsne.cell_id, tsne.x, tsne.y, clusters.cluster_id " +
            "FROM scxa_tsne AS tsne " +
            "LEFT JOIN " +
            "(SELECT * FROM scxa_cell_clusters WHERE k=:k) " +
            "AS clusters " +
            "ON clusters.cell_id=tsne.cell_id AND clusters.experiment_accession=tsne.experiment_accession " +
            "WHERE tsne.experiment_accession=:experiment_accession AND tsne.perplexity=:perplexity";
    @Transactional(readOnly = true)
    public List<TSnePoint.Dto> fetchTSnePlotWithClusters(String experimentAccession, int perplexity, int k) {
        Map<String, Object> namedParameters =
                ImmutableMap.of(
                        "experiment_accession", experimentAccession,
                        "perplexity", perplexity,
                        "k", k);
        return namedParameterJdbcTemplate.query(
                SELECT_T_SNE_PLOT_WITH_CLUSTERS,
                namedParameters,
                (rs, rowNum) -> TSnePoint.Dto.create(
                        rs.getDouble("x"), rs.getDouble("y"), rs.getInt("cluster_id"), rs.getString("cell_id")));
    }

    private static final String SELECT_T_SNE_PLOT_WITH_EXPRESSION_AND_CLUSTERS =
            "SELECT tsne.cell_id, tsne.x, tsne.y, analytics.expression_level, clusters.cluster_id " +
            "FROM scxa_tsne AS tsne " +

                "LEFT JOIN (SELECT * FROM scxa_analytics WHERE gene_id=:gene_id) AS analytics " +
                "ON analytics.cell_id=tsne.cell_id " +

                "LEFT JOIN (SELECT * FROM scxa_cell_clusters WHERE k=:k) AS clusters " +
                "ON clusters.cell_id=tsne.cell_id AND clusters.experiment_accession=tsne.experiment_accession " +

            "WHERE tsne.experiment_accession=:experiment_accession AND tsne.perplexity=:perplexity";
    @Transactional(readOnly = true)
    public List<TSnePoint.Dto> fetchTSnePlotWithExpressionAndClusters(String experimentAccession,
                                                                      int perplexity,
                                                                      String geneId, int k) {
        Map<String, Object> namedParameters =
                ImmutableMap.of(
                        "experiment_accession", experimentAccession,
                        "perplexity", perplexity,
                        "gene_id", geneId,
                        "k", k);
        return namedParameterJdbcTemplate.query(
                SELECT_T_SNE_PLOT_WITH_EXPRESSION_AND_CLUSTERS,
                namedParameters,
                (rs, rowNum) -> TSnePoint.Dto.create(
                        rs.getDouble("x"),
                        rs.getDouble("y"),
                        rs.getDouble("expression_level"),
                        rs.getInt("cluster_id"),
                        rs.getString("cell_id")));
    }
}
