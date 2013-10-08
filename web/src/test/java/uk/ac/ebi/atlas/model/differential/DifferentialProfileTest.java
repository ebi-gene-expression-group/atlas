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

import com.google.common.collect.Sets;
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
    private static final String GENE_NAME = "A_GENE_NAME";

    @Mock
    private DifferentialExpression differentialExpressionMock1;

    @Mock
    private DifferentialExpression differentialExpressionMock2;

    private DifferentialProfile subject;

    @Before
    public void setUp() throws Exception {
        this.subject = new DifferentialProfile(GENE_ID, GENE_NAME);
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
    public void addingAnOverExpressedExpressionShouldUpdateMinAndMaxUpRegulatedLevelsAndSpecificity() throws Exception {
        //given
        given(differentialExpressionMock1.isOverExpressed()).willReturn(true);
        given(differentialExpressionMock1.getLevel()).willReturn(0.4D);

        //when
        subject.add(differentialExpressionMock1);

        //then
        assertThat(subject.getMaxUpRegulatedExpressionLevel(), is(0.4D));
        assertThat(subject.getMinUpRegulatedExpressionLevel(), is(0.4D));
        assertThat(subject.getMaxDownRegulatedExpressionLevel(), is(0D));
        assertThat(subject.getMinDownRegulatedExpressionLevel(), is(Double.MAX_VALUE));
        //and
        assertThat(subject.getMinExpressionLevel(), is(0.4D));
        //and
        assertThat(subject.getSpecificity(Regulation.UP), is(1));
        assertThat(subject.getSpecificity(Regulation.UP_DOWN), is(1));
        assertThat(subject.getSpecificity(Regulation.DOWN), is(0));
    }

    @Test
    public void addingAnUnderExpressedExpressionShouldUpdateMinAndMaxDownRegulatedLevelsAndSpecificity() throws Exception {
        //given
        given(differentialExpressionMock1.isUnderExpressed()).willReturn(true);
        given(differentialExpressionMock1.getLevel()).willReturn(0.3D);

        //when
        subject.add(differentialExpressionMock1);

        //then
        assertThat(subject.getMaxUpRegulatedExpressionLevel(), is(0D));
        assertThat(subject.getMinUpRegulatedExpressionLevel(), is(Double.MAX_VALUE));
        assertThat(subject.getMaxDownRegulatedExpressionLevel(), is(0.3D));
        assertThat(subject.getMinDownRegulatedExpressionLevel(), is(0.3D));
        //and
        assertThat(subject.getMinExpressionLevel(), is(0.3D));
        //and
        assertThat(subject.getSpecificity(Regulation.DOWN), is(1));
        assertThat(subject.getSpecificity(Regulation.UP_DOWN), is(1));
        assertThat(subject.getSpecificity(Regulation.UP), is(0));
    }

    @Test
    public void getAverageExpressionLevelOnShouldReturnAverageValueOfOneExpression() throws Exception {
        //given
        given(differentialExpressionMock1.isRegulatedLike(Regulation.DOWN)).willReturn(true);
        given(differentialExpressionMock1.getLevel()).willReturn(0.3D);
        Contrast contrastMock1 = mock(Contrast.class);
        given(differentialExpressionMock1.getContrast()).willReturn(contrastMock1);

        given(differentialExpressionMock2.isRegulatedLike(Regulation.DOWN)).willReturn(false);
        Contrast contrastMock2 = mock(Contrast.class);
        given(differentialExpressionMock2.getContrast()).willReturn(contrastMock2);

        //when
        subject.add(differentialExpressionMock1);
        subject.add(differentialExpressionMock2);

        //then
        double averageExpressionLevelOn = subject.getAverageExpressionLevelOn(Sets.newHashSet(contrastMock1, contrastMock2), Regulation.DOWN);
        assertThat(averageExpressionLevelOn, is(0.15));
    }

    @Test
    public void getAverageExpressionLevelOnShouldReturnAverageValueOfBoth() throws Exception {
        //given
        given(differentialExpressionMock1.isRegulatedLike(Regulation.DOWN)).willReturn(true);
        given(differentialExpressionMock1.getLevel()).willReturn(0.3D);
        Contrast contrastMock1 = mock(Contrast.class);
        given(differentialExpressionMock1.getContrast()).willReturn(contrastMock1);

        given(differentialExpressionMock2.isRegulatedLike(Regulation.DOWN)).willReturn(true);
        Contrast contrastMock2 = mock(Contrast.class);
        given(differentialExpressionMock2.getLevel()).willReturn(0.5D);
        given(differentialExpressionMock2.getContrast()).willReturn(contrastMock2);

        //when
        subject.add(differentialExpressionMock1);
        subject.add(differentialExpressionMock2);

        //then
        double averageExpressionLevelOn = subject.getAverageExpressionLevelOn(Sets.newHashSet(contrastMock1, contrastMock2), Regulation.DOWN);
        assertThat(averageExpressionLevelOn, is(0.4D));
    }

}
