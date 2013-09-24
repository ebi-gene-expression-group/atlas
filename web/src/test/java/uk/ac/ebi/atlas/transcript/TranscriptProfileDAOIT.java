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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfile;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class TranscriptProfileDAOIT {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-599";
    private static final String GENE_ID_1 = "ENSMUSG00000064356";

    @Inject
    private TranscriptProfileDAO subject;


    @Before
    public void setup() {
    }

    @Test
    public void testDeserializeTranscriptProfiles() {

        Collection<TranscriptProfile> deserializedTranscriptProfiles = subject.findTranscriptProfiles(EXPERIMENT_ACCESSION, GENE_ID_1);

        Iterator<TranscriptProfile> transcriptProfilesIterator = deserializedTranscriptProfiles.iterator();

        assertThat(transcriptProfilesIterator.next().getTranscriptId(), is("ENSMUST00000082407"));
        assertThat(transcriptProfilesIterator.hasNext(), is(false));

    }

    @Test
    public void testFailsToFindNotExistingTranscriptProfile() {

        Collection<TranscriptProfile> transcriptProfiles = subject.findTranscriptProfiles(EXPERIMENT_ACCESSION, "not valid");

        assertThat(transcriptProfiles, is(empty()));

    }

}