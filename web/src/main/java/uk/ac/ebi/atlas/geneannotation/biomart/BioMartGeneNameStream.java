/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.geneannotation.biomart;


import au.com.bytecode.opencsv.CSVReader;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.util.Arrays.asList;
import static uk.ac.ebi.atlas.commons.HttpRequest.httpPost;

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

        private static final String ENSEMBL_GENE_ID_ATTRIBUTE = "ensembl_gene_id";
        private static final String EXTERNAL_GENE_ID_ATTRIBUTE = "external_gene_id";
        private HttpClient httpClient;
        private MartQueryBuilder queryBuilder;

        @Inject
        public Builder(HttpClient httpClient, MartQueryBuilder queryBuilder) {
            this.httpClient = httpClient;
            this.queryBuilder = queryBuilder;
        }

        public ObjectInputStream<String[]> create(String organism) {

            String martXmlQueryString = queryBuilder.setDataset(organism)
                    .addAttribute(ENSEMBL_GENE_ID_ATTRIBUTE)
                    .addAttribute(EXTERNAL_GENE_ID_ATTRIBUTE)
                    .build();

            InputStream inputStream = getBioMartInputStream(martXmlQueryString);

            BioMartGeneNameStream bioMartGeneNameStream = new BioMartGeneNameStream();

            bioMartGeneNameStream.setCsvReader(new CSVReader(new InputStreamReader(inputStream), '\t'));

            return bioMartGeneNameStream;

        }

        private InputStream getBioMartInputStream(String martXmlQueryString) {
            InputStream inputStream;
            try {
                inputStream = httpPost(httpClient, "http://www.ensembl.org/biomart/martservice?",
                        asList(new BasicNameValuePair("query", martXmlQueryString)
                        ));
            } catch (IOException e) {
                throw new IllegalStateException("Error while reading from biomart: " + e.getMessage());
            }
            return inputStream;
        }
    }

}
