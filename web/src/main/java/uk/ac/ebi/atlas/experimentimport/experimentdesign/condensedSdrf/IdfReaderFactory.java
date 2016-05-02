package uk.ac.ebi.atlas.experimentimport.experimentdesign.condensedSdrf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.readers.FileTsvReaderBuilder;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.UrlTsvReaderBuilder;

import javax.inject.Named;
import java.io.IOException;

@Named
@Scope("singleton")
public class IdfReaderFactory {

    @Value("#{configuration['experiment.magetab.idf.path.template']}")
    private String idfPathTemplate;

    @Value("#{configuration['experiment.magetab.idf.url.template']}")
    private String idfUrlTemplate;

    private UrlTsvReaderBuilder urlTsvReaderBuilder;
    private FileTsvReaderBuilder fileTsvReaderBuilder;

    public IdfReaderFactory() {
        this.urlTsvReaderBuilder = new UrlTsvReaderBuilder().forTsvFileUrlTemplate(idfUrlTemplate);
        this.fileTsvReaderBuilder = new FileTsvReaderBuilder().forTsvFilePathTemplate(idfPathTemplate);
    }

    public TsvReader create(String experimentAccession) {
        try {
            return urlTsvReaderBuilder.withExperimentAccession(experimentAccession).build();
        } catch (IOException e) {
            return fileTsvReaderBuilder.withExperimentAccession(experimentAccession).build();
        }
    }

}