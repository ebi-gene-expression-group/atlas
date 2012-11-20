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

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.utils.NumberUtils;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
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
    public void testGetGeneIDs() throws Exception {

        //given
        subject.setGeneIDsString("g1, g2   g3 , g4 ,, g5 , , g6");

        //then
        assertThat(subject.getGeneIDs().size(), is(6));

        //and
        assertThat(subject.getGeneIDs(), hasItems("g1", "g2", "g3", "g4", "g5", "g6"));

    }

    @Test
    public void testGetQueryValuesIDs() throws Exception {

        //given
        subject.setGeneIDsString("\"g1 g2\"   g3 , g4 ,, g5 , , g6");

        //then
        assertThat(subject.getGeneIDs().size(), is(5));

        //and
        assertThat(subject.getGeneIDs(), hasItems("\"g1 g2\"", "g3", "g4", "g5", "g6"));

    }

    @Test
    public void geneQuerySplit() {
        //"aa 1 bb" cc, dd
        String s = "\"aa 1 bb\" cc, dd";
        Iterable<String> strings = Splitter.on(CharMatcher.anyOf(", ")).omitEmptyStrings().split(s);
        System.out.println("strings = " + strings);
    }

    public void cutoffShouldBeRoundedToNoFractionalDigitForValuesLargerThanOne() {
        //given
        subject.setCutoff(2.1211);
        //then
        assertThat(subject.getCutoff(), is(2d));
    }


    public void cutoffShouldBeRoundedTo1FractionalDigitForValuesSmallerThanOne() {
        //given
        subject.setCutoff(0.1211);
        //then
        assertThat(subject.getCutoff(), is(0.1d));
    }

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
