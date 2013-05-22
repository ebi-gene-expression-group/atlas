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

    private static final String E_GEOD_22351 = "E-GEOD-22351";
    private static final String E_GEOD_21860 = "E-GEOD-21860";
    private static final String E_MTAB_698 = "E-MTAB-698";

    private static final String FIRST_IDENTIFIER = "ENSMUSG00000029816";
    private static final String SECOND_IDENTIFIER = "ENSMUSG00000036887";
    private static final String THIRD_IDENTIFIER = "ENSMUSG00000026628";

    private static final String MUS_MUSCULUS = "Mus musculus";
    private static final double FDR_CUTOFF = 0.5;

    @Inject
    private DifferentialGeneProfileService subject;

    @Test
    public void testGetDifferentialProfilesListForIdentifierFirst() throws Exception {
        DifferentialProfilesList differentialProfilesList = subject.getDifferentialProfilesListForIdentifier(FIRST_IDENTIFIER, MUS_MUSCULUS, FDR_CUTOFF);
        assertThat(differentialProfilesList, is(not(nullValue())));
        assertThat(differentialProfilesList.size(), is(1));
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesList.get(0);
        assertThat(differentialProfile.getId(), is(FIRST_IDENTIFIER));
    }

    @Test
    public void testGetDifferentialProfilesListForIdentifierSecond() throws Exception {
        DifferentialProfilesList differentialProfilesList = subject.getDifferentialProfilesListForIdentifier(SECOND_IDENTIFIER, MUS_MUSCULUS, FDR_CUTOFF);
        assertThat(differentialProfilesList, is(not(nullValue())));
        assertThat(differentialProfilesList.size(), is(2));
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesList.get(0);
        assertThat(differentialProfile.getId(), is(SECOND_IDENTIFIER));
        differentialProfile = (DifferentialProfile) differentialProfilesList.get(1);
        assertThat(differentialProfile.getId(), is(SECOND_IDENTIFIER));
    }

    @Test
    public void testGetDifferentialProfilesListForIdentifierThird() throws Exception {
        DifferentialProfilesList differentialProfilesList = subject.getDifferentialProfilesListForIdentifier(THIRD_IDENTIFIER, MUS_MUSCULUS, FDR_CUTOFF);
        assertThat(differentialProfilesList, is(not(nullValue())));
        assertThat(differentialProfilesList.size(), is(2));
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesList.get(0);
        assertThat(differentialProfile.getId(), is(THIRD_IDENTIFIER));
        differentialProfile = (DifferentialProfile) differentialProfilesList.get(1);
        assertThat(differentialProfile.getId(), is(THIRD_IDENTIFIER));
    }

    @Test
    public void testRetrieveDifferentialProfileForExperimentEGEOD22351First() throws Exception {
        DifferentialProfilesList differentialProfilesList = subject.retrieveDifferentialProfilesForExperiment(E_GEOD_22351, FIRST_IDENTIFIER, MUS_MUSCULUS, FDR_CUTOFF);
        assertThat(differentialProfilesList, is(not(nullValue())));
        assertThat(differentialProfilesList.size(), is(1));
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesList.get(0);
        assertThat(differentialProfile.getId(), is(FIRST_IDENTIFIER));
    }

    @Test
    public void testRetrieveDifferentialProfileForExperimentEGEOD21860Second() throws Exception {
        DifferentialProfilesList differentialProfilesList = subject.retrieveDifferentialProfilesForExperiment(E_GEOD_21860, SECOND_IDENTIFIER, MUS_MUSCULUS, FDR_CUTOFF);
        assertThat(differentialProfilesList, is(not(nullValue())));
        assertThat(differentialProfilesList.size(), is(1));
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesList.get(0);
        assertThat(differentialProfile.getId(), is(SECOND_IDENTIFIER));
    }

    @Test
    public void testRetrieveDifferentialProfileForExperimentEGEOD22351Second() throws Exception {
        DifferentialProfilesList differentialProfilesList = subject.retrieveDifferentialProfilesForExperiment(E_GEOD_22351, SECOND_IDENTIFIER, MUS_MUSCULUS, FDR_CUTOFF);
        assertThat(differentialProfilesList, is(not(nullValue())));
        assertThat(differentialProfilesList.size(), is(1));
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesList.get(0);
        assertThat(differentialProfile.getId(), is(SECOND_IDENTIFIER));
    }

    @Test
    public void testRetrieveDifferentialProfileForExperimentEMTAB698Third() throws Exception {
        DifferentialProfilesList differentialProfilesList = subject.retrieveDifferentialProfilesForExperiment(E_MTAB_698, THIRD_IDENTIFIER, MUS_MUSCULUS, FDR_CUTOFF);
        assertThat(differentialProfilesList, is(not(nullValue())));
        assertThat(differentialProfilesList.size(), is(1));
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesList.get(0);
        assertThat(differentialProfile.getId(), is(THIRD_IDENTIFIER));
    }

    @Test
    public void testRetrieveDifferentialProfileForExperimentEGEOD22351Third() throws Exception {
        DifferentialProfilesList differentialProfilesList = subject.retrieveDifferentialProfilesForExperiment(E_GEOD_22351, THIRD_IDENTIFIER, MUS_MUSCULUS, FDR_CUTOFF);
        assertThat(differentialProfilesList, is(not(nullValue())));
        assertThat(differentialProfilesList.size(), is(1));
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesList.get(0);
        assertThat(differentialProfile.getId(), is(THIRD_IDENTIFIER));
    }
}