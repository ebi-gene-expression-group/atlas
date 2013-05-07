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

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TranscriptContributionsTest {

    private TranscriptContributions subject = new TranscriptContributions();

    @Test
    public void testGetTranscriptPercentageRates() throws Exception {
        //given
        subject.put("t1", 3d);
        subject.put("t2", 2d);
        subject.put("t3", 4d);

        //when
        Map<String,Double> expressions = subject.getTranscriptExpressions();

        //then
        assertThat(expressions.get("t1"), is(3d));
        assertThat(expressions.get("t2"), is(2d));
        assertThat(expressions.get("t3"), is(4d));

    }
}
