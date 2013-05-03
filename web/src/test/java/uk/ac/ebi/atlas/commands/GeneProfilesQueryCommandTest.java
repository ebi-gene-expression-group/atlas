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

package uk.ac.ebi.atlas.commands;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commands.context.RequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.GeneProfileInputStreamMock;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfilesQueryCommandTest {

    private static final String SPECIES = "Species 1";
    private static final String GENE_QUERY = "A GENE QUERY";
    public static final String A_GENE_IDENTIFIER = "A GENE IDENTIFIER";


    @Mock
    private SolrClient solrClientMock;

    @Mock
    private BaselineRequestContext requestContextMock;

    @Mock
    private Command commandMock;

    private ObjectInputStream<BaselineProfile> largeInputStream;

    private GeneProfilesQueryCommand<GeneProfilesList, BaselineProfile> subject;

    public GeneProfilesQueryCommandTest() {
    }

    //ToDo: better to do verifications on real values that on anyX(), using anyX() could hide bugs
    @Before
    public void initializeSubject() throws Exception {

        when(requestContextMock.getGeneQuery()).thenReturn("");

        // no filtering should be done here
        when(solrClientMock.findGeneIds(GENE_QUERY, false, SPECIES)).thenReturn(Sets.<String>newHashSet(A_GENE_IDENTIFIER));
        when(solrClientMock.findGeneIds(GENE_QUERY.split(" ")[0], false, SPECIES)).thenReturn(Sets.<String>newHashSet(A_GENE_IDENTIFIER));

        //a stream with 5 profile of 2 expressions
        largeInputStream = new GeneProfileInputStreamMock(5);

        subject = new GeneProfilesQueryCommand<GeneProfilesList, BaselineProfile>(requestContextMock) {
            @Override
            protected ObjectInputStream<BaselineProfile> createInputStream(String experimentAccession) {
                return largeInputStream;
            }

            @Override
            protected GeneProfilesList execute(ObjectInputStream<BaselineProfile> inputStream, RequestContext requestContext) {
                return null;
            }
        };
        subject.setSolrClient(solrClientMock);

    }

    @Test
    public void givenEmptyGeneQuerySolrClientFindGeneIdsShouldNotBeInvoked() throws GenesNotFoundException {

        when(requestContextMock.getGeneQuery()).thenReturn("");

        subject.execute("");

        verify(solrClientMock, times(0)).findGeneIds(GENE_QUERY, false, SPECIES);
    }

    @Test
    public void testGetSelectedGeneIds() throws GenesNotFoundException {

        when(requestContextMock.getGeneQuery()).thenReturn(GENE_QUERY);
        when(requestContextMock.getFilteredBySpecies()).thenReturn(SPECIES);

        Set<String> selectedGeneIds = subject.getSelectedGeneIds();

        verify(solrClientMock, times(1)).findGeneIds(GENE_QUERY, false, SPECIES);
        assertThat(selectedGeneIds, hasItem(A_GENE_IDENTIFIER));
    }

    @Test
    public void testGetSelectedGeneIdsPerQueryToken() throws GenesNotFoundException {

        when(requestContextMock.getGeneQuery()).thenReturn(GENE_QUERY);
        when(requestContextMock.getFilteredBySpecies()).thenReturn(SPECIES);

        Map<String, Set<String>> selectedGeneIdsPerQueryToken = subject.getSelectedGeneIdsPerQueryToken();

        String[] split = GENE_QUERY.split(" ");
        verify(solrClientMock, times(1)).findGeneIds(split[0], false, SPECIES);
        verify(solrClientMock, times(1)).findGeneIds(split[1], false, SPECIES);
        verify(solrClientMock, times(1)).findGeneIds(split[2], false, SPECIES);

        assertThat(selectedGeneIdsPerQueryToken.size(), is(3));
        assertThat(selectedGeneIdsPerQueryToken.keySet(), hasItems(split[0], split[1], split[2]));
        assertThat(selectedGeneIdsPerQueryToken.get(split[0]), hasItem(A_GENE_IDENTIFIER));
        assertThat(selectedGeneIdsPerQueryToken.get(split[1]).size(), is(0));
        assertThat(selectedGeneIdsPerQueryToken.get(split[2]).size(), is(0));
    }


}
