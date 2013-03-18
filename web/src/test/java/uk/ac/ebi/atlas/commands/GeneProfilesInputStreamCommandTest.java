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
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.GeneProfileInputStreamMock;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfilesInputStreamCommandTest {

    private static final String SPECIES = "Species 1";
    private static final String GENE_QUERY = "A GENE QUERY";


    @Mock
    private SolrClient solrClientMock;

    @Mock
    private BaselineRequestContext requestContextMock;

    @Mock
    private CommandExecutor commandExecutorMock;

    private ObjectInputStream<BaselineProfile> largeInputStream;

    private GeneProfilesInputStreamCommand<GeneProfilesList, ObjectInputStream<BaselineProfile>> subject;

    public GeneProfilesInputStreamCommandTest() {
    }

    //ToDo: better to do verifications on real values that on anyX(), using anyX() could hide bugs
    @Before
    public void initializeSubject() throws Exception {

        when(requestContextMock.getGeneQuery()).thenReturn("");

        // no filtering should be done here
        when(solrClientMock.findGeneIds(GENE_QUERY, SPECIES)).thenReturn(Sets.<String>newHashSet("A GENE IDENTIFIER"));

                //a stream with 5 profile of 2 expressions
        largeInputStream = new GeneProfileInputStreamMock(5);

        subject = new GeneProfilesInputStreamCommand<>();
        subject.setSolrClient(solrClientMock);
        subject.setRequestContext(requestContextMock);
        subject.setCommandExecutor(commandExecutorMock);

    }

    @Test
    public void givenEmptyGeneQuerySolrClientFindGeneIdsShouldNotBeInvoked() throws GenesNotFoundException {

        when(requestContextMock.getGeneQuery()).thenReturn("");

        subject.apply(largeInputStream);

        verify(solrClientMock, times(0)).findGeneIds(GENE_QUERY, SPECIES);
    }


}
