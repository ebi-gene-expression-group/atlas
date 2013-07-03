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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.controllers.rest.ExperimentLoaderController;

import javax.inject.Inject;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DifferentialGeneProfileServiceIT {

    private static final String E_GEOD_22351 = "E-GEOD-22351";
    private static final String E_GEOD_21860 = "E-GEOD-21860";
    private static final String E_MTAB_698 = "E-MTAB-698";
    private static final String E_GEOD_38400 = "E-GEOD-38400";
    private static final String E_MTAB_1066 = "E-MTAB-1066";

    private static final String FIRST_IDENTIFIER = "ENSMUSG00000029816";
    private static final String SECOND_IDENTIFIER = "ENSMUSG00000036887";
    private static final String THIRD_IDENTIFIER = "ENSMUSG00000026628";
    private static final String FOURTH_IDENTIFIER = "AT3G29644";
    private static final String FIFTH_IDENTIFIER = "FBgn0035109";
    private static final String SIXTH_IDENTIFIER = "FBgn0032801";

    private static final double FDR_CUTOFF = 0.5;
    private static final String MUS_MUSCULUS = "Mus musculus";
    private static final String ARABIDOPSIS_THALIANA = "Arabidopsis thaliana";
    private static final String DROSOPHILA_MELANOGASTER = "Drosophila melanogaster";

    @Inject
    private ApplicationProperties properties;

    @Inject
    private DifferentialGeneProfileService subject;

    @Inject
    private ExperimentLoaderController experimentLoaderController;

    @Before
    public void setup() throws Exception {
        experimentLoaderController.loadExperiment(E_GEOD_21860, ExperimentType.DIFFERENTIAL.name());
        experimentLoaderController.loadExperiment(E_GEOD_38400, ExperimentType.DIFFERENTIAL.name());
        experimentLoaderController.loadExperiment(E_GEOD_22351, ExperimentType.DIFFERENTIAL.name());
        experimentLoaderController.loadExperiment(E_MTAB_698, ExperimentType.DIFFERENTIAL.name());
        experimentLoaderController.loadExperiment(E_MTAB_1066, ExperimentType.MICROARRAY.name());
    }

    @Test
    public void testForAccessions() throws Exception {
        assertThat(properties.getDifferentialExperimentsIdentifiers(), containsInAnyOrder(E_GEOD_22351, E_GEOD_38400, E_GEOD_21860, E_MTAB_698));
        assertThat(properties.getMicroarrayExperimentsIdentifiers(), containsInAnyOrder(E_MTAB_1066));
    }

    @Test
    public void testGetDifferentialProfilesListForIdentifierFirst() throws Exception {
        DifferentialGeneProfileProperties differentialGeneProfileProperties = subject.initDifferentialProfilesListMapForIdentifier(FIRST_IDENTIFIER, FDR_CUTOFF);
        assertThat(differentialGeneProfileProperties, is(not(nullValue())));
        assertThat(differentialGeneProfileProperties.getAllExperimentAccessions(), contains(E_GEOD_22351));
        DifferentialProfilesList differentialProfilesListForExperiment = differentialGeneProfileProperties.getDifferentialProfilesListForExperiment(E_GEOD_22351);
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesListForExperiment.get(0);
        assertThat(differentialProfile.getId(), is(FIRST_IDENTIFIER));
        assertThat(differentialProfile.getConditions().size(), is(1));
    }

    @Test
    public void testGetDifferentialProfilesListForIdentifierSecond() throws Exception {
        DifferentialGeneProfileProperties differentialGeneProfileProperties = subject.initDifferentialProfilesListMapForIdentifier(SECOND_IDENTIFIER, FDR_CUTOFF);
        assertThat(differentialGeneProfileProperties, is(not(nullValue())));
        assertThat(differentialGeneProfileProperties.getAllExperimentAccessions(), containsInAnyOrder(E_GEOD_21860, E_GEOD_22351));
        DifferentialProfilesList differentialProfilesListForExperiment = differentialGeneProfileProperties.getDifferentialProfilesListForExperiment(E_GEOD_21860);
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesListForExperiment.get(0);
        assertThat(differentialProfile.getId(), is(SECOND_IDENTIFIER));
        assertThat(differentialProfile.getConditions().size(), is(1));
        differentialProfilesListForExperiment = differentialGeneProfileProperties.getDifferentialProfilesListForExperiment(E_GEOD_22351);
        differentialProfile = (DifferentialProfile) differentialProfilesListForExperiment.get(0);
        assertThat(differentialProfile.getId(), is(SECOND_IDENTIFIER));
        assertThat(differentialProfile.getConditions().size(), is(1));
    }

    @Test
    public void testGetDifferentialProfilesListForIdentifierThird() throws Exception {
        DifferentialGeneProfileProperties differentialGeneProfileProperties = subject.initDifferentialProfilesListMapForIdentifier(THIRD_IDENTIFIER, FDR_CUTOFF);
        assertThat(differentialGeneProfileProperties, is(not(nullValue())));
        assertThat(differentialGeneProfileProperties.getAllExperimentAccessions(), containsInAnyOrder(E_MTAB_698, E_GEOD_22351));
        DifferentialProfilesList differentialProfilesListForExperiment = differentialGeneProfileProperties.getDifferentialProfilesListForExperiment(E_MTAB_698);
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesListForExperiment.get(0);
        assertThat(differentialProfile.getId(), is(THIRD_IDENTIFIER));
        assertThat(differentialProfile.getConditions().size(), is(1));
        differentialProfilesListForExperiment = differentialGeneProfileProperties.getDifferentialProfilesListForExperiment(E_GEOD_22351);
        differentialProfile = (DifferentialProfile) differentialProfilesListForExperiment.get(0);
        assertThat(differentialProfile.getId(), is(THIRD_IDENTIFIER));
        assertThat(differentialProfile.getConditions().size(), is(1));
    }

    @Test
    public void testGetDifferentialProfilesListForIdentifierFourth() throws Exception {
        DifferentialGeneProfileProperties differentialGeneProfileProperties = subject.initDifferentialProfilesListMapForIdentifier(FOURTH_IDENTIFIER, FDR_CUTOFF);
        assertThat(differentialGeneProfileProperties, is(not(nullValue())));
        assertThat(differentialGeneProfileProperties.getAllExperimentAccessions(), contains(E_GEOD_38400));
        DifferentialProfilesList differentialProfilesListForExperiment = differentialGeneProfileProperties.getDifferentialProfilesListForExperiment(E_GEOD_38400);
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesListForExperiment.get(0);
        assertThat(differentialProfile.getId(), is(FOURTH_IDENTIFIER));
        assertThat(differentialProfile.getConditions().size(), is(3));
    }

    @Test
    public void testRetrieveDifferentialProfileForExperimentEGEOD22351First() throws Exception {
        DifferentialProfilesList differentialProfilesList = subject.retrieveDifferentialProfilesForRnaSeqExperiment(E_GEOD_22351, FIRST_IDENTIFIER, FDR_CUTOFF, MUS_MUSCULUS);
        assertThat(differentialProfilesList, is(not(nullValue())));
        assertThat(differentialProfilesList.size(), is(1));
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesList.get(0);
        assertThat(differentialProfile.getId(), is(FIRST_IDENTIFIER));
    }

    @Test
    public void testRetrieveDifferentialProfileForExperimentEGEOD21860Second() throws Exception {
        DifferentialProfilesList differentialProfilesList = subject.retrieveDifferentialProfilesForRnaSeqExperiment(E_GEOD_21860, SECOND_IDENTIFIER, FDR_CUTOFF, MUS_MUSCULUS);
        assertThat(differentialProfilesList, is(not(nullValue())));
        assertThat(differentialProfilesList.size(), is(1));
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesList.get(0);
        assertThat(differentialProfile.getId(), is(SECOND_IDENTIFIER));
    }

    @Test
    public void testRetrieveDifferentialProfileForExperimentEGEOD22351Second() throws Exception {
        DifferentialProfilesList differentialProfilesList = subject.retrieveDifferentialProfilesForRnaSeqExperiment(E_GEOD_22351, SECOND_IDENTIFIER, FDR_CUTOFF, MUS_MUSCULUS);
        assertThat(differentialProfilesList, is(not(nullValue())));
        assertThat(differentialProfilesList.size(), is(1));
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesList.get(0);
        assertThat(differentialProfile.getId(), is(SECOND_IDENTIFIER));
    }

    @Test
    public void testRetrieveDifferentialProfileForExperimentEMTAB698Third() throws Exception {
        DifferentialProfilesList differentialProfilesList = subject.retrieveDifferentialProfilesForRnaSeqExperiment(E_MTAB_698, THIRD_IDENTIFIER, FDR_CUTOFF, MUS_MUSCULUS);
        assertThat(differentialProfilesList, is(not(nullValue())));
        assertThat(differentialProfilesList.size(), is(1));
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesList.get(0);
        assertThat(differentialProfile.getId(), is(THIRD_IDENTIFIER));
    }

    @Test
    public void testRetrieveDifferentialProfileForExperimentEGEOD22351Third() throws Exception {
        DifferentialProfilesList differentialProfilesList = subject.retrieveDifferentialProfilesForRnaSeqExperiment(E_GEOD_22351, THIRD_IDENTIFIER, FDR_CUTOFF, MUS_MUSCULUS);
        assertThat(differentialProfilesList, is(not(nullValue())));
        assertThat(differentialProfilesList.size(), is(1));
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesList.get(0);
        assertThat(differentialProfile.getId(), is(THIRD_IDENTIFIER));
    }

    @Test
    public void testRetrieveDifferentialProfileForExperimentEGEOD38400Fourth() throws Exception {
        DifferentialProfilesList differentialProfilesList = subject.retrieveDifferentialProfilesForRnaSeqExperiment(E_GEOD_38400, FOURTH_IDENTIFIER, FDR_CUTOFF, ARABIDOPSIS_THALIANA);
        assertThat(differentialProfilesList, is(not(nullValue())));
        assertThat(differentialProfilesList.size(), is(1));
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesList.get(0);
        assertThat(differentialProfile.getId(), is(FOURTH_IDENTIFIER));
    }

    @Test
    public void testRetrieveDifferentialProfilesForMicroarrayExperimentEMTAB1066Fifth() throws Exception {
        Collection<DifferentialProfilesList> differentialProfilesLists = subject.retrieveDifferentialProfilesForMicroarrayExperiment(E_MTAB_1066, FIFTH_IDENTIFIER, FDR_CUTOFF, DROSOPHILA_MELANOGASTER);
        assertThat(differentialProfilesLists, is(not(nullValue())));
        assertThat(differentialProfilesLists.size(), is(1));
        DifferentialProfilesList differentialProfilesList = differentialProfilesLists.iterator().next();
        assertThat(differentialProfilesLists.size(), is(1));
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesList.get(0);
        assertThat(differentialProfile.getId(), is(FIFTH_IDENTIFIER));
    }

    @Test
    public void testRetrieveDifferentialProfilesForMicroarrayExperimentEMTAB1066Sixth() throws Exception {
        Collection<DifferentialProfilesList> differentialProfilesLists = subject.retrieveDifferentialProfilesForMicroarrayExperiment(E_MTAB_1066, SIXTH_IDENTIFIER, FDR_CUTOFF, DROSOPHILA_MELANOGASTER);
        assertThat(differentialProfilesLists, is(not(nullValue())));
        assertThat(differentialProfilesLists.size(), is(1));
        DifferentialProfilesList differentialProfilesList = differentialProfilesLists.iterator().next();
        assertThat(differentialProfilesList.size(), is(1));
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesList.get(0);
        assertThat(differentialProfile.getId(), is(SIXTH_IDENTIFIER));
        assertThat(differentialProfile.getConditions().size(), is(2));
    }

    @Test
    public void testRetrieveDifferentialProfileForExperimentEGEOD22351WrongSpecie() throws Exception {
        DifferentialProfilesList differentialProfilesList = subject.retrieveDifferentialProfilesForRnaSeqExperiment(E_GEOD_22351, FIRST_IDENTIFIER, FDR_CUTOFF, "BLA");
        assertThat(differentialProfilesList.isEmpty(), is(true));
    }

    @Test
    public void testRetrieveDifferentialProfilesForMicroarrayExperimentEMTAB1066WrongSpecie() throws Exception {
        Collection<DifferentialProfilesList> differentialProfilesLists = subject.retrieveDifferentialProfilesForMicroarrayExperiment(E_MTAB_1066, SIXTH_IDENTIFIER, FDR_CUTOFF, "BLA");
        assertThat(differentialProfilesLists.isEmpty(), is(true));
    }
}