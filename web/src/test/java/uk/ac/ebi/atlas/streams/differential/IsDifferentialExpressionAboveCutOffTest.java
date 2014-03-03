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

package uk.ac.ebi.atlas.streams.differential;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.Regulation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class IsDifferentialExpressionAboveCutOffTest {

    IsDifferentialExpressionAboveCutOff subject;

    @Mock
    Contrast contrastMock;

    DifferentialExpression expression1 = new DifferentialExpression(0.05, 40.0, contrastMock);

    DifferentialExpression expression2 = new DifferentialExpression(0.00005, -40.0, contrastMock);

    @Before
    public void setUp() throws Exception {
        subject = new IsDifferentialExpressionAboveCutOff();
        subject.setPValueCutoff(0.1);
        subject.setRegulation(Regulation.UP_DOWN);
    }

    @Test
    public void testApply() throws Exception {
        assertThat(subject.apply(expression1), is(true));
        assertThat(subject.apply(expression2), is(true));
    }

    @Test
    public void setPValueCutoff() throws Exception {
        subject.setPValueCutoff(0.0001);

        assertThat(subject.apply(expression1), is(false));
        assertThat(subject.apply(expression2), is(true));
    }

    @Test
    public void setFoldChangeCutoff() throws Exception {
        subject.setFoldChangeCutOff(50);

        assertThat(subject.apply(expression1), is(false));
        assertThat(subject.apply(expression2), is(false));
    }

    @Test
    public void testSetRegulation() throws Exception {
        subject.setRegulation(Regulation.DOWN);

        assertThat(subject.apply(expression1), is(false));
        assertThat(subject.apply(expression2), is(true));

        subject.setRegulation(Regulation.UP);

        assertThat(subject.apply(expression1), is(true));
        assertThat(subject.apply(expression2), is(false));
    }
}