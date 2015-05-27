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

package uk.ac.ebi.atlas.experimentpage.baseline.geod30352;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class HeatmapTableForDifferentOrganismsAndDefaultQueryParamsSIT extends SeleniumFixture {

    private static final String E_GEOD_30352_ACCESSION = "E-GEOD-30352";
    public static final String ORGANISM_PART = "Organism part";
    protected HeatmapTablePage subject;

    @Test
    public void verifyQueryFactorLabelAndHeatmapHeadersForPanPaniscus() {
        subject = new HeatmapTablePage(driver, E_GEOD_30352_ACCESSION, "serializedFilterFactors=ORGANISM:Pan%20paniscus");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is(ORGANISM_PART));

        assertThat(subject.getFactorValueHeaders().size(), is(6));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("cerebellum"));
    }

    @Test
    public void verifyQueryFactorLabelAndHeatmapHeadersForPanTroglodytes() {
        subject = new HeatmapTablePage(driver, E_GEOD_30352_ACCESSION, "serializedFilterFactors=ORGANISM:Pan%20troglodytes");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is(ORGANISM_PART));

        assertThat(subject.getFactorValueHeaders().size(), is(6));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("cerebellum"));
    }

    @Test
    public void verifyQueryFactorLabelAndHeatmapHeadersForPongoPygmaeus() {
        subject = new HeatmapTablePage(driver, E_GEOD_30352_ACCESSION, "serializedFilterFactors=ORGANISM:Pongo%20pygmaeus");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is(ORGANISM_PART));

        assertThat(subject.getFactorValueHeaders().size(), is(5));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("cerebellum"));
    }

    @Test
    public void verifyQueryFactorLabelAndHeatmapHeadersForGallusGallus() {
        subject = new HeatmapTablePage(driver, E_GEOD_30352_ACCESSION, "serializedFilterFactors=ORGANISM:Gallus%20gallus");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is(ORGANISM_PART));

        assertThat(subject.getFactorValueHeaders().size(), is(6));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("brain"));
    }

    @Test
    public void verifyQueryFactorLabelAndHeatmapHeadersForGorillaGorilla() {
        subject = new HeatmapTablePage(driver, E_GEOD_30352_ACCESSION, "serializedFilterFactors=ORGANISM:Gorilla%20gorilla");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is(ORGANISM_PART));

        assertThat(subject.getFactorValueHeaders().size(), is(6));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("cerebellum"));
    }

    @Test
    public void verifyQueryFactorLabelAndHeatmapHeadersForHomoSapiens() {
        subject = new HeatmapTablePage(driver, E_GEOD_30352_ACCESSION, "serializedFilterFactors=ORGANISM:Homo%20sapiens");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is(ORGANISM_PART));

        assertThat(subject.getFactorValueHeaders().size(), is(8));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("cerebellum"));
    }

    @Test
    public void verifyGeneExpressionLevelOfNAIsNotShown() {
        subject = new HeatmapTablePage(driver, E_GEOD_30352_ACCESSION, "serializedFilterFactors=ORGANISM:Homo%20sapiens&displayLevels=true");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is(ORGANISM_PART));

        assertThat(subject.getFactorValueHeaders().size(), is(8));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("cerebellum"));

        List<String> geneNames = subject.getGeneNames();
        assertThat(geneNames, hasItem("TGDS"));

        int tgdsPosition = geneNames.indexOf("TGDS");
        List<String> tgds = subject.getGeneProfile(tgdsPosition + 1);

        assertThat(tgds, contains("1", "1", "1", "2", "", "2", "1", "3"));
    }

    @Test
    public void verifyQueryFactorLabelAndHeatmapHeadersForMacacaMulatta() {
        subject = new HeatmapTablePage(driver, E_GEOD_30352_ACCESSION, "serializedFilterFactors=ORGANISM:Macaca%20mulatta");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is(ORGANISM_PART));

        assertThat(subject.getFactorValueHeaders().size(), is(6));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("brain"));
    }

    @Test
    public void verifyQueryFactorLabelAndHeatmapHeadersForMonodelphisDomestica() {
        subject = new HeatmapTablePage(driver, E_GEOD_30352_ACCESSION, "serializedFilterFactors=ORGANISM:Monodelphis%20domestica");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is(ORGANISM_PART));

        assertThat(subject.getFactorValueHeaders().size(), is(6));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("brain"));
    }

}
