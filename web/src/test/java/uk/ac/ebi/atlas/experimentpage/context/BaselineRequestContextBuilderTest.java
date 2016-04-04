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

package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Matchers.anySet;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineRequestContextBuilderTest {

    public static final String QUERY_FACTOR1 = "a";
    public static final String QUERY_FACTOR2 = "b";
    public static final String QUERY_FACTOR3 = "c";
    public static final String FACTOR_TYPE = "organism";
    public static final String FACTOR_VALUE = "homo sapiens";
    public static final String SERIALIZED_FACTORS = "serialized";

    BaselineRequestContextBuilder subject;

    @Mock
    FilterFactorsConverter filterFactorsConverterMock;

    @Mock
    BaselineExperiment experimentMock;

    @Mock
    BaselineRequestPreferences preferencesMock;

    @Mock
    Factor factorMock;

    @Mock
    ExperimentalFactors experimentalFactorsMock;

    @Before
    public void setUp() throws Exception {
        subject = new BaselineRequestContextBuilder(filterFactorsConverterMock);

        when(factorMock.getType()).thenReturn(FACTOR_TYPE);
        when(factorMock.getValue()).thenReturn(FACTOR_VALUE);
        when(filterFactorsConverterMock.deserialize(SERIALIZED_FACTORS)).thenReturn(Sets.newHashSet(factorMock));
        when(preferencesMock.getSerializedFilterFactors()).thenReturn(SERIALIZED_FACTORS);
        when(preferencesMock.getQueryFactorValues()).thenReturn(Sets.newTreeSet(Sets.newHashSet(QUERY_FACTOR1, QUERY_FACTOR2, QUERY_FACTOR3)));
        when(preferencesMock.getQueryFactorType()).thenReturn(FACTOR_TYPE);
        when(experimentMock.getExperimentalFactors()).thenReturn(experimentalFactorsMock);
        when(experimentalFactorsMock.getComplementFactors(anySet())).thenReturn(Sets.newTreeSet(Sets.newHashSet(new Factor(FACTOR_TYPE, FACTOR_VALUE))));
    }

    @Test
    public void testBuild() throws Exception {
        BaselineRequestContext context = subject.forExperiment(experimentMock).withPreferences(preferencesMock).build();
        assertThat(context.getSelectedFilterFactors(), hasItem(factorMock));
        assertThat(context.getFilteredBySpecies(), is(FACTOR_VALUE));
        Factor factor1 = new Factor(FACTOR_TYPE, QUERY_FACTOR1);
        Factor factor2 = new Factor(FACTOR_TYPE, QUERY_FACTOR2);
        Factor factor3 = new Factor(FACTOR_TYPE, QUERY_FACTOR3);
        assertThat(context.getSelectedQueryFactors(), hasItems(factor1, factor2, factor3));
        assertThat(context.getAllQueryFactors(), hasItem(new Factor(FACTOR_TYPE, FACTOR_VALUE)));
    }
}