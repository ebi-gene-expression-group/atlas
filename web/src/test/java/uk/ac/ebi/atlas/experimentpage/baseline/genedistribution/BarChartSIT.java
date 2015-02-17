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

public class BarChartSIT extends SinglePageSeleniumFixture {

    private static final String E_GEOD_26284_ACCESSION = "E-GEOD-26284";
    private HeatmapTableWithSearchFormAndBarChartPage subject;

    @Override
    protected void getStartingPage() {
        subject = new HeatmapTableWithSearchFormAndBarChartPage(driver, E_GEOD_26284_ACCESSION, null);
        subject.get();
    }

    @Before
    public void displayBarChart() {
        subject.clickDisplayChartButton();
    }

    @Ignore
    public void checkBarChartAxisForAllOrganismParts() {
        assertThat(subject.getXAxisValue(0), is("0"));
        assertThat(subject.getXAxisValue(1), is("0.2"));
        assertThat(subject.getMaxXAxisValue(), is("1"));

        assertThat(subject.getYAxisValue(0), is("0"));
        assertThat(subject.getYAxisValue(1), is("10"));
        assertThat(subject.getMaxYAxisValue(), is("50"));

        assertThat(subject.getLegendLabel(), is("Y = number of genes expressed above the given FPKM cutoff in any experimental variable"));
    }

    @Ignore
    public void checkBarChartAxisForCellLine() {
        subject.selectQueryFactorValue("HFDPC cell line");
        assertThat(subject.getYAxisValue(0), is("0"));
        assertThat(subject.getYAxisValue(1), is("10"));
        assertThat(subject.getMaxYAxisValue(), is("40"));

        assertThat(subject.getLegendLabel(), is("Y = number of genes expressed above the given FPKM cutoff for the selected experimental variables"));
    }

}