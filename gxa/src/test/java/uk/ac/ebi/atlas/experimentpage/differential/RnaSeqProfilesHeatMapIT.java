package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.experiment.differential.Regulation;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.stream.RnaSeqProfileStreamFactory;
import uk.ac.ebi.atlas.solr.bioentities.query.SolrQueryService;
import uk.ac.ebi.atlas.testutils.JdbcUtils;
import uk.ac.ebi.atlas.trader.cache.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.RNASEQ_MRNA_DIFFERENTIAL;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RnaSeqProfilesHeatMapIT {
    @Inject
    private DataSource dataSource;

    @Inject
    private JdbcUtils jdbcUtils;

    @Inject
    private RnaSeqDiffExperimentsCache experimentsCache;

    @Inject
    private RnaSeqProfileStreamFactory rnaSeqProfileStreamFactory;

    @Inject
    private SolrQueryService solrQueryService;

    private DifferentialRequestPreferences requestPreferences;

    private
    DifferentialProfilesHeatMap<DifferentialExpression, DifferentialExperiment, RnaSeqProfile, RnaSeqRequestContext>
            subject;

    @BeforeAll
    void populateDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/experiment-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/experiment-delete.sql"));
        populator.execute(dataSource);
    }

    @BeforeEach
    void setUp() {
        requestPreferences = new DifferentialRequestPreferences();
        subject = new DifferentialProfilesHeatMap<>(rnaSeqProfileStreamFactory, solrQueryService);
    }

    private RnaSeqRequestContext populateRequestContext(String experimentAccession) {
        return populateRequestContext(experimentAccession, 1.0, 0.0);
    }

    private RnaSeqRequestContext populateRequestContext(
            String experimentAccession, double cutoff, double logFoldCutoff) {
        requestPreferences.setFoldChangeCutoff(logFoldCutoff);
        requestPreferences.setCutoff(cutoff);
        DifferentialExperiment experiment = experimentsCache.getExperiment(experimentAccession);

        return new RnaSeqRequestContext(requestPreferences, experiment);
    }

    @ParameterizedTest
    @MethodSource("rnaSeqDifferentialExperimentAccessionProvider")
    void testExperiment(String accession) {
        testDefaultParameters(accession);
        testNotSpecific(accession);
        testUpAndDownRegulated(accession);
        testNoResultsWithStrictCutoff(accession);
    }

     private void testDefaultParameters(String accession) {
        RnaSeqRequestContext requestContext = populateRequestContext(accession);
        DifferentialExperiment experiment = requestContext.getExperiment();

        DifferentialProfilesList<RnaSeqProfile> profiles = subject.fetch(requestContext);

        assertAbout(experiment, profiles);
    }

     private void testNotSpecific(String accession) {
        requestPreferences.setSpecific(false);
        RnaSeqRequestContext requestContext = populateRequestContext(accession);
        DifferentialExperiment experiment = requestContext.getExperiment();

        DifferentialProfilesList<RnaSeqProfile> profiles = subject.fetch(requestContext);

        assertAbout(experiment, profiles);
    }

    private void testUpAndDownRegulated(String accession) {
        RnaSeqRequestContext requestContext = populateRequestContext(accession);

        DifferentialProfilesList<RnaSeqProfile> profilesAll = subject.fetch(requestContext);

        requestPreferences.setRegulation(Regulation.UP);
        requestContext = populateRequestContext(accession);

        DifferentialProfilesList<RnaSeqProfile> profilesUp = subject.fetch(requestContext);

        assertThat(
                profilesAll.size() == 50 || extractGeneNames(profilesAll).containsAll(extractGeneNames(profilesUp)),
                is(true));

        requestPreferences.setRegulation(Regulation.DOWN);
        requestContext = populateRequestContext(accession);

        DifferentialProfilesList<RnaSeqProfile> profilesDown = subject.fetch(requestContext);
        assertThat(
                profilesAll.size() == 50 || extractGeneNames(profilesAll).containsAll(extractGeneNames(profilesDown)),
                is(true));

        assertAbout(requestContext.getExperiment(), profilesAll);
    }


    private void testNoResultsWithStrictCutoff(String accession) {
        RnaSeqRequestContext requestContext = populateRequestContext(accession, 0.0, 1E90d);

        DifferentialProfilesList<RnaSeqProfile> profiles = subject.fetch(requestContext);

        assertThat(extractGeneNames(profiles).size(), is(0));
    }


    private void assertAbout(DifferentialExperiment experiment, List<RnaSeqProfile> profiles) {

        for (RnaSeqProfile profile: profiles) {
            assertThat(profile.getSpecificity(experiment.getDataColumnDescriptors()) > 0, is(true));
            assertThat(profile.getId().isEmpty(), is(false));
            assertThat(profile.getName().isEmpty(), is(false));
        }

        assertThat(experiment.getAccession(), profiles.size(), greaterThan(0));
        assertThat(profiles.size(), is(profiles.stream().map(Profile::getId).collect(Collectors.toSet()).size()));
    }

    private static <T extends Profile> ImmutableList<String> extractGeneNames(List<T> profiles) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (T profile : profiles) {
            builder.add(profile.getName());
        }
        return builder.build();
    }

    private Stream<String> rnaSeqDifferentialExperimentAccessionProvider() {
        return Stream.of(jdbcUtils.fetchRandomExpressionAtlasExperimentAccession(RNASEQ_MRNA_DIFFERENTIAL));
    }
}
