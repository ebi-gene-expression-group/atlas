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

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commands.RankRnaSeqProfilesCommand;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.geneindex.GeneQueryResponse;
import uk.ac.ebi.atlas.model.cache.differential.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialGeneProfileServiceTest {

    private static final String SPECIES = "species";
    private static final String EXPERIMENT_ACCESSION = "experimentAccession";
    private static final String IDENTIFIER = "identifier";

    @Mock
    private ApplicationProperties applicationPropertiesMock;

    @Mock
    private RankRnaSeqProfilesCommand rankRnaSeqProfilesCommandMock;

    @Mock
    private RnaSeqRequestContextBuilder rnaSeqRequestContextBuilderMock;

    @Mock
    private RnaSeqRequestContext rnaSeqRequestContextMock;

    @Mock
    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCacheMock;

    @Mock
    private DifferentialExperiment differentialExperimentMock;

    @Mock
    private DifferentialProfilesList differentialProfilesListMock;

    private DifferentialGeneProfileService subject;

    @Before
    public void setUp() throws Exception {
        when(applicationPropertiesMock.getDifferentialExperimentsIdentifiers()).thenReturn(Sets.newHashSet(EXPERIMENT_ACCESSION));
        when(rnaSeqDiffExperimentsCacheMock.getExperiment(EXPERIMENT_ACCESSION)).thenReturn(differentialExperimentMock);
        when(differentialExperimentMock.getSpecies()).thenReturn(Sets.newHashSet(SPECIES));
        when(rankRnaSeqProfilesCommandMock.execute(EXPERIMENT_ACCESSION)).thenReturn(differentialProfilesListMock);
        when(rnaSeqRequestContextBuilderMock.forExperiment(differentialExperimentMock)).thenReturn(rnaSeqRequestContextBuilderMock);
        when(rnaSeqRequestContextBuilderMock.withPreferences(any(DifferentialRequestPreferences.class))).thenReturn(rnaSeqRequestContextBuilderMock);
        when(rnaSeqRequestContextBuilderMock.build()).thenReturn(rnaSeqRequestContextMock);

        subject = new DifferentialGeneProfileService(applicationPropertiesMock, rankRnaSeqProfilesCommandMock, rnaSeqRequestContextBuilderMock,
                rnaSeqDiffExperimentsCacheMock);
    }

    @Test
    public void testGetDifferentialProfilesList() throws Exception {
        assertThat(subject.getDifferentialProfilesList(IDENTIFIER, SPECIES), is(differentialProfilesListMock));
    }

    @Test
    public void testRetrieveDifferentialProfileForExperiment() throws Exception {
        assertThat(subject.retrieveDifferentialProfileForExperiment(EXPERIMENT_ACCESSION, SPECIES), is(differentialProfilesListMock));
        verify(rnaSeqRequestContextBuilderMock).forExperiment(differentialExperimentMock);
        verify(rnaSeqRequestContextBuilderMock).withPreferences(any(DifferentialRequestPreferences.class));
        verify(rnaSeqRequestContextBuilderMock).build();
        verify(rnaSeqRequestContextMock).setGeneQueryResponse(any(GeneQueryResponse.class));
    }
}