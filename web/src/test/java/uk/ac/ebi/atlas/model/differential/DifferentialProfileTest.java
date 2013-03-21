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

package uk.ac.ebi.atlas.model.differential;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


@RunWith(MockitoJUnitRunner.class)
public class DifferentialProfileTest {

    private static final String GENE_ID = "A_GENE_ID";

    @Mock
    private DifferentialExpression differentialExpressionMock;

    private DifferentialProfile subject;

    @Before
    public void setUp() throws Exception {
        this.subject = new DifferentialProfile(GENE_ID);
    }

    @Test
    public void testDefaultMinMaxExpressionLevels() throws Exception {
        //given
        assertThat(subject.getMaxUpRegulatedExpressionLevel(), is(0D));
        assertThat(subject.getMinUpRegulatedExpressionLevel(), is(Double.MAX_VALUE));
        assertThat(subject.getMaxDownRegulatedExpressionLevel(), is(0D));
        assertThat(subject.getMinDownRegulatedExpressionLevel(), is(Double.MAX_VALUE));
        //and
        assertThat(subject.getMinExpressionLevel(), is(Double.MAX_VALUE));
    }


    @Test
    public void addingAnOverExpressedExpressionShouldUpdateMinAndMaxUpRegulatedLevels() throws Exception {
        //given
        given(differentialExpressionMock.isOverExpressed()).willReturn(true);
        given(differentialExpressionMock.getLevel()).willReturn(0.4D);

        //when
        subject.add(differentialExpressionMock);

        //then
        assertThat(subject.getMaxUpRegulatedExpressionLevel(), is(0.4D));
        assertThat(subject.getMinUpRegulatedExpressionLevel(), is(0.4D));
        assertThat(subject.getMaxDownRegulatedExpressionLevel(), is(0D));
        assertThat(subject.getMinDownRegulatedExpressionLevel(), is(Double.MAX_VALUE));
        //and
        assertThat(subject.getMinExpressionLevel(), is(0.4D));
    }

    @Test
    public void addingAnUnderExpressedExpressionShouldUpdateMinAndMaxDownRegulatedLevels() throws Exception {
        //given
        given(differentialExpressionMock.isUnderExpressed()).willReturn(true);
        given(differentialExpressionMock.getLevel()).willReturn(0.3D);

        //when
        subject.add(differentialExpressionMock);

        //then
        assertThat(subject.getMaxUpRegulatedExpressionLevel(), is(0D));
        assertThat(subject.getMinUpRegulatedExpressionLevel(), is(Double.MAX_VALUE));
        assertThat(subject.getMaxDownRegulatedExpressionLevel(), is(0.3D));
        assertThat(subject.getMinDownRegulatedExpressionLevel(), is(0.3D));
        //and
        assertThat(subject.getMinExpressionLevel(), is(0.3D));
    }
}
