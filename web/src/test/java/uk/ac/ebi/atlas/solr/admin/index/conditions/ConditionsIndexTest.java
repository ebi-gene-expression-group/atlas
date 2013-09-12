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

package uk.ac.ebi.atlas.solr.admin.index.conditions;

import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.ExperimentTrader;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConditionsIndexTest {

    private static final String EXPERIMENT_ACCESSION = "AN_ACCESSION";

    private ConditionsIndex subject;

    @Mock
    private SolrServer solrServerMock;
    @Mock
    private ConditionPropertiesBuilder conditionsPropertiesBuilderMock;
    @Mock
    private ExperimentTrader experimentTraderMock;
    @Mock
    private DifferentialExperiment differentialExperimentMock;

    @Before
    public void setUp() throws Exception {
        given(experimentTraderMock.getPublicExperiment(EXPERIMENT_ACCESSION)).willReturn(differentialExperimentMock);
        given(conditionsPropertiesBuilderMock.buildProperties(differentialExperimentMock)).willReturn(CollectionUtils.EMPTY_COLLECTION);
        subject = new ConditionsIndex(solrServerMock, conditionsPropertiesBuilderMock, experimentTraderMock);
    }

    @Test
    public void updateConditionsShouldReindexDiffExperiment() throws Exception {
        subject.updateConditions(EXPERIMENT_ACCESSION);

        ArgumentCaptor<String> deleteQueryCaptor = ArgumentCaptor.forClass(String.class);

        verify(solrServerMock).deleteByQuery(deleteQueryCaptor.capture());
        assertThat(deleteQueryCaptor.getValue(), endsWith(EXPERIMENT_ACCESSION));

        verify(experimentTraderMock).getPublicExperiment(EXPERIMENT_ACCESSION);

        verify(conditionsPropertiesBuilderMock).buildProperties(differentialExperimentMock);

        verify(solrServerMock).addBeans(CollectionUtils.EMPTY_COLLECTION);
    }

    @Test
    public void updateConditionsShouldNotIndexBaselineExperiment() throws Exception {
        given(experimentTraderMock.getPublicExperiment(EXPERIMENT_ACCESSION)).willReturn(mock(BaselineExperiment.class));

        subject.updateConditions(EXPERIMENT_ACCESSION);

        verify(solrServerMock, times(0)).addBeans(anyCollection());

    }

}
