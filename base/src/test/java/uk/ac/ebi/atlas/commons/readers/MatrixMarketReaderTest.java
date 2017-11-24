package uk.ac.ebi.atlas.commons.readers;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class MatrixMarketReaderTest {

    static final Triple[] RESULTS = new Triple[] {
            Triple.of(1, 1, 1.000e+00),
            Triple.of(2, 2, 1.050e+01),
            Triple.of(3, 3, 1.500e-02),
            Triple.of(1, 4, 6.000e+00),
            Triple.of(4, 2, 2.505e+02),
            Triple.of(4, 4, -2.800e+02),
            Triple.of(4, 5, 3.332e+01),
            Triple.of(5, 5, 1.200e+01),
            Triple.of(5, 7, 4.350e+02)};

    MatrixMarketReader subject;

    @Before
    public void setUp() throws Exception {
        subject =
                new MatrixMarketReader(MatrixMarketReaderTest.class.getResourceAsStream("matrix_market_example.mtx"));
    }

    @Test
    public void getMatrixSize() {
        assertThat(subject.getRows(), is(5));
        assertThat(subject.getColumns(), is (7));
        assertThat(subject.getElements(), is(9));
    }

    @Test
    public void getMatrixElements() {
        assertThat(subject.list(), contains(RESULTS));
    }

    @Test
    public void streamElements() {
        assertThat(subject.stream().collect(Collectors.toList()), contains(RESULTS));
    }

    @Test
    public void underlyingInputStreamIsClosed() throws Exception {
        InputStream in = spy(MatrixMarketReaderTest.class.getResourceAsStream("matrix_market_example.mtx"));

        try (MatrixMarketReader matrixMarketReader = new MatrixMarketReader(in)) {
            // Use matrixMarketReader here
        }

        verify(in).close();
    }

}
