package uk.ac.ebi.atlas.resource;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.Collection;

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

    private void addTemporaryTsv(String where, Collection<String[]> lines){
        addTemporaryFile( where,
                FluentIterable.from(lines).transform(new Function<String[], String>() {
                    @Nullable
                    @Override
                    public String apply(String[] strings) {
                        return Joiner.on('\t').join(strings);
                    }
                }).toList());
    }

    public void addTemporaryFile(String where, Collection<String> lines){
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

    public void addTpmsExpressionFile(String accession, Collection<String[]> lines){
        addTemporaryTsv(MessageFormat.format(RNASEQ_BASELINE_TPMS_FILE_PATH_TEMPLATE, accession), lines);
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
//    public void addProteomicsExpressionFile(String accession, Collection<String[]> lines) {
//        addTemporaryTsv(MessageFormat.format(PROTEOMICS_BASELINE_EXPRESSION_FILE_PATH_TEMPLATE, accession), lines);
//    }

    public void addFactorsFile(String accession, Collection<String> lines) {
        addTemporaryFile(MessageFormat.format(FACTORS_FILE_PATH_TEMPLATE, accession), lines);
    }

    public String getFactorsFilePathTemplate() {
        return FileSystems.getDefault().getPath(dataFilesLocation, FACTORS_FILE_PATH_TEMPLATE).toString();
    }

    public void addSpeciesJsonFile(Collection<String> lines) {
        addTemporaryFile(SPECIES_PROPERTIES_FILE_PATH, lines);
    }

}
