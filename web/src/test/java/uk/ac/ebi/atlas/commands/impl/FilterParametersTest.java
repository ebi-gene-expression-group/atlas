/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.commands.impl;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commands.RequestContextBuilder;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentalFactors;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.web.FactorsConverter;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FilterParametersTest {

    public static final String ORGANISM_PART = "ORGANISM_PART";
    public static final String CELL_LINE = "CELL_LINE";

    private FilterParameters subject;

    @Mock
    ExperimentsCache experimentCacheMock;

    @Mock
    Experiment experimentMock;

    @Mock
    ExperimentalFactors experimentalFactorsMock;

    @Mock
    SolrClient solrClientMock;

    @Before
    public void initSubject() {

        when(experimentalFactorsMock.getFactorName(anyString())).thenReturn("X");
        when(experimentMock.getExperimentalFactors()).thenReturn(experimentalFactorsMock);

        when(experimentMock.getFirstSpecies()).thenReturn("Homo sapiens");

        FactorsConverter factorsConverter = mock(FactorsConverter.class);

        RequestContextBuilder builder = new RequestContextBuilder(new FilterParameters(), factorsConverter);

        subject = builder.forExperiment(experimentMock)
                .withSerializedFilterFactors("A:B,C:D")
                .build();

    }

}
