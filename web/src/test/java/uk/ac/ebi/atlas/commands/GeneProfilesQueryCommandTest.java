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
import uk.ac.ebi.atlas.geneindex.GeneQueryTokenizer;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.GeneProfileInputStreamMock;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfilesQueryCommandTest {

    private static final String SPECIES = "Species 1";
    private static final String GENE_QUERY = "A QUERY";
    public static final String A_GENE_IDENTIFIER = "A GENE IDENTIFIER";

    @Mock
    private GeneQueryTokenizer geneQueryTokenizerMock;

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

    //ToDo: better to do verifications on real values than on anyX(), using anyX() could hide bugs
    @Before
    public void initializeSubject() throws Exception {

        when(requestContextMock.getGeneQuery()).thenReturn("");

        // no filtering should be done here
        when(solrClientMock.findGeneIds(GENE_QUERY, false, SPECIES)).thenReturn(Sets.<String>newHashSet(A_GENE_IDENTIFIER));
        when(solrClientMock.findGeneIds("A", false, SPECIES)).thenReturn(Sets.<String>newHashSet(A_GENE_IDENTIFIER));
        when(solrClientMock.findGeneIds("QUERY", false, SPECIES)).thenReturn(Sets.<String>newHashSet(A_GENE_IDENTIFIER));

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
        subject.setSolrClient(solrClientMock, geneQueryTokenizerMock);

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

        verify(solrClientMock).findGeneIds(GENE_QUERY, false, SPECIES);
        assertThat(selectedGeneIds, hasItem(A_GENE_IDENTIFIER));
    }

}
