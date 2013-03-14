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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commands.RequestContext;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;

import java.util.Collections;
import java.util.TreeSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfileBuilderTest {

    private static final String QUERY_FACTOR_TYPE = "ORGANISM_PART";
    private BaselineProfile.Builder subject;

    @Mock
    private BaselineProfilePrecondition baselineProfilePreconditionMock;
    @Mock
    private BaselineExpressionPrecondition baselineExpressionPreconditionMock;

    @Mock
    private GeneNamesProvider geneNamesProviderMock;

    @Mock
    private RequestContext requestContextMock;

    @Mock
    private BaselineExpression expressionMock1;
    @Mock
    private BaselineExpression expressionMock2;



    @Before
    public void initSubject() {
        subject = new BaselineProfile.Builder(requestContextMock, baselineExpressionPreconditionMock, baselineProfilePreconditionMock);

        when(requestContextMock.getCutoff()).thenReturn(0d);
        when(requestContextMock.isSpecific()).thenReturn(true);
        when(requestContextMock.getQueryFactorType()).thenReturn(QUERY_FACTOR_TYPE);
        when(requestContextMock.getAllQueryFactors()).thenReturn(new TreeSet<Factor>());
        when(requestContextMock.getFilteredBySpecies()).thenReturn("homo");
        when(requestContextMock.getSelectedFilterFactors()).thenReturn(Collections.EMPTY_SET);

        when(baselineExpressionPreconditionMock.setCutoff(anyDouble())).thenReturn(baselineExpressionPreconditionMock);
        when(baselineExpressionPreconditionMock.setFilterFactors(anySet())).thenReturn(baselineExpressionPreconditionMock);
        when(baselineProfilePreconditionMock.setAllQueryFactors(anySet())).thenReturn(baselineProfilePreconditionMock);
        when(baselineProfilePreconditionMock.setSelectedQueryFactors(anySet())).thenReturn(baselineProfilePreconditionMock);
        when(baselineProfilePreconditionMock.setSpecific(anyBoolean())).thenReturn(baselineProfilePreconditionMock);
    }

    @Test(expected = IllegalStateException.class)
    public void createShouldFailWhenSetGeneIdIsNotInvoked() {
        subject.addExpression(expressionMock1).create();
    }

    @Test(expected = IllegalStateException.class)
    public void addExpressionShouldFailBeforeSetGeneIdIsNotInvoked() {
        subject.addExpression(expressionMock1);
    }

    @Test
    public void createShouldReturnNullIfNoneOfTheExpressionsSatisfyPreconditionsWithoutSelectedQueryFactors() {
        //when
        subject.forGeneId("Gene1")
                .addExpression(expressionMock1)
                .addExpression(expressionMock2);

        //then
        assertThat(subject.create(), is(nullValue()));

    }

    @Test
    public void createShouldReturnGeneProfileIfAtLeastOneExpressionSatisfiesPreconditionsWithoutSelectedQueryFactors() {
        //given
        given(baselineExpressionPreconditionMock.apply(expressionMock1)).willReturn(true);
        given(baselineProfilePreconditionMock.apply(Matchers.any(BaselineProfile.class))).willReturn(true);
        //and
        given(expressionMock1.isGreaterThan(0d)).willReturn(true);
        given(expressionMock2.isGreaterThan(0d)).willReturn(true);
        given(expressionMock1.getFactor(QUERY_FACTOR_TYPE)).willReturn(mock(Factor.class));

        subject.forGeneId("Gene1")
                .addExpression(expressionMock1)
                .addExpression(expressionMock2);

        //then
        assertThat(subject.create().getGeneId(), is("Gene1"));

    }

    @Test
    public void createShouldReturnGeneProfileIfOnlyOneExpressionPresentForPreconditionWithSelectedQueryFactors() {
        //given
        Factor selectedFactorMock = new Factor(QUERY_FACTOR_TYPE, "value1");

        given(baselineExpressionPreconditionMock.apply(expressionMock1)).willReturn(true);
        given(baselineExpressionPreconditionMock.apply(expressionMock2)).willReturn(false);

        given(baselineProfilePreconditionMock.apply(Matchers.any(BaselineProfile.class))).willReturn(true);

        //and
        given(expressionMock1.isGreaterThan(0d)).willReturn(true);
        given(expressionMock1.getLevel()).willReturn(5d);
        given(expressionMock1.getFactor(QUERY_FACTOR_TYPE)).willReturn(selectedFactorMock);

        subject.forGeneId("Gene1")
                .addExpression(expressionMock1)
                .addExpression(expressionMock2);

        //then
        assertThat(subject.create().getGeneId(), is("Gene1"));

    }

    @Test
    public void createShouldReturnGeneProfileIfAtLeastOneExpressionSatisfiesPreconditionWithSelectedQueryFactors() {
        //given
        Factor selectedFactorMock = new Factor(QUERY_FACTOR_TYPE, "value1");
        Factor otherFactorMock = new Factor(QUERY_FACTOR_TYPE, "value2");

        given(baselineExpressionPreconditionMock.apply(expressionMock1)).willReturn(true);
        given(baselineExpressionPreconditionMock.apply(expressionMock2)).willReturn(true);
        given(baselineProfilePreconditionMock.apply(Matchers.any(BaselineProfile.class))).willReturn(true);
        //and
        given(expressionMock1.isGreaterThan(0d)).willReturn(true);
        given(expressionMock1.getLevel()).willReturn(5d);
        given(expressionMock2.isGreaterThan(0d)).willReturn(true);
        given(expressionMock2.getLevel()).willReturn(3d);
        given(expressionMock1.getFactor(QUERY_FACTOR_TYPE)).willReturn(selectedFactorMock);
        given(expressionMock2.getFactor(QUERY_FACTOR_TYPE)).willReturn(otherFactorMock);

        subject.forGeneId("Gene1")
                .addExpression(expressionMock1)
                .addExpression(expressionMock2);

        //then
        assertThat(subject.create().getGeneId(), is("Gene1"));

    }

}