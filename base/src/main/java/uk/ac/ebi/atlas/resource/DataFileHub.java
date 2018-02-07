package uk.ac.ebi.atlas.resource;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.commons.readers.MatrixMarketReader;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.XmlReader;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.commons.writers.TsvWriter;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
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
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Named
public class DataFileHub {

    protected final String experimentsFilesLocation;

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
    public DataFileHub(@Value("#{configuration['experimentsFilesLocation']}") String experimentsFilesLocation) {
        Preconditions.checkNotNull(
                experimentsFilesLocation,
                "Data files location not found - if this is a development environment try `mvn clean install`");
        this.experimentsFilesLocation = experimentsFilesLocation;
    }

    public String getGxaExperimentDataLocation() {
        return Paths.get(experimentsFilesLocation, "gxa", "magetab").toString() + "/";
    }

    public ExperimentFiles getExperimentFiles(String experimentAccession) {
        return new ExperimentFiles(discoverExperimentLocation(experimentAccession), experimentAccession);
    }

    public BaselineExperimentFiles getBaselineExperimentFiles(String experimentAccession) {
        return new BaselineExperimentFiles(discoverExperimentLocation(experimentAccession), experimentAccession);
    }

    public DifferentialExperimentFiles getDifferentialExperimentFiles(String experimentAccession) {
        return new DifferentialExperimentFiles(discoverExperimentLocation(experimentAccession), experimentAccession);
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

    public AtlasResource<KryoFile.Handle> getKryoFile(String experimentAccession,
                                                      ProfileStreamOptions<?> profileStreamOptions){
        return new KryoFile(experimentsFilesLocation, experimentAccession, profileStreamOptions);
    }

    public SingleCellExperimentFiles getSingleCellExperimentFiles(String experimentAccession) {
        return new SingleCellExperimentFiles(experimentAccession);
    }

    public final class ExperimentFiles {
        public final AtlasResource<TsvReader> analysisMethods;
        public final AtlasResource<XmlReader> configuration;
        public final AtlasResource<TsvReader> condensedSdrf;
        public final AtlasResource<TsvReader> idf;
        public final AtlasResource<Set<Path>> qcFolder;
        public final AtlasResource<TsvReader> experimentDesign;
        public final AtlasResource<TsvWriter> experimentDesignWrite;
        public final AtlasResource<TsvReader> adminOpLog;
        public final AtlasResource<TsvWriter> adminOpLogWrite;
        public final AtlasResource<TsvWriter> adminOpLogAppend;

        ExperimentFiles(String baseDir, String experimentAccession) {
            analysisMethods = new TsvFile.ReadOnly(baseDir, ANALYSIS_METHODS_FILE_PATH_TEMPLATE, experimentAccession);
            configuration = new XmlFile.ReadOnly(baseDir, CONFIGURATION_FILE_PATH_TEMPLATE, experimentAccession);
            condensedSdrf = new TsvFile.ReadOnly(baseDir, CONDENSED_SDRF_FILE_PATH_TEMPLATE, experimentAccession);
            idf = new TsvFile.ReadOnly(baseDir, IDF_FILE_PATH_TEMPLATE, experimentAccession);
            qcFolder = new Directory(baseDir, QC_DIRECTORY_PATH_TEMPLATE, experimentAccession);

            experimentDesign =
                    new TsvFile.ReadOnly(
                            baseDir,
                            EXPERIMENT_DESIGN_FILE_PATH_TEMPLATE,
                            experimentAccession);
            experimentDesignWrite =
                    new TsvFile.Overwrite(
                            baseDir,
                            EXPERIMENT_DESIGN_FILE_PATH_TEMPLATE,
                            experimentAccession);

            adminOpLog = new TsvFile.ReadOnly(baseDir, OP_LOG_FILE_PATH_TEMPLATE, experimentAccession);
            adminOpLogWrite = new TsvFile.Overwrite(baseDir, OP_LOG_FILE_PATH_TEMPLATE, experimentAccession);
            adminOpLogAppend = new TsvFile.Appendable(baseDir, OP_LOG_FILE_PATH_TEMPLATE, experimentAccession);
        }
    }

    // baseDir parameterised as we might want to have BaselineExperimentFiles bundled with SC experiments
    public final class BaselineExperimentFiles {
        public final ExperimentFiles experimentFiles;
        public final AtlasResource<XmlReader> factors;
        public final AtlasResource<CSVReader> coexpressions;

        BaselineExperimentFiles(String baseDir, String experimentAccession) {
            experimentFiles = new ExperimentFiles(baseDir, experimentAccession);

            factors = new XmlFile.ReadOnly(baseDir, FACTORS_FILE_PATH_TEMPLATE, experimentAccession);
            coexpressions = new TsvFile.ReadCompressed(baseDir, COEXPRESSION_FILE_TEMPLATE, experimentAccession);
        }
    }

    // baseDir parameterised as we might want to have DifferentialExperimentFiles bundled with SC experiments
    public final class DifferentialExperimentFiles {
        public final ExperimentFiles experimentFiles;
        public final AtlasResource<ObjectInputStream<String[]>> percentileRanks;

        DifferentialExperimentFiles(String baseDir, String experimentAccession) {
            experimentFiles = new ExperimentFiles(baseDir, experimentAccession);

            percentileRanks =
                    new TsvFile.ReadAsStream(
                            baseDir, DIFFERENTIAL_PERCENTILE_RANKS_FILE_PATH_TEMPLATE,
                            experimentAccession);
        }

        public AtlasResource<TsvReader> reactomePathwaysFiles(String experimentAccession, String comparison) {
            return new TsvFile.ReadOnly(
                    Paths.get(experimentsFilesLocation, "gxa").toString(),
                    REACTOME_PATHWAYS_FILE_PATH_TEMPLATE,
                    experimentAccession,
                    comparison);
        }
    }

    public final class RnaSeqBaselineExperimentFiles {
        public final BaselineExperimentFiles baselineExperimentFiles;

        public final AtlasResource<ObjectInputStream<String[]>> fpkms;
        public final AtlasResource<ObjectInputStream<String[]>> tpms;
        public final AtlasResource<ObjectInputStream<String[]>> transcriptsTpms;

        RnaSeqBaselineExperimentFiles(String experimentAccession) {
            baselineExperimentFiles =
                    new BaselineExperimentFiles(
                            Paths.get(experimentsFilesLocation, "gxa").toString(),
                            experimentAccession);

            fpkms =
                    new TsvFile.ReadAsStream(
                            Paths.get(experimentsFilesLocation, "gxa").toString(),
                            RNASEQ_BASELINE_FPKMS_FILE_PATH_TEMPLATE,
                            experimentAccession);
            tpms =
                    new TsvFile.ReadAsStream(
                            Paths.get(experimentsFilesLocation, "gxa").toString(),
                            RNASEQ_BASELINE_TPMS_FILE_PATH_TEMPLATE,
                            experimentAccession);

            transcriptsTpms =
                    new TsvFile.ReadAsStream(
                            Paths.get(experimentsFilesLocation, "gxa").toString(),
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

    public final class ProteomicsBaselineExperimentFiles {
        public final BaselineExperimentFiles baselineExperimentFiles;
        public final AtlasResource<ObjectInputStream<String[]>> main;

        ProteomicsBaselineExperimentFiles(String experimentAccession) {
            baselineExperimentFiles =
                    new BaselineExperimentFiles(
                            Paths.get(experimentsFilesLocation, "gxa").toString(),
                            experimentAccession);

            main =
                    new TsvFile.ReadAsStream(
                            Paths.get(experimentsFilesLocation, "gxa").toString(),
                            PROTEOMICS_BASELINE_EXPRESSION_FILE_PATH_TEMPLATE,
                            experimentAccession);
            }
    }

    public final class RnaSeqDifferentialExperimentFiles {
        public final DifferentialExperimentFiles differentialExperimentFiles;
        public final AtlasResource<ObjectInputStream<String[]>> analytics;
        public final AtlasResource<TsvReader> rawCounts;

        RnaSeqDifferentialExperimentFiles(String experimentAccession) {
            differentialExperimentFiles =
                    new DifferentialExperimentFiles(
                            Paths.get(experimentsFilesLocation, "gxa").toString(),
                            experimentAccession);

            analytics =
                    new TsvFile.ReadAsStream(
                            Paths.get(experimentsFilesLocation, "gxa").toString(),
                            DIFFERENTIAL_ANALYTICS_FILE_PATH_TEMPLATE,
                            experimentAccession);
            rawCounts =
                    new TsvFile.ReadOnly(
                            Paths.get(experimentsFilesLocation, "gxa").toString(),
                            DIFFERENTIAL_RAW_COUNTS_FILE_PATH_TEMPLATE,
                            experimentAccession);
        }
    }

    public final class MicroarrayExperimentFiles {
        public final DifferentialExperimentFiles differentialExperimentFiles;
        public final AtlasResource<ObjectInputStream<String[]>> analytics;
        public final AtlasResource<TsvReader> normalizedExpressions;    // Microarray 1-colour specific
        public final AtlasResource<TsvReader> logFoldChanges;           // Microarray 2-colour specific

        MicroarrayExperimentFiles(String experimentAccession, String arrayDesign) {
            differentialExperimentFiles =
                    new DifferentialExperimentFiles(
                            Paths.get(experimentsFilesLocation, "gxa").toString(),
                            experimentAccession);

            analytics =
                    new TsvFile.ReadAsStream(
                            Paths.get(experimentsFilesLocation, "gxa").toString(),
                            MICROARRAY_ANALYTICS_FILE_PATH_TEMPLATE,
                            experimentAccession, arrayDesign);

            normalizedExpressions =
                    new TsvFile.ReadOnly(
                            Paths.get(experimentsFilesLocation, "gxa").toString(),
                            MICROARRAY_NORMALIZED_EXPRESSIONS_FILE_PATH_TEMPLATE,
                            experimentAccession, arrayDesign);

            logFoldChanges =
                    new TsvFile.ReadOnly(
                            Paths.get(experimentsFilesLocation, "gxa").toString(),
                            MICROARRAY_LOG_FOLD_CHANGES_FILE_PATH_TEMPLATE,
                            experimentAccession, arrayDesign);
        }
    }

    public final class SingleCellExperimentFiles {
        public final ExperimentFiles experimentFiles;
        public final AtlasResource<MatrixMarketReader> tpmsMatrix;
        public final AtlasResource<TsvReader> geneIdsTsv;
        public final AtlasResource<TsvReader> cellIdsTsv;
        public final Map<Integer, ? extends AtlasResource<TsvReader>> tSnePlotTsvs;

        SingleCellExperimentFiles(String experimentAccession) {
            experimentFiles =
                    new ExperimentFiles(
                            Paths.get(experimentsFilesLocation, "scxa").toString(),
                            experimentAccession);

            tpmsMatrix =
                    new MatrixMarketFile(
                            Paths.get(experimentsFilesLocation, "scxa").toString(),
                            SINGLE_CELL_MATRIX_MARKET_TPMS_FILE_PATH_TEMPLATE,
                            experimentAccession);

            geneIdsTsv =
                    new TsvFile.ReadOnly(
                            Paths.get(experimentsFilesLocation, "scxa").toString(),
                            SINGLE_CELL_MATRIX_MARKET_TPMS_GENE_IDS_FILE_PATH_TEMPLATE,
                            experimentAccession);

            cellIdsTsv =
                    new TsvFile.ReadOnly(
                            Paths.get(experimentsFilesLocation, "scxa").toString(),
                            SINGLE_CELL_MATRIX_MARKET_TPMS_CELL_IDS_FILE_PATH_TEMPLATE,
                            experimentAccession);

            tSnePlotTsvs = discoverAvailablePerplexitiesFromTSnePlotFiles(experimentAccession).stream()
                    .collect(
                            Collectors.toMap(
                                    perplexity -> perplexity,
                                    perplexity -> new TsvFile.ReadOnly(
                                            experimentsFilesLocation,
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
                    Paths.get(experimentsFilesLocation).resolve(
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

    // After splitting the experiments directory to gxa and scxa we need to discover the experiment location because
    // sometimes we won’t know where it is (
    // Consider moving ExperimentSilo and the discocery method to a separate class if we go ahead with the plan of
    // supporting multi-species single cell experiments, whose outcome will be having a directory layout of the form
    // scxa/<species>/<accession>: scxa/mus_musculus/E-MTAB-9001, scxa/homo_sapiens/E-MTAB-9001
    private enum ExperimentSilo {
        GXA("gxa"),
        SCXA("scxa");

        private final String dir;

        ExperimentSilo(String dir) {
            this.dir = dir;
        }
    }

    private String discoverExperimentLocation(String experimentAccession) {
        for (ExperimentSilo silo : ExperimentSilo.values()) {
            if (Paths.get(experimentsFilesLocation, silo.dir, experimentAccession).toFile().isDirectory()) {
                return Paths.get(experimentsFilesLocation, silo.dir).toString();
            }
        }

        throw new ResourceNotFoundException(String.format("Experiment %s could not be found", experimentAccession));
    }
}
