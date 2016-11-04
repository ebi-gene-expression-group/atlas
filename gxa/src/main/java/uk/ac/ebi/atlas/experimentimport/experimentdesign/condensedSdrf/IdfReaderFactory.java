package uk.ac.ebi.atlas.experimentimport.experimentdesign.condensedSdrf;

import uk.ac.ebi.atlas.commons.readers.FileTsvReaderBuilder;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.UrlTsvReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@Scope("singleton")
public class IdfReaderFactory {

    private UrlTsvReaderBuilder urlTsvReaderBuilder;
    private FileTsvReaderBuilder fileTsvReaderBuilder;

    @Inject
    public IdfReaderFactory(@Value("#{configuration['experiment.magetab.idf.url.template']}") String idfUrlTemplate,
                            @Value("#{configuration['experiment.magetab.idf.path.template']}") String idfPathTemplate,
                            UrlTsvReaderBuilder urlTsvReaderBuilder, FileTsvReaderBuilder fileTsvReaderBuilder) {
        this.urlTsvReaderBuilder = urlTsvReaderBuilder.forTsvFileUrlTemplate(idfUrlTemplate);
        this.fileTsvReaderBuilder = fileTsvReaderBuilder.forTsvFilePathTemplate(idfPathTemplate);
    }

    public TsvReader create(String experimentAccession) {
        try {
            return urlTsvReaderBuilder.withExperimentAccession(experimentAccession).build();
        } catch (IOException e) {
            return fileTsvReaderBuilder.withExperimentAccession(experimentAccession).build();
        }
    }

}