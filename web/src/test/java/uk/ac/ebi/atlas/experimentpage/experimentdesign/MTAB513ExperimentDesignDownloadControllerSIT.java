/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.experimentpage.experimentdesign;


import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MTAB513ExperimentDesignDownloadControllerSIT {

    private EndPoint subject = new EndPoint("/gxa/experiments/E-MTAB-513/experiment-design.tsv");

    @Test
    public void verifyHeader() {
        Response response = subject.getResponse();

        // http status code OK
        assertThat(response.getStatusCode(), is(200));

        // unicode encoded plain text
        assertThat(response.getContentType(), is("text/plain;charset=utf-8"));

        // filename of attachment should be ending in -experiment-design.tsv
        assertThat(response.getHeader("Content-Disposition"), containsString("-experiment-design.tsv"));
    }

    @Test
    public void verifyFirstLine() {
        ResponseBody body = subject.getResponseBody();

        List<String> firstLine = subject.getRowValues(0);

        assertThat(firstLine,
                contains("Run", "Sample Characteristic[age]", "Sample Characteristic Ontology Term[age]", "Sample Characteristic[ethnic group]", "Sample Characteristic Ontology Term[ethnic group]", "Sample Characteristic[organism]", "Sample Characteristic Ontology Term[organism]", "Sample Characteristic[organism part]", "Sample Characteristic Ontology Term[organism part]", "Sample Characteristic[sex]", "Sample Characteristic Ontology Term[sex]", "Factor Value[organism part]", "Factor Value Ontology Term[organism part]", "Analysed")
        );

    }

    @Test
    public void verifySecondLine() {
        ResponseBody body = subject.getResponseBody();

        List<String> secondLine = subject.getRowValues(1);

        assertThat(secondLine,
                contains("ERR030856", "", "", "", "", "Homo sapiens", "http://purl.obolibrary.org/obo/NCBITaxon_9606", "16 tissues mixture", "", "", "", "16 tissues mixture", "", "No")
        );

    }

    @Test
    public void verifyUsedLine() {

        ResponseBody body = subject.getResponseBody();

        List<String> line = subject.getRowValues(17);

        assertThat(line,
                // TODO https://www.pivotaltracker.com/story/show/100371514
                // contains("ERR030872", "60 years", "", "Caucasian", "http://www.ebi.ac.uk/efo/EFO_0003156", "Homo sapiens", "http://purl.obolibrary.org/obo/NCBITaxon_9606", "thyroid", "http://purl.obolibrary.org/obo/UBERON_0002046", "female", "http://www.ebi.ac.uk/efo/EFO_0001265", "thyroid", "http://purl.obolibrary.org/obo/UBERON_0002046", "Yes")
                contains("ERR030872", "60 year", "", "Caucasian", "http://www.ebi.ac.uk/efo/EFO_0003156", "Homo sapiens", "http://purl.obolibrary.org/obo/NCBITaxon_9606", "thyroid", "http://purl.obolibrary.org/obo/UBERON_0002046", "female", "http://www.ebi.ac.uk/efo/EFO_0001265", "thyroid", "http://purl.obolibrary.org/obo/UBERON_0002046", "Yes")
        );

    }

    @Test
    public void verifyLenghtOfDocument() {
        ResponseBody body = subject.getResponseBody();

        String[] lines = body.asString().split("\n");
        assertThat(lines.length, is(49));
    }

}