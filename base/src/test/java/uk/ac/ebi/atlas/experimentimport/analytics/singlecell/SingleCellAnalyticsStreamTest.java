package uk.ac.ebi.atlas.experimentimport.analytics.singlecell;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.readers.MatrixMarketReader;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.model.resource.AtlasResource;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SingleCellAnalyticsStreamTest {

    @Mock
    private AtlasResource<MatrixMarketReader> tpmsMatrixResourceMock;

    @Mock
    private MatrixMarketReader matrixMarketReaderMock;

    @Mock
    private AtlasResource<TsvReader> geneIdsResourceMock;

    @Mock
    private TsvReader geneIdsTsvReaderMock;

    @Mock
    private AtlasResource<TsvReader> cellIdsResourceMock;

    @Mock
    private TsvReader cellIdsTsvReaderMock;

    @Before
    public void setUp() throws Exception {
        when(tpmsMatrixResourceMock.get()).thenReturn(matrixMarketReaderMock);
        when(geneIdsResourceMock.get()).thenReturn(geneIdsTsvReaderMock);
        when(cellIdsResourceMock.get()).thenReturn(cellIdsTsvReaderMock);

        doNothing().when(matrixMarketReaderMock).close();
        doNothing().when(geneIdsTsvReaderMock).close();
        doNothing().when(cellIdsTsvReaderMock).close();
    }

    @Test
    public void resourcesAreClosed() {
        try (SingleCellAnalyticsStream subject =
                     new SingleCellAnalyticsStream(tpmsMatrixResourceMock, geneIdsResourceMock, cellIdsResourceMock)) {
            // Use subject
        }

        verify(matrixMarketReaderMock).close();
        verify(geneIdsTsvReaderMock).close();
        verify(cellIdsTsvReaderMock).close();
    }


}