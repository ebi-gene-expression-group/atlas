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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MicroArrayExpDesignMageTabParserIT {

    private static final String MICROARRAY_EXPERIMENT_ACCESSION = "E-MTAB-1066";

    @Named("microarrayExperimentDesignMageTabParser")
    @Inject
    private MicroarrayExperimentDesignMageTabParser subject;

    @Test
    public void asTableDataShouldReturnTheRightStuff() throws IOException {
        ExperimentDesign experimentDesign = subject.parse(MICROARRAY_EXPERIMENT_ACCESSION);

        assertThat(experimentDesign.asTableData().size(), is(9));
        assertThat(experimentDesign.asTableData().get(0), arrayContaining("C1", "A-AFFY-35", "3rd instar larva", "w1118; +; cycCY5", "Drosophila melanogaster", null, "cycC mutant"));
        assertThat(experimentDesign.asTableData().get(8), arrayContaining("WT3", "A-AFFY-35", "3rd instar larva", "wild_type", "Drosophila melanogaster", "Oregon R", "wild_type"));

    }

}