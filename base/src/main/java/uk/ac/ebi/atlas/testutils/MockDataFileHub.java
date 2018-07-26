package uk.ac.ebi.atlas.testutils;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.Triple;
import uk.ac.ebi.atlas.resource.DataFileHub;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.leftPad;

// I belong in the test dir, but here I can be shared among different modules without the need of creating a test jar
public class MockDataFileHub extends DataFileHub {
    private final Path basePath;

    private MockDataFileHub() throws IOException {
        super(Files.createTempDirectory(""));
        Files.createDirectory(experimentsMageTabDirLocation);
        Files.createDirectory(experimentsDesignDirLocation);
        Files.createDirectory(experimentsAdminDirLocation);
        basePath = experimentsMageTabDirLocation.getParent();
        basePath.toFile().deleteOnExit();
    }

    public static MockDataFileHub create() {
        try {
            return new MockDataFileHub();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addTemporaryMatrixMarket(Path where, int rows, int columns, Collection<Triple> lines) {
        ImmutableList<String> preamble =
                ImmutableList.of(
                        "%%MatrixMarket matrix coordinate real general",
                        String.format("%d %d %d", rows, columns, lines.size()));

        addTemporaryFile(
                where,
                Stream.concat(
                        preamble.stream(),
                        lines.stream().map(line -> line.toString("%1$s %2$s %3$s"))).collect(Collectors.toList()));
    }

    private void addTemporaryTsv(Path where, Collection<String[]> lines) {
        addTemporaryFile(
                where,
                lines.stream()
                        .map(line -> Arrays.stream(line).collect(Collectors.joining("\t")))
                        .collect(Collectors.toList()));
    }

    private void addTemporaryFile(Path where, Collection<String> lines) {
        File f = where.toFile();
        f.deleteOnExit();
        f.getParentFile().mkdirs();
        try {
            f.createNewFile();
            Files.write(f.toPath(), lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void addTemporaryFile(String relativeLocation, Collection<String> lines) {
        addTemporaryFile(basePath.resolve(relativeLocation), lines);
    }

    public void addMatrixMarketExpressionFiles(Path accession,
                                               Collection<Triple> lines,
                                               String[] geneIds,
                                               String[] cellIds) {
        addTemporaryMatrixMarket(
                experimentsMageTabDirLocation.resolve(
                        MessageFormat.format(SINGLE_CELL_MATRIX_MARKET_TPMS_FILE_PATH_TEMPLATE, accession)),
                geneIds.length, cellIds.length, lines);
        addTemporaryTsv(
                experimentsMageTabDirLocation.resolve(
                        MessageFormat.format(SINGLE_CELL_MATRIX_MARKET_TPMS_GENE_IDS_FILE_PATH_TEMPLATE, accession)),
                IntStream.range(0, geneIds.length).boxed()
                        .map(i -> new String[] {leftPad(Integer.toString(i + 1), Integer.toString(geneIds.length).length()), geneIds[i]})
                        .collect(Collectors.toList()));
        addTemporaryTsv(
                experimentsMageTabDirLocation.resolve(
                        MessageFormat.format(SINGLE_CELL_MATRIX_MARKET_TPMS_CELL_IDS_FILE_PATH_TEMPLATE, accession)),
                IntStream.range(0, cellIds.length).boxed()
                        .map(i -> new String[] {leftPad(Integer.toString(i + 1), Integer.toString(cellIds.length).length()), cellIds[i]})
                        .collect(Collectors.toList()));
    }

    public void addTpmsExpressionFile(String accession, Collection<String[]> lines) {
        addTemporaryTsv(
                experimentsMageTabDirLocation.resolve(
                        MessageFormat.format(RNASEQ_BASELINE_TPMS_FILE_PATH_TEMPLATE, accession)),
                lines);
    }

    public void addFpkmsExpressionFile(String accession, Collection<String[]> lines) {
        addTemporaryTsv(
                experimentsMageTabDirLocation.resolve(
                        MessageFormat.format(RNASEQ_BASELINE_FPKMS_FILE_PATH_TEMPLATE, accession)),
                lines);
    }

    public void addTranscriptsTpmsExpressionFile(String accession, Collection<String[]> lines) {
        addTemporaryTsv(
                experimentsMageTabDirLocation.resolve(
                        MessageFormat.format(RNASEQ_BASELINE_TRANSCRIPTS_TPMS_FILE_PATH_TEMPLATE, accession)),
                lines);
    }

    public void addExperimentDesignFile(String accession, Collection<String[]> lines) {
        addTemporaryTsv(
                experimentsDesignDirLocation.resolve(
                        MessageFormat.format(EXPERIMENT_DESIGN_FILE_PATH_TEMPLATE, accession)),
                lines);
    }

    public void addCondensedSdrfFile(String accession, Collection<String[]> lines) {
        addTemporaryTsv(
                experimentsMageTabDirLocation.resolve(
                        MessageFormat.format(CONDENSED_SDRF_FILE_PATH_TEMPLATE, accession)),
                lines);
    }

    public void addPercentileRanksFile(String accession, Collection<String[]> lines){
        addTemporaryTsv(
                experimentsMageTabDirLocation.resolve(
                        MessageFormat.format(DIFFERENTIAL_PERCENTILE_RANKS_FILE_PATH_TEMPLATE, accession)),
                lines);
    }

    public void addRnaSeqAnalyticsFile(String accession, Collection<String[]> lines){
        addTemporaryTsv(
                experimentsMageTabDirLocation.resolve(
                        MessageFormat.format(DIFFERENTIAL_ANALYTICS_FILE_PATH_TEMPLATE, accession)),
                lines);
    }

    public void addProteomicsExpressionFile(String accession, Collection<String[]> lines) {
        addTemporaryTsv(
                experimentsMageTabDirLocation.resolve(
                        MessageFormat.format(PROTEOMICS_BASELINE_EXPRESSION_FILE_PATH_TEMPLATE, accession)),
                lines);
    }

    public void addFactorsFile(String accession, Collection<String> lines) {
        addTemporaryFile(
                experimentsMageTabDirLocation.resolve(
                        MessageFormat.format(FACTORS_FILE_PATH_TEMPLATE, accession)),
                lines);
    }

    public void addConfigurationFile(String accession,Collection<String> lines){
        addTemporaryFile(
                experimentsMageTabDirLocation.resolve(
                        MessageFormat.format(CONFIGURATION_FILE_PATH_TEMPLATE, accession)),
                lines);
    }

    public void addReactomePathwaysFile(String accession, String comparison, Collection<String[]> lines) {
        addTemporaryTsv(
                experimentsMageTabDirLocation.resolve(
                        MessageFormat.format(REACTOME_PATHWAYS_FILE_PATH_TEMPLATE, accession, comparison)),
                lines);
    }

    public void addClustersFile(String accession, Collection<String[]> lines) {
        addTemporaryTsv(
                experimentsMageTabDirLocation.resolve(
                        MessageFormat.format(SINGLE_CELL_CLUSTERS_FILE_PATH_TEMPLATE, accession)),
                lines);
    }
}
