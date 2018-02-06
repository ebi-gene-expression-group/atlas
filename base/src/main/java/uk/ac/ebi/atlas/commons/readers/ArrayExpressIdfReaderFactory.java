package uk.ac.ebi.atlas.commons.readers;

import javax.inject.Named;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.MessageFormat;

@Named
public class ArrayExpressIdfReaderFactory {
    private static final String ARRAYEXPRESS_IDF_TEMPLATE = "https://www.ebi.ac.uk/arrayexpress/files/{0}/{0}.idf.txt";

    public TsvReader createArrayExpressIdfReader(String experimentAccession) throws IOException{
        String tsvFileUrlAsString = MessageFormat.format(ARRAYEXPRESS_IDF_TEMPLATE, experimentAccession);

        URL tsvFileUrl = new URL(tsvFileUrlAsString);
        InputStreamReader tsvFileInputStreamReader = new InputStreamReader(tsvFileUrl.openStream());
        return new TsvReader(tsvFileInputStreamReader);
    }
}
