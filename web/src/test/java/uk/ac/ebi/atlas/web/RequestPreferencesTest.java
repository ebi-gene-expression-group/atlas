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

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.FactorValue;
import uk.ac.ebi.atlas.utils.NumberUtils;

import java.util.TreeSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class RequestPreferencesTest {

    private RequestPreferences subject;

    @Mock
    private NumberUtils numberUtilsMock;

    @Before
    public void setUp() throws Exception {
        subject = new RequestPreferences();
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

    @Test
    public void getFilterFactorValuesAsObjects() {
        TreeSet<String> filterFactorValuesStrings = Sets.newTreeSet();
        filterFactorValuesStrings.add("MATERIAL_TYPE:total rna");
        filterFactorValuesStrings.add("CELLULAR_COMPONENT:whole cell");

        // given
        subject.setFilterFactorValues(filterFactorValuesStrings);
        //then
        assertThat(subject.getFilterFactorValues().size(), is(2));
        //and
        assertThat(subject.getFilterFactorValues(), hasItems("MATERIAL_TYPE:total rna", "CELLULAR_COMPONENT:whole cell"));
        //and
        assertThat(subject.getFilterFactorValuesAsObjects().size(), is(2));
        //and
        assertThat(subject.getFilterFactorValuesAsObjects(), hasItems(
                new FactorValue("CELLULAR_COMPONENT", "", "whole cell"), new FactorValue("MATERIAL_TYPE", "", "total rna")));
    }
}
