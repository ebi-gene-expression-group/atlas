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

package uk.ac.ebi.atlas.model.baseline;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TranscriptProfilesTest {

    private static final String JSON_STRING =
            "{\"transcriptProfiles\":" +
                "[" +
                    "{\"transcriptId\":\"A_TRANSCRIPT_ID_1\",\"expressions\":[2.0,3.0]}," +
                    "{\"transcriptId\":\"A_TRANSCRIPT_ID_2\",\"expressions\":[1.0,0.0]}" +
                "]" +
            "}";

    private TranscriptProfiles subject;

    @Before
    public void setUp() throws Exception {
        TranscriptProfile transcriptProfile1 = new TranscriptProfile("A_TRANSCRIPT_ID_1", Lists.newArrayList(2D, 3D));
        TranscriptProfile transcriptProfile2 = new TranscriptProfile("A_TRANSCRIPT_ID_2", Lists.newArrayList(1D, 0D));

        subject = new TranscriptProfiles(Lists.newArrayList(transcriptProfile1, transcriptProfile2));
    }

    @Test
    public void toJsonShouldReturnJsonRepresentation() throws Exception {
        assertThat(subject.toJson(), is(JSON_STRING));
    }

    @Test
    public void fromJsonShouldDeserializeTranscriptProfiles() throws Exception {
        assertThat(subject.fromJson(JSON_STRING), is(subject));
    }
}
