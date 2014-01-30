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
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineExperimentsPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class BaselineExperimentsControllerIT extends SinglePageSeleniumFixture {

    private BaselineExperimentsPage subject;

    public void getStartingPage() {
        subject = new BaselineExperimentsPage(driver);
        subject.get();
    }

    @Test
    public void countNumberOfSpecies() {
        assertThat(subject.getAllSpeciesItems().size(), is(9));
    }

    @Test
    public void checkFirstSpecieName() {
        assertThat(subject.getNameOfSpecies(0), is("Gallus gallus"));
    }

    @Test
    public void checkSecondSpecieName() {
        assertThat(subject.getNameOfSpecies(1), is("Gorilla gorilla"));
    }

    @Test
    public void checkNumberOfExperimentsOfFirstSpecies() {
        assertThat(subject.getAllExperimentsOfSpecies(2).size(), is(4));
    }

    @Test
    public void checkNumberOfExperimentsOfSecondSpecies() {
        assertThat(subject.getAllExperimentsOfSpecies(5).size(), is(2));
    }

    @Test
    public void checkSecondSpecieExperimentLink() {
        List<String> allExperimentLinksOfSpecies = subject.getAllExperimentLinksOfSpecies(1);
        assertThat(allExperimentLinksOfSpecies.get(0), containsString("experiments/E-GEOD-30352?serializedFilterFactors=ORGANISM:Gorilla%20gorilla"));
    }

    @Test
    public void checkMouseExperimentLink() {
        List<String> allExperimentLinksOfSpecies = subject.getAllExperimentLinksOfSpecies(5);
        assertThat(allExperimentLinksOfSpecies.get(0), containsString("experiments/E-MTAB-599"));
        assertThat(allExperimentLinksOfSpecies.get(1), containsString("experiments/E-GEOD-30352?serializedFilterFactors=ORGANISM:Mus%20musculus"));
    }
}