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

package uk.ac.ebi.atlas.acceptance.rest.tests.geod22351;


import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GEOD22351RnaSeqExperimentDownloadControllerIT {

    private EndPoint subject = new EndPoint("/gxa/experiments/E-GEOD-22351.tsv");

    @Test
    public void verifyHeader() {
        Response response = subject.getResponse();

        // http status code OK
        assertThat(response.getStatusCode(), is(200));

        // unicode encoded plain text
        assertThat(response.getContentType(), is("text/plain;charset=utf-8"));

        // filename of attachment should be ending in -query-results.tsv
        assertThat(response.getHeader("Content-Disposition"), containsString("-query-results.tsv"));
    }

    @Test
    public void verifyProjectVersionHeader() {

        List<String> firstLine = subject.getRowValues(0);

        assertThat(firstLine,
                contains("# Expression Atlas version: 3.0")
        );

    }

    @Test
    public void verifyTimestampHeader() {

        List<String> firstLine = subject.getRowValues(3);

        assertThat(firstLine,
                contains("Gene ID", "Gene Name", "genotype:'expressing human TDP-43' vs 'non transgenic'.p-value", "genotype:'expressing human TDP-43' vs 'non transgenic'.log2foldchange")
        );

    }


    @Test
    public void verifyHeaders() {

        List<String> firstLine = subject.getRowValues(3);

        assertThat(firstLine,
                contains("Gene ID", "Gene Name", "genotype:'expressing human TDP-43' vs 'non transgenic'.p-value", "genotype:'expressing human TDP-43' vs 'non transgenic'.log2foldchange")
        );

    }

    @Test
    public void verifySecondLine() {

        List<String> firstLine = subject.getRowValues(4);

        assertThat(firstLine,
                contains("ENSMUSG00000050370", "Ch25h", "1.70428798138445E-6", "3.01033089730209")
        );

        List<String> secondLine = subject.getRowValues(5);

        assertThat(secondLine,
                contains("ENSMUSG00000024799", "Tm7sf2", "0.0137444998099392", "-1.15843751459071")
        );

    }

    @Test
    public void verifyLenghtOfDocument() {
        ResponseBody body = subject.getResponseBody();

        String[] lines = body.asString().split("\n");
        assertThat(lines.length, is(53));
    }

}