package uk.ac.ebi.atlas.testutils;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import java.util.List;

@Component
public class JdbcUtils {
    private JdbcTemplate jdbcTemplate;

    public JdbcUtils(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> fetchPublicSingleCellExperimentAccessions() {
        return jdbcTemplate.queryForList("SELECT accession FROM scxa_public_experiment", String.class);
    }

    public List<String> fetchAllSingleCellExperimentAccessions() {
        return jdbcTemplate.queryForList("SELECT accession FROM scxa_experiment", String.class);
    }

    public String fetchRandomArrayDesignAccession() {
        return jdbcTemplate.queryForObject(
                "SELECT arraydesign FROM designelement_mapping ORDER BY RANDOM() LIMIT 1",
                String.class);
    }

    public List<String> getAllExpressionAtlasExperimentAccessions() {
        return jdbcTemplate.queryForList("SELECT accession FROM experiment", String.class);
    }

    public String fetchRandomSingleCellExperimentAccession() {
        return jdbcTemplate.queryForObject(
                "SELECT accession FROM scxa_experiment ORDER BY RANDOM() LIMIT 1",
                String.class);
    }

    public String fetchRandomExpressionAtlasExperimentAccession() {
        return jdbcTemplate.queryForObject(
                "SELECT accession FROM experiment ORDER BY RANDOM() LIMIT 1",
                String.class);
    }

    public String fetchRandomPublicExpressionAtlasExperimentAccession() {
        return jdbcTemplate.queryForObject(
                "SELECT accession FROM experiment WHERE private=FALSE ORDER BY RANDOM() LIMIT 1",
                String.class);
    }

    public String fetchRandomExpressionAtlasExperimentAccession(ExperimentType experimentType) {
        return jdbcTemplate.queryForObject(
                "SELECT accession FROM experiment WHERE type=? AND private=FALSE ORDER BY RANDOM() LIMIT 1",
                String.class,
                experimentType.name());
    }

    public List<String> fetchSpeciesForSingleCellExperiments() {
        return jdbcTemplate.queryForList(
                "SELECT DISTINCT species FROM scxa_experiment",
                String.class);
    }

    public String fetchRandomSpeciesForSingleCellExperiments() {
        return jdbcTemplate.queryForObject(
                "SELECT species FROM scxa_experiment ORDER BY RANDOM() LIMIT 1",
                String.class);
    }

    public String fetchRandomGene() {
        return jdbcTemplate.queryForObject(
                "SELECT gene_id FROM scxa_analytics ORDER BY RANDOM() LIMIT 1",
                String.class);
    }

    public String fetchRandomGeneFromSingleCellExperiment(String experimentAccession) {
        return jdbcTemplate.queryForObject(
                "SELECT gene_id FROM scxa_analytics WHERE experiment_accession=? ORDER BY RANDOM() LIMIT 1",
                String.class,
                experimentAccession);
    }

    public String fetchRandomMarkerGeneFromSingleCellExperiment(String experimentAccession) {
        return jdbcTemplate.queryForObject(
                "SELECT gene_id FROM scxa_marker_genes WHERE marker_probability < 0.05 AND experiment_accession=? ORDER BY RANDOM() LIMIT 1",
                String.class,
                experimentAccession);
    }

    public String fetchRandomCellFromExperiment(String experimentAccession) {
        return jdbcTemplate.queryForObject(
                "SELECT cell_id FROM scxa_analytics WHERE experiment_accession=? ORDER BY RANDOM() LIMIT 1",
                String.class,
                experimentAccession);
    }

    public List<String> fetchRandomListOfCells(int numberOfCells) {
        return jdbcTemplate.queryForList(
                "SELECT cell_id FROM scxa_analytics ORDER BY RANDOM() LIMIT ?",
                String.class,
                numberOfCells);
    }

    public List<String> fetchRandomListOfCellsFromExperiment(String experimentAccession, int numberOfCells) {
        return jdbcTemplate.queryForList(
                "SELECT cell_id FROM scxa_analytics  WHERE experiment_accession=? ORDER BY RANDOM() LIMIT ?",
                String.class,
                experimentAccession,
                numberOfCells);
    }

    public int fetchRandomPerplexityFromExperimentTSne(String experimentAccession) {
        return jdbcTemplate.queryForObject(
                "SELECT perplexity FROM scxa_tsne WHERE experiment_accession=? ORDER BY RANDOM() LIMIT 1",
                Integer.class,
                experimentAccession);
    }

    public int fetchRandomPerplexityFromExperimentTSne(String experimentAccession, String geneId) {
        return jdbcTemplate.queryForObject(
                "SELECT perplexity FROM scxa_tsne AS tsne " +
                    "LEFT JOIN scxa_analytics AS analytics " +
                    "ON analytics.experiment_accession=tsne.experiment_accession AND analytics.cell_id=tsne.cell_id " +
                "WHERE tsne.experiment_accession=? AND analytics.gene_id=? ORDER BY RANDOM() LIMIT 1",
                Integer.class,
                experimentAccession, geneId);
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
