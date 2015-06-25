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
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.StringContains.containsString;

public class HeatmapWidgetControllerMultiSpeciesExperimentSIT extends SeleniumFixture {

    private static final String GENE_IN_MULTI_SPECIES_EXPERIMENT = "ENSGALG00000006591";

    private HeatmapTableWidgetPage subject;

    @Before
    public void initPage(){
        subject = new HeatmapTableWidgetPage(driver, "geneQuery=" + GENE_IN_MULTI_SPECIES_EXPERIMENT + "&propertyType=bioentity_identifier");
        subject.get();
    }

    @Test
    public void header() {
        assertThat(subject.getAnatomogram().isDisplayed(), is(true));
        assertThat(subject.getGeneCount(), containsString("of 1"));

        String experimentDescription = subject.getExperimentDescription();
        assertThat(experimentDescription, startsWith("RNA-seq of poly-A enriched total RNA of brain, liver, kidney, heart and skeletal muscle samples from 5 vertebrate species: mouse, chicken, frog, lizard and pufferfish"));

        String experimentDescriptionLink = subject.getExperimentDescriptionLink();
        assertThat(experimentDescriptionLink, endsWith("/experiments/E-GEOD-41338?geneQuery=ENSGALG00000006591&serializedFilterFactors=ORGANISM:Gallus%20gallus"));
    }

    @Test
    public void heatmap() {
        assertThat(subject.getGeneNamesJsp(), hasSize(1));

        String firstGeneName = subject.getGeneNamesJsp().get(0);
        assertThat(firstGeneName, is("TNNI2"));
    }
}
