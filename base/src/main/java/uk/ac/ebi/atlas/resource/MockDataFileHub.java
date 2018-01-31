package uk.ac.ebi.atlas.resource;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.Triple;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
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

    private MockDataFileHub() throws IOException {
        super(Files.createTempDirectory("gxa").toString(), Files.createTempDirectory("scxa").toString());
        new File(gxaExperimentsFilesLocation, "admin").mkdir();
        new File(gxaExperimentsFilesLocation, "magetab").mkdir();
        new File(gxaExperimentsFilesLocation, "expdesign").mkdir();
        new File(gxaExperimentsFilesLocation, "serialized_expression").mkdir();
        new File(gxaExperimentsFilesLocation).deleteOnExit();
    }

    public static MockDataFileHub create() {
        try {
            return new MockDataFileHub();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addTemporaryMatrixMarket(String where, int rows, int columns, Collection<Triple> lines) {
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

    private void addTemporaryTsv(String where, Collection<String[]> lines) {
        addTemporaryFile(
                where,
                lines.stream()
                        .map(line -> Arrays.stream(line).collect(Collectors.joining("\t")))
                        .collect(Collectors.toList()));
    }

    public void addTemporaryFile(String where, Collection<String> lines) {
        File f = Paths.get(gxaExperimentsFilesLocation, where).toFile();
        f.deleteOnExit();
        f.getParentFile().mkdirs();
        try {
            f.createNewFile();
            Files.write(f.toPath(), lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMatrixMarketExpressionFiles(String accession,
                                               Collection<Triple> lines,
                                               String[] geneIds,
                                               String[] cellIds) {
        addTemporaryMatrixMarket(
                MessageFormat.format(SINGLE_CELL_MATRIX_MARKET_TPMS_FILE_PATH_TEMPLATE, accession),
                geneIds.length, cellIds.length, lines);
        addTemporaryTsv(
                MessageFormat.format(SINGLE_CELL_MATRIX_MARKET_TPMS_GENE_IDS_FILE_PATH_TEMPLATE, accession),
                IntStream.range(0, geneIds.length).boxed()
                        .map(i -> new String[] {leftPad(Integer.toString(i + 1), Integer.toString(geneIds.length).length()), geneIds[i]})
                        .collect(Collectors.toList()));
        addTemporaryTsv(
                MessageFormat.format(SINGLE_CELL_MATRIX_MARKET_TPMS_CELL_IDS_FILE_PATH_TEMPLATE, accession),
                IntStream.range(0, cellIds.length).boxed()
                        .map(i -> new String[] {leftPad(Integer.toString(i + 1), Integer.toString(cellIds.length).length()), cellIds[i]})
                        .collect(Collectors.toList()));
    }

    public void addTpmsExpressionFile(String accession, Collection<String[]> lines) {
        addTemporaryTsv(MessageFormat.format(RNASEQ_BASELINE_TPMS_FILE_PATH_TEMPLATE, accession), lines);
    }

    public void addFpkmsExpressionFile(String accession, Collection<String[]> lines) {
        addTemporaryTsv(MessageFormat.format(RNASEQ_BASELINE_FPKMS_FILE_PATH_TEMPLATE, accession), lines);
    }

    public void addTranscriptsTpmsExpressionFile(String accession, Collection<String[]> lines) {
        addTemporaryTsv(MessageFormat.format(RNASEQ_BASELINE_TRANSCRIPTS_TPMS_FILE_PATH_TEMPLATE, accession), lines);
    }

    public void addExperimentDesignFile(String accession, Collection<String[]> lines) {
        addTemporaryTsv(MessageFormat.format(EXPERIMENT_DESIGN_FILE_PATH_TEMPLATE, accession), lines);
    }

    public void addCondensedSdrfFile(String accession, Collection<String[]> lines) {
        addTemporaryTsv(MessageFormat.format(CONDENSED_SDRF_FILE_PATH_TEMPLATE, accession), lines);
    }

    public void addPercentileRanksFile(String accession, Collection<String[]> lines){
        addTemporaryTsv(MessageFormat.format(DIFFERENTIAL_PERCENTILE_RANKS_FILE_PATH_TEMPLATE, accession), lines);
    }

    public void addRnaSeqAnalyticsFile(String accession, Collection<String[]> lines){
        addTemporaryTsv(MessageFormat.format(DIFFERENTIAL_ANALYTICS_FILE_PATH_TEMPLATE, accession), lines);
    }

//    public void addRawCountsFile(String accession, Collection<String[]> lines) {
//        addTemporaryTsv(MessageFormat.format(DIFFERENTIAL_RAW_COUNTS_FILE_PATH_TEMPLATE, accession), lines);
//    }
//
//    public void addAnalysisMethodsFile(String accession, Collection<String[]> lines) {
//        addTemporaryTsv(MessageFormat.format(ANALYSIS_METHODS_FILE_PATH_TEMPLATE, accession), lines);
//    }
//

    public void addProteomicsExpressionFile(String accession, Collection<String[]> lines) {
        addTemporaryTsv(MessageFormat.format(PROTEOMICS_BASELINE_EXPRESSION_FILE_PATH_TEMPLATE, accession), lines);
    }

    public void addFactorsFile(String accession, Collection<String> lines) {
        addTemporaryFile(MessageFormat.format(FACTORS_FILE_PATH_TEMPLATE, accession), lines);
    }

    public void addConfigurationFile(String accession,Collection<String> lines){
        addTemporaryFile(MessageFormat.format(CONFIGURATION_FILE_PATH_TEMPLATE, accession), lines);
    }

    public void addReactomePathwaysFile(String accession, String comparison, Collection<String[]> lines) {
        addTemporaryTsv(MessageFormat.format(REACTOME_PATHWAYS_FILE_PATH_TEMPLATE, accession, comparison), lines);
    }

}
