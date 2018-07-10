package uk.ac.ebi.atlas.experimentpage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.WebConfig;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
class CellMetadataServiceIT {
    @Inject
    private IdfParser idfParser;
    @Inject
    private SolrCloudCollectionProxyFactory solrCloudCollectionProxyFactory;
    @Inject
    private JdbcUtils jdbcUtils;

    private CellMetadataService subject;

    @BeforeEach
    void setUp() {
        this.subject = new CellMetadataService(idfParser, solrCloudCollectionProxyFactory);
    }

    @Test
    void existingInferredCellType() {
        // Ideally we would retrieve a random experiment accession, but not all experiments have the inferred cell type characteristic
        String cellId = jdbcUtils.fetchRandomCellFromExperiment("E-ENAD-14");
        assertThat(
                subject.getInferredCellType(
                        "E-ENAD-14",
                        cellId))
                .isPresent();
    }

    @Test
    void inferredCellTypeForNonexistentExperimentId() {
        assertThat(subject.getInferredCellType("FOO", "FOO")).isNotPresent();
    }

    @Test
    void factorsForInvalidExperiment() {
        assertThat(subject.getFactors("FOO", "FOO")).isEmpty();
    }

    @Test
    void inferredCellTypeForNonexistentCellId() {
        assertThat(subject.getInferredCellType("E-ENAD-14", "FOO")).isNotPresent();
    }

    @Test
    void experimentWithMetadataFieldsInIdf() {
        // Ideally we would retrieve a random experiment accession, but not all experiments have curated metadata files in the idf file
        assertThat(
                subject.getIdfFileAttributes(
                        "E-ENAD-14",
                        jdbcUtils.fetchRandomCellFromExperiment("E-ENAD-14")))
                .isNotEmpty()
                .containsOnlyKeys("characteristic_individual");
    }

    @Test
    void experimentWithoutMetadataFieldsInIdf() {
        String experimentAccession = "E-GEOD-99058";    // Empty Comment[EAAdditionalAttributes] in IDF file

        assertThat(
                subject.getIdfFileAttributes(
                        experimentAccession,
                        jdbcUtils.fetchRandomCellFromExperiment(experimentAccession)))
                .isEmpty();

        assertThat(
                subject.getFactors(
                        experimentAccession,
                        jdbcUtils.fetchRandomCellFromExperiment(experimentAccession)))
                .isEmpty();
    }
}
