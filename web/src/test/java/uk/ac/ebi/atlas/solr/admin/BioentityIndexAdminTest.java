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

package uk.ac.ebi.atlas.solr.admin;

import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.solr.admin.index.BioentityIndex;
import uk.ac.ebi.atlas.solr.admin.monitor.BioentityIndexMonitor;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.util.concurrent.ExecutorService;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BioentityIndexAdminTest {

    private static final String BIOENTITY_PROPERTY_DIRECTORY = ".";

    @Mock
    private BioentityIndex bioentityIndexMock;

    @Mock
    private BioentityIndexMonitor bioentityIndexMonitorMock;

    private ExecutorService sameThreadExecutorService = MoreExecutors.sameThreadExecutor();

    private BioentityIndexAdmin subject;

    @Before
    public void setup(){
        subject = new BioentityIndexAdmin(bioentityIndexMock, bioentityIndexMonitorMock, BIOENTITY_PROPERTY_DIRECTORY, sameThreadExecutorService);
    }

    @Test
    public void shouldUseBioentityIndex() throws IOException, InterruptedException {

        given(bioentityIndexMonitorMock.start()).willReturn(true);

        subject.rebuildIndex();

        verify(bioentityIndexMock).deleteAll();

        verify(bioentityIndexMock).indexAll(any(DirectoryStream.class));

    }

    @Test
    public void shouldNotRunIndexingIfMonitorDoesntStart() throws IOException {

        given(bioentityIndexMonitorMock.start()).willReturn(false);

        subject.rebuildIndex();

        verify(bioentityIndexMock,never()).deleteAll();
        verify(bioentityIndexMock,never()).indexAll(any(DirectoryStream.class));

    }



}
