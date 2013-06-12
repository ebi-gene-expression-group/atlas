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
import org.junit.Test;

import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class ContrastPropertyTest {
    @Test
    public void testHasEqualValues() throws Exception {
        ContrastProperty property = new ContrastProperty("a", "a", "a", ContrastProperty.ContrastPropertyType.FACTOR);
        assertThat(property.hasEqualValues(), is(true));

        property = new ContrastProperty("a", "z", "a", ContrastProperty.ContrastPropertyType.FACTOR);
        assertThat(property.hasEqualValues(), is(false));

        property = new ContrastProperty("a", "z", null, ContrastProperty.ContrastPropertyType.FACTOR);
        assertThat(property.hasEqualValues(), is(false));
    }

    @Test
    public void testCompareTo() throws Exception {
        ContrastProperty property1 = new ContrastProperty("b", "z", "a", ContrastProperty.ContrastPropertyType.FACTOR);
        ContrastProperty property2 = new ContrastProperty("a", "a", "a", ContrastProperty.ContrastPropertyType.FACTOR);
        ContrastProperty property3 = new ContrastProperty("c", null, "a", ContrastProperty.ContrastPropertyType.FACTOR);
        ContrastProperty property4 = new ContrastProperty("q", "a", "z", ContrastProperty.ContrastPropertyType.FACTOR);
        ContrastProperty property5 = new ContrastProperty("d", "a", "a", ContrastProperty.ContrastPropertyType.FACTOR);

        SortedSet<ContrastProperty> properties = Sets.newTreeSet(Sets.newHashSet(property1, property2, property3, property4, property5));

        assertThat(properties, contains(property2, property5, property1, property3, property4));
    }
}
