package uk.ac.ebi.atlas.resource;

import au.com.bytecode.opencsv.CSVReader;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.writers.TsvWriter;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.model.resource.CsvFile;
import uk.ac.ebi.atlas.model.resource.TsvFile;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DataFileHub {

    private final String dataFilesLocation;
    final static String EXPRESSION_FILE_PATH_TEMPLATE = "/magetab/{0}/{0}.tsv";
    final static String ANALYSIS_METHODS_FILE_PATH_TEMPLATE = "/magetab/{0}/{0}-analysis-methods.tsv";
    final static String EXPERIMENT_DESIGN_FILE_PATH_TEMPLATE = "/expdesign/ExpDesign-{0}.tsv";
    final static String CONDENSED_SDRF_FILE_PATH_TEMPLATE = "/magetab/{0}/{0}.condensed-sdrf.tsv";
    final static String ANALYTICS_FILE_PATH_TEMPLATE = "/magetab/{0}/{0}-analytics.tsv";
    final static String RAW_COUNTS_FILE_PATH_TEMPLATE = "/magetab/{0}/{0}-raw-counts.tsv";
    final static String OP_LOG_FILE_PATH_TEMPLATE = "/admin/{0}-op-log.tsv";

    @Inject
    public DataFileHub(@Value("#{configuration['dataFilesLocation']}") String dataFilesLocation){
        Validate.notNull(dataFilesLocation);
        this.dataFilesLocation = dataFilesLocation;
    }

    public ExperimentFiles getExperimentFiles(String experimentAccession){
        return new ExperimentFiles(experimentAccession);
    }

    public DifferentialExperimentFiles getDifferentialExperimentFiles(String experimentAccession){
        return new DifferentialExperimentFiles(experimentAccession);
    }

    public class ExperimentFiles {

        public final AtlasResource<TsvReader> analysisMethods;
        public final AtlasResource<TsvReader> experimentDesign;
        public final AtlasResource<TsvWriter> experimentDesignWrite;
        public final AtlasResource<CSVReader> main;
        public final AtlasResource<TsvReader> condensedSdrf;
        public final AtlasResource<TsvReader> adminOpLog;
        public final AtlasResource<TsvWriter> adminOpLogWrite;
        public final AtlasResource<TsvWriter> adminOpLogAppend;

        ExperimentFiles(String experimentAccession){
            this.analysisMethods = new TsvFile.ReadOnly(dataFilesLocation, ANALYSIS_METHODS_FILE_PATH_TEMPLATE, experimentAccession);
            this.experimentDesign = new TsvFile.ReadOnly(dataFilesLocation, EXPERIMENT_DESIGN_FILE_PATH_TEMPLATE, experimentAccession);
            this.experimentDesignWrite = new TsvFile.Overwrite(dataFilesLocation, EXPERIMENT_DESIGN_FILE_PATH_TEMPLATE, experimentAccession);
            this.main = new CsvFile(dataFilesLocation, EXPRESSION_FILE_PATH_TEMPLATE, experimentAccession);
            this.condensedSdrf = new TsvFile.ReadOnly(dataFilesLocation, CONDENSED_SDRF_FILE_PATH_TEMPLATE, experimentAccession);
            this.adminOpLog = new TsvFile.ReadOnly(dataFilesLocation, OP_LOG_FILE_PATH_TEMPLATE, experimentAccession);
            this.adminOpLogWrite = new TsvFile.Overwrite(dataFilesLocation, OP_LOG_FILE_PATH_TEMPLATE, experimentAccession);
            this.adminOpLogAppend = new TsvFile.Appendable(dataFilesLocation, OP_LOG_FILE_PATH_TEMPLATE, experimentAccession);
        }

    }

    public class DifferentialExperimentFiles extends ExperimentFiles {

        public final AtlasResource<CSVReader> analytics;
        public final AtlasResource<CSVReader> rawCounts;

        DifferentialExperimentFiles(String experimentAccession){
            super(experimentAccession);
            this.analytics = new CsvFile(dataFilesLocation, ANALYTICS_FILE_PATH_TEMPLATE, experimentAccession);
            this.rawCounts = new CsvFile(dataFilesLocation, RAW_COUNTS_FILE_PATH_TEMPLATE, experimentAccession);
        }
    }

}
