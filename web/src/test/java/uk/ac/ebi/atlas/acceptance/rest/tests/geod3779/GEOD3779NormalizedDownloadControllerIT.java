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

public class GEOD3779NormalizedDownloadControllerIT {

    private EndPoint subject = new EndPoint("/gxa/experiments/E-GEOD-3779/normalized.tsv");

    @Test
    public void verifyHeader() {
        Response response = subject.getResponse();

        // http status code OK
        assertThat(response.getStatusCode(), is(200));

        // unicode encoded plain text
        assertThat(response.getContentType(), is("application/octet-stream;charset=ISO-8859-1"));

        // filename of attachment should be a zip
        assertThat(response.getHeader("Content-Disposition"), is("attachment; filename=\"E-GEOD-3779-normalized-expressions.tsv.zip\""));
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
        assertThat(zipEntry.getName(), is("E-GEOD-3779_A-AFFY-23-normalized-expressions.tsv"));

        BufferedReader reader = new BufferedReader(new InputStreamReader(zipInputStream));
        assertThat(reader.readLine(), is("Gene name\tDesign Element\t9859-11, chip MOE430A\t9619-3 p107 -/-, chip MOE430A\t9619-6, chip MOE430A\t9859-12 p107 -/-, chip MOE430A\t9887-4, chip MOE430A\t9447-4 -/-, chip MOE430A\t9887-6 p107 -/-, chip MOE430A"));
        assertThat(reader.readLine(), is("Atp6v0d1\t1415671_at\t8.484999\t8.020546\t8.057817\t8.076476\t8.4062805\t7.7016144\t8.34271"));
    }

    @Test
    public void verifySecondEntry() throws Exception {
        ResponseBody body = subject.getResponseBody();

        ZipInputStream zipInputStream = new ZipInputStream(body.asInputStream());
        zipInputStream.getNextEntry();
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        assertThat(zipEntry.getName(), is("E-GEOD-3779_A-AFFY-24-normalized-expressions.tsv"));

        BufferedReader reader = new BufferedReader(new InputStreamReader(zipInputStream));
        assertThat(reader.readLine(), is("Gene name\tDesign Element\t9619-3 p107 -/-, chip MOE430B\t9859-11, chip MOE430B\t9887-6 p107 -/-, chip MOE430B\t9447-4 -/-, chip MOE430B\t9859-12 p107 -/-, chip MOE430B\t9887-4, chip MOE430B\t9619-6, chip MOE430B"));
        assertThat(reader.readLine(), is("Atp6v0d1\t1415671_at\t7.6078053\t8.543172\t8.350148\t7.5729947\t7.837444\t8.3335\t7.5833025"));
    }

}