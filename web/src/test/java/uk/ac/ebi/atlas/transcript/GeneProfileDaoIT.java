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
import javax.sql.DataSource;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class GeneProfileDaoIT {

    private static final String EXPERIMENT_ACCESSION = "experiment_accession";
    private static final String GENE_ID = "geneId";
    @Inject
    private GeneProfileDao subject;

    @Inject
    DataSource dataSource;

    private TranscriptProfile transcriptProfile1 = new TranscriptProfile("A_TRANSCRIPT_ID_1", Lists.newArrayList(2D, 3D));
    private TranscriptProfile transcriptProfile2 = new TranscriptProfile("A_TRANSCRIPT_ID_2", Lists.newArrayList(1D, 3D));


    @Before
    public void setup() {

    }

    @Test
    public void testAddTranscriptProfiles() throws Exception {

        subject.addTranscriptProfile(EXPERIMENT_ACCESSION, GENE_ID, transcriptProfile1);
        subject.addTranscriptProfile(EXPERIMENT_ACCESSION, GENE_ID, transcriptProfile2);

        Collection<TranscriptProfile> deserializedTranscriptProfiles = subject.getTranscriptProfiles(EXPERIMENT_ACCESSION, GENE_ID);

        assertThat(deserializedTranscriptProfiles, containsInAnyOrder(transcriptProfile1, transcriptProfile2));

    }

    @Test
    public void testFailsToFindNotExistingTranscriptProfile() throws Exception {

        subject.addTranscriptProfile(EXPERIMENT_ACCESSION, GENE_ID, transcriptProfile1);

        Collection<TranscriptProfile> transcriptProfiles = subject.getTranscriptProfiles(EXPERIMENT_ACCESSION, "not valid");

        assertThat(transcriptProfiles, is(empty()));

    }
}