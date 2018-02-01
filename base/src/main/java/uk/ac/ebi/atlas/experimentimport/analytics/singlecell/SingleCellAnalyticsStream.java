package uk.ac.ebi.atlas.experimentimport.analytics.singlecell;

import uk.ac.ebi.atlas.commons.readers.MatrixMarketReader;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.model.resource.AtlasResource;

import java.util.stream.Stream;

public class SingleCellAnalyticsStream implements AutoCloseable {

    private final MatrixMarketReader matrixMarketReader;
    private final String[] geneIds;
    private final String[] cellIds;

    public SingleCellAnalyticsStream(AtlasResource<MatrixMarketReader> tpmsMatrix,
                                     AtlasResource<TsvReader> geneIdsTsv,
                                     AtlasResource<TsvReader> cellIdsTsv) {

        matrixMarketReader = tpmsMatrix.get();

        geneIds = new String[matrixMarketReader.getRows()];
        cellIds = new String[matrixMarketReader.getColumns()];

        geneIdsTsv.get().readAll().forEach(line -> geneIds[Integer.parseInt(line[0].trim()) - 1] = line[1]);
        cellIdsTsv.get().readAll().forEach(line -> cellIds[Integer.parseInt(line[0].trim()) - 1] = line[1]);

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
    public void close() {
        matrixMarketReader.close();
    }
}
