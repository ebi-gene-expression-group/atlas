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

package uk.ac.ebi.atlas.utils;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class NameUtilsTest {

    NameUtils subject = new NameUtils();

    @Test
    public void testShortName() {
        String shortName = "hmsc-at cell line";
        assertThat(subject.restrictSize(shortName, 17), is(shortName));
    }

    @Test
    public void testLongName() {
        String longName = "cd14-positive monocyte cell line";
        assertThat(subject.restrictSize(longName, 17), not(longName));
        assertThat(subject.restrictSize(longName, 17).length(), is(16));
        assertThat(subject.restrictSize(longName, 17), is("cd14-positive..."));
    }

}