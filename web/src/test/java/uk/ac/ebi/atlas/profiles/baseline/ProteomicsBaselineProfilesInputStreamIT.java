
package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;

import javax.annotation.Resource;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class ProteomicsBaselineProfilesInputStreamIT {

    public static final String EXPERIMENT_ACCESSION = "E-PROT-1";

    @Resource(name = "proteomicsBaselineProfileInputStreamFactory")
    private ProteomicsBaselineProfileInputStreamFactory inputStreamFactory;

    private ObjectInputStream<BaselineProfile> subject;

    private static final String ORGANISM_PART = "ORGANISM_PART";
    private static final double CUTOFF_ZERO = 0;
    private static final Factor DEVELOPMENTAL_STAGE_ADULT = new Factor("developmental stage", "adult");
    private static final Factor DEVELOPMENTAL_STAGE_FETUS = new Factor("developmental stage", "fetus");

    private void setup(Factor factor) {
        setup(factor,CUTOFF_ZERO) ;
    }

    private void setup(Factor factor, double cutoff) {
        subject = inputStreamFactory.createBaselineProfileInputStream(EXPERIMENT_ACCESSION, ORGANISM_PART, cutoff,
                ImmutableSet.of(factor));
    }

    @Test
    public void firstThreeProfiles_ForAdult() throws IOException {
        setup(DEVELOPMENTAL_STAGE_ADULT);
        BaselineProfile baselineProfile = subject.readNext();

        assertThat(baselineProfile.getId(), is("ENSG00000000003"));
        assertThat(baselineProfile.getName(), is("TSPAN6"));
        assertThat(baselineProfile.getSpecificity(), is(5));
        for(String organismPart: "colon ovary pancreas prostate".split(" ")){
            assertNotNull(baselineProfile.getKnownExpressionLevel(new Factor(ORGANISM_PART, organismPart)));
        }
        BaselineProfile baselineProfile2 = subject.readNext();

        assertThat(baselineProfile2.getId(), is("ENSG00000000419"));
        assertThat(baselineProfile2.getName(), is("DPM1"));
        assertThat(baselineProfile2.getSpecificity(), is(20));

        BaselineProfile baselineProfile3 = subject.readNext();

        assertThat(baselineProfile3.getId(), is("ENSG00000000457"));
        assertThat(baselineProfile3.getName(), is("SCYL3"));
        assertThat(baselineProfile3.getSpecificity(), is(1));
    }

    @Test
    public void firstThreeProfiles_ForFetus() throws IOException {
        setup(DEVELOPMENTAL_STAGE_FETUS);
        BaselineProfile baselineProfile = subject.readNext();

        assertThat(baselineProfile.getId(), is("ENSG00000000003"));
        assertThat(baselineProfile.getName(), is("TSPAN6"));
        assertThat(baselineProfile.getSpecificity(), is(1));
        assertNotNull(baselineProfile.getKnownExpressionLevel(new Factor(ORGANISM_PART, "ovary")));

        BaselineProfile baselineProfile2 = subject.readNext();

        assertThat(baselineProfile2.getId(), is("ENSG00000000419"));
        assertThat(baselineProfile2.getName(), is("DPM1"));
        assertThat(baselineProfile2.getSpecificity(), is(7));

        BaselineProfile baselineProfile3 = subject.readNext();

        assertThat(baselineProfile3.getId(), is("ENSG00000000971"));
        assertThat(baselineProfile3.getName(), is("CFH"));
        assertThat(baselineProfile3.getSpecificity(), is(7));
    }


    @Test
    public void cutoffChangesAmountsOfGenes() throws Exception {
        setup(DEVELOPMENTAL_STAGE_ADULT);
        long countProfiles1 = 0;
        while (subject.readNext() != null) {
            ++countProfiles1;
        }

        assertThat(countProfiles1, greaterThan(10L));

        setup(DEVELOPMENTAL_STAGE_ADULT, 1E-4);
        long countProfiles2 = 0;
        while (subject.readNext() != null) {
            ++countProfiles2;
        }

        assertThat(countProfiles2, greaterThan(5L));

        assertTrue(countProfiles1>countProfiles2);
    }

    @Test(expected = IllegalStateException.class)
    public void givenTheReaderHasBeenClosedReadNextShouldThrowIllegalStateException() throws Exception {
        setup(DEVELOPMENTAL_STAGE_ADULT);
        //given
        subject.close();
        //when
        subject.readNext();
    }


    @Test
    public void closingTwiceShouldNotThrowException() throws Exception {
        setup(DEVELOPMENTAL_STAGE_ADULT);
        //given
        subject.close();
        //when
        subject.close();
    }

}
