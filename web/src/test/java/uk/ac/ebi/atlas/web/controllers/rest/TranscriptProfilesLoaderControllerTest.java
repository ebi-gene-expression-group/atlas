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

package uk.ac.ebi.atlas.web.controllers.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.transcript.TranscriptProfilesLoader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TranscriptProfilesLoaderControllerTest {

    public static final String EXPERIMENT_ACCESSION = "experimentAccession";
    @Mock
    private TranscriptProfilesLoader transcriptProfileLoaderMock;

    private TranscriptProfilesLoaderController subject;

    @Before
    public void setUp() throws Exception {
        subject = new TranscriptProfilesLoaderController(transcriptProfileLoaderMock);
    }

    @Test
    public void testUpdateAnnotations() throws Exception {
        when(transcriptProfileLoaderMock.load(EXPERIMENT_ACCESSION)).thenReturn(0);

        String s = subject.updateAnnotations(EXPERIMENT_ACCESSION);
        verify(transcriptProfileLoaderMock).load(EXPERIMENT_ACCESSION);
        assertThat(s, containsString("Inserted 0 transcript profiles and took "));
    }
}