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

package uk.ac.ebi.atlas.experimentpage.tooltip;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BaselineExperimentFactorTooltipSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION);
        subject.get();
    }

    @Test
    public void factorTooltipHeader() {
        assertThat(subject.getFactorTooltipHeader(0), is("Property"));
        assertThat(subject.getFactorTooltipHeader(1), is("Value (N=1)"));
    }

    @Test
    public void adiposeFactorTooltip() {
        assertThat(subject.getFactorTooltipContent(1, 0, 0), is("organism part"));
        assertThat(subject.getFactorTooltipContent(1, 0, 1), is("adipose tissue"));
        assertThat(subject.getFactorTooltipContent(1, 1, 0), is("age"));
        assertThat(subject.getFactorTooltipContent(1, 1, 1), is("73 year"));
        assertThat(subject.getFactorTooltipContent(1, 4, 0), is("sex"));
        assertThat(subject.getFactorTooltipContent(1, 4, 1), is("female"));
    }

}
