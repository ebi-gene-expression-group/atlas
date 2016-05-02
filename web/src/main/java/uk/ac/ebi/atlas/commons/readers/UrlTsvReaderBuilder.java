package uk.ac.ebi.atlas.commons.readers;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.readers.impl.TsvReaderImpl;

import javax.inject.Named;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.MessageFormat;

@Named
@Scope("prototype")
public class UrlTsvReaderBuilder {

    private String experimentAccession;
    private String tsvFileUrlTemplate;

    public UrlTsvReaderBuilder withExperimentAccession(String experimentAccession){
        this.experimentAccession = experimentAccession;
        return this;
    }

    public UrlTsvReaderBuilder forTsvFileUrlTemplate(String tsvFileUrlTemplate) {
        this.tsvFileUrlTemplate = tsvFileUrlTemplate;
        return this;
    }

    public TsvReader build() throws IOException {
        String tsvFileUrlAsString = MessageFormat.format(tsvFileUrlTemplate, experimentAccession);

        URL tsvFileUrl = new URL(tsvFileUrlAsString);
        InputStreamReader tsvFileInputStreamReader = new InputStreamReader(tsvFileUrl.openStream());
        return new TsvReaderImpl(tsvFileInputStreamReader);
    }

}
