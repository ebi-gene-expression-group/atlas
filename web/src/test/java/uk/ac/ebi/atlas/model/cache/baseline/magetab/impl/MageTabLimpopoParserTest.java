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

package uk.ac.ebi.atlas.model.cache.baseline.magetab.impl;

import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MageTabLimpopoParserTest {

    MageTabLimpopoParser subject;

    @Before
    public void setUp() throws Exception {
        subject = new MageTabLimpopoParser();
    }

    @Test
    public void testCountUpperCaseLetters() {

        // given
        int nbUpperCase1 = subject.countUpperCaseLetters("RNA");
        int nbUpperCase2 = subject.countUpperCaseLetters("organism");

        // then
        assertThat(nbUpperCase1, is(3));
        assertThat(nbUpperCase2, is(0));
    }

    @Test
    public void testPrettifyFactorType() {

        // given
        String pretty1 = subject.prettifyFactorType("organism_part");
        String pretty2 = subject.prettifyFactorType("organism part");
        String pretty3 = subject.prettifyFactorType("RNA");
        String pretty4 = subject.prettifyFactorType("qPCR");

        // then
        assertThat(pretty1, is("Organism Part"));
        assertThat(pretty2, is("Organism Part"));
        assertThat(pretty3, is("RNA"));
        assertThat(pretty4, is("qPCR"));
    }

    @Test
    public void testTransformFactorNames() {

        Map<String, String> map = Maps.newHashMap();
        map.put("ORGANISM", "organism");
        map.put("ORGANISM_PART", "organism_part");
        map.put("RNA", "RNA");

        // given
        Map<String, String> transform = subject.transformFactorNames(map);

        // then
        assertThat(transform.get("ORGANISM"), is("Organism"));
        assertThat(transform.get("ORGANISM_PART"), is("Organism Part"));
        assertThat(transform.get("RNA"), is("RNA"));

    }
}