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

package uk.ac.ebi.atlas.acceptance.selenium.tests.mtab1066;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.MicroarrayExperimentDesignTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MicroarrayExperimentDesignPageIT extends SeleniumFixture {

    public static final String DEFAULT = "genotype:'cycC mutant' vs 'wild type'";
    public static final String OTHER = "genotype:'cdk8 mutant' vs 'wild type'";
    private MicroarrayExperimentDesignTablePage subject;

    @Before
    public void setUp() {
        subject = new MicroarrayExperimentDesignTablePage(driver);
        subject.get();
    }

    @Test
    public void testContrastDefault() {

        // given
        assertThat(subject.getSelectedContrast(), is(DEFAULT));

        // then
        assertThat(subject.getExperimentDesignTableHeader().size(), is(10));
        assertThat(subject.getFirstExperimentDesign(), contains("C1", "A-AFFY-35", "3rd instar larva", "w1118; +; cycCY5", "Drosophila melanogaster", "", "cycC mutant"));
        assertThat(subject.getDownloadExperimentDesignLink(), endsWith(MicroarrayExperimentDesignTablePage.EXPERIMENT_ACCESSION + "/experiment-design.tsv"));

        // and
        assertThat(subject.getLineColor(1), is("rgba(130, 205, 205, 1)"));
        assertThat(subject.getLineColor(4), is("transparent"));
        assertThat(subject.getLineColor(9), is("rgba(255, 194, 102, 1)"));

    }

    @Test
    public void testSelectAContrast() {

        // given
        subject.selectContrast("g2_g1");

        // then
        assertThat(subject.getSelectedContrast(), is(DEFAULT));
        assertThat(subject.getExperimentDesignTableHeader().size(), is(10));
        assertThat(subject.getFirstExperimentDesign(), contains("C1", "A-AFFY-35", "3rd instar larva", "w1118; +; cycCY5", "Drosophila melanogaster", "", "cycC mutant"));

        // and
        assertThat(subject.getLineColor(1), is("rgba(130, 205, 205, 1)"));
        assertThat(subject.getLineColor(4), is("transparent"));
        assertThat(subject.getLineColor(9), is("rgba(255, 194, 102, 1)"));

    }

    @Test
    public void testSelectAnotherContrast() {

        // given
        subject.selectContrast("g2_g3");

        // then
        assertThat(subject.getSelectedContrast(), is(OTHER));
        assertThat(subject.getExperimentDesignTableHeader().size(), is(10));
        assertThat(subject.getFirstExperimentDesign(), contains("C1", "A-AFFY-35", "3rd instar larva", "w1118; +; cycCY5", "Drosophila melanogaster", "", "cycC mutant"));

        // and
        assertThat(subject.getLineColor(1), is("transparent"));
        assertThat(subject.getLineColor(4), is("rgba(130, 205, 205, 1)"));
        assertThat(subject.getLineColor(9), is("rgba(255, 194, 102, 1)"));

    }

}