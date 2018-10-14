package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.solr.common.SolrInputDocument;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.model.analyticsindex.SolrInputDocumentInputStream;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.solr.analytics.AnalyticsSearchService;
import uk.ac.ebi.atlas.solr.analytics.query.AnalyticsQueryClient;
import uk.ac.ebi.atlas.testutils.JdbcUtils;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.sql.DataSource;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SolrInputDocumentInputStreamIT {
    @Inject
    private DataSource dataSource;

    @Inject
    private JdbcUtils jdbcUtils;

    @Inject
    private ExperimentTrader experimentTrader;

    @Inject
    private ExperimentDataPointStreamFactory experimentDataPointStreamFactory;

    @Inject
    private AnalyticsQueryClient analyticsQueryClient;

    // TODO The lack of a subject is “code smell” that this class is testing stuff from here and there
    // TODO Split this into isolated tests for ExperimentDataPoint, SolrInputDocumentInputStream, etc.

    private Iterable<SolrInputDocument> getResults(Experiment experiment) throws Exception {
        Map<String, Map<BioentityPropertyName, Set<String>>> bioentityPropertyNames = ImmutableMap.of();

        SolrInputDocumentInputStream solrInputDocumentInputStream =
                new SolrInputDocumentInputStream(
                        experimentDataPointStreamFactory.stream(experiment),
                        bioentityPropertyNames);

        return new IterableObjectInputStream<>(solrInputDocumentInputStream);
    }

    @BeforeAll
    void populateDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/small-experiment-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/experiment-delete.sql"));
        populator.execute(dataSource);
    }

    @ParameterizedTest
    @MethodSource("randomExperimentAccessionProvider")
    void assertThatExperimentInformationIsTransformedIntoSolrInputDocuments(String accession) throws Exception {
        Experiment experiment = experimentTrader.getPublicExperiment(accession);
        Iterable<SolrInputDocument> result = getResults(experiment);

        int count = 0;
        for (SolrInputDocument solrInputDocument : result) {
            count++;

            assertThat(solrInputDocument.size(), greaterThan(6));
            assertThat(
                    experiment.getType().name().toUpperCase(),
                    is(solrInputDocument.getField("experiment_type").getValue()));
            assertThat(
                    experiment.getSpecies().getReferenceName(),
                    is(solrInputDocument.getField("species").getValue()));
        }

        assertThat(count, is(greaterThan(100)));
    }

    @ParameterizedTest
    @MethodSource("randomExperimentAccessionProvider")
    void generatedDocumentsAreCompatibleWithIndexContent(String experimentAccession) throws Exception {
        Collection<SolrInputDocument> results =
                ImmutableList.copyOf(getResults(experimentTrader.getPublicExperiment(experimentAccession)));

        assertThatIdentifiersInGeneratedDocumentsMatchCurrentIndexContent(experimentAccession, results);
        assertThatDocumentsReturnContent(experimentAccession, results);
        assertThatSpeciesFieldIsEnsemblName(experimentAccession, results);
    }

    private void
    assertThatIdentifiersInGeneratedDocumentsMatchCurrentIndexContent(String accession,
                                                                      Collection<SolrInputDocument> results) {
        Collection<String> identifiersForThatExperiment = AnalyticsSearchService.readBuckets(analyticsQueryClient
                .queryBuilder()
                .bioentityIdentifierFacets(-1)
                .inExperiment(accession)
                .fetch());

        assertThat(identifiersForThatExperiment, not(empty()));

        for (SolrInputDocument solrInputDocument: results) {
            String bioentityIdentifier = solrInputDocument.getField("bioentity_identifier").getValue().toString();
            assertThat(identifiersForThatExperiment, hasItem(bioentityIdentifier.toLowerCase()));
         }
    }

    private void assertThatDocumentsReturnContent(String accession, Collection<SolrInputDocument> results) {
        Map<String, String> keywordFieldsPresent = new HashMap<>();
        for (SolrInputDocument solrInputDocument: results) {
            for (String fieldName: solrInputDocument.getFieldNames()) {
                if (fieldName.startsWith("keyword_")) {
                    //we repeatedly put into the same fields but that's okay I just want one example value per field
                    keywordFieldsPresent.put(
                            fieldName.replace("keyword_", ""), solrInputDocument.getFieldValue(fieldName).toString());
                }
            }
        }

        //category searches e.g. symbol:PIM1
        for (Map.Entry<String, String> e: keywordFieldsPresent.entrySet()) {
            assertThatIndexReturnsDataFor(
                    accession, SemanticQuery.create(SemanticQueryTerm.create(e.getValue(), e.getKey())));
        }

        //identifier search searches e.g. symbol:PIM
        for (Map.Entry<String, String> e: keywordFieldsPresent.entrySet()) {
            assertThatIndexReturnsDataFor(accession, SemanticQuery.create(SemanticQueryTerm.create(e.getValue())));
        }

    }

    private void assertThatSpeciesFieldIsEnsemblName(String accession, Collection<SolrInputDocument> results) {
        Set<String> species = new HashSet<>();
        for (SolrInputDocument solrInputDocument: results) {
            species.add(solrInputDocument.getField("species").getValue().toString());
        }

        assertThat(species.size(), is(1));

        assertThat(species.iterator().next(),
                is(experimentTrader.getPublicExperiment(accession).getSpecies().getReferenceName()));
    }

    private void assertThatIndexReturnsDataFor(String accession, SemanticQuery identifierSearch) {
        Collection<String> identifiersForThatExperiment = AnalyticsSearchService.readBuckets(
                analyticsQueryClient.queryBuilder()
                        .bioentityIdentifierFacets(-1)
                        .queryIdentifierSearch(identifierSearch)
                        .inExperiment(accession)
                        .fetch()
        );

        assertThat(
                MessageFormat.format("Nothing in the index for {0} , {1}", accession, identifierSearch.description()),
                identifiersForThatExperiment, not(empty()));
    }

    private Stream<String> randomExperimentAccessionProvider() {
        return Stream.of(jdbcUtils.fetchRandomExpressionAtlasExperimentAccession());
    }
}
