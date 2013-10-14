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

package uk.ac.ebi.atlas.acceptance.rest.tests.geod3779;


import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GEOD3779AnalyticsDownloadControllerIT {

    private EndPoint subject = new EndPoint("/gxa/experiments/E-GEOD-3779/all-analytics.tsv");

    @Test
    public void verifyHeader() {
        Response response = subject.getResponse();

        // http status code OK
        assertThat(response.getStatusCode(), is(200));

        // unicode encoded plain text
        assertThat(response.getContentType(), is("application/octet-stream;charset=ISO-8859-1"));

        // filename of attachment should be a zip
        assertThat(response.getHeader("Content-Disposition"), is("attachment; filename=\"E-GEOD-3779-analytics.tsv.zip\""));
    }

    @Test
    public void verifyLenghtOfDocument() throws Exception {
        ResponseBody body = subject.getResponseBody();

        ZipInputStream zipInputStream = new ZipInputStream(body.asInputStream());
        int entries = 0;
        while (zipInputStream.getNextEntry() != null) {
            entries++;
        }
        assertThat(entries, is(2));
    }

    @Test
    public void verifyFirstEntry() throws Exception {
        ResponseBody body = subject.getResponseBody();

        ZipInputStream zipInputStream = new ZipInputStream(body.asInputStream());
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        assertThat(zipEntry.getName(), is("E-GEOD-3779_A-AFFY-23-analytics.tsv"));

        BufferedReader reader = new BufferedReader(new InputStreamReader(zipInputStream));
        assertThat(reader.readLine(), is("Gene ID\tGene name\tDesign Element\tgenotype:'p107 -/-' vs 'wild type' on A-AFFY-23.p-value\tgenotype:'p107 -/-' vs 'wild type' on A-AFFY-23.t-statistic\tgenotype:'p107 -/-' vs 'wild type' on A-AFFY-23.log2foldchange"));
        assertThat(reader.readLine(), is("ENSMUSG00000041538\tH2-Ob\t1422201_at\t0.974206814501029\t0.44248894656976\t0.0761328916666661"));
    }

    @Test
    public void verifySecondEntry() throws Exception {
        ResponseBody body = subject.getResponseBody();

        ZipInputStream zipInputStream = new ZipInputStream(body.asInputStream());
        zipInputStream.getNextEntry();
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        assertThat(zipEntry.getName(), is("E-GEOD-3779_A-AFFY-24-analytics.tsv"));

        BufferedReader reader = new BufferedReader(new InputStreamReader(zipInputStream));
        assertThat(reader.readLine(), is("Gene ID\tGene name\tDesign Element\tgenotype:'p107 -/-' vs 'wild type' on A-AFFY-24.p-value\tgenotype:'p107 -/-' vs 'wild type' on A-AFFY-24.t-statistic\tgenotype:'p107 -/-' vs 'wild type' on A-AFFY-24.log2foldchange"));
        assertThat(reader.readLine(), is("ENSMUSG00000028385\tSnx30\t1456479_at\t0.02\t0.120394369986336\t0.00641014999999978"));
    }

}