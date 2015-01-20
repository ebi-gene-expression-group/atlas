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

package uk.ac.ebi.atlas.widget.protein;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWidgetPage;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.StringContains.containsString;

public class HeatmapWidgetControllerMultipleGenesExperimentSIT extends SeleniumFixture {

    private HeatmapTableWidgetPage subject;

    // tests the URL being used by http://www.ebi.ac.uk/Tools/biojs/registry/Biojs.ExpressionAtlasBaselineSummary.html
    @Before
    public void initPage(){
        subject = new HeatmapTableWidgetPage(driver, "geneQuery=ENSG00000187003+ENSG00000185264");
        subject.get();
    }

    @Test
    public void header() {
        assertThat(subject.getAnatomogram().isDisplayed(), is(true));
        assertThat(subject.getGeneCount(), containsString("2 of 2"));

        String experimentDescription = subject.getExperimentDescription();
        assertThat(experimentDescription, startsWith("RNA-seq of coding RNA from tissue samples of 95 human individuals representing 27 different tissues in order to determine tissue-specificity of all protein-coding genes"));

        //TODO: fix experiment URL
        String experimentDescriptionLink = subject.getExperimentDescriptionLink();
        assertThat(experimentDescriptionLink, endsWith("/gxa/experiments/E-MTAB-1733?geneQuery=ENSG00000187003%20ENSG00000185264&serializedFilterFactors="));

        assertThat(subject.getGeneNames(), contains("ACTL7A", "TEX33"));
    }
}
