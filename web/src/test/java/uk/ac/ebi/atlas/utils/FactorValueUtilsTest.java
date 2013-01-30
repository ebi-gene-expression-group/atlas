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

package uk.ac.ebi.atlas.utils;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.ac.ebi.atlas.model.FactorValue;
import uk.ac.ebi.atlas.streams.FilterParameters;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class FactorValueUtilsTest {

    public static final String ORGANISM_PART = "ORGANISM_PART";
    public static final String CELL_LINE = "CELL_LINE";

    FactorValue factorValue1 = new FactorValue(ORGANISM_PART, ORGANISM_PART, "liver");
    FactorValue factorValue2 = new FactorValue(ORGANISM_PART, ORGANISM_PART, "heart");
    FactorValue factorValue3 = new FactorValue(CELL_LINE, CELL_LINE, "imr-90");

    @Mock
    FilterParameters filterParameters;

    FactorValueUtils subject;

    @Before
    public void initSubject() {
        this.subject = new FactorValueUtils();
    }

    @Test
    public void testIndexFactorValuesByName() {

        Set<FactorValue> factorValues = Sets.newHashSet(factorValue1, factorValue2, factorValue3);

        assertThat(subject.factorValuesByName(factorValues), notNullValue());
        assertThat(subject.factorValuesByName(factorValues).keySet(), hasItems(ORGANISM_PART, CELL_LINE));
        assertThat(subject.factorValuesByName(factorValues).get(ORGANISM_PART), hasItems(factorValue1, factorValue2));
        assertThat(subject.factorValuesByName(factorValues).get(CELL_LINE), hasItems(factorValue3));
    }

    @Test
    public void testFormatFactorTypeForDisplay() {
        assertThat(subject.formatFactorTypeForDisplay(ORGANISM_PART), is("Organism part"));
        assertThat(subject.formatFactorTypeForDisplay(CELL_LINE), is("Cell line"));
    }
}