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

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
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
    //ToDo: 20 lines of setup for a 2 lines test?
    @Before
    public void initializeSubject() throws Exception {

        Multimap<String, String> geneSets = ArrayListMultimap.create();
        geneSets.put(GENE_QUERY, A_GENE_IDENTIFIER);

        // no filtering should be done here
        when(solrClientMock.findGeneSets(GENE_QUERY, false, SPECIES, false)).thenReturn(geneSets);
        when(solrClientMock.findGeneSets("A", false, SPECIES, false)).thenReturn(geneSets);
        when(solrClientMock.findGeneSets("QUERY", false, SPECIES, false)).thenReturn(geneSets);

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

        subject.execute("");

        verify(solrClientMock, times(0)).findGeneSets(GENE_QUERY, false, SPECIES, false);
    }

}
