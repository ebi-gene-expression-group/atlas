package uk.ac.ebi.atlas.resource;

import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.model.resource.TsvFile;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SingleCellFileHub extends DataFileHub {

    @Inject
    public SingleCellFileHub(@Value("#{configuration['dataFilesLocation']}") String dataFilesLocation) {
        super(dataFilesLocation);
    }

    public AtlasResource<TsvReader> getGuysIdentifiers() {
        return new TsvFile.ReadOnly(dataFilesLocation, "/sc/guyIdToEnsemblId.tsv");
    }
}
