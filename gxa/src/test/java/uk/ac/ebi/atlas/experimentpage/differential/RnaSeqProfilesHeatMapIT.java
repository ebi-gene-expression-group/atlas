package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.*;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.model.experiment.differential.*;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;
import uk.ac.ebi.atlas.trader.cache.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class RnaSeqProfilesHeatMapIT {

    @Inject
    private ExpressionAtlasExperimentTrader experimentTrader;

    @Inject
    private RnaSeqDiffExperimentsCache experimentsCache;

    @Inject
    private RnaSeqProfilesHeatMap subject;

    @Inject
    RnaSeqRequestContextBuilder requestContextBuilder;

    private DifferentialRequestPreferences requestPreferences;

    public void setUp(){
        requestPreferences = new DifferentialRequestPreferences();
    }

    private RnaSeqRequestContext populateRequestContext(String experimentAccession) {
        return populateRequestContext(experimentAccession, 1.0, 0.0);
    }

    private RnaSeqRequestContext populateRequestContext(String experimentAccession, double cutoff, double
            logFoldCutoff) {
        requestPreferences.setFoldChangeCutOff(logFoldCutoff);
        requestPreferences.setCutoff(cutoff);
        DifferentialExperiment experiment = experimentsCache.getExperiment(experimentAccession);

        return requestContextBuilder.forExperiment(experiment)
                .withPreferences(requestPreferences)
                .build();
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


     private void testDefaultParameters(String accession) {
        RnaSeqRequestContext requestContext = populateRequestContext(accession);
        DifferentialExperiment experiment = requestContext.getExperiment();

        DifferentialProfilesList profiles = subject.fetch(requestContext);

        assertAbout(experiment, profiles);
    }

     private void testNotSpecific(String accession) {
        requestPreferences.setSpecific(false);
        RnaSeqRequestContext requestContext = populateRequestContext(accession);
        DifferentialExperiment experiment = requestContext.getExperiment();

        DifferentialProfilesList profiles = subject.fetch(requestContext);

        assertAbout(experiment, profiles);
    }

    private void testUpAndDownRegulatedAndAlsoQueryFactorValues(String accession) {
        RnaSeqRequestContext requestContext = populateRequestContext(accession);

        DifferentialProfilesList profilesAll = subject.fetch(requestContext);

        requestPreferences.setRegulation(Regulation.UP);
        requestContext = populateRequestContext(accession);

        DifferentialProfilesList profilesUp = subject.fetch(requestContext);

        assertTrue(profilesAll.size()==50 || profilesAll.extractGeneNames().containsAll(profilesUp
                .extractGeneNames()));
        for(Object o: profilesUp) {
            RnaSeqProfile profile = (RnaSeqProfile) o;
            for(Contrast contrast: profile.getConditions()){
                assertEquals(true, profile.getExpression(contrast).isOverExpressed());
                assertEquals(false, profile.getExpression(contrast).isUnderExpressed());
            }
        }


        requestPreferences.setRegulation(Regulation.DOWN);
        requestContext = populateRequestContext(accession);

        DifferentialProfilesList profilesDown = subject.fetch(requestContext);
        assertTrue(profilesAll.size()==50 || profilesAll.extractGeneNames().containsAll(profilesDown.extractGeneNames
                ()));
        for(Object o: profilesDown) {
            RnaSeqProfile profile = (RnaSeqProfile) o;
            for(Contrast contrast: profile.getConditions()){
                assertEquals(false, profile.getExpression(contrast).isOverExpressed());
                assertEquals(true, profile.getExpression(contrast).isUnderExpressed());
            }
        }

        requestPreferences.setQueryFactorValues(Collections.singleton(experimentsCache.getExperiment(accession).getContrastIds().iterator().next()));
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

        double maxUp = profiles.getMaxUpRegulatedExpressionLevel();
        double maxDown = profiles.getMaxDownRegulatedExpressionLevel();
        double minUp = profiles.getMinUpRegulatedExpressionLevel();
        double minDown = profiles.getMinDownRegulatedExpressionLevel();

        for(Object o: profiles){
            RnaSeqProfile profile = (RnaSeqProfile) o;
            double maxUpHere = profile.getMaxUpRegulatedExpressionLevel();
            double maxDownHere = profile.getMaxDownRegulatedExpressionLevel();
            assertTrue(String.format("%s %s %s >= %s", experiment.getAccession(), profile.getName(), maxUpHere, maxDownHere),
                    Double.isNaN(maxUpHere)|| Double.isNaN(maxDownHere) || maxUpHere >= maxDownHere );

            assertTrue(experiment.getContrasts().containsAll(profile.getConditions()));
            for(Contrast contrast: profile.getConditions()){
                assertEquals(true, profile.isExpressedOnAnyOf(Collections.singleton(contrast)));

                double expressionLevel = profile.getKnownExpressionLevel(contrast);
                if(! Double.isNaN(expressionLevel)) {
                    assertTrue(expressionLevel+"<="+maxUp, Double.isNaN(maxUp) || expressionLevel <= maxUp);
                    assertTrue(expressionLevel+">="+maxDown, Double.isNaN(maxDown) || expressionLevel >= maxDown);
                    assertTrue(Double.isNaN(minUp) || Double.isNaN(minDown) ||
                            expressionLevel >= minUp ||
                            expressionLevel <=  minDown);
                }

                DifferentialExpression expression = profile.getExpression(contrast);
                assertEquals(contrast, expression.getContrast());
                assertThat(expression.getPValue(), greaterThan(0d));
                assertThat(expression.getPValue(), lessThanOrEqualTo(1d));
                assertThat(expression.getAbsoluteFoldChange(), greaterThan(0d));
            }

            assertFalse(profile.getId().isEmpty());
            assertFalse(profile.getName().isEmpty());
        }

        assertThat(experiment.getAccession(), profiles.getTotalResultCount(), greaterThan(0));
        assertEquals(profiles.size(), profiles.extractGeneNames().size());
    }

}
