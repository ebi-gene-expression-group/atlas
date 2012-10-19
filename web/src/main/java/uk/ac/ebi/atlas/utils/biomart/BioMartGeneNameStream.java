package uk.ac.ebi.atlas.utils.biomart;


import au.com.bytecode.opencsv.CSVReader;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.ObjectInputStream;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import static java.util.Arrays.asList;
import static uk.ac.ebi.atlas.utils.biomart.HttpClientHelper.httpPost;

public class BioMartGeneNameStream implements ObjectInputStream<String[]> {

    private CSVReader csvReader;

    @Override
    public String[] readNext() {
        try {
            return csvReader.readNext();
        } catch (IOException e) {
            throw new IllegalStateException("Exception thrown while reading next csv line: " + e.getMessage());
        }
    }

    @Override
    public void close() throws IOException {
        csvReader.close();

    }

    public void setCsvReader(CSVReader csvReader) {
        this.csvReader = csvReader;
    }

    @Named("bioMartGeneNameStreamBuilder")
    @Scope("prototype")
    public static class Builder {

        private HttpClient httpClient;

        @Inject
        public Builder(HttpClient httpClient) {
            this.httpClient = httpClient;
        }


        public ObjectInputStream<String[]> create() {
            try {
                InputStream inputStream = httpPost(httpClient, URI.create("http://www.ensembl.org/biomart/martservice?"),
                        asList(new BasicNameValuePair("query", MartQuery.buildGeneNameQuery().toString())
                        ));

                BioMartGeneNameStream bioMartGeneNameStream = new BioMartGeneNameStream();

                bioMartGeneNameStream.setCsvReader(new CSVReader(new InputStreamReader(inputStream), '\t'));

                return bioMartGeneNameStream;

            } catch (IOException e) {
                throw new IllegalStateException("Error while reading from biomart: " + e.getMessage());
            }
        }
    }

}
