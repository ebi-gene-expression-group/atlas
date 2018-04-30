package uk.ac.ebi.atlas.experimentimport.analytics.singlecell.analytics;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.readers.MatrixMarketReader;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.model.resource.AtlasResource;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnalyticsStreamerTest {

    @Mock
    private AtlasResource<MatrixMarketReader> tpmsMatrixResourceMock;

    @Mock
    private MatrixMarketReader matrixMarketReaderMock;

    @Mock
    private AtlasResource<TsvStreamer> geneIdsResourceMock;

    @Mock
    private TsvStreamer geneIdsTsvStreamerMock;

    @Mock
    private AtlasResource<TsvStreamer> cellIdsResourceMock;

    @Mock
    private TsvStreamer cellIdsTsvStreamerMock;

    @Before
    public void setUp() throws Exception {
        when(tpmsMatrixResourceMock.get()).thenReturn(matrixMarketReaderMock);
        when(geneIdsResourceMock.get()).thenReturn(geneIdsTsvStreamerMock);
        when(cellIdsResourceMock.get()).thenReturn(cellIdsTsvStreamerMock);

        doNothing().when(matrixMarketReaderMock).close();
        doNothing().when(geneIdsTsvStreamerMock).close();
        doNothing().when(cellIdsTsvStreamerMock).close();
    }

    @Test
    public void resourcesAreClosed() {
        try (AnalyticsStreamer subject =
                     new AnalyticsStreamer(tpmsMatrixResourceMock, geneIdsResourceMock, cellIdsResourceMock)) {
            // Use subject
        }

        verify(matrixMarketReaderMock).close();
        verify(geneIdsTsvStreamerMock).close();
        verify(cellIdsTsvStreamerMock).close();
    }


}