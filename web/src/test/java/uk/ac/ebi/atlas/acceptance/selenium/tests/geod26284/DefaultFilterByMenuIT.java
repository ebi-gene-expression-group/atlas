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

package uk.ac.ebi.atlas.acceptance.selenium.tests.geod26284;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DefaultFilterByMenuIT extends SeleniumFixture {

    protected Geod26284HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new Geod26284HeatmapTablePage(driver);
        subject.get();
    }

    @Test
    public void verifyFilterByMenuTopLabel() {
        assertThat(subject.getFilterByMenuTopText(), is("Change filters"));
    }

    @Test
    public void verifyFilterByMenuFirstLabels() {
        assertThat(subject.getFilterByMenuText(0), is("RNA type"));
        assertThat(subject.getFilterByMenuText(1), is("cell line"));
        assertThat(subject.getFilterByMenuText(2), is("cellular component"));
    }

    @Test
    public void verifyFilterByMenuFirstFirstLabels() {
        assertThat(subject.getFilterByMenuText(new int[]{0, 0}), is("long non-polya rna"));
    }
}