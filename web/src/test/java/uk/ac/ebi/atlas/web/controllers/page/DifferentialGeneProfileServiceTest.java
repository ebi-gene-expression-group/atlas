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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commands.RankProfilesCommandFactory;
import uk.ac.ebi.atlas.commands.RankRnaSeqProfilesCommand;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.model.cache.differential.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesListMap;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialGeneProfileServiceTest {

    private static final String SPECIES = "species";
    private static final String EXPERIMENT_ACCESSION = "experimentAccession";
    private static final String IDENTIFIER = "identifier";
    private static final double CUTOFF = 0.05;
    private static final String CONTRAST1 = "contrast1";
    private static final String CONTRAST2 = "contrast2";

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
    private RankProfilesCommandFactory rankProfilesCommandFactoryMock;

    @Mock
    private DifferentialRequestPreferences differentialRequestPreferencesMock;

    @Mock
    private DifferentialProfilesListMap differentialProfilesListMapMock;

    @Mock
    private DifferentialProfile differentialProfileMock;

    private DifferentialProfilesList differentialProfilesList = new DifferentialProfilesList(Collections.emptyList());

    private DifferentialGeneProfileService subject;

    @Before
    public void setUp() throws Exception {
        when(applicationPropertiesMock.getDifferentialExperimentsIdentifiers()).thenReturn(Sets.newHashSet(EXPERIMENT_ACCESSION));

        when(rnaSeqDiffExperimentsCacheMock.getExperiment(EXPERIMENT_ACCESSION)).thenReturn(differentialExperimentMock);

        when(differentialExperimentMock.getContrastIds()).thenReturn(Sets.newTreeSet(Sets.newHashSet(CONTRAST1, CONTRAST2)));
        when(differentialExperimentMock.getAccession()).thenReturn(EXPERIMENT_ACCESSION);

        when(rankRnaSeqProfilesCommandMock.execute(EXPERIMENT_ACCESSION)).thenReturn(differentialProfilesList);

        when(rnaSeqRequestContextBuilderMock.forExperiment(differentialExperimentMock)).thenReturn(rnaSeqRequestContextBuilderMock);
        when(rnaSeqRequestContextBuilderMock.withPreferences(any(DifferentialRequestPreferences.class))).thenReturn(rnaSeqRequestContextBuilderMock);
        when(rnaSeqRequestContextBuilderMock.build()).thenReturn(rnaSeqRequestContextMock);

        when(rankProfilesCommandFactoryMock.getRankRnaSeqProfilesCommand()).thenReturn(rankRnaSeqProfilesCommandMock);

        // to have a non-empty list
        differentialProfilesList.add(differentialProfileMock);

        subject = new DifferentialGeneProfileService(applicationPropertiesMock,
                rnaSeqRequestContextBuilderMock, rnaSeqDiffExperimentsCacheMock,
                rankProfilesCommandFactoryMock, differentialProfilesListMapMock);
    }

    @Test
    public void testGetDifferentialProfilesList() throws Exception {
        assertThat(subject.getDifferentialProfilesListMapForIdentifier(IDENTIFIER, CUTOFF), is(differentialProfilesListMapMock));
        verify(applicationPropertiesMock).getDifferentialExperimentsIdentifiers();
        verify(differentialProfilesListMapMock).clear();
        ArgumentCaptor<DifferentialProfilesList> argumentCaptor = ArgumentCaptor.forClass(DifferentialProfilesList.class);
        verify(differentialProfilesListMapMock).putDifferentialProfilesListForExperiment(eq(EXPERIMENT_ACCESSION), argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().size(), is(2));
    }

    @Test
    public void testRetrieveDifferentialProfileForExperiment() throws Exception {
        DifferentialProfilesList profilesList = subject.retrieveDifferentialProfilesForExperiment(EXPERIMENT_ACCESSION, IDENTIFIER, CUTOFF);
        // size is two because of two contrasts
        assertThat(profilesList.size(), is(2));
        verify(rnaSeqDiffExperimentsCacheMock).getExperiment(EXPERIMENT_ACCESSION);
    }

    @Test
    public void testExecuteForExperimentAndContrast() throws Exception {
        DifferentialProfilesList profilesList = subject.executeForExperimentAndAllContrasts(differentialExperimentMock, differentialRequestPreferencesMock);
        // size is two because of two contrasts
        assertThat(profilesList.size(), is(2));
        verify(differentialExperimentMock).getContrastIds();
        verify(differentialRequestPreferencesMock).setQueryFactorValues(Sets.newTreeSet(Sets.newHashSet(CONTRAST1)));
        verify(differentialRequestPreferencesMock).setQueryFactorValues(Sets.newTreeSet(Sets.newHashSet(CONTRAST2)));
        verify(differentialExperimentMock, times(2)).getAccession();
        verify(rnaSeqRequestContextBuilderMock, times(2)).forExperiment(differentialExperimentMock);
        verify(rnaSeqRequestContextBuilderMock, times(2)).withPreferences(any(DifferentialRequestPreferences.class));
        verify(rnaSeqRequestContextBuilderMock, times(2)).build();
        verify(rankRnaSeqProfilesCommandMock, times(2)).execute(EXPERIMENT_ACCESSION);
    }

}