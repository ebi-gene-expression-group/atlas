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
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
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
    private static final String SERIALIZED_FILTER_FACTORS = "";

    @Mock
    private TranscriptContributionsCalculator transcriptContributionsCalculatorMock;


    private RankedGeneTranscriptsController subject;

    private FactorSet factorSet = new FactorSet();

    @Before
    public void setUp() throws Exception {
        factorSet.add(new Factor(FACTOR_TYPE, FACTOR_VALUE));

        FilterFactorsConverter filterFactorsConverter = new FilterFactorsConverter();
        subject = new RankedGeneTranscriptsController(transcriptContributionsCalculatorMock, filterFactorsConverter);
    }

    @Test
    public void testGetRankedTranscripts() throws Exception {
        TranscriptContributions transcriptContributions = new TranscriptContributions();
        transcriptContributions.setTotalTranscriptsCount(7);
        transcriptContributions.put(TRANSCRIPT_1, 0.5);
        transcriptContributions.put(TRANSCRIPT_2, 0.4);
        transcriptContributions.put(TRANSCRIPT_3, 0.1);

        when(transcriptContributionsCalculatorMock.getTranscriptsContribution(GENE_ID, EXPERIMENT_ACCESSION, factorSet)).thenReturn(transcriptContributions);

        String rankedTranscripts = subject.getRankedTranscripts(EXPERIMENT_ACCESSION, GENE_ID, FACTOR_TYPE, FACTOR_VALUE, SERIALIZED_FILTER_FACTORS);
        assertThat(rankedTranscripts, containsString("7"));
        assertThat(rankedTranscripts, containsString(TRANSCRIPT_1));
        assertThat(rankedTranscripts, containsString(TRANSCRIPT_2));
        assertThat(rankedTranscripts, containsString(TRANSCRIPT_3));
    }

    @Test
    public void extractFilterFactors() {
        String serializedFilterFactors = "RNA:long polyA RNA,CELLULAR_COMPONENT:whole cell";
        Collection<Factor> factors = subject.extractFilterFactors(serializedFilterFactors);

        Factor factor1 = new Factor("RNA", "long polyA RNA");
        Factor factor2 = new Factor("CELLULAR_COMPONENT", "whole cell");
        assertThat(factors, contains(factor1, factor2));
    }

}