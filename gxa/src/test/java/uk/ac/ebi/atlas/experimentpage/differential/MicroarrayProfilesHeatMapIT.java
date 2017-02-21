package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.experiment.differential.Regulation;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;
import uk.ac.ebi.atlas.trader.cache.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class MicroarrayProfilesHeatMapIT {

    @Inject
    private ExpressionAtlasExperimentTrader experimentTrader;

    @Inject
    private MicroarrayExperimentsCache experimentsCache;

    @Inject
    private MicroarrayProfilesHeatMap subject;

    @Inject
    private MicroarrayRequestContextBuilder requestContextBuilder;

    private MicroarrayRequestPreferences requestPreferences;

    public void setUp(){
        requestPreferences = new MicroarrayRequestPreferences();
    }

    private MicroarrayRequestContext populateRequestContext(String experimentAccession) throws Exception {
        return populateRequestContext(experimentAccession, 1.0, 0.0);
    }

    private MicroarrayRequestContext populateRequestContext(
            String experimentAccession, double cutoff, double logFoldCutoff) throws Exception {
        requestPreferences.setFoldChangeCutOff(logFoldCutoff);
        requestPreferences.setCutoff(cutoff);
        MicroarrayExperiment experiment = experimentsCache.getExperiment(experimentAccession);

        return requestContextBuilder.forExperiment(experiment)
                .withPreferences(requestPreferences)
                .build();
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

        assertThat(profilesAll.size()==50 || profilesAll.extractGeneNames().containsAll(profilesUp.extractGeneNames()),
                is(true));

        for(Object o: profilesUp) {
            MicroarrayProfile profile = (MicroarrayProfile) o;
            for(Contrast contrast: profile.getConditions()){
                assertThat(profile.getExpression(contrast).isOverExpressed(), is(true));
                assertThat(profile.getExpression(contrast).isUnderExpressed(), is(false));
            }
        }

        requestPreferences.setRegulation(Regulation.DOWN);
        requestContext = populateRequestContext(accession);

        DifferentialProfilesList profilesDown = subject.fetch(requestContext);
        assertThat(profilesAll.size()==50 ||
                        profilesAll.extractGeneNames().containsAll(profilesDown.extractGeneNames()),
                is(true));
        for(Object o: profilesDown) {
            MicroarrayProfile profile = (MicroarrayProfile) o;
            for(Contrast contrast: profile.getConditions()){
                assertThat(profile.getExpression(contrast).isOverExpressed(), is(false));
                assertThat(profile.getExpression(contrast).isUnderExpressed(), is(true));
            }
        }

        setUp();
        requestPreferences.setQueryFactorValues(Collections.singleton(experimentsCache.getExperiment(accession).getDataColumnDescriptors()
                .iterator().next().getId()));
        requestContext = populateRequestContext(accession);

        DifferentialProfilesList profilesQueryFactorValues = subject.fetch(requestContext);
        assertThat(profilesAll.size() ==50 ||
                        profilesAll.extractGeneNames().containsAll(profilesQueryFactorValues.extractGeneNames()),
                is(true));
        assertAbout(requestContext.getExperiment(), profilesQueryFactorValues);
    }


    private void testNoResultsWithStrictCutoff(String accession) throws Exception {
        MicroarrayRequestContext requestContext = populateRequestContext(accession, 0.0, 1E90d);

        DifferentialProfilesList profiles = subject.fetch(requestContext);

        assertThat(profiles.extractGeneNames().isEmpty(), is(true));
    }


    private void assertAbout(DifferentialExperiment experiment, DifferentialProfilesList profiles){

        double maxUp = profiles.getMaxUpRegulatedExpressionLevel();
        double maxDown = profiles.getMaxDownRegulatedExpressionLevel();
        double minUp = profiles.getMinUpRegulatedExpressionLevel();
        double minDown = profiles.getMinDownRegulatedExpressionLevel();

        for(Object o: profiles){
            MicroarrayProfile profile = (MicroarrayProfile) o;
            double maxUpHere = profile.getMaxUpRegulatedExpressionLevel();
            double maxDownHere = profile.getMaxDownRegulatedExpressionLevel();
            assertThat(String.format("%s %s %s >= %s", experiment.getAccession(), profile.getName(), maxUpHere, maxDownHere),
                    Double.isNaN(maxUpHere)|| Double.isNaN(maxDownHere) || maxUpHere >= maxDownHere, is(true) );

            assertThat(experiment.getDataColumnDescriptors().containsAll(profile.getConditions()), is(true));
            for(Contrast contrast: profile.getConditions()){
                assertThat(profile.isExpressedOnAnyOf(Collections.singleton(contrast)), is(true));

                double expressionLevel = profile.getKnownExpressionLevel(contrast);
                if(! Double.isNaN(expressionLevel)) {
                    assertThat(expressionLevel+"<="+maxUp, Double.isNaN(maxUp) || expressionLevel <= maxUp, is(true));
                    assertThat(expressionLevel+">="+maxDown, Double.isNaN(maxDown) || expressionLevel >= maxDown, is(true));
                    assertThat(Double.isNaN(minUp) || Double.isNaN(minDown) ||
                            expressionLevel >= minUp || expressionLevel <=  minDown, is(true));
                }

                DifferentialExpression expression = profile.getExpression(contrast);
                assertThat(contrast, is(expression.getContrast()));
                assertThat(expression.getPValue(), greaterThan(0d));
                assertThat(expression.getPValue(), lessThanOrEqualTo(1d));
                assertThat(expression.getAbsoluteFoldChange(), greaterThan(0d));
            }

            assertThat(profile.getId().isEmpty(), is(false));
            assertThat(profile.getName().isEmpty(), is(false));
        }

        assertThat(experiment.getAccession(), profiles.getTotalResultCount(), greaterThan(0));
        assertThat(profiles.size(), is(profiles.extractGeneNames().size()));
    }


}
