package uk.ac.ebi.atlas.metadata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy;
import uk.ac.ebi.atlas.testutils.JdbcUtils;
import uk.ac.ebi.atlas.testutils.RandomDataTestUtils;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CellMetadataDaoIT {
    private static final Logger LOGGER = LoggerFactory.getLogger(CellMetadataDaoIT.class);

    @Inject
    private SolrCloudCollectionProxyFactory solrCloudCollectionProxyFactory;
    @Inject
    private JdbcUtils jdbcUtils;

    private CellMetadataDao subject;

    @BeforeEach
    void setUp() {
        this.subject = new CellMetadataDao(solrCloudCollectionProxyFactory);
    }

    @ParameterizedTest
    @MethodSource("experimentsWithMetadataProvider")
    public void validExperimentAccessionHasMetadataFields(String experimentAccession) {
        assertThat(subject.getMetadataFieldNames(experimentAccession)).isNotEmpty();
    }

    @Test
    public void invalidExperimentAccessionHasNoMetadata() {
        String experimentAccession = RandomDataTestUtils.getRandomExperimentAccession();

        assertThat(subject.getMetadataFieldNames(experimentAccession)).isEmpty();
    }

    @Test
    public void invalidCellIdHasNoMetadata() {
        String experimentAccession = jdbcUtils.fetchRandomSingleCellExperimentAccession();
        String cellId = "FOOBAR";

        List<SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField> metadataFieldNames = subject.getMetadataFieldNames(experimentAccession);

        assertThat(metadataFieldNames).isNotEmpty();

        metadataFieldNames.forEach(field -> {
            assertThat(subject.getMetadataValueForCellId(experimentAccession, field, cellId)).isNotPresent();
        });
    }

    @ParameterizedTest
    @MethodSource("experimentsWithMetadataProvider")
    public void validCellIdHasMetadataValues(String experimentAccession) {
        String cellId = jdbcUtils.fetchRandomCellFromExperiment(experimentAccession);

        LOGGER.info("Retrieving metadata field names for experiment " + experimentAccession);
        List<SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField> metadataFieldNames = subject.getMetadataFieldNames(experimentAccession);

        assertThat(metadataFieldNames).isNotEmpty();

        metadataFieldNames.forEach(field -> {
            LOGGER.info("Retrieving values for " + field.displayName() + " metadata for cell ID " + cellId + " from experiment " + experimentAccession);

            assertThat(subject.getMetadataValueForCellId(experimentAccession, field, cellId)).isPresent();
        });
    }

    @ParameterizedTest
    @MethodSource("experimentsWithFactorsProvider")
    public void validExperimentAccessionHasFactorFields(String experimentAccession) {
        String cellId = jdbcUtils.fetchRandomCellFromExperiment(experimentAccession);

        LOGGER.info("Retrieving factor fields for cell ID " + cellId + " from experiment " + experimentAccession);
        assertThat(subject.getFactorFieldNames(experimentAccession, cellId)).isNotEmpty();
    }

    @Test
    public void invalidCellIdAndExperimentAccessionHasNoFactorFields() {
        String experimentAccession = RandomDataTestUtils.getRandomExperimentAccession();
        String cellId = "FOO";

        assertThat(subject.getFactorFieldNames(experimentAccession, cellId)).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("experimentsWithMetadataProvider")
    public void validCellIdsHaveMetadataValues(String experimentAccession) {
        List<String> cellIds = jdbcUtils.fetchRandomListOfCellsFromExperiment(experimentAccession, ThreadLocalRandom.current().nextInt(1, 2000));

        LOGGER.info("Retrieving metadata field names for experiment " + experimentAccession);
        List<SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField> metadataFieldNames = subject.getMetadataFieldNames(experimentAccession);

        assertThat(metadataFieldNames).isNotEmpty();

        metadataFieldNames.forEach(field -> {
            LOGGER.info("Retrieving values for " + field.displayName() + " metadata for " + cellIds.size() + " random cell IDs from experiment " + experimentAccession);

            assertThat(subject.getMetadataValueForCellIds(experimentAccession, field, cellIds))
                    .isNotEmpty()
                    .containsKeys(cellIds.toArray(new String[0]));
        });

    }

    @Test
    public void getFieldValuesForNoFieldsReturnsEmpty() {
        String experimentAccession = jdbcUtils.fetchRandomSingleCellExperimentAccession();

        assertThat(subject.getQueryResultForMultiValueFields(experimentAccession, Optional.empty())).isEmpty();
    }

    private Iterable<String> experimentsWithMetadataProvider() {
        // E-GEOD-99058 does not have any metadata (factors or inferred cell types)
        return jdbcUtils.getPublicSingleCellExperimentAccessions()
                .stream()
                .filter(accession -> !accession.equalsIgnoreCase("E-GEOD-99058"))
                .collect(Collectors.toSet());
    }

    private Iterable<String> experimentsWithFactorsProvider() {
        // E-GEOD-99058 and E-ENAD-13 do not have any factors
        return jdbcUtils.getPublicSingleCellExperimentAccessions()
                .stream()
                .filter(accession -> !accession.equalsIgnoreCase("E-GEOD-99058") && !accession.equalsIgnoreCase("E-ENAD-13") )
                .collect(Collectors.toSet());
    }
}
