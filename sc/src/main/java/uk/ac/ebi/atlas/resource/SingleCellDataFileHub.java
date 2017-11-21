package uk.ac.ebi.atlas.resource;

import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.model.resource.TsvFile;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SingleCellDataFileHub extends DataFileHub {

    private final static String SINGLE_CELL_FILE_PATH_TEMPLATE = "scxa/magetab/{0}/{0}.tsv";

    @Inject
    public SingleCellDataFileHub(@Value("#{configuration['experimentsFilesLocation']}") String dataFilesLocation) {
        super(dataFilesLocation);
    }

    public SingleCellExperimentFiles getSingleCellExperimentFiles(String experimentAccession) {
        return new SingleCellExperimentFiles(experimentAccession);
    }

    public class SingleCellExperimentFiles extends ExperimentFiles {
        public final AtlasResource<ObjectInputStream<String[]>> data;

        SingleCellExperimentFiles(String experimentAccession) {
            super(experimentAccession);
            this.data = new TsvFile.ReadAsStream(experimentsFilesLocation, SINGLE_CELL_FILE_PATH_TEMPLATE, experimentAccession);

        }
    }
}
