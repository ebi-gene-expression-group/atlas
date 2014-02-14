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

package uk.ac.ebi.atlas.model.differential.microarray;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.differential.Contrast;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayExpressionTest {

    public static final double P_VALUE = 0.05;
    public static final int FOLD_CHANGE = 14;
    public static final double TSTATISTIC = 0.6;

    public static final double SMALLPVALUE = 1.17501162847487E-242;

    @Mock
    Contrast contrastMock;

    MicroarrayExpression subject;

    @Before
    public void setUp() throws Exception {
        subject = new MicroarrayExpression(P_VALUE, FOLD_CHANGE, TSTATISTIC, contrastMock);
    }

    @Test
    public void testGetTstatistic() throws Exception {
        assertThat(subject.getTstatistic(), is(TSTATISTIC));
    }

    @Test
    public void testSmallPValue() {
        //when
        MicroarrayExpression expression = new MicroarrayExpression(SMALLPVALUE, -1.0, TSTATISTIC, contrastMock);

        //then
        assertThat(expression.getLevel(), is(0D));
    }
}