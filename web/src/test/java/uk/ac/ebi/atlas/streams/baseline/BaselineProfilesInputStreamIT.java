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

package uk.ac.ebi.atlas.streams.baseline;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.trader.cache.baseline.BaselineExperimentsCache;
import uk.ac.ebi.atlas.streams.InputStreamFactory;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.inject.Inject;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineProfilesInputStreamIT {

    public static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private static final String GENE_ID_2 = "ENSG00000127720";
    private static final String GENE_ID_1 = "ENSG00000244656";
    private static final String GENE_ID_3 = "ENSG00000224440";
    private static final String GENE_ID_6 = "ENSG00000266468";

    @Inject
    private InputStreamFactory inputStreamFactory;

    @Inject
    private BaselineRequestContextBuilder baselineRequestContextBuilder;

    private BaselineRequestContext baselineRequestContext;

    @Inject
    private BaselineExperimentsCache baselineExperimentsCache;

    private ObjectInputStream<BaselineProfile> subject;

    private BaselineRequestPreferences requestPreferences = new BaselineRequestPreferences();

    @Before
    public void setUp() throws Exception {

        requestPreferences.setCutoff(0.5d);
        requestPreferences.setQueryFactorType("ORGANISM_PART");

        BaselineExperiment experiment = baselineExperimentsCache.getExperiment(EXPERIMENT_ACCESSION);

        baselineRequestContext = baselineRequestContextBuilder.forExperiment(experiment).withPreferences(requestPreferences).build();

        subject = inputStreamFactory.createBaselineProfileInputStream(EXPERIMENT_ACCESSION);
    }

    @Test
    public void readNextShouldReturnNextExpression() throws IOException {
        //given
        BaselineProfile baselineProfile = subject.readNext();
        //then
        assertThat(baselineProfile.getId(), is(GENE_ID_1));
        assertThat(baselineProfile.getSpecificity(), is(1));

        //given we poll twice more
        baselineProfile = subject.readNext();
        //then
        assertThat(baselineProfile.getId(), is(GENE_ID_2));
        assertThat(baselineProfile.getSpecificity(), is(15));

        baselineProfile = subject.readNext();

        assertThat(baselineProfile.getId(), is(GENE_ID_3));
        assertThat(baselineProfile.getSpecificity(), is(1));
    }


    @Test
    public void readNextShouldReturnNullGivenAllExpressionLevelsHaveBeenRead() throws Exception {
        long countProfiles = 0;
        while (subject.readNext() != null) {
            ++countProfiles;
        }

        assertThat(countProfiles, is(263L));
    }

    @Test
    public void setCutoffChangesSpecificity() throws IOException {

        requestPreferences.setCutoff(4D);

        //when
        BaselineProfile baselineProfile = subject.readNext();
        //specificity that was 15 when cutoff was 0.5d now is 2
        assertThat(baselineProfile.getSpecificity(), is(1));

        //then
        subject.readNext();
        subject.readNext();

        //and next gene popping up after the third becomes the sixth, because 4th and 5th have lowern than 4D specificity.
        assertThat(subject.readNext().getId(), is(GENE_ID_6));

    }

    @Test(expected = IllegalStateException.class)
    public void givenTheReaderHasBeenClosedReadNextShouldThrowIllegalStateException() throws Exception {
        //given
        subject.close();
        //when
        subject.readNext();
    }


    @Test
    public void closingTwiceShouldNotThrowException() throws Exception {
        //given
        subject.close();
        //when
        subject.close();
    }

}
