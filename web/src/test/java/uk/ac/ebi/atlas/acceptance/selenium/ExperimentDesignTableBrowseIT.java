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

import org.junit.Assert;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExperimentDesignTablePage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ExperimentDesignTableBrowseIT extends SeleniumFixture {

    private ExperimentDesignTablePage subject;

    public void getStartingPage() {
        subject = new ExperimentDesignTablePage(driver);
        subject.get();
    }

    @Test
    public void defaultLandingPage() {
        assertThat(subject.getExperimentDesignTableHeader().size(), is(10));
        assertThat(subject.getExperimentDesignTableInfo(), is("Showing 1 to 16 of 16 entries (filtered from 48 total entries)"));
        assertThat(subject.getFirstExperimentDesign(), hasItem("ERR030872"));
        assertThat(subject.getLastExperimentDesign(), hasItem("ERR030887"));
        assertThat(subject.isSelectedOnlyAnalysedBox(), is(true));
        assertThat(subject.isTextInBoldFace(), is(false));
    }

    @Test
    public void showNotOnlyAnalysed() {
        subject.clickOnlyAnalysedBox();
        assertThat(subject.isSelectedOnlyAnalysedBox(), is(false));
        assertThat(subject.isTextInBoldFace(), is(false));
        assertThat(subject.getExperimentDesignTableInfo(), is("Showing 1 to 48 of 48 entries"));
        assertThat(subject.getFirstExperimentDesign(), hasItem("ERR030856"));
        assertThat(subject.getLastExperimentDesign(), hasItem("ERR030903"));
    }

    @Test
    public void filterBySearch() {
        assertThat(subject.getSearchFieldValue(), is(""));
        subject.setSearchFieldValue("female");
        assertThat(subject.getSearchFieldValue(), is("female"));
        assertThat(subject.getExperimentDesignTableInfo(), is("Showing 1 to 8 of 8 entries (filtered from 48 total entries)"));
        assertThat(subject.getFirstExperimentDesign(), hasItem("female"));
        assertThat(subject.getLastExperimentDesign(), hasItem("female"));
    }

    @Test
    public void sortOnFirstColumn() {
        assertThat(subject.getFirstExperimentDesign(), hasItem("ERR030872"));
        assertThat(subject.getLastExperimentDesign(), hasItem("ERR030887"));
        subject.clickFirstColumnHeader();
        assertThat(subject.getFirstExperimentDesign(), hasItem("ERR030887"));
        assertThat(subject.getLastExperimentDesign(), hasItem("ERR030872"));
        subject.clickFirstColumnHeader();
        assertThat(subject.getFirstExperimentDesign(), hasItem("ERR030872"));
        assertThat(subject.getLastExperimentDesign(), hasItem("ERR030887"));
    }

    @Test
    public void sortOnSecondColumn() {
        assertThat(subject.getFirstExperimentDesign(), hasItem("ERR030872"));
        assertThat(subject.getLastExperimentDesign(), hasItem("ERR030887"));
        subject.clickSecondColumnHeader();
        assertThat(subject.getFirstExperimentDesign(), hasItem("ERR030873"));
        assertThat(subject.getLastExperimentDesign(), hasItem("ERR030878"));
        subject.clickSecondColumnHeader();
        assertThat(subject.getFirstExperimentDesign(), hasItem("ERR030878"));
        assertThat(subject.getLastExperimentDesign(), hasItem("ERR030873"));
    }

    @Test
    public void sortOnThirdColumn() {
        assertThat(subject.getFirstExperimentDesign(), hasItem("ERR030872"));
        assertThat(subject.getLastExperimentDesign(), hasItem("ERR030887"));
        subject.clickThirdColumnHeader();
        assertThat(subject.getFirstExperimentDesign(), hasItem("ERR030880"));
        assertThat(subject.getLastExperimentDesign(), hasItem("ERR030875"));
        subject.clickThirdColumnHeader();
        assertThat(subject.getFirstExperimentDesign(), hasItem("ERR030875"));
        assertThat(subject.getLastExperimentDesign(), hasItem("ERR030880"));
    }

    @Test
    public void verifyDownloadExpressionProfilesLink() {
        Assert.assertThat(subject.getDownloadExperimentDesignLink(), endsWith(ExperimentDesignTablePage.EXPERIMENT_ACCESSION + "-experiment-design.tsv"));
    }
}
