package uk.ac.ebi.atlas.utils;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestingUtils {
    private JdbcTemplate jdbcTemplate;

    public TestingUtils(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getSingleCellExperimentAccessions() {
        return jdbcTemplate.queryForList("SELECT accession FROM scxa_public_experiment", String.class);
    }

    public List<String> getExpressionAtlasExperimentAccessions() {
        return jdbcTemplate.queryForList("SELECT accession FROM experiment", String.class);
    }
}
