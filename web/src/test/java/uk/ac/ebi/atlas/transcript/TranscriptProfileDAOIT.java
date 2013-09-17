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

package uk.ac.ebi.atlas.transcript;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfile;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class TranscriptProfileDAOIT {

    private static final String EXPERIMENT_ACCESSION = "experiment_accession";
    private static final String GENE_ID_1 = "geneId1";
    private static final String GENE_ID_2 = "geneId2";

    @Inject
    private TranscriptProfileDAO subject;

    private TranscriptProfile transcriptProfile1 = new TranscriptProfile(GENE_ID_1, "A_TRANSCRIPT_ID_1", Lists.newArrayList(2D, 3D));
    private TranscriptProfile transcriptProfile2 = new TranscriptProfile(GENE_ID_1, "A_TRANSCRIPT_ID_2", Lists.newArrayList(1D, 3D));
    private TranscriptProfile transcriptProfile3 = new TranscriptProfile(GENE_ID_2, "A_TRANSCRIPT_ID_1", Lists.newArrayList(2D, 3D));
    private TranscriptProfile transcriptProfile4 = new TranscriptProfile(GENE_ID_2, "A_TRANSCRIPT_ID_2", Lists.newArrayList(1D, 3D));
    private TranscriptProfile transcriptProfile5 = new TranscriptProfile(GENE_ID_2, "A_TRANSCRIPT_ID_5", Lists.newArrayList(4D, 5D));
    private TranscriptProfile transcriptProfile6 = new TranscriptProfile(GENE_ID_2, "A_TRANSCRIPT_ID_6", Lists.newArrayList(5D, 4D));

    @Before
    public void setup() {
        subject.deleteTranscriptProfilesForExperiment(EXPERIMENT_ACCESSION);
        subject.addTranscriptProfile(EXPERIMENT_ACCESSION, transcriptProfile1);
        subject.addTranscriptProfile(EXPERIMENT_ACCESSION, transcriptProfile2);
        subject.addTranscriptProfile(EXPERIMENT_ACCESSION, transcriptProfile3);
        subject.addTranscriptProfile(EXPERIMENT_ACCESSION, transcriptProfile4);
    }

    @Test
    public void addedTranscriptProfilesShouldBeFoundWhenQueryingByGeneID() {

        List<TranscriptProfile> profiles = Lists.newArrayList(transcriptProfile5, transcriptProfile6);
        subject.addTranscriptProfiles(EXPERIMENT_ACCESSION, profiles);

        Collection<TranscriptProfile> transcriptProfiles = subject.findTranscriptProfiles(EXPERIMENT_ACCESSION, GENE_ID_2);
        assertThat(transcriptProfiles, containsInAnyOrder(transcriptProfile3, transcriptProfile4, transcriptProfile5, transcriptProfile6));

    }

    @Test
    public void testDeserializeTranscriptProfiles() {

        Collection<TranscriptProfile> deserializedTranscriptProfiles = subject.findTranscriptProfiles(EXPERIMENT_ACCESSION, GENE_ID_1);

        assertThat(deserializedTranscriptProfiles, containsInAnyOrder(transcriptProfile1, transcriptProfile2));

    }

    @Test
    public void testFailsToFindNotExistingTranscriptProfile() {

        Collection<TranscriptProfile> transcriptProfiles = subject.findTranscriptProfiles(EXPERIMENT_ACCESSION, "not valid");

        assertThat(transcriptProfiles, is(empty()));

    }

    @Test
    public void testDeleteTranscriptProfilesForGeneId() {

        subject.deleteTranscriptProfilesForExperimentAndGene(EXPERIMENT_ACCESSION, GENE_ID_1);
        assertThat(subject.findTranscriptProfiles(EXPERIMENT_ACCESSION, GENE_ID_1), is(empty()));
        assertThat(subject.findTranscriptProfiles(EXPERIMENT_ACCESSION, GENE_ID_2), hasSize(2));

    }

    @Test
    public void testDeleteTranscriptProfilesForExperiment() {

        subject.deleteTranscriptProfilesForExperiment(EXPERIMENT_ACCESSION);
        assertThat(subject.findTranscriptProfiles(EXPERIMENT_ACCESSION, GENE_ID_1), is(empty()));
        assertThat(subject.findTranscriptProfiles(EXPERIMENT_ACCESSION, GENE_ID_2), is(empty()));

    }

    @Test
    public void testDeleteTranscriptProfilesForNonExistingExperiment() {

        subject.deleteTranscriptProfilesForExperiment("UNKNOWN");
        assertThat(subject.findTranscriptProfiles(EXPERIMENT_ACCESSION, GENE_ID_1), hasSize(2));
        assertThat(subject.findTranscriptProfiles(EXPERIMENT_ACCESSION, GENE_ID_2), hasSize(2));

    }
}