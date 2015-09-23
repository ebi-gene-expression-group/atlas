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

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.DifferentialExperimentDesignTablePage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DifferentialExperimentDesignPageSIT extends SeleniumFixture {

    public static final String NRPE1_MUTANT_VS_WILD_TYPE = "nrpe1 mutant vs wild type";
    private DifferentialExperimentDesignTablePage subject;

    @Before
    public void setUp() {
        subject = new DifferentialExperimentDesignTablePage(driver);
        subject.get();
    }

    @Test
    public void testContrastDefault() {

        // given
        assertThat(subject.getSelectedContrast(), is(NRPE1_MUTANT_VS_WILD_TYPE));

        // then
        assertThat(subject.getExperimentDesignTableHeader().size(), is(10));
        // TODO https://www.pivotaltracker.com/story/show/100371514
        // assertThat(subject.getFirstExperimentDesignTableLine(), contains("SRR576327", "2 to 3 weeks", "seedling", "Col-0", "idn2-1", "Arabidopsis thaliana", "idn2-1"));
        assertThat(subject.getFirstExperimentDesignTableLine(), contains("SRR576327", "2 to 3 week", "seedling", "Col-0", "idn2-1", "Arabidopsis thaliana", "idn2-1"));
        assertThat(subject.getDownloadExperimentDesignLink(), endsWith(DifferentialExperimentDesignTablePage.EXPERIMENT_ACCESSION + "/experiment-design.tsv"));

        // and
        assertThat(subject.getLineColor(1), isOneOf("transparent","rgba(0, 0, 0, 0)"));
        assertThat(subject.getLineColor(4), is("rgba(130, 205, 205, 1)"));
        assertThat(subject.getLineColor(10), is("rgba(255, 194, 102, 1)"));

    }

    @Test
    public void testSelectAContrast() {

        // given
        subject.selectContrast("g1_g2");

        // then
        assertThat(subject.getSelectedContrast(), is(NRPE1_MUTANT_VS_WILD_TYPE));
        assertThat(subject.getExperimentDesignTableHeader().size(), is(10));
        // TODO https://www.pivotaltracker.com/story/show/100371514
        // assertThat(subject.getFirstExperimentDesignTableLine(), contains("SRR576327", "2 to 3 weeks", "seedling", "Col-0", "idn2-1", "Arabidopsis thaliana", "idn2-1"));
        assertThat(subject.getFirstExperimentDesignTableLine(), contains("SRR576327", "2 to 3 week", "seedling", "Col-0", "idn2-1", "Arabidopsis thaliana", "idn2-1"));

        // and
        assertThat(subject.getLineColor(1), isOneOf("transparent","rgba(0, 0, 0, 0)"));
        assertThat(subject.getLineColor(4), is("rgba(130, 205, 205, 1)"));
        assertThat(subject.getLineColor(10), is("rgba(255, 194, 102, 1)"));

    }

}