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

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HeatmapTableWithCutoff05AndGenePropertyFilterIT extends SeleniumFixture {

    protected HeatmapTablePage subject;

    public void getStartingPage() {
//
    }

    @Test
    public void verifyResultOnSinglePropertyQuery() {
        subject = new HeatmapTablePage(firefoxDriver, "geneQuery=regulation&cutoff=0.5");
        subject.get();
        assertThat(subject.getGeneCount().contains("62"), is(true));

    }

    @Test
    public void verifyResultOnMultiplePropertyQuery() {
        subject = new HeatmapTablePage(firefoxDriver, "geneQuery=regulation+%22protein+binding%22&cutoff=0.5");
        subject.get();
        assertThat(subject.getGeneCount().contains("82"), is(true));

    }

    @Test
    public void verifyResultOnMultiplePropertyAndOrganismPartQuery() {
        subject = new HeatmapTablePage(firefoxDriver, "geneQuery=regulation+%22protein+binding%22&" +
                "organismParts=skeletal+muscle&organismParts=thyroid&_organismParts=1&cutoff=0.5");
        subject.get();
        assertThat(subject.getGeneCount().contains("1"), is(true));
        assertThat(subject.getFirstGeneProfile(), contains("2", "0.7"));


    }

}
