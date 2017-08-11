package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.model.experiment.differential.*;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.stream.RnaSeqProfileStreamFactory;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;
import uk.ac.ebi.atlas.trader.cache.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:dbContext.xml"})
public class RnaSeqProfilesHeatMapIT {

    @Inject
    ExpressionAtlasExperimentTrader experimentTrader;

    @Inject
    RnaSeqDiffExperimentsCache experimentsCache;

    @Inject
    RnaSeqProfileStreamFactory rnaSeqProfileStreamFactory;

    @Inject
    SolrQueryService solrQueryService;

    DifferentialProfilesHeatMap<DifferentialExpression, DifferentialExperiment, RnaSeqProfile, RnaSeqRequestContext> subject;

    DifferentialRequestPreferences requestPreferences;

    @Before
    public void setUp(){
        requestPreferences = new DifferentialRequestPreferences();
        subject = new DifferentialProfilesHeatMap<>(rnaSeqProfileStreamFactory,solrQueryService);
    }

    private RnaSeqRequestContext populateRequestContext(String experimentAccession) throws Exception {
        return populateRequestContext(experimentAccession, 1.0, 0.0);
    }

    private RnaSeqRequestContext populateRequestContext(
            String experimentAccession, double cutoff, double logFoldCutoff) throws Exception {
        requestPreferences.setFoldChangeCutoff(logFoldCutoff);
        requestPreferences.setCutoff(cutoff);
        DifferentialExperiment experiment = experimentsCache.getExperiment(experimentAccession);

        return new RnaSeqRequestContext(requestPreferences, experiment);
    }

    @Test
    public void testSomeExperiments() throws Exception {
        Collection<String> accessions = Lists.newArrayList(experimentTrader.getRnaSeqDifferentialExperimentAccessions
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
        RnaSeqRequestContext requestContext = populateRequestContext(accession);
        DifferentialExperiment experiment = requestContext.getExperiment();

        DifferentialProfilesList profiles = subject.fetch(requestContext);

        assertAbout(experiment, profiles);
    }

     private void testNotSpecific(String accession) throws Exception {
        requestPreferences.setSpecific(false);
        RnaSeqRequestContext requestContext = populateRequestContext(accession);
        DifferentialExperiment experiment = requestContext.getExperiment();

        DifferentialProfilesList profiles = subject.fetch(requestContext);

        assertAbout(experiment, profiles);
    }

    private void testUpAndDownRegulatedAndAlsoQueryFactorValues(String accession) throws Exception {
        RnaSeqRequestContext requestContext = populateRequestContext(accession);

        DifferentialProfilesList<RnaSeqProfile> profilesAll = subject.fetch(requestContext);

        requestPreferences.setRegulation(Regulation.UP);
        requestContext = populateRequestContext(accession);

        DifferentialProfilesList profilesUp = subject.fetch(requestContext);

        assertTrue(profilesAll.size()==50 || profilesAll.extractGeneNames().containsAll(profilesUp
                .extractGeneNames()));
        for(Object o: profilesUp) {
            RnaSeqProfile profile = (RnaSeqProfile) o;
            assertThat(
                    profile.getSpecificity(Regulation.DOWN),
                    is(0)
            );
        }


        requestPreferences.setRegulation(Regulation.DOWN);
        requestContext = populateRequestContext(accession);

        DifferentialProfilesList profilesDown = subject.fetch(requestContext);
        assertTrue(profilesAll.size()==50 || profilesAll.extractGeneNames().containsAll(profilesDown.extractGeneNames
                ()));
        for(Object o: profilesDown) {
            RnaSeqProfile profile = (RnaSeqProfile) o;
            assertThat(
                    profile.getSpecificity(Regulation.DOWN),
                    is(0)
            );
        }
        requestContext = populateRequestContext(accession);

        DifferentialProfilesList profilesQueryFactorValues = subject.fetch(requestContext);
        assertTrue(profilesAll.size() ==50 || profilesAll.extractGeneNames().containsAll
                (profilesQueryFactorValues
                .extractGeneNames()));
        assertAbout(requestContext.getExperiment(), profilesQueryFactorValues);

    }


    private void testNoResultsWithStrictCutoff(String accession) throws Exception {
        RnaSeqRequestContext requestContext = populateRequestContext(accession, 0.0, 1E90d);

        DifferentialProfilesList profiles = subject.fetch(requestContext);

        assertTrue( profiles.extractGeneNames().isEmpty());
    }


    private void assertAbout(DifferentialExperiment experiment, DifferentialProfilesList profiles){

        for(Object o: profiles){
            RnaSeqProfile profile = (RnaSeqProfile) o;
            assertEquals(true, profile.isExpressedOnAnyOf(experiment.getDataColumnDescriptors()));

            assertFalse(profile.getId().isEmpty());
            assertFalse(profile.getName().isEmpty());
        }

        assertThat(experiment.getAccession(), profiles.getTotalResultCount(), greaterThan(0));
        assertEquals(profiles.size(), profiles.extractGeneNames().size());
    }

}
