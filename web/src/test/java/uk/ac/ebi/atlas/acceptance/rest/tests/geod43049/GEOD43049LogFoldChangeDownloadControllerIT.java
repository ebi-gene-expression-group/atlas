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

package uk.ac.ebi.atlas.acceptance.rest.tests.geod43049;


import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GEOD43049LogFoldChangeDownloadControllerIT {

    private EndPoint subject = new EndPoint("/gxa/experiments/E-GEOD-43049/logFold.tsv");

    @Test
    public void verifyHeader() {
        Response response = subject.getResponse();

        // http status code OK
        assertThat(response.getStatusCode(), is(200));

        // unicode encoded plain text
        assertThat(response.getContentType(), is("text/plain;charset=utf-8"));

        // filename of attachment should be ending in -query-results.tsv
        assertThat(response.getHeader("Content-Disposition"), containsString("-log-fold-changes.tsv"));
    }

    @Test
    public void verifyFirstLine() {

        List<String> firstLine = subject.getRowValues(0);

        assertThat(firstLine,
                contains("Gene ID", "Gene Name", "Design Element", "GSM1055612", "GSM1055613", "GSM1055614", "GSM1055615", "GSM1055616", "GSM1055617")
        );

    }

    @Test
    public void verifySecondLine() {

        List<String> secondLine = subject.getRowValues(1);

        assertThat(secondLine,
                contains("", "GE_BrightCorner", "0.371292374524635", "0.334825950656852", "0.4133498535358", "0.0338093072577259", "0.595093980055508", "-0.454786287469673")
        );

    }

    @Test
    public void verifyLenghtOfDocument() {
        ResponseBody body = subject.getResponseBody();

        String[] lines = body.asString().split("\n");
        assertThat(lines.length, is(151));
    }

}