package uk.ac.ebi.atlas.resource;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.commons.readers.MatrixMarketReader;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.XmlReader;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.commons.writers.TsvWriter;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.resource.*;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Named
public class DataFileHub {

    protected final String gxaExperimentsFilesLocation;
    protected final String scxaExperimentsFilesLocation;

    final static String CONFIGURATION_FILE_PATH_TEMPLATE = "magetab/{0}/{0}-configuration.xml";
    final static String ANALYSIS_METHODS_FILE_PATH_TEMPLATE = "magetab/{0}/{0}-analysis-methods.tsv";
    final static String EXPERIMENT_DESIGN_FILE_PATH_TEMPLATE = "expdesign/ExpDesign-{0}.tsv";
    final static String CONDENSED_SDRF_FILE_PATH_TEMPLATE = "magetab/{0}/{0}.condensed-sdrf.tsv";
    final static String IDF_FILE_PATH_TEMPLATE = "magetab/{0}/{0}.idf.txt";
    final static String OP_LOG_FILE_PATH_TEMPLATE = "admin/{0}-op-log.tsv";

    final static String PROTEOMICS_BASELINE_EXPRESSION_FILE_PATH_TEMPLATE = "magetab/{0}/{0}.tsv";
    final static String RNASEQ_BASELINE_FPKMS_FILE_PATH_TEMPLATE = "magetab/{0}/{0}-fpkms.tsv";
    final static String RNASEQ_BASELINE_TPMS_FILE_PATH_TEMPLATE = "magetab/{0}/{0}-tpms.tsv";
    final static String RNASEQ_BASELINE_TRANSCRIPTS_TPMS_FILE_PATH_TEMPLATE = "magetab/{0}/{0}-transcripts-tpms.tsv";

    final static String FACTORS_FILE_PATH_TEMPLATE = "magetab/{0}/{0}-factors.xml";
    final static String DIFFERENTIAL_ANALYTICS_FILE_PATH_TEMPLATE = "magetab/{0}/{0}-analytics.tsv";
    final static String DIFFERENTIAL_PERCENTILE_RANKS_FILE_PATH_TEMPLATE = "magetab/{0}/{0}-percentile-ranks.tsv";
    final static String DIFFERENTIAL_RAW_COUNTS_FILE_PATH_TEMPLATE = "magetab/{0}/{0}-raw-counts.tsv";
    final static String QC_DIRECTORY_PATH_TEMPLATE = "magetab/{0}/qc";
    final static String MICROARRAY_ANALYTICS_FILE_PATH_TEMPLATE = "magetab/{0}/{0}_{1}-analytics.tsv";
    final static String MICROARRAY_NORMALIZED_EXPRESSIONS_FILE_PATH_TEMPLATE =
            "magetab/{0}/{0}_{1}-normalized-expressions.tsv";
    final static String MICROARRAY_LOG_FOLD_CHANGES_FILE_PATH_TEMPLATE = "magetab/{0}/{0}_{1}-log-fold-changes.tsv";
    final static String COEXPRESSION_FILE_TEMPLATE = "magetab/{0}/{0}-coexpressions.tsv.gz";

    final static String REACTOME_PATHWAYS_FILE_PATH_TEMPLATE = "magetab/{0}/{0}.{1}.reactome.gsea.tsv";

    final static String SINGLE_CELL_MATRIX_MARKET_TPMS_FILE_PATH_TEMPLATE = "magetab/{0}/{0}-tpms.mtx";
    final static String SINGLE_CELL_MATRIX_MARKET_TPMS_GENE_IDS_FILE_PATH_TEMPLATE =
            SINGLE_CELL_MATRIX_MARKET_TPMS_FILE_PATH_TEMPLATE + "_rows";
    final static String SINGLE_CELL_MATRIX_MARKET_TPMS_CELL_IDS_FILE_PATH_TEMPLATE =
            SINGLE_CELL_MATRIX_MARKET_TPMS_FILE_PATH_TEMPLATE + "_cols";
    final static String SINGLE_CELL_T_SNE_PLOT_FILE_PATH_TEMPLATE = "magetab/{0}/{0}-tsne_perp_{1}.tsv";

    @Inject
    public DataFileHub(
            @Value("#{configuration['experimentsFilesLocation']}") String gxaExperimentsFilesLocation,
            @Value("#{configuration['scExperimentsFilesLocation']}") String scxaExperimentsFilesLocation){
        Validate.notNull(
                gxaExperimentsFilesLocation,
                "Data files location not found - if this is a development environment try `mvn clean install`");
        this.gxaExperimentsFilesLocation = gxaExperimentsFilesLocation;
        this.scxaExperimentsFilesLocation = scxaExperimentsFilesLocation;
    }

    public ExperimentFiles getExperimentFiles(String experimentAccession) {
        return new ExperimentFiles(experimentAccession);
    }

    public BaselineExperimentFiles getBaselineExperimentFiles(String experimentAccession) {
        return new BaselineExperimentFiles(experimentAccession);
    }

    public RnaSeqBaselineExperimentFiles getRnaSeqBaselineExperimentFiles(String experimentAccession) {
        return new RnaSeqBaselineExperimentFiles(experimentAccession);
    }

    public ProteomicsBaselineExperimentFiles getProteomicsBaselineExperimentFiles(String experimentAccession) {
        return new ProteomicsBaselineExperimentFiles(experimentAccession);
    }

    public DifferentialExperimentFiles getDifferentialExperimentFiles(String experimentAccession){
        return new DifferentialExperimentFiles(experimentAccession);
    }

    public RnaSeqDifferentialExperimentFiles getRnaSeqDifferentialExperimentFiles(String experimentAccession) {
        return new RnaSeqDifferentialExperimentFiles(experimentAccession);
    }

    public MicroarrayExperimentFiles getMicroarrayExperimentFiles(String experimentAccession, String arrayDesign) {
        return new MicroarrayExperimentFiles(experimentAccession, arrayDesign);
    }

    public AtlasResource<TsvReader> getReactomePathwaysFiles(String experimentAccession, String comparison) {
        return new TsvFile.ReadOnly(
                gxaExperimentsFilesLocation, REACTOME_PATHWAYS_FILE_PATH_TEMPLATE,
                experimentAccession, comparison);
    }

    public AtlasResource<KryoFile.Handle> getKryoFile(String experimentAccession,
                                                      ProfileStreamOptions<?> profileStreamOptions){
        return new KryoFile(gxaExperimentsFilesLocation, experimentAccession, profileStreamOptions);
    }

    public SingleCellExperimentFiles getSingleCellExperimentFiles(String experimentAccession) {
        return new SingleCellExperimentFiles(experimentAccession);
    }

    public class ExperimentFiles {

        public final AtlasResource<TsvReader> analysisMethods;
        public final AtlasResource<XmlReader> configuration;
        public final AtlasResource<TsvReader> experimentDesign;
        public final AtlasResource<TsvWriter> experimentDesignWrite;
        public final AtlasResource<TsvReader> condensedSdrf;
        public final AtlasResource<TsvReader> idf;
        public final AtlasResource<TsvReader> adminOpLog;
        public final AtlasResource<TsvWriter> adminOpLogWrite;
        public final AtlasResource<TsvWriter> adminOpLogAppend;
        public final AtlasResource<Set<Path>> qcFolder;

        ExperimentFiles(String experimentAccession) {
            this.analysisMethods =
                    new TsvFile.ReadOnly(gxaExperimentsFilesLocation, ANALYSIS_METHODS_FILE_PATH_TEMPLATE, experimentAccession);
            this.configuration =
                    new XmlFile.ReadOnly(gxaExperimentsFilesLocation, CONFIGURATION_FILE_PATH_TEMPLATE, experimentAccession);

            this.experimentDesign =
                    new TsvFile.ReadOnly(gxaExperimentsFilesLocation, EXPERIMENT_DESIGN_FILE_PATH_TEMPLATE, experimentAccession);
            this.experimentDesignWrite =
                    new TsvFile.Overwrite(gxaExperimentsFilesLocation, EXPERIMENT_DESIGN_FILE_PATH_TEMPLATE, experimentAccession);

            this.condensedSdrf =
                    new TsvFile.ReadOnly(gxaExperimentsFilesLocation, CONDENSED_SDRF_FILE_PATH_TEMPLATE, experimentAccession);

            this.idf = new TsvFile.ReadOnly(gxaExperimentsFilesLocation, IDF_FILE_PATH_TEMPLATE, experimentAccession);

            this.adminOpLog = new TsvFile.ReadOnly(gxaExperimentsFilesLocation, OP_LOG_FILE_PATH_TEMPLATE, experimentAccession);
            this.adminOpLogWrite =
                    new TsvFile.Overwrite(gxaExperimentsFilesLocation, OP_LOG_FILE_PATH_TEMPLATE, experimentAccession);
            this.adminOpLogAppend =
                    new TsvFile.Appendable(gxaExperimentsFilesLocation, OP_LOG_FILE_PATH_TEMPLATE, experimentAccession);
            this.qcFolder = new Directory(gxaExperimentsFilesLocation, QC_DIRECTORY_PATH_TEMPLATE, experimentAccession);
        }

    }

    public class RnaSeqBaselineExperimentFiles extends BaselineExperimentFiles {
        private final AtlasResource<ObjectInputStream<String[]>> fpkms;
        private final AtlasResource<ObjectInputStream<String[]>> tpms;
        public final AtlasResource<ObjectInputStream<String[]>> transcriptsTpms;
        RnaSeqBaselineExperimentFiles(String experimentAccession) {
            super(experimentAccession);
            this.fpkms =
                    new TsvFile.ReadAsStream(
                            gxaExperimentsFilesLocation, RNASEQ_BASELINE_FPKMS_FILE_PATH_TEMPLATE, experimentAccession);
            this.tpms =
                    new TsvFile.ReadAsStream(
                            gxaExperimentsFilesLocation, RNASEQ_BASELINE_TPMS_FILE_PATH_TEMPLATE, experimentAccession);
            this.transcriptsTpms =
                    new TsvFile.ReadAsStream(
                            gxaExperimentsFilesLocation, RNASEQ_BASELINE_TRANSCRIPTS_TPMS_FILE_PATH_TEMPLATE, experimentAccession);
        }

        public AtlasResource<ObjectInputStream<String[]>> dataFile(ExpressionUnit.Absolute.Rna unit) {
            switch(unit){
                case FPKM:
                    return fpkms;
                case TPM:
                    return tpms;
                default:
                    throw new RuntimeException("No file for: "+unit);
            }
        }

        public ImmutableList<ExpressionUnit.Absolute.Rna> dataFiles() {
            ImmutableList.Builder<ExpressionUnit.Absolute.Rna> b = ImmutableList.builder();
            if(tpms.exists()){
                b.add(ExpressionUnit.Absolute.Rna.TPM);
            }
            if(fpkms.exists()){
                b.add(ExpressionUnit.Absolute.Rna.FPKM);
            }
            return b.build();
        }
    }

    public class ProteomicsBaselineExperimentFiles extends BaselineExperimentFiles {
        public final AtlasResource<ObjectInputStream<String[]>> main;
        ProteomicsBaselineExperimentFiles(String experimentAccession) {
            super(experimentAccession);
            this.main =
                    new TsvFile.ReadAsStream(
                            gxaExperimentsFilesLocation, PROTEOMICS_BASELINE_EXPRESSION_FILE_PATH_TEMPLATE, experimentAccession);
        }
    }

    public class BaselineExperimentFiles extends ExperimentFiles {
        public final AtlasResource<XmlReader> factors;
        public final AtlasResource<CSVReader> coexpressions;

        BaselineExperimentFiles(String experimentAccession) {
            super(experimentAccession);
            this.factors = new XmlFile.ReadOnly(gxaExperimentsFilesLocation, FACTORS_FILE_PATH_TEMPLATE, experimentAccession);
            this.coexpressions =
                    new TsvFile.ReadCompressed(gxaExperimentsFilesLocation, COEXPRESSION_FILE_TEMPLATE, experimentAccession);
        }
    }

    public class DifferentialExperimentFiles extends ExperimentFiles {
        public AtlasResource<ObjectInputStream<String[]>> percentileRanks;

        DifferentialExperimentFiles(String experimentAccession) {
            super(experimentAccession);
            this.percentileRanks =
                    new TsvFile.ReadAsStream(
                            gxaExperimentsFilesLocation, DIFFERENTIAL_PERCENTILE_RANKS_FILE_PATH_TEMPLATE, experimentAccession);
        }

    }

    public class RnaSeqDifferentialExperimentFiles extends DifferentialExperimentFiles {
        public final AtlasResource<ObjectInputStream<String[]>> analytics;
        public final AtlasResource<TsvReader> rawCounts;

        RnaSeqDifferentialExperimentFiles(String experimentAccession) {
            super(experimentAccession);
            this.analytics =
                    new TsvFile.ReadAsStream(
                            gxaExperimentsFilesLocation, DIFFERENTIAL_ANALYTICS_FILE_PATH_TEMPLATE, experimentAccession);
            this.rawCounts =
                    new TsvFile.ReadOnly(
                            gxaExperimentsFilesLocation, DIFFERENTIAL_RAW_COUNTS_FILE_PATH_TEMPLATE, experimentAccession);
        }

    }

    public class MicroarrayExperimentFiles extends DifferentialExperimentFiles {
        public final AtlasResource<ObjectInputStream<String[]>> analytics;
        public final AtlasResource<TsvReader> normalizedExpressions;    // Microarray 1-colour specific
        public final AtlasResource<TsvReader> logFoldChanges;   // Microarray 2-colour specific

        MicroarrayExperimentFiles(String experimentAccession, String arrayDesign) {
            super(experimentAccession);
            analytics =
                    new TsvFile.ReadAsStream(
                            gxaExperimentsFilesLocation, MICROARRAY_ANALYTICS_FILE_PATH_TEMPLATE,
                            experimentAccession, arrayDesign);
            normalizedExpressions =
                    new TsvFile.ReadOnly(
                            gxaExperimentsFilesLocation, MICROARRAY_NORMALIZED_EXPRESSIONS_FILE_PATH_TEMPLATE,
                            experimentAccession, arrayDesign);
            logFoldChanges =
                    new TsvFile.ReadOnly(
                            gxaExperimentsFilesLocation, MICROARRAY_LOG_FOLD_CHANGES_FILE_PATH_TEMPLATE,
                            experimentAccession, arrayDesign);
        }
    }

    public class SingleCellExperimentFiles extends ExperimentFiles {
        public final AtlasResource<MatrixMarketReader> tpmsMatrix;
        public final AtlasResource<TsvReader> geneIdsTsv;
        public final AtlasResource<TsvReader> cellIdsTsv;
        public final Set<AtlasResource<TsvReader>> tSnePlotTsvs;

        SingleCellExperimentFiles(String experimentAccession) {
            super(experimentAccession);
            this.tpmsMatrix =
                    new MatrixMarketFile(
                            scxaExperimentsFilesLocation,
                            SINGLE_CELL_MATRIX_MARKET_TPMS_FILE_PATH_TEMPLATE,
                            experimentAccession);
            this.geneIdsTsv =
                    new TsvFile.ReadOnly(
                            scxaExperimentsFilesLocation,
                            SINGLE_CELL_MATRIX_MARKET_TPMS_GENE_IDS_FILE_PATH_TEMPLATE,
                            experimentAccession);
            this.cellIdsTsv =
                    new TsvFile.ReadOnly(
                            scxaExperimentsFilesLocation,
                            SINGLE_CELL_MATRIX_MARKET_TPMS_CELL_IDS_FILE_PATH_TEMPLATE,
                            experimentAccession);

            tSnePlotTsvs =
                    findAvailablePerplexityValuesFromTSnePlotFiles(experimentAccession).stream()
                    .map(
                            perplexity ->
                                    new TsvFile.ReadOnly(
                                            scxaExperimentsFilesLocation,
                                            SINGLE_CELL_T_SNE_PLOT_FILE_PATH_TEMPLATE,
                                            experimentAccession,
                                            Integer.toString(perplexity)))
                    .collect(Collectors.toSet());
        }

//        public AtlasResource<MatrixMarketReader> dataFile(ExpressionUnit.Absolute.Rna unit) {
//            switch(unit) {
//                case TPM:
//                    return tpms;
//                default:
//                    throw new RuntimeException("No file for " + unit);
//            }
//        }

        private Set<Integer> findAvailablePerplexityValuesFromTSnePlotFiles(String experimentAccession) {
            Path tSnePlotFilePathTemplate =
                    Paths.get(scxaExperimentsFilesLocation).resolve(
                            MessageFormat.format(
                                    SINGLE_CELL_T_SNE_PLOT_FILE_PATH_TEMPLATE, experimentAccession, "(\\d+)"));

            Pattern tSnePlotTsvFileRegex = Pattern.compile(tSnePlotFilePathTemplate.getFileName().toString());

            ImmutableSet.Builder<Integer> perplexityValues = ImmutableSet.builder();
            try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(tSnePlotFilePathTemplate.getParent())) {

                for (Path filePath : dirStream) {
                    Matcher matcher = tSnePlotTsvFileRegex.matcher(filePath.getFileName().toString());
                    if (matcher.matches()) {
                        perplexityValues.add(Integer.parseInt(matcher.group(1)));
                    }
                }

            } catch (IOException e) {
                // log warning, the set will be empty, the caller decides what to do
            }
            return perplexityValues.build();
        }
//
//        private Set<Path> findMatchingFiles(Path directory, String glob) {
//            //                        Paths.get(gxaExperimentsFilesLocation, new File(SINGLE_CELL_T_SNE_PLOT_FILE_PATH_TEMPLATE).getPath()),
//
//            try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory, glob)) {
//                ImmutableSet.Builder<Path> fileNameSetBuilder = ImmutableSet.builder();
//                for (Path path : dirStream) {
//                    fileNameSetBuilder.add(path);
//                }
//                return fileNameSetBuilder.build();
//            } catch (IOException e) {
//                return Collections.emptySet();
//            }
//        }
    }

    public String getExperimentDataLocation() {
        return Paths.get(gxaExperimentsFilesLocation, "magetab").toString() + "/";
    }

}
