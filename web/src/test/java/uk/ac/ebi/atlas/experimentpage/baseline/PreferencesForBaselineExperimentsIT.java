package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.inject.Inject;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class PreferencesForBaselineExperimentsIT {

    private PreferencesForBaselineExperiments subject = new PreferencesForBaselineExperiments();

    @Inject
    BaselineExperimentsCache baselineExperimentsCache;

    @Test
    public void searchingForAllFactorsSetsSpecificToFalse_singleFactorExperiment() throws ExecutionException {
        BaselineExperiment experiment = baselineExperimentsCache.getExperiment("E-MTAB-3827");
        ImmutableSet<String> allFactors = ImmutableSet.of("cord blood", "venous blood");

        BaselineRequestPreferences preferences = new BaselineRequestPreferences();
        preferences.setSpecific(true);
        preferences.setQueryFactorValues(allFactors);

        subject.setPreferenceDefaults(preferences, experiment);

        assertThat(preferences.isSpecific(), is(false));
    }

    @Test
    public void searchingForAllFactorsSetsSpecificToFalse_multiFactorExperiment() throws ExecutionException {
        BaselineExperiment experiment = baselineExperimentsCache.getExperiment("E-MTAB-2812");
        ImmutableSet<String> allFactors = ImmutableSet.of("hermaphrodite");

        BaselineRequestPreferences preferences = new BaselineRequestPreferences();
        preferences.setSpecific(true);
        preferences.setQueryFactorValues(allFactors);
        preferences.setSerializedFilterFactors("DEVELOPMENTAL_STAGE:3-fold embryo Ce,ORGANISM_PART:organism");

        subject.setPreferenceDefaults(preferences, experiment);

        assertThat(preferences.isSpecific(), is(false));
    }

}