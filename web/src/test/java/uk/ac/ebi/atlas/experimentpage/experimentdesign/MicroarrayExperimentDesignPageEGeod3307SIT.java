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

public class MicroarrayExperimentDesignPageEGeod3307SIT extends SeleniumFixture {

    public static final String DEFAULT = "'DMD' vs 'normal' on 'Affymetrix HG-U133A'";
    public static final String OTHER = "'EDMD XR' vs 'normal' on 'Affymetrix HG-U133A'";
    public static final String E_GEOD_3307 = "E-GEOD-3307";
    private MicroarrayExperimentDesignTablePage subject;

    @Before
    public void setUp() {
        subject = new MicroarrayExperimentDesignTablePage(driver);
        subject.setExperimentAccession(E_GEOD_3307);
        subject.get();
    }

    @Test
    public void testContrastDefault() {

        // given
        assertThat(subject.getSelectedContrast(), is(DEFAULT));

        // then
        assertThat(subject.getExperimentDesignTableHeader().size(), is(11));
        assertThat(subject.getFirstExperimentDesignTableLine(), contains("GSM120591", "A-AFFY-34", "", "acute quadriplegic myopathy", "Homo sapiens", "skeletal muscle", "male", "acute quadriplegic myopathy"));
        assertThat(subject.getDownloadExperimentDesignLink(), endsWith(E_GEOD_3307 + "/experiment-design.tsv"));
        assertThat(subject.getLastExperimentDesignTableLine(), contains("GSM74394", "A-AFFY-33", "", "X-linked recessive Emery-Dreifuss muscular dystrophy", "Homo sapiens", "skeletal muscle", "male", "X-linked recessive Emery-Dreifuss muscular dystrophy"));

        // and
        assertThat(subject.getLineColor(1), isOneOf("transparent", "rgba(0, 0, 0, 0)"));
        assertThat(subject.getLineColor(4), isOneOf("transparent", "rgba(0, 0, 0, 0)"));
        assertThat(subject.getLineColor(1), isOneOf("transparent", "rgba(0, 0, 0, 0)"));

    }

    @Test
    public void testSelectAContrast() {

        // given
        subject.selectContrast("g1_g2");

        // then
        assertThat(subject.getExperimentDesignTableHeader().size(), is(11));
        assertThat(subject.getFirstExperimentDesignTableLine(), contains("GSM120591", "A-AFFY-34", "", "acute quadriplegic myopathy", "Homo sapiens", "skeletal muscle", "male", "acute quadriplegic myopathy"));
        assertThat(subject.getDownloadExperimentDesignLink(), endsWith(E_GEOD_3307 + "/experiment-design.tsv"));
        assertThat(subject.getLastExperimentDesignTableLine(), contains("GSM74394", "A-AFFY-33", "", "X-linked recessive Emery-Dreifuss muscular dystrophy", "Homo sapiens", "skeletal muscle", "male", "X-linked recessive Emery-Dreifuss muscular dystrophy"));

        // and
        assertThat(subject.getLineColor(1), isOneOf("transparent", "rgba(0, 0, 0, 0)"));
        assertThat(subject.getLineColor(4), isOneOf("transparent", "rgba(0, 0, 0, 0)"));
        assertThat(subject.getLineColor(1), isOneOf("transparent", "rgba(0, 0, 0, 0)"));

    }

    @Test
    public void testSelectAnotherContrast() {

        // given
        subject.selectContrast("g1_g3");

        // then
        assertThat(subject.getSelectedContrast(), is(OTHER));
        assertThat(subject.getExperimentDesignTableHeader().size(), is(11));
        assertThat(subject.getFirstExperimentDesignTableLine(), contains("GSM120591", "A-AFFY-34", "", "acute quadriplegic myopathy", "Homo sapiens", "skeletal muscle", "male", "acute quadriplegic myopathy"));
        assertThat(subject.getLastExperimentDesignTableLine(), contains("GSM74394", "A-AFFY-33", "", "X-linked recessive Emery-Dreifuss muscular dystrophy", "Homo sapiens", "skeletal muscle", "male", "X-linked recessive Emery-Dreifuss muscular dystrophy"));

        // and
        assertThat(subject.getLineColor(1), isOneOf("transparent", "rgba(0, 0, 0, 0)"));
        assertThat(subject.getLineColor(4), isOneOf("transparent", "rgba(0, 0, 0, 0)"));
        assertThat(subject.getLineColor(9), isOneOf("transparent", "rgba(0, 0, 0, 0)"));

    }

}