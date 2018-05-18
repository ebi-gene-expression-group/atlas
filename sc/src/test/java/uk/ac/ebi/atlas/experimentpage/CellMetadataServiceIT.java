package uk.ac.ebi.atlas.experimentpage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class CellMetadataServiceIT {

    @Inject
    private IdfParser idfParser;
    @Inject
    private SolrCloudCollectionProxyFactory solrCloudCollectionProxyFactory;
    @Inject
    private JdbcUtils jdbcUtils;

    private CellMetadataService subject;

    @Before
    public void setUp() {
        this.subject = new CellMetadataService(idfParser, solrCloudCollectionProxyFactory);
    }

    @Test
    public void existingInferredCellType() {
        // Ideally we would retrieve a random experiment accession, but not all experiments have the inferred cell type characteristic
        String cellId = jdbcUtils.fetchRandomCellFromExperiment("E-ENAD-14");
        assertThat(
                subject.getInferredCellType(
                        "E-ENAD-14",
                        cellId))
                .isPresent();
    }

    @Test
    public void inferredCellTypeForNonexistentExperimentId() {
        assertThat(subject.getInferredCellType("FOO", "FOO")).isNotPresent();
    }

    @Test
    public void factorsForValidExperiment() {
        String experimentAccession = jdbcUtils.fetchRandomSingleCellExperimentAccession();
        String cellId = jdbcUtils.fetchRandomCellFromExperiment(experimentAccession);

        assertThat(subject.getFactors(experimentAccession, cellId)).isNotEmpty();
    }

    @Test
    public void factorsForInvalidExperiment() {
        assertThat(subject.getFactors("FOO", "FOO")).isEmpty();
    }

    @Test
    public void inferredCellTypeForNonexistentCellId() {
        assertThat(subject.getInferredCellType("E-ENAD-14", "FOO")).isNotPresent();
    }

    @Test
    public void experimentWithMetadataFieldsInIdf() {
        // Ideally we would retrieve a random experiment accession, but not all experiments have curated metadata files in the idf file
        assertThat(
                subject.getIdfFileAttributes(
                        "E-ENAD-14",
                        jdbcUtils.fetchRandomCellFromExperiment("E-ENAD-14")))
                .isNotEmpty()
                .containsOnlyKeys("characteristic_individual");
    }

    @Test
    public void experimentWithoutMetadataFieldsInIdf() {
        assertThat(
                subject.getIdfFileAttributes(
                        "E-MTAB-5061",
                        jdbcUtils.fetchRandomCellFromExperiment("E-MTAB-5061")))
                .isEmpty();
    }
}
