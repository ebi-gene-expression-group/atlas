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

package uk.ac.ebi.atlas.acceptance.selenium.tests;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExperimentsTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SinglePageSeleniumFixture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ExperimentsTablePageIT extends SinglePageSeleniumFixture {

    private ExperimentsTablePage subject;

    @Override
    protected void getStartingPage() {
        subject = new ExperimentsTablePage(driver);
        subject.get();
    }

    @Test
    public void defaultExperimentsPage() {
        assertThat(subject.getExperimentsTableHeader().size(), is(8));
        assertThat(subject.getExperimentsTableInfo(), startsWith("Showing 1 to 10 of"));
        assertThat(subject.getFirstExperimentInfo(), hasItem("E-MTAB-599"));
        assertThat(subject.getLastExperimentInfo(), hasItem("E-GEOD-43049"));
    }

    @Test
    public void filterBySearch() {
        assertThat(subject.getSearchFieldValue(), is(""));
        subject.setSearchFieldValue("baseline");
        assertThat(subject.getSearchFieldValue(), is("baseline"));
        assertThat(subject.getExperimentsTableInfo(), startsWith("Showing 1 to 4 of"));
        assertThat(subject.getFirstExperimentInfo(), hasItem("E-MTAB-599"));
        assertThat(subject.getLastExperimentInfo(), hasItem("E-GEOD-30352"));
    }

    @Test
    public void sortOnFirstColumn() {
        assertThat(subject.getFirstExperimentInfo(), hasItem("E-MTAB-599"));
        assertThat(subject.getLastExperimentInfo(), hasItem("E-GEOD-43049"));
        subject.clickFirstColumnHeader();
        assertThat(subject.getFirstExperimentInfo(), hasItem("E-GEOD-21860"));
        assertThat(subject.getLastExperimentInfo(), hasItem("E-GEOD-26284"));
        subject.clickFirstColumnHeader();
        assertThat(subject.getFirstExperimentInfo(), hasItem("E-MTAB-599"));
        assertThat(subject.getLastExperimentInfo(), hasItem("E-GEOD-43049"));
    }

    @Test
    public void sortOnSecondColumn() {
        assertThat(subject.getFirstExperimentInfo(), hasItem("E-MTAB-599"));
        assertThat(subject.getLastExperimentInfo(), hasItem("E-GEOD-43049"));
        subject.clickSecondColumnHeader();
        assertThat(subject.getFirstExperimentInfo(), hasItem("E-GEOD-21860"));
        assertThat(subject.getLastExperimentInfo(), hasItem("E-MTAB-698"));
        subject.clickSecondColumnHeader();
        assertThat(subject.getFirstExperimentInfo(), hasItem("E-TABM-51"));
        assertThat(subject.getLastExperimentInfo(), hasItem("E-GEOD-22351"));
    }

    @Test
    public void sortOnThirdColumn() {
        assertThat(subject.getFirstExperimentInfo(), hasItem("E-MTAB-599"));
        assertThat(subject.getLastExperimentInfo(), hasItem("E-GEOD-43049"));
        subject.clickThirdColumnHeader();
        assertThat(subject.getFirstExperimentInfo(), hasItem("E-GEOD-43049"));
        assertThat(subject.getLastExperimentInfo(), hasItem("E-MTAB-1066"));
        subject.clickThirdColumnHeader();
        assertThat(subject.getFirstExperimentInfo(), hasItem("E-TABM-51"));
        assertThat(subject.getLastExperimentInfo(), hasItem("E-GEOD-30352"));
    }
}