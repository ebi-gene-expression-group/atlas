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

package uk.ac.ebi.atlas.model.baseline;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentalFactorsBuilderTest {
    ExperimentalFactorsBuilder subject;

    private Factor factorWithType1 = new Factor("TYPE1", "VALUE1");
    private Factor factorWithType2 = new Factor("TYPE2", "VALUE2");
    private Factor factorWithType2DifferentValue = new Factor("TYPE2", "DIFFERENT_VALUE2");
    private Factor factorWithType3 = new Factor("TYPE3", "VALUE3");
    private FactorGroup factorGroup1 = new FactorSet().add(factorWithType2)
            .add(factorWithType1)
            .add(factorWithType3);
    private FactorGroup factorGroup2 = new FactorSet().add(factorWithType3)
            .add(factorWithType2DifferentValue)
            .add(factorWithType1);


    @Before
    public void setUp() throws Exception {

        Map<String, String> factorNameByType = new HashMap<>();
        factorNameByType.put("TYPE1", "NAME1");
        factorNameByType.put("TYPE2", "NAME2");
        factorNameByType.put("TYPE3", "NAME3");

        List<FactorGroup> factorGroups = Lists.newArrayList(factorGroup1, factorGroup2);

        subject = new ExperimentalFactorsBuilder();
        subject.withMenuFilterFactorTypes(Sets.newHashSet("TYPE1"))
                .withOrderedFactorGroups(factorGroups)
                .withFactorNamesByType(factorNameByType);
    }

    @Test
    public void testFactorByNameIsCreated() {
        subject.create();

        SortedSetMultimap<String, Factor> factorsByType = subject.buildFactorsByType();
        assertThat(factorsByType.keySet(), contains("TYPE1", "TYPE2", "TYPE3"));
        assertThat(factorsByType.get("TYPE2"), contains(factorWithType2DifferentValue, factorWithType2));
    }

    @Test
    public void testCoOccurringFactors() {
        subject.create();

        SortedSetMultimap<Factor, Factor> coOccurringFactors = subject.buildCoOccurringFactors();

        assertThat(coOccurringFactors.get(factorWithType1), contains(factorWithType2DifferentValue, factorWithType2, factorWithType3));
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateWithNullMenuFilterFactorTypes() throws Exception {
        subject.withMenuFilterFactorTypes(null);
        subject.create();
    }

}
