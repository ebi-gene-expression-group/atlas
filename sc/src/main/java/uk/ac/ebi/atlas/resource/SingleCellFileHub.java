package uk.ac.ebi.atlas.resource;

import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.model.resource.TsvFile;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SingleCellFileHub extends DataFileHub {

    final static String SINGLECELL_FILE_PATH_TEMPLATE = "/magetab/{0}/{0}.tsv";

    @Inject
    public SingleCellFileHub(@Value("#{configuration['dataFilesLocation']}") String dataFilesLocation) {
        super(dataFilesLocation);
    }

    public AtlasResource<TsvReader> getGuysIdentifiers() {
        return new TsvFile.ReadOnly(dataFilesLocation, "/sc/guyIdToEnsemblId.tsv");
    }

    public SingleCellExperimentFiles getSingleCellExperimentFiles(String experimentAccession) {
        return new SingleCellExperimentFiles(experimentAccession);
    }

    public class SingleCellExperimentFiles extends ExperimentFiles {
        public final AtlasResource<ObjectInputStream<String[]>> data;

        SingleCellExperimentFiles(String experimentAccession) {
            super(experimentAccession);
            this.data = new TsvFile.ReadAsStream(dataFilesLocation, SINGLECELL_FILE_PATH_TEMPLATE, experimentAccession);

        }
    }
}
