package uk.ac.ebi.atlas.commons.readers;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class MatrixMarketReaderTest {

    private static final List<Triple<Integer, Integer, Double>> RESULTS =
            ImmutableList.of(
                    Triple.of(1, 1, 1.000e+00),
                    Triple.of(2, 2, 1.050e+01),
                    Triple.of(3, 3, 1.500e-02),
                    Triple.of(1, 4, 6.000e+00),
                    Triple.of(4, 2, 2.505e+02),
                    Triple.of(4, 4, -2.800e+02),
                    Triple.of(4, 5, 3.332e+01),
                    Triple.of(5, 5, 1.200e+01),
                    Triple.of(5, 7, 4.350e+02));

    private MatrixMarketReader subject;

    @Before
    public void setUp() throws Exception {
        subject =
                new MatrixMarketReader(
                        new InputStreamReader(
                                MatrixMarketReaderTest.class.getResourceAsStream("matrix_market_example.mtx")));
    }

    @Test
    public void getMatrixSize() {
        assertThat(subject.getRows()).isEqualTo(5);
        assertThat(subject.getColumns()).isEqualTo(7);
    }

    @Test
    public void getMatrixElements() {
        assertThat(subject.stream().collect(Collectors.toList())).containsExactlyElementsOf(RESULTS);
    }

    @Test
    public void underlyingInputStreamIsClosed() throws IOException {
        Reader readerSpy =
                spy(new InputStreamReader(
                        MatrixMarketReaderTest.class.getResourceAsStream("matrix_market_example.mtx")));

        try (MatrixMarketReader matrixMarketReader = new MatrixMarketReader(readerSpy)) {
            // Use matrixMarketReader here
            matrixMarketReader.stream();
        }

        verify(readerSpy).close();
    }

    @Test
    public void emptyMatrixProducesEmptyStream() throws IOException {
        subject = new MatrixMarketReader(
                new InputStreamReader(
                        MatrixMarketReaderTest.class.getResourceAsStream("empty_matrix.mtx")));

        assertThat(subject.stream().collect(Collectors.toList())).hasSize(0);
    }

    @Test
    public void throwsWrappedIOException() throws IOException {
        // The decorator pattern used with readers makes it very hard to have a pure mock
        Reader readerSpy =
                spy(new InputStreamReader(
                        MatrixMarketReaderTest.class.getResourceAsStream("matrix_market_example.mtx")));
        doThrow(new IOException()).when(readerSpy).close();

        assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> {
            try (MatrixMarketReader matrixMarketReader = new MatrixMarketReader(readerSpy)) {
                // Use matrixMarketReader here
                matrixMarketReader.stream();
            }
        });
    }

}
