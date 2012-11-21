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

package uk.ac.ebi.atlas.geneindex;

import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class GenePropertyQueryBuilderTest {

    GenePropertyQueryBuilder subject = new GenePropertyQueryBuilder();

    @Test
    public void testParseSearchText() {
        Collection<String> strings = subject.parseSearchText("aa \"1 cc\" d , \"c\"");
        System.out.println(strings);
        assertThat(strings, hasItems("aa", "1 cc", "d", "c"));
        assertThat(strings, not(hasItem(",")));

        strings = subject.parseSearchText("ENSG00000175084 ENSG00000210195");
        System.out.println(strings);
        assertThat(strings, hasItems("ENSG00000175084","ENSG00000210195"));

        strings = subject.parseSearchText("LINC00402, RP11-192H23.4");
        System.out.println(strings);
        assertThat(strings, hasItems("LINC00402","RP11-192H23.4"));
    }


}