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

package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormAndBarChartPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BarChartProteomicsSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-PROT-1";

    private HeatmapTableWithSearchFormAndBarChartPage subject;

    @Override
    protected void getStartingPage() {
        subject = new HeatmapTableWithSearchFormAndBarChartPage(driver, EXPERIMENT_ACCESSION);
        subject.get();
    }

    @Before
    public void displayBarChart() {
        subject.clickDisplayChartButton();
    }

    @Ignore
    public void legendLabel() {
        assertThat(subject.getLegendLabel(), is("Y = number of genes expressed above the given expression level cutoff in any experimental variable"));
    }

}