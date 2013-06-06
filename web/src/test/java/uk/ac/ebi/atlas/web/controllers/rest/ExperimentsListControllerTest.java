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

package uk.ac.ebi.atlas.web.controllers.rest;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.utils.ExperimentInfo;
import uk.ac.ebi.atlas.utils.ExperimentInfoListBuilder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentsListControllerTest {

    private static final String EXPERIMENT_ACCESSION = "ACCESSION";
    private static final String EXPECTED_JSON = "{\"aaData\":[{\"experimentAccession\":\"" + EXPERIMENT_ACCESSION +
            "\",\"numberOfAssays\":0,\"numberOfContrasts\":0,\"species\":[],\"experimentalFactors\":[],\"arrayDesigns\":[]}]}";

    @Mock
    private ExperimentInfoListBuilder experimentInfoListBuilderMock;

    private ExperimentInfo experimentInfo = new ExperimentInfo();

    private ExperimentsListController subject;

    @Before
    public void setUp() throws Exception {

        experimentInfo.setExperimentAccession(EXPERIMENT_ACCESSION);

        when(experimentInfoListBuilderMock.build()).thenReturn(Lists.newArrayList(experimentInfo));

        subject = new ExperimentsListController(experimentInfoListBuilderMock);
    }

    @Test
    public void testGetExperimentsList() throws Exception {
        assertThat(subject.getExperimentsList(), is(EXPECTED_JSON));
    }
}