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

package uk.ac.ebi.atlas.web.controllers.rest;


import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.junit.Test;
import uk.ac.ebi.atlas.web.controllers.rest.pages.GeneProfilesDownloadPage;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GeneProfilesDownloadControllerIT {

    GeneProfilesDownloadPage subject = new GeneProfilesDownloadPage("");

    @Test
    public void verifyHeader() {
        Response response = subject.getResponse();

        // http status code OK
        assertThat(response.getStatusCode(), is(200));

        // unicode encoded plain text
        assertThat(response.getContentType(), is("text/plain;charset=utf-8"));

        // filename of attachment should be ending in -gene-expression-profiles.tsv
        assertThat(response.getHeader("Content-Disposition"), containsString("-gene-expression-profiles.tsv"));
    }

    @Test
    public void verifyFirstLine() {
        ResponseBody body = subject.getResponseBody();

        List<String> firstLine = getRowAsList(body, 0);

        assertThat(firstLine,
                hasItems("Gene name", "Gene Id", "adipose", "adrenal", "brain", "breast", "colon", "heart", "kidney", "liver", "lung", "lymph node", "ovary", "prostate", "skeletal muscle", "testes", "thyroid", "white blood cells")
        );

    }

    @Test
    public void verifySecondLine() {
        ResponseBody body = subject.getResponseBody();

        List<String> secondLine = getRowAsList(body, 1);

        assertThat(secondLine,
                hasItems("METTL25", "ENSG00000127720", "0", "2", "0.7", "2", "0.9", "2", "5", "4", "0.9", "2", "3", "3", "1", "3", "3", "4")
        );

    }

    @Test
    public void verifyLenghtOfDocument() {
        ResponseBody body = subject.getResponseBody();

        String[] lines = body.asString().split("\n");
        assertThat(lines.length, is(160));
    }

    private List<String> getRowAsList(ResponseBody body, int row) {
        String bodyAsString = body.asString();
        String[] lines = bodyAsString.split("\n");
        String line = lines[row];
        return Arrays.asList(line.split("\t"));
    }

}