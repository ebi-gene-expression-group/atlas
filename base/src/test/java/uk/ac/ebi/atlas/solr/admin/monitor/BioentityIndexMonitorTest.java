
package uk.ac.ebi.atlas.solr.admin.monitor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BioentityIndexMonitorTest {

    @Mock
    private IndexingProgress indexingProgressMock;

    private BioentityIndexMonitor subject;

    @Before
    public void init(){
        subject = new BioentityIndexMonitor(indexingProgressMock);
    }

    @Test
    public void startShouldResetIndexingProgress(){
        subject.start();
        verify(indexingProgressMock).reset();
    }



}
