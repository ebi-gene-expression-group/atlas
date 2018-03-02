package uk.ac.ebi.atlas.experimentimport.analytics.singlecell;

import uk.ac.ebi.atlas.commons.readers.MatrixMarketReader;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.model.resource.AtlasResource;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class SingleCellAnalyticsStreamer implements AutoCloseable, Supplier<Stream<SingleCellAnalytics>> {
    private final MatrixMarketReader matrixMarketReader;
    private final String[] geneIds;
    private final String[] cellIds;

    public SingleCellAnalyticsStreamer(AtlasResource<MatrixMarketReader> tpmsMatrix,
                                       AtlasResource<TsvStreamer> geneIdsTsv,
                                       AtlasResource<TsvStreamer> cellIdsTsv) {

        matrixMarketReader = tpmsMatrix.get();

        geneIds = new String[matrixMarketReader.getRows()];
        cellIds = new String[matrixMarketReader.getColumns()];

        try (TsvStreamer geneIdsTsvReader = geneIdsTsv.get(); TsvStreamer cellIdsTsvReader = cellIdsTsv.get()) {
            geneIdsTsvReader.get().forEach(line -> geneIds[Integer.parseInt(line[0].trim()) - 1] = line[1]);
            cellIdsTsvReader.get().forEach(line -> cellIds[Integer.parseInt(line[0].trim()) - 1] = line[1]);
        }
    }

    @Override
    public Stream<SingleCellAnalytics> get() {
        return matrixMarketReader.stream()
                .map(triple ->
                        SingleCellAnalytics.create(
                                geneIds[triple.getLeft() - 1],
                                cellIds[triple.getMiddle() - 1],
                                triple.getRight()));
    }

    @Override
    public void close() {
        matrixMarketReader.close();
    }
}
