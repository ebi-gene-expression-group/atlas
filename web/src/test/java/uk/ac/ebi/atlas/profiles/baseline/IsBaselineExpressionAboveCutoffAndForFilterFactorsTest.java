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

package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.Factor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class IsBaselineExpressionAboveCutoffAndForFilterFactorsTest {

    private IsBaselineExpressionAboveCutoffAndForFilterFactors subject;

    private Factor factor1 = new Factor("type1", "value1");
    private Factor factor2 = new Factor("type2", "value2");

    @Mock
    private BaselineExpression expressionMock;

    @Before
    public void init(){
    }

    @Test
    public void checkLimitingFactorsShouldSucceedWhenExpressionContainsAllLimitingFactors() throws Exception {

        //given
        given(expressionMock.containsAll(Sets.newHashSet(factor1,factor2))).willReturn(true);

        //when
        subject = new IsBaselineExpressionAboveCutoffAndForFilterFactors();
        subject.setFilterFactors(Sets.newHashSet(factor1, factor2));

        //then
        assertThat(subject.checkFilterFactors(expressionMock), is(true));
    }

    @Test
    public void checkLimitingFactorsShouldSucceedWhenNoLimitingFactorSetIsProvided() throws Exception {

        //given
        subject = new IsBaselineExpressionAboveCutoffAndForFilterFactors();

        //then
        assertThat(subject.checkFilterFactors(expressionMock), is(true));
    }

    @Test
    public void applyShouldFailExpressionDoesntContainAllLimitingFactors() throws Exception {

        //given
        subject = new IsBaselineExpressionAboveCutoffAndForFilterFactors();
        subject.setFilterFactors(Sets.newHashSet(factor1, factor2));
        given(expressionMock.isKnown()).willReturn(true);
        given(expressionMock.containsAll(Sets.newHashSet(factor1,factor2))).willReturn(false);
        //then
        assertThat(subject.apply(expressionMock), is(false));
    }

    @Test
    public void applyShouldFailUnknownExpressionDoesntContainAllLimitingFactors() throws Exception {

        //given
        subject = new IsBaselineExpressionAboveCutoffAndForFilterFactors();
        subject.setFilterFactors(Sets.newHashSet(factor1, factor2));
        given(expressionMock.isKnown()).willReturn(false);
        given(expressionMock.containsAll(Sets.newHashSet(factor1,factor2))).willReturn(false);
        //then
        assertThat(subject.apply(expressionMock), is(false));
    }


    @Test
    public void applyShouldSucceedIfLevelIsGreaterThanCutoff() throws Exception {

        //given
        subject = new IsBaselineExpressionAboveCutoffAndForFilterFactors();
        subject.setFilterFactors(Sets.newHashSet(factor1, factor2));
        subject.setCutoff(1d);

        given(expressionMock.containsAll(Sets.newHashSet(factor1,factor2))).willReturn(true);
        given(expressionMock.isGreaterThan(1d)).willReturn(true);

        //then
        assertThat(subject.apply(expressionMock), is(true));
    }

}
