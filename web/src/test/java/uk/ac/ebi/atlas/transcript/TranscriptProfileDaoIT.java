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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfile;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
@Transactional  // enable transaction manager, so that changes to the database are rolled back after each test method
public class TranscriptProfileDaoIT {

    private static final String TEST_EXPERIMENT_ACCESSION = "delme";
    private static final String EXPERIMENT_ACCESSION = "E-MTAB-599";
    private static final String GENE_ID = "ENSMUSG00000064356";

    private static final String GENE_ID_1 = "geneId1";
    private static final String GENE_ID_2 = "geneId2";

    private TranscriptProfile transcriptProfile1 = new TranscriptProfile(GENE_ID_1, "A_TRANSCRIPT_ID_1", Lists.newArrayList("2", "3"));
    private TranscriptProfile transcriptProfile2 = new TranscriptProfile(GENE_ID_1, "A_TRANSCRIPT_ID_2", Lists.newArrayList("4", "0"));

    private TranscriptProfile transcriptProfile3 = new TranscriptProfile(GENE_ID_2, "A_TRANSCRIPT_ID_1", Lists.newArrayList("2", "3"));
    private TranscriptProfile transcriptProfile4 = new TranscriptProfile(GENE_ID_2, "A_TRANSCRIPT_ID_2", Lists.newArrayList("1", "3"));
    private TranscriptProfile transcriptProfile5 = new TranscriptProfile(GENE_ID_2, "A_TRANSCRIPT_ID_5", Lists.newArrayList("4", "5"));
    private TranscriptProfile transcriptProfile6 = new TranscriptProfile(GENE_ID_2, "A_TRANSCRIPT_ID_6", Lists.newArrayList("5", "4"));

    private List<TranscriptProfile> transcriptProfiles = Lists.newArrayList(transcriptProfile1, transcriptProfile2, transcriptProfile3,
            transcriptProfile4, transcriptProfile5, transcriptProfile6);

    @Inject
    private TranscriptProfileDao subject;

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
    }

    @Test
    public void testDeserializeTranscriptProfiles() {

        Collection<TranscriptProfile> deserializedTranscriptProfiles = subject.findTranscriptProfiles(EXPERIMENT_ACCESSION, GENE_ID);

        Iterator<TranscriptProfile> transcriptProfilesIterator = deserializedTranscriptProfiles.iterator();

        assertThat(transcriptProfilesIterator.next().getTranscriptId(), is("ENSMUST00000082407"));
        assertThat(transcriptProfilesIterator.hasNext(), is(false));

    }

    @Test
    public void testFailsToFindNotExistingTranscriptProfile() {

        Collection<TranscriptProfile> transcriptProfiles = subject.findTranscriptProfiles(EXPERIMENT_ACCESSION, "not valid");

        assertThat(transcriptProfiles, is(empty()));

    }

    @Test
    public void insertAndDeleteTranscriptProfiles() throws IOException {
        assertThat(getCount(), is(0));

        subject.loadTranscriptProfiles(TEST_EXPERIMENT_ACCESSION, transcriptProfiles);

        assertThat(getCount(), is(transcriptProfiles.size()));

        Collection<TranscriptProfile> deserializedTranscriptProfiles1 = subject.findTranscriptProfiles(TEST_EXPERIMENT_ACCESSION, GENE_ID_1);
        assertThat(deserializedTranscriptProfiles1, containsInAnyOrder(transcriptProfile1, transcriptProfile2));

        Collection<TranscriptProfile> deserializedTranscriptProfiles2 = subject.findTranscriptProfiles(TEST_EXPERIMENT_ACCESSION, GENE_ID_2);
        assertThat(deserializedTranscriptProfiles2, containsInAnyOrder(transcriptProfile3, transcriptProfile4, transcriptProfile5, transcriptProfile6));

        subject.deleteTranscriptProfilesForExperiment(TEST_EXPERIMENT_ACCESSION);

        assertThat(getCount(), is(0));
    }


    private int getCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) " +
                "FROM RNASEQ_BSLN_TRANSCRIPTS WHERE EXPERIMENT = ?", Integer.class, TEST_EXPERIMENT_ACCESSION);
    }

}