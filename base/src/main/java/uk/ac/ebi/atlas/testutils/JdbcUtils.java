package uk.ac.ebi.atlas.testutils;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdbcUtils {
    private JdbcTemplate jdbcTemplate;

    public JdbcUtils(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getPublicSingleCellExperimentAccessions() {
        return jdbcTemplate.queryForList("SELECT accession FROM scxa_public_experiment", String.class);
    }

    public List<String> getAllSingleCellExperimentAccessions() {
        return jdbcTemplate.queryForList("SELECT accession FROM scxa_experiment", String.class);
    }

    public List<String> getPublicExpressionAtlasExperimentAccessions() {
        return jdbcTemplate.queryForList("SELECT accession FROM public_experiment", String.class);
    }

    public List<String> getAllExpressionAtlasExperimentAccessions() {
        return jdbcTemplate.queryForList("SELECT accession FROM experiment", String.class);
    }

    public String fetchRandomSingleCellExperimentAccession() {
        return jdbcTemplate.queryForObject(
                "SELECT accession FROM scxa_experiment ORDER BY RANDOM() LIMIT 1",
                String.class);
    }

    public String fetchRandomGeneFromExperiment(String experimentAccession) {
         return jdbcTemplate.queryForObject(
                 "SELECT gene_id FROM scxa_analytics WHERE experiment_accession=? ORDER BY RANDOM() LIMIT 1",
                 String.class,
                 experimentAccession);
    }

    public String fetchRandomCellFromExperiment(String experimentAccession) {
        return jdbcTemplate.queryForObject(
                "SELECT cell_id FROM scxa_tsne WHERE experiment_accession=? ORDER BY RANDOM() LIMIT 1",
                String.class,
                experimentAccession);
    }

    public int fetchRandomPerplexityFromExperimentTSne(String experimentAccession) {
        return jdbcTemplate.queryForObject(
                "SELECT perplexity FROM scxa_tsne WHERE experiment_accession=? ORDER BY RANDOM() LIMIT 1",
                Integer.class,
                experimentAccession);
    }

    public int fetchRandomKFromCellClusters(String experimentAccession) {
        return jdbcTemplate.queryForObject(
                "SELECT k FROM scxa_cell_clusters WHERE experiment_accession=? ORDER BY RANDOM() LIMIT 1",
                Integer.class,
                experimentAccession);
    }

    public List<Integer> fetchKsFromCellClusters(String experimentAccession) {
        return jdbcTemplate.queryForList(
                "SELECT DISTINCT(k) FROM scxa_cell_clusters WHERE experiment_accession=?",
                Integer.class,
                experimentAccession);
    }
}
