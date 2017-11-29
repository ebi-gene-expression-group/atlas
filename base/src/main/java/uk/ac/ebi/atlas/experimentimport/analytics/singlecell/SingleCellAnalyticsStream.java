package uk.ac.ebi.atlas.experimentimport.analytics.singlecell;

import org.apache.commons.lang3.tuple.Triple;
import uk.ac.ebi.atlas.commons.readers.MatrixMarketReader;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.model.resource.AtlasResource;

import java.io.Closeable;
import java.io.IOException;
import java.util.stream.Stream;

public class SingleCellAnalyticsStream implements Closeable {

    private final MatrixMarketReader matrixMarketReader;
    private final String[] geneIds;
    private final String[] cellIds;

    public SingleCellAnalyticsStream(
            Triple<AtlasResource<MatrixMarketReader>, AtlasResource<TsvReader>, AtlasResource<TsvReader>> readers)
            throws IOException {

        matrixMarketReader = readers.getLeft().get();

        geneIds = new String[matrixMarketReader.getRows()];
        cellIds = new String[matrixMarketReader.getColumns()];

        readers.getMiddle().get().readAll().forEach(line -> geneIds[Integer.parseInt(line[0].trim()) - 1] = line[1]);
        readers.getRight().get().readAll().forEach(line -> cellIds[Integer.parseInt(line[0].trim()) - 1] = line[1]);

    }

    public Stream<SingleCellAnalytics> stream() {
        return matrixMarketReader.stream()
                .map(triple ->
                        SingleCellAnalytics.create(
                                geneIds[triple.getLeft() - 1],
                                cellIds[triple.getMiddle() - 1],
                                triple.getRight()));
    }

    @Override
    public void close() throws IOException {
        matrixMarketReader.close();
    }
}
