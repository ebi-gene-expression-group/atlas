package uk.ac.ebi.atlas.resource;

import au.com.bytecode.opencsv.CSVReader;
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

    @Inject
    public DataFileHub(@Value("#{configuration['dataFilesLocation']}") String dataFilesLocation){
        this.dataFilesLocation = dataFilesLocation;
    }

    public ExperimentFiles  getExperimentFiles(String experimentAccession){
        return new ExperimentFiles(experimentAccession);
    }

    public DifferentialExperimentFiles getDifferentialExperimentFiles(String experimentAccession){
        return new DifferentialExperimentFiles(experimentAccession);
    }

        /*
    Replaces:
    (done) experiment.experiment-design.path.template = ${data.files.location}/expdesign/ExpDesign-{0}.tsv
    diff.experiment.data.path.template = ${data.files.location}/magetab/{0}/{0}-analytics.tsv
    diff.experiment.raw-counts.path.template = ${data.files.location}/magetab/{0}/{0}-raw-counts.tsv
    experiment.magetab.path.template = ${data.files.location}/magetab/{0}/{0}.tsv
    experiment.condensed-sdrf.path.template = ${data.files.location}/magetab/{0}/{0}.condensed-sdrf.tsv
    (done) experiment.op_log.template = ${data.files.location}/admin/{0}-op-log.tsv



     */

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
            this.analysisMethods = new TsvFile.ReadOnly(dataFilesLocation, "/magetab/{0}/{0}-analysis-methods.tsv",
                    experimentAccession);
            this.experimentDesign = new TsvFile.ReadOnly(dataFilesLocation, "/expdesign/ExpDesign-{0}.tsv", experimentAccession);
            this.experimentDesignWrite = new TsvFile.Overwrite(dataFilesLocation, "/expdesign/ExpDesign-{0}.tsv", experimentAccession);
            this.main = new CsvFile(dataFilesLocation, "/magetab/{0}/{0}.tsv", experimentAccession);
            this.condensedSdrf = new TsvFile.ReadOnly(dataFilesLocation, "/magetab/{0}/{0}.condensed-sdrf.tsv", experimentAccession);
            this.adminOpLog = new TsvFile.ReadOnly(dataFilesLocation, "/admin/{0}-op-log.tsv", experimentAccession);
            this.adminOpLogWrite = new TsvFile.Overwrite(dataFilesLocation, "/admin/{0}-op-log.tsv", experimentAccession);
            this.adminOpLogAppend = new TsvFile.Appendable(dataFilesLocation, "/admin/{0}-op-log.tsv", experimentAccession);
        }

    }

    class DifferentialExperimentFiles extends ExperimentFiles {

        public final TsvFile<TsvReader> analytics;
        public final TsvFile<TsvReader> rawCounts;

        DifferentialExperimentFiles(String experimentAccession){
            super(experimentAccession);
            this.analytics = new TsvFile.ReadOnly(dataFilesLocation, "/magetab/{0}/{0}-analytics.tsv", experimentAccession);
            this.rawCounts = new TsvFile.ReadOnly(dataFilesLocation, "/magetab/{0}/{0}-raw-counts.tsv", experimentAccession);
        }
    }

}
