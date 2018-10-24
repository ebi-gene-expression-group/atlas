package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
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
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.experiment.differential.Regulation;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.stream.MicroarrayProfileStreamFactory;
import uk.ac.ebi.atlas.solr.bioentities.query.SolrQueryService;
import uk.ac.ebi.atlas.testutils.JdbcUtils;
import uk.ac.ebi.atlas.trader.cache.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MicroarrayProfilesHeatMapIT {
    @Inject
    private DataSource dataSource;

    @Inject
    private JdbcUtils jdbcUtils;

    @Inject
    private MicroarrayExperimentsCache experimentsCache;

    @Inject
    private MicroarrayProfileStreamFactory microarrayProfileStreamFactory;

    @Inject
    private SolrQueryService solrQueryService;

    private MicroarrayRequestPreferences requestPreferences;

    private
    DifferentialProfilesHeatMap
            <MicroarrayExpression, MicroarrayExperiment, MicroarrayProfile, MicroarrayRequestContext> subject;

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
        subject = new DifferentialProfilesHeatMap<>(microarrayProfileStreamFactory, solrQueryService);
        requestPreferences = new MicroarrayRequestPreferences();
    }

    private MicroarrayRequestContext populateRequestContext(String experimentAccession) {
        return populateRequestContext(experimentAccession, 1.0, 0.0);
    }

    private MicroarrayRequestContext populateRequestContext(
            String experimentAccession, double cutoff, double logFoldCutoff) {
        requestPreferences.setFoldChangeCutoff(logFoldCutoff);
        requestPreferences.setCutoff(cutoff);
        MicroarrayExperiment experiment = experimentsCache.getExperiment(experimentAccession);

        return new MicroarrayRequestContext(requestPreferences, experiment);
    }

    @ParameterizedTest
    @MethodSource("microarrayDifferentialExperimentAccessionProvider")
    void testExperiment(String accession) {
        testDefaultParameters(accession);
        testNotSpecific(accession);
        testUpAndDownRegulated(accession);
        testNoResultsWithStrictCutoff(accession);
    }

    private void testDefaultParameters(String accession) {
        MicroarrayRequestContext requestContext = populateRequestContext(accession);
        DifferentialExperiment experiment = requestContext.getExperiment();

        DifferentialProfilesList<MicroarrayProfile> profiles = subject.fetch(requestContext);

        assertAbout(experiment, profiles);
    }

    private void testNotSpecific(String accession) {
        requestPreferences.setSpecific(false);
        MicroarrayRequestContext requestContext = populateRequestContext(accession);
        DifferentialExperiment experiment = requestContext.getExperiment();

        DifferentialProfilesList<MicroarrayProfile> profiles = subject.fetch(requestContext);

        assertAbout(experiment, profiles);
    }

    private void testUpAndDownRegulated(String accession) {
        MicroarrayRequestContext requestContext = populateRequestContext(accession);

        DifferentialProfilesList<MicroarrayProfile> profilesAll = subject.fetch(requestContext);

        requestPreferences.setRegulation(Regulation.UP);
        requestContext = populateRequestContext(accession);

        DifferentialProfilesList<MicroarrayProfile> profilesUp = subject.fetch(requestContext);

        assertThat(
                profilesAll.size() == 50 || extractGeneNames(profilesAll).containsAll(extractGeneNames(profilesUp)),
                is(true));

        requestPreferences.setRegulation(Regulation.DOWN);
        requestContext = populateRequestContext(accession);

        DifferentialProfilesList<MicroarrayProfile> profilesDown = subject.fetch(requestContext);
        assertThat(
                profilesAll.size() == 50 || extractGeneNames(profilesAll).containsAll(extractGeneNames(profilesDown)),
                is(true));

        assertAbout(requestContext.getExperiment(), profilesAll);
    }


    private void testNoResultsWithStrictCutoff(String accession) {
        MicroarrayRequestContext requestContext = populateRequestContext(accession, 0.0, 1E90d);

        DifferentialProfilesList profiles = subject.fetch(requestContext);

        assertThat(profiles.isEmpty(), is(true));
    }


    private void assertAbout(DifferentialExperiment experiment, List<MicroarrayProfile> profiles) {

        for (MicroarrayProfile profile: profiles) {
            assertThat(profile.getSpecificity(experiment.getDataColumnDescriptors()) > 0, is(true));
            assertThat(profile.getId().isEmpty(), is(false));
            assertThat(profile.getName().isEmpty(), is(false));
        }

        assertThat(experiment.getAccession(), profiles.size(), greaterThan(0));
        assertThat(
                experiment.getAccession(),
                profiles.size(),
                is(profiles.stream()
                        .map(p -> Joiner.on("").join(p.identifiers()))
                        .collect(Collectors.toSet()).size()));
    }

    private static <T extends Profile> ImmutableList<String> extractGeneNames(List<T> profiles) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (T profile : profiles) {
            builder.add(profile.getName());
        }
        return builder.build();
    }

    private Stream<String> microarrayDifferentialExperimentAccessionProvider() {
        List<ExperimentType> microarrayExperimentTypes =
                Lists.newArrayList(
                        MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL,
                        MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL,
                        MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL);

        Collections.shuffle(microarrayExperimentTypes);

        return Stream.of(jdbcUtils.fetchRandomExpressionAtlasExperimentAccession(microarrayExperimentTypes.get(0)));
    }
}
