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

import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.transcript.TranscriptContributionCalculator;
import uk.ac.ebi.atlas.transcript.TranscriptsContribution;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RankedGeneTranscriptsControllerTest {

    public static final String EXPERIMENT_ACCESSION = "experimentAccession";
    public static final String GENE_ID = "geneId";
    public static final String FACTOR_TYPE = "factorType";
    public static final String FACTOR_VALUE = "factorValue";
    public static final String TRANSCRIPT_1 = "transcript1";
    public static final String TRANSCRIPT_2 = "transcript2";
    public static final String TRANSCRIPT_3 = "transcript3";

    @Mock
    private HttpServletRequest requestMock;

    @Mock
    private TranscriptContributionCalculator transcriptContributionCalculatorMock;

    @Mock
    private TranscriptsContribution transcriptsContributionMock;

    private RankedGeneTranscriptsController subject;

    @Before
    public void setUp() throws Exception {
        subject = new RankedGeneTranscriptsController(transcriptContributionCalculatorMock);
    }

    @Test
    public void testGetRankedTranscripts() throws Exception {
        when(transcriptContributionCalculatorMock.getTranscriptsContribution(GENE_ID, EXPERIMENT_ACCESSION, new Factor(FACTOR_TYPE, FACTOR_VALUE))).thenReturn(transcriptsContributionMock);
        when(transcriptsContributionMock.getTotalTranscriptCount()).thenReturn(7);
        Map<String, Double> map = Maps.newHashMap();
        map.put(TRANSCRIPT_1, 0.5);
        map.put(TRANSCRIPT_2, 0.4);
        map.put(TRANSCRIPT_3, 0.1);
        when(transcriptsContributionMock.getTranscriptPercentageRates()).thenReturn(map);

        String rankedTranscripts = subject.getRankedTranscripts(requestMock, EXPERIMENT_ACCESSION, GENE_ID, FACTOR_TYPE, FACTOR_VALUE, 3);
        assertThat(rankedTranscripts, containsString("7"));
        assertThat(rankedTranscripts, containsString(TRANSCRIPT_1));
        assertThat(rankedTranscripts, containsString(TRANSCRIPT_2));
        assertThat(rankedTranscripts, containsString(TRANSCRIPT_3));
    }
}