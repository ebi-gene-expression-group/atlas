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

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.Factor;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.mock;

public class FilterFactorsConverterTest {

    private static final String SERIALIZED_FACTOR1 = "TYPE1:value1";
    private static final String SERIALIZED_FACTOR2 = "TYPE2:value2";
    private static final String SERIALIZED_FACTORS = SERIALIZED_FACTOR1 + "," + SERIALIZED_FACTOR2;
    private FilterFactorsConverter subject;

    private Factor factor1 = new Factor("type1", "value1");
    private Factor factor2 = new Factor("type2", "value2");

    @Before
    public void setUp() throws Exception {
        Experiment experimentMock = mock(Experiment.class);
        subject = new FilterFactorsConverter();
    }

    @Test
    public void testSerializeFactors() throws Exception {
        //given
        String serializedFactors = subject.serialize(Lists.newArrayList(factor1, factor2));
        //then
        assertThat(serializedFactors, is(SERIALIZED_FACTORS));
    }

    @Test
    public void testDeserialize() throws Exception {
        //given
        Set<Factor> factors = subject.deserialize(SERIALIZED_FACTORS);
        //then
        assertThat(factors, containsInAnyOrder(factor1, factor2));
    }
}
