package uk.ac.ebi.atlas.experimentpage.baseline.tsne;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:applicationContext.xml")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TSnePlotServiceDaoIT {
    @Inject
    private JdbcTemplate jdbcTemplate;

    @Inject
    private JdbcUtils jdbcTestUtils;

    @Inject
    private TSnePlotServiceDao subject;

    @Test
    void testExpression() {
        String experimentAccession = jdbcTestUtils.fetchRandomExperimentAccession();
        String geneId = jdbcTestUtils.fetchRandomGeneFromExperiment(experimentAccession);
        int perplexity = jdbcTestUtils.fetchRandomPerplexityFromExperimentTSne(experimentAccession);

        assertThat(subject.fetchTSnePlotWithExpression(experimentAccession, perplexity, geneId))
                .isNotEmpty()
                .doesNotHaveDuplicates()
                .allMatch(tSnePointDto -> tSnePointDto.expressionLevel() >= 0.0)
                .extracting("name")
                // There are potentially more cells in the TPMs file than in the t-SNE plot files
                .isSubsetOf(fetchCellIds(experimentAccession));
    }

    @Test
    void testClusters() {
        String experimentAccession = jdbcTestUtils.fetchRandomExperimentAccession();
        int k = jdbcTestUtils.fetchRandomKFromCellClusters(experimentAccession);
        int perplexity = jdbcTestUtils.fetchRandomPerplexityFromExperimentTSne(experimentAccession);

        assertThat(subject.fetchTSnePlotWithClusters(experimentAccession, perplexity, k))
                .isNotEmpty()
                .doesNotHaveDuplicates()
                .allMatch(tSnePointDto -> tSnePointDto.clusterK() <= k)
                .extracting("name")
                .isSubsetOf(fetchCellIds(experimentAccession));
    }

    private final static String SELECT_CELL_IDS_STATEMENT =
            "SELECT DISTINCT(cell_id) FROM scxa_analytics WHERE experiment_accession=?";
    private List<String> fetchCellIds(String experiment_accession) {
        return jdbcTemplate.queryForList(SELECT_CELL_IDS_STATEMENT, String.class, experiment_accession);
    }
}