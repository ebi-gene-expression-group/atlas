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

package uk.ac.ebi.atlas.streams.baseline;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class BaselineExpressionBufferTest {

    private static final String GENE_ID = "ENST00000000233";

    public static final String EXPRESSION_LEVEL_1 = "0";

    public static final String EXPRESSION_LEVEL_2 = "42.9134";
    public static final String EXPRESSION_LEVEL_3 = "0.0001";
    private static final String[] THREE_EXPRESSION_LEVELS = new String[]{GENE_ID, EXPRESSION_LEVEL_1, EXPRESSION_LEVEL_2, EXPRESSION_LEVEL_3};

    private BaselineExpressionsBuffer subject;


    @Before
    public void initializeSubject() {

        Factor factor1 = new Factor("ORGANISM_PART", "lung");
        Factor factor2 = new Factor("ORGANISM_PART", "liver");
        Factor factor3 = new Factor("ORGANISM_PART", "longue");

        // the only possible factor values here are the default ones
        List<FactorGroup> orderedAllFactorValues = new LinkedList<>();
        orderedAllFactorValues.add(new FactorSet().add(factor1));
        orderedAllFactorValues.add(new FactorSet().add(factor2));
        orderedAllFactorValues.add(new FactorSet().add(factor3));

        subject = new BaselineExpressionsBuffer(orderedAllFactorValues);

    }

    @Test
    public void pollShouldReturnExpressionsInTheRightOrder() throws Exception {
        //given we load the buffer with three expressions
        subject.reload(THREE_EXPRESSION_LEVELS);

        //when
        BaselineExpression expression = subject.poll();
        //then we expect first expression
        assertThat(expression.getLevel(), is(Double.valueOf(EXPRESSION_LEVEL_1)));

        //given we poll again
        expression = subject.poll();
        //then we expect second BaselineExpression
        assertThat(expression.getLevel(), is(Double.valueOf(EXPRESSION_LEVEL_2)));

        //given we poll again
        expression = subject.poll();
        //then we expect second BaselineExpression
        assertThat(expression.getLevel(), is(Double.valueOf(EXPRESSION_LEVEL_3)));

    }

    @Test
    public void bufferShouldReturnNullWhenExhausted() throws Exception {
        //given we load the buffer with three expressions
        subject.reload(THREE_EXPRESSION_LEVELS);

        //when
        subject.poll();
        subject.poll();
        subject.poll();
        //then we expect next poll to return null
        assertThat(subject.poll(), Matchers.is(nullValue()));
    }

    @Test
    public void reloadShouldRefillAnExhaustedBuffer() throws Exception {
        //given we load the buffer with three expressions
        subject.reload(THREE_EXPRESSION_LEVELS);

        //when we poll until exhaustion
        BaselineExpression run;
        do {
            run = subject.poll();
        } while (run != null);
        //and we reload again with new values
        subject.reload("T1", "1", "2", "3");
        //and we poll
        BaselineExpression expression = subject.poll();
        //then we expect to find the new values
        assertThat(expression.getLevel(), is(1d));
    }

    @Test(expected = IllegalStateException.class)
    public void reloadShouldThrowExceptionIfBufferIsNotEmpty() throws Exception {
        //given we load the buffer with three expressions
        subject.reload(THREE_EXPRESSION_LEVELS);
        //and we poll only a single expression
        subject.poll();

        //when we reload again
        subject.reload(THREE_EXPRESSION_LEVELS);

        //then we expect an IllegalArgumentException
    }


}
