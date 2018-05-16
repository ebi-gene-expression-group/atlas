package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableMap;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
public class GeneSearchServiceDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public GeneSearchServiceDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static final String SELECT_CELL_ID_WITH_MARKER_GENE_STATEMENT =
            "SELECT clusters.experiment_accession, clusters.cell_id, markers.k, markers.cluster_id " +
            "FROM scxa_cell_clusters AS clusters " +
            "LEFT JOIN " +
            "(SELECT * FROM scxa_marker_genes WHERE gene_id=:gene_id AND marker_probability<:probability_threshold) AS markers " +
            "ON clusters.experiment_accession=markers.experiment_accession AND clusters.k=markers.k AND clusters.cluster_id=markers.cluster_id ";
    @Transactional(readOnly = true)
    public List<GeneSearchResult.Dto> fetchCellIds(String geneId, double probabilityThreshold) {
        Map<String, Object> namedParameters =
                ImmutableMap.of(
                        "gene_id", geneId,
                        "probability_threshold", probabilityThreshold);
        return namedParameterJdbcTemplate.query(
                SELECT_CELL_ID_WITH_MARKER_GENE_STATEMENT,
                namedParameters,
                (rs, rowNum) -> GeneSearchResult.Dto.create(
                        rs.getString("experiment_accession"),
                        geneId,
                        rs.getString("cell_id"),
                        rs.getInt("k"),
                        rs.getInt("cluster_id")));
    }

}
