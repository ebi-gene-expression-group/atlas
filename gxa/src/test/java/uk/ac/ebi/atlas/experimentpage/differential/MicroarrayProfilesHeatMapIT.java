package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.differential.*;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.stream.MicroarrayProfileStreamFactory;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;
import uk.ac.ebi.atlas.trader.cache.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "/dispatcher-servlet.xml"})
public class MicroarrayProfilesHeatMapIT {

    @Inject
    ExpressionAtlasExperimentTrader experimentTrader;

    @Inject
    MicroarrayExperimentsCache experimentsCache;

    @Inject
    MicroarrayProfileStreamFactory microarrayProfileStreamFactory;

    @Inject
    SolrQueryService solrQueryService;

    DifferentialProfilesHeatMap<MicroarrayExpression, MicroarrayExperiment, MicroarrayProfile, MicroarrayRequestContext> subject;

    MicroarrayRequestPreferences requestPreferences;

    @Before
    public void setUp(){
        subject = new DifferentialProfilesHeatMap<>(microarrayProfileStreamFactory, solrQueryService) ;
        requestPreferences = new MicroarrayRequestPreferences();
    }

    private MicroarrayRequestContext populateRequestContext(String experimentAccession) throws Exception {
        return populateRequestContext(experimentAccession, 1.0, 0.0);
    }

    private MicroarrayRequestContext populateRequestContext(
            String experimentAccession, double cutoff, double logFoldCutoff) throws Exception {
        requestPreferences.setFoldChangeCutoff(logFoldCutoff);
        requestPreferences.setCutoff(cutoff);
        MicroarrayExperiment experiment = experimentsCache.getExperiment(experimentAccession);

        return new MicroarrayRequestContext(requestPreferences, experiment);
    }

    @Test
    public void testSomeExperiments() throws Exception {
        Collection<String> accessions = Lists.newArrayList(experimentTrader.getMicroarrayExperimentAccessions
                ()).subList(0,10);

        for(String accession: accessions){
            setUp();
            testExperiment(accession);
        }
    }

    private void testExperiment(String accession) throws Exception {

        testDefaultParameters(accession);

        testNotSpecific(accession);

        testUpAndDownRegulatedAndAlsoQueryFactorValues(accession);

        testNoResultsWithStrictCutoff(accession);
    }

    private void testDefaultParameters(String accession) throws Exception {
        MicroarrayRequestContext requestContext = populateRequestContext(accession);
        DifferentialExperiment experiment = requestContext.getExperiment();

        DifferentialProfilesList profiles = subject.fetch(requestContext);

        assertAbout(experiment, profiles);
    }

    private void testNotSpecific(String accession) throws Exception {
        requestPreferences.setSpecific(false);
        MicroarrayRequestContext requestContext = populateRequestContext(accession);
        DifferentialExperiment experiment = requestContext.getExperiment();

        DifferentialProfilesList profiles = subject.fetch(requestContext);

        assertAbout(experiment, profiles);
    }

    private void testUpAndDownRegulatedAndAlsoQueryFactorValues(String accession) throws Exception {
        MicroarrayRequestContext requestContext = populateRequestContext(accession);

        DifferentialProfilesList<MicroarrayProfile> profilesAll = subject.fetch(requestContext);

        requestPreferences.setRegulation(Regulation.UP);
        requestContext = populateRequestContext(accession);

        DifferentialProfilesList profilesUp = subject.fetch(requestContext);

        assertThat(
                profilesAll.size() == 50 || extractGeneNames(profilesAll).containsAll(extractGeneNames(profilesUp)),
                is(true));

        requestPreferences.setRegulation(Regulation.DOWN);
        requestContext = populateRequestContext(accession);

        DifferentialProfilesList profilesDown = subject.fetch(requestContext);
        assertThat(
                profilesAll.size() == 50 || extractGeneNames(profilesAll).containsAll(extractGeneNames(profilesDown)),
                is(true));

        setUp();
        requestContext = populateRequestContext(accession);

        DifferentialProfilesList profilesQueryFactorValues = subject.fetch(requestContext);
        assertThat(
                profilesAll.size() == 50 ||
                        extractGeneNames(profilesAll).containsAll(extractGeneNames(profilesQueryFactorValues)),
                is(true));
        assertAbout(requestContext.getExperiment(), profilesQueryFactorValues);
    }


    private void testNoResultsWithStrictCutoff(String accession) throws Exception {
        MicroarrayRequestContext requestContext = populateRequestContext(accession, 0.0, 1E90d);

        DifferentialProfilesList profiles = subject.fetch(requestContext);

        assertThat(profiles.isEmpty(), is(true));
    }


    private void assertAbout(DifferentialExperiment experiment, List<MicroarrayProfile> profiles){

        for(MicroarrayProfile profile: profiles){
            assertThat(profile.getSpecificity(experiment.getDataColumnDescriptors()) > 0, is(true));
            assertThat(profile.getId().isEmpty(), is(false));
            assertThat(profile.getName().isEmpty(), is(false));
        }

        assertThat(experiment.getAccession(), profiles.size(), greaterThan(0));
        assertThat(experiment.getAccession(), profiles.size(), is(profiles.stream().map(p -> Joiner.on("").join(p.identifiers())).collect(Collectors.toSet()).size()));
    }

    static <T extends Profile> ImmutableList<String> extractGeneNames(List<T> profiles) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (T profile : profiles) {
            builder.add(profile.getName());
        }
        return builder.build();
    }


}
