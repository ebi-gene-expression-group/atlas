package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.OldBaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/oracleContext.xml"})
public class ProteomicsBaselineProfilesInputStreamIT {

    @Inject
    private BaselineProfileInputStreamFactory inputStreamFactory;

    @Inject
    private ExperimentTrader experimentTrader;

    private ObjectInputStream<OldBaselineProfile> subject;

    private static final String ORGANISM_PART = "ORGANISM_PART";
    private static final double CUTOFF_ZERO = 0;
    private static final Factor DEVELOPMENTAL_STAGE_ADULT = new Factor("developmental stage", "adult");
    private static final Factor DEVELOPMENTAL_STAGE_FETUS = new Factor("developmental stage", "fetus");

    private void setUp(Factor factor) throws Exception {
        setUp(factor, CUTOFF_ZERO) ;
    }

    private void setUp(Factor factor, double cutoff) throws Exception {
        subject = inputStreamFactory.createBaselineProfileInputStream((BaselineExperiment) experimentTrader.getPublicExperiment("E-PROT-1"),
                ORGANISM_PART, cutoff,
                ImmutableSet.of(factor));
    }

    @Test
    public void firstThreeProfiles_ForAdult() throws Exception {
        setUp(DEVELOPMENTAL_STAGE_ADULT);
        OldBaselineProfile baselineProfile = subject.readNext();

        assertThat(baselineProfile.getId(), is("ENSG00000000003"));
        assertThat(baselineProfile.getName(), is("TSPAN6"));
        assertThat(baselineProfile.getSpecificity(), is(23));
        for(String organismPart: "colon ovary pancreas prostate".split(" ")){
            assertNotNull(baselineProfile.getExpressionLevel(new Factor(ORGANISM_PART, organismPart)));
        }
        OldBaselineProfile baselineProfile2 = subject.readNext();

        assertThat(baselineProfile2.getId(), is("ENSG00000000419"));
        assertThat(baselineProfile2.getName(), is("DPM1"));
        assertThat(baselineProfile2.getSpecificity(), is(23));

        OldBaselineProfile baselineProfile3 = subject.readNext();

        assertThat(baselineProfile3.getId(), is("ENSG00000000457"));
        assertThat(baselineProfile3.getName(), is("SCYL3"));
        assertThat(baselineProfile3.getSpecificity(), is(23));
    }

    @Test
    public void firstThreeProfiles_ForFetus() throws Exception {
        setUp(DEVELOPMENTAL_STAGE_FETUS);
        OldBaselineProfile baselineProfile = subject.readNext();

        assertThat(baselineProfile.getId(), is("ENSG00000000003"));
        assertThat(baselineProfile.getName(), is("TSPAN6"));
        assertThat(baselineProfile.getSpecificity(), is(7));
        assertNotNull(baselineProfile.getExpressionLevel(new Factor(ORGANISM_PART, "ovary")));

        OldBaselineProfile baselineProfile2 = subject.readNext();

        assertThat(baselineProfile2.getId(), is("ENSG00000000419"));
        assertThat(baselineProfile2.getName(), is("DPM1"));
        assertThat(baselineProfile2.getSpecificity(), is(7));

        OldBaselineProfile baselineProfile3 = subject.readNext();

        assertThat(baselineProfile3.getId(), is("ENSG00000000457"));
        assertThat(baselineProfile3.getName(), is("SCYL3"));
        assertThat(baselineProfile3.getSpecificity(), is(7));
    }


    @Test
    public void cutoffChangesAmountsOfGenes() throws Exception {
        setUp(DEVELOPMENTAL_STAGE_ADULT);
        long countProfiles1 = 0;
        while (subject.readNext() != null) {
            ++countProfiles1;
        }

        assertThat(countProfiles1, greaterThan(10L));

        setUp(DEVELOPMENTAL_STAGE_ADULT, 1E-4);
        long countProfiles2 = 0;
        while (subject.readNext() != null) {
            ++countProfiles2;
        }

        assertThat(countProfiles2, greaterThan(5L));

        assertTrue(countProfiles1>countProfiles2);
    }

    @Test(expected = IllegalStateException.class)
    public void givenTheReaderHasBeenClosedReadNextShouldThrowIllegalStateException() throws Exception {
        setUp(DEVELOPMENTAL_STAGE_ADULT);
        //given
        subject.close();
        //when
        subject.readNext();
    }


    @Test
    public void closingTwiceShouldNotThrowException() throws Exception {
        setUp(DEVELOPMENTAL_STAGE_ADULT);
        //given
        subject.close();
        //when
        subject.close();
    }

}
