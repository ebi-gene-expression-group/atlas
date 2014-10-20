/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

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
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ProteomicsBaselineProfilesInputStreamIT {

    public static final String EXPERIMENT_ACCESSION = "E-PROT-1";

    @Resource(name = "proteomicsBaselineProfileInputStreamFactory")
    private ProteomicsBaselineProfileInputStreamFactory inputStreamFactory;

    private ObjectInputStream<BaselineProfile> subject;

    private static final String ORGANISM_PART = "ORGANISM_PART";
    private static final double CUTOFF_ZERO = 0;
    private static final Factor DEVELOPMENTAL_STAGE_ADULT = new Factor("developmental stage", "adult");
    private static final Factor DEVELOPMENTAL_STAGE_FETUS = new Factor("developmental stage", "fetus");

    public void setup(Factor factor) {
        subject = inputStreamFactory.createBaselineProfileInputStream(EXPERIMENT_ACCESSION, ORGANISM_PART, CUTOFF_ZERO, ImmutableSet.of(factor));
    }

    @Test
    public void firstThreeProfiles_ForAdult() throws IOException {
        setup(DEVELOPMENTAL_STAGE_ADULT);
        BaselineProfile baselineProfile = subject.readNext();

        assertThat(baselineProfile.getId(), is("ENSG00000000003"));
        assertThat(baselineProfile.getName(), is("TSPAN6"));
        assertThat(baselineProfile.getSpecificity(), is(5));
        assertThat(baselineProfile.getKnownExpressionLevel(new Factor(ORGANISM_PART, "colon")),is(9940000D));
        assertThat(baselineProfile.getKnownExpressionLevel(new Factor(ORGANISM_PART, "ovary")),is(6760000D));
        assertThat(baselineProfile.getKnownExpressionLevel(new Factor(ORGANISM_PART, "pancreas")),is(0.0000229));
        assertThat(baselineProfile.getKnownExpressionLevel(new Factor(ORGANISM_PART, "ovary")),is(6760000D));
        assertThat(baselineProfile.getKnownExpressionLevel(new Factor(ORGANISM_PART, "prostate")),is(0.00000731));

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
        assertThat(baselineProfile.getKnownExpressionLevel(new Factor(ORGANISM_PART, "ovary")),is(0.000052));

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
    public void readNextShouldReturnNullGivenAllExpressionLevelsHaveBeenRead() throws Exception {
        setup(DEVELOPMENTAL_STAGE_ADULT);
        long countProfiles = 0;
        while (subject.readNext() != null) {
            ++countProfiles;
        }

        assertThat(countProfiles, is(290L));
    }

    @Test
    public void setCutoffChangesSpecificity() throws IOException {
        subject = inputStreamFactory.createBaselineProfileInputStream(EXPERIMENT_ACCESSION, ORGANISM_PART, 4, ImmutableSet.of(DEVELOPMENTAL_STAGE_ADULT));

        BaselineProfile baselineProfile = subject.readNext();
        assertThat(baselineProfile.getId(), is("ENSG00000000003"));
        assertThat(baselineProfile.getName(), is("TSPAN6"));
        assertThat(baselineProfile.getSpecificity(), is(3));
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
