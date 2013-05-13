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

package uk.ac.ebi.atlas.expdesign;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

public class MageTabLimpopoExpDesignParserTest {

    static final String EXPERIMENT_ACCESSION_E_MTAB_513 = "E-MTAB-513";

    static final String EXPERIMENT_ACCESSION_E_GEOD_26284 = "E-GEOD-26284";

    static final String EXPERIMENT_ACCESSION_E_MTAB_1066 = "E-MTAB-1066";

    static final String EXPERIMENT_ACCESSION_E_GEOD_43049 = "E-GEOD-43049";

    MageTabLimpopoExpDesignParser subject;

    @Before
    public void setUp() throws Exception {

        subject = new MageTabLimpopoExpDesignParser();
        subject.setIdfUrlTemplate("http://www.ebi.ac.uk/arrayexpress/files/{0}/{0}.idf.txt");
        subject.setIdfPathTemplate("/magetab/{0}/{0}.idf.txt");

    }

    @Test(expected = IllegalStateException.class)
    public void testForExperimentAccession() throws Exception {
        subject.forExperimentAccession(null);
        subject.build();
    }

    @Test
    public void testBuild() throws Exception {
        assertThat(subject.forExperimentAccession(EXPERIMENT_ACCESSION_E_MTAB_513).build(), is(subject));
    }

    @Test
    public void testExtractCharacteristics513() throws Exception {
        subject.forExperimentAccession(EXPERIMENT_ACCESSION_E_MTAB_513).build();
        assertThat(subject.extractCharacteristics(), containsInAnyOrder("sex", "age", "organism part", "Organism", "ethnic group"));
    }

    @Test
    public void testExtractCharacteristics26284() throws Exception {
        subject.forExperimentAccession(EXPERIMENT_ACCESSION_E_GEOD_26284).build();
        assertThat(subject.extractCharacteristics(), containsInAnyOrder("sex", "biosource provider", "cell line", "cellular component", "organism part", "karyotype", "disease state", "cell type", "Organism"));
    }

    @Test
    public void testExtractCharacteristics1066() throws Exception {
        subject.forExperimentAccession(EXPERIMENT_ACCESSION_E_MTAB_1066).build();
        assertThat(subject.extractCharacteristics(), containsInAnyOrder("DevelopmentalStage", "Genotype", "Organism", "StrainOrLine"));
    }

    @Test
    public void testExtractCharacteristics43049() throws Exception {
        subject.forExperimentAccession(EXPERIMENT_ACCESSION_E_GEOD_43049).build();
        assertThat(subject.extractCharacteristics(), containsInAnyOrder("Organism", "cell line", "culture condition"));
    }

}