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

package uk.ac.ebi.atlas.streams;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.*;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfilesInputStreamIT {

    public static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";
    @Mock
    ExperimentsCache cacheMock;

    private static final String RUN_ACCESSION_1 = "ERR030872";
    private static final String RUN_ACCESSION_2 = "ERR030873";
    private static final String RUN_ACCESSION_3 = "ERR030874";
    private static final String GENE_ID_1 = "ENST00000000233";
    private static final String GENE_ID_2 = "ENST00000000412";
    private static final String GENE_ID_3 = "ENST00000000442";

    private Map<String, ExperimentRun> experimentRuns = new HashMap<>();

    private URL dataFileURL;

    private ObjectInputStream<GeneProfile> subject;

    private GeneProfileBuilderFactory geneProfileBuilderFactory;

    @Before
    public void initSubject() throws Exception {

        dataFileURL = GeneProfilesInputStreamIT.class.getResource("testCSVReader-data.tab");

        ExperimentRun experimentRun1 = new ExperimentRun(RUN_ACCESSION_1)
                .addFactorValue("ORGANISM_PART", "org", "heart");
        ExperimentRun experimentRun2 = new ExperimentRun(RUN_ACCESSION_2)
                .addFactorValue("ORGANISM_PART", "org", "liver");
        ExperimentRun experimentRun3 = new ExperimentRun(RUN_ACCESSION_3)
                .addFactorValue("ORGANISM_PART", "org", "lung");

        Experiment experiment = new Experiment(EXPERIMENT_ACCESSION, null)
                .addAll(Lists.newArrayList(experimentRun1,experimentRun2,experimentRun3));

        when(cacheMock.getExperiment(anyString())).thenReturn(experiment);

        geneProfileBuilderFactory = new GeneProfileBuilderConcreteFactory();

        subject = new GeneProfilesInputStream.Builder(new GeneProfilesInputStream(geneProfileBuilderFactory), new ExpressionsBuffer.Builder(cacheMock))
                                                .forExperiment(EXPERIMENT_ACCESSION, dataFileURL.openStream())
                                                .create();

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

        //given
        subject = new GeneProfilesInputStream.Builder(new GeneProfilesInputStream(geneProfileBuilderFactory), new ExpressionsBuffer.Builder(cacheMock))
                                                .forExperiment("AN_ACCESSION", dataFileURL.openStream())
            .withCutoff(20D)
            .create();

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
