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

package uk.ac.ebi.atlas.streams.baseline;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.ExperimentRun;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.trader.cache.baseline.BaselineExperimentsCache;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExpressionBufferBuilderTest {

    private static final String MOCK_EXPERIMENT_ACCESSION = "MOCK_EXPERIMENT_ACCESSION";

    private static final String RUN_ACCESSION_1 = "ENS0";
    private static final String RUN_ACCESSION_2 = "ENS1";
    private static final String RUN_ACCESSION_3 = "ENS2";

    private ExperimentRun experimentRun1;
    private ExperimentRun experimentRun2;

    @Mock
    private BaselineExperimentsCache experimentsCacheMock;

    @Mock
    private Factor factorMock1;

    @Mock
    private Factor factorMock2;

    @Mock
    private Factor factorMock3;

    @Mock
    private BaselineExperiment experimentMock;

    @Mock
    ExperimentalFactors experimentalFactorsMock;


    private BaselineExpressionsQueueBuilder subject;



    @Before
    public void initializeSubject() {

        when(factorMock1.getType()).thenReturn("ORGANISM_PART");
        when(factorMock1.getValue()).thenReturn("heart");

        when(factorMock2.getType()).thenReturn("ORGANISM_PART");
        when(factorMock2.getValue()).thenReturn("liver");

        when(factorMock3.getType()).thenReturn("ORGANISM_PART");
        when(factorMock3.getValue()).thenReturn("lung");

        experimentRun1 = new ExperimentRun(RUN_ACCESSION_1).addFactor(factorMock1);
        experimentRun2 = new ExperimentRun(RUN_ACCESSION_2).addFactor(factorMock2);

        when(experimentsCacheMock.getExperiment(MOCK_EXPERIMENT_ACCESSION)).thenReturn(experimentMock);
        when(experimentMock.getExperimentalFactors()).thenReturn(experimentalFactorsMock);

        subject = new BaselineExpressionsQueueBuilder(experimentsCacheMock);
    }

    @Test(expected = IllegalStateException.class)
    public void createThrowsExceptionGivenThatExperimentAccessionHasNotBeenProvided() {
        //when
        subject.build();
    }

    @Test(expected = IllegalStateException.class)
    public void createThrowsExceptionGivenThatOrderedHeadersHaveNotBeenProvided() {
        //when
        subject.build();
    }

    @Test
    public void createShouldSucceedWhenSpecificationHasBeenSet() {
        //when
        subject.forExperiment(MOCK_EXPERIMENT_ACCESSION);
        //then
        assertThat(subject.build(), is(notNullValue()));
    }

    @Test
    public void experimentRunIsRequiredWhenItsAccessionIsIncludedInTheProvidedOrderedRunAccessions() {
        //given
        List<String> orderSpecification = Lists.newArrayList(RUN_ACCESSION_2, RUN_ACCESSION_3);
        //when
        boolean isRequired = subject.isExperimentRunRequired(orderSpecification).apply(experimentRun2);
        //then
        assertThat(isRequired, is(true));
    }

    @Test
    public void experimentRunIsNotRequiredWhenItsAccessionIsNotIncludedInTheProvidedOrderedRunAccessions() {
        //given
        List<String> orderSpecification = Lists.newArrayList(RUN_ACCESSION_2, RUN_ACCESSION_3);
        //when
        boolean isRequired = subject.isExperimentRunRequired(orderSpecification).apply(experimentRun1);
        //then
        assertThat(isRequired, is(false));
    }

}
