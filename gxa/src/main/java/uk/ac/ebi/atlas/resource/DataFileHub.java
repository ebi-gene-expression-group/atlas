package uk.ac.ebi.atlas.resource;

import org.springframework.beans.factory.annotation.Value;
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
    experiment.experiment-design.path.template = ${data.files.location}/expdesign/ExpDesign-{0}.tsv
    diff.experiment.data.path.template = ${data.files.location}/magetab/{0}/{0}-analytics.tsv
    diff.experiment.raw-counts.path.template = ${data.files.location}/magetab/{0}/{0}-raw-counts.tsv
    experiment.magetab.path.template = ${data.files.location}/magetab/{0}/{0}.tsv
    experiment.condensed-sdrf.path.template = ${data.files.location}/magetab/{0}/{0}.condensed-sdrf.tsv
    experiment.op_log.template = ${data.files.location}/admin/{0}-op-log.tsv



     */

    class ExperimentFiles {

        public final TsvFile analysisMethods;
        public final TsvFile experimentDesign;
        public final TsvFile main;
        public final TsvFile condensedSdrf;
        public final TsvFile adminOpLog;

        ExperimentFiles(String experimentAccession){
            this.analysisMethods = new TsvFile(dataFilesLocation, "/magetab/{0}/{0}-analysis-methods.tsv", experimentAccession);
            this.experimentDesign = new TsvFile(dataFilesLocation, "/expdesign/ExpDesign-{0}.tsv", experimentAccession);
            this.main = new TsvFile(dataFilesLocation, "/magetab/{0}/{0}.tsv", experimentAccession);
            this.condensedSdrf = new TsvFile(dataFilesLocation, "/magetab/{0}/{0}.condensed-sdrf.tsv", experimentAccession);
            this.adminOpLog = new TsvFile(dataFilesLocation, "/admin/{0}-op-log.tsv", experimentAccession);
        }

    }

    class DifferentialExperimentFiles extends ExperimentFiles {

        public final TsvFile analytics;
        public final TsvFile rawCounts;

        DifferentialExperimentFiles(String experimentAccession){
            super(experimentAccession);
            this.analytics = new TsvFile(dataFilesLocation, "/magetab/{0}/{0}-analytics.tsv", experimentAccession);
            this.rawCounts = new TsvFile(dataFilesLocation, "/magetab/{0}/{0}-raw-counts.tsv", experimentAccession);
        }
    }

}
