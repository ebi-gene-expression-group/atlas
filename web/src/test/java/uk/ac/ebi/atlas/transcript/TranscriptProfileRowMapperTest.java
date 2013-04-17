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
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfile;

import java.sql.Array;
import java.sql.ResultSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TranscriptProfileRowMapperTest {

    public static final String TRANSCRIPT_ID = "transcript_id";
    private TranscriptProfileRowMapper subject;

    @Mock
    private ResultSet resultSetMock;

    @Mock
    private Array arrayMock;

    @Before
    public void setUp() throws Exception {
        subject = new TranscriptProfileRowMapper();

        Object[] array = new Object[]{new Double(1), new Double(2)};

        when(resultSetMock.getString(TRANSCRIPT_ID)).thenReturn(TRANSCRIPT_ID);
        when(resultSetMock.getArray("transcript_expressions")).thenReturn(arrayMock);
        when(arrayMock.getArray()).thenReturn(array);

    }

    @Test
    public void testMapRow() throws Exception {
        TranscriptProfile transcriptProfile = subject.mapRow(resultSetMock, 0);
        assertThat(transcriptProfile.getTranscriptId(), is(TRANSCRIPT_ID));
        assertThat(transcriptProfile.getExpressions(), contains(1D, 2D));
    }
}