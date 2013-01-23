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

package uk.ac.ebi.atlas.model;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfileForMultipleFactorValuesExperimentTest {

    private GeneProfile subject;

    @Mock
    private Expression multipleFactorValuesExpression1Mock;

    @Mock
    private Expression multipleFactorValuesExpression2Mock;

    @Mock
    private Expression multipleFactorValuesExpression3Mock;

    private FactorValue factorValue1 = new FactorValue("TYPE1", "VALUE1");
    private FactorValue factorValue2 = new FactorValue("TYPE2", "VALUE2");

    private FactorValue factorValue3 = new FactorValue("TYPE3", "VALUE3");
    private FactorValue factorValue4 = new FactorValue("TYPE4", "VALUE4");

    @Before
    public void initSubject(){
        GeneProfile.Builder geneProfileBuilder = new GeneProfile.Builder();

        when(multipleFactorValuesExpression1Mock.getAllFactorValues()).thenReturn(Sets.newHashSet(factorValue1, factorValue2));
        when(multipleFactorValuesExpression1Mock.getLevel()).thenReturn(1d);
        when(multipleFactorValuesExpression1Mock.isGreaterThan(anyDouble())).thenReturn(true);

        when(multipleFactorValuesExpression2Mock.getAllFactorValues()).thenReturn(Sets.newHashSet(factorValue3, factorValue4));
        when(multipleFactorValuesExpression2Mock.getLevel()).thenReturn(2d);
        when(multipleFactorValuesExpression2Mock.isGreaterThan(anyDouble())).thenReturn(true);

        when(multipleFactorValuesExpression3Mock.getAllFactorValues()).thenReturn(Sets.newHashSet(factorValue2, factorValue3));
        when(multipleFactorValuesExpression3Mock.getLevel()).thenReturn(3d);
        when(multipleFactorValuesExpression3Mock.isGreaterThan(anyDouble())).thenReturn(true);

        subject = geneProfileBuilder
                        .forGeneId("Gene_one")
                        .addExpression(multipleFactorValuesExpression1Mock)
                        .addExpression(multipleFactorValuesExpression2Mock)
                        .addExpression(multipleFactorValuesExpression3Mock)
                        .create();
    }

    @Test
    public void getExpressionLevelShouldSucceed(){
        //assertThat(subject.getExpressionLevel(factorValue1),is(1d));
        //assertThat(subject.getExpressionLevel(factorValue2),is(1d));
    }

}
