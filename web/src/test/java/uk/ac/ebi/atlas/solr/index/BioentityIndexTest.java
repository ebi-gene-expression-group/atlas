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

package uk.ac.ebi.atlas.solr.index;

import com.google.common.collect.Lists;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.mockito.AnswerWithSelf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BioentityIndexTest {

    @Mock
    private SolrServer solrServerMock;

    @Mock
    private BioentityPropertiesStream propertiesStreamMock;

    @Mock
    private BioentityIndexMonitor bioentityIndexMonitorMock;

    private BioentityPropertiesStreamBuilder streamBuilderMock;

    ArrayList<BioentityProperty> bioentityProperties;

    private Path tempDirectoryPath;
    private Path tempFilePath1;
    private Path tempFilePath2;

    private BioentityIndex subject;

    @Before
    public void setUp() throws Exception {
        tempDirectoryPath = Paths.get(System.getProperty("java.io.tmpdir"), "bioentity-properties/mirbase");

        tempDirectoryPath = Files.createDirectories(tempDirectoryPath);
        tempFilePath1 = Files.createFile(tempDirectoryPath.resolve("temp-file1.tmp"));
        tempFilePath2 = Files.createFile(tempDirectoryPath.resolve("temp-file2.tmp"));

        streamBuilderMock = mock(BioentityPropertiesStreamBuilder.class, new AnswerWithSelf(BioentityPropertiesStreamBuilder.class));
        given(streamBuilderMock.build()).willReturn(propertiesStreamMock);
        bioentityProperties = Lists.newArrayList(mock(BioentityProperty.class));
        given(propertiesStreamMock.next()).willReturn(bioentityProperties, bioentityProperties, null);

        subject = new BioentityIndex(bioentityIndexMonitorMock, solrServerMock, streamBuilderMock);
    }

    @After
    public void tearDown() throws IOException {
        Files.delete(tempFilePath1);
        Files.delete(tempFilePath2);
        Files.delete(tempDirectoryPath);
    }

    @Test
    public void shouldInvokeDeleteOnSolrServer() throws IOException, SolrServerException {
        subject.deleteAll();

        verify(solrServerMock).deleteByQuery("*:*");
    }

    @Test
    public void shouldThrowIllegalStateExceptionInCaseOfFailure() throws IOException, SolrServerException {
        given(solrServerMock.deleteByQuery(anyString())).willThrow(IOException.class);

        subject.indexAll(Files.newDirectoryStream(tempDirectoryPath));
    }

    @Test(expected = NotDirectoryException.class)
    public void indexAllShouldNotAcceptFilePath() throws IOException {

        subject.indexAll(Files.newDirectoryStream(tempFilePath1));
    }

    @Test
    public void indexAllShouldIterateOnNestedDirectoriesAndFiles() throws IOException, SolrServerException {

        subject.indexAll(Files.newDirectoryStream(tempDirectoryPath));

        InOrder inOrder = inOrder(streamBuilderMock);
        inOrder.verify(streamBuilderMock).forPath(tempFilePath1);
        inOrder.verify(streamBuilderMock).build();
        inOrder.verify(streamBuilderMock).forPath(tempFilePath2);
        inOrder.verify(streamBuilderMock).build();

        inOrder = inOrder(bioentityIndexMonitorMock);
        inOrder.verify(bioentityIndexMonitorMock).processing(tempFilePath1);
        inOrder.verify(bioentityIndexMonitorMock).completed(tempFilePath1);
        inOrder.verify(bioentityIndexMonitorMock).processing(tempFilePath2);
        inOrder.verify(bioentityIndexMonitorMock).completed(tempFilePath2);

        //3 times on on tempFilePath1 and 1 time only (because streamMock is exhausted) on tempFilePath2
        verify(propertiesStreamMock, times(4)).next();

        verify(solrServerMock,times(2)).addBeans(bioentityProperties);
    }

    @Test(expected = IllegalStateException.class)
    public void deleteAllShouldThrowIllegalStateExceptionInCaseOfFailure() throws IOException, SolrServerException {
        given(solrServerMock.deleteByQuery(anyString())).willThrow(IOException.class);

        subject.deleteAll();
    }

    @Test
    public void commitShouldCommitAndStopTheMonitor() throws IOException, SolrServerException {
        subject.optimize();

        verify(solrServerMock, only()).optimize();

    }

}
