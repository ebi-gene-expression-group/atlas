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
import java.util.Arrays;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class MicroArrayExperimentCondensedSdrfParserIT {

    private static final String MICROARRAY_EXPERIMENT_ACCESSION = "E-MEXP-1276";

    @Inject
    private CondensedSdrfParser subject;

    @Test
    public void asTableDataShouldReturnTheRightStuff() throws IOException {
        ExperimentDesign experimentDesign = subject.parse(MICROARRAY_EXPERIMENT_ACCESSION, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL).getExperimentDesign();

        assertThat(experimentDesign.asTableData().size(), is(6));
        System.out.println(Arrays.deepToString(experimentDesign.asTableData().get(0)));
        assertThat(experimentDesign.asTableData().get(0), arrayContaining("G-DBZ1","A-AFFY-36","8 to 12 weeks","adult","normal","wild type","birth","Mus musculus","pancreas","female","fresh sample","C57BL/6","dibenzazepine 10 micromoles per kilogram"));
        assertThat(experimentDesign.asTableData().get(5), arrayContaining("G-Vehicle3", "A-AFFY-36", "8 to 12 weeks", "adult", "normal", "wild type", "birth", "Mus musculus", "pancreas", "female", "fresh sample", "C57BL/6", "none"));

    }

    @Test
    public void testGetSpeciesForAssays() throws IOException {
        ExperimentDesign experimentDesign = subject.parse(MICROARRAY_EXPERIMENT_ACCESSION, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL).getExperimentDesign();
        Set<String> species = experimentDesign.getSpeciesForAssays(Sets.newHashSet("G-DBZ2", "G-Vehicle2"));
        assertThat(species, containsInAnyOrder("Mus musculus"));

    }

}