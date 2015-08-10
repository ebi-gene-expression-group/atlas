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

package uk.ac.ebi.atlas.solr.admin.index;

import com.google.common.collect.Lists;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.mockito.AnswerWithSelf;
import uk.ac.ebi.atlas.solr.BioentityProperty;
import uk.ac.ebi.atlas.solr.admin.monitor.BioentityIndexMonitor;

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
    private SolrClient solrClientMock;

    @Mock
    private BioentityPropertiesStream propertiesStreamMock;

    @Mock
    private BioentityIndexMonitor bioentityIndexMonitorMock;

    private BioentityPropertiesStreamBuilder streamBuilderMock;

    ArrayList<BioentityProperty> bioentityProperties;

    private Path tempDirectoryPath;
    private Path tsvFilePath1;
    private Path tsvFilePath2;
    private Path nonTsvFilePath;


    private Path reactomePath;
    private Path reactomeFilePath1;


    private BioentityIndex subject;

    @Before
    public void setUp() throws Exception {
        tempDirectoryPath = Paths.get(System.getProperty("java.io.tmpdir"), "bioentity_properties/mirbase");

        tempDirectoryPath = Files.createDirectories(tempDirectoryPath);
        tsvFilePath1 = Files.createFile(tempDirectoryPath.resolve("temp-file1.tsv"));
        tsvFilePath2 = Files.createFile(tempDirectoryPath.resolve("temp-file2.tsv"));
        nonTsvFilePath = Files.createFile(tempDirectoryPath.resolve("temp-file.abc"));


        reactomePath = Paths.get(System.getProperty("java.io.tmpdir"), "bioentity_properties/reactome");
        reactomePath = Files.createDirectories(reactomePath);
        reactomeFilePath1 = Files.createFile(reactomePath.resolve("react-file1.tsv"));

        streamBuilderMock = mock(BioentityPropertiesStreamBuilder.class, new AnswerWithSelf(BioentityPropertiesStreamBuilder.class));
        given(streamBuilderMock.build()).willReturn(propertiesStreamMock);
        bioentityProperties = Lists.newArrayList(mock(BioentityProperty.class));
        given(propertiesStreamMock.next()).willReturn(bioentityProperties, bioentityProperties, null);

        subject = new BioentityIndex(bioentityIndexMonitorMock, solrClientMock, streamBuilderMock);
    }

    @After
    public void tearDown() throws IOException {
        Files.delete(tsvFilePath1);
        Files.delete(tsvFilePath2);
        Files.delete(nonTsvFilePath);
        Files.delete(tempDirectoryPath);

        Files.delete(reactomeFilePath1);
        Files.delete(reactomePath);

    }

    @Test
    public void shouldInvokeDeleteOnSolrServer() throws IOException, SolrServerException {
        subject.deleteAll();

        verify(solrClientMock).deleteByQuery("*:*");
        verify(solrClientMock).commit();
    }

    @Test
    public void shouldThrowIllegalStateExceptionInCaseOfFailure() throws IOException, SolrServerException {
        given(solrClientMock.deleteByQuery(anyString())).willThrow(IOException.class);

        subject.indexAll(Files.newDirectoryStream(tempDirectoryPath));
    }

    @Test(expected = NotDirectoryException.class)
    public void indexAllShouldNotAcceptFilePath() throws IOException {

        subject.indexAll(Files.newDirectoryStream(tsvFilePath1));
    }

    @Test
    public void indexAllShouldIterateOnNestedDirectoriesAndFiles() throws IOException, SolrServerException {

        subject.indexAll(Files.newDirectoryStream(tempDirectoryPath));

        verify(streamBuilderMock, times(0)).forPath(nonTsvFilePath);

        InOrder inOrder = inOrder(streamBuilderMock);
        inOrder.verify(streamBuilderMock).forPath(tsvFilePath1);
        inOrder.verify(streamBuilderMock).build();

        inOrder = inOrder(streamBuilderMock);
        inOrder.verify(streamBuilderMock).forPath(tsvFilePath2);
        inOrder.verify(streamBuilderMock).build();

        inOrder = inOrder(bioentityIndexMonitorMock);
        inOrder.verify(bioentityIndexMonitorMock).processing(tsvFilePath1);
        inOrder.verify(bioentityIndexMonitorMock).completed(tsvFilePath1);

        inOrder = inOrder(bioentityIndexMonitorMock);
        inOrder.verify(bioentityIndexMonitorMock).processing(tsvFilePath2);
        inOrder.verify(bioentityIndexMonitorMock).completed(tsvFilePath2);

        //3 times on on tsvFilePath1 and 1 time only (because we are using the same propertiesStreamMock for both files) on tsvFilePath2
        verify(propertiesStreamMock, times(4)).next();

        verify(solrClientMock, times(2)).addBeans(bioentityProperties);

        verify(solrClientMock).optimize();
    }

    @Test(expected = IllegalStateException.class)
    public void deleteAllShouldThrowIllegalStateExceptionInCaseOfFailure() throws IOException, SolrServerException {
        given(solrClientMock.deleteByQuery(anyString())).willThrow(IOException.class);

        subject.deleteAll();
    }

    @Test
    public void findReactomeDirectory() throws Exception {

        subject.indexAll(Files.newDirectoryStream(reactomePath.getParent()));

        InOrder inOrder = inOrder(streamBuilderMock);
        inOrder.verify(streamBuilderMock).forPath(reactomeFilePath1);
        inOrder.verify(streamBuilderMock).isForReactome(true);
        inOrder.verify(streamBuilderMock).build();




    }

}
