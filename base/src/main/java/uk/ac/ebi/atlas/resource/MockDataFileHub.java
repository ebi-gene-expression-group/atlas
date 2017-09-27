package uk.ac.ebi.atlas.resource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

// I belong in the test dir, but here I can be shared among different modules without the need of creating a test jar
public class MockDataFileHub extends DataFileHub {

    public MockDataFileHub() throws IOException {
        super(Files.createTempDirectory("").toString());
        new File(dataFilesLocation, "admin").mkdir();
        new File(dataFilesLocation, "magetab").mkdir();
        new File(dataFilesLocation, "expdesign").mkdir();
        new File(dataFilesLocation, "species").mkdir();
        new File(dataFilesLocation, "serialized_expression").mkdir();
        new File(dataFilesLocation).deleteOnExit();
    }

    private void addTemporaryTsv(String where, Collection<String[]> lines) {
        addTemporaryFile(
                where,
                lines.stream()
                        .map(line -> Arrays.stream(line).collect(Collectors.joining("\t")))
                        .collect(Collectors.toList()));
    }

    public void addTemporaryFile(String where, Collection<String> lines) {
        File f = new File(dataFilesLocation + where);
        f.deleteOnExit();
        f.getParentFile().mkdirs();
        try {
            f.createNewFile();
            Files.write(f.toPath(), lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public void addTemporaryPercentileRanksFile(String accession, Collection<String[]> lines){
        addTemporaryTsv(MessageFormat.format(DIFFERENTIAL_PERCENTILE_RANKS_FILE_PATH_TEMPLATE, accession), lines);
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

    public void addSpeciesJsonFile(Collection<String> lines) {
        addTemporaryFile(SPECIES_PROPERTIES_FILE_PATH, lines);
    }

}
