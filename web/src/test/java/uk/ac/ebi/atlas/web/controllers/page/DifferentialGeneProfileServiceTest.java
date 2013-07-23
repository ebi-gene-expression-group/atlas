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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commands.RankMicroarrayProfilesCommand;
import uk.ac.ebi.atlas.commands.RankProfilesCommandFactory;
import uk.ac.ebi.atlas.commands.RankRnaSeqProfilesCommand;
import uk.ac.ebi.atlas.commands.context.*;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.model.ExperimentTrader;
import uk.ac.ebi.atlas.model.cache.differential.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.model.cache.microarray.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialGeneProfileServiceTest {

    private static final String EXPERIMENT_ACCESSION = "experimentAccession";
    private static final String IDENTIFIER = "IDENTIFIER";
    private static final double CUTOFF = 0.05;
    private static final String ARRAY_DESIGN_ACCESSION = "AFFY";
    private static final String SPECIE = "SPECIE";

    @Mock
    private ExperimentTrader experimentTraderMock;

    @Mock
    private SolrClient solrClientMock;

    @Mock
    private RankRnaSeqProfilesCommand rankRnaSeqProfilesCommandMock;

    @Mock
    private RankMicroarrayProfilesCommand rankMicroarrayProfilesCommandMock;

    @Mock
    private RnaSeqRequestContextBuilder rnaSeqRequestContextBuilderMock;

    @Mock
    private MicroarrayRequestContextBuilder microarrayRequestContextBuilderMock;

    @Mock
    private RnaSeqRequestContext rnaSeqRequestContextMock;

    @Mock
    private MicroarrayRequestContext microarrayRequestContextMock;

    @Mock
    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCacheMock;

    @Mock
    private MicroarrayExperimentsCache microarrayExperimentsCacheMock;

    @Mock
    private DifferentialExperiment differentialExperimentMock;

    @Mock
    private MicroarrayExperiment microarrayExperimentMock;

    @Mock
    private RankProfilesCommandFactory rankProfilesCommandFactoryMock;

    @Mock
    private DifferentialGeneProfileProperties differentialGeneProfilePropertiesMock;

    @Mock
    private DifferentialProfile differentialProfileMock;

    private DifferentialProfilesList differentialProfilesList = new DifferentialProfilesList(Collections.emptyList());

    private DifferentialGeneProfileService subject;

    @Before
    public void setUp() throws Exception {
        when(experimentTraderMock.getDifferentialExperimentsIdentifiers()).thenReturn(Sets.newHashSet(EXPERIMENT_ACCESSION));
        when(solrClientMock.findSpeciesForGeneId(IDENTIFIER)).thenReturn(Lists.newArrayList(SPECIE));

        when(rnaSeqDiffExperimentsCacheMock.getExperiment(EXPERIMENT_ACCESSION)).thenReturn(differentialExperimentMock);
        when(microarrayExperimentsCacheMock.getExperiment(EXPERIMENT_ACCESSION)).thenReturn(microarrayExperimentMock);

        when(differentialExperimentMock.getAccession()).thenReturn(EXPERIMENT_ACCESSION);
        when(differentialExperimentMock.getSpecies()).thenReturn(Sets.newHashSet(SPECIE));

        when(microarrayExperimentMock.getAccession()).thenReturn(EXPERIMENT_ACCESSION);
        when(microarrayExperimentMock.getArrayDesignAccessions()).thenReturn(Sets.newTreeSet(Sets.newHashSet(ARRAY_DESIGN_ACCESSION)));
        when(microarrayExperimentMock.getSpecies()).thenReturn(Sets.newHashSet(SPECIE));


        when(rankRnaSeqProfilesCommandMock.execute(any(ObjectInputStream.class), any(RequestContext.class))).thenReturn(differentialProfilesList);
        when(rankMicroarrayProfilesCommandMock.execute(any(ObjectInputStream.class), any(RequestContext.class))).thenReturn(differentialProfilesList);

        when(rnaSeqRequestContextBuilderMock.forExperiment(differentialExperimentMock)).thenReturn(rnaSeqRequestContextBuilderMock);
        when(rnaSeqRequestContextBuilderMock.withPreferences(any(DifferentialRequestPreferences.class))).thenReturn(rnaSeqRequestContextBuilderMock);
        when(rnaSeqRequestContextBuilderMock.build()).thenReturn(rnaSeqRequestContextMock);

        when(microarrayRequestContextBuilderMock.forExperiment(microarrayExperimentMock)).thenReturn(microarrayRequestContextBuilderMock);
        when(microarrayRequestContextBuilderMock.withPreferences(any(MicroarrayRequestPreferences.class))).thenReturn(microarrayRequestContextBuilderMock);
        when(microarrayRequestContextBuilderMock.build()).thenReturn(microarrayRequestContextMock);

        when(rankProfilesCommandFactoryMock.getRankRnaSeqProfilesCommand()).thenReturn(rankRnaSeqProfilesCommandMock);
        when(rankProfilesCommandFactoryMock.getRankMicroarrayProfilesCommand()).thenReturn(rankMicroarrayProfilesCommandMock);

        // to have a non-empty list
        differentialProfilesList.add(differentialProfileMock);

        subject = new DifferentialGeneProfileService(experimentTraderMock, solrClientMock,
                rnaSeqRequestContextBuilderMock, microarrayRequestContextBuilderMock,
                rnaSeqDiffExperimentsCacheMock, microarrayExperimentsCacheMock,
                rankProfilesCommandFactoryMock, differentialGeneProfilePropertiesMock);
    }

    @Test
    public void testGetDifferentialProfilesList() throws Exception {
        assertThat(subject.initDifferentialProfilesListMapForIdentifier(IDENTIFIER, CUTOFF), is(differentialGeneProfilePropertiesMock));
        verify(experimentTraderMock).getDifferentialExperimentsIdentifiers();
        verify(experimentTraderMock).getMicroarrayExperimentsIdentifiers();
        verify(rnaSeqDiffExperimentsCacheMock).getExperiment(EXPERIMENT_ACCESSION);
        verify(differentialGeneProfilePropertiesMock).clear();
        ArgumentCaptor<DifferentialProfilesList> argumentCaptor = ArgumentCaptor.forClass(DifferentialProfilesList.class);
        verify(differentialGeneProfilePropertiesMock).putDifferentialProfilesListForExperiment(eq(EXPERIMENT_ACCESSION), argumentCaptor.capture());
        assertThat((DifferentialProfile) argumentCaptor.getValue().get(0), is(differentialProfileMock));
    }

    @Test
    public void testRetrieveDifferentialProfileForExperiment() throws Exception {
        DifferentialProfilesList profilesList = subject.retrieveDifferentialProfilesForRnaSeqExperiment(EXPERIMENT_ACCESSION, IDENTIFIER, CUTOFF, SPECIE);
        assertThat(profilesList.size(), is(1));
        verify(differentialExperimentMock).getSpecies();
        verify(rnaSeqRequestContextBuilderMock).forExperiment(differentialExperimentMock);
        verify(rnaSeqRequestContextBuilderMock).withPreferences(any(DifferentialRequestPreferences.class));
        verify(rnaSeqRequestContextBuilderMock).build();
        verify(rankRnaSeqProfilesCommandMock).execute(any(ObjectInputStream.class), any(RequestContext.class));
    }

    @Test
    public void testRetrieveDifferentialProfilesForMicroarrayExperiment() throws Exception {
        Collection<DifferentialProfilesList> profilesLists = subject.retrieveDifferentialProfilesForMicroarrayExperiment(EXPERIMENT_ACCESSION, IDENTIFIER, CUTOFF, SPECIE);
        assertThat(profilesLists.size(), is(1));
        DifferentialProfilesList profilesList = profilesLists.iterator().next();
        assertThat(profilesList.size(), is(1));
        verify(microarrayExperimentMock).getSpecies();
        verify(microarrayRequestContextBuilderMock).forExperiment(microarrayExperimentMock);
        verify(microarrayRequestContextBuilderMock).withPreferences(any(MicroarrayRequestPreferences.class));
        verify(microarrayRequestContextBuilderMock).build();
        verify(rankMicroarrayProfilesCommandMock).execute(any(ObjectInputStream.class), any(RequestContext.class));
    }

    @Test
    public void testRetrieveDifferentialProfileForExperimentWrongSpecie() throws Exception {
        when(differentialExperimentMock.getSpecies()).thenReturn(Collections.<String>emptySet());
        DifferentialProfilesList profilesList = subject.retrieveDifferentialProfilesForRnaSeqExperiment(EXPERIMENT_ACCESSION, IDENTIFIER, CUTOFF, SPECIE);
        assertThat(profilesList.isEmpty(), is(true));
    }

    @Test
    public void testRetrieveDifferentialProfileForMicroarrayExperiment() throws Exception {
        when(microarrayExperimentMock.getSpecies()).thenReturn(Collections.<String>emptySet());
        Collection<DifferentialProfilesList> profilesLists = subject.retrieveDifferentialProfilesForMicroarrayExperiment(EXPERIMENT_ACCESSION, IDENTIFIER, CUTOFF, SPECIE);
        assertThat(profilesLists.isEmpty(), is(true));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetDifferentialProfilesListMultipleSpecies() throws Exception {
        when(solrClientMock.findSpeciesForGeneId(IDENTIFIER)).thenReturn(Lists.newArrayList(SPECIE, "ANOTHER"));
        subject.initDifferentialProfilesListMapForIdentifier(IDENTIFIER, CUTOFF);
    }

}