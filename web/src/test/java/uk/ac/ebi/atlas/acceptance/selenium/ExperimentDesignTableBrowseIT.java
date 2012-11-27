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

package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExperimentDesignTablePage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class ExperimentDesignTableBrowseIT extends SeleniumFixture {

    private ExperimentDesignTablePage subject;

    public void getStartingPage() {
        subject = new ExperimentDesignTablePage(firefoxDriver);
        subject.get();
    }

    @Test
    public void defaultLandingPage() {
        assertThat(subject.getExperimentDesignTableHeader().size(), is(8));
        assertThat(subject.getExperimentDesignTableInfo(), is("Showing 1 to 25 of 64 entries"));
        assertThat(subject.getFirstExperimentDesign(), hasItem("ERR030856"));
        assertThat(subject.getLastExperimentDesign(), hasItem("ERR030876"));
        assertThat(subject.getToggleButtonValue(), is("De-hightlight Analysed"));
        assertThat(subject.isTextInBoldFace(), is(true));
    }

    @Test
    public void toggleHighlight() {
        subject.clickToggleButton();
        assertThat(subject.getToggleButtonValue(), is("Highlight Analysed"));
        assertThat(subject.isTextInBoldFace(), is(false));
    }

    @Test
    public void clickNextButton() {
        subject.clickNextButton();
        assertThat(subject.getExperimentDesignTableInfo(), is("Showing 26 to 50 of 64 entries"));
        assertThat(subject.getFirstExperimentDesign(), hasItem("ERR030876"));
        assertThat(subject.getLastExperimentDesign(), hasItem("ERR030889"));
        assertThat(subject.isTextInBoldFace(), is(true));
    }

    @Test
    public void clickPreviousButton() {
        clickNextButton();
        subject.clickPreviousButton();
        assertThat(subject.getExperimentDesignTableInfo(), is("Showing 1 to 25 of 64 entries"));
        assertThat(subject.getFirstExperimentDesign(), hasItem("ERR030856"));
        assertThat(subject.getLastExperimentDesign(), hasItem("ERR030876"));
        assertThat(subject.isTextInBoldFace(), is(true));
    }

    @Test
    public void changeTableLength() {
        assertThat(subject.getLenghtValue(), is("25"));
        subject.setLengthValue("50");
        assertThat(subject.getLenghtValue(), is("50"));
        assertThat(subject.getExperimentDesignTableInfo(), is("Showing 1 to 50 of 64 entries"));
        assertThat(subject.getFirstExperimentDesign(), hasItem("ERR030856"));
        assertThat(subject.getLastExperimentDesign(), hasItem("ERR030889"));
    }

    @Test
    public void filterBySearch() {
        assertThat(subject.getSearchFieldValue(), is(""));
        subject.setSearchFieldValue("female");
        assertThat(subject.getSearchFieldValue(), is("female"));
        assertThat(subject.getExperimentDesignTableInfo(), is("Showing 1 to 24 of 24 entries (filtered from 64 total entries)"));
        assertThat(subject.getFirstExperimentDesign(), hasItem("female"));
        assertThat(subject.getLastExperimentDesign(), hasItem("female"));
    }

    @Test
    public void sortOnFirstColumn() {
        assertThat(subject.getFirstExperimentDesign(), hasItem("ERR030856"));
        assertThat(subject.getLastExperimentDesign(), hasItem("ERR030876"));
        subject.clickFirstColumnHeader();
        assertThat(subject.getFirstExperimentDesign(), hasItem("ERR030903"));
        assertThat(subject.getLastExperimentDesign(), hasItem("ERR030883"));
        subject.clickFirstColumnHeader();
        assertThat(subject.getFirstExperimentDesign(), hasItem("ERR030856"));
        assertThat(subject.getLastExperimentDesign(), hasItem("ERR030876"));
    }

    @Test
    public void sortOnThirdColumn() {
        assertThat(subject.getFirstExperimentDesign(), hasItem("ERR030856"));
        assertThat(subject.getLastExperimentDesign(), hasItem("ERR030876"));
        subject.clickThirdColumnHeader();
        assertThat(subject.getFirstExperimentDesign(), hasItem("ERR030856"));
        assertThat(subject.getLastExperimentDesign(), hasItem("ERR030895"));
        subject.clickThirdColumnHeader();
        assertThat(subject.getFirstExperimentDesign(), hasItem("ERR030878"));
        assertThat(subject.getLastExperimentDesign(), hasItem("ERR030872"));
    }
}
