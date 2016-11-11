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
        subject = new BioentityIndexAdmin(bioentityIndexMonitorMock, BIOENTITY_PROPERTY_DIRECTORY, sameThreadExecutorService);
        subject.setBioentityIndex(bioentityIndexMock);
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
