package uk.ac.ebi.atlas.search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.MapEntry.entry;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_INFERRED_CELL_TYPE;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_ORGANISM_PART;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_SPECIES;

@Transactional
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class GeneSearchServiceDaoIT {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneSearchServiceDaoIT.class);

    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Inject
    private SolrCloudCollectionProxyFactory solrCloudCollectionProxyFactory;

    @Inject
    private JdbcUtils jdbcTestUtils;

    private GeneSearchServiceDao subject;

    @BeforeEach
    public void setUp() {
        subject = new GeneSearchServiceDao(namedParameterJdbcTemplate, solrCloudCollectionProxyFactory);
    }

    @Test
    public void validGeneIdReturnsCellIdsPerExperiment() {
        String geneId = jdbcTestUtils.fetchRandomGene();

        LOGGER.info("Retrieving cell IDs for gene ID " + geneId);

        Map<String, List<String>> result = subject.fetchCellIds(geneId);

        assertThat(result).isNotEmpty();
    }

    @Test
    @Sql({"scxa_experiment_fixture.sql", "scxa_marker_genes_fixture.sql"})
    public void validGeneIdReturnKAndClusterIds() {
        String geneId = "ENSG00000000009";

        Map<String, Map<Integer, List<Integer>>> result = subject.fetchKAndClusterIds(geneId);

        assertThat(result)
                .containsKeys("E-GEOD-106540")
                .doesNotContainKeys("E-ENAD-13", "E-ENAD-14", "E-EHCA-2", "E-GEOD-99058");
        
        // Only marker genes with probablity < 0.05 are returned
        assertThat(result.get("E-GEOD-106540"))
                .containsOnly(
                        entry(3, Collections.singletonList(0)),
                        entry(2, Collections.singletonList(1)));

    }

    @Test
    @Sql({"scxa_experiment_fixture.sql", "scxa_marker_genes_fixture.sql"})
    public void searchForGeneOverProbabilityThresholdReturnsEmpty() {
        String geneId = "ENSMUSG00000000006";

        Map<String, Map<Integer, List<Integer>>> result = subject.fetchKAndClusterIds(geneId);

        assertThat(result).isEmpty();
    }

    @Test
    public void invalidGeneIdReturnsEmpty() {
        String geneId = "FOO";

        Map<String, Map<Integer, List<Integer>>> result = subject.fetchKAndClusterIds(geneId);

        assertThat(result).isEmpty();
    }


    @Test
    public void getFacetsForValidCellIds() {
        List<String> cellIds = jdbcTestUtils.fetchRandomListOfCells(10);

        LOGGER.info("Retrieving facets for cell IDs " + String.join(", ", cellIds));

        Map<String, Map<String, List<String>>> result = subject.getFacets(cellIds, CHARACTERISTIC_INFERRED_CELL_TYPE, CHARACTERISTIC_ORGANISM_PART, CHARACTERISTIC_SPECIES);

        assertThat(result).isNotEmpty();
    }
}
