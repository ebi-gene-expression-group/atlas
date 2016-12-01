package uk.ac.ebi.atlas.resource;

import com.google.gson.stream.JsonReader;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.XmlReader;
import uk.ac.ebi.atlas.commons.writers.TsvWriter;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.model.resource.JsonFile;
import uk.ac.ebi.atlas.model.resource.TsvFile;
import uk.ac.ebi.atlas.model.resource.XmlFile;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.nio.file.Paths;

@Named
public class DataFileHub {

    final String dataFilesLocation;

    final static String SPECIES_FILE_PATH = "/species/species.json";

    final static String CONFIGURATION_FILE_PATH_TEMPLATE = "/magetab/{0}/{0}-configuration.xml";
    final static String ANALYSIS_METHODS_FILE_PATH_TEMPLATE = "/magetab/{0}/{0}-analysis-methods.tsv";
    final static String EXPERIMENT_DESIGN_FILE_PATH_TEMPLATE = "/expdesign/ExpDesign-{0}.tsv";
    final static String CONDENSED_SDRF_FILE_PATH_TEMPLATE = "/magetab/{0}/{0}.condensed-sdrf.tsv";
    final static String IDF_FILE_PATH_TEMPLATE = "/magetab/{0}/{0}.idf.txt";
    final static String OP_LOG_FILE_PATH_TEMPLATE = "/admin/{0}-op-log.tsv";

    final static String EXPRESSION_FILE_PATH_TEMPLATE = "/magetab/{0}/{0}.tsv"; // For baseline and RNA-seq diff
    final static String FACTORS_FILE_PATH_TEMPLATE = "/magetab/{0}/{0}-factors.xml";
    final static String DIFFERENTIAL_ANALYTICS_FILE_PATH_TEMPLATE = "/magetab/{0}/{0}-analytics.tsv";
    final static String DIFFERENTIAL_RAW_COUNTS_FILE_PATH_TEMPLATE = "/magetab/{0}/{0}-raw-counts.tsv";
    final static String MICROARRAY_ANALYTICS_FILE_PATH_TEMPLATE = "/magetab/{0}/{0}_{1}-analytics.tsv";
    final static String MICROARRAY_NORMALIZED_EXPRESSIONS_FILE_PATH_TEMPLATE = "/magetab/{0}/{0}_{1}-normalized-expressions.tsv";
    final static String MICROARRAY_LOG_FOLD_CHANGES_FILE_PATH_TEMPLATE = "/magetab/{0}/{0}_{1}-log-fold-changes.tsv";

    @Inject
    public DataFileHub(@Value("#{configuration['dataFilesLocation']}") String dataFilesLocation){
        Validate.notNull(dataFilesLocation);
        this.dataFilesLocation = dataFilesLocation;
    }

    public SpeciesFiles getSpeciesFiles() {
        return new SpeciesFiles();
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

    public MicroarrayExperimentFiles getMicroarrayExperimentFiles(String experimentAccession, String arrayDesign) {
        return new MicroarrayExperimentFiles(experimentAccession, arrayDesign);
    }

    public class SpeciesFiles {
        public final AtlasResource<JsonReader> all = new JsonFile.ReadOnly(dataFilesLocation, SPECIES_FILE_PATH);
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

        ExperimentFiles(String experimentAccession){
            this.analysisMethods = new TsvFile.ReadOnly(dataFilesLocation, ANALYSIS_METHODS_FILE_PATH_TEMPLATE, experimentAccession);
            this.configuration = new XmlFile.ReadOnly(dataFilesLocation, CONFIGURATION_FILE_PATH_TEMPLATE, false, experimentAccession);

            this.experimentDesign = new TsvFile.ReadOnly(dataFilesLocation, EXPERIMENT_DESIGN_FILE_PATH_TEMPLATE, experimentAccession);
            this.experimentDesignWrite = new TsvFile.Overwrite(dataFilesLocation, EXPERIMENT_DESIGN_FILE_PATH_TEMPLATE, experimentAccession);

            this.condensedSdrf = new TsvFile.ReadOnly(dataFilesLocation, CONDENSED_SDRF_FILE_PATH_TEMPLATE, experimentAccession);

            this.idf = new TsvFile.ReadOnly(dataFilesLocation, IDF_FILE_PATH_TEMPLATE, experimentAccession);

            this.adminOpLog = new TsvFile.ReadOnly(dataFilesLocation, OP_LOG_FILE_PATH_TEMPLATE, experimentAccession);
            this.adminOpLogWrite = new TsvFile.Overwrite(dataFilesLocation, OP_LOG_FILE_PATH_TEMPLATE, experimentAccession);
            this.adminOpLogAppend = new TsvFile.Appendable(dataFilesLocation, OP_LOG_FILE_PATH_TEMPLATE, experimentAccession);
        }

    }

    public class BaselineExperimentFiles extends ExperimentFiles {
        public final AtlasResource<TsvReader> main;
        public final AtlasResource<XmlReader> factors;

        BaselineExperimentFiles(String experimentAccession) {
            super(experimentAccession);
            this.main = new TsvFile.ReadOnly(dataFilesLocation, EXPRESSION_FILE_PATH_TEMPLATE, experimentAccession);
            this.factors = new XmlFile.ReadOnly(dataFilesLocation, FACTORS_FILE_PATH_TEMPLATE, true, experimentAccession);
        }
    }

    public class DifferentialExperimentFiles extends ExperimentFiles {
        public final AtlasResource<TsvReader> analytics;
        public final AtlasResource<TsvReader> rawCounts;

        DifferentialExperimentFiles(String experimentAccession) {
            super(experimentAccession);
            this.analytics = new TsvFile.ReadOnly(dataFilesLocation, DIFFERENTIAL_ANALYTICS_FILE_PATH_TEMPLATE, experimentAccession);
            this.rawCounts = new TsvFile.ReadOnly(dataFilesLocation, DIFFERENTIAL_RAW_COUNTS_FILE_PATH_TEMPLATE, experimentAccession);
        }
    }

    public class MicroarrayExperimentFiles extends ExperimentFiles {
        public final AtlasResource<TsvReader> analytics;
        public final AtlasResource<TsvReader> normalizedExpressions;    // Microarray 1 colour specific
        public final AtlasResource<TsvReader> logFoldChanges;   // Microarray 2 colour specific

        MicroarrayExperimentFiles(String experimentAccession, String arrayDesign) {
            super(experimentAccession);
            analytics = new TsvFile.ReadOnly(dataFilesLocation, MICROARRAY_ANALYTICS_FILE_PATH_TEMPLATE, experimentAccession, arrayDesign);
            normalizedExpressions = new TsvFile.ReadOnly(dataFilesLocation, MICROARRAY_NORMALIZED_EXPRESSIONS_FILE_PATH_TEMPLATE, experimentAccession, arrayDesign);
            logFoldChanges = new TsvFile.ReadOnly(dataFilesLocation, MICROARRAY_LOG_FOLD_CHANGES_FILE_PATH_TEMPLATE, experimentAccession, arrayDesign);
        }
    }

    public String getExperimentDataLocation() {
        return Paths.get(dataFilesLocation, "magetab").toString() + "/";
    }

}
