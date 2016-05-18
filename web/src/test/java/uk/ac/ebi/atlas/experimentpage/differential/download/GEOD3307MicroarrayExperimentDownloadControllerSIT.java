
package uk.ac.ebi.atlas.experimentpage.differential.download;


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
import static org.hamcrest.Matchers.startsWith;

public class GEOD3307MicroarrayExperimentDownloadControllerSIT {

    private static final String EXPRESSION_ATLAS_VERSION = "# Expression Atlas version:";
    private static final String QUERY_DESCRIPTION = "# Query: Genes matching: '', specifically up/down differentially expressed in any contrast given the p-value cutoff 0.05 and log2-fold change cutoff 0 in experiment E-GEOD-3307";
    private static final String TIMESTAMP = "# Timestamp:";
    private EndPoint subject = new EndPoint("/gxa/experiments/E-GEOD-3307.tsv?geneQuery=&exactMatch=false&foldChangeCutOff=0");

    @Test
    public void verifyHeader() {
        Response response = subject.getResponse();

        // http status code OK
        assertThat(response.getStatusCode(), is(200));

        // unicode encoded plain text
        assertThat(response.getContentType(), is("application/octet-stream;charset=ISO-8859-1"));

        // filename of attachment should be a zip
        assertThat(response.getHeader("Content-Disposition"), is("attachment; filename=\"E-GEOD-3307-query-results.tsv.zip\""));
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
        assertThat(zipEntry.getName(), is("E-GEOD-3307_A-AFFY-33-query-results.tsv"));

        BufferedReader reader = new BufferedReader(new InputStreamReader(zipInputStream));
        assertThat(reader.readLine(), startsWith(EXPRESSION_ATLAS_VERSION));
        assertThat(reader.readLine(), is(QUERY_DESCRIPTION));
        assertThat(reader.readLine(), startsWith(TIMESTAMP));
        assertThat(reader.readLine(), is("Gene ID\tGene Name\tDesign Element\t'LGMD2I' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'LGMD2I' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange\t'LGMD2I' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic\t'EDMD XR' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'EDMD XR' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange\t'EDMD XR' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic\t'BMD' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'BMD' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange\t'BMD' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic\t'EDMD AD' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'EDMD AD' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange\t'EDMD AD' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic\t'HSP' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'HSP' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange\t'HSP' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic\t'FSHD' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'FSHD' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange\t'FSHD' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic\t'JDM' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'JDM' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange\t'JDM' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic\t'LGMD2A' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'LGMD2A' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange\t'LGMD2A' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic\t'AQM' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'AQM' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange\t'AQM' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic\t'ALS' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'ALS' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange\t'ALS' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic\t'LGMD2B' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'LGMD2B' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange\t'LGMD2B' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic\t'DMD' vs 'normal' on 'Affymetrix HG-U133A'.p-value\t'DMD' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange\t'DMD' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic"));
        assertThat(reader.readLine(), is("ENSG00000000003\tTSPAN6\t209108_at\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t3.22392001630122E-5\t0.413107096522765\t5.02112235896101\t\t\t\t\t\t\t0.0119843116644908\t0.313794065216056\t3.12520915964694\t\t\t\t0.0110142369806691\t0.453232593478132\t3.27165792658763"));
    }

    @Test
    public void verifySecondEntry() throws Exception {
        ResponseBody body = subject.getResponseBody();

        ZipInputStream zipInputStream = new ZipInputStream(body.asInputStream());
        zipInputStream.getNextEntry();
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        assertThat(zipEntry.getName(), is("E-GEOD-3307_A-AFFY-34-query-results.tsv"));

        BufferedReader reader = new BufferedReader(new InputStreamReader(zipInputStream));
        assertThat(reader.readLine(), startsWith(EXPRESSION_ATLAS_VERSION));
        assertThat(reader.readLine(), is(QUERY_DESCRIPTION));
        assertThat(reader.readLine(), startsWith(TIMESTAMP));
        assertThat(reader.readLine(), is("Gene ID\tGene Name\tDesign Element\t'FSHD' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'FSHD' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange\t'FSHD' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic\t'EDMD AD' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'EDMD AD' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange\t'EDMD AD' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic\t'BMD' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'BMD' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange\t'BMD' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic\t'DMD' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'DMD' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange\t'DMD' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic\t'LGMD2I' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'LGMD2I' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange\t'LGMD2I' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic\t'EDMD XR' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'EDMD XR' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange\t'EDMD XR' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic\t'AQM' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'AQM' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange\t'AQM' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic\t'LGMD2A' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'LGMD2A' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange\t'LGMD2A' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic\t'HSP' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'HSP' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange\t'HSP' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic\t'JDM' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'JDM' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange\t'JDM' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic\t'LGMD2B' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'LGMD2B' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange\t'LGMD2B' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic\t'ALS' vs 'normal' on 'Affymetrix HG-U133B'.p-value\t'ALS' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange\t'ALS' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic"));
        assertThat(reader.readLine(), is("ENSG00000000457\tSCYL3\t231111_at\t0.0130556396535823\t0.212702005676215\t3.1690924162594\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t7.74486134849082E-4\t0.300456061009448\t3.9026735704204\t\t\t\t\t\t"));
    }

}