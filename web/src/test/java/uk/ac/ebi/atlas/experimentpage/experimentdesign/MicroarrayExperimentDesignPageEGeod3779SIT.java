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
import uk.ac.ebi.atlas.acceptance.selenium.pages.MicroarrayExperimentDesignTablePage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MicroarrayExperimentDesignPageEGeod3779SIT extends SeleniumFixture {

    public static final String DEFAULT = "genotype:'p107 -/-' vs 'wild type' on A-AFFY-24";
    public static final String OTHER = "genotype:'p107 -/-' vs 'wild type' on A-AFFY-23";
    public static final String E_GEOD_3779 = "E-GEOD-3779";
    private MicroarrayExperimentDesignTablePage subject;

    @Before
    public void setUp() {
        subject = new MicroarrayExperimentDesignTablePage(driver);
        subject.setExperimentAccession(E_GEOD_3779);
        subject.get();
    }

    @Test
    public void testContrastDefault() {

        // given
        assertThat(subject.getSelectedContrast(), is(DEFAULT));

        // then
        assertThat(subject.getExperimentDesignTableHeader().size(), is(12));
        assertThat(subject.getFirstExperimentDesignTableLine(), contains("9447-4 -/-, chip MOE430A", "A-AFFY-23", "neurosphere", "embryonic day 13.5", "gene_knock_out", "p107 -/-", "Mus musculus", "brain germinal zone", "p107 -/-"));
        assertThat(subject.getDownloadExperimentDesignLink(), endsWith(E_GEOD_3779 + "/experiment-design.tsv"));
        assertThat(subject.getLastExperimentDesignTableLine(), contains("9887-4, chip MOE430B", "A-AFFY-24", "neurosphere", "embryonic day 13.5", "", "wild type", "Mus musculus", "brain germinal zone", "wild type"));

        // and
        assertThat(subject.getLineColor(1), isOneOf("transparent","rgba(0, 0, 0, 0)"));
        assertThat(subject.getLineColor(4), is("rgba(130, 205, 205, 1)"));
        assertThat(subject.getLineColor(1), isOneOf("transparent","rgba(0, 0, 0, 0)"));

    }

    @Test
    public void testSelectAContrast() {

        // given
        subject.selectContrast("g2_g1");

        // then
        assertThat(subject.getSelectedContrast(), is(DEFAULT));
        assertThat(subject.getExperimentDesignTableHeader().size(), is(12));
        assertThat(subject.getFirstExperimentDesignTableLine(), contains("9447-4 -/-, chip MOE430A", "A-AFFY-23", "neurosphere", "embryonic day 13.5", "gene_knock_out", "p107 -/-", "Mus musculus", "brain germinal zone", "p107 -/-"));
        assertThat(subject.getLastExperimentDesignTableLine(), contains("9887-4, chip MOE430B", "A-AFFY-24", "neurosphere", "embryonic day 13.5", "", "wild type", "Mus musculus", "brain germinal zone", "wild type"));

        // and
        assertThat(subject.getLineColor(1), isOneOf("transparent","rgba(0, 0, 0, 0)"));
        assertThat(subject.getLineColor(4), is("rgba(130, 205, 205, 1)"));
        assertThat(subject.getLineColor(1), isOneOf("transparent","rgba(0, 0, 0, 0)"));

    }

    @Test
    public void testSelectAnotherContrast() {

        // given
        subject.selectContrast("g3_g4");

        // then
        assertThat(subject.getSelectedContrast(), is(OTHER));
        assertThat(subject.getExperimentDesignTableHeader().size(), is(12));
        assertThat(subject.getFirstExperimentDesignTableLine(), contains("9447-4 -/-, chip MOE430A", "A-AFFY-23", "neurosphere", "embryonic day 13.5", "gene_knock_out", "p107 -/-", "Mus musculus", "brain germinal zone", "p107 -/-"));
        assertThat(subject.getLastExperimentDesignTableLine(), contains("9887-4, chip MOE430B", "A-AFFY-24", "neurosphere", "embryonic day 13.5", "", "wild type", "Mus musculus", "brain germinal zone", "wild type"));

        // and
        assertThat(subject.getLineColor(1), is("rgba(130, 205, 205, 1)"));
        assertThat(subject.getLineColor(4), isOneOf("transparent","rgba(0, 0, 0, 0)"));
        assertThat(subject.getLineColor(9), is("rgba(255, 194, 102, 1)"));

    }

}