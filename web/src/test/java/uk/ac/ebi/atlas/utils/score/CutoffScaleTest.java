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

package uk.ac.ebi.atlas.utils.score;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CutoffScaleTest {

    private CutoffScale subject;

    @Before
    public void setUp() throws Exception {
        this.subject = new CutoffScale();
    }

    @Test
    public void cutoffValuesShouldBeMagnified(){
        assertThat(subject.getNthValue(0), is(0d));
        assertThat(subject.getNthValue(1), is(0.1d));
        assertThat(subject.getNthValue(2), is(0.2d));

        assertThat(subject.getNthValue(10), is(1d));

        assertThat(subject.getNthValue(34), is(700d));

        assertThat(subject.getNthValue(54), is(90000d));

    }

    @Test
    public void cutoffStringValuesShouldBeMagnified(){

        assertThat(subject.getNthStringValue(0), is("0"));
        assertThat(subject.getNthStringValue(1), is("0.1"));
        assertThat(subject.getNthStringValue(4), is("0.4"));

        assertThat(subject.getNthStringValue(10), is("1"));
        assertThat(subject.getNthStringValue(11), is("2"));
        assertThat(subject.getNthStringValue(14), is("5"));

        assertThat(subject.getNthStringValue(30), is("300"));
        assertThat(subject.getNthStringValue(33), is("600"));
        assertThat(subject.getNthStringValue(34), is("700"));

        assertThat(subject.getNthStringValue(50), is("50000"));
        assertThat(subject.getNthStringValue(53), is("80000"));
        assertThat(subject.getNthStringValue(54), is("90000"));

    }

}
