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

package uk.ac.ebi.atlas.experimentimport.experimentdesign.condensedSdrf;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class MicroArrayExperimentWithMultipleEFOTermsCondensedSdrfParserIT {

    private static final String MICROARRAY_EXPERIMENT_ACCESSION = "E-GEOD-2507";

    @Inject
    private CondensedSdrfParser subject;

    @Test
    public void asTableDataShouldReturnTheRightStuff() throws IOException {
        ExperimentDesign experimentDesign = subject.parse(MICROARRAY_EXPERIMENT_ACCESSION, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL).getExperimentDesign();

        assertThat(experimentDesign.asTableData().size(), is(20));
        assertThat(experimentDesign.asTableData().get(0), arrayContaining("N/LV1", "A-AFFY-6", null, "wild type genotype", "Mus musculus", "left ventricle cardiac muscle", "female", "C57BL/6", "wild type genotype", "left ventricle cardiac muscle"));
        assertThat(experimentDesign.asTableData().get(19), arrayContaining("S/SM5", "A-AFFY-6", "muscular dystrophy", "dysferlin mutant", "Mus musculus", "skeletal muscle", "female", "SJL/J", "dysferlin mutant", "skeletal muscle"));

    }

    @Test
    public void testGetSpeciesForAssays() throws IOException {
        ExperimentDesign experimentDesign = subject.parse(MICROARRAY_EXPERIMENT_ACCESSION, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL).getExperimentDesign();
        Set<String> species = experimentDesign.getSpeciesForAssays(Sets.newHashSet("N/LV1", "S/SM5"));
        assertThat(species, containsInAnyOrder("Mus musculus"));

    }

    @Test
    public void sampleHasMultipleOntologyTerms() throws IOException{
        ExperimentDesign experimentDesign = subject.parse(MICROARRAY_EXPERIMENT_ACCESSION, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL).getExperimentDesign();
        assertThat(experimentDesign.getAllOntologyTermIdsByAssayAccession().get("N/LV1").size(), is(5));
        assertThat(experimentDesign.getFactors("N/LV1").getFactorByType("ORGANISM_PART").getValueOntologyTerms().size(), is(2));
    }

}