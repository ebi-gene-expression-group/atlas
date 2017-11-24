package uk.ac.ebi.atlas.experimentimport.analytics.singlecell;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.resource.MockDataFileHub;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.StringUtils.leftPad;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SingleCellAnalyticsStreamFactoryIT {

    static final String EXPERIMENT_ACCESSION = "TEST-SINGLE-CELL";

    static final int MAX_ROWS = 100;
    static final int MAX_COLS = 500;
    static final double MAX_EXPRESSION  = 10000000.0;
    static final double SPARSE_FACTOR = 0.85;

    private Triple<Collection<Triple>, String[], String[]> randomMatrixMarketGenerator() {
        String[] geneIds =
                IntStream.range(0, ThreadLocalRandom.current().nextInt(1, MAX_ROWS + 1)).boxed()
                        .map(i -> "ENSG" + leftPad(Integer.toString(i), 10, '0'))
                        .toArray(String[]::new);
        String[] cellIds =
                IntStream.range(0, ThreadLocalRandom.current().nextInt(1, MAX_COLS + 1)).boxed()
                        .map(i -> "CELL_" + Integer.toString(i))
                        .toArray(String[]::new);

        ImmutableList.Builder<Triple> matrixBuilder = ImmutableList.builder();
        for (int i = 0 ; i < geneIds.length ; i++) {
            for (int j = 0 ; j < cellIds.length ; j++) {
                if (ThreadLocalRandom.current().nextDouble() > SPARSE_FACTOR) {
                    matrixBuilder.add(
                            Triple.of(i + 1, j + 1, ThreadLocalRandom.current().nextDouble(0.1, MAX_EXPRESSION)));
                }
            }
        }

        return Triple.of(matrixBuilder.build(), geneIds, cellIds);
    }

    MockDataFileHub dataFileHub = MockDataFileHub.create();
    SingleCellAnalyticsStreamFactory subject = new SingleCellAnalyticsStreamFactory(dataFileHub);

    @Test
    public void readsAllEntries() throws Exception {
        Triple<Collection<Triple>, String[], String[]> matrixMarketFiles = randomMatrixMarketGenerator();

        Collection<Triple> matrixEntries = matrixMarketFiles.getLeft();
        List<String> geneIds = ImmutableList.copyOf(matrixMarketFiles.getMiddle());
        List<String> cellIds = ImmutableList.copyOf(matrixMarketFiles.getRight());

        dataFileHub.addMatrixMarketExpressionFiles(
                EXPERIMENT_ACCESSION, matrixEntries, matrixMarketFiles.getMiddle(), matrixMarketFiles.getRight());

        SingleCellAnalyticsStream singleCellAnalyticsStream = subject.create(EXPERIMENT_ACCESSION);

        singleCellAnalyticsStream.stream().forEach(
                sca -> assertThat(
                        matrixEntries,
                        hasItem(
                                Triple.of(
                                        geneIds.indexOf(sca.geneId()) + 1,
                                        cellIds.indexOf(sca.cellId()) + 1,
                                        sca.expressionLevel()))));
    }
}