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

package uk.ac.ebi.atlas.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.utils.NumberUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestPreferencesTest {

    private RequestPreferences subject;

    @Mock
    private NumberUtils numberUtilsMock;

    @Mock
    private ApplicationProperties properties;

    @Before
    public void setUp() throws Exception {
        when(properties.getDefaultCutoff()).thenReturn(0.5);
        subject = new RequestPreferences();
        subject.setApplicationProperties(properties);
    }

    @Test
    public void cutoffShouldBeRoundedToNoFractionalDigitForValuesLargerThanOne() {
        //given
        subject.setCutoff(2.1211);
        //then
        assertThat(subject.getCutoff(), is(2d));
    }

    @Test
    public void cutoffShouldBeRoundedTo1FractionalDigitForValuesSmallerThanOne() {
        //given
        subject.setCutoff(0.1211);
        //then
        assertThat(subject.getCutoff(), is(0.1d));
    }

    @Test
    public void heatmapMatrixSizeIsSetToTheDefaultRankingSizeIfRequestDoesntSpecifyAnyValue() {
        //given
        subject.setHeatmapMatrixSize(null);
        //then
        assertThat(subject.getHeatmapMatrixSize(), is(RequestPreferences.DEFAULT_NUMBER_OF_RANKED_GENES));
        //and given
        subject.setHeatmapMatrixSize(33);
        //then
        assertThat(subject.getHeatmapMatrixSize(), is(33));


    }
}
