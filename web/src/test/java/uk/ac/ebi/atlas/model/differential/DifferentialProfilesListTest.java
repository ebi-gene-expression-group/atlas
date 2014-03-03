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

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialProfilesListTest {

    private DifferentialProfilesList subject;

    @Mock
    private DifferentialProfile differentialProfileMock1;
    @Mock
    private DifferentialProfile differentialProfileMock2;
    @Mock
    private DifferentialProfile differentialProfileMock3;

    @Before
    public void initSubject() throws Exception {

        Collection<DifferentialProfile> differentialProfiles = Lists.newArrayList(differentialProfileMock1, differentialProfileMock2, differentialProfileMock3);

        subject = new DifferentialProfilesList(differentialProfiles);
    }

    @Test
    public void maxUpRegulatedExpressionLevelShouldBeZeroWhenAllProfilesHaveNoUpRegulatedExpressionLevel() throws Exception {
        //given
        given(differentialProfileMock1.getMaxUpRegulatedExpressionLevel()).willReturn(0D);
        given(differentialProfileMock2.getMaxUpRegulatedExpressionLevel()).willReturn(0D);
        given(differentialProfileMock3.getMaxUpRegulatedExpressionLevel()).willReturn(0D);

        //
        assertThat(subject.getMaxUpRegulatedExpressionLevel(), is(0D));
    }

    @Test
    public void minUpRegulatedExpressionLevelShouldBeNaNWhenAllProfilesHaveNoUpRegulatedExpressionLevel() throws Exception {
        //given
        given(differentialProfileMock1.getMinUpRegulatedExpressionLevel()).willReturn(Double.MAX_VALUE);
        given(differentialProfileMock2.getMinUpRegulatedExpressionLevel()).willReturn(Double.MAX_VALUE);
        given(differentialProfileMock3.getMinUpRegulatedExpressionLevel()).willReturn(Double.MAX_VALUE);

        //
        assertThat(subject.getMinUpRegulatedExpressionLevel(), is(Double.NaN));
    }

    @Test
    public void maxUpRegulatedExpressionLevelShouldReturnTheMaxUpRegulatedExpressionLevelAcrossAllProfiiles() throws Exception {
        //given
        given(differentialProfileMock1.getMaxUpRegulatedExpressionLevel()).willReturn(0D);
        given(differentialProfileMock2.getMaxUpRegulatedExpressionLevel()).willReturn(23.3D);
        given(differentialProfileMock3.getMaxUpRegulatedExpressionLevel()).willReturn(0.22D);

        //
        assertThat(subject.getMaxUpRegulatedExpressionLevel(), is(23.3D));
    }

    @Test
    public void minUpRegulatedExpressionLevelShouldReturnTheMinUpRegulatedExpressionLevelAcrossAllProfiiles() throws Exception {
        //given
        given(differentialProfileMock1.getMinUpRegulatedExpressionLevel()).willReturn(23D);
        given(differentialProfileMock2.getMinUpRegulatedExpressionLevel()).willReturn(0.001D);
        given(differentialProfileMock3.getMinUpRegulatedExpressionLevel()).willReturn(3D);

        //
        assertThat(subject.getMinUpRegulatedExpressionLevel(), is(0.001D));
    }

    @Test
    public void maxDownRegulatedExpressionLevelShouldBeZeroWhenAllProfilesHaveNoDownRegulatedExpressionLevel() throws Exception {
        //given
        given(differentialProfileMock1.getMaxDownRegulatedExpressionLevel()).willReturn(0D);
        given(differentialProfileMock2.getMaxDownRegulatedExpressionLevel()).willReturn(0D);
        given(differentialProfileMock3.getMaxDownRegulatedExpressionLevel()).willReturn(0D);

        //
        assertThat(subject.getMaxDownRegulatedExpressionLevel(), is(0D));
    }

    @Test
    public void minDownRegulatedExpressionLevelShouldBeNaNWhenAllProfilesHaveNoDownRegulatedExpressionLevel() throws Exception {
        //given
        given(differentialProfileMock1.getMinDownRegulatedExpressionLevel()).willReturn(Double.MAX_VALUE);
        given(differentialProfileMock2.getMinDownRegulatedExpressionLevel()).willReturn(Double.MAX_VALUE);
        given(differentialProfileMock3.getMinDownRegulatedExpressionLevel()).willReturn(Double.MAX_VALUE);

        //
        assertThat(subject.getMinDownRegulatedExpressionLevel(), is(Double.NaN));
    }

    @Test
    public void maxDownRegulatedExpressionLevelShouldReturnTheMaxDownRegulatedExpressionLevelAcrossAllProfiiles() throws Exception {
        //given
        given(differentialProfileMock1.getMaxDownRegulatedExpressionLevel()).willReturn(0D);
        given(differentialProfileMock2.getMaxDownRegulatedExpressionLevel()).willReturn(-23.3D);
        given(differentialProfileMock3.getMaxDownRegulatedExpressionLevel()).willReturn(-0.22D);

        //
        assertThat(subject.getMaxDownRegulatedExpressionLevel(), is(-23.3D));
    }

    @Test
    public void minDownRegulatedExpressionLevelShouldReturnTheMinDownRegulatedExpressionLevelAcrossAllProfiiles() throws Exception {
        //given
        given(differentialProfileMock1.getMinDownRegulatedExpressionLevel()).willReturn(-23D);
        given(differentialProfileMock2.getMinDownRegulatedExpressionLevel()).willReturn(-0.001D);
        given(differentialProfileMock3.getMinDownRegulatedExpressionLevel()).willReturn(-3D);

        //
        assertThat(subject.getMinDownRegulatedExpressionLevel(), is(-0.001D));
    }

}
