package uk.ac.ebi.atlas.resource;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import uk.ac.ebi.atlas.commons.readers.MatrixMarketReader;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.commons.readers.XmlReader;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.commons.writers.TsvWriter;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.model.resource.Directory;
import uk.ac.ebi.atlas.model.resource.MatrixMarketFile;
import uk.ac.ebi.atlas.model.resource.TsvFile;
import uk.ac.ebi.atlas.model.resource.XmlFile;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Named
public class DataFileHub {
    protected final Path experimentsMageTabDirLocation;
    protected final Path experimentsDesignDirLocation;
    protected final Path experimentsAdminDirLocation;

    protected final static String EXPERIMENT_DESIGN_FILE_PATH_TEMPLATE = "ExpDesign-{0}.tsv";
    final static String OP_LOG_FILE_PATH_TEMPLATE = "{0}-op-log.tsv";

    protected final static String CONFIGURATION_FILE_PATH_TEMPLATE = "{0}/{0}-configuration.xml";
    final static String ANALYSIS_METHODS_FILE_PATH_TEMPLATE = "{0}/{0}-analysis-methods.tsv";
    protected final static String CONDENSED_SDRF_FILE_PATH_TEMPLATE = "{0}/{0}.condensed-sdrf.tsv";
    final static String IDF_FILE_PATH_TEMPLATE = "{0}/{0}.idf.txt";

    protected final static String PROTEOMICS_BASELINE_EXPRESSION_FILE_PATH_TEMPLATE = "{0}/{0}.tsv";
    protected final static String RNASEQ_BASELINE_FPKMS_FILE_PATH_TEMPLATE = "{0}/{0}-fpkms.tsv";
    protected final static String RNASEQ_BASELINE_TPMS_FILE_PATH_TEMPLATE = "{0}/{0}-tpms.tsv";
    protected final static String RNASEQ_BASELINE_TRANSCRIPTS_TPMS_FILE_PATH_TEMPLATE = "{0}/{0}-transcripts-tpms.tsv";

    protected final static String FACTORS_FILE_PATH_TEMPLATE = "{0}/{0}-factors.xml";
    protected final static String DIFFERENTIAL_ANALYTICS_FILE_PATH_TEMPLATE = "{0}/{0}-analytics.tsv";
    protected final static String DIFFERENTIAL_PERCENTILE_RANKS_FILE_PATH_TEMPLATE = "{0}/{0}-percentile-ranks.tsv";
    final static String DIFFERENTIAL_RAW_COUNTS_FILE_PATH_TEMPLATE = "{0}/{0}-raw-counts.tsv";
    final static String QC_DIRECTORY_PATH_TEMPLATE = "{0}/qc";
    final static String MICROARRAY_ANALYTICS_FILE_PATH_TEMPLATE = "{0}/{0}_{1}-analytics.tsv";
    final static String MICROARRAY_NORMALIZED_EXPRESSIONS_FILE_PATH_TEMPLATE =
            "{0}/{0}_{1}-normalized-expressions.tsv";
    final static String MICROARRAY_LOG_FOLD_CHANGES_FILE_PATH_TEMPLATE = "{0}/{0}_{1}-log-fold-changes.tsv";
    final static String COEXPRESSION_FILE_TEMPLATE = "{0}/{0}-coexpressions.tsv.gz";

    protected final static String REACTOME_PATHWAYS_FILE_PATH_TEMPLATE = "{0}/{0}.{1}.reactome.gsea.tsv";

    // Single cell files
    protected final static String SINGLE_CELL_MATRIX_MARKET_TPMS_FILE_PATH_TEMPLATE = "{0}/{0}.expression_tpm.mtx";
    protected final static String SINGLE_CELL_MATRIX_MARKET_TPMS_GENE_IDS_FILE_PATH_TEMPLATE =
            SINGLE_CELL_MATRIX_MARKET_TPMS_FILE_PATH_TEMPLATE + "_rows";
    protected final static String SINGLE_CELL_MATRIX_MARKET_TPMS_CELL_IDS_FILE_PATH_TEMPLATE =
            SINGLE_CELL_MATRIX_MARKET_TPMS_FILE_PATH_TEMPLATE + "_cols";

    final static String SINGLE_CELL_MATRIX_MARKET_RAW_AGGREGATED_COUNTS_FILE_PATH_TEMPLATE = "{0}/{0}.aggregated_counts.mtx";
    final static String SINGLE_CELL_MATRIX_MARKET_RAW_AGGREGATED_COUNTS_GENE_IDS_FILE_PATH_TEMPLATE =
            SINGLE_CELL_MATRIX_MARKET_RAW_AGGREGATED_COUNTS_FILE_PATH_TEMPLATE + "_rows";
    final static String SINGLE_CELL_MATRIX_MARKET_RAW_AGGREGATED_COUNTS_CELL_IDS_FILE_PATH_TEMPLATE =
            SINGLE_CELL_MATRIX_MARKET_RAW_AGGREGATED_COUNTS_FILE_PATH_TEMPLATE + "_cols";

    final static String SINGLE_CELL_MATRIX_MARKET_FILTERED_AGGREGATED_COUNTS_FILE_PATH_TEMPLATE = "{0}/{0}.aggregated_filtered_counts.mtx";
    final static String SINGLE_CELL_MATRIX_MARKET_FILTERED_AGGREGATED_COUNTS_GENE_IDS_FILE_PATH_TEMPLATE =
            SINGLE_CELL_MATRIX_MARKET_FILTERED_AGGREGATED_COUNTS_FILE_PATH_TEMPLATE + "_rows";
    final static String SINGLE_CELL_MATRIX_MARKET_FILTERED_AGGREGATED_COUNTS_CELL_IDS_FILE_PATH_TEMPLATE =
            SINGLE_CELL_MATRIX_MARKET_FILTERED_AGGREGATED_COUNTS_FILE_PATH_TEMPLATE + "_cols";

    protected final static String SINGLE_CELL_T_SNE_PLOT_FILE_PATH_TEMPLATE = "{0}/{0}.tsne_perp_{1}.tsv";
    protected final static String SINGLE_CELL_SDRF_FILE_PATH_TEMPLATE = "{0}/{0}.sdrf.txt";
    protected final static String SINGLE_CELL_CLUSTERS_FILE_PATH_TEMPLATE = "{0}/{0}.clusters.tsv";
    protected final static String SINGLE_CELL_SOFTWARE_USED_FILE_PATH_TEMPLATE = "{0}/{0}.software.tsv";


    @Inject
    public DataFileHub(Path experimentsDirPath) {
        this.experimentsMageTabDirLocation = experimentsDirPath.resolve("magetab");
        this.experimentsDesignDirLocation = experimentsDirPath.resolve("expdesign");
        this.experimentsAdminDirLocation = experimentsDirPath.resolve("admin");
    }

    public Path getExperimentMageTabDirLocation() {
        return experimentsMageTabDirLocation;
    }

    public ExperimentFiles getExperimentFiles(String experimentAccession) {
        return new ExperimentFiles(experimentAccession);
    }

    public BaselineExperimentFiles getBaselineExperimentFiles(String experimentAccession) {
        return new BaselineExperimentFiles(experimentAccession);
    }

    public DifferentialExperimentFiles getDifferentialExperimentFiles(String experimentAccession) {
        return new DifferentialExperimentFiles(experimentAccession);
    }

    public RnaSeqBaselineExperimentFiles getRnaSeqBaselineExperimentFiles(String experimentAccession) {
        return new RnaSeqBaselineExperimentFiles(experimentAccession);
    }

    public ProteomicsBaselineExperimentFiles getProteomicsBaselineExperimentFiles(String experimentAccession) {
        return new ProteomicsBaselineExperimentFiles(experimentAccession);
    }

    public RnaSeqDifferentialExperimentFiles getRnaSeqDifferentialExperimentFiles(String experimentAccession) {
        return new RnaSeqDifferentialExperimentFiles(experimentAccession);
    }

    public MicroarrayExperimentFiles getMicroarrayExperimentFiles(String experimentAccession, String arrayDesign) {
        return new MicroarrayExperimentFiles(experimentAccession, arrayDesign);
    }

    public SingleCellExperimentFiles getSingleCellExperimentFiles(String experimentAccession) {
        return new SingleCellExperimentFiles(experimentAccession);
    }

    public class ExperimentFiles {
        public final AtlasResource<TsvStreamer> analysisMethods;
        public final AtlasResource<XmlReader> configuration;
        public final AtlasResource<TsvStreamer> condensedSdrf;
        public final AtlasResource<TsvStreamer> idf;
        public final AtlasResource<Set<Path>> qcFolder;
        public final AtlasResource<TsvStreamer> experimentDesign;
        public final AtlasResource<TsvWriter> experimentDesignWrite;
        public final AtlasResource<TsvStreamer> adminOpLog;
        public final AtlasResource<TsvWriter> adminOpLogWrite;
        public final AtlasResource<TsvWriter> adminOpLogAppend;

        ExperimentFiles(String experimentAccession) {
            analysisMethods = new TsvFile.ReadOnly(experimentsMageTabDirLocation, ANALYSIS_METHODS_FILE_PATH_TEMPLATE, experimentAccession);
            configuration = new XmlFile.ReadOnly(experimentsMageTabDirLocation, CONFIGURATION_FILE_PATH_TEMPLATE, experimentAccession);
            condensedSdrf = new TsvFile.ReadOnly(experimentsMageTabDirLocation, CONDENSED_SDRF_FILE_PATH_TEMPLATE, experimentAccession);
            idf = new TsvFile.ReadOnly(experimentsMageTabDirLocation, IDF_FILE_PATH_TEMPLATE, experimentAccession);
            qcFolder = new Directory(experimentsMageTabDirLocation, QC_DIRECTORY_PATH_TEMPLATE, experimentAccession);

            experimentDesign =
                    new TsvFile.ReadOnly(
                            experimentsDesignDirLocation,
                            EXPERIMENT_DESIGN_FILE_PATH_TEMPLATE,
                            experimentAccession);
            experimentDesignWrite =
                    new TsvFile.Overwrite(
                            experimentsDesignDirLocation,
                            EXPERIMENT_DESIGN_FILE_PATH_TEMPLATE,
                            experimentAccession);

            adminOpLog = new TsvFile.ReadOnly(experimentsAdminDirLocation, OP_LOG_FILE_PATH_TEMPLATE, experimentAccession);
            adminOpLogWrite = new TsvFile.Overwrite(experimentsAdminDirLocation, OP_LOG_FILE_PATH_TEMPLATE, experimentAccession);
            adminOpLogAppend = new TsvFile.Appendable(experimentsAdminDirLocation, OP_LOG_FILE_PATH_TEMPLATE, experimentAccession);
        }
    }

    public class BaselineExperimentFiles {
        public final AtlasResource<XmlReader> factors;
        public final AtlasResource<CSVReader> coexpressions;

        BaselineExperimentFiles(String experimentAccession) {
            factors = new XmlFile.ReadOnly(experimentsMageTabDirLocation, FACTORS_FILE_PATH_TEMPLATE, experimentAccession);
            coexpressions = new TsvFile.ReadCompressed(experimentsMageTabDirLocation, COEXPRESSION_FILE_TEMPLATE, experimentAccession);
        }
    }

    public class DifferentialExperimentFiles {
        public final AtlasResource<ObjectInputStream<String[]>> percentileRanks;

        DifferentialExperimentFiles(String experimentAccession) {
            percentileRanks =
                    new TsvFile.ReadAsStream(
                            experimentsMageTabDirLocation,
                            DIFFERENTIAL_PERCENTILE_RANKS_FILE_PATH_TEMPLATE,
                            experimentAccession);
        }

        public AtlasResource<TsvStreamer> reactomePathwaysFiles(String experimentAccession, String comparison) {
            return new TsvFile.ReadOnly(
                    experimentsMageTabDirLocation,
                    REACTOME_PATHWAYS_FILE_PATH_TEMPLATE,
                    experimentAccession,
                    comparison);
        }
    }

    public class RnaSeqBaselineExperimentFiles {
        public final ExperimentFiles experimentFiles;
        public final BaselineExperimentFiles baselineExperimentFiles;

        public final AtlasResource<ObjectInputStream<String[]>> fpkms;
        public final AtlasResource<ObjectInputStream<String[]>> tpms;
        public final AtlasResource<ObjectInputStream<String[]>> transcriptsTpms;

        RnaSeqBaselineExperimentFiles(String experimentAccession) {
            experimentFiles = new ExperimentFiles(experimentAccession);
            baselineExperimentFiles = new BaselineExperimentFiles(experimentAccession);

            fpkms =
                    new TsvFile.ReadAsStream(
                            experimentsMageTabDirLocation,
                            RNASEQ_BASELINE_FPKMS_FILE_PATH_TEMPLATE,
                            experimentAccession);
            tpms =
                    new TsvFile.ReadAsStream(
                            experimentsMageTabDirLocation,
                            RNASEQ_BASELINE_TPMS_FILE_PATH_TEMPLATE,
                            experimentAccession);

            transcriptsTpms =
                    new TsvFile.ReadAsStream(
                            experimentsMageTabDirLocation,
                            RNASEQ_BASELINE_TRANSCRIPTS_TPMS_FILE_PATH_TEMPLATE,
                            experimentAccession);
            }

        public AtlasResource<ObjectInputStream<String[]>> dataFile(ExpressionUnit.Absolute.Rna unit) {
            switch(unit) {
                case FPKM:
                    return fpkms;
                case TPM:
                    return tpms;
                default:
                    throw new IllegalArgumentException(String.format("No file for: %s", unit));
            }
        }

        public ImmutableList<ExpressionUnit.Absolute.Rna> dataFiles() {
            ImmutableList.Builder<ExpressionUnit.Absolute.Rna> b = ImmutableList.builder();
            if (tpms.exists()) {
                b.add(ExpressionUnit.Absolute.Rna.TPM);
            }
            if (fpkms.exists()) {
                b.add(ExpressionUnit.Absolute.Rna.FPKM);
            }
            return b.build();
        }
    }

    public class ProteomicsBaselineExperimentFiles {
        public final ExperimentFiles experimentFiles;
        public final BaselineExperimentFiles baselineExperimentFiles;

        public final AtlasResource<ObjectInputStream<String[]>> main;

        ProteomicsBaselineExperimentFiles(String experimentAccession) {
            experimentFiles = new ExperimentFiles(experimentAccession);
            baselineExperimentFiles = new BaselineExperimentFiles(experimentAccession);

            main =
                    new TsvFile.ReadAsStream(
                            experimentsMageTabDirLocation,
                            PROTEOMICS_BASELINE_EXPRESSION_FILE_PATH_TEMPLATE,
                            experimentAccession);
            }
    }

    public class RnaSeqDifferentialExperimentFiles {
        public final ExperimentFiles experimentFiles;
        public final DifferentialExperimentFiles differentialExperimentFiles;

        public final AtlasResource<ObjectInputStream<String[]>> analytics;
        public final AtlasResource<TsvStreamer> rawCounts;

        RnaSeqDifferentialExperimentFiles(String experimentAccession) {
            experimentFiles = new ExperimentFiles(experimentAccession);
            differentialExperimentFiles = new DifferentialExperimentFiles(experimentAccession);

            analytics =
                    new TsvFile.ReadAsStream(
                            experimentsMageTabDirLocation,
                            DIFFERENTIAL_ANALYTICS_FILE_PATH_TEMPLATE,
                            experimentAccession);
            rawCounts =
                    new TsvFile.ReadOnly(
                            experimentsMageTabDirLocation,
                            DIFFERENTIAL_RAW_COUNTS_FILE_PATH_TEMPLATE,
                            experimentAccession);
        }
    }

    public class MicroarrayExperimentFiles {
        public final ExperimentFiles experimentFiles;
        public final DifferentialExperimentFiles differentialExperimentFiles;

        public final AtlasResource<ObjectInputStream<String[]>> analytics;
        public final AtlasResource<TsvStreamer> normalizedExpressions;    // Microarray 1-colour specific
        public final AtlasResource<TsvStreamer> logFoldChanges;           // Microarray 2-colour specific

        MicroarrayExperimentFiles(String experimentAccession, String arrayDesign) {
            experimentFiles = new ExperimentFiles(experimentAccession);
            differentialExperimentFiles = new DifferentialExperimentFiles(experimentAccession);

            analytics =
                    new TsvFile.ReadAsStream(
                            experimentsMageTabDirLocation,
                            MICROARRAY_ANALYTICS_FILE_PATH_TEMPLATE,
                            experimentAccession,
                            arrayDesign);

            normalizedExpressions =
                    new TsvFile.ReadOnly(
                            experimentsMageTabDirLocation,
                            MICROARRAY_NORMALIZED_EXPRESSIONS_FILE_PATH_TEMPLATE,
                            experimentAccession,
                            arrayDesign);

            logFoldChanges =
                    new TsvFile.ReadOnly(
                            experimentsMageTabDirLocation,
                            MICROARRAY_LOG_FOLD_CHANGES_FILE_PATH_TEMPLATE,
                            experimentAccession,
                            arrayDesign);
        }
    }

    public class SingleCellExperimentFiles {
        public final ExperimentFiles experimentFiles;

        public final AtlasResource<TsvStreamer> softwareUsed;
        public final AtlasResource<MatrixMarketReader> tpmsMatrix;
        public final AtlasResource<TsvStreamer> geneIdsTsv;
        public final AtlasResource<TsvStreamer> cellIdsTsv;
        public final AtlasResource<TsvStreamer> sdrf;
        public final AtlasResource<TsvStreamer> clustersTsv;
        public final Map<Integer, AtlasResource<TsvStreamer>> tSnePlotTsvs;

        SingleCellExperimentFiles(String experimentAccession) {
            experimentFiles = new ExperimentFiles(experimentAccession);

            softwareUsed = new TsvFile.ReadOnly(
                    experimentsMageTabDirLocation,
                    SINGLE_CELL_SOFTWARE_USED_FILE_PATH_TEMPLATE,
                    experimentAccession);

            sdrf = new TsvFile.ReadOnly(
                    experimentsMageTabDirLocation,
                    SINGLE_CELL_SDRF_FILE_PATH_TEMPLATE,
                    experimentAccession);

            clustersTsv = new TsvFile.ReadOnly(
                    experimentsMageTabDirLocation,
                    SINGLE_CELL_CLUSTERS_FILE_PATH_TEMPLATE,
                    experimentAccession);

            tpmsMatrix =
                    new MatrixMarketFile(
                            experimentsMageTabDirLocation,
                            SINGLE_CELL_MATRIX_MARKET_TPMS_FILE_PATH_TEMPLATE,
                            experimentAccession);

            geneIdsTsv =
                    new TsvFile.ReadOnly(
                            experimentsMageTabDirLocation,
                            SINGLE_CELL_MATRIX_MARKET_TPMS_GENE_IDS_FILE_PATH_TEMPLATE,
                            experimentAccession);

            cellIdsTsv =
                    new TsvFile.ReadOnly(
                            experimentsMageTabDirLocation,
                            SINGLE_CELL_MATRIX_MARKET_TPMS_CELL_IDS_FILE_PATH_TEMPLATE,
                            experimentAccession);

            tSnePlotTsvs = discoverAvailablePerplexitiesFromTSnePlotFiles(experimentAccession).stream()
                    .collect(
                            Collectors.toMap(
                                    perplexity -> perplexity,
                                    perplexity -> new TsvFile.ReadOnly(
                                            experimentsMageTabDirLocation,
                                            SINGLE_CELL_T_SNE_PLOT_FILE_PATH_TEMPLATE,
                                            experimentAccession,
                                            perplexity.toString())));
        }

//        public AtlasResource<MatrixMarketReader> dataFile(ExpressionUnit.Absolute.Rna unit) {
//            switch(unit) {
//                case TPM:
//                    return tpms;
//                default:
//                    throw new RuntimeException("No file for " + unit);
//            }
//        }

        private Set<Integer> discoverAvailablePerplexitiesFromTSnePlotFiles(String experimentAccession) {
            Path tSnePlotFilePathTemplate =
                    experimentsMageTabDirLocation.resolve(
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
    }
}
