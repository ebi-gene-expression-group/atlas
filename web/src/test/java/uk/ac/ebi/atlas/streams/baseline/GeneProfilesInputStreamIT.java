/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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
import uk.ac.ebi.atlas.commands.impl.FilterParameters;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.GeneProfile;
import uk.ac.ebi.atlas.model.cache.baseline.BaselineExperimentsCache;
import uk.ac.ebi.atlas.web.RequestPreferences;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class GeneProfilesInputStreamIT {

    public static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private static final String GENE_ID_1 = "ENST00000000233";
    private static final String GENE_ID_2 = "ENST00000000412";
    private static final String GENE_ID_3 = "ENST00000000442";

    private URL dataFileURL;

    @Inject
    private GeneProfileInputStreamBuilder geneInputStreamBuilder;

    @Inject
    private FilterParameters filterParameters;

    @Inject
    private BaselineExperimentsCache experimentsCache;

    private ObjectInputStream<GeneProfile> subject;

    RequestPreferences requestPreferences = new RequestPreferences();

    @Before
    public void initSubject() throws Exception {
        requestPreferences.setCutoff(0.5d);
        requestPreferences.setQueryFactorType("ORGANISM_PART");

        BaselineExperiment experiment = experimentsCache.getExperiment(EXPERIMENT_ACCESSION);
        filterParameters.setRequestPreferences(requestPreferences);
        filterParameters.setFilteredBySpecies("homo");
        filterParameters.setSelectedFilterFactors(Collections.EMPTY_SET);
        filterParameters.setSelectedQueryFactors(Collections.EMPTY_SET);
        filterParameters.setAllQueryFactors(experiment.getExperimentalFactors().getAllFactors());

        dataFileURL = GeneProfilesInputStreamIT.class.getResource("testCSVReader-data.tab");

        subject = geneInputStreamBuilder.forExperiment(EXPERIMENT_ACCESSION, dataFileURL.openStream())
                .createGeneProfileInputStream();

    }

    @Test
    public void readNextShouldReturnNextExpression() throws IOException {
        //given
        GeneProfile geneProfile = subject.readNext();
        //then
        assertThat(geneProfile.getGeneId(), is(GENE_ID_1));
        assertThat(geneProfile.getSpecificity(), is(1));
        assertThat(geneProfile.iterator().hasNext(), is(true));
        //ToDo: GeneProfile needs a getter for Expressions

        //given we poll twice more
        geneProfile = subject.readNext();
        //then
        assertThat(geneProfile.getGeneId(), is(GENE_ID_2));
        assertThat(geneProfile.getSpecificity(), is(3));

        geneProfile = subject.readNext();

        assertThat(geneProfile.getGeneId(), is(GENE_ID_3));
        assertThat(geneProfile.getSpecificity(), is(2));
    }


    @Test
    public void readNextShouldReturnNullGivenAllExpressionLevelsHaveBeenRead() throws Exception {
        GeneProfile geneProfile;

        for (int i = 0; i < 3; i++) {
            //given
            geneProfile = subject.readNext();
            //then
            assertThat(geneProfile, is(notNullValue()));
        }
        //given
        geneProfile = subject.readNext();
        //then
        assertThat(geneProfile, is(nullValue()));
    }

    @Test
    public void setCutoffChangesSpecificity() throws IOException {

        requestPreferences.setCutoff(20D);

        //when
        subject.readNext();
        GeneProfile geneProfile = subject.readNext();

        //then specificity of second gene should change
        assertThat(geneProfile.getSpecificity(), is(2));

        //then third gene is not created since it has no expressions higher than cutoff.
        assertThat(subject.readNext(), is(nullValue()));

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
