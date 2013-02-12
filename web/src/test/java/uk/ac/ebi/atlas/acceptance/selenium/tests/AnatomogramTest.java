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
import org.openqa.selenium.By;
import uk.ac.ebi.atlas.acceptance.selenium.pages.AtlasPage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AnatomogramTest extends SeleniumFixture {

    AtlasPage subject;

    public void getStartingPage() {
    }

    private void initStartingPage(final String url) {
        subject = new AtlasPage(driver) {
            @Override
            protected String getPageURI() {
                return url;
            }
        };
        subject.get();
    }

    private boolean isAnotomogramElementFound() {
        return driver.findElements(By.id("anatomogram")).size() > 0;
    }

    @Test
    public void testAnotomogramIsThereForHomoSapiensExp() {
        initStartingPage("/gxa/experiments/E-GEOD-30352");
        assertThat(isAnotomogramElementFound(), is(true));
    }


    @Test
    public void testAnotomogramIsNotThereForMultiSpeciesExpForChicken() {
        initStartingPage("/gxa/experiments/E-GEOD-30352?serializedFilterFactors=ORGANISM:Gallus+gallus&queryFactorType=ORGANISM_PART&geneQuery=");
        assertThat(isAnotomogramElementFound(), is(false));
    }

    @Test
    public void testAnotomogramIsThereForMultiSpeciesExpForHomoSapiens() {
        initStartingPage("/gxa/experiments/E-GEOD-30352?serializedFilterFactors=ORGANISM:Homo+sapiens&queryFactorType=ORGANISM_PART&geneQuery=");
        assertThat(isAnotomogramElementFound(), is(true));
    }

    @Test
    public void testAnotomogramIsNotThereForMultiSpeciesExpForOrganismPartFiltering() {
        initStartingPage("/gxa/experiments/E-GEOD-30352?serializedFilterFactors=ORGANISM_PART:liver&queryFactorType=ORGANISM&geneQuery=");
        assertThat(isAnotomogramElementFound(), is(false));
    }
}
