package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GeneSearchServiceDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public GeneSearchServiceDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static final String SELECT_CELL_IDS_FOR_GENE_STATEMENT = 
            "SELECT experiment_accession, cell_id FROM scxa_analytics WHERE gene_id=:gene_id";
    @Transactional(readOnly = true)
    public Map<String, List<String>> fetchCellIds(String geneId) {
        Map<String, Object> namedParameters =
                ImmutableMap.of(
                        "gene_id", geneId );

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
            "SELECT experiment_accession, k, cluster_id FROM scxa_marker_genes WHERE gene_id=:gene_id";
    @Transactional(readOnly = true)
    public Map<String, List<Pair<Integer, Integer>>> fetchKAndClusterIds(String geneId) {
        Map<String, Object> namedParameters =
                ImmutableMap.of(
                        "gene_id", geneId );

        return namedParameterJdbcTemplate.query(
                SELECT_K_AND_CLUSTER_ID_FOR_GENE_STATEMENT,
                namedParameters,
                (ResultSet resultSet) -> {
                    Map<String, List<Pair<Integer, Integer>>> result = new HashMap<>();

                    while(resultSet.next()) {
                        String experimentAccession = resultSet.getString("experiment_accession");
                        Integer k = resultSet.getInt("k");
                        Integer clusterId = resultSet.getInt("cluster_id");

                        List<Pair<Integer, Integer>> kAndClusterIds = result.getOrDefault(experimentAccession, new ArrayList<>());

                        kAndClusterIds.add(Pair.of(k, clusterId));
                        result.put(experimentAccession, kAndClusterIds);
                    }

                    return result;
                }
        );
    }
}
