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
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

public class TranscriptProfileTest {

    public static final String TRANSCRIPT_ID = "A_TRANSCRIPT_ID";
    public static final String GENE_ID = "GENE_ID";
    private TranscriptProfile subject;

    @Before
    public void setUp() throws Exception {
        this.subject = new TranscriptProfile(GENE_ID, TRANSCRIPT_ID, Lists.newArrayList(2D, 3D));
    }

    @Test
    public void testGetGeneId() {
        assertThat(subject.getGeneId(), is(GENE_ID));
    }

    @Test
    public void testGetTranscriptId() {
        assertThat(subject.getTranscriptId(), is(TRANSCRIPT_ID));
    }

    @Test
    public void testGetExpressions() {
        assertThat(subject.getExpressions(), contains(2D, 3D));
    }

    @Test
    public void testGetExpression() {
        assertThat(subject.getExpression(0), is(2D));
    }
}
