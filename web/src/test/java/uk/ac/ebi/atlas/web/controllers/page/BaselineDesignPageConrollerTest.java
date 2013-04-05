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

package uk.ac.ebi.atlas.web.controllers.page;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BaselineDesignPageConrollerTest {

    private static final String[] HEADER_LINE = new String[]{"Assay", "Sample Characteristics[organism]", "Sample Characteristics[age]", "Sample Characteristics[sex]", "Sample Characteristics[biosource provider]", "Factor Values[organism part]"};

    private ExperimentDesignPageRequestHandler subject;

    @Before
    public void setUp() throws Exception {
        subject = new BaselineDesignPageController();
    }

    @Test
    public void testExtractSubcategoriesSamples() {

        // given
        Map<String, Integer> map = subject.extractHeaderIndexes(HEADER_LINE, ExperimentDesignPageRequestHandler.SAMPLE_COLUMN_HEADER_PATTERN);

        // then
        assertThat(map.size(), is(4));
        assertThat(map.get("organism"), is(1));
        assertThat(map.get("age"), is(2));
        assertThat(map.get("sex"), is(3));
        assertThat(map.get("biosource provider"), is(4));
    }

    @Test
    public void testExtractSubcategoriesFactors() {

        // given
        Map<String, Integer> map = subject.extractHeaderIndexes(HEADER_LINE, ExperimentDesignPageRequestHandler.FACTOR_COLUMN_HEADER_PATTERN);

        // then
        assertThat(map.size(), is(1));
        assertThat(map.get("organism part"), is(5));
    }

    @Test
    public void testCreateReorderMapping() {

        // split header line into samples and factors
        Map<String, Integer> samples = subject.extractHeaderIndexes(HEADER_LINE, ExperimentDesignPageRequestHandler.SAMPLE_COLUMN_HEADER_PATTERN);
        Map<String, Integer> factors = subject.extractHeaderIndexes(HEADER_LINE, ExperimentDesignPageRequestHandler.FACTOR_COLUMN_HEADER_PATTERN);

        // given
        Map<Integer, Integer> mapping = subject.createReorderMapping(samples, factors);

        // then
        assertThat(mapping.size(), is(5));
        assertThat(mapping.get(0), is(2)); // age
        assertThat(mapping.get(1), is(4)); // biosource provider
        assertThat(mapping.get(2), is(1)); // organism
        assertThat(mapping.get(3), is(3)); // sex
        assertThat(mapping.get(4), is(5)); // organism part
    }
}