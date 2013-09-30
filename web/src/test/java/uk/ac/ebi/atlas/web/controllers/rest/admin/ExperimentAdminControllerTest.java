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

package uk.ac.ebi.atlas.web.controllers.rest.admin;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentloader.ExperimentCRUD;
import uk.ac.ebi.atlas.experimentloader.ExperimentChecker;
import uk.ac.ebi.atlas.experimentloader.ExperimentDAO;
import uk.ac.ebi.atlas.experimentloader.ExperimentDTO;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.transcript.TranscriptProfileDAO;

import java.util.GregorianCalendar;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentAdminControllerTest {

    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";
    private static final String EXCEPTION_MESSAGE = "EXCEPTION_MESSAGE";
    private static final String ACCESS_KEY = "AN_UUID";

    @Mock
    private ExperimentDAO experimentDAOMock;

    @Mock
    private TranscriptProfileDAO transcriptProfileDAOMock;

    @Mock
    private ExperimentChecker experimentCheckerMock;

    @Mock
    private ExperimentCRUD experimentCRUDMock;

    private ExperimentAdminController subject;

    @Before
    public void setUp() throws Exception {

        subject = new ExperimentAdminController(experimentCheckerMock, experimentCRUDMock);

        given(experimentDAOMock.findPublicExperiment(EXPERIMENT_ACCESSION)).willReturn(null);
        given(experimentDAOMock.findAllExperiments()).willReturn(
                Lists.newArrayList(new ExperimentDTO(EXPERIMENT_ACCESSION, ExperimentType.BASELINE, null, null, null, false)));
        ExperimentDTO experimentDTO = new ExperimentDTO(EXPERIMENT_ACCESSION, ExperimentType.BASELINE, Sets.newHashSet("human", "mouse"),
                        Sets.newHashSet("1", "2"), "Illumina", false);
        given(experimentDAOMock.addExperiment(experimentDTO)).willReturn(UUID.randomUUID());
    }

    @Test
    public void loadExperimentShouldSucceed() throws Exception {
        String responseText = subject.loadExperiment(EXPERIMENT_ACCESSION, ExperimentType.BASELINE, false);
        assertThat(responseText, startsWith("Experiment " + EXPERIMENT_ACCESSION + " loaded, accessKey:"));
        verify(experimentCheckerMock).checkAllFiles(EXPERIMENT_ACCESSION, ExperimentType.BASELINE);
        verify(experimentCRUDMock).importExperiment(EXPERIMENT_ACCESSION, ExperimentType.BASELINE, false);
    }

    @Test(expected = IllegalStateException.class)
    public void loadExperimentShouldFail() throws Exception {
        willThrow(new IllegalStateException(EXCEPTION_MESSAGE))
                .given(experimentCRUDMock).importExperiment(EXPERIMENT_ACCESSION, ExperimentType.TWOCOLOUR, false);

        subject.loadExperiment(EXPERIMENT_ACCESSION, ExperimentType.TWOCOLOUR, false);
    }

    @Test
    public void deleteExperimentShouldSucceed() throws Exception {
        String responseText = subject.deleteExperiment(EXPERIMENT_ACCESSION);
        assertThat(responseText, is("Experiment " + EXPERIMENT_ACCESSION + " successfully deleted."));
    }

    @Test
    public void testListExperiments() throws Exception {
        given(experimentCRUDMock.findAllExperiments())
            .willReturn(Lists.newArrayList(new ExperimentDTO(EXPERIMENT_ACCESSION, ExperimentType.BASELINE, Sets.newHashSet("mouse"), Sets.newHashSet("1"), "title",
                    new GregorianCalendar(39 + 1900, 12, 12).getTime(), false, ACCESS_KEY)));

        assertThat(subject.listExperiments(null), is(
                "[{\"accessKey\":\"AN_UUID\",\"experimentAccession\":\"EXPERIMENT_ACCESSION\",\"experimentType\":\"BASELINE\",\"lastUpdate\":\"Jan 12, 1940 12:00:00 AM\",\"isPrivate\":false,\"species\":[\"mouse\"],\"pubmedIds\":[\"1\"],\"title\":\"title\"}]"));
    }

}