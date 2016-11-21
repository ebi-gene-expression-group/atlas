package uk.ac.ebi.atlas.experimentimport.condensedSdrf;

import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.UrlTsvReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class IdfReaderFactory {

    private UrlTsvReaderBuilder urlTsvReaderBuilder;
    private DataFileHub dataFileHub;

    @Inject
    public IdfReaderFactory(@Value("#{configuration['experiment.magetab.idf.url.template']}") String idfUrlTemplate,
                            UrlTsvReaderBuilder urlTsvReaderBuilder,
                            DataFileHub dataFileHub) {
        this.urlTsvReaderBuilder = urlTsvReaderBuilder.forTsvFileUrlTemplate(idfUrlTemplate);
        this.dataFileHub = dataFileHub;
    }

    public TsvReader create(String experimentAccession) {
        try {
            return urlTsvReaderBuilder.withExperimentAccession(experimentAccession).build();
        } catch (IOException e) {
            return dataFileHub.getExperimentFiles(experimentAccession).idf.get();
        }
    }

}