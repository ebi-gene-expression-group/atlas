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

package uk.ac.ebi.atlas.acceptance.rest.tests.geod3307;


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

public class GEOD3307AnalyticsDownloadControllerIT {

    private EndPoint subject = new EndPoint("/gxa/experiments/E-GEOD-3307/all-analytics.tsv");

    @Test
    public void verifyHeader() {
        Response response = subject.getResponse();

        // http status code OK
        assertThat(response.getStatusCode(), is(200));

        // unicode encoded plain text
        assertThat(response.getContentType(), is("application/octet-stream;charset=ISO-8859-1"));

        // filename of attachment should be a zip
        assertThat(response.getHeader("Content-Disposition"), is("attachment; filename=\"E-GEOD-3307-analytics.tsv.zip\""));
    }

    @Test
    public void verifyLengthOfDocument() throws Exception {
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
        assertThat(zipEntry.getName(), is("E-GEOD-3307_A-AFFY-33-analytics.tsv"));

        BufferedReader reader = new BufferedReader(new InputStreamReader(zipInputStream));
        assertThat(reader.readLine(), is("Gene ID\tGene Name\tDesign Element\t'EDMD XR' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'EDMD XR' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic\t'EDMD XR' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange\t'DMD' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'DMD' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic\t'DMD' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange\t'FSHD' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'FSHD' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic\t'FSHD' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange\t'LGMD2B' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'LGMD2B' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic\t'LGMD2B' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange\t'LGMD2I' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'LGMD2I' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic\t'LGMD2I' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange\t'HSP' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'HSP' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic\t'HSP' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange\t'ALS' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'ALS' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic\t'ALS' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange\t'LGMD2A' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'LGMD2A' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic\t'LGMD2A' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange\t'JDM' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'JDM' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic\t'JDM' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange\t'BMD' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'BMD' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic\t'BMD' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange\t'EDMD AD' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'EDMD AD' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic\t'EDMD AD' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange\t'AQM' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'AQM' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic\t'AQM' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange"));
        assertThat(reader.readLine(), is("ENSG00000000003\tTSPAN6\t209108_at\t0.0809842994761046\t2.81351332850746\t0.428631473561202\t0.0110142369806691\t3.27165792658763\t0.453232593478132\t0.174972860926135\t1.76514340370992\t0.181209088899287\t0.542438175707591\t0.946711686833612\t0.10798646883933\t0.929533450226814\t-0.166662788243064\t-0.0193122062673687\t0.129939248051952\t-2.09607452028685\t-0.309940413618155\t0.0119843116644908\t3.12520915964694\t0.313794065216056\t0.987448451377842\t-0.0304880024515985\t-0.0031613869327618\t3.22392001630122e-05\t5.02112235896101\t0.413107096522765\t0.913714594454733\t-0.220736875597542\t-0.0320392390623248\t0.226199358268869\t1.6013541775251\t0.352179635764147\t0.448157690443233\t0.918279612843084\t0.120919709872407"));
    }

    @Test
    public void verifySecondEntry() throws Exception {
        ResponseBody body = subject.getResponseBody();

        ZipInputStream zipInputStream = new ZipInputStream(body.asInputStream());
        zipInputStream.getNextEntry();
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        assertThat(zipEntry.getName(), is("E-GEOD-3307_A-AFFY-34-analytics.tsv"));

        BufferedReader reader = new BufferedReader(new InputStreamReader(zipInputStream));
        assertThat(reader.readLine(), is("Gene ID\tGene Name\tDesign Element\t'LGMD2I' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'LGMD2I' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic\t'LGMD2I' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange\t'DMD' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'DMD' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic\t'DMD' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange\t'EDMD AD' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'EDMD AD' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic\t'EDMD AD' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange\t'ALS' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'ALS' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic\t'ALS' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange\t'EDMD XR' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'EDMD XR' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic\t'EDMD XR' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange\t'BMD' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'BMD' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic\t'BMD' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange\t'LGMD2B' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'LGMD2B' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic\t'LGMD2B' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange\t'AQM' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'AQM' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic\t'AQM' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange\t'LGMD2A' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'LGMD2A' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic\t'LGMD2A' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange\t'JDM' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'JDM' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic\t'JDM' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange\t'HSP' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'HSP' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic\t'HSP' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange\t'FSHD' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'FSHD' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic\t'FSHD' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange"));
        assertThat(reader.readLine(), is("ENSG00000000457\tSCYL3\t231111_at\t0.242024442651299\t-1.91017397403874\t-0.162979401849388\t0.518726985182683\t0.933546537118964\t0.0790453864593719\t0.96454558994965\t-0.0709221824630889\t-0.00659756447581739\t0.445987349012349\t1.00497306715328\t0.0726209971558287\t0.831371294486443\t0.442145482360745\t0.05234818862571\t0.159293642813997\t-2.32253024514015\t-0.221438691792118\t0.127683146970531\t-2.33202302679386\t-0.170786162024152\t0.919932924196632\t0.133430787992951\t0.0122968563265413\t0.293673895712099\t-1.76520039332919\t-0.131487816796912\t0.000774486134849082\t3.9026735704204\t0.300456061009448\t0.0574808866682653\t-2.98820659372037\t-0.312334774792427\t0.0130556396535823\t3.1690924162594\t0.212702005676215"));
    }

}