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

package uk.ac.ebi.atlas.experimentpage.experimentdesign;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExperimentDesignTablePage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class ExperimentDesignMultiFactorSIT extends SinglePageSeleniumFixture {

    private ExperimentDesignTablePage subject;

    public void getStartingPage() {
        subject = new ExperimentDesignTablePage(driver, "E-TABM-51");
        subject.get();
    }

    @Test
    public void sortedByAllFactors() {
        assertThat(subject.getExperimentDesignTableHeader().size(), is(12));
        assertThat(subject.getExperimentDesignTableInfo(), is("Showing 1 to 18 of 18 entries (filtered from 126 total entries)"));
        assertThat(subject.getFirstExperimentDesignTableLine(), hasItem("284b"));
        assertThat(subject.getLastExperimentDesignTableLine(), hasItem("357-2"));
    }

}
