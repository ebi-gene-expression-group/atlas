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

package uk.ac.ebi.atlas.web.controllers.page;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DifferentialGeneProfileServiceIT {

    private static final String EXPERIMENT_ACCESSION = "E-GEOD-22351";
    private static final String IDENTIFIER = "ENSMUSG00000029816";
    private static final String SPECIES = "Mus musculus";

    @Inject
    private DifferentialGeneProfileService subject;

    @Test
    public void testGetDifferentialProfilesList() throws Exception {
        DifferentialProfilesList differentialProfilesList = subject.getDifferentialProfilesList(IDENTIFIER, SPECIES);
        assertThat(differentialProfilesList, is(not(nullValue())));
        assertThat(differentialProfilesList.size(), is(1));
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesList.get(0);
        assertThat(differentialProfile.getId(), is(IDENTIFIER));
    }

    @Test
    public void testRetrieveDifferentialProfileForExperiment() throws Exception {
        DifferentialProfilesList differentialProfilesList = subject.retrieveDifferentialProfileForExperiment(EXPERIMENT_ACCESSION, SPECIES);
        assertThat(differentialProfilesList, is(not(nullValue())));
        assertThat(differentialProfilesList.size(), is(49));
    }
}