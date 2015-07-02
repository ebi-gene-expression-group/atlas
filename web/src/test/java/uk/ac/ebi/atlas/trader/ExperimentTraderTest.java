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

package uk.ac.ebi.atlas.trader;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.trader.cache.*;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentTraderTest {

    private static final String E_TABM_713 = "E-TABM-713";
    private static final String E_MTAB_513 = "E-MTAB-513";
    private static final String E_MTAB_599 = "E-MTAB-599";
    private static final String E_MTAB_1066 = "E-MTAB-1066";
    private static final String E_GEOD_43049 = "E-GEOD-43049";
    private static final String E_GEOD_22351 = "E-GEOD-22351";
    private static final String E_GEOD_38400 = "E-GEOD-38400";
    private static final String E_GEOD_21860 = "E-GEOD-21860";
    private static final String E_PROT_1 = "E-PROT-1";

    private ExperimentTrader subject;

    @Mock
    private ExperimentDAO experimentDAOMock;
    @Mock
    private ApplicationProperties applicationPropertiesMock;
    @Mock
    private BaselineExperimentsCache baselineExperimentsCacheMock;
    @Mock
    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCacheMock;
    @Mock
    private MicroarrayExperimentsCache microarrayExperimentsCacheMock;
    @Mock
    private ProteomicsBaselineExperimentsCache proteomicsBaselineExperimentsCacheMock;
    @Mock
    private ExperimentTypesCache experimentTypesCacheMock;

    @Mock
    ExperimentDTO experimentDTOMock;



    @Before
    public void initSubject(){
        when(experimentDAOMock.findPublicExperimentAccessions(ExperimentType.RNASEQ_MRNA_BASELINE)).thenReturn(Sets.newHashSet(E_MTAB_513, E_MTAB_599));

        when(experimentDAOMock.findPublicExperimentAccessions(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL)).thenReturn(Sets.newHashSet(E_GEOD_22351, E_GEOD_38400, E_GEOD_21860));

        when(experimentDAOMock.findPublicExperimentAccessions(ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL)).thenReturn(Sets.newHashSet(E_MTAB_1066));

        when(experimentDAOMock.findPublicExperimentAccessions(ExperimentType.MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL)).thenReturn(Sets.newHashSet(E_GEOD_43049));

        when(experimentDAOMock.findPublicExperimentAccessions(ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL)).thenReturn(Sets.newHashSet(E_TABM_713));

        when(experimentDAOMock.findPublicExperimentAccessions(ExperimentType.PROTEOMICS_BASELINE)).thenReturn(Sets.newHashSet(E_PROT_1));

        subject = new ExperimentTrader(experimentDAOMock,
                                        baselineExperimentsCacheMock,
                                        rnaSeqDiffExperimentsCacheMock,
                                        microarrayExperimentsCacheMock,
                                        proteomicsBaselineExperimentsCacheMock,
                                        experimentTypesCacheMock);
    }

    @Test
    public void testGetBaselineExperimentsIdentifiers() throws Exception {
        assertThat(subject.getBaselineExperimentAccessions(), containsInAnyOrder(E_MTAB_513, E_MTAB_599));
    }

    @Test
    public void testGetDifferentialExperimentsIdentifiers() throws Exception {
        assertThat(subject.getRnaSeqDifferentialExperimentAccessions(), containsInAnyOrder(E_GEOD_22351, E_GEOD_38400, E_GEOD_21860));
    }

    @Test
    public void testGetMicroarrayExperimentsIdentifiers() throws Exception {
        assertThat(subject.getMicroarrayExperimentAccessions(), containsInAnyOrder(E_MTAB_1066, E_GEOD_43049, E_TABM_713));
    }

    @Test
    public void getExperimentShouldUseTheCache(){
        given(experimentTypesCacheMock.getExperimentType(E_GEOD_21860)).willReturn(ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL);
        subject.getPublicExperiment(E_GEOD_21860);
        verify(baselineExperimentsCacheMock,times(0)).getExperiment(E_GEOD_21860);
        verify(rnaSeqDiffExperimentsCacheMock, times(0)).getExperiment(E_GEOD_21860);
        verify(microarrayExperimentsCacheMock).getExperiment(E_GEOD_21860);
    }

    @Test
    public void getAllBaselineExperiments() {
        assertThat(subject.getAllBaselineExperimentAccessions(), containsInAnyOrder(E_MTAB_513, E_MTAB_599, E_PROT_1));
    }

}
