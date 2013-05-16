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
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commons.magetab.MageTabLimpopoUtils;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MageTabLimpopoExpDesignParserIT {

    private static final String EXPERIMENT_ACCESSION_E_MTAB_513 = "E-MTAB-513";

    private static final String EXPERIMENT_ACCESSION_E_GEOD_26284 = "E-GEOD-26284";

    private static final String EXPERIMENT_ACCESSION_E_MTAB_1066 = "E-MTAB-1066";

    private static final String EXPERIMENT_ACCESSION_E_GEOD_43049 = "E-GEOD-43049";

    @Inject
    private MageTabLimpopoUtils mageTabLimpopoUtils;

    private ExpDesignMageTabParser subject;

    @Before
    public void setUp() throws Exception {
        subject = new ExpDesignMageTabParser();
        subject.setMageTabLimpopoUtils(mageTabLimpopoUtils);
    }

    @Test(expected = IllegalStateException.class)
    public void testForExperimentAccession() throws Exception {
        subject.forExperimentAccession(null);
        subject.init();
    }

    @Test
    public void testExtractCharacteristics513() throws Exception {
        subject.forExperimentAccession(EXPERIMENT_ACCESSION_E_MTAB_513).init();
        assertThat(subject.extractCharacteristics(), containsInAnyOrder("sex", "age", "organism part", "Organism", "ethnic group"));
    }

    @Test
    public void testExtractCharacteristics26284() throws Exception {
        subject.forExperimentAccession(EXPERIMENT_ACCESSION_E_GEOD_26284).init();
        assertThat(subject.extractCharacteristics(), containsInAnyOrder("sex", "biosource provider", "cell line", "cellular component", "organism part", "karyotype", "disease state", "cell type", "Organism"));
    }

    @Test
    public void testExtractCharacteristics1066() throws Exception {
        subject.forExperimentAccession(EXPERIMENT_ACCESSION_E_MTAB_1066).init();
        assertThat(subject.extractCharacteristics(), containsInAnyOrder("DevelopmentalStage", "Genotype", "Organism", "StrainOrLine"));
    }

    @Test
    public void testExtractCharacteristics43049() throws Exception {
        subject.forExperimentAccession(EXPERIMENT_ACCESSION_E_GEOD_43049).init();
        assertThat(subject.extractCharacteristics(), containsInAnyOrder("Organism", "cell line", "culture condition"));
    }

}